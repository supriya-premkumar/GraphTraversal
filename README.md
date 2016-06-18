Description:
===========

This implements graph methods.

GraphTester.java has the main file.
GraphWrapper.java implements command file parsing 
Graphs.java implements addLink(), removeLink(), and isLinked()

Design Details:
===============

The graph is implemented with adjacency list. Since the graph is known to be sparse. 
This will keep the memory required to represent the graph is O(|V| + |E|)

Since we also want to maintain a reachability matrix, we need an extra O(|V^2|) space.

addLink(): Adds the link between two vertices. 
  TimeComplexity: O(|V| + |E|) to add a link and O(|V^2|) to perfrom breadth first searches to populate the reachability matrix

removeLink(): Removes the link between two vertices.
  TimeComplexity: O(|V| + |E|) to remove a link and O(|V^2|) to perfrom breadth first searches to populate the reachability matrix

isLinked(): Checks whether there is a path between the two nodes.
  TimeComplexity: O(1) Since we are always keeping an up-to-date version of reachability matrix.
  
  
In a real world application with many nodes, we can store the adjacency list map as a distributed hash across multiple machines.
To be more specific we can use a graph database like Titan. And use Gremlin to query the database.
All we need is query if there is path between two nodes using a query like 
g.V.has(vertex).out(vertex) to build a path.
Since this will be a sparse graph the number of traversals required to determine a path between nodes will be lesser.
