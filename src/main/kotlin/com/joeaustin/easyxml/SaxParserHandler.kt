package com.joeaustin.easyxml

import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.Locator
import org.xml.sax.SAXParseException
import org.xml.sax.helpers.DefaultHandler


internal class SaxParserHandler : DefaultHandler() {

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)
    }

    override fun processingInstruction(target: String?, data: String?) {
        super.processingInstruction(target, data)
    }

    override fun startPrefixMapping(prefix: String?, uri: String?) {
        super.startPrefixMapping(prefix, uri)
    }

    override fun ignorableWhitespace(ch: CharArray?, start: Int, length: Int) {
        super.ignorableWhitespace(ch, start, length)
    }

    override fun notationDecl(name: String?, publicId: String?, systemId: String?) {
        super.notationDecl(name, publicId, systemId)
    }

    override fun error(e: SAXParseException?) {
        super.error(e)
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
    }

    override fun endDocument() {
        super.endDocument()
    }

    override fun resolveEntity(publicId: String?, systemId: String?): InputSource {
        return super.resolveEntity(publicId, systemId)
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        super.startElement(uri, localName, qName, attributes)
    }

    override fun skippedEntity(name: String?) {
        super.skippedEntity(name)
    }

    override fun unparsedEntityDecl(name: String?, publicId: String?, systemId: String?, notationName: String?) {
        super.unparsedEntityDecl(name, publicId, systemId, notationName)
    }

    override fun endPrefixMapping(prefix: String?) {
        super.endPrefixMapping(prefix)
    }

    override fun startDocument() {
        super.startDocument()
    }

    override fun fatalError(e: SAXParseException?) {
        super.fatalError(e)
    }
}