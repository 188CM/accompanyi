

import httpServer.HttpServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
    public static final int PORT = 8080;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    private final ServerBootstrap bootstrap;

    public HttpServer() {
        //bossGroup 클라이언트 연결을 수락하는 부모 스레드 그룹
        //NioEventLoopGroup(인수) 스레드 그룹 내에서 생성할 최대 스레드 수
        bossGroup = new NioEventLoopGroup();
        
        //연결된 클라이언트 소켓으로부터 데이터 입출력(I/O) 및 이벤트처리 담당하는 자식 쓰레드 그룹
        //인수가 없으면 CPU 코어 수에 따른 쓰레드의 수 결정.
        workerGroup = new NioEventLoopGroup();

        //부트 스트랩 객체 생성
        bootstrap = new ServerBootstrap();
        
        //스레드 그룹 초기화
        bootstrap.group(bossGroup, workerGroup);
        
        //채널 초기화
        bootstrap.channel(NioServerSocketChannel.class);

        //자식 채널의 초기화
        bootstrap.childHandler(new HttpServerInitializer());

    
    }

    
    public void start() throws Exception {
        try {
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            //wait until the server socket is closed.
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpServer().start();
    }
}
