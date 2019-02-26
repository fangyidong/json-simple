package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import junit.framework.TestCase;
import org.junit.Ignore;

@Ignore
public class JSONArrayTest extends TestCase {

	public void testJSONArray() {
		final JSONArray jsonArray = new JSONArray();
		
		assertEquals("[]", jsonArray.toJSONString());
	}

	public void testJSONArrayCollection() {
		final JSONArray jsonArray = new JSONArray();
		jsonArray.add("First item");
		jsonArray.add("Second item");
		
		assertEquals("[\"First item\",\"Second item\"]", jsonArray.toJSONString());
	}

	public void testWriteJSONStringCollectionWriter() throws IOException, ParseException {
		final JSONArray jsonArray = new JSONArray();
		jsonArray.add("First item");
		jsonArray.add("Second item");
		
		final StringWriter writer = new StringWriter();
		
		jsonArray.writeJSONString(writer);
		
		final JSONParser parser = new JSONParser();
		final JSONArray parsedArray = (JSONArray)parser.parse(writer.toString());
		
		assertTrue(parsedArray.containsAll(jsonArray));
		assertTrue(jsonArray.containsAll(parsedArray));
		assertEquals(2, jsonArray.size());
	}

	public void testToJSONStringCollection() throws ParseException {
		final JSONArray jsonArray = new JSONArray();
		jsonArray.add("First item");
		jsonArray.add("Second item");
		
		final JSONParser parser = new JSONParser();
		final JSONArray parsedArray = (JSONArray)parser.parse(jsonArray.toJSONString());
		
		assertTrue(parsedArray.containsAll(jsonArray));
		assertTrue(jsonArray.containsAll(parsedArray));
		assertEquals(2, jsonArray.size());
	}

