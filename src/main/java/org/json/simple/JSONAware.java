/* Copyright 2006 FangYidong

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */
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
