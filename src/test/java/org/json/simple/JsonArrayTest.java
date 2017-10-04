/* See: README for this file's copyright, terms, and conditions. */
package org.json.simple;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Ensures that JsonArray hasn't regressed in functionality or breaks its API contract. */
public class JsonArrayTest{
	private enum TestEnums{
		A,
		B;
	}

	private static enum TestStaticEnums{
		ONE,
		TWO;
	}

	/** Called before each Test Method. */
	@Before
	public void setUp(){
		/* All of the implemented tests use local variables in their own respective method. */
	}

	/** Called after each Test method. */
	@After
	public void tearDown(){
		/* All of the implemented tests use local variables in their own respective method. */
	}

	/** Ensures a homogeneous JsonArray can be viewed as another collection of a specific type. */
	@Test
	public void testAsCollection(){
		JsonArray json;
		LinkedList<Integer> parameterList;
		HashSet<Integer> parameterSet;
		json = new JsonArray();
		json.add(1);
		json.add(2);
		json.add(3);
		json.add(4);
		json.add(5);
		parameterList = new LinkedList<>();
		parameterSet = new HashSet<>();
		json.asCollection(parameterList);
		json.asCollection(parameterSet);
		Assert.assertTrue(parameterList.contains(1));
		Assert.assertTrue(parameterList.contains(2));
		Assert.assertTrue(parameterList.contains(3));
		Assert.assertTrue(parameterList.contains(4));
		Assert.assertTrue(parameterList.contains(5));
		Assert.assertTrue(parameterSet.contains(1));
		Assert.assertTrue(parameterSet.contains(2));
		Assert.assertTrue(parameterSet.contains(3));
		Assert.assertTrue(parameterSet.contains(4));
		Assert.assertTrue(parameterSet.contains(5));
	}

	/** Ensures another collection can be used to instantiate a JsonArray. */
	@Test
	public void testConstructor(){
		JsonArray json;
		LinkedList<Integer> parameterList;
		HashSet<Integer> parameterSet;
		parameterList = new LinkedList<>();
		parameterList.add(5);
		parameterList.add(10);
		parameterList.add(15);
		json = new JsonArray(parameterList);
		Assert.assertTrue(json.contains(5));
		Assert.assertTrue(json.contains(10));
		Assert.assertTrue(json.contains(15));
		parameterSet = new HashSet<>();
		parameterSet.add(20);
		parameterSet.add(25);
		parameterSet.add(30);
		json = new JsonArray(parameterSet);
		Assert.assertTrue(json.contains(20));
		Assert.assertTrue(json.contains(25));
		Assert.assertTrue(json.contains(30));
	}

