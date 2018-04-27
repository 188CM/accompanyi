package httpServer;

import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;

import handler.JerseyHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import jersey.NettyEndpoint;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final int MESSAGE_SIZE = 1024 * 1024;
    private final ChannelHandler jerseyHandler;
    
    //클라이언트로부터 연결된 채널이 초기화 될때의 기본동작이 지정된 추상 클래스
    public HttpServerInitializer() {
    	 jerseyHandler = createJerseyHandler();
    }

//    @Override
//    public void initChannel(SocketChannel ch) throws Exception {
//        ChannelPipeline p = ch.pipeline();
//        p.addLast("codec", new HttpServerCodec());
//        p.addLast("aggregator", new HttpObjectAggregator(MESSAGE_SIZE));//POST 처리
//        p.addLast("jerseyhandler", new JerseyHandler());
//        
//    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast("codec", new HttpServerCodec());
        p.addLast("aggregator", new HttpObjectAggregator(MESSAGE_SIZE));//POST 처리
        p.addLast("jerseyHandler", jerseyHandler);
    }

    private ChannelHandler createJerseyHandler() {   	
        ResourceConfig resourceConfig = new ResourceConfig().registerClasses(NettyEndpoint.class);
        return new JerseyHandler(new ApplicationHandler(resourceConfig));

    }
    
    
}
