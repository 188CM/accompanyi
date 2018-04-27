package handler;

import java.io.OutputStream;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ContainerException;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.spi.ContainerResponseWriter;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

@ChannelHandler.Sharable
public class JerseyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private volatile ApplicationHandler applicationHandler;

	public JerseyHandler(ApplicationHandler applicationHandler) {
		this.applicationHandler = applicationHandler;
	}
   
	@Override
	protected void messageReceived(ChannelHandlerContext context, FullHttpRequest request) throws Exception {
		// TODO Auto-generated method stub
	
		String protocolName = request.getProtocolVersion().protocolName().toLowerCase();
		String localAddress = context.channel().localAddress().toString();
		String uri = request.getUri();
		String method = request.getMethod().name();
		
		URI appURI = new URI(String.format("%s:/%s", protocolName, localAddress));
		URI fullURI = new URI(String.format("%s:/%s%s", protocolName, localAddress, uri));

		ContainerRequest containerRequest = new ContainerRequest(appURI, fullURI,
							method, getSecurityContext(), new MapPropertiesDelegate());

		for (String key : request.headers().names()) {
			containerRequest.headers(key, request.headers().getAll(key));
		}

		containerRequest.setEntityStream(new ByteBufInputStream(request.content()));
		containerRequest.setWriter(new ResponseWriter(context.channel()));
		
		
		applicationHandler.handle(containerRequest);

	}


	private SecurityContext getSecurityContext() {
		return new SecurityContext() {

			@Override
			public boolean isUserInRole(String role) {
				return false;
			}

			@Override
			public boolean isSecure() {
				return false;
			}

			@Override
			public Principal getUserPrincipal() {
				return null;
			}

			@Override
			public String getAuthenticationScheme() {
				return null;
			}
		};
	}

	private final static class ResponseWriter implements ContainerResponseWriter {

		private FullHttpResponse response;
		private Channel channel;

		ResponseWriter(Channel channel) {
			this.channel = channel;
		}

		@Override
		public void commit() {
			channel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}

		@Override
		public boolean suspend(long timeOut, TimeUnit timeUnit, TimeoutHandler timeoutHandler) {
			return false;
		}

		@Override
		public void setSuspendTimeout(long timeOut, TimeUnit timeUnit) throws IllegalStateException {

		}


		@Override
		public OutputStream writeResponseStatusAndHeaders(long contentLength, ContainerResponse containerResponse)
				throws ContainerException {

			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
					HttpResponseStatus.valueOf(containerResponse.getStatus()));

			for (final Map.Entry<String, List<String>> e : containerResponse.getStringHeaders().entrySet()) {
				for (final String value : e.getValue()) {
					response.headers().add(e.getKey(), value);
				}
			}

			return new ByteBufOutputStream(response.content());
		}

		@Override
		public void failure(Throwable error) {
			channel.close();
		}

		@Override
		public boolean enableResponseBuffering() {
			return true;
		}

	}

}
