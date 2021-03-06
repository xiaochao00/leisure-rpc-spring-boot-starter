package child.leisure.rpc.client.net;

import child.leisure.rpc.client.net.handler.SendHandler;
import child.leisure.rpc.common.service.Service;
import child.leisure.rpc.common.util.LeisureRpcUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty 网络请求客户端
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 11:16
 */
public class NettyNetClient implements NetClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyNetClient.class);

    @Override
    public byte[] sendRequest(byte[] data, Service service) throws InterruptedException {
        String[] ipPort = LeisureRpcUtil.parserServiceAddress(service.getAddress());
        String serverAddress = ipPort[0];
        int serverPort = Integer.parseInt(ipPort[1]);

        SendHandler sendHandler = new SendHandler(data);
        byte[] resData;
        // 配置客户端
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(sendHandler);
                        }
                    });
            // 启动客户端
            bootstrap.connect(serverAddress, serverPort).sync();
            resData = (byte[]) sendHandler.getResponseData();
            logger.info("Get send response:{}.", resData);
        } finally {
            group.shutdownGracefully();
        }
        return resData;
    }
}
