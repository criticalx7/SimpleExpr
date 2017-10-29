package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.*;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
class ConverterServer {
    private final Logger logger = Logger.getLogger(ConverterServer.class.getName());
    private final AtomicInteger numThreads = new AtomicInteger(0);
    private final int port;

    ConverterServer(int port) {
        this.port = port;
        // logger handle
        logger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            @Override
            public String format(LogRecord record) {
                return String.format("%s %s: %s%n",
                        timeFormatter.format(LocalDateTime.ofInstant(record.getInstant(), ZoneId.systemDefault())),
                        record.getLevel(),
                        record.getMessage());
            }
        });
        logger.addHandler(handler);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    void listen() {
        logger.info(String.format("Try connecting to port: %d", port));
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info(String.format("Listening on: %d%n", server.getLocalPort()));
            while (true) {
                Socket client = server.accept();
                logger.info(String.format("Thread %d started for %s", numThreads.incrementAndGet(), client.getLocalAddress()));
                ServerThread serverThread = new ServerThread(client);
                serverThread.start();
            }
        } catch (IOException e) {
            logger.severe(String.format("Cannot connect to port: %d, will now terminated.", port));
            System.exit(-1);
        }
    }

    private class ServerThread extends Thread {

        private final Socket client;

        ServerThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                String clientInput = in.readLine();
                logger.info(String.format("Processing %s", clientInput));
                ConverterProtocol protocol = new ConverterProtocol();
                String output = protocol.process(clientInput);
                out.println(output);
                logger.info(String.format("Sent: %s", output));
                logger.info(String.format("Client: %s exited%n", client.getLocalAddress()));
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
