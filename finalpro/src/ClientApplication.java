import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientApplication {

    private int rootId;
    private DatagramSocket socket;

    public ClientApplication(int rootId) throws IOException {
        this.rootId = rootId;
        this.socket = new DatagramSocket();
    }

    public void connect() throws IOException {
        // Send a message to the root node to initiate the construction of the spanning tree
        sendMessage(rootId, "INITIATE");
    }

    private void sendMessage(int recipientId, String message) throws IOException {
        InetAddress address = InetAddress.getByName("localhost");
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, address, recipientId);
        socket.send(packet);
    }

}
