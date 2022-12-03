package com.exemple.reactor.Server;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.exemple.reactor.Server.Handlers.EventHandler;

/*
 * Reactor Pattern
 * 
 * @author: Mauro Zorzin 866001
 * @version: 1.0
 * @since: 2020-12-01
 * Università degli Studi Milano Bicocca
 * Ingerieria del Software
 * 
 * Risorse utili 
 * https://wiki.sch.bme.hu/images/5/50/Sznikak_Pattern-Oriented-SA_vol2.pdf
 * https://www.dre.vanderbilt.edu/~schmidt/PDF/reactor-siemens.pdf
 * file:///C:/Users/mauze/Desktop/Scalable%20IO%20in%20Java.pdf
 * https://dzone.com/articles/the-hollywood-principle (Hollywood Principle)
 * https://github.com/kasun04/nio-reactor
 * https://en.wikipedia.org/wiki/Non-blocking_I/O_(Java)
 * 
 * 
 */

public class Reactor {
    private Map<Integer, EventHandler> registeredHandlers = new ConcurrentHashMap<Integer, EventHandler>();
    private Selector demultiplexer;

    public Reactor() throws Exception {
        demultiplexer = Selector.open();
    }

    public Selector getDemultiplexer() {
        return demultiplexer;
    }

    // * Rimuove l'evento dal selettore
    public void removeEventHandler(SelectionKey handle) {
        registeredHandlers.remove(handle.interestOps());
    }

    // * Associa l'evento con il suo handler
    public void registerEventHandler(
            int eventType, EventHandler eventHandler) {
        registeredHandlers.put(eventType, eventHandler);
    }

    // * Associa il canale con il selettore tramite la register
    public void registerChannel(
            int eventType, SelectableChannel channel) throws Exception {
        channel.register(demultiplexer, eventType);
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                // * Il selettore attende che un canale sia pronto per l'operazione
                if (demultiplexer.select() != 0)
                    continue;

                Set<SelectionKey> readyHandles = demultiplexer.selectedKeys();
                Iterator<SelectionKey> handleIterator = readyHandles.iterator();

                while (handleIterator.hasNext()) {
                    SelectionKey handle = handleIterator.next();

                    // * Eseguo il dispatching dell'evento
                    dispatch(handle);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void dispatch(SelectionKey handle) throws Exception {
        // * Chiamo l'handler approporiato per l'evento
        EventHandler handler = registeredHandlers.get(handle.interestOps());
        handler.handleEvent(handle);
    }

}
