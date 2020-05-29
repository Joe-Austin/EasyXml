import com.joeaustin.easyxml.XmlAttribute
import com.joeaustin.easyxml.XmlBuildOptions
import com.joeaustin.easyxml.XmlDocument
import com.joeaustin.easyxml.XmlElement
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
}