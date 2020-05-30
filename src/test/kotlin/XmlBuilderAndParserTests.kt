import com.joeaustin.easyxml.XmlBuildOptions
import com.joeaustin.easyxml.XmlDocument
import com.joeaustin.easyxml.dsl.xmlDocument
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.min

class XmlBuilderAndParserTests {
    @Test
    fun testSimpleXmlDocumentCreation() {
        val rootName = "root"
        val childName = "child"
        val childCount = 3

        val doc = xmlDocument {
            root(rootName) {
                for (i in 0 until childCount) {
                    child("$childName$i")
                }
            }
        }

        val docString = doc.toString()
        val parsedDoc = XmlDocument.parse(docString)
        val root = parsedDoc?.root

        assertNotNull("Parsed document should not be null", parsedDoc)
        assertNotNull("Parsed document root should not be null", root)
        assertEquals("Root should have $childCount children", childCount, root!!.getChildren().size)
    }

    @Test
    fun testInvalidDocumentParsing() {
        val invalidXml = "<xml that's invalid"
        val doc = XmlDocument.parse(invalidXml)

        assertNull("Doc should be null", doc)
    }

    @Test
    fun testSpecialChars () {
        val text = "<>&\"'"
        val doc = xmlDocument {
            root("root"){
                text(text)
            }
        }

        val parsedDoc = XmlDocument.parse(doc.toString())
        val rootText = parsedDoc!!.root!!.innerText

        assertEquals(text, rootText)
    }

    @Test
    fun testComplicatedDocumentParsing() {
        val doc = xmlDocument {
            comment("Comment Before Root")
            root("root") {
                comment("First Comment Before First Child")
                child("FirstChild") {
                    comment("First Comment Before First Sub-Child")
                    child("FirstChildFirstSubChild") {
                        attributes("Att1" to "1", "Att2" to "2")
                        comment("First Comment Before Final Nested Child")
                        child("AnotherNestedChild") {
                            text("Nested Child")
                        }
                    }
                }

                child("SecondChild") {
                    attribute("Attr", "Attr")
                }

                child("ThirdChild") {
                    text("Third child With Special Chars <>&'\"")
                }
            }
            comment("Comment After Root")
        }

        val prettyXml = doc.toString()
        val miniXml = doc.toString(XmlBuildOptions(pretty = false))

        val parsedFromPretty = XmlDocument.parse(prettyXml)
        val parsedFromMini = XmlDocument.parse(miniXml)

        assertNotNull("Parsed from pretty should not be null", parsedFromPretty)
        assertNotNull("Parsed from mini should not be null", parsedFromMini)

        val prettyRoot = parsedFromPretty!!.root!!
        val miniRoot = parsedFromMini!!.root!!

        assertEquals("Pretty root should have 3 children", 3, prettyRoot.getChildren().size)
        assertEquals("Mini root should have 3 children", 3, miniRoot.getChildren().size)

        val prettyFullyNestedChild = prettyRoot.getChildren().first().getChildren().first().getChildren().first()
        val miniFullyNestedChild = prettyRoot.getChildren().first().getChildren().first().getChildren().first()

        assertEquals(prettyFullyNestedChild.name, miniFullyNestedChild.name)

        println(prettyXml)

    }


}