package org.json.simple.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

/**
 * Result class of static access {@link JSONParser#of}
 * Used to fastly navigate through resulted JSON
 * @author <a href="https://github.com/SashaSemenishchev">SashaSemenishchev</a>
 */
public class ParseResult implements Accessor {
    private final JSONAware result;

    public ParseResult(JSONAware result) {
        this.result = result;
    }

    /**
     *
     * @param index The index of resulted array to get
     * @return Accessor to manipulate output or continue digging into json further
     */
    public ParseResultAccessor get(int index) {
        return new ParseResultAccessor(result, index);
    }

    /**
     * @param key The key of the object
     * @return Accessor to manipulate output or continue digging into json further
     */
    public ParseResultAccessor get(String key) {
        return new ParseResultAccessor(result, key);
    }

    @Override
    public boolean isArray() {
        return result instanceof JSONArray;
    }

    @Override
    public boolean isJsonObject() {
        return result instanceof JSONObject;
    }

    @Override
    public boolean isNull() {
        return result == null;
    }

    @Override
    public JSONObject asJsonObject() {
        return (JSONObject) result;
    }

    @Override
    public JSONArray asArray() {
        return (JSONArray) result;
    }

    public static class ParseResultAccessor implements Accessor {
        private Object init;

        public ParseResultAccessor(JSONAware init, int first) {
            this.init = init;
            get(first);
        }

        public ParseResultAccessor(JSONAware init, String first) {
            this.init = init;
            get(first);
        }

        /**
         * Used if current element is {@link JSONArray} and it's needed to continue by index
         * @param index Index in the array
         * @return Array element accessor
         */
        public ParseResultAccessor get(int index) {
            this.init = ((JSONArray)this.init).get(index);
            return this;
        }

        /**
         * Used if current element is {@link JSONObject} and it's needed to continue by string key
         * @param key Key in the object
         * @return Object element accessor
         */
        public ParseResultAccessor get(String key) {
            this.init = ((JSONObject)this.init).get(key);
            return this;
        }

        /**
         * Used if it's needed to save current JSON position in the variable, for example to reduce amount of code
         * when needed objects are in the same root object
         * @return Parse result on the current point of the accessor
         */
        public ParseResult fixate() {
            return new ParseResult((JSONAware) init);
        }

        // Methods used to convert current position to needed objects

        public long asInt() {
            return (long) init;
        }

        public double asDouble() {
            return (double) init;
        }

        public float asFloat() {
            return (float) init;
        }

        public long asLong() {
            return (long) init;
        }

        public boolean asBoolean() {
            try {
                return (boolean) init;
            } catch (ClassCastException exception) {
                String val = init.toString();
                if(val.equalsIgnoreCase("true")) {
                    return true;
                } else if(val.equals("false")) {
                    return false;
                } else {
                    throw new ClassCastException("Invalid boolean literal at " + ((JSONAware) init).toJSONString());
                }
            }
        }

        public String asString() {
            return init.toString();
        }

        @Override
        public JSONArray asArray() {
            return (JSONArray) init;
        }

        @Override
        public JSONObject asJsonObject() {
            return (JSONObject) init;
        }

        @Override
        public boolean isArray() {
            return init instanceof JSONArray;
        }

        @Override
        public boolean isJsonObject() {
            return init instanceof JSONObject;
        }

        @Override
        public boolean isNull() {
            return init == null;
        }
    }
}
