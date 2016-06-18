Description:
===========

This implements graph methods.

<br>GraphTester.java has the main file.
<br>GraphWrapper.java implements command file parsing 
<br>Graphs.java implements addLink(), removeLink(), and isLinked()
<br>cmdFile.in is the input file which has the commands to be executed.

Design Details:
===============

The graph is implemented with adjacency list. Since the graph is known to be sparse. 
This will ensure that the space required to represent the graph is O(|V| + |E|)

Since we also want to maintain a reachability matrix(for O(1) isLinked()), we need an extra O(|V^2|) space.

<strong>addLink():</strong> Adds the link between two vertices. 
    <br> TimeComplexity:
    <br>--------------------
        <br>O(1) to add a link and O((|E| + |V|) ^ 2) to perfrom breadth first searches to populate the reachability matrix

<strong>removeLink():</strong> Removes the link between two vertices.
  <br>TimeComplexity: 
  <br>---------------
        <br>O(1) to remove a link and O((|E| + |V|) ^ 2) to perfrom breadth first searches to populate the reachability matrix

<strong>isLinked():</strong> Checks whether there is a path between the two nodes.
  <br>TimeComplexity: 
  <br>---------------
        <br>O(1) Since we are always keeping an up-to-date version of reachability matrix.
  

Real World Application Design Constraints:
==========================================
In a real world application with many nodes, we can store the adjacency list map as a distributed hash across multiple machines.
To be more specific we can use a graph database like Titan. And use Gremlin to query the database.
All we need is query if there is path between two nodes using a query like 
g.V.has(vertex).out(vertex) to build a path.
Since this will be a sparse graph the number of traversals required to determine a path between nodes will be lesser.

Also in a real world application we should make addLink(), removeLink() and isLinked() thread safe.
