import com.joeaustin.easyxml.XmlAttribute
import com.joeaustin.easyxml.XmlBuildOptions
import com.joeaustin.easyxml.XmlDocument
import com.joeaustin.easyxml.XmlElement
import com.joeaustin.easyxml.dsl.xmlDocument
import org.junit.Test

class XmlDocumetTests {
    @Test
    fun testXmlDocumentCreation() {
        val child = XmlElement("book", "Where the wind blows", emptyList(), emptyList())
        val child2 = XmlElement(
            "book", "Where the \"wind\" blows",
            listOf(XmlAttribute("IsPublished", "True")),
            emptyList()
        )
        val books = XmlElement("books", "", emptyList(), listOf(child, child2))
        val doc = XmlDocument(listOf(books))

        println(doc.toString(XmlBuildOptions(qualifierCharacter = '\'', pretty = true)))
    }

    @Test
    fun textXmlDocumentDsl() {
        val doc = xmlDocument {
            root("books") {
                comment("Book Title")
                child("book") {
                    text("Where the wind blows")
                }
                child("book") {
                    attributes(
                        "IsPublished" to "true",
                        "ISBN" to "ISBN #1234"
                    )
                    text("Where the \"wind\" blows")
                }
                child("book")
            }
        }

        println(doc.toString())
    }

    @Test
    fun testParser() {
        val xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<books>\n" +
                "    <!--Book Title-->\n" +
                "    <book>Where the wind blows</book>\n" +
                "    <book IsPublished=\"true\" ISBN=\"ISBN #1234\">Where the &#34;wind&#34; blows</book>\n" +
                "    <book/>\n" +
                "</books>"

        val doc = XmlDocument.parse(xml)
        println(doc)
    }
}