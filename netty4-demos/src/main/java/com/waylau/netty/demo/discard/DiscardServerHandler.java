package com.waylau.netty.demo.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;

/**
 * 处理服务端channel
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 默默地丢弃收到的数据
		// ((ByteBuf) msg).release();

		/*
		try {
			// Do something with msg
		} finally {
			ReferenceCountUtil.release(msg);
		}
		*/

		ByteBuf in = (ByteBuf) msg;
		try {
			Date d = new Date();
      System.out.print(d + " ~ Msg from " + ctx.channel().remoteAddress()+"->Server : ");
			int len = 0;
			while (in.isReadable()) {
				System.out.print((char) in.readByte());
				len++;
			}
			System.out.println("Msg length = " + len + "\n");
			System.out.flush();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}

