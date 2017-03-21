package com.waylau.netty.demo.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 处理服务端 channel.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Date d = new Date();
        System.out.println(d + " ~ Msg from " + ctx.channel().remoteAddress()+"->Server : " + msg.toString());
        System.out.println("Msg length = " + msg.toString().length() + "\n");
        ctx.write("Msg from svr - " + msg); // (1)
        ctx.flush(); // (2)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
