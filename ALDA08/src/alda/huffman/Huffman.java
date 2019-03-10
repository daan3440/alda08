package alda.huffman;
/*
 * ALDA08 - Algoritmdesigntekniker
 * Huffman
 * Daniel Andersson - daan3440
 *  
 */

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
	//Visibility for testing
	Map<Character, Integer> stats(char[] charArray) {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (char c : charArray) {
			Character character = new Character(c);
			if (map.containsKey(character)) {
				map.put(character, map.get(character) + 1);
			} else {
				map.put(character, 1);
			}
		}
		return map;
	}

	private Map<Character, String> buildEncodingInfo(List<Node> leafNodes) {
		Map<Character, String> direction = new HashMap<Character, String>();
		for (Node leafNode : leafNodes) {
			Character character = new Character(leafNode.getChars().charAt(0));
			String keyForChar = "";
			Node currentNode = leafNode;

			while (currentNode.getParent() != null) {
				if (currentNode.isLeftChild()) {
					keyForChar = "0" + keyForChar;
				} else {
					keyForChar = "1" + keyForChar;
				}
				currentNode = currentNode.getParent();
			}
			direction.put(character, keyForChar);
		}
		return direction;
	}

	private Tree createTree(Map<Character, Integer> stats, List<Node> leafs) {
		Character[] keys = stats.keySet().toArray(new Character[0]);
		PriorityQueue<Node> prioQueue = new PriorityQueue<Node>();
		for (Character character : keys) {
			Node node = new Node();
			node.setChars(character.toString());
			node.setFrequence(stats.get(character));
			prioQueue.add(node);
			leafs.add(node);
		}
		if(prioQueue.size()== 1) {
			Node node1 = prioQueue.poll();
			Node sumNode = new Node();
			sumNode.setChars(node1.getChars());
			sumNode.setFrequence(node1.getFrequence());
			sumNode.setLeftNode(node1);
			node1.setParent(sumNode);
			prioQueue.add(sumNode);
		}else {
			int size = prioQueue.size() ;
			for (int i = 1; i <= size- 1; i++) {
				Node node1 = prioQueue.poll();
				Node node2 = prioQueue.poll();
				Node sumNode = new Node();
				sumNode.setChars(node1.getChars() + node2.getChars());
//				System.out.println("Node chars: " + sumNode.getChars().toString());
				sumNode.setFrequence(node1.getFrequence() + node2.getFrequence());
//				System.out.println("Node Freq: " + sumNode.getFrequence());
				sumNode.setLeftNode(node1);
				sumNode.setRightNode(node2);
				node1.setParent(sumNode);
				node2.setParent(sumNode);
				prioQueue.add(sumNode);
			}
		}
		Tree tree = new Tree();
		tree.setRoot(prioQueue.poll());
		return tree;
	}

	//Visibility for testing
	String encode(String originalString, Map<Character, Integer> stats) {
		if (originalString == null || originalString.equals("")) {
			return "";
		}
		char[] charArray = originalString.toCharArray();
		List<Node> leafNodes = new ArrayList<Node>();
		createTree(stats, leafNodes);
		Map<Character, String> encodInfo = buildEncodingInfo(leafNodes);

		StringBuffer buffer = new StringBuffer();
		for (char c : charArray) {
			Character character = new Character(c);
//			System.out.println("Chars: " + character);

			buffer.append(encodInfo.get(character));
//			System.out.println("ReturnBuffer: " + buffer);
		}
		return buffer.toString();
	}
	//Visibility for testing
	String decode(String binaryStr,Map<Character, Integer> stats) {
		if (binaryStr == null || binaryStr.equals("")) {
			return "";
		}
		char[] binaryCharArray = binaryStr.toCharArray();
		LinkedList<Character> binaryList = new LinkedList<Character>();
		int size = binaryCharArray.length;
		for (int i = 0; i < size; i++) {
			binaryList.addLast(new Character(binaryCharArray[i]));
		}
		List<Node> leafNodes = new ArrayList<Node>();
		Tree tree = createTree(stats, leafNodes);
		StringBuffer returnBuffer = new StringBuffer();
		while (binaryList.size() > 0) {
			Node node = tree.getRoot();

			do {
				Character c = binaryList.removeFirst();
				if (c.charValue() == '0') {
					node = node.getLeftNode();
				} else {
					node = node.getRightNode();
				}
			} while (!node.isLeaf());
			returnBuffer.append(node.getChars());
		}
		return returnBuffer.toString();
	}
	String readFile(String path, Charset encoding) 
			throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private void run() {
		String inputFileName = null;
		String outputFileName = null;
		String expandFileName = null;
		FileOutputStream outStream = null;
		OutputStreamWriter writer = null;

		try {
			try {
				inputFileName = "inFile.txt";//args[0];
				outputFileName = "outFile.txt";// args[1];
				expandFileName= "expandedFile.txt";// args[2];

				String preEncode = readFile(inputFileName, StandardCharsets.UTF_8);
				Map<Character, Integer> stats = stats(preEncode.toCharArray());

				String encodedBinString = encode(preEncode, stats);
				String decodedString = decode(encodedBinString, stats);

//				System.out.println("Original String: " + preEncode);
//				System.out.println("Huffman encoded: " + encodedBinString);
//				System.out.println("Decoded: " + decodedString);	

				outStream = new FileOutputStream(outputFileName);
				writer = new OutputStreamWriter(outStream);
				writer.write(encodedBinString);
				writer.flush();
				writer.close();

				outStream = new FileOutputStream(expandFileName);
				writer = new OutputStreamWriter(outStream);
				writer.write(decodedString);
				writer.flush();
				writer.close();
			}
			catch (Exception e) {
				System.out.println("Exception inner: " + e);
			}
			finally {
				if (writer != null)
					writer.close();
				if (outStream != null)
					outStream.close();
			}
		}
		catch (Exception e) {
			System.out.println("Exception outer: " + e);
		}
	}

	public static void main(String[] args) {
		new Huffman().run();
	}
}

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