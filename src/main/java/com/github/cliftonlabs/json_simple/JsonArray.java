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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/** JsonArray is a common non-thread safe data format for a collection of data. The contents of a JsonArray are only
 * validated as JSON values on serialization. Meaning all values added to a JsonArray must be recognized by the Jsoner
 * for it to be a true 'JsonArray', so it is really a JsonableArrayList that will serialize to a JsonArray if all of
 * its contents are valid JSON.
 * @see Jsoner
 * @since 2.0.0 */
public class JsonArray extends ArrayList<Object> implements Jsonable{
	/** The serialization version this class is compatible with. This value doesn't need to be incremented if and only
	 * if the only changes to occur were updating comments, updating javadocs, adding new fields to the class, changing
	 * the fields from static to non-static, or changing the fields from transient to non transient. All other changes
	 * require this number be incremented. */
	private static final long serialVersionUID = 1L;

	/** Instantiates an empty JsonArray. */
	public JsonArray(){
		super();
	}

	/** Instantiate a new JsonArray using ArrayList's constructor of the same type.
	 * @param collection represents the elements to produce the JsonArray with. */
	public JsonArray(final Collection<?> collection){
		super(collection);
	}

	/** Calls add for the given collection of elements, but returns the JsonArray for chaining calls.
	 * @param collection represents the items to be appended to the JsonArray.
	 * @return the JsonArray to allow chaining calls.
	 * @see ArrayList#addAll(Collection)
	 * @since 3.1.0 for inline instantiation. */
	public JsonArray addAllChain(final Collection<?> collection){
		this.addAll(collection);
		return this;
	}

	/** Calls add for the given index and collection, but returns the JsonArray for chaining calls.
	 * @param index represents what index the element is added to in the JsonArray.
	 * @param collection represents the item to be appended to the JsonArray.
	 * @return the JsonArray to allow chaining calls.
	 * @see ArrayList#addAll(int, Collection)
	 * @since 3.1.0 for inline instantiation. */
	public JsonArray addAllChain(final int index, final Collection<?> collection){
		this.addAll(index, collection);
		return this;
	}

	/** Calls add for the given element, but returns the JsonArray for chaining calls.
	 * @param index represents what index the element is added to in the JsonArray.
	 * @param element represents the item to be appended to the JsonArray.
	 * @return the JsonArray to allow chaining calls.
	 * @see ArrayList#add(int, Object)
	 * @since 3.1.0 for inline instantiation. */
	public JsonArray addChain(final int index, final Object element){
		this.add(index, element);
		return this;
	}

	/** Calls add for the given element, but returns the JsonArray for chaining calls.
	 * @param element represents the item to be appended to the JsonArray.
	 * @return the JsonArray to allow chaining calls.
	 * @see ArrayList#add(Object)
	 * @since 3.1.0 for inline instantiation. */
	public JsonArray addChain(final Object element){
		this.add(element);
		return this;
	}

	/** A convenience method that assumes every element of the JsonArray is castable to T before adding it to a
	 * collection of Ts.
	 * @param <T> represents the type that all of the elements of the JsonArray should be cast to and the type the
	 *        collection will contain.
	 * @param destination represents where all of the elements of the JsonArray are added to after being cast to the
	 *        generic type
	 *        provided.
	 * @throws ClassCastException if the unchecked cast of an element to T fails. */
	@SuppressWarnings("unchecked")
	public <T> void asCollection(final Collection<T> destination){
		for(final Object o : this){
			destination.add((T)o);
		}
	}

