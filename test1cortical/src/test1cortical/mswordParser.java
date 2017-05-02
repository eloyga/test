package test1cortical;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import org.apache.tika.exception.TikaException;

import org.apache.tika.metadata.Metadata;

import org.apache.tika.parser.ParseContext;

import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;

import org.apache.tika.sax.BodyContentHandler;

import org.apache.tika.language.*;



import org.xml.sax.SAXException;

public class mswordParser {

@SuppressWarnings("deprecation")

public static void main(final String[] args) throws IOException,
TikaException, SAXException {

//detecting the file type

BodyContentHandler handler = new BodyContentHandler();

Metadata metadata = new Metadata();

FileInputStream inputstream = new FileInputStream(new
File("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/Fixed Term Agreeementv2.docx"));

ParseContext pcontext = new ParseContext();

//OOXml parser

OOXMLParser msofficeparser = new OOXMLParser ();

msofficeparser.parse(inputstream, handler, metadata,pcontext);

System.out.println("Contents of the document:" + handler.toString());

LanguageIdentifier object=new LanguageIdentifier(handler.toString());

System.out.println("languaje detected in the text :"+ object.getLanguage());

System.out.println("Metadata of the document:");

String[] metadataNames = metadata.names();

for(String name : metadataNames) {

System.out.println(name + ": " + metadata.get(name));

}

}

}


