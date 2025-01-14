package aoc.project.year2023.day25;

public record Edge(String firstNode, String secondNode) {

    public boolean equals(Object other) {
        Edge otherEdge = ((Edge) other);
        return 
        otherEdge.firstNode().equals(this.firstNode()) && otherEdge.secondNode().equals(this.secondNode())
        || otherEdge.secondNode().equals(this.firstNode()) && otherEdge.firstNode().equals(this.secondNode());
    }

    public String toString() {
        return firstNode() + "-" + secondNode();
    }
}
