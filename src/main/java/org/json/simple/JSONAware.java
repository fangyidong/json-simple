package org.json.simple;

/**
 * Beans that support customized output of JSON text shall implement this interface.  
 * @author FangYidong&lt;fangyidong@yahoo.com.cn&gt;
 * @deprecated since 2.0.0, replaced by {@link org.json.simple.Jsonable}
 */
@Deprecated
public interface JSONAware {
	/**
	 * @return JSON text
	 */
	String toJSONString();
}
