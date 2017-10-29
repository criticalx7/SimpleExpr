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
 */
class Connector {
    private final String hostname;
    private final int port;

    Connector(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    String requestResult(String expr) {
        String result = "";
        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println(expr);
            result = in.readLine();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            result = "SERVER OFFLINE";
        }
        return result;

    }
}
