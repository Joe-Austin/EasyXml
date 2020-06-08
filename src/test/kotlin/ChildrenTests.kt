import net.joeaustin.easyxml.dsl.xmlDocument
import org.junit.Assert
import org.junit.Test

class ChildrenTests {
    @Test
    fun testGetNonExistentChild() {
        val doc = xmlDocument {
            root("root") {
                child("Child")
            }
        }

        val docRoot = doc.root!!
        val missingChild = docRoot.getFirstChild("DoesNotExist")
        Assert.assertNull("Child should not exist", missingChild)
    }

    @Test
    fun testGetMultipleChildren() {
        val childCount = 3
        val childName = "Child"

        val doc = xmlDocument {
            root("root") {
                for (i in 0 until childCount) {
                    child(childName)
                }
            }
        }

        val root = doc.root!!
        val children = root[childName]

        Assert.assertEquals("Should have gotten $childCount children", childCount, children.size)
    }
}