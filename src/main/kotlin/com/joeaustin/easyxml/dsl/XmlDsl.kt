package com.joeaustin.easyxml.dsl

import com.joeaustin.easyxml.XmlAttribute
import com.joeaustin.easyxml.XmlDocument
import com.joeaustin.easyxml.XmlElement

fun xmlDocument(init: DocumentDsl.() -> Unit): XmlDocument {
    return DocumentDsl().also(init).build()
}

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

    fun build(): XmlElement {
        return elementBuilder.build()
    }
}