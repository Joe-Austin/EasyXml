package net.joeaustin.easyxml

interface XmlComponent {
    fun build(sb: StringBuilder, currentPadding: String, buildOptions: XmlBuildOptions)
}