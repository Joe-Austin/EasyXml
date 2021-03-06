package net.joeaustin.easyxml

import javax.xml.parsers.SAXParserFactory

/**
 * XmlDocument
 *
 * @param subComponents will be comments and a single root element (everything after the first root will be ignored)
 */
class XmlDocument(val subComponents: List<XmlComponent>) {
    val root: XmlElement? by lazy { subComponents.firstOrNull { child -> child is XmlElement } as? XmlElement }

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

        for (component in subComponents) {
            component.build(sb, "", buildOptions)
            if (component is XmlElement) {
                break
            }
        }

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
        /**
         * Tries to parse an XmlDocument from text.
         * @return An [XmlDocument] if the parse is successful, otherwise null.
         */
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