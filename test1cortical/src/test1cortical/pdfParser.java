package test1cortical;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import org.apache.tika.exception.TikaException;

import org.apache.tika.language.LanguageIdentifier;

import org.apache.tika.metadata.Metadata;

import org.apache.tika.parser.AutoDetectParser;

import org.apache.tika.parser.ParseContext;

import org.apache.tika.parser.pdf.PDFParser;

import org.apache.tika.sax.BodyContentHandler;

import org.xml.sax.SAXException;

@SuppressWarnings("deprecation")

public class pdfParser {

public static void main(final String[] args) throws
IOException,TikaException, SAXException {

BodyContentHandler handler = new BodyContentHandler();

Metadata metadata = new Metadata();

FileInputStream inputstream = new FileInputStream(new
File("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/Sales & Purchase by Tender.pdf"));

ParseContext pcontext = new ParseContext();

//parsing the document using PDF parser

PDFParser pdfparser = new PDFParser();

pdfparser.parse(inputstream, handler, metadata,pcontext);

//getting the content of the document

System.out.println("Contents of the PDF :" + handler.toString());

//getting the content of the document

System.out.println("Languge of the PDF :" +
identifyLanguage(handler.toString()));

//getting metadata of the document

System.out.println("Metadata of the PDF:");

String[] metadataNames = metadata.names();

for(String name : metadataNames) {

System.out.println(name+ " : " + metadata.get(name));

}

}

public static String identifyLanguage(String text) {

LanguageIdentifier identifier = new LanguageIdentifier(text);

return identifier.getLanguage();

}

/*

public String parseExample() throws IOException, SAXException, TikaException {

AutoDetectParser parser = new AutoDetectParser();

BodyContentHandler handler = new BodyContentHandler();

Metadata metadata = new Metadata();

try (InputStream stream =
ParsingExample.class.getResourceAsStream("test.doc")) {

parser.parse(stream, handler, metadata);

return handler.toString();

}

}

*/

}




