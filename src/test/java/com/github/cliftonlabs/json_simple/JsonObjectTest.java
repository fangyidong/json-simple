/* See: README for this file's copyright, terms, and conditions. */
package com.github.cliftonlabs.json_simple;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Ensures that JsonObject hasn't regressed in functionality or breaks its API contract. */
public class JsonObjectTest{
	@SuppressWarnings("javadoc")
	private enum TestEnums{
		A,
		B;
	}

	@SuppressWarnings("javadoc")
	private static enum TestKeys implements JsonKey{
		DNE(null),
		DNE_BIG_DECIMAL(new BigDecimal("101")),
		DNE_COLLECTION(new JsonArray()),
		DNE_ENUM(JsonObjectTest.TestEnums.A),
		DNE_MAP(new JsonObject()),
		DNE2(null),
		key0(null),
		key1(null),
		key2(null),
		key3(null),
		key4(null),
		key5(null),
		key6(null),
		key7(null),
		key8(null);
		private final Object value;

		private TestKeys(final Object value){
			this.value = value;
		}

		@Override
		public String getKey(){
			return this.name();
		}

		@Override
		public Object getValue(){
			return this.value;
		}
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

	/** Ensures another Map can be used to instantiate a JsonObject. */
	@Test
	public void testConstructor(){
		JsonObject json;
		LinkedHashMap<String, Integer> parameter;
		parameter = new LinkedHashMap<>();
		parameter.put("key0", 5);
		parameter.put("key1", 10);
		parameter.put("key2", 15);
		json = new JsonObject(parameter);
		Assert.assertTrue(json.containsKey("key0"));
		Assert.assertTrue(json.containsKey("key1"));
		Assert.assertTrue(json.containsKey("key2"));
		Assert.assertTrue(json.containsValue(5));
		Assert.assertTrue(json.containsValue(10));
		Assert.assertTrue(json.containsValue(15));
	}

