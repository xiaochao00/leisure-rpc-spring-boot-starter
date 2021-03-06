# 手写rpc

参考：

[看了这篇你就会手写RPC框架了](https://www.cnblogs.com/itoak/p/13370031.html)

[手写一个RPC框架](https://www.cnblogs.com/2YSP/p/13545217.html)

[介绍 netty](https://blog.csdn.net/acmman/article/details/87448666)

[介绍 rpc](https://blog.csdn.net/acmman/article/details/88370184)


## 客户端

需要做的事情？
> 客户端想要调用远程服务，必须具备服务发现的能力；<br/>
> 在知道有哪些服务过后，还必须有服务代理来执行服务调用；<br/>
> 客户端想要与服务端通信，必须要有相同的消息协议；<br/>
> 客户端想要调用远程服务，那么必须具备网络请求的能力，即网络层功能。<br/>
> 当然，这是客户端所需的最基本的能力，其实还可以扩展的能力，例如负载均衡。


## 服务端
> 首先，服务端要提供远程服务，必须具备服务注册及暴露的能力；<br/>
> 在这之后，还需要开启网络服务，供客户端连接；<br/>
> 有些项目可能既是服务提供者，又是服务消费者，那什么时候开启服务，什么时候注入服务呢？这里我们引入一个RPC处理者的概念，由它来帮我们开启服务，以及注入服务。