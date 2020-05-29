package com.joeaustin.easyxml.builders

import com.joeaustin.easyxml.XmlComment
import com.joeaustin.easyxml.XmlComponent
import com.joeaustin.easyxml.XmlDocument
import com.joeaustin.easyxml.XmlElement

class XmlDocumentBuilder {
    private val children = ArrayList<XmlComponent>()

    fun setRoot(root: XmlElement) {
        children.removeIf { child -> child is XmlElement }
        children.add(root)
    }

    fun addComment(comment: XmlComment) {
        children.add(comment)
    }

    fun addComment(commentText: String) {
        addComment(XmlComment(commentText))
    }

    fun build(): XmlDocument {
        return XmlDocument(children)
    }
}