	/** A convenience method that assumes there is a BigDecimal, Number, or String at the given index. If a Number or
	 * String is there it is used to construct a new BigDecimal.
	 * @param index representing where the value is expected to be at.
	 * @return the value stored at the key or the default provided if the key doesn't exist.
	 * @throws ClassCastException if there was a value but didn't match the assumed return types.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal.
	 * @see BigDecimal
	 * @see Number#doubleValue() */
	public BigDecimal getBigDecimal(final int index){
		Object returnable = this.get(index);
		if(returnable instanceof BigDecimal){
			/* Success there was a BigDecimal. */
		}else if(returnable instanceof Number){
			/* A number can be used to construct a BigDecimal. */
			returnable = new BigDecimal(returnable.toString());
		}else if(returnable instanceof String){
			/* A number can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return (BigDecimal)returnable;
	}

	/** A convenience method that assumes there is a Boolean or String value at the given index.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a boolean.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray. */
	public Boolean getBoolean(final int index){
		Object returnable = this.get(index);
		if(returnable instanceof String){
			returnable = Boolean.valueOf((String)returnable);
		}
		return (Boolean)returnable;
	}

	/** A convenience method that assumes there is a Number or String value at the given index.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a byte.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
	 * @see Number */
	public Byte getByte(final int index){
		Object returnable = this.get(index);
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).byteValue();
	}

	/** A convenience method that assumes there is a Collection value at the given index.
	 * @param <T> the kind of collection to expect at the index. Note unless manually added, collection values will be a
	 *        JsonArray.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a Collection.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
	 * @see Collection */
	@SuppressWarnings("unchecked")
	public <T extends Collection<?>> T getCollection(final int index){
		/* The unchecked warning is suppressed because there is no way of guaranteeing at compile time the cast will
		 * work. */
		return (T)this.get(index);
	}

	/** A convenience method that assumes there is a Number or String value at the given index.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a double.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
	 * @see Number */
	public Double getDouble(final int index){
		Object returnable = this.get(index);
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).doubleValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given index.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a float.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
	 * @see Number */
	public Float getFloat(final int index){
		Object returnable = this.get(index);
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).floatValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given index.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a int.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
	 * @see Number */
	public Integer getInteger(final int index){
		Object returnable = this.get(index);
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).intValue();
	}

	/** A convenience method that assumes there is a Number or String value at the given index.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a long.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
	 * @see Number */
	public Long getLong(final int index){
		Object returnable = this.get(index);
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).longValue();
	}

	/** A convenience method that assumes there is a Map value at the given index.
	 * @param <T> the kind of map to expect at the index. Note unless manually added, Map values will be a JsonObject.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a Map.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
	 * @see Map */
	@SuppressWarnings("unchecked")
	public <T extends Map<?, ?>> T getMap(final int index){
		/* The unchecked warning is suppressed because there is no way of guaranteeing at compile time the cast will
		 * work. */
		return (T)this.get(index);
	}

	/** A convenience method that assumes there is a Number or String value at the given index.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a short.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws NumberFormatException if a String isn't a valid representation of a BigDecimal or if the Number
	 *         represents the double or float Infinity or NaN.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
	 * @see Number */
	public Short getShort(final int index){
		Object returnable = this.get(index);
		if(returnable == null){
			return null;
		}
		if(returnable instanceof String){
			/* A String can be used to construct a BigDecimal. */
			returnable = new BigDecimal((String)returnable);
		}
		return ((Number)returnable).shortValue();
	}

	/** A convenience method that assumes there is a Boolean, Number, or String value at the given index.
	 * @param index represents where the value is expected to be at.
	 * @return the value at the index provided cast to a String.
	 * @throws ClassCastException if there was a value but didn't match the assumed return type.
	 * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray. */
	public String getString(final int index){
		Object returnable = this.get(index);
		if(returnable instanceof Boolean){
			returnable = returnable.toString();
		}else if(returnable instanceof Number){
			returnable = returnable.toString();
		}
		return (String)returnable;
	}

	/* (non-Javadoc)
	 * @see org.json.simple.Jsonable#asJsonString() */
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
	 * @see org.json.simple.Jsonable#toJsonString(java.io.Writer) */
	@Override
	public void toJson(final Writer writable) throws IOException{
		boolean isFirstElement = true;
		final Iterator<Object> elements = this.iterator();
		writable.write('[');
		while(elements.hasNext()){
			if(isFirstElement){
				isFirstElement = false;
			}else{
				writable.write(',');
			}
			Jsoner.serialize(elements.next(), writable);
		}
		writable.write(']');
	}
}
