package com.exemple.reactor.Handlers;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.exemple.reactor.Utility.MsgCodec;

import lombok.extern.java.Log;

@Log
public class ReadEventHandler implements EventHandler {

    private Selector demultiplexer;

    private ByteBuffer inputBuffer = ByteBuffer.allocate(1024);

    public ReadEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }

    public void handleEvent(SelectionKey handle) throws Exception {
        log.info("Read Event Handler");

        SocketChannel socketChannel = (SocketChannel) handle.channel();

        socketChannel.read(inputBuffer);
        String s = MsgCodec.decode(inputBuffer);
        inputBuffer.clear();
        System.out.println("Received message from client : " + s);

        // ? Simulo esecuzione di codice
        Thread.sleep(10000);

        // * Passo il messaggio al demultiplexer
        socketChannel.register(demultiplexer, SelectionKey.OP_WRITE, MsgCodec.encode(s));

    }
}
