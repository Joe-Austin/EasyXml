package net.joeaustin.easyxml

data class XmlBuildOptions(
    val qualifierCharacter: Char = '\"',
    val pretty: Boolean = true,
    val includeComments: Boolean = true,
    val includeHeader: Boolean = true
)