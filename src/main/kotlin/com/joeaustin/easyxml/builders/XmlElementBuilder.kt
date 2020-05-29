package com.joeaustin.easyxml.builders

import com.joeaustin.easyxml.XmlAttribute
import com.joeaustin.easyxml.XmlComment
import com.joeaustin.easyxml.XmlComponent
import com.joeaustin.easyxml.XmlElement

class XmlElementBuilder(val name: String) {
    private val attributes = ArrayList<XmlAttribute>()
    private val children = ArrayList<XmlComponent>()
    private var innerText = ""

    fun addAttribute(attr: XmlAttribute) {
        attributes.add(attr)
    }

    fun addXmlElement(element: XmlElement) {
        children.add(element)
    }

    fun addComment(comment: XmlComment) {
        children.add(comment)
    }

    fun addComment(commentText: String) {
        addComment(XmlComment(commentText))
    }

    fun build(): XmlElement {
        return XmlElement(name, innerText, attributes, children)
    }
}