package org.json.simple.rope;

import junit.framework.TestCase;

public class RopeTest extends TestCase {
	public void testOpts(){
		Rope rope = new Rope();
		
		assertEquals("",rope.toString());
		assertEquals(0,rope.length());
		
		rope.append('c');
		assertEquals("c",rope.toString());
		assertEquals(1,rope.length());
		
		rope.append("fang");
		assertEquals("cfang",rope.toString());
		assertEquals(5,rope.length());
		
		rope.append('1');
		assertEquals("cfang1",rope.toString());
		assertEquals(6,rope.length());
		
		rope.append("你好");
		assertEquals("cfang1你好",rope.toString());
		assertEquals(8,rope.length());
		
		rope.append('世');
		assertEquals("cfang1你好世",rope.toString());
		assertEquals(9,rope.length());
		
		rope.append("界");
		assertEquals("cfang1你好世界",rope.toString());
		assertEquals(10,rope.length());
		
		rope.append("");
		assertEquals("cfang1你好世界",rope.toString());
		assertEquals(10,rope.length());
		
		rope.clear();
		assertEquals("",rope.toString());
		assertEquals(0,rope.length());
	}
}
