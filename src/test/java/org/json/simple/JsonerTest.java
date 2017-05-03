/* See: README for this file's copyright, terms, and conditions. */
package org.json.simple;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Ensures that deserialization and serialization hasn't regressed in functionality or breaks its API contract. */
public class JsonerTest{
    /** @see JsonerTest#testEnumSerialization() */
    private enum TestEnums{
        A,
        B;
    }

    /** @see JsonerTest#testEnumSerialization() */
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

    /** Ensures arrays are directly deserializable.
     * @throws DeserializationException if the test fails. */
    @Test
    public void testArrayDeserialization() throws DeserializationException{
        JsonArray defaultValue;
        Object deserialized;
        /* Trailing commas are common causes of wasting time debugging JSON. Allowing it in deserialization will
         * inevitably make it feel more simple and save the user time debugging pointless things. */
        deserialized = Jsoner.deserialize("[,]");
        Assert.assertEquals(new JsonArray(), deserialized);
        /* Serializing JsonArrays directly requires a defaultValue in case it doesn't deserialize a JsonArray. */
        defaultValue = new JsonArray();
        defaultValue.add("default");
        deserialized = Jsoner.deserialize("[,]", defaultValue);
        Assert.assertEquals(new JsonArray(), deserialized);
        /* The call should return the defaultValue instead. */
        deserialized = Jsoner.deserialize("[asdf,]", defaultValue);
        Assert.assertEquals(defaultValue, deserialized);
    }

