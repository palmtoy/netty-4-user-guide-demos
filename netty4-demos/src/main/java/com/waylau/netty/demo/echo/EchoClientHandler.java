package com.waylau.netty.demo.echo;

import java.util.Date;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

	private final ByteBuf firstMessage;

	public EchoClientHandler() {
		System.out.println("EchoClient.SIZE = " + EchoClient.SIZE);
		firstMessage = Unpooled.buffer(EchoClient.SIZE+1);
		for (int i = 1; i < firstMessage.capacity(); i ++) {
			byte b = (byte)(i+64);
			firstMessage.writeByte(b);
		}
		firstMessage.writeByte('\n');
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(firstMessage);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Date d = new Date();
		ByteBuf in = (ByteBuf) msg;
		System.out.println(d + " ~ Client received : \'" + in.toString(CharsetUtil.UTF_8) + "\'");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		Date d = new Date();
		System.out.println(d + " ~ ChannelReadComplete is running ... ");
		ctx.flush();
		System.exit(0);
	}

	@Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}

