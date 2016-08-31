package org.json.simple;

import java.io.IOException;
import java.io.Writer;

/**
 * Beans that support customized output of JSON text to a writer shall implement this interface.  
 * @author FangYidong&lt;fangyidong@yahoo.com.cn&gt;
 * @deprecated since 2.0.0, replaced by {@link org.json.simple.Jsonable}
 */
@Deprecated
public interface JSONStreamAware {
	/**
	 * write JSON string to out.
	 * @param out description omitted.
	 * @throws IOException description omitted.
	 */
	void writeJSONString(Writer out) throws IOException;
}
