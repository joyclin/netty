package com.joyce.netty.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

/**
 * @author joyce
 * @date 2018/4/25
 */
public class EchoServer {

  private final int port;

  public EchoServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws Exception {
    new EchoServer(8080).start();
  }

  public void start() throws Exception {
    NioEventLoopGroup group = new NioEventLoopGroup();
    NioEventLoopGroup workGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(group,workGroup)
          // 指定使用 NIO 的传输 Channel
          .channel(NioServerSocketChannel.class)
          // 设置 socket 地址使用所选的端口
          .localAddress(new InetSocketAddress(port))
          // 添加 EchoServerHandler 到 Channel 的 ChannelPipeline
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast(new EchoServerHandler());
            }
          });
      // 绑定的服务器;sync 等待服务器关闭
      ChannelFuture f = b.bind().sync();
      System.out.println(
          EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
      f.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
      workGroup.shutdownGracefully().sync();
    }
  }
}

