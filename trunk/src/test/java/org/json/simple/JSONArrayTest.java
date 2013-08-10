package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import junit.framework.TestCase;

public class JSONArrayTest extends TestCase {

	public void testJSONArray() {
		final JSONArray jsonArray = new JSONArray();
		
		assertEquals("[]", jsonArray.toJSONString());
	}

	public void testJSONArrayCollection() {
		final ArrayList testList = new ArrayList();
		testList.add("First item");
		testList.add("Second item");
		
		final JSONArray jsonArray = new JSONArray(testList);
		
		assertEquals("[\"First item\",\"Second item\"]", jsonArray.toJSONString());
	}

	public void testWriteJSONStringCollectionWriter() throws IOException, ParseException {
		final HashSet testSet = new HashSet();
		testSet.add("First item");
		testSet.add("Second item");
		
		final JSONArray jsonArray = new JSONArray(testSet);
		final StringWriter writer = new StringWriter();
		
		jsonArray.writeJSONString(writer);
		
		final JSONParser parser = new JSONParser();
		final JSONArray parsedArray = (JSONArray)parser.parse(writer.toString());
		
		assertTrue(parsedArray.containsAll(jsonArray));
		assertTrue(jsonArray.containsAll(parsedArray));
		assertEquals(2, jsonArray.size());
	}

	public void testToJSONStringCollection() throws ParseException {
		final HashSet testSet = new HashSet();
		testSet.add("First item");
		testSet.add("Second item");
		
		final JSONArray jsonArray = new JSONArray(testSet);
		
		final JSONParser parser = new JSONParser();
		final JSONArray parsedArray = (JSONArray)parser.parse(jsonArray.toJSONString());
		
		assertTrue(parsedArray.containsAll(jsonArray));
		assertTrue(jsonArray.containsAll(parsedArray));
		assertEquals(2, jsonArray.size());
	}

}
