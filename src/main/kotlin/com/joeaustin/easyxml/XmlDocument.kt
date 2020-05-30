package com.joeaustin.easyxml

import javax.xml.parsers.SAXParserFactory

class XmlDocument(children: List<XmlComponent>) {
    val root: XmlElement? by lazy { children.firstOrNull { child -> child is XmlElement } as? XmlElement }

    override fun toString(): String {
        return toString(XmlBuildOptions())
    }

    fun toString(buildOptions: XmlBuildOptions): String {
        val sb = StringBuilder()
        if (buildOptions.includeHeader) {
            val version = "version=${buildOptions.qualifierCharacter}1.0${buildOptions.qualifierCharacter}"
            val encoding = "encoding=${buildOptions.qualifierCharacter}UTF-8${buildOptions.qualifierCharacter}"
            val standalone = "standalone=${buildOptions.qualifierCharacter}yes${buildOptions.qualifierCharacter}"
            val header = "<?xml $version $encoding $standalone?>"

            if (buildOptions.pretty) {
                sb.appendln(header)
            } else {
                sb.append(header)
            }
        }

        root?.build(sb, "", buildOptions)

        return sb.toString()
    }

    class Builder {
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

    companion object {
        fun parse(text: String): XmlDocument? {
            text.byteInputStream().use { inputStream ->
                val parserFactory = SAXParserFactory.newInstance()
                val parser = parserFactory.newSAXParser()
                val handler = SaxParserHandler()

                return try {
                    parser.parse(inputStream, handler)
                    handler.getDocument()
                } catch (t: Throwable) {
                    null
                }
            }
        }
    }
}