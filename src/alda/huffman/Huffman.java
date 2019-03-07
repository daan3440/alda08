package alda.huffman;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

private Map<Character, Integer> statistics(char[] charArray) {
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
		Map<Character, String> codewords = new HashMap<Character, String>();
		for (Node leafNode : leafNodes) {
			Character character = new Character(leafNode.getChars().charAt(0));
			String codeword = "";
			Node currentNode = leafNode;
 
			do {
				if (currentNode.isLeftChild()) {
					codeword = "0" + codeword;
				} else {
					codeword = "1" + codeword;
				}
 
				currentNode = currentNode.getParent();
			} while (currentNode.getParent() != null);
 
			codewords.put(character, codeword);
		}
 
		return codewords;
	}

private static Tree buildTree(Map<Character, Integer> statistics,
			List<Node> leafs) {
		Character[] keys = statistics.keySet().toArray(new Character[0]);
 
		PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();
		for (Character character : keys) {
			Node node = new Node();
			node.setChars(character.toString());
			node.setFrequence(statistics.get(character));
			priorityQueue.add(node);
			leafs.add(node);
		}
 
		int size = priorityQueue.size();
		for (int i = 1; i <= size - 1; i++) {
			Node node1 = priorityQueue.poll();
			Node node2 = priorityQueue.poll();
 
			Node sumNode = new Node();
			sumNode.setChars(node1.getChars() + node2.getChars());
			sumNode.setFrequence(node1.getFrequence() + node2.getFrequence());
 
			sumNode.setLeftNode(node1);
			sumNode.setRightNode(node2);
 
			node1.setParent(sumNode);
			node2.setParent(sumNode);
 
			priorityQueue.add(sumNode);
		}
 
		Tree tree = new Tree();
		tree.setRoot(priorityQueue.poll());
		return tree;
}

public String encode(String originalStr,
			Map<Character, Integer> statistics) {
		if (originalStr == null || originalStr.equals("")) {
			return "";
		}
 
		char[] charArray = originalStr.toCharArray();
		List<Node> leafNodes = new ArrayList<Node>();
		buildTree(statistics, leafNodes);
		Map<Character, String> encodInfo = buildEncodingInfo(leafNodes);
 
		StringBuffer buffer = new StringBuffer();
		for (char c : charArray) {
			Character character = new Character(c);
			buffer.append(encodInfo.get(character));
		}
 
		return buffer.toString();
}
	public String decode(String binaryStr,
			Map<Character, Integer> statistics) {
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
		Tree tree = buildTree(statistics, leafNodes);
 
		StringBuffer buffer = new StringBuffer();
 
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
 
			buffer.append(node.getChars());
		}
 
		return buffer.toString();
	}
	private void run() {
		String inputFileName = null;
		String outputFileName = null;
//		Stream parser = null;
//		INode root = null; // Root of the parse tree.
		StringBuilder builder = null;
		FileOutputStream outStream = null;
		OutputStreamWriter writer = null;
		
		try {
			try {
//				if (args.length < 2)
//					throw new Exception("Incorrect number of parameters to program.");
				inputFileName = "AAAABBBCCDDFFSSSSAAWWÅQPLLL";//args[0];
				if(inputFileName != null)
					System.out.println("notNULL");
				outputFileName = "/Users/daniel/Documents/000_eclipse_workspace/ALDA08/outFile.txt";// args[1];
				if(outputFileName != null)
					System.out.println("outnNtNULL");
				
//				parser = new Parser();
//				parser.open(inputFileName);
//				root = parser.parse();
//				builder = new StringBuilder();
//				builder.append("PARSE TREE:\n");
//				root.buildString(builder, 0);
//				builder.append("\nEVALUATION:\n");
//				builder.append(root.evaluate(null));
				
				//TODO INPUT Stream
				//Läs in Fil
				String oriStr = inputFileName;// parser.open(inputFileName);// "Huffman codes compress data very effectively: savings of 20% to 90% are typical, depending on the characteristics of the data being compressed.";
//				String oriStr = "Huffman codes compress data very effectively: savings of 20% to 90% are typical, depending on the characteristics of the data being compressed.";
				Map<Character, Integer> statistics = statistics(oriStr.toCharArray());
				String encodedBinariStr = encode(oriStr, statistics);
				String decodedStr = decode(encodedBinariStr, statistics);
				
				
				//TODO Printout Stream
				System.out.println("Original sstring: " + oriStr);
				System.out.println("Huffman encoed binary string: " + encodedBinariStr);
				System.out.println("decoded string from binariy string: " + decodedStr);
				
				System.out.println("binary string of UTF-8: "
						+ getStringOfByte(oriStr, Charset.forName("UTF-8")));
				System.out.println("binary string of UTF-16: "
						+ getStringOfByte(oriStr, Charset.forName("UTF-16")));
				System.out.println("binary string of US-ASCII: "
						+ getStringOfByte(oriStr, Charset.forName("US-ASCII")));
				System.out.println("binary string of GB2312: "
						+ getStringOfByte(oriStr, Charset.forName("GB2312")));
				
				//TODO OUTPUT
				outStream = new FileOutputStream(outputFileName);
				writer = new OutputStreamWriter(outStream);
//				writer.write(builder.toString());
				writer.write("Original sstring: " + oriStr +
				"\nHuffman encoed binary string: " + encodedBinariStr+
				"\ndecoded string from binariy string: " + decodedStr+
				"\nbinary string of UTF-8: "
						+ getStringOfByte(oriStr, Charset.forName("UTF-8"))+
				"\nbinary string of UTF-16: "
						+ getStringOfByte(oriStr, Charset.forName("UTF-16") )+
				"\nbinary string of US-ASCII: "
						+ getStringOfByte(oriStr, Charset.forName("US-ASCII"))+
				"\nbinary string of GB2312: "
						+ getStringOfByte(oriStr, Charset.forName("GB2312")));
			}
			catch (Exception exception) {
				System.out.println("EXCEPTION 1: " + exception);
			}
			finally {
//				if (parser != null)
//					parser.close();
				if (writer != null)
					writer.close();
				if (outStream != null)
					outStream.close();
			}
		}
		catch (Exception exception) {
			System.out.println("EXCEPTION 2: " + exception);
		}
	}

	public String getStringOfByte(String str, Charset charset) {
		if (str == null || str.equals("")) {
			return "";
		}

		byte[] byteArray = str.getBytes(charset);
		int size = byteArray.length;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < size; i++) {
			byte temp = byteArray[i];
			buffer.append(getStringOfByte(temp));
		}

		return buffer.toString();
	}

	public String getStringOfByte(byte b) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 7; i >= 0; i--) {
			byte temp = (byte) ((b >> i) & 0x1);
			buffer.append(String.valueOf(temp));
		}

		return buffer.toString();
	}
		
	
	
	public static void main(String[] args) {
		new Huffman().run();
	}
}