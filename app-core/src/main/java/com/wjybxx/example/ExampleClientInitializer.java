package com.wjybxx.example;

import com.wjybxx.netty.protobufcodec.ByteToProtoBufDecoder;
import com.wjybxx.netty.protobufcodec.ProtoBufToByteEncoder;
import com.wjybxx.protobuf.MessageDispatcher;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ExampleClientInitializer extends ChannelInitializer<SocketChannel> {

    private final MessageDispatcher messageDispatcher;

    public ExampleClientInitializer(MessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(8192,0,4,0,4))
                .addLast(new ByteToProtoBufDecoder())
                .addLast(new ProtoBufToByteEncoder());

        // 这里是客户端的逻辑处理器
        pipeline.addLast(new ExampleClientHandler(messageDispatcher));
    }
}
