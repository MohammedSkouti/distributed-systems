import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class ClusterNode {

    private int id;
    private int parent;
    private boolean root;
    private DatagramSocket socket;

    public ClusterNode(int id, int parent, boolean root) throws IOException {
        this.id = id;
        this.parent = parent;
        this.root = root;
        this.socket = new DatagramSocket();
    }

    public void buildSpanningTree() throws IOException, InterruptedException {
        if (root) {
            // If this node is the root, broadcast a message to all other nodes
            // to initiate the construction of the spanning tree
            broadcast("INITIATE");
        } else {
            // If this node is not the root, wait for a message from the root
            waitForMessage();
        }
    }

    private void waitForMessage() throws IOException, InterruptedException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String message = new String(packet.getData(), 0, packet.getLength());
        if (message.equals("INITIATE")) {
            // If the message is an "INITIATE" message, send a message to the parent
            // node with this node's ID and wait for a response
            sendMessage(parent, "ID " + id);
            waitForMessage();
        } else if (message.startsWith("ID")) {
            // If the message is an "ID" message, extract the ID of the sender
            // and send a message to the parent node with the sender's ID
            int senderId = Integer.parseInt(message.split(" ")[1]);
            sendMessage(parent, "ID " + senderId);
        } else if (message.equals("DONE")) {
            // If the message is a "DONE" message, the construction of the
            // spanning tree is complete. Display the tree.
            displaySpanningTree();
        }
    }

    private void sendMessage(int recipientId, String message) throws IOException, InterruptedException {
        InetAddress address = InetAddress.getByName("localhost");
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, address, recipientId);
        // Introduce a random delay before sending the message
        Thread.sleep(new Random().nextInt(1000));
        socket.send(packet);
    }

    private void broadcast(String message) throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            if (i != id) {
                sendMessage(i, message);
            }
        }
    }

    private void displaySpanningTree() {
        // Display the spanning tree
        System.out.println("Spanning tree: " + id);
    }

}

