import net.joeaustin.easyxml.XmlDocument
import net.joeaustin.easyxml.dsl.xmlDocument
import org.junit.Assert
import org.junit.Test

class AttributeTests {

    @Test
    fun testChildrenWithAttributes() {
        val rootName = "root"
        val childName = "child"
        val childCount = 3
        val attributeName = "attr"
        val attributeCount = 5

        val doc = xmlDocument {
            root(rootName) {
                for (i in 0 until childCount) {
                    child("$childName$i") {
                        for (j in 0 until attributeCount) {
                            attribute(attributeName + j, j.toString())
                        }
                    }
                }
            }
        }

        val docString = doc.toString()
        val parsedDoc = XmlDocument.parse(docString)
        val root = parsedDoc?.root

        Assert.assertNotNull("Parsed document should not be null", parsedDoc)
        Assert.assertNotNull("Parsed document root should not be null", root)
        Assert.assertEquals("Root should have $childCount children", childCount, root!!.getChildren().size)

        for (i in 0 until childCount) {
            val child = parsedDoc.root!!.getFirstChild(childName + i)
            Assert.assertNotNull("Child$i should not be null", child)
            Assert.assertEquals("Child should have $attributeCount attributes", attributeCount, child!!.attributes.size)
            for (j in 0 until attributeCount) {
                Assert.assertEquals(
                    "Attribute$j should have the value $j",
                    j.toString(),
                    child.getAttributeValue("$attributeName$j")
                )
            }
        }
    }
}