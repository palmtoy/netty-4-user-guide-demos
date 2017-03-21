package com.waylau.netty.demo.echo;


import java.util.Date;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!\n", CharsetUtil.UTF_8));
  }

  @Override
  public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
    Date d = new Date();
    System.out.println(d + " ~ Client received : \'" + in.toString(CharsetUtil.UTF_8) + "\'");
    System.exit(0);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}

