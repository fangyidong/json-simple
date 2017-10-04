/* See: README for this file's copyright, terms, and conditions. */
package org.json.simple;

import java.math.BigDecimal;
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
	private enum TestEnums{
		A,
		B;
	}

	private static enum TestStaticEnums{
		ONE,
		TWO;
	}
	
	private static enum TestKeys implements JsonKey{
		key0(null),
		key1(null),
		key2(null),
		key3(null),
		key4(null),
		key5(null),
		key6(null),
		key7(null),
		key8(null),
		DNE(null),
		DNE2(null),
		DNE_BIG_DECIMAL(new BigDecimal("101")),
		DNE_COLLECTION(new JsonArray()),
		DNE_ENUM(JsonObjectTest.TestEnums.A),
		DNE_MAP(new JsonObject());
		private final Object value;
		private TestKeys(Object value) {
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
	/** Ensures that when required keys are not present the NoSuchElementException is thrown. */
	@Test(expected = NoSuchElementException.class)
	public void testRequiresThrows() {
		JsonObject json = new JsonObject();
		json.requireKeys(TestKeys.DNE, TestKeys.DNE2);
	}
	
	/** Ensures that when keys are present it does not throw NoSuchElementException. */
	public void testRequires() {
		JsonObject json = new JsonObject();
		json.put(TestKeys.key0.getKey(), 0);
		json.put(TestKeys.key1.getKey(), 0);
		json.put(TestKeys.key2.getKey(), 0);
		json.requireKeys(TestKeys.key0, TestKeys.key1);
	}

	/** Ensures a BigDecimal can be gotten if there is a BigDecimal, Number, or String at the key. */
	@SuppressWarnings("deprecation")
	@Test
	public void testGetBigDecimalDeprecated(){
		final JsonObject json = new JsonObject();
		final BigDecimal defaultValue = new BigDecimal("101");
		json.put("big", new BigDecimal("0"));
		json.put("double", new Double(0));
		json.put("float", new Float(0));
		json.put("long", new Long(0));
		json.put("int", new Integer(0));
		json.put("short", new Short((short)0));
		json.put("byte", new Byte((byte)0));
		json.put("string", new String("0"));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal("big"));
		Assert.assertEquals(new BigDecimal("0.0"), json.getBigDecimal("double"));
		Assert.assertEquals(new BigDecimal("0.0"), json.getBigDecimal("float"));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal("long"));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal("int"));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal("short"));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal("byte"));
		Assert.assertEquals(new BigDecimal("0"), json.getBigDecimal("string"));
		Assert.assertEquals(new BigDecimal("101"), json.getBigDecimalOrDefault("doesnotexist", defaultValue));
	}
	
	/** Ensures a BigDecimal can be gotten if there is a BigDecimal, Number, or String at the key. */
	@Test
	public void testGetBigDecimal(){
		final JsonObject json = new JsonObject();
		json.put(TestKeys.key0.getKey(), new BigDecimal("0"));
		json.put(TestKeys.key1.getKey(), new Double(0));
		json.put(TestKeys.key2.getKey(), new Float(0));
		json.put(TestKeys.key3.getKey(), new Long(0));
		json.put(TestKeys.key4.getKey(), new Integer(0));
		json.put(TestKeys.key5.getKey(), new Short((short)0));
		json.put(TestKeys.key6.getKey(), new Byte((byte)0));
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
	@SuppressWarnings("deprecation")
	@Test
	public void testGetCollectionOrDefaultDeprecated(){
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
		json.put("list", list);
		json.put("set", set);
		json.put("array", array);
		output0 = json.getCollectionOrDefault("list", new LinkedList<Integer>());
		Assert.assertTrue(output0.contains(5));
		Assert.assertTrue(output0.contains(10));
		Assert.assertTrue(output0.contains(15));
		output1 = json.getCollectionOrDefault("set", new HashSet<Integer>());
		Assert.assertTrue(output1.contains(20));
		Assert.assertTrue(output1.contains(25));
		Assert.assertTrue(output1.contains(30));
		output2 = json.getCollectionOrDefault("array", new JsonArray());
		Assert.assertTrue(output2.contains(35));
		Assert.assertTrue(output2.contains(40));
		Assert.assertTrue(output2.contains(45));
		Assert.assertEquals(new JsonArray(), json.getCollectionOrDefault("doesnotexist", new JsonArray()));
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

	/** Ensures enums can be returned from a String value at an index.
	 * @throws ClassNotFoundException if the test failed. */
	@SuppressWarnings("deprecation")
	@Test
	public void testGetEnumDeprecated() throws ClassNotFoundException{
		final JsonObject json = new JsonObject();
		json.put(TestKeys.key0.getKey(), "org.json.simple.JsonObjectTest$TestStaticEnums.ONE");
		json.put(TestKeys.key1.getKey(), "org.json.simple.JsonObjectTest$TestEnums.A");
		Assert.assertEquals(JsonObjectTest.TestStaticEnums.ONE, json.getEnum("key0"));
		Assert.assertEquals(JsonObjectTest.TestEnums.A, json.getEnum("key1"));
		Assert.assertEquals(JsonObjectTest.TestEnums.A, json.getEnumOrDefault("doesnotexist", JsonObjectTest.TestEnums.A));
		/* Same tests with JsonKey. */
		Assert.assertEquals(JsonObjectTest.TestStaticEnums.ONE, json.getEnum(TestKeys.key0));
		Assert.assertEquals(JsonObjectTest.TestEnums.A, json.getEnum(TestKeys.key1));
		Assert.assertEquals(JsonObjectTest.TestEnums.A, json.getEnumOrDefault(TestKeys.DNE_ENUM));
	}

	/** Ensure a map can be returned from a key. */
	@SuppressWarnings("deprecation")
	@Test
	public void testGetMapDeprecated(){
		final JsonObject json = new JsonObject();
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
		json.put("map", map);
		json.put("object", object);
		output0 = json.<LinkedHashMap<Object, Object>> getMap("map");
		Assert.assertTrue(output0.containsKey("key0"));
		Assert.assertTrue(output0.containsKey("key1"));
		Assert.assertTrue(output0.containsKey("key2"));
		Assert.assertTrue(output0.containsValue(0));
		Assert.assertTrue(output0.containsValue(1));
		Assert.assertTrue(output0.containsValue(2));
		output1 = json.<JsonObject> getMap("object");
		Assert.assertTrue(output1.containsKey("key3"));
		Assert.assertTrue(output1.containsKey("key4"));
		Assert.assertTrue(output1.containsKey("key5"));
		Assert.assertTrue(output1.containsValue(3));
		Assert.assertTrue(output1.containsValue(4));
		Assert.assertTrue(output1.containsValue(5));
		Assert.assertEquals(new JsonObject(), json.getMapOrDefault("doesnotexist", new JsonObject()));
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
	@SuppressWarnings("deprecation")
	@Test
	public void testOtherJsonGetsDeprecated(){
		final JsonObject json = new JsonObject();
		json.put("string", "101");
		json.put("boolean", true);
		json.put("number", 101);
		json.put("big", new BigDecimal("101"));
		json.put("null", null);
		/* Booleans are gotten from strings and booleans. */
		Assert.assertEquals(true, json.getBoolean("boolean"));
		Assert.assertEquals(false, json.getBoolean("string"));
		Assert.assertEquals(true, json.getBooleanOrDefault("boolean", false));
		Assert.assertEquals(false, json.getBooleanOrDefault("string", true));
		/* Numbers are gotten from strings. */
		Assert.assertEquals(new Byte((byte)101), json.getByte("string"));
		Assert.assertEquals(new Short((short)101), json.getShort("string"));
		Assert.assertEquals(new Integer(101), json.getInteger("string"));
		Assert.assertEquals(new Long(101), json.getLong("string"));
		Assert.assertEquals(new Float(101), json.getFloat("string"));
		Assert.assertEquals(new Double(101), json.getDouble("string"));
		Assert.assertEquals(new Byte((byte)101), json.getByteOrDefault("string", (byte)0));
		Assert.assertEquals(new Short((short)101), json.getShortOrDefault("string", (short)0));
		Assert.assertEquals(new Integer(101), json.getIntegerOrDefault("string", 0));
		Assert.assertEquals(new Long(101), json.getLongOrDefault("string", 0));
		Assert.assertEquals(new Float(101), json.getFloatOrDefault("string", 0));
		Assert.assertEquals(new Double(101), json.getDoubleOrDefault("string", 0));
		/* Numbers are gotten from numbers. */
		Assert.assertEquals(new Byte((byte)101), json.getByte("number"));
		Assert.assertEquals(new Short((short)101), json.getShort("number"));
		Assert.assertEquals(new Integer(101), json.getInteger("number"));
		Assert.assertEquals(new Long(101), json.getLong("number"));
		Assert.assertEquals(new Float(101), json.getFloat("number"));
		Assert.assertEquals(new Double(101), json.getDouble("number"));
		Assert.assertEquals(new Byte((byte)101), json.getByteOrDefault("number", (byte)0));
		Assert.assertEquals(new Short((short)101), json.getShortOrDefault("number", (short)0));
		Assert.assertEquals(new Integer(101), json.getIntegerOrDefault("number", 0));
		Assert.assertEquals(new Long(101), json.getLongOrDefault("number", 0));
		Assert.assertEquals(new Float(101), json.getFloatOrDefault("number", 0));
		Assert.assertEquals(new Double(101), json.getDoubleOrDefault("number", 0));
		Assert.assertEquals(new Byte((byte)101), json.getByte("big"));
		Assert.assertEquals(new Short((short)101), json.getShort("big"));
		Assert.assertEquals(new Integer(101), json.getInteger("big"));
		Assert.assertEquals(new Long(101), json.getLong("big"));
		Assert.assertEquals(new Float(101), json.getFloat("big"));
		Assert.assertEquals(new Double(101), json.getDouble("big"));
		Assert.assertEquals(new Byte((byte)101), json.getByteOrDefault("big", (byte)0));
		Assert.assertEquals(new Short((short)101), json.getShortOrDefault("big", (short)0));
		Assert.assertEquals(new Integer(101), json.getIntegerOrDefault("big", 0));
		Assert.assertEquals(new Long(101), json.getLongOrDefault("big", 0));
		Assert.assertEquals(new Float(101), json.getFloatOrDefault("big", 0));
		Assert.assertEquals(new Double(101), json.getDoubleOrDefault("big", 0));
		/* Strings are gotten from booleans, numbers, and strings. */
		Assert.assertEquals("101", json.getString("string"));
		Assert.assertEquals("true", json.getString("boolean"));
		Assert.assertEquals("101", json.getString("number"));
		Assert.assertEquals("101", json.getString("big"));
		Assert.assertEquals("101", json.getStringOrDefault("string", "failed"));
		Assert.assertEquals("true", json.getStringOrDefault("boolean", "failed"));
		Assert.assertEquals("101", json.getStringOrDefault("number", "failed"));
		Assert.assertEquals("101", json.getStringOrDefault("big", "failed"));
		/* Gets return null if the value is null. */
		Assert.assertEquals(null, json.getStringOrDefault("null", ""));
		Assert.assertEquals(null, json.getBigDecimalOrDefault("null", new BigDecimal(0)));
		Assert.assertEquals(null, json.getBooleanOrDefault("null", true));
		Assert.assertEquals(null, json.getByteOrDefault("null", (byte)0));
		Assert.assertEquals(null, json.getShortOrDefault("null", (short)0));
		Assert.assertEquals(null, json.getIntegerOrDefault("null", 0));
		Assert.assertEquals(null, json.getLongOrDefault("null", 0L));
		Assert.assertEquals(null, json.getFloatOrDefault("null", 0));
		Assert.assertEquals(null, json.getDoubleOrDefault("null", 0));
		Assert.assertEquals(null, json.getString("null"));
		Assert.assertEquals(null, json.getBigDecimal("null"));
		Assert.assertEquals(null, json.getBoolean("null"));
		Assert.assertEquals(null, json.getByte("null"));
		Assert.assertEquals(null, json.getShort("null"));
		Assert.assertEquals(null, json.getInteger("null"));
		Assert.assertEquals(null, json.getLong("null"));
		Assert.assertEquals(null, json.getFloat("null"));
		Assert.assertEquals(null, json.getDouble("null"));
		Assert.assertEquals(null, json.get("null"));
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
		 * TestKeys need to swap values once in a while.
		 */
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
		Assert.assertEquals(new Byte((byte)101), json.getByte(TestKeys.key0));
		Assert.assertEquals(new Short((short)101), json.getShort(TestKeys.key0));
		Assert.assertEquals(new Integer(101), json.getInteger(TestKeys.key0));
		Assert.assertEquals(new Long(101), json.getLong(TestKeys.key0));
		Assert.assertEquals(new Float(101), json.getFloat(TestKeys.key0));
		Assert.assertEquals(new Double(101), json.getDouble(TestKeys.key0));
		Assert.assertEquals(new Byte((byte)101), json.getByteOrDefault(Jsoner.mintJsonKey("key0", (byte)0)));
		Assert.assertEquals(new Short((short)101), json.getShortOrDefault(Jsoner.mintJsonKey("key0", (short)0)));
		Assert.assertEquals(new Integer(101), json.getIntegerOrDefault(Jsoner.mintJsonKey("key0", 0)));
		Assert.assertEquals(new Long(101), json.getLongOrDefault(Jsoner.mintJsonKey("key0", 0)));
		Assert.assertEquals(new Float(101), json.getFloatOrDefault(Jsoner.mintJsonKey("key0", 0)));
		Assert.assertEquals(new Double(101), json.getDoubleOrDefault(Jsoner.mintJsonKey("key0", 0)));
		/* Numbers are gotten from numbers. */
		Assert.assertEquals(new Byte((byte)101), json.getByte(TestKeys.key2));
		Assert.assertEquals(new Short((short)101), json.getShort(TestKeys.key2));
		Assert.assertEquals(new Integer(101), json.getInteger(TestKeys.key2));
		Assert.assertEquals(new Long(101), json.getLong(TestKeys.key2));
		Assert.assertEquals(new Float(101), json.getFloat(TestKeys.key2));
		Assert.assertEquals(new Double(101), json.getDouble(TestKeys.key2));
		Assert.assertEquals(new Byte((byte)101), json.getByteOrDefault(Jsoner.mintJsonKey("key2", (byte)0)));
		Assert.assertEquals(new Short((short)101), json.getShortOrDefault(Jsoner.mintJsonKey("key2", (short)0)));
		Assert.assertEquals(new Integer(101), json.getIntegerOrDefault(Jsoner.mintJsonKey("key2", 0)));
		Assert.assertEquals(new Long(101), json.getLongOrDefault(Jsoner.mintJsonKey("key2", 0)));
		Assert.assertEquals(new Float(101), json.getFloatOrDefault(Jsoner.mintJsonKey("key2", 0)));
		Assert.assertEquals(new Double(101), json.getDoubleOrDefault(Jsoner.mintJsonKey("key2", 0)));
		Assert.assertEquals(new Byte((byte)101), json.getByte(TestKeys.key3));
		Assert.assertEquals(new Short((short)101), json.getShort(TestKeys.key3));
		Assert.assertEquals(new Integer(101), json.getInteger(TestKeys.key3));
		Assert.assertEquals(new Long(101), json.getLong(TestKeys.key3));
		Assert.assertEquals(new Float(101), json.getFloat(TestKeys.key3));
		Assert.assertEquals(new Double(101), json.getDouble(TestKeys.key3));
		Assert.assertEquals(new Byte((byte)101), json.getByteOrDefault(Jsoner.mintJsonKey("key3", (byte)0)));
		Assert.assertEquals(new Short((short)101), json.getShortOrDefault(Jsoner.mintJsonKey("key3", (short)0)));
		Assert.assertEquals(new Integer(101), json.getIntegerOrDefault(Jsoner.mintJsonKey("key3", 0)));
		Assert.assertEquals(new Long(101), json.getLongOrDefault(Jsoner.mintJsonKey("key3", 0)));
		Assert.assertEquals(new Float(101), json.getFloatOrDefault(Jsoner.mintJsonKey("key3", 0)));
		Assert.assertEquals(new Double(101), json.getDoubleOrDefault(Jsoner.mintJsonKey("key3", 0)));
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
}
