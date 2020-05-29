package com.joeaustin.easyxml

abstract class BaseXmlComponent : XmlComponent {

    protected fun escapeText(text: String): String {
        var escapedText = text

        escapedText = escapedText.replace("&", "&#38;")
        escapedText = escapedText.replace("'", "&#39;")
        escapedText = escapedText.replace("\"", "&#34;")
        escapedText = escapedText.replace("<", "&lt;")
        escapedText = escapedText.replace(">", "&gt;")

        return escapedText
    }

}