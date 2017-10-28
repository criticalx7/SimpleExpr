package server;

import server.model.ConverterFactory;
import server.model.ExpressionConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

class ConverterServer {
    private final Logger logger = Logger.getLogger(ConverterServer.class.getName());
    private final AtomicInteger numThreads = new AtomicInteger(0);
    private final int port;

    ConverterServer(int port) {
        logger.setUseParentHandlers(false);
        this.port = port;
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("%s: %s%n", record.getLevel(), record.getMessage());
            }
        });
        logger.addHandler(handler);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    void listen() {
        logger.info("Try connecting to port: " + port);
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Listening on: " + server.getLocalPort());
            while (true) {
                Socket client = server.accept();
                ServerThread serverThread = new ServerThread(client);
                serverThread.start();
                logger.info("Thread " + numThreads.incrementAndGet() + " started/n");
            }
        } catch (IOException e) {
            logger.severe("Cannot connect to port: " + port + " terminated");
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
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))
            ) {
                String clientInput = in.readLine();
                String[] clientData = clientInput.split(" ");
                String mode = clientData[0];
                String expr = clientData[1];
                String result = getConverter(mode).convert(expr);
                out.println(result);
                logger.info("Client: " + client.getLocalAddress() + " exited");
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private ExpressionConverter getConverter(String mode) {
            ExpressionConverter converter = null;
            switch (mode) {
                case "POSTFIX":
                    converter = ConverterFactory.getPostfixConverter();
                    break;
                case "PREFIX":
                    converter = ConverterFactory.getPrefixConverter();
            }
            return converter;
        }

    }
}
