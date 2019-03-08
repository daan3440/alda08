//// Ändra inte på paketet
//package alda.huffman;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.junit.*;
//
//public class HuffmanTest {
//	
//	private static String defaultString = "Just idag är jag stark";
//	private static String aString = "a";
//
//	private Huffman huff = new Huffman();
//	
//	void exampleString() {
//		byte[] encoded = aString.getBytes(StandardCharsets.UTF_8);
//		assertNotNull(encoded);
//		if(encoded != null) {
//			System.out.println("encoded not null " + encoded.toString());
//		}
//		String preEncode = new String(encoded, StandardCharsets.UTF_8);
//	Map<Character, Integer> frequency = huff.frequency(preEncode.toCharArray());
//	assertNotNull(frequency);
//	if(frequency != null) {
//		System.out.println("frequency not null " + frequency.toString());
//	}
//	String encodedBinString = huff.encode(preEncode, frequency);
//	
////	assertNotNull(encodedBinString);
//	if(encodedBinString != null) {
//		System.out.println("encodedBinString not null " + encodedBinString.toString());
//	}
//	}
//	
//	
//	
//	@Test
//	public void testEncode() {
//		String str = aString;
//		
//		
//	}
//	
//
//	@Test
//	public void readFileTest() throws IOException {
//		String path = "testFile.txt";
//		String readFile = huff.readFile(path, StandardCharsets.UTF_8);
//		
//			assertTrue(null, path!=null);	
//			assertEquals("123456abcdef", readFile.toString());	
//	}
//	@Test
//	public void testTest() {
//		int i = 4;
//		assertTrue(4 == i);	
//	}
//	
//	@Test
//	public void putBMapTest() {
//		String s = "aabaaa";
//		Map<Character, Integer> testMap = huff.frequency(s.toCharArray());
//			assertTrue(testMap.containsKey('b'));
//		}
//	
//	@Test
//	public void putAMapTest() {
//		String s = "aabaaa";
//		Map<Character, Integer> testMap = huff.frequency(s.toCharArray());
//		assertTrue(testMap.containsKey('a'));
//	}
//	
////	@Test
////	public void aBinaryTest() throws IOException{ 
//////	String preEncode = huff.readFile(aString, StandardCharsets.UTF_8);
////		byte[] encoded = aString.getBytes(StandardCharsets.UTF_8);
////		assertNotNull(encoded);
////		if(encoded != null) {
////			System.out.println("encoded not null " + encoded.toString());
////		}
////		String preEncode = new String(encoded, StandardCharsets.UTF_8);
////	Map<Character, Integer> frequency = huff.frequency(preEncode.toCharArray());
////	assertNotNull(frequency);
////	if(frequency != null) {
////		System.out.println("frequency not null " + frequency.toString());
////	}
////	String encodedBinString = huff.encode(preEncode, frequency);
////	
//////	assertNotNull(encodedBinString);
////	if(encodedBinString != null) {
////		System.out.println("encodedBinString not null " + encodedBinString.toString());
////	}
////	
////	assertEquals(1, encodedBinString);
////	}
////	
//	@Test
//	public void frequencyTest() {
//		String s = "aabaaa";
//		Map<Character, Integer> testMap = huff.frequency(s.toCharArray());
//			if(testMap.containsKey('a')) {
//		assertEquals(5, testMap.get('a').intValue());
//			}
//
//		
//	}
//
//}
