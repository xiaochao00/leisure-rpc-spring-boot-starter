package child.leisure.rpc.server.net;

import child.leisure.rpc.server.net.handler.RequestHandler;

/**
 * RPC服务抽象类
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 17:53
 */
public abstract class RpcServer {
    protected int port;
    protected String protocol;
    protected RequestHandler handler;

    public RpcServer(int port, String protocol, RequestHandler handler) {
        super();
        this.port = port;
        this.protocol = protocol;
        this.handler = handler;
    }

    public abstract void start();

    public abstract void stop();

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public RequestHandler getHandler() {
        return handler;
    }

    public void setHandler(RequestHandler handler) {
        this.handler = handler;
    }
}
