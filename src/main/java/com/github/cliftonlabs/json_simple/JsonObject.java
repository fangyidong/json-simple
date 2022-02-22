/* Copyright 2016-2017 Clifton Labs
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */
package com.github.cliftonlabs.json_simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/** JsonObject is a common non-thread safe data format for string to data mappings. The contents of a JsonObject are
 * only validated as JSON values on serialization. Meaning all values added to a JsonObject must be recognized by the
 * Jsoner for it to be a true 'JsonObject', so it is really a JsonableHashMap that will serialize to a JsonObject if all
 * of its contents are valid JSON.
 * @see Jsoner
 * @since 2.0.0 */
public class JsonObject extends HashMap<String, Object> implements Jsonable{
	/** The serialization version this class is compatible with. This value doesn't need to be incremented if and only
	 * if the only changes to occur were updating comments, updating javadocs, adding new fields to the class, changing
	 * the fields from static to non-static, or changing the fields from transient to non transient. All other changes
	 * require this number be incremented. */
	private static final long serialVersionUID = 2L;

	/** Instantiates an empty JsonObject. */
	public JsonObject(){
		super();
	}

	/** Instantiate a new JsonObject by accepting a map's entries, which could lead to de/serialization issues of the
	 * resulting JsonObject since the entry values aren't validated as JSON values.
	 * @param map represents the mappings to produce the JsonObject with. */
	public JsonObject(final Map<String, ?> map){
		super(map);
	}

