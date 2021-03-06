package child.leisure.rpc.server.net;

import child.leisure.rpc.server.net.handler.RequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Netty服务端，提供Netty网络服务开启关闭的能力
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 18:15
 */
public class NettyRpcServer extends RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyRpcServer.class);

    private Channel channel;

    public NettyRpcServer(int port, String protocol, RequestHandler handler) {
        super(port, protocol, handler);
    }

    @Override
    public void start() {
        // 配置服务器
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new ChannelRequestHandler());
                }
            });
            // 启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            logger.info("Server start successfully on port:{}.", port);
            this.channel = channelFuture.channel();
            // 等待服务通道关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("Fail when start:{}.", e.getMessage(), e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void stop() {
        this.channel.close();
        logger.info("Server channel was close.");
    }

    private class ChannelRequestHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            logger.info("Channel active:{}.", ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("The server receives a message:{}.", msg);
            ByteBuf msgBuf = (ByteBuf) msg;
            byte[] req = new byte[msgBuf.readableBytes()];
            msgBuf.readBytes(req);
            byte[] res = handler.handleRequest(req);
            logger.info("Send response:{}.", msg);
            ByteBuf resBuf = Unpooled.buffer(res.length);
            resBuf.writeBytes(res);
            ctx.write(resBuf);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            logger.error("Exception occurred:{}.", cause.getMessage(), cause);
            ctx.close();
        }
    }
}
