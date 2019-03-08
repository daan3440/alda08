package alda.huffman;

class Tree {
	private Node root;

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
}
class Node implements Comparable<Node> {
	private String chars = "";
	private int freq = 0;
	private Node parent;
	private Node leftNode;
	private Node rightNode;

	@Override
	public int compareTo(Node n) {
		return freq - n.freq;
	}

	public boolean isLeaf() {
		return chars.length() == 1;
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeftChild() {
		return parent != null && this == parent.leftNode;
	}

	public int getFrequence() {
		return freq;
	}

	public void setFrequence(int frequence) {
		this.freq = frequence;
	}

	public String getChars() {
		return chars;
	}

	public void setChars(String chars) {
		this.chars = chars;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}
}
