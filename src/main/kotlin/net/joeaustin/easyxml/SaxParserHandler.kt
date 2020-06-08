package net.joeaustin.easyxml

import org.xml.sax.Attributes
import org.xml.sax.SAXParseException
import org.xml.sax.helpers.DefaultHandler
import java.util.*


internal class SaxParserHandler : DefaultHandler() {

    //region Fields and Properties

    private val elementStack = Stack<XmlElement.Builder>()
    private val sb = StringBuilder()
    private var docBuilder = XmlDocument.Builder()
    private var encounteredError = false

    //endregion Fields and Properties

    fun getDocument(): XmlDocument? {
        return if (!encounteredError && elementStack.size == 1) {
            val root = elementStack.pop().build()
            XmlDocument(listOf(root))
        } else {
            null
        }
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        if (qName != null) {
            sb.setLength(0)
            val newBuilder = XmlElement.Builder(qName)

            if (attributes != null) {
                for (i in 0 until attributes.length) {
                    val name = attributes.getQName(i)
                    val value = attributes.getValue(i)
                    newBuilder.addAttribute(XmlAttribute(name, value))
                }
            }

            elementStack.push(newBuilder)
        }
    }



    override fun endElement(uri: String?, localName: String?, qName: String?) {
        if (sb.isNotEmpty()) {
            val innerText = sb.toString().trimEnd()
            elementStack.peek().addInnerText(innerText)

            sb.setLength(0)
        }

        if (elementStack.size > 1) {
            val currentElement = elementStack.pop().build()
            elementStack.peek().addXmlElement(currentElement)
        }
    }

    override fun error(e: SAXParseException?) {
        encounteredError = true
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        sb.append(ch, start, length)
    }


    override fun startDocument() {
        sb.setLength(0)
        encounteredError = false
        docBuilder = XmlDocument.Builder()
        elementStack.clear()
    }

    override fun fatalError(e: SAXParseException?) {
        encounteredError = true
    }
}