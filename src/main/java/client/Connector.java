package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 * <p>
 * This class handle connection to server
 */
class Connector {
    private final String hostname;
    private final int port;
    private final InputProcessor processor = new InputProcessor();

    /**
     * @param hostname - to connect host name
     * @param port     - to connect port
     */
    Connector(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Open a connection and request result from server
     *
     * @param expr An expression to be sent
     * @return A result string either answer or error
     */
    String requestResult(String expr) {
        String result = "";
        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println(expr);
            String input = in.readLine();
            result = processor.process(input);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            result = "SERVER OFFLINE";
        }
        return result;

    }
}
