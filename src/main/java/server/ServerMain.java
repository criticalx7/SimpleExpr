package server;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
class ServerMain {

    public static void main(String[] args) {
        int port = 31463;
        ConverterServer server = new ConverterServer(port);
        server.listen();

    }
}