	/** Ensures a BigDecimal can be gotten if there is a BigDecimal, Number, or String at the index. */
	@Test
	public void testGetBigDecimal(){
		final JsonArray json = new JsonArray();
		json.add(new BigDecimal("0"));
		json.add(new Double(0));
		json.add(new Float(0));
		json.add(new Long(0));
		json.add(new Integer(0));
		json.add(new Short((short)0));
		json.add(new Byte((byte)0));
		json.add(new String("0"));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(0));
		Assert.assertEquals(new BigDecimal("0.0"), json.getBigDecimal(1));
		Assert.assertEquals(new BigDecimal("0.0"), json.getBigDecimal(2));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(3));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(4));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(5));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(6));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(7));
	}

	/** Ensures a Collection can be returned from an index. */
	@Test
	public void testGetCollection(){
		final JsonArray json = new JsonArray();
		LinkedList<Integer> list;
		HashSet<Integer> set;
		JsonArray array;
		List<?> output0;
		Set<?> output1;
		JsonArray output2;
		list = new LinkedList<>();
		list.add(5);
		list.add(10);
		list.add(15);
		set = new HashSet<>();
		set.add(20);
		set.add(25);
		set.add(30);
		array = new JsonArray();
		array.add(35);
		array.add(40);
		array.add(45);
		json.add(list);
		json.add(set);
		json.add(array);
		output0 = json.getCollection(0);
		Assert.assertTrue(output0.contains(5));
		Assert.assertTrue(output0.contains(10));
		Assert.assertTrue(output0.contains(15));
		output1 = json.getCollection(1);
		Assert.assertTrue(output1.contains(20));
		Assert.assertTrue(output1.contains(25));
		Assert.assertTrue(output1.contains(30));
		output2 = json.getCollection(2);
		Assert.assertTrue(output2.contains(35));
		Assert.assertTrue(output2.contains(40));
		Assert.assertTrue(output2.contains(45));
	}

	/** Ensures enums can be returned from a String value at an index.
	 * @throws ClassNotFoundException if the test failed. */
	@SuppressWarnings("deprecation")
	@Test
	public void testGetEnumDeprecated() throws ClassNotFoundException{
		final JsonArray json = new JsonArray();
		json.add("org.json.simple.JsonArrayTest$TestStaticEnums.ONE");
		json.add("org.json.simple.JsonArrayTest$TestEnums.A");
		Assert.assertEquals(JsonArrayTest.TestStaticEnums.ONE, json.getEnum(0));
		Assert.assertEquals(JsonArrayTest.TestEnums.A, json.getEnum(1));
	}

	/** Ensure a map can be returned from an index. */
	@Test
	public void testGetMap(){
		final JsonArray json = new JsonArray();
		final LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
		final JsonObject object = new JsonObject();
		Map<?, ?> output0;
		JsonObject output1;
		map.put("key0", 0);
		map.put("key1", 1);
		map.put("key2", 2);
		object.put("key3", 3);
		object.put("key4", 4);
		object.put("key5", 5);
		json.add(map);
		json.add(object);
		output0 = json.<LinkedHashMap<Object, Object>> getMap(0);
		Assert.assertTrue(output0.containsKey("key0"));
		Assert.assertTrue(output0.containsKey("key1"));
		Assert.assertTrue(output0.containsKey("key2"));
		Assert.assertTrue(output0.containsValue(0));
		Assert.assertTrue(output0.containsValue(1));
		Assert.assertTrue(output0.containsValue(2));
		output1 = json.<JsonObject> getMap(1);
		Assert.assertTrue(output1.containsKey("key3"));
		Assert.assertTrue(output1.containsKey("key4"));
		Assert.assertTrue(output1.containsKey("key5"));
		Assert.assertTrue(output1.containsValue(3));
		Assert.assertTrue(output1.containsValue(4));
		Assert.assertTrue(output1.containsValue(5));
	}

	/** Ensures basic JSON values can be gotten. */
	@Test
	public void testOtherJsonGets(){
		final JsonArray json = new JsonArray();
		json.add("101");
		json.add(true);
		json.add(101);
		json.add(new BigDecimal("101"));
		json.add(null);
		/* Booleans are gotten from strings and booleans. */
		Assert.assertEquals(true, json.getBoolean(1));
		Assert.assertEquals(false, json.getBoolean(0));
		/* Numbers are gotten from strings. */
		Assert.assertEquals(new Byte((byte)101), json.getByte(0));
		Assert.assertEquals(new Short((short)101), json.getShort(0));
		Assert.assertEquals(new Integer(101), json.getInteger(0));
		Assert.assertEquals(new Long(101), json.getLong(0));
		Assert.assertEquals(new Float(101), json.getFloat(0));
		Assert.assertEquals(new Double(101), json.getDouble(0));
		/* Numbers are gotten from numbers. */
		Assert.assertEquals(new Byte((byte)101), json.getByte(2));
		Assert.assertEquals(new Short((short)101), json.getShort(2));
		Assert.assertEquals(new Integer(101), json.getInteger(2));
		Assert.assertEquals(new Long(101), json.getLong(2));
		Assert.assertEquals(new Float(101), json.getFloat(2));
		Assert.assertEquals(new Double(101), json.getDouble(2));
		Assert.assertEquals(new Byte((byte)101), json.getByte(3));
		Assert.assertEquals(new Short((short)101), json.getShort(3));
		Assert.assertEquals(new Integer(101), json.getInteger(3));
		Assert.assertEquals(new Long(101), json.getLong(3));
		Assert.assertEquals(new Float(101), json.getFloat(3));
		Assert.assertEquals(new Double(101), json.getDouble(3));
		/* Strings are gotten from booleans, numbers, and strings. */
		Assert.assertEquals("101", json.getString(0));
		Assert.assertEquals("true", json.getString(1));
		Assert.assertEquals("101", json.getString(2));
		Assert.assertEquals("101", json.getString(3));
		/* Gets return null if the value is null. */
		Assert.assertEquals(null, json.getString(4));
		Assert.assertEquals(null, json.getBigDecimal(4));
		Assert.assertEquals(null, json.getBoolean(4));
		Assert.assertEquals(null, json.getByte(4));
		Assert.assertEquals(null, json.getShort(4));
		Assert.assertEquals(null, json.getInteger(4));
		Assert.assertEquals(null, json.getLong(4));
		Assert.assertEquals(null, json.getFloat(4));
		Assert.assertEquals(null, json.getDouble(4));
		Assert.assertEquals(null, json.get(4));
	}
}
