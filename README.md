# distributed-systems
Implement a distributed application in order to build a spanning tree over a cluster.
The communication topology among cluster nodes is represented by a strongly connected graph. Each cluster node will have a unique ID.
The communication inside the cluster will use UDP packets.
Use randomly generated delays between communication messages sent to neighbor nodes in order to build different spanning trees
on different runs.
Develop a client application that will be able to connect to any cluster node (the root of the spanning tree) in order to initiate
the construction of the spanning tree. The client application will display the spanning tree once it is built.
