package com.exemple.reactor.Utility;

import java.nio.ByteBuffer;

// ? Classe per gestire lo scambio di messaggi 
public class MsgCodec {
    public static ByteBuffer encode(final String msg) {
        return ByteBuffer.wrap(msg.getBytes());
    }

    public static String decode(final ByteBuffer buffer) {
        return new String(buffer.array(), buffer.arrayOffset(), buffer.remaining());
    }
}