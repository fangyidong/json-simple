package org.json.simple.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Root of classes that can be one of {@link JSONObject} OR {@link JSONArray}
 * @author <a href="https://github.com/SashaSemenishchev">SashaSemenishchev</a>
 */
public interface Accessor {
    boolean isArray();
    boolean isJsonObject();
    boolean isNull();

    JSONObject asJsonObject();
    JSONArray asArray();
}
