package child.leisure.rpc.client.net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 发送消息处理类
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 11:28
 */
public class SendHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SendHandler.class);

    private CountDownLatch cdl;
    private byte[] data;
    private Object readMsg;

    public SendHandler(byte[] data) {
        this.cdl = new CountDownLatch(1);
        this.data = data;
    }

    /**
     * 当连接成功后，发送消息
     *
     * @param ctx 通道上下文
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("Success connect to server:{}.", ctx);
        ByteBuf reqBuf = Unpooled.buffer(data.length);
        reqBuf.writeBytes(data);
        logger.info("Client send message:{}.", reqBuf);
        ctx.writeAndFlush(reqBuf);
    }

    /**
     * 读取数据，数据读取完毕后释放锁
     *
     * @param ctx 通道上下文
     * @param msg 获取的消息内容
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("Client read the response message:{}.", msg);
        ByteBuf msgBuf = (ByteBuf) msg;
        byte[] res = new byte[msgBuf.readableBytes()];
        msgBuf.writeBytes(res);

        this.readMsg = res;
        this.cdl.countDown();
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

    /**
     * 获得响应的消息内容
     *
     * @return 响应的消息内容
     * @throws InterruptedException 当前线程被打断异常
     */
    public Object getResponseData() throws InterruptedException {
        this.cdl.await();
        return this.readMsg;
    }
}
