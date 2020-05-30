package com.joeaustin.easyxml

class XmlAttribute(val name: String, val value: String) : BaseXmlComponent() {
    override fun build(sb: StringBuilder, currentPadding: String, buildOptions: XmlBuildOptions) {
        sb.append(" ")
        sb.append(normalizeKey(name))
        sb.append("=")
        sb.append(buildOptions.qualifierCharacter)
        sb.append(escapeText(value))
        sb.append(buildOptions.qualifierCharacter)
    }
}