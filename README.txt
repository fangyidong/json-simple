[CHANGE LOG]
Version 2.1.0 (2016/09/*)
* Bug fix: JsonObject#getDefaultByte(key, defaultValue) now properly returns a byte value instead of a float.
* Enhancement: JsonObject has typed gets for each JSON value type.
* Enhancement: JsonArray and JsonObject no longer return primitives.

Version 2.0.0 (2016/09/*)
* Davin Loegering was added to the list of Authors.
* Consolidated the author list, change log, and license files from the base directory into the README.txt file.
* Removed ant build file.
* SCM section of the POM is updated with the github information since the svn repo urls were 404s.
* POM now defines the source at 1.7 instead of 1.2, and is the only cause for the major version increment. The 2.0.0 release of this library is otherwise 100% backwards compatible with the older versions.
* Minor code quality changes have been made to the old files of the project.
* JFlex plugin now included in POM.
* JFlex will produce a lexing class from all lex files in src/main/lex.
* Javadocs are now produced when the jar goal is executed.
* Moved lex files from doc/ to src/main/lex.
* Deprecated the old json.lex in favor of jsonstrict.lex.
* Deprecated ContentHandler and doesn't have a 2.0 equivalent.
* Deprecated ContainerFactory and doesn't have a 2.0 equivalent.
* Deprecated ItemList and doesn't have a 2.0 equivalent.
* Deprecated JSONParse and JSONValue in favor of Jsoner.
* Deprecated JSONStreamAware and JSONAware in favor of Jsonable.
* Deprecated JSONObject in favor of JsonObject.
* Deprecated JSONArray in favor of JsonArray.
* Deprecated org.json.simple.parser.ParseException for org.json.simple.DeserializationException.
* Deprecated org.json.simple.parser.Yytoken for org.json.simple.Yytoken.
* Deprecated org.json.simple.parser.Yylex for org.json.simple.Yylex.
* Tests for deprecated classes have been reorganized and updated to ensure backwards compatibility is maintained throughout the 2.x release lifetime.
* Classes that have been deprecated still have shoddy javadocs but were updated to not produce errors and warnings during the build process.
* Classes introduced in the 2.0 release have substantial javadocs to help projects heathily update ASAP.
* The Jsonable interface allows others to define how their objects should be serialized in JSON.
* The new ParseException has a new problem type for disallowed tokens.
* The new ParseException now recommends recovery actions based on the problem that caused the DeserializationException in its message. All recovery scenarios are basically the same so ParseException is still the only json-simple exception class.
* The new Yytoken types are renamed.
* The new Yytoken is robustly constructed only allowing a null value when it is a null value in the DATUM tokens.
* Jsoner can escape strings provided to it to help with implementing the Jsonable interface.
* Jsoner can pretty print JSON strings provided to it for logging and basic display purposes.
* Jsoner can serialize data defined in the RFC 4627 specification and objects that implement the Jsonable interface. If data could be serialized multiple ways the deepest Jsonable implementation in the heiarchy is preferred. Any defined Jsonable implementation will be preferred before falling back to a default serialization.
* Jsoner can serialize an Enum that doesn't implement Jsonable.
* Jsoner will deserialize any numerical value as a BigDecimal.
* Jsoner can deserialize JsonArrays, JsonObjects, Strings, Numbers, Booleans, and null from strings provided to it.
* Jsoner can deserialize a JsonArray and exception out if any other value would be returned.
* Jsoner can deserialize a JsonObject and exception out if any other value would be returned.
* Jsoner can deserialize multiple JsonArrays, JsonObjects, Strings, Numbers, Booleans, and nulls from a single string provided to it.
* Jsoner deserialization (parsing) is thread safe.
* JsonArray is based on ArrayList<Object>. So it won't produce code warnings and can be used to construct a more convenient Collection.
* JsonArrays that are homogeneous can be cast and copied into a provided collection of the homogenous type.
* JsonArray contains gets for each allowed data type in JSON and convenience methods for Collections, Enums, and Maps. Note that they will throw ClassCastExceptions in such cases since it is still indictitive of a programmer's error.
* JsonObject is based on HashMap<String, Object>.
* JsonObject contains getTypeOrDefault for each allowed data type in JSON and convenience methods for Collections, Enums, and Maps. Note that they will throw ClassCastExceptions in such cases since it is still indictitive of a programmer's error.

Version 1.1.1 (2012/01/29)
* Supports OSGi
* Accepts a java.util.Map parameter in constructor of JSONObject

Version 1.1  (2009/01/23)
* Supports stoppable SAX-like content handler for streaming of JSON text
* Added JSONStreamAware to support streaming JSON text
* Added ContainerFactory to support creating arbitrary Map and List as JSON object and JSON array container during decoding
* Supports any Map and List as JSON object and JSON array container during encoding
* Added interface JSONAware
* Added ParseException to get detail error report while parsing 
* Added escaping for Unicode characters that cause problems for browser JS eval
 
Version 1.02 (2009/01/10)
* Updated json.lex to improve performance of the lexer
* Removed Rope.java and related junit test

Version 1.01 (2008/08/26)
* License changed to a more commerce friendly and clear one, Apache License 2.0
* Use JFlex to generate a faster Yylex.java
* Added Rope.java to get faster string operations
* Separate test codes from source codes
* Added ant build file build.xml

Version 1.0 (2006/04/15)
* Initial version

[CONTRIBUTORS]
Davin Loegering
Yidong Fang
Chris Nokleberg
Dave Hughes

[DEVELOPERS]
Generate json-simple project files for eclipse:
mvn eclipse:eclipse

Build the project:
mvn compile

Run unit tests:
mvn test

Build the project with javadocs and source:
mvn package

Make the signed artifacts available to other local projects:
mvn clean install

Guided deploy of project with signed artifacts:
mvn release:clean
mvn release:prepare
mvn release:perform
mvn release:clean

Update gh-pages:
1) Replace latest offical build's target/ directory to the gh-pages branch.
2) Update the index.html hrefs if necessary.
3) commit and push to gh-pages.

[LICENSE]
                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/

   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

   1. Definitions.

      "License" shall mean the terms and conditions for use, reproduction,
      and distribution as defined by Sections 1 through 9 of this document.

      "Licensor" shall mean the copyright owner or entity authorized by
      the copyright owner that is granting the License.

      "Legal Entity" shall mean the union of the acting entity and all
      other entities that control, are controlled by, or are under common
      control with that entity. For the purposes of this definition,
      "control" means (i) the power, direct or indirect, to cause the
      direction or management of such entity, whether by contract or
      otherwise, or (ii) ownership of fifty percent (50%) or more of the
      outstanding shares, or (iii) beneficial ownership of such entity.

      "You" (or "Your") shall mean an individual or Legal Entity
      exercising permissions granted by this License.

      "Source" form shall mean the preferred form for making modifications,
      including but not limited to software source code, documentation
      source, and configuration files.

      "Object" form shall mean any form resulting from mechanical
      transformation or translation of a Source form, including but
      not limited to compiled object code, generated documentation,
      and conversions to other media types.

      "Work" shall mean the work of authorship, whether in Source or
      Object form, made available under the License, as indicated by a
      copyright notice that is included in or attached to the work
      (an example is provided in the Appendix below).

      "Derivative Works" shall mean any work, whether in Source or Object
      form, that is based on (or derived from) the Work and for which the
      editorial revisions, annotations, elaborations, or other modifications
      represent, as a whole, an original work of authorship. For the purposes
      of this License, Derivative Works shall not include works that remain
      separable from, or merely link (or bind by name) to the interfaces of,
      the Work and Derivative Works thereof.

      "Contribution" shall mean any work of authorship, including
      the original version of the Work and any modifications or additions
      to that Work or Derivative Works thereof, that is intentionally
      submitted to Licensor for inclusion in the Work by the copyright owner
      or by an individual or Legal Entity authorized to submit on behalf of
      the copyright owner. For the purposes of this definition, "submitted"
      means any form of electronic, verbal, or written communication sent
      to the Licensor or its representatives, including but not limited to
      communication on electronic mailing lists, source code control systems,
      and issue tracking systems that are managed by, or on behalf of, the
      Licensor for the purpose of discussing and improving the Work, but
      excluding communication that is conspicuously marked or otherwise
      designated in writing by the copyright owner as "Not a Contribution."

      "Contributor" shall mean Licensor and any individual or Legal Entity
      on behalf of whom a Contribution has been received by Licensor and
      subsequently incorporated within the Work.

   2. Grant of Copyright License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      copyright license to reproduce, prepare Derivative Works of,
      publicly display, publicly perform, sublicense, and distribute the
      Work and such Derivative Works in Source or Object form.

   3. Grant of Patent License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      (except as stated in this section) patent license to make, have made,
      use, offer to sell, sell, import, and otherwise transfer the Work,
      where such license applies only to those patent claims licensable
      by such Contributor that are necessarily infringed by their
      Contribution(s) alone or by combination of their Contribution(s)
      with the Work to which such Contribution(s) was submitted. If You
      institute patent litigation against any entity (including a
      cross-claim or counterclaim in a lawsuit) alleging that the Work
      or a Contribution incorporated within the Work constitutes direct
      or contributory patent infringement, then any patent licenses
      granted to You under this License for that Work shall terminate
      as of the date such litigation is filed.

   4. Redistribution. You may reproduce and distribute copies of the
      Work or Derivative Works thereof in any medium, with or without
      modifications, and in Source or Object form, provided that You
      meet the following conditions:

      (a) You must give any other recipients of the Work or
          Derivative Works a copy of this License; and

      (b) You must cause any modified files to carry prominent notices
          stating that You changed the files; and

      (c) You must retain, in the Source form of any Derivative Works
          that You distribute, all copyright, patent, trademark, and
          attribution notices from the Source form of the Work,
          excluding those notices that do not pertain to any part of
          the Derivative Works; and

      (d) If the Work includes a "NOTICE" text file as part of its
          distribution, then any Derivative Works that You distribute must
          include a readable copy of the attribution notices contained
          within such NOTICE file, excluding those notices that do not
          pertain to any part of the Derivative Works, in at least one
          of the following places: within a NOTICE text file distributed
          as part of the Derivative Works; within the Source form or
          documentation, if provided along with the Derivative Works; or,
          within a display generated by the Derivative Works, if and
          wherever such third-party notices normally appear. The contents
          of the NOTICE file are for informational purposes only and
          do not modify the License. You may add Your own attribution
          notices within Derivative Works that You distribute, alongside
          or as an addendum to the NOTICE text from the Work, provided
          that such additional attribution notices cannot be construed
          as modifying the License.

      You may add Your own copyright statement to Your modifications and
      may provide additional or different license terms and conditions
      for use, reproduction, or distribution of Your modifications, or
      for any such Derivative Works as a whole, provided Your use,
      reproduction, and distribution of the Work otherwise complies with
      the conditions stated in this License.

   5. Submission of Contributions. Unless You explicitly state otherwise,
      any Contribution intentionally submitted for inclusion in the Work
      by You to the Licensor shall be under the terms and conditions of
      this License, without any additional terms or conditions.
      Notwithstanding the above, nothing herein shall supersede or modify
      the terms of any separate license agreement you may have executed
      with Licensor regarding such Contributions.

   6. Trademarks. This License does not grant permission to use the trade
      names, trademarks, service marks, or product names of the Licensor,
      except as required for reasonable and customary use in describing the
      origin of the Work and reproducing the content of the NOTICE file.

   7. Disclaimer of Warranty. Unless required by applicable law or
      agreed to in writing, Licensor provides the Work (and each
      Contributor provides its Contributions) on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
      implied, including, without limitation, any warranties or conditions
      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
      PARTICULAR PURPOSE. You are solely responsible for determining the
      appropriateness of using or redistributing the Work and assume any
      risks associated with Your exercise of permissions under this License.

   8. Limitation of Liability. In no event and under no legal theory,
      whether in tort (including negligence), contract, or otherwise,
      unless required by applicable law (such as deliberate and grossly
      negligent acts) or agreed to in writing, shall any Contributor be
      liable to You for damages, including any direct, indirect, special,
      incidental, or consequential damages of any character arising as a
      result of this License or out of the use or inability to use the
      Work (including but not limited to damages for loss of goodwill,
      work stoppage, computer failure or malfunction, or any and all
      other commercial damages or losses), even if such Contributor
      has been advised of the possibility of such damages.

   9. Accepting Warranty or Additional Liability. While redistributing
      the Work or Derivative Works thereof, You may choose to offer,
      and charge a fee for, acceptance of support, warranty, indemnity,
      or other liability obligations and/or rights consistent with this
      License. However, in accepting such obligations, You may act only
      on Your own behalf and on Your sole responsibility, not on behalf
      of any other Contributor, and only if You agree to indemnify,
      defend, and hold each Contributor harmless for any liability
      incurred by, or claims asserted against, such Contributor by reason
      of your accepting any such warranty or additional liability.

   END OF TERMS AND CONDITIONS

   APPENDIX: How to apply the Apache License to your work.

      To apply the Apache License to your work, attach the following
      boilerplate notice, with the fields enclosed by brackets "[]"
      replaced with your own identifying information. (Don't include
      the brackets!)  The text should be enclosed in the appropriate
      comment syntax for the file format. We also recommend that a
      file or class name and description of purpose be included on the
      same "printed page" as the copyright notice for easier
      identification within third-party archives.

   Copyright 2016 Clifton Labs

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

Please visit for the old version of this library:
https://github.com/fangyidong/json-simple
http://code.google.com/p/json-simple/
