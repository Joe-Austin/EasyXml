package com.joeaustin.easyxml

class XmlComment(val text: String) : XmlComponent {
    override fun build(sb: StringBuilder, currentPadding: String, buildOptions: XmlBuildOptions) {
        if (buildOptions.includeComments) {
            sb.append(currentPadding)
            sb.append("<!--")
            sb.append(text)

            if (buildOptions.pretty) {
                sb.appendln("-->")
            } else {
                sb.append("-->")
            }

        }
    }
}