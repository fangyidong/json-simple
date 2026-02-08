package org.json.simple;

import junit.framework.TestCase;

public class ItemListTest extends TestCase {

	public void testTwoArgConstructorUsesProvidedSeparator() {
		ItemList itemList = new ItemList("a:b:c", ":");
		
		assertEquals(3, itemList.size());
		assertEquals("a:b:c", itemList.toString());
		assertEquals("a", itemList.get(0));
		assertEquals("b", itemList.get(1));
		assertEquals("c", itemList.get(2));
	}
	
	public void testThreeArgConstructorUsesProvidedSeparator() {
		ItemList itemList = new ItemList("a:b:c", ":", false);
		
		assertEquals(3, itemList.size());
		assertEquals("a:b:c", itemList.toString());
	}
	
	public void testGetArrayReturnsTypedStringArray() {
		ItemList itemList = new ItemList();
		itemList.add("first");
		itemList.add("second");
		
		String[] result = itemList.getArray();
		
		assertEquals(2, result.length);
		assertEquals("first", result[0]);
		assertEquals("second", result[1]);
	}
}
