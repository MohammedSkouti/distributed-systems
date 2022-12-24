import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Create a scanner to read input from the user
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ID of the root node: ");
        int rootId = scanner.nextInt();

        // Create a cluster node for each ID
        ClusterNode[] nodes = new ClusterNode[10];
        for (int i = 0; i < 10; i++) {
            boolean root = (i == rootId);
            nodes[i] = new ClusterNode(i, (i + 1) % 10, root);
        }

        // Connect the client application to the root node
        ClientApplication client = new ClientApplication(rootId);
        client.connect();

        // Build the spanning tree
        for (ClusterNode node : nodes) {
            node.buildSpanningTree();
        }
    }

}

