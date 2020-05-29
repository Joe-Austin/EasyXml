package com.joeaustin.easyxml

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
}