/* See: README for this file's copyright, terms, and conditions. */
package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/** JsonArray is a common non-thread safe data format for a collection of data. The contents of a JsonArray are only validated as
 * JSON values on serialization.
 * @see Jsoner
 * @since 2.0.0 */
public class JsonArray extends ArrayList<Object> implements Jsonable{
    /** The serialization version this class is compatible
     * with. This value doesn't need to be incremented if and only if the only changes to occur were updating comments,
     * updating javadocs, adding new
     * fields to the class, changing the fields from static to non-static, or changing the fields from transient to non
     * transient. All other changes require this number be incremented. */
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
            /* A number can be used to construct a BigDecimal */
            returnable = new BigDecimal(returnable.toString());
        }else if(returnable instanceof String){
            /* A number can be used to construct a BigDecimal */
            returnable = new BigDecimal((String)returnable);
        }
        return (BigDecimal)returnable;
    }

    /** A convenience method that assumes there is a boolean value at the given index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a boolean.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray. */
    public boolean getBoolean(final int index){
        return (boolean)this.get(index);
    }

    /** A convenience method that assumes there is a Number value at the given index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a byte.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
     * @see Number */
    public byte getByte(final int index){
        return ((Number)this.get(index)).byteValue();
    }

    /** A convenience method that assumes there is a Number value at the given index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a double.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
     * @see Number */
    public double getDouble(final int index){
        return ((Number)this.get(index)).doubleValue();
    }

    /** A convenience method that assumes there is a String value at the given index representing a fully qualified name
     * in dot notation of an enum.
     * @param index representing where the value is expected to be at.
     * @param <T> the Enum type the value at the index is expected to belong to.
     * @return the enum based on the string found at the index.
     * @throws ClassNotFoundException if the element was a String but the declaring enum type couldn't be determined
     *         with it.
     * @throws ClassCastException if the element at the index was not a String or if the fully qualified enum name is of
     *         the wrong type.
     * @throws IllegalArgumentException if an enum type was dynamically determined but it doesn't define an enum with
     *         the dynamically determined name.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
     * @see Enum static method valueOf(Class, String). */
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T getEnum(final int index) throws ClassNotFoundException{
        /* Supressing the unchecked warning because the returnType is dynamically identified and could lead to a
         * ClassCastException when returnType is cast to Class<T>, which is expected by the method's contract. */
        T returnable;
        final String element;
        final String[] splitValues;
        final int numberOfValues;
        final StringBuilder returnTypeName;
        final StringBuilder enumName;
        final Class<T> returnType;
        /* Make sure the element at the index is a String. */
        element = this.getString(index);
        /* Get the package, class, and enum names. */
        splitValues = element.split("\\.");
        numberOfValues = splitValues.length;
        returnTypeName = new StringBuilder();
        enumName = new StringBuilder();
        for(int i = 0; i < numberOfValues; i++){
            if(i == (numberOfValues - 1)){
                /* If it is the last split value then it should be the name of the Enum since dots are not allowed in
                 * enum names. */
                enumName.append(splitValues[i]);
            }else if(i == (numberOfValues - 2)){
                /* If it is the penultimate split value then it should be the end of the package/enum type and not need
                 * a dot appended to it. */
                returnTypeName.append(splitValues[i]);
            }else{
                /* Must be part of the package/enum type and will need a dot appended to it since they got removed in
                 * the split. */
                returnTypeName.append(splitValues[i]);
                returnTypeName.append(".");
            }
        }
        /* Use the package/class and enum names to get the Enum<T>. */
        returnType = (Class<T>)Class.forName(returnTypeName.toString());
        returnable = Enum.valueOf(returnType, enumName.toString());
        return returnable;
    }

    /** A convenience method that assumes there is a Number value at the given index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a float.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
     * @see Number */
    public float getFloat(final int index){
        return ((Number)this.get(index)).floatValue();
    }

    /** A convenience method that assumes there is a Number value at the given index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a int.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
     * @see Number */
    public int getInteger(final int index){
        return ((Number)this.get(index)).intValue();
    }

    /** A convenience method that assumes there is a Collection value at the given index.
     * @param <T> the kind of collection to expect at the index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a Collection.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
     * @see Collection */
    @SuppressWarnings("unchecked")
    public <T extends Collection<?>> T getCollection(final int index){
        /* The unchecked warning is suppressed because there is no way of guaranteeing at compile time the cast will work. */
        return (T)this.get(index);
    }

    /** A convenience method that assumes there is a Map value at the given index.
     * @param <T> the kind of map to expect at the index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a Map.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
     * @see Map */
    @SuppressWarnings("unchecked")
    public <T extends Map<?,?>> T getMap(final int index){
        /* The unchecked warning is suppressed because there is no way of guaranteeing at compile time the cast will work. */
        return (T)this.get(index);
    }

    /** A convenience method that assumes there is a Number value at the given index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a long.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
     * @see Number */
    public long getLong(final int index){
        return ((Number)this.get(index)).longValue();
    }

    /** A convenience method that assumes there is a Number value at the given index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a short.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray.
     * @see Number */
    public short getShort(final int index){
        return ((Number)this.get(index)).shortValue();
    }

    /** A convenience method that assumes there is a String value at the given index.
     * @param index represents where the value is expected to be at.
     * @return the value at the index provided cast to a String.
     * @throws ClassCastException if there was a value but didn't match the assumed return type.
     * @throws IndexOutOfBoundsException if the index is outside of the range of element indexes in the JsonArray. */
    public String getString(final int index){
        return (String)this.get(index);
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
            writable.write(Jsoner.serialize(elements.next()));
        }
        writable.write(']');
    }
}