    /** Ensures arrays are serializable.
     * @throws IOException if the test fails. */
    @Test
    public void testArraySerialization() throws IOException{
        StringWriter serialized;
        /* Extraneous commas are not allowed when serializing an array. */
        serialized = new StringWriter();
        Jsoner.serialize(new JsonArray(), serialized);
        Assert.assertEquals("[]", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly(new JsonArray(), serialized);
        Assert.assertEquals("[]", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(new JsonArray(), serialized);
        Assert.assertEquals("[]", serialized.toString());
    }

    /** Ensures booleans are directly deserializable.
     * @throws DeserializationException if the test fails. */
    @Test
    public void testBooleanDeserialization() throws DeserializationException{
        Object deserialized;
        deserialized = Jsoner.deserialize("true");
        Assert.assertEquals(true, deserialized);
        deserialized = Jsoner.deserialize("false");
        Assert.assertEquals(false, deserialized);
    }

    /** Ensures booleans are serializable.
     * @throws IOException if the test fails. */
    @Test
    public void testBooleanSerialization() throws IOException{
        StringWriter serialized;
        serialized = new StringWriter();
        Jsoner.serialize(true, serialized);
        Assert.assertEquals("true", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly(true, serialized);
        Assert.assertEquals("true", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(true, serialized);
        Assert.assertEquals("true", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serialize(false, serialized);
        Assert.assertEquals("false", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly(false, serialized);
        Assert.assertEquals("false", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(false, serialized);
        Assert.assertEquals("false", serialized.toString());
    }

    /** Ensures multiple concatenated JSON values are directly deserializable.
     * @throws DeserializationException if the test fails.
     * @throws IOException if the test fails. */
    @Test
    public void testDeserializationMany() throws DeserializationException, IOException{
        final StringBuilder deserializable = new StringBuilder();
        JsonArray expected;
        Object deserialized;
        /* Build the input string and the expected output one by one. */
        expected = new JsonArray();
        deserializable.append(false);
        expected.add(false);
        deserializable.append("{}");
        expected.add(new JsonObject());
        deserializable.append("{}");
        expected.add(new JsonObject());
        deserializable.append("{}");
        expected.add(new JsonObject());
        deserializable.append((String)null);
        expected.add(null);
        deserializable.append((String)null);
        expected.add(null);
        deserializable.append((String)null);
        expected.add(null);
        deserializable.append(true);
        expected.add(true);
        deserializable.append(true);
        expected.add(true);
        deserializable.append("[]");
        expected.add(new JsonArray());
        deserializable.append("123");
        expected.add(new BigDecimal("123"));
        deserializable.append("{}");
        expected.add(new JsonObject());
        deserializable.append("[]");
        expected.add(new JsonArray());
        deserializable.append("12.3");
        expected.add(new BigDecimal("12.3"));
        deserializable.append("\"\"");
        expected.add("");
        deserializable.append("\"\\\"\\\"\"");
        expected.add("\"\"");
        deserializable.append("\"String\"");
        expected.add("String");
        deserializable.append("12.3e-10");
        expected.add(new BigDecimal("12.3e-10"));
        deserializable.append("[]");
        expected.add(new JsonArray());
        deserializable.append("[]");
        expected.add(new JsonArray());
        deserializable.append("[]");
        expected.add(new JsonArray());
        deserialized = Jsoner.deserializeMany(new StringReader(deserializable.toString()));
        Assert.assertEquals(expected, deserialized);
    }

    /** Makes sure enums are serialized when appropriate.
     * @throws IOException if the test fails. */
    @Test
    public void testEnumSerialization() throws IOException{
        StringWriter serialized;
        serialized = new StringWriter();
        Jsoner.serialize(TestStaticEnums.ONE, serialized);
        Assert.assertEquals("\"org.json.simple.JsonerTest$TestStaticEnums.ONE\"", serialized.toString());
        serialized = new StringWriter();
        try{
            Jsoner.serializeStrictly(TestStaticEnums.ONE, serialized);
        }catch(final IllegalArgumentException caught){
            /* Strictly doesn't allow enums. */
        }
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(TestStaticEnums.ONE, serialized);
        Assert.assertEquals(TestStaticEnums.ONE.toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serialize(TestEnums.A, serialized);
        Assert.assertEquals("\"org.json.simple.JsonerTest$TestEnums.A\"", serialized.toString());
        serialized = new StringWriter();
        try{
            Jsoner.serializeStrictly(TestEnums.A, serialized);
        }catch(final IllegalArgumentException caught){
            /* Strictly doesn't allow enums. */
        }
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(TestEnums.A, serialized);
        Assert.assertEquals(TestEnums.A.toString(), serialized.toString());
    }

    /** Ensures booleans, JsonArray, JsonObject, null, numbers, and Strings are deserializable while inside a JsonObject
     * or JsonArray.
     * @throws DeserializationException if the test fails. */
    @Test
    public void testNestedDeserialization() throws DeserializationException{
        JsonArray expectedArray;
        JsonObject expectedObject;
        Object deserialized;
        /* Set up the expected array. */
        expectedArray = new JsonArray();
        expectedArray.add(true);
        expectedArray.add(false);
        expectedArray.add(new JsonArray());
        expectedArray.add(new JsonObject());
        expectedArray.add(null);
        expectedArray.add(new BigDecimal("-0.0e-100"));
        expectedArray.add("String");
        /* Set up the expected object. */
        expectedObject = new JsonObject();
        expectedObject.put("key0", true);
        expectedObject.put("key1", false);
        expectedObject.put("key2", new JsonArray());
        expectedObject.put("key3", new JsonObject());
        expectedObject.put("key4", null);
        expectedObject.put("key5", new BigDecimal("-0.0e-100"));
        expectedObject.put("key6", "String");
        /* Check that the nested serializations worked, with extra commas for good measure. */
        deserialized = Jsoner.deserialize("[true,false,[],,{},null,-0.0e-100,,\"String\",]");
        Assert.assertEquals(expectedArray, deserialized);
        deserialized = Jsoner.deserialize("{\"key0\":true,\"key1\":false,\"key2\":[],,\"key3\":{},,\"key4\":null,\"key5\":-0.0e-100,\"key6\":\"String\",}");
        Assert.assertEquals(expectedObject, deserialized);
    }

    /** Ensures booleans, JsonArray, JsonObject, null, numbers, and Strings are serializable while inside a JsonObject or
     * JsonArray.
     * @throws IOException if the test failed. */
    @Test
    public void testNestedSerialization() throws IOException{
        JsonArray inputArray;
        JsonObject inputObject;
        StringWriter output;
        String serialized;
        /* Set up the input array. */
        inputArray = new JsonArray();
        inputArray.add(true);
        inputArray.add(false);
        inputArray.add(new JsonArray());
        inputArray.add(new JsonObject());
        inputArray.add(null);
        inputArray.add(new BigDecimal("-0.0e-100"));
        inputArray.add("String");
        /* Set up the input object. */
        inputObject = new JsonObject();
        inputObject.put("key0", true);
        inputObject.put("key1", false);
        inputObject.put("key2", new JsonArray());
        inputObject.put("key3", new JsonObject());
        inputObject.put("key4", null);
        inputObject.put("key5", new BigDecimal("-0.0e-100"));
        inputObject.put("key6", "String");
        /* Check that the nested serializations worked and should never have extraneous commas.
         * First check the array's serialization [normal, strictly, carelessly] output. */
        output = new StringWriter();
        Jsoner.serialize(inputArray, output);
        serialized = output.toString();
        Assert.assertEquals("[true,false,[],{},null,0E-101,\"String\"]", output.toString());
        output = new StringWriter();
        Jsoner.serializeStrictly(inputArray, output);
        serialized = output.toString();
        Assert.assertEquals("[true,false,[],{},null,0E-101,\"String\"]", output.toString());
        output = new StringWriter();
        Jsoner.serializeCarelessly(inputArray, output);
        serialized = output.toString();
        Assert.assertEquals("[true,false,[],{},null,0E-101,\"String\"]", output.toString());
        /* Next check the object's serialization [normal, strictly, carelessly] output. */
        output = new StringWriter();
        Jsoner.serialize(inputObject, output);
        serialized = output.toString();
        /* Ensure it started with a '{' and ended with a '}'. */
        Assert.assertTrue(serialized.charAt(0) == '{');
        Assert.assertTrue(serialized.charAt(serialized.length() - 1) == '}');
        /* Ensure each key and value were present in the correct format. */
        Assert.assertTrue(serialized.contains("\"key0\":true"));
        Assert.assertTrue(serialized.contains("\"key1\":false"));
        Assert.assertTrue(serialized.contains("\"key2\":[]"));
        Assert.assertTrue(serialized.contains("\"key3\":{}"));
        Assert.assertTrue(serialized.contains("\"key4\":null"));
        Assert.assertTrue(serialized.contains("\"key5\":0E-101"));
        Assert.assertTrue(serialized.contains("\"key6\":\"String\""));
        /* Ensure there were the correct amount of entries separated by a comma. */
        Assert.assertTrue(serialized.split(",").length == 7);
        output = new StringWriter();
        Jsoner.serializeStrictly(inputObject, output);
        serialized = output.toString();
        /* Ensure it started with a '{' and ended with a '}'. */
        Assert.assertTrue(serialized.charAt(0) == '{');
        Assert.assertTrue(serialized.charAt(serialized.length() - 1) == '}');
        /* Ensure each key and value were present in the correct format. */
        Assert.assertTrue(serialized.contains("\"key0\":true"));
        Assert.assertTrue(serialized.contains("\"key1\":false"));
        Assert.assertTrue(serialized.contains("\"key2\":[]"));
        Assert.assertTrue(serialized.contains("\"key3\":{}"));
        Assert.assertTrue(serialized.contains("\"key4\":null"));
        Assert.assertTrue(serialized.contains("\"key5\":0E-101"));
        Assert.assertTrue(serialized.contains("\"key6\":\"String\""));
        /* Ensure there were the correct amount of entries separated by a comma. */
        Assert.assertTrue(serialized.split(",").length == 7);
        output = new StringWriter();
        Jsoner.serializeCarelessly(inputObject, output);
        serialized = output.toString();
        /* Ensure it started with a '{' and ended with a '}'. */
        Assert.assertTrue(serialized.charAt(0) == '{');
        Assert.assertTrue(serialized.charAt(serialized.length() - 1) == '}');
        /* Ensure each key and value were present in the correct format. */
        Assert.assertTrue(serialized.contains("\"key0\":true"));
        Assert.assertTrue(serialized.contains("\"key1\":false"));
        Assert.assertTrue(serialized.contains("\"key2\":[]"));
        Assert.assertTrue(serialized.contains("\"key3\":{}"));
        Assert.assertTrue(serialized.contains("\"key4\":null"));
        Assert.assertTrue(serialized.contains("\"key5\":0E-101"));
        Assert.assertTrue(serialized.contains("\"key6\":\"String\""));
        /* Ensure there were the correct amount of entries separated by a comma. */
        Assert.assertTrue(serialized.split(",").length == 7);
    }

    /** Ensures null is directly deserializable.
     * @throws DeserializationException if the test fails. */
    @Test
    public void testNullDeserialization() throws DeserializationException{
        Object deserialized;
        deserialized = Jsoner.deserialize("null");
        Assert.assertEquals(null, deserialized);
    }

    /** Ensures null is serializable.
     * @throws IOException if the test fails. */
    @Test
    public void testNullSerialization() throws IOException{
        StringWriter serialized;
        serialized = new StringWriter();
        Jsoner.serialize(null, serialized);
        Assert.assertEquals("null", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly(null, serialized);
        Assert.assertEquals("null", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(null, serialized);
        Assert.assertEquals("null", serialized.toString());
    }

    /** Ensures Numbers are directly deserializable.
     * @throws DeserializationException if the test fails. */
    @Test
    public void testNumberDeserialization() throws DeserializationException{
        Object deserialized;
        deserialized = Jsoner.deserialize("-1234567890987654321.01234567890987654321E-50");
        Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321E-50"), deserialized);
        deserialized = Jsoner.deserialize("-1234567890987654321.01234567890987654321");
        Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321"), deserialized);
        deserialized = Jsoner.deserialize("1234567890987654321.01234567890987654321");
        Assert.assertEquals(new BigDecimal("1234567890987654321.01234567890987654321"), deserialized);
        deserialized = Jsoner.deserialize("123456789098765432101234567890987654321");
        Assert.assertEquals(new BigDecimal("123456789098765432101234567890987654321"), deserialized);
    }

    /** Ensures Numbers are serializable.
     * @throws IOException if the test fails. */
    @Test
    public void testNumberSerialization() throws IOException{
        StringWriter serialized;
        serialized = new StringWriter();
        Jsoner.serialize(new BigDecimal("-1234567890987654321.01234567890987654321E-50"), serialized);
        Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321E-50").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly(new BigDecimal("-1234567890987654321.01234567890987654321E-50"), serialized);
        Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321E-50").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(new BigDecimal("-1234567890987654321.01234567890987654321E-50"), serialized);
        Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321E-50").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serialize(new BigDecimal("-1234567890987654321.01234567890987654321"), serialized);
        Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly(new BigDecimal("-1234567890987654321.01234567890987654321"), serialized);
        Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(new BigDecimal("-1234567890987654321.01234567890987654321"), serialized);
        Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serialize(new BigDecimal("1234567890987654321.01234567890987654321"), serialized);
        Assert.assertEquals(new BigDecimal("1234567890987654321.01234567890987654321").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly(new BigDecimal("1234567890987654321.01234567890987654321"), serialized);
        Assert.assertEquals(new BigDecimal("1234567890987654321.01234567890987654321").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(new BigDecimal("1234567890987654321.01234567890987654321"), serialized);
        Assert.assertEquals(new BigDecimal("1234567890987654321.01234567890987654321").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serialize(new BigDecimal("123456789098765432101234567890987654321"), serialized);
        Assert.assertEquals(new BigDecimal("123456789098765432101234567890987654321").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly(new BigDecimal("123456789098765432101234567890987654321"), serialized);
        Assert.assertEquals(new BigDecimal("123456789098765432101234567890987654321").toString(), serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(new BigDecimal("123456789098765432101234567890987654321"), serialized);
        Assert.assertEquals(new BigDecimal("123456789098765432101234567890987654321").toString(), serialized.toString());
    }

    /** Ensures objects are directly deserializable.
     * @throws DeserializationException if the test fails. */
    @Test
    public void testObjectDeserialization() throws DeserializationException{
        JsonObject defaultValue;
        Object deserialized;
        JsonObject expected;
        expected = new JsonObject();
        defaultValue = new JsonObject();
        defaultValue.put("error", -1);
        /* Trailing commas are common causes of wasting time debugging JSON. Allowing it in deserialization will
         * inevitably make it feel more simple and save the user time debugging pointless things. */
        deserialized = Jsoner.deserialize("{,}");
        Assert.assertEquals(expected, deserialized);
        /* A missing colon can be frustrating to track down and a waste of time debugging JSON. Allowing it in
         * deserialization will inevitably make it feel more simple and save the user time debugging things that don't
         * actually impede the library. */
        expected.put("key", "value");
        deserialized = Jsoner.deserialize("{\"key\"\"value\"}", defaultValue);
        Assert.assertEquals(expected, deserialized);
        /* Same thing but with numbers. */
        expected.remove("key");
        expected.put("key", new BigDecimal("234.0"));
        deserialized = Jsoner.deserialize("{\"key\"234.0}", defaultValue);
        Assert.assertEquals(expected, deserialized);
        /* Same thing but with booleans. */
        expected.remove("key");
        expected.put("key", true);
        deserialized = Jsoner.deserialize("{\"key\"true}", defaultValue);
        Assert.assertEquals(expected, deserialized);
        /* Same thing but with objects. */
        expected.remove("key");
        expected.put("key", new JsonObject());
        deserialized = Jsoner.deserialize("{\"key\"{}}", defaultValue);
        Assert.assertEquals(expected, deserialized);
        /* Same thing but with arrays. */
        expected.remove("key");
        expected.put("key", new JsonArray());
        deserialized = Jsoner.deserialize("{\"key\"[]}", defaultValue);
        Assert.assertEquals(expected, deserialized);
        /* Deserializing JsonObjects directly requires a defaultValue in case it doesn't deserialize a JsonObject. */
        deserialized = Jsoner.deserialize("{asdf,}", defaultValue);
        Assert.assertEquals(defaultValue, deserialized);
    }

    /** Ensures objects are serializable.
     * @throws IOException if the test fails. */
    @Test
    public void testObjectSerialization() throws IOException{
        StringWriter serialized;
        /* Extraneous commas are not allowed when serializing an object. */
        serialized = new StringWriter();
        Jsoner.serialize(new JsonObject(), serialized);
        Assert.assertEquals("{}", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly(new JsonObject(), serialized);
        Assert.assertEquals("{}", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly(new JsonObject(), serialized);
        Assert.assertEquals("{}", serialized.toString());
    }

    /** Ensures arrays and objects can be printed in an easier to read format. */
    @Test
    public void testPrettyPrint(){
        Assert.assertEquals("[\n\t0,\n\t1,\n\t2,\n\t{\n\t\t\"k0\":\"v0\",\n\t\t\"k1\":\"v1\"\n\t},\n\t[\n\t\t[\n\t\t\t\"\",\n\t\t\t\"\"\n\t\t]\n\t],\n\tnull,\n\ttrue,\n\tfalse\n]", Jsoner.prettyPrint("[0,1,2,{\"k0\":\"v0\",\"k1\":\"v1\"},[[\"\",\"\"]],null,true,false]"));
    }

    /** Ensures Strings are directly deserializable.
     * @throws DeserializationException if the test fails. */
    @Test
    public void testStringDeserialization() throws DeserializationException{
        Object deserialized;
        /* Uses typical US English and picks out characters in unicode that have a decimal representation that ends with
         * 050, like 1050, 3050, 4050, 5050, etc. */
        deserialized = Jsoner.deserialize("\"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]\\/`123456789-=~!@#$%^&*_+()\\r\\b\\n\\t\\f\\\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ\\u4e2d\"");
        Assert.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()\r\b\n\t\f\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ\u4e2d", deserialized);
    }

    /** Ensures Strings are serializable.
     * @throws IOException if the test fails. */
    @Test
    public void testStringSerialization() throws IOException{
        StringWriter serialized;
        /* Uses typical US English and picks out characters in unicode that have a decimal representation that ends with
         * 050, like 1050, 3050, 4050, 5050, etc. */
        serialized = new StringWriter();
        Jsoner.serialize("ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()\r\b\n\t\f\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ\u4e2d", serialized);
        Assert.assertEquals("\"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]\\/`123456789-=~!@#$%^&*_+()\\r\\b\\n\\t\\f\\\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ中\"", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeStrictly("ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()\r\b\n\t\f\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ\u4e2d", serialized);
        Assert.assertEquals("\"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]\\/`123456789-=~!@#$%^&*_+()\\r\\b\\n\\t\\f\\\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ中\"", serialized.toString());
        serialized = new StringWriter();
        Jsoner.serializeCarelessly("ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()\r\b\n\t\f\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ\u4e2d", serialized);
        Assert.assertEquals("\"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]\\/`123456789-=~!@#$%^&*_+()\\r\\b\\n\\t\\f\\\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ中\"", serialized.toString());
    }
}
