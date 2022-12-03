package com.exemple.reactor.Server.Handlers;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import lombok.extern.java.Log;

@Log
public class WriteEventHandler implements EventHandler {
    public void handleEvent(SelectionKey handle) throws Exception {
        log.info("Write Event Handler");

        SocketChannel socketChannel = (SocketChannel) handle.channel();
        ByteBuffer inputBuffer = (ByteBuffer) handle.attachment();

        // ? Simulo esecuzione di codice
        Thread.sleep(10000);

        socketChannel.write(inputBuffer);
        socketChannel.close();
    }
}
