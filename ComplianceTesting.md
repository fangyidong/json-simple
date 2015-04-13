#### Introduction ####

JSON.simple uses [json-test-suite](http://code.google.com/p/json-test-suite/) to do compliance testing. Please follow [The mapping between JSON and Java](MappingBetweenJSONAndJavaEntities.md) to get the result compliant to JSON spec. Please note that interface JSONAware and JSONStreamAware is to provide the flexibility and freedom to the user so that he can render the result in any way and make any extensions (such as comments that are not supported by JSON spec), so the user should know what he's doing when he implements these interfaces.


#### Steps ####
  1. Download the [latest json-test-suite package](http://code.google.com/p/json-test-suite/downloads/list) from the home page of json-test-suite
  1. Unzip and run the following command in the directory of the package:
```
   ant -f test.xml compliance
```
  1. You may need to wait for a few minutes and then check the results from the following files:
```
   TEST-org.json.simple.compliance.EncoderTest.txt
   TEST-org.json.simple.compliance.DecoderTest.txt
```

#### Results ####
Both of the encoder and decoder have passed tens of thousands randomly generated samples. No errors found so far. In real world applications, JSON.simple has been used in [quite a few projects](http://code.google.com/p/json-simple/#JSON.simple_in_Projects) in the past years.
| **Module** | **Result** |
|:-----------|:-----------|
| Encoder | 100,000+ samples passed, no errors found. |
| Streaming Encoder | 100,000+ samples passed, no errors found. |
| Decoder | 100,000+ samples passed, no errors found. |
| Decoder with SAX-like content handler | 100,000+ samples passed, no errors found. |

#### How does it work? ####
TBD