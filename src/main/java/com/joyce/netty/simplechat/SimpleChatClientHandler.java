package com.joyce.netty.simplechat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lilin
 * @date 2018/6/26
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
    System.out.println(s);
  }
}
