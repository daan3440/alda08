package alda.huffman;
/*
 * ALDA08 - Algoritmdesigntekniker
 * Huffman
 * Daniel Andersson - daan3440
 *  
 */
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


import java.util.Map;


import org.junit.*;

public class HuffmanTest {
	private String inputFileName = "inFile.txt";
	private String inputFileNameAbcd = "abcd.txt";
	private String encodedOutputFileName = "encodedFile.txt";
	private String decodedOutputFileName= "decodedFile.txt";
	private String preEncode;

	private FileOutputStream outStream = null;
	private OutputStreamWriter writer = null;

	private String defaultString = "Just idag är jag stark";

	private Huffman huff = new Huffman();

	@Test
	public void testPath() throws IOException {
		assertEquals("abcd.txt", inputFileNameAbcd);
	}

	@Test
	public void getBinaryCodeOfByteTest() {
		String str = "a";
			byte[] byteArray = str.getBytes(Charset.forName("UTF-8"));
			int size = byteArray.length;
			StringBuffer returnBuffer = new StringBuffer();
			for (int i = 0; i < size; i++) {
				byte temp = byteArray[i];
				returnBuffer.append(getStringOfByte(temp));
			}
			assertEquals("01100001" , returnBuffer.toString());
		}
	@Test
	public void getBinaryCodeOfByteAATest() {
		String str = "ab";
		byte[] byteArray = str.getBytes(Charset.forName("UTF-8"));
		int size = byteArray.length;
		StringBuffer returnBuffer = new StringBuffer();
		for (int i = 0; i < size; i++) {
			byte temp = byteArray[i];
			returnBuffer.append(getStringOfByte(temp));
		}
		assertEquals("0110000101100010" , returnBuffer.toString());
	}
	@Test
	public void getStringFromBinaryCodeOfByteATest() {
		String str = "a";
		byte[] byteArray = str.getBytes(Charset.forName("UTF-8"));
		int size = byteArray.length;
		StringBuffer returnBuffer = new StringBuffer();
		for (int i = 0; i < size; i++) {
			byte temp = byteArray[i];
			returnBuffer.append(getStringOfByte(temp));
		}
		String outputString = "";
		 for(int index = 0; index < returnBuffer.length(); index+=8) {
		     String temp = returnBuffer.substring(index, index+8);
		     int num = Integer.parseInt(temp,2);
		     char letter = (char) num;
		     outputString +=letter;
		 }
		System.out.println("String from binary: " + outputString);
		assertEquals("a" , outputString);
	}
	@Test
	public void getStringFromBinaryCodeOfByteAATest() {
		String str = "aa";
		byte[] byteArray = str.getBytes(Charset.forName("UTF-8"));
		int size = byteArray.length;
		StringBuffer returnBuffer = new StringBuffer();
		for (int i = 0; i < size; i++) {
			byte temp = byteArray[i];
			returnBuffer.append(getStringOfByte(temp));
		}
		String outputString = "";
		for(int index = 0; index < returnBuffer.length(); index+=8) {
			String temp = returnBuffer.substring(index, index+8);
			int num = Integer.parseInt(temp,2);
			char letter = (char) num;
			outputString +=letter;
		}
		System.out.println("String from binary: " + outputString);
		assertEquals("aa" , outputString);
	}
	
	
	private String getStringOfByte(byte b) {
		StringBuffer returnBuffer = new StringBuffer();
		for (int i = 0b111; i >= 0; i--) {
			byte tmp = (byte) ((b >> i) & 0x1);
			returnBuffer.append(tmp);
		}
		System.out.println("returnBuffer: " + returnBuffer);
		return returnBuffer.toString();
	}
	
