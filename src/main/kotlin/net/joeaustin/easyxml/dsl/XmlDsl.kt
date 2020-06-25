package net.joeaustin.easyxml.dsl

import net.joeaustin.easyxml.XmlAttribute
import net.joeaustin.easyxml.XmlDocument
import net.joeaustin.easyxml.XmlElement

@DslMarker
private annotation class XmlDsl

fun xmlDocument(init: DocumentDsl.() -> Unit): XmlDocument {
    return DocumentDsl().also(init).build()
}

fun xmlElement(name: String, init: ElementDsl.() -> Unit): XmlElement {
    return ElementDsl(name).also(init).build()
}

@XmlDsl
class DocumentDsl {
    private val builder = XmlDocument.Builder()

    fun comment(text: String) {
        builder.addComment(text)
    }

    fun root(name: String, init: ElementDsl.() -> Unit = {}) {
        builder.setRoot(ElementDsl(name).also(init).build())
    }

    fun build(): XmlDocument {
        return builder.build()
    }

}

@XmlDsl
class ElementDsl(name: String) {
    private val elementBuilder = XmlElement.Builder(name)

    fun attribute(name: String, value: String) {
        elementBuilder.addAttribute(XmlAttribute(name, value))
    }

    fun attributes(vararg attributes: Pair<String, String>) {
        attributes.forEach { (name, value) -> attribute(name, value) }
    }

    fun comment(text: String) {
        elementBuilder.addComment(text)
    }

    fun text(text: String) {
        elementBuilder.addInnerText(text)
    }

    fun child(name: String, init: ElementDsl.() -> Unit = {}) {
        elementBuilder.addXmlElement(ElementDsl(name).also(init).build())
    }

    fun child(element: XmlElement) {
        elementBuilder.addXmlElement(element)
    }

    fun build(): XmlElement {
        return elementBuilder.build()
    }
}
