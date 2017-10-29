package server;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
class ServerMain {

    public static void main(String[] args) {
        int DEFAULT_PORT = 31463;
        ConverterServer server = new ConverterServer(DEFAULT_PORT);
        server.listen();
    }
}
