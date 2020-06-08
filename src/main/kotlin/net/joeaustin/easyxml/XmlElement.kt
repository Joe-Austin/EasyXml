package net.joeaustin.easyxml

private const val PADDING = "    "

class XmlElement(
    val name: String,
    val innerText: String,
    val attributes: List<XmlAttribute>,
    val subComponents: List<XmlComponent>
) : BaseXmlComponent() {

    operator fun get(childName: String): List<XmlElement> {
        return getChildren(childName)
    }

    fun getChildren(name: String, ignoreCase: Boolean = false): List<XmlElement> {
        return subComponents
            .filter { child ->
                child is XmlElement && child.name.equals(name, ignoreCase)
            }
            .mapNotNull { child -> child as? XmlElement }
    }

    fun getChildren(): List<XmlElement> {
        return subComponents
            .mapNotNull { component -> component as? XmlElement }
    }

    fun getFirstChild(name: String, ignoreCase: Boolean = false): XmlElement? {
        return subComponents
            .firstOrNull { c -> (c as? XmlElement)?.name.equals(name, ignoreCase) } as? XmlElement
    }

    fun getAttributeValue(name: String, ignoreCase: Boolean = false): String? {
        return attributes.firstOrNull { attr ->
            attr.name.equals(name, ignoreCase)
        }?.value
    }

    override fun build(sb: StringBuilder, currentPadding: String, buildOptions: XmlBuildOptions) {
        val normalizedName = normalizeKey(name)
        sb.append(currentPadding)
        sb.append("<")
        sb.append(normalizedName)

        attributes.forEach { attr ->
            attr.build(sb, "", buildOptions)
        }

        if (innerText.isNotEmpty()) {
            sb.append(">")
            sb.append(escapeText(innerText))
        } else if (subComponents.isNotEmpty()) {
            if (buildOptions.pretty) {
                sb.appendln(">")
            } else {
                sb.append(">")
            }

            val subPadding = if (buildOptions.pretty) currentPadding + PADDING else ""

            subComponents.forEach { child ->
                child.build(sb, subPadding, buildOptions)
            }
        }

        if (innerText.isEmpty() && subComponents.isEmpty()) {
            if (buildOptions.pretty) {
                sb.appendln("/>")
            } else {
                sb.append("/>")
            }
        } else {
            //If we're doing pretty print and there aren't children, the closing tag should be
            //on a new line indented to match the start tag
            if (buildOptions.pretty && subComponents.isNotEmpty()) {
                sb.append(currentPadding)
            }
            sb.append("</")
            sb.append(normalizedName)
            if (buildOptions.pretty) {
                sb.appendln(">")
            } else {
                sb.append(">")
            }
        }

    }

    class Builder(val name: String) {
        private val attributes = ArrayList<XmlAttribute>()
        private val children = ArrayList<XmlComponent>()
        private var innerText = ""

        fun addInnerText(text: String) {
            innerText = text
        }

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
}