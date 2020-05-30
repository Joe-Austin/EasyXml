# Easy Xml
A Kotlin-DSL which makes writing and parsing simple xml very easy for both Java and Android applications.

## Installation

- [ ] Publish to maven

## Authoring

Dsl:
```kotlin
xmlDocument {
            root("Employees"){
                comment("Employee")
                child("Employee") {
                    attributes(
                        "IsActive" to "true",
                        "IsManager" to "false"
                    )
                    child("FirstName") {
                        text("John")
                    }
                    child("LastName"){
                        text("Doe")
                    }
                }

                comment("Manager")
                child("Employee") {
                    attributes(
                        "IsActive" to "true",
                        "IsManager" to "true"
                    )

                    child("FirstName") {
                        text("Ann")
                    }
                    child("LastName"){
                        text("Fakerson")
                    }
                }
            }
        }
```

produces this xml:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Employees>
    <!--Employee-->
    <Employee IsActive="true" IsManager="false">
        <FirstName>John</FirstName>
        <LastName>Doe</LastName>
    </Employee>
    <!--Manager-->
    <Employee IsActive="true" IsManager="true">
        <FirstName>Ann</FirstName>
        <LastName>Fakerson</LastName>
    </Employee>
</Employees>
```

## Parsing

To parse a document:
```kotlin
val doc = XmlDocument.parse(xmlText)
```

## Limitations
This is currently fairly limited. It's intended for simple xml and doesn't currently support things like namespaces or CData. If there is a need and/or desire, additional features may be considered in the future.

License (MIT)
=======
Copyright 2020 Joe Austin

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