	/** Ensures a BigDecimal can be gotten if there is a BigDecimal, Number, or String at the key. */
	@Test
	public void testGetBigDecimal(){
		final JsonObject json = new JsonObject();
		json.put(TestKeys.key0.getKey(), new BigDecimal("0"));
		json.put(TestKeys.key1.getKey(), Double.valueOf(0));
		json.put(TestKeys.key2.getKey(), Float.valueOf(0));
		json.put(TestKeys.key3.getKey(), Long.valueOf(0));
		json.put(TestKeys.key4.getKey(), Integer.valueOf(0));
		json.put(TestKeys.key5.getKey(), Short.valueOf((short)0));
		json.put(TestKeys.key6.getKey(), Byte.valueOf((byte)0));
		json.put(TestKeys.key7.getKey(), new String("0"));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(TestKeys.key0));
		Assert.assertEquals(new BigDecimal("0.0"), json.getBigDecimal(TestKeys.key1));
		Assert.assertEquals(new BigDecimal("0.0"), json.getBigDecimal(TestKeys.key2));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(TestKeys.key3));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(TestKeys.key4));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(TestKeys.key5));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(TestKeys.key6));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal(TestKeys.key7));
		Assert.assertEquals(new BigDecimal("101"), json.getBigDecimalOrDefault(TestKeys.DNE_BIG_DECIMAL));
	}

	/** Ensures a Collection can be returned from a key. */
	@Test
	public void testGetCollection(){
		final JsonObject json = new JsonObject();
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
		json.put(TestKeys.key0.getKey(), list);
		json.put(TestKeys.key1.getKey(), set);
		json.put(TestKeys.key2.getKey(), array);
		output0 = json.getCollection(TestKeys.key0);
		Assert.assertTrue(output0.contains(5));
		Assert.assertTrue(output0.contains(10));
		Assert.assertTrue(output0.contains(15));
		output1 = json.getCollection(TestKeys.key1);
		Assert.assertTrue(output1.contains(20));
		Assert.assertTrue(output1.contains(25));
		Assert.assertTrue(output1.contains(30));
		output2 = json.getCollection(TestKeys.key2);
		Assert.assertTrue(output2.contains(35));
		Assert.assertTrue(output2.contains(40));
		Assert.assertTrue(output2.contains(45));
		Assert.assertEquals(new JsonArray(), json.getCollectionOrDefault(TestKeys.DNE_COLLECTION));
	}

	/** Ensure a map can be returned from a key. */
	@Test
	public void testGetMap(){
		final JsonObject json = new JsonObject();
		final LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
		final JsonObject object = new JsonObject();
		Map<?, ?> output0;
		JsonObject output1;
		map.put(TestKeys.key0.getKey(), 0);
		map.put(TestKeys.key1.getKey(), 1);
		map.put(TestKeys.key2.getKey(), 2);
		object.put(TestKeys.key3.getKey(), 3);
		object.put(TestKeys.key4.getKey(), 4);
		object.put(TestKeys.key5.getKey(), 5);
		json.put(TestKeys.key6.getKey(), map);
		json.put(TestKeys.key7.getKey(), object);
		output0 = json.<LinkedHashMap<Object, Object>> getMap(TestKeys.key6);
		Assert.assertTrue(output0.containsKey(TestKeys.key0.getKey()));
		Assert.assertTrue(output0.containsKey(TestKeys.key1.getKey()));
		Assert.assertTrue(output0.containsKey(TestKeys.key2.getKey()));
		Assert.assertTrue(output0.containsValue(0));
		Assert.assertTrue(output0.containsValue(1));
		Assert.assertTrue(output0.containsValue(2));
		output1 = json.<JsonObject> getMap(TestKeys.key7);
		Assert.assertTrue(output1.containsKey(TestKeys.key3.getKey()));
		Assert.assertTrue(output1.containsKey(TestKeys.key4.getKey()));
		Assert.assertTrue(output1.containsKey(TestKeys.key5.getKey()));
		Assert.assertTrue(output1.containsValue(3));
		Assert.assertTrue(output1.containsValue(4));
		Assert.assertTrue(output1.containsValue(5));
		Assert.assertEquals(new JsonObject(), json.getMapOrDefault(TestKeys.DNE_MAP));
	}

	/** Ensures basic JSON values can be gotten. */
	@Test
	public void testOtherJsonGets(){
		final JsonObject json = new JsonObject();
		/* Key0 -> string
		 * key1 -> boolean
		 * key2 -> number
		 * key3 -> big decimal
		 * key4 -> null
		 * TestKeys need to swap values once in a while. */
		json.put(TestKeys.key0.getKey(), "101");
		json.put(TestKeys.key1.getKey(), true);
		json.put(TestKeys.key2.getKey(), 101);
		json.put(TestKeys.key3.getKey(), new BigDecimal("101"));
		json.put(TestKeys.key4.getKey(), null);
		/* Booleans are gotten from strings and booleans. */
		Assert.assertEquals(true, json.getBoolean(TestKeys.key1));
		Assert.assertEquals(false, json.getBoolean(TestKeys.key0));
		Assert.assertEquals(true, json.getBooleanOrDefault(Jsoner.mintJsonKey("key1", false)));
		Assert.assertEquals(false, json.getBooleanOrDefault(Jsoner.mintJsonKey("key0", true)));
		/* Numbers are gotten from strings. */
		Assert.assertEquals(Byte.valueOf((byte)101), json.getByte(TestKeys.key0));
		Assert.assertEquals(Short.valueOf((short)101), json.getShort(TestKeys.key0));
		Assert.assertEquals(Integer.valueOf(101), json.getInteger(TestKeys.key0));
		Assert.assertEquals(Long.valueOf(101), json.getLong(TestKeys.key0));
		Assert.assertEquals(Float.valueOf(101), json.getFloat(TestKeys.key0));
		Assert.assertEquals(Double.valueOf(101), json.getDouble(TestKeys.key0));
		Assert.assertEquals(Byte.valueOf((byte)101), json.getByteOrDefault(Jsoner.mintJsonKey("key0", (byte)0)));
		Assert.assertEquals(Short.valueOf((short)101), json.getShortOrDefault(Jsoner.mintJsonKey("key0", (short)0)));
		Assert.assertEquals(Integer.valueOf(101), json.getIntegerOrDefault(Jsoner.mintJsonKey("key0", 0)));
		Assert.assertEquals(Long.valueOf(101), json.getLongOrDefault(Jsoner.mintJsonKey("key0", 0)));
		Assert.assertEquals(Float.valueOf(101), json.getFloatOrDefault(Jsoner.mintJsonKey("key0", 0)));
		Assert.assertEquals(Double.valueOf(101), json.getDoubleOrDefault(Jsoner.mintJsonKey("key0", 0)));
		/* Numbers are gotten from numbers. */
		Assert.assertEquals(Byte.valueOf((byte)101), json.getByte(TestKeys.key2));
		Assert.assertEquals(Short.valueOf((short)101), json.getShort(TestKeys.key2));
		Assert.assertEquals(Integer.valueOf(101), json.getInteger(TestKeys.key2));
		Assert.assertEquals(Long.valueOf(101), json.getLong(TestKeys.key2));
		Assert.assertEquals(Float.valueOf(101), json.getFloat(TestKeys.key2));
		Assert.assertEquals(Double.valueOf(101), json.getDouble(TestKeys.key2));
		Assert.assertEquals(Byte.valueOf((byte)101), json.getByteOrDefault(Jsoner.mintJsonKey("key2", (byte)0)));
		Assert.assertEquals(Short.valueOf((short)101), json.getShortOrDefault(Jsoner.mintJsonKey("key2", (short)0)));
		Assert.assertEquals(Integer.valueOf(101), json.getIntegerOrDefault(Jsoner.mintJsonKey("key2", 0)));
		Assert.assertEquals(Long.valueOf(101), json.getLongOrDefault(Jsoner.mintJsonKey("key2", 0)));
		Assert.assertEquals(Float.valueOf(101), json.getFloatOrDefault(Jsoner.mintJsonKey("key2", 0)));
		Assert.assertEquals(Double.valueOf(101), json.getDoubleOrDefault(Jsoner.mintJsonKey("key2", 0)));
		Assert.assertEquals(Byte.valueOf((byte)101), json.getByte(TestKeys.key3));
		Assert.assertEquals(Short.valueOf((short)101), json.getShort(TestKeys.key3));
		Assert.assertEquals(Integer.valueOf(101), json.getInteger(TestKeys.key3));
		Assert.assertEquals(Long.valueOf(101), json.getLong(TestKeys.key3));
		Assert.assertEquals(Float.valueOf(101), json.getFloat(TestKeys.key3));
		Assert.assertEquals(Double.valueOf(101), json.getDouble(TestKeys.key3));
		Assert.assertEquals(Byte.valueOf((byte)101), json.getByteOrDefault(Jsoner.mintJsonKey("key3", (byte)0)));
		Assert.assertEquals(Short.valueOf((short)101), json.getShortOrDefault(Jsoner.mintJsonKey("key3", (short)0)));
		Assert.assertEquals(Integer.valueOf(101), json.getIntegerOrDefault(Jsoner.mintJsonKey("key3", 0)));
		Assert.assertEquals(Long.valueOf(101), json.getLongOrDefault(Jsoner.mintJsonKey("key3", 0)));
		Assert.assertEquals(Float.valueOf(101), json.getFloatOrDefault(Jsoner.mintJsonKey("key3", 0)));
		Assert.assertEquals(Double.valueOf(101), json.getDoubleOrDefault(Jsoner.mintJsonKey("key3", 0)));
		/* Strings are gotten from booleans, numbers, and strings. */
		Assert.assertEquals("101", json.getString(TestKeys.key0));
		Assert.assertEquals("true", json.getString(TestKeys.key1));
		Assert.assertEquals("101", json.getString(TestKeys.key2));
		Assert.assertEquals("101", json.getString(TestKeys.key3));
		Assert.assertEquals("101", json.getStringOrDefault(Jsoner.mintJsonKey("key0", "failed")));
		Assert.assertEquals("true", json.getStringOrDefault(Jsoner.mintJsonKey("key1", "failed")));
		Assert.assertEquals("101", json.getStringOrDefault(Jsoner.mintJsonKey("key2", "failed")));
		Assert.assertEquals("101", json.getStringOrDefault(Jsoner.mintJsonKey("key3", "failed")));
		/* Gets return null if the value is null. */
		Assert.assertEquals(null, json.getStringOrDefault(Jsoner.mintJsonKey("key4", "")));
		Assert.assertEquals(null, json.getBigDecimalOrDefault(Jsoner.mintJsonKey("key4", new BigDecimal(0))));
		Assert.assertEquals(null, json.getBooleanOrDefault(Jsoner.mintJsonKey("key4", true)));
		Assert.assertEquals(null, json.getByteOrDefault(Jsoner.mintJsonKey("key4", (byte)0)));
		Assert.assertEquals(null, json.getShortOrDefault(Jsoner.mintJsonKey("key4", (short)0)));
		Assert.assertEquals(null, json.getIntegerOrDefault(Jsoner.mintJsonKey("key4", 0)));
		Assert.assertEquals(null, json.getLongOrDefault(Jsoner.mintJsonKey("key4", 0L)));
		Assert.assertEquals(null, json.getFloatOrDefault(Jsoner.mintJsonKey("key4", 0)));
		Assert.assertEquals(null, json.getDoubleOrDefault(Jsoner.mintJsonKey("key4", 0)));
		Assert.assertEquals(null, json.getString(TestKeys.key4));
		Assert.assertEquals(null, json.getBigDecimal(TestKeys.key4));
		Assert.assertEquals(null, json.getBoolean(TestKeys.key4));
		Assert.assertEquals(null, json.getByte(TestKeys.key4));
		Assert.assertEquals(null, json.getShort(TestKeys.key4));
		Assert.assertEquals(null, json.getInteger(TestKeys.key4));
		Assert.assertEquals(null, json.getLong(TestKeys.key4));
		Assert.assertEquals(null, json.getFloat(TestKeys.key4));
		Assert.assertEquals(null, json.getDouble(TestKeys.key4));
		Assert.assertEquals(null, json.get(TestKeys.key4));
	}

	/** Ensures the chain methods put entries as expected. */
	@Test
	public void testPutChains(){
		final Map<String, Object> testAll = new HashMap<>();
		testAll.put("field1", "value1");
		testAll.put("field2", 2);
		testAll.put("field3", "three");
		final JsonObject json = new JsonObject();
		final JsonObject chained = new JsonObject().putChain("field4", "4four").putAllChain(testAll);
		json.put("field4", "4four");
		json.putAllChain(testAll);
		Assert.assertEquals(json, chained);
	}

	/** Ensures that when keys are present it does not throw NoSuchElementException. */
	@Test
	public void testRequires(){
		final JsonObject json = new JsonObject();
		json.put(TestKeys.key0.getKey(), 0);
		json.put(TestKeys.key1.getKey(), 0);
		json.put(TestKeys.key2.getKey(), 0);
		json.requireKeys(TestKeys.key0, TestKeys.key1);
	}

	/** Ensures that when required keys are not present the NoSuchElementException is thrown. */
	@Test(expected = NoSuchElementException.class)
	public void testRequiresThrows(){
		final JsonObject json = new JsonObject();
		json.requireKeys(TestKeys.DNE, TestKeys.DNE2);
	}
}
