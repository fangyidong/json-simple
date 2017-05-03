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

    /** Ensures another collection can be used to instantiate a JsonObject. */
    @Test
    public void testConstructor(){
        JsonObject json;
        LinkedHashMap<String, Integer> parameter;
        parameter = new LinkedHashMap<String, Integer>();
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

    /** Ensures a Collection can be returned from a key. */
    @Test
    public void testGetCollectionOrDefault(){
        final JsonObject json = new JsonObject();
        LinkedList<Integer> list;
        HashSet<Integer> set;
        JsonArray array;
        List<?> output0;
        Set<?> output1;
        JsonArray output2;
        list = new LinkedList<Integer>();
        list.add(5);
        list.add(10);
        list.add(15);
        set = new HashSet<Integer>();
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

    /** Ensures enums can be returned from a String value at an index.
     * @throws ClassNotFoundException if the test failed. */
    @Test
    public void testGetEnum() throws ClassNotFoundException{
        final JsonObject json = new JsonObject();
        json.put("key0", "org.json.simple.JsonObjectTest$TestStaticEnums.ONE");
        json.put("key1", "org.json.simple.JsonObjectTest$TestEnums.A");
        Assert.assertEquals(JsonObjectTest.TestStaticEnums.ONE, json.getEnum("key0"));
        Assert.assertEquals(JsonObjectTest.TestEnums.A, json.getEnum("key1"));
        Assert.assertEquals(JsonObjectTest.TestEnums.A, json.getEnumOrDefault("doesnotexist", JsonObjectTest.TestEnums.A));
    }

    /** Ensure a map can be returned from a key. */
    @Test
    public void testGetMap(){
        final JsonObject json = new JsonObject();
        final LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
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

    /** Ensures basic JSON values can be gotten. */
    @Test
    public void testOtherJsonGets(){
        final JsonObject json = new JsonObject();
        json.put("string", "101");
        json.put("boolean", true);
        json.put("number", 101);
        json.put("big", new BigDecimal("101"));
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
    }
}