	@Test
	public void loadFileStringTest() throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(inputFileNameAbcd));
		String str = new String (encoded, StandardCharsets.UTF_8);
		System.out.println("StringFromByte: " + str);
		assertEquals("abcd, efgh", str);
	}
	@Test
	public void loadFileBytelengthTest() throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(inputFileNameAbcd));
		String str = new String (encoded, StandardCharsets.UTF_8);
		assertEquals(10, str.length());
	}

	@Test
	public void readFileTest() throws IOException {
		String path = "testFile.txt";
		preEncode= huff.readFile(path, StandardCharsets.UTF_8);

		assertTrue(null, path!=null);	
		assertEquals("123456abcdef", preEncode.toString());	
	}
	@Test
	public void encodedBinStringATest() throws IOException {
		String path = "a.txt";
		preEncode= huff.readFile(path, StandardCharsets.UTF_8);
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		System.out.println("Stats: " + stats);
		String encodedBinString = huff.encode(preEncode, stats);
		System.out.println("Encoded: " + encodedBinString);
		assertEquals("0", encodedBinString);	
	}
	@Test
	public void decodedBinStringATest() throws IOException {
		preEncode= "a";
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		String encodedBinString = "0";
		String decodedString = huff.decode(encodedBinString, stats);
		assertEquals("a", decodedString);	
	}
	@Test
	public void encodedBinStringAATest() throws IOException {
		preEncode = "aa";//new String("aa", StandardCharsets.UTF_8);
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		System.out.println("Stats: " + stats);
		String encodedBinString = huff.encode(preEncode, stats);
		System.out.println("Encoded: " + encodedBinString);
		assertEquals("00", encodedBinString);	
	}
	@Test
	public void decodedBinStringAATest() throws IOException {
		preEncode= "aa";
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		String encodedBinString = "00";
		String decodedString = huff.decode(encodedBinString, stats);
		System.out.println("Decoded: " + decodedString);
		assertEquals("aa", decodedString);	
	}

	@Test
	public void encodedBinStringTest() throws IOException {
		String path = "abcd.txt";
		preEncode= huff.readFile(path, StandardCharsets.UTF_8);
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		System.out.println("Stats: " + stats);
		String encodedBinString = huff.encode(preEncode, stats);
		System.out.println("Encoded: " + encodedBinString);
		assertEquals("0010111110101110111000101001111000", encodedBinString);	
	}
	@Test
	public void decodedBinStringTest() throws IOException {
		preEncode= "abcd, efgh";
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		String encodedBinString = "0010111110101110111000101001111000";
		String decodedString = huff.decode(encodedBinString, stats);
		System.out.println("Decoded string: " + decodedString);
		assertEquals("abcd, efgh", decodedString);	
	}
	@Test
	public void encodedBinStringBigFileTest() throws IOException {
		String path = "inFile.txt";
		preEncode= huff.readFile(path, StandardCharsets.UTF_8);
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		System.out.println("Stats: " + stats);
		String encodedBinString = huff.encode(preEncode, stats);
		assertTrue(preEncode.length() < encodedBinString.length());	
	}
	
	@Test
	public void decodedBinStringBigFileTest() throws IOException {
		String path = inputFileName;
		preEncode= huff.readFile(path, StandardCharsets.UTF_8);
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		String encodedBinString = huff.encode(preEncode, stats);
		String decodedString = huff.decode(encodedBinString, stats);
		assertEquals(preEncode, decodedString);	
		assertTrue(encodedBinString.length() > decodedString.length());	
	}
	@Test
	public void writeEncodedFileTest() throws IOException {
		String path = inputFileName;
		File pathFile = new File(path);
		preEncode= huff.readFile(path, StandardCharsets.UTF_8);
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		System.out.println("Stats: " + stats);
		String encodedBinString = huff.encode(preEncode, stats);
		File encodedFile = new File(encodedOutputFileName);
	        if(encodedFile.delete()){
	        }else {
	        	System.out.println(encodedOutputFileName +" doesn't exists in project root directory");
	        }
		outStream = new FileOutputStream(encodedOutputFileName);
		writer = new OutputStreamWriter(outStream);
		writer.write(encodedBinString);
		System.out.println(encodedOutputFileName +" written to Project root directory");
		writer.flush();
		writer.close();
		assertTrue(encodedFile.exists() && encodedFile.length() > pathFile.length());	
	}
	
	@Test
	public void writeDecodedFileTest() throws IOException {
		String path = inputFileName;
		File pathFile = new File(path);
		preEncode= huff.readFile(path, StandardCharsets.UTF_8);
		Map<Character, Integer> stats = huff.stats(preEncode.toCharArray());
		String encodedBinString = huff.encode(preEncode, stats);
		String decodedString = huff.decode(encodedBinString, stats);
		File decodedFile = new File(decodedOutputFileName);
		if(decodedFile.delete()){
			System.out.println(decodedOutputFileName +" deleted from Project root directory");
		}else {
			System.out.println(decodedOutputFileName +" doesn't exists in project root directory");
		}
		
		outStream = new FileOutputStream(decodedOutputFileName);
		writer = new OutputStreamWriter(outStream);
		writer.write(decodedString);
		System.out.println(decodedOutputFileName +" written to Project root directory");
		writer.flush();
		writer.close();
		
		assertTrue(decodedFile.exists() && decodedFile.length() == pathFile.length());	
	}

	@Test
	public void putBMapTest() {
		String s = "aabaaa";
		Map<Character, Integer> testMap = huff.stats(s.toCharArray());
		assertTrue(testMap.containsKey('b'));
	}

	@Test
	public void putAMapTest() {
		String s = "aabaaa";
		Map<Character, Integer> testMap = huff.stats(s.toCharArray());
		assertTrue(testMap.containsKey('a'));
	}

	@Test
	public void frequencyTest() {
		String s = "aabaaa";
		Map<Character, Integer> testMap = huff.stats(s.toCharArray());
		if(testMap.containsKey('a')) {
			assertEquals(5, testMap.get('a').intValue());
		}


	}

}