	public void testByteArrayToString() throws IOException {
		assertEquals("null", JSONArray.toJSONString(null));
		assertEquals("[]", JSONArray.toJSONString(Collections.emptyList()));
		assertEquals("[12]", JSONArray.toJSONString(Collections.singletonList((byte) 12)));
		assertEquals("[-7,22,86,-99]", JSONArray.toJSONString(Arrays.asList((byte) -7, (byte) 22, (byte) 86, (byte) -99)));
		
		StringWriter writer;
		
		writer = new StringWriter();
		JSONArray.writeJSONString(null, writer);
		assertEquals("null", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.emptyList(), writer);
		assertEquals("[]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.singletonList((byte) 12), writer);
		assertEquals("[12]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Arrays.asList((byte) -7, (byte) 22, (byte) 86, (byte) -99), writer);
		assertEquals("[-7,22,86,-99]", writer.toString());
	}
	
	public void testShortArrayToString() throws IOException {
		assertEquals("null", JSONArray.toJSONString(null));
		assertEquals("[]", JSONArray.toJSONString(Collections.emptyList()));
		assertEquals("[12]", JSONArray.toJSONString(Collections.singletonList((short) 12)));
		assertEquals("[-7,22,86,-99]", JSONArray.toJSONString(Arrays.asList((short) -7, (short) 22, (short) 86, (short) -99)));
		
		StringWriter writer;
		
		writer = new StringWriter();
		JSONArray.writeJSONString(null, writer);
		assertEquals("null", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.emptyList(), writer);
		assertEquals("[]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.singletonList((short) 12), writer);
		assertEquals("[12]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Arrays.asList((short) -7, (short) 22, (short) 86, (short) -99), writer);
		assertEquals("[-7,22,86,-99]", writer.toString());
	}
	
	public void testIntArrayToString() throws IOException {
		assertEquals("null", JSONArray.toJSONString(null));
		assertEquals("[]", JSONArray.toJSONString(Collections.emptyList()));
		assertEquals("[12]", JSONArray.toJSONString(Collections.singletonList(12)));
		assertEquals("[-7,22,86,-99]", JSONArray.toJSONString(Arrays.asList(-7, 22, 86, -99)));
		
		StringWriter writer;
		
		writer = new StringWriter();
		JSONArray.writeJSONString(null, writer);
		assertEquals("null", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.emptyList(), writer);
		assertEquals("[]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.singletonList(12), writer);
		assertEquals("[12]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Arrays.asList(-7, 22, 86, -99), writer);
		assertEquals("[-7,22,86,-99]", writer.toString());
	}
	
	public void testLongArrayToString() throws IOException {
		assertEquals("null", JSONArray.toJSONString(null));
		assertEquals("[]", JSONArray.toJSONString(Collections.emptyList()));
		assertEquals("[12]", JSONArray.toJSONString(Collections.singletonList(12L)));
		assertEquals("[-7,22,9223372036854775807,-99]", JSONArray.toJSONString(Arrays.asList(-7L, 22L, 9223372036854775807L, -99L)));
		
		StringWriter writer;
		
		writer = new StringWriter();
		JSONArray.writeJSONString(null, writer);
		assertEquals("null", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.emptyList(), writer);
		assertEquals("[]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.singletonList(12L), writer);
		assertEquals("[12]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Arrays.asList(-7L, 22L, 86L, -99L), writer);
		assertEquals("[-7,22,86,-99]", writer.toString());
	}
	
	public void testFloatArrayToString() throws IOException {
		assertEquals("null", JSONArray.toJSONString(null));
		assertEquals("[]", JSONArray.toJSONString(Collections.emptyList()));
		assertEquals("[12.8]", JSONArray.toJSONString(Collections.singletonList(12.8f)));
		assertEquals("[-7.1,22.234,86.7,-99.02]", JSONArray.toJSONString(Arrays.asList(-7.1f, 22.234f, 86.7f, -99.02f)));
		
		StringWriter writer;
		
		writer = new StringWriter();
		JSONArray.writeJSONString(null, writer);
		assertEquals("null", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.emptyList(), writer);
		assertEquals("[]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.singletonList(12.8f), writer);
		assertEquals("[12.8]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Arrays.asList(-7.1f, 22.234f, 86.7f, -99.02f), writer);
		assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
	}
	
	public void testDoubleArrayToString() throws IOException {
		assertEquals("null", JSONArray.toJSONString(null));
		assertEquals("[]", JSONArray.toJSONString(Collections.emptyList()));
		assertEquals("[12.8]", JSONArray.toJSONString(Collections.singletonList(12.8)));
		assertEquals("[-7.1,22.234,86.7,-99.02]", JSONArray.toJSONString(Arrays.asList(-7.1, 22.234, 86.7, -99.02)));
		
		StringWriter writer;
		
		writer = new StringWriter();
		JSONArray.writeJSONString(null, writer);
		assertEquals("null", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.emptyList(), writer);
		assertEquals("[]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.singletonList(12.8), writer);
		assertEquals("[12.8]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Arrays.asList(-7.1, 22.234, 86.7, -99.02), writer);
		assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
	}
	
	public void testBooleanArrayToString() throws IOException {
		assertEquals("null", JSONArray.toJSONString(null));
		assertEquals("[]", JSONArray.toJSONString(Collections.emptyList()));
		assertEquals("[true]", JSONArray.toJSONString(Collections.singletonList(true)));
		assertEquals("[true,false,true]", JSONArray.toJSONString(Arrays.asList(true, false, true)));
		
		StringWriter writer;
		
		writer = new StringWriter();
		JSONArray.writeJSONString(null, writer);
		assertEquals("null", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.emptyList(), writer);
		assertEquals("[]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.singletonList(true), writer);
		assertEquals("[true]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Arrays.asList(true, false, true), writer);
		assertEquals("[true,false,true]", writer.toString());
	}
	
	public void testCharArrayToString() throws IOException {
		assertEquals("null", JSONArray.toJSONString(null));
		assertEquals("[]", JSONArray.toJSONString(Collections.emptyList()));
		assertEquals("[\"a\"]", JSONArray.toJSONString(Collections.singletonList('a')));
		assertEquals("[\"a\",\"b\",\"c\"]", JSONArray.toJSONString(Arrays.asList('a', 'b', 'c')));
		
		StringWriter writer;
		
		writer = new StringWriter();
		JSONArray.writeJSONString(null, writer);
		assertEquals("null", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.emptyList(), writer);
		assertEquals("[]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.singletonList('a'), writer);
		assertEquals("[\"a\"]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Arrays.asList('a', 'b', 'c'), writer);
		assertEquals("[\"a\",\"b\",\"c\"]", writer.toString());
	}
	
	public void testObjectArrayToString() throws IOException {
		assertEquals("null", JSONArray.toJSONString(null));
		assertEquals("[]", JSONArray.toJSONString(Collections.emptyList()));
		assertEquals("[\"Hello\"]", JSONArray.toJSONString(Collections.singletonList("Hello")));
		assertEquals("[\"Hello\",12,[1,2,3]]", JSONArray.toJSONString(Arrays.asList("Hello", 12, new int[] { 1, 2, 3 })));
		
		StringWriter writer;
		
		writer = new StringWriter();
		JSONArray.writeJSONString(null, writer);
		assertEquals("null", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.emptyList(), writer);
		assertEquals("[]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Collections.singletonList("Hello"), writer);
		assertEquals("[\"Hello\"]", writer.toString());
		
		writer = new StringWriter();
		JSONArray.writeJSONString(Arrays.asList("Hello", 12, new int[] { 1, 2, 3}), writer);
		assertEquals("[\"Hello\",12,[1,2,3]]", writer.toString());
	}
}
