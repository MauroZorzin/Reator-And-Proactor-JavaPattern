package com.exemple.reactor;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

import com.exemple.reactor.Handlers.*;

import lombok.extern.java.Log;

@Log
public class ReactorManager {
    private static final int SERVER_PORT = 7070;

    public void startReactor(int port) throws Exception {

        ServerSocketChannel server = ServerSocketChannel.open();

        server.socket().bind(new InetSocketAddress(port));

        server.configureBlocking(false);

        Reactor reactor = new Reactor();

        // * Associamo il canale con il selettore tramite la register
        reactor.registerChannel(SelectionKey.OP_ACCEPT, server);

        // * Associamo ad ogni evento il suo handler
        reactor.registerEventHandler(
                SelectionKey.OP_ACCEPT, new AcceptEventHandler(
                        reactor.getDemultiplexer()));

        reactor.registerEventHandler(
                SelectionKey.OP_READ, new ReadEventHandler(
                        reactor.getDemultiplexer()));

        reactor.registerEventHandler(
                SelectionKey.OP_WRITE, new WriteEventHandler());

        reactor.run(); // * Run the dispatcher loop

    }

    public static void main(String[] args) {
        log.info("Server Started at port : " + SERVER_PORT);
        try {
            new ReactorManager().startReactor(SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
