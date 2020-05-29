package com.joeaustin.easyxml

private const val PADDING = "    "

class XmlElement(
    val name: String,
    val innerText: String,
    val attributes: List<XmlAttribute>,
    val children: List<XmlComponent>
) : BaseXmlComponent() {

    fun get(childName: String): List<XmlElement> {
        return getChildrenElements(childName)
    }

    fun getChildrenElements(name: String, ignoreCase: Boolean = false): List<XmlElement> {
        return children
            .filter { child ->
                child is XmlElement && child.name.equals(name, ignoreCase)
            }
            .mapNotNull { child -> child as? XmlElement }
    }

    fun getAttributeValue(name: String, ignoreCase: Boolean = false): String? {
        return attributes.firstOrNull { attr ->
            attr.name.equals(name, ignoreCase)
        }?.value
    }

    override fun build(sb: StringBuilder, currentPadding: String, buildOptions: XmlBuildOptions) {
        sb.append(currentPadding)
        sb.append("<")
        sb.append(name)

        attributes.forEach { attr ->
            attr.build(sb, "", buildOptions)
        }

        if (innerText.isNotEmpty()) {
            sb.append(">")
            sb.append(escapeText(innerText))
        } else if (children.isNotEmpty()) {
            if (buildOptions.pretty) {
                sb.appendln(">")
            } else {
                sb.append(">")
            }

            val subPadding = if (buildOptions.pretty) currentPadding + PADDING else ""

            children.forEach { child ->
                child.build(sb, subPadding, buildOptions)
            }
        }

        if (innerText.isEmpty() && children.isEmpty()) {
            sb.append("/>")
        } else {
            sb.append("</")
            sb.append(name)
            if (buildOptions.pretty) {
                sb.appendln(">")
            } else {
                sb.append(">")
            }
        }

    }
}