	/** A convenience method that assumes there is a BigDecimal, Number, or String at the given key. If a Number is
	 * there its Number#toString() is used to construct a new BigDecimal(String). If a String is there it is used to
	 * construct a new BigDecimal(String).
	 * @param key representing where the value ought to be paired with.
	 * @return a BigDecimal representing the value paired with the key.
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see BigDecimal
	 * @see Number#toString()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public BigDecimal getBigDecimal(final JsonKey key){
		Object returnable = this.get(key.getKey());
		if(returnable instanceof BigDecimal){
			/* Success there was a BigDecimal or it defaulted. */
		}else if(returnable instanceof Number){
			/* A number can be used to construct a BigDecimal */
			returnable = new BigDecimal(returnable.toString());
		}else if(returnable instanceof String){
			/* A number can be used to construct a BigDecimal */
			returnable = new BigDecimal((String)returnable);
		}
		return (BigDecimal)returnable;
	}

	/** A convenience method that assumes there is a BigDecimal, Number, or String at the given key. If a Number is
	 * there its Number#toString() is used to construct a new BigDecimal(String). If a String is there it is used to
	 * construct a new BigDecimal(String).
	 * @param key representing where the value ought to be paired with.
	 * @return a BigDecimal representing the value paired with the key or JsonKey#getValue() if the key isn't present.
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see BigDecimal
	 * @see Number#toString()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public BigDecimal getBigDecimalOrDefault(final JsonKey key){
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		if(returnable instanceof BigDecimal){
			/* Success there was a BigDecimal or it defaulted. */
		}else if(returnable instanceof Number){
			/* A number can be used to construct a BigDecimal */
			returnable = new BigDecimal(returnable.toString());
		}else if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal */
			returnable = new BigDecimal((String)returnable);
		}
		return (BigDecimal)returnable;
	}

	/** A convenience method that assumes there is a Boolean or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Boolean representing the value paired with the key.
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Boolean getBoolean(final JsonKey key){
		Object returnable = this.get(key.getKey());
		if(returnable instanceof String){
			returnable = Boolean.valueOf((String)returnable);
		}
		return (Boolean)returnable;
	}

	/** A convenience method that assumes there is a Boolean or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Boolean representing the value paired with the key or JsonKey#getValue() if the key isn't present.
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Boolean getBooleanOrDefault(final JsonKey key){
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		if(returnable instanceof String){
			returnable = Boolean.valueOf((String)returnable);
		}
		return (Boolean)returnable;
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Byte representing the value paired with the key (which may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#byteValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Byte getByte(final JsonKey key){
		Object returnable = this.get(key.getKey());
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).byteValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Byte representing the value paired with the key or JsonKey#getValue() if the key isn't present (which
	 *         may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#byteValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Byte getByteOrDefault(final JsonKey key){
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).byteValue();
	}

	/** A convenience method that assumes there is a Collection at the given key.
	 * @param <T> the kind of collection to expect at the key. Note unless manually added, collection values will be a
	 *        JsonArray.
	 * @param key representing where the value ought to be paired with.
	 * @return a Collection representing the value paired with the key.
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	@SuppressWarnings("unchecked")
	public <T extends Collection<?>> T getCollection(final JsonKey key){
		/* The unchecked warning is suppressed because there is no way of guaranteeing at compile time the cast will
		 * work. */
		return (T)this.get(key.getKey());
	}

	/** A convenience method that assumes there is a Collection at the given key.
	 * @param <T> the kind of collection to expect at the key. Note unless manually added, collection values will be a
	 *        JsonArray.
	 * @param key representing where the value ought to be paired with.
	 * @return a Collection representing the value paired with the key or JsonKey#getValue() if the key isn't present..
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	@SuppressWarnings("unchecked")
	public <T extends Collection<?>> T getCollectionOrDefault(final JsonKey key){
		/* The unchecked warning is suppressed because there is no way of guaranteeing at compile time the cast will
		 * work. */
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		return (T)returnable;
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Double representing the value paired with the key (which may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#doubleValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Double getDouble(final JsonKey key){
		Object returnable = this.get(key.getKey());
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).doubleValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Double representing the value paired with the key or JsonKey#getValue() if the key isn't present (which
	 *         may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#doubleValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Double getDoubleOrDefault(final JsonKey key){
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).doubleValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Float representing the value paired with the key (which may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#floatValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Float getFloat(final JsonKey key){
		Object returnable = this.get(key.getKey());
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).floatValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Float representing the value paired with the key or JsonKey#getValue() if the key isn't present (which
	 *         may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#floatValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Float getFloatOrDefault(final JsonKey key){
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).floatValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return an Integer representing the value paired with the key (which may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#intValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Integer getInteger(final JsonKey key){
		Object returnable = this.get(key.getKey());
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).intValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return an Integer representing the value paired with the key or JsonKey#getValue() if the key isn't present
	 *         (which may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#intValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Integer getIntegerOrDefault(final JsonKey key){
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).intValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Long representing the value paired with the key (which may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#longValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Long getLong(final JsonKey key){
		Object returnable = this.get(key.getKey());
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).longValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Long representing the value paired with the key or JsonKey#getValue() if the key isn't present (which
	 *         may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#longValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Long getLongOrDefault(final JsonKey key){
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).longValue();
	}

	/** A convenience method that assumes there is a Map at the given key.
	 * @param <T> the kind of map to expect at the key. Note unless manually added, Map values will be a JsonObject.
	 * @param key representing where the value ought to be paired with.
	 * @return a Map representing the value paired with the key.
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	@SuppressWarnings("unchecked")
	public <T extends Map<?, ?>> T getMap(final JsonKey key){
		/* The unchecked warning is suppressed because there is no way of guaranteeing at compile time the cast will
		 * work. */
		return (T)this.get(key.getKey());
	}

	/** A convenience method that assumes there is a Map at the given key.
	 * @param <T> the kind of map to expect at the key. Note unless manually added, Map values will be a JsonObject.
	 * @param key representing where the value ought to be paired with.
	 * @return a Map representing the value paired with the key or JsonKey#getValue() if the key isn't present.
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	@SuppressWarnings("unchecked")
	public <T extends Map<?, ?>> T getMapOrDefault(final JsonKey key){
		/* The unchecked warning is suppressed because there is no way of guaranteeing at compile time the cast will
		 * work. */
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		return (T)returnable;
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Short representing the value paired with the key (which may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#shortValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Short getShort(final JsonKey key){
		Object returnable = this.get(key.getKey());
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).shortValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a Short representing the value paired with the key or JsonKey#getValue() if the key isn't present (which
	 *         may involve rounding or truncation).
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @see Number#shortValue()
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public Short getShortOrDefault(final JsonKey key){
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).shortValue();
	}

	/** A convenience method that assumes there is a Boolean, Number, or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a String representing the value paired with the key.
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public String getString(final JsonKey key){
		Object returnable = this.get(key.getKey());
		if(returnable instanceof Boolean){
			returnable = returnable.toString();
		}else if(returnable instanceof Number){
			returnable = returnable.toString();
		}
		return (String)returnable;
	}

	/** A convenience method that assumes there is a Boolean, Number, or String value at the given key.
	 * @param key representing where the value ought to be paired with.
	 * @return a String representing the value paired with the key or JsonKey#getValue() if the key isn't present.
	 * @throws ClassCastException if the value didn't match the assumed return type.
	 * @see JsonKey
	 * @since 2.3.0 to utilize JsonKey */
	public String getStringOrDefault(final JsonKey key){
		Object returnable;
		if(this.containsKey(key.getKey())){
			returnable = this.get(key.getKey());
		}else{
			returnable = key.getValue();
		}
		if(returnable instanceof Boolean){
			returnable = returnable.toString();
		}else if(returnable instanceof Number){
			returnable = returnable.toString();
		}
		return (String)returnable;
	}

	/** Convenience method that calls put for the given key and value.
	 * @param key represents the JsonKey used for the value's association in the map.
	 * @param value represents the key's association in the map.
	 * @see Map#put(Object, Object)
	 * @since 3.1.1 to use JsonKey instead of calling JsonKey#getKey() each time. */
	public void put(final JsonKey key, final Object value){
		this.put(key.getKey(), value);
	}

	/** Calls putAll for the given map, but returns the JsonObject for chaining calls.
	 * @param map represents the map to be copied into the JsonObject.
	 * @return the JsonObject to allow chaining calls.
	 * @see Map#putAll(Map)
	 * @since 3.1.0 for inline instantiation. */
	public JsonObject putAllChain(final Map<String, Object> map){
		this.putAll(map);
		return this;
	}

	/** Convenience method that calls put for the given key and value, but returns the JsonObject for chaining calls.
	 * @param key represents the JsonKey used for the value's association in the map.
	 * @param value represents the key's association in the map.
	 * @return the JsonObject to allow chaining calls.
	 * @see Map#put(Object, Object)
	 * @since 3.1.1 to use JsonKey instead of calling JsonKey#getKey() each time. */
	public JsonObject putChain(final JsonKey key, final Object value){
		this.put(key.getKey(), value);
		return this;
	}

	/** Calls put for the given key and value, but returns the JsonObject for chaining calls.
	 * @param key represents the value's association in the map.
	 * @param value represents the key's association in the map.
	 * @return the JsonObject to allow chaining calls.
	 * @see Map#put(Object, Object)
	 * @since 3.1.0 for inline instantiation. */
	public JsonObject putChain(final String key, final Object value){
		this.put(key, value);
		return this;
	}

	/** Convenience method that calls remove for the given key.
	 * @param key represents the value's association in the map.
	 * @return an object representing the removed value or null if there wasn't one.
	 * @since 3.1.1 to use JsonKey instead of calling JsonKey#getKey() each time.
	 * @see Map#remove(Object) */
	public Object remove(final JsonKey key){
		return this.remove(key.getKey());
	}

	/** Convenience method that calls remove for the given key and value.
	 * @param key represents the value's association in the map.
	 * @param value represents the expected value at the given key.
	 * @return a boolean, which is true if the value was removed. It is false otherwise.
	 * @since 3.1.1 to use JsonKey instead of calling JsonKey#getKey() each time.
	 * @see Map#remove(Object, Object) */
	public boolean remove(final JsonKey key, final Object value){
		return this.remove(key.getKey(), value);
	}

	/** Ensures the given keys are present.
	 * @param keys represents the keys that must be present.
	 * @throws NoSuchElementException if any of the given keys are missing.
	 * @since 2.3.0 to ensure critical keys are in the JsonObject. */
	public void requireKeys(final JsonKey... keys){
		/* Track all of the missing keys. */
		final Set<JsonKey> missing = new HashSet<>();
		for(final JsonKey k : keys){
			if(!this.containsKey(k.getKey())){
				missing.add(k);
			}
		}
		if(!missing.isEmpty()){
			/* Report any missing keys in the exception. */
			final StringBuilder sb = new StringBuilder();
			for(final JsonKey k : missing){
				sb.append(k.getKey()).append(", ");
			}
			sb.setLength(sb.length() - 2);
			final String s = missing.size() > 1 ? "s" : "";
			throw new NoSuchElementException("A JsonObject is missing required key" + s + ": " + sb.toString());
		}
	}

	/* (non-Javadoc)
	 * @see org.json.simple.Jsonable#toJson() */
	@Override
	public String toJson(){
		final StringWriter writable = new StringWriter();
		try{
			this.toJson(writable);
		}catch(final IOException caught){
			/* See java.io.StringWriter. */
		}
		return writable.toString();
	}

	/* (non-Javadoc)
	 * @see org.json.simple.Jsonable#toJson(java.io.Writer) */
	@Override
	public void toJson(final Writer writable) throws IOException{
		/* Writes the map in JSON object format. */
		boolean isFirstEntry = true;
		final Iterator<Map.Entry<String, Object>> entries = this.entrySet().iterator();
		writable.write('{');
		while(entries.hasNext()){
			if(isFirstEntry){
				isFirstEntry = false;
			}else{
				writable.write(',');
			}
			final Map.Entry<String, Object> entry = entries.next();
			Jsoner.serialize(entry.getKey(), writable);
			writable.write(':');
			Jsoner.serialize(entry.getValue(), writable);
		}
		writable.write('}');
	}
}
