package test1cortical;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.tika.exception.TikaException;

import org.apache.tika.language.LanguageIdentifier;

import org.apache.tika.metadata.Metadata;

import org.apache.tika.parser.AutoDetectParser;

import org.apache.tika.parser.ParseContext;

import org.apache.tika.parser.Parser;

import org.apache.tika.sax.BodyContentHandler;

import org.xml.sax.SAXException;

@SuppressWarnings("deprecation")

public class AutoDetection {

public static void main(final String[] args) throws IOException,

SAXException, TikaException {

Parser parser = new AutoDetectParser();

File folder = new File("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData");

File[] listOfFiles = folder.listFiles();

for (int i = 0; i < listOfFiles.length; i++) {

if (listOfFiles[i].isFile()) {

System.out.println("File: --> " + listOfFiles[i].getName());

String document=folder+"/"+listOfFiles[i].getName();

String extractedText=extractFromFile(parser, document);

try {
	File file = new File("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/temp/test.txt");
	FileWriter fileWriter = new FileWriter(file);
	fileWriter.write(extractedText);
	//fileWriter.write("a test");
	fileWriter.flush();
	fileWriter.close();
} catch (IOException e) {
	e.printStackTrace();
}


List<Set<String>> paragraphs = composeParagraphs("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/temp","test.txt");
System.out.println("Size paragraph --> "+paragraphs.size());
for (Set<String> temp : paragraphs) {
	System.out.println("number of the paragraph:  "+paragraphs.indexOf(temp)+" --> "+temp);
}
//" first paragraph : -->"+paragraphs.get(0));


//readFileAsList("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData","test.txt");

} else if (listOfFiles[i].isDirectory()) {

System.out.println("Directory " + listOfFiles[i].getName());

}

}

/*

System.out.println("------------ Parsing a PDF:");

extractFromFile(parser, "E:/Users/67elgo/various/data/somedata/Sales &
Purchase by Tender.pdf");

System.out.println("------------ Parsing an Office Document:");

extractFromFile(parser, "E:/Users/67elgo/various/data/somedata/Fixed
Term Agreeement.docx");

System.out.println("------------ Parsing a Spreadsheet:");

// extractFromFile(parser, "/demo.xlsx");

System.out.println("------------ Parsing a Presentation:");

//extractFromFile(parser, "/demo.odp");

System.out.println("------------ Parsing a PNG:");

// extractFromFile(parser, "/demo.png");

System.out.println("------------ Parsing a Video/AVI:");

// extractFromFile(parser, "/demo.avi");

System.out.println("------------ Parsing a MP3:");

// extractFromFile(parser, "/demo.mp3");

System.out.println("------------ Parsing a Java Class:");

// extractFromFile(parser,

// "/com/hascode/tutorial/ConcretePDFExtractor.class");

System.out.println("------------ Parsing a HTML File:");

// extractFromFile(parser, "/demo.html");

*/

}

private static  String extractFromFile(final Parser parser,

final String fileName) throws IOException, SAXException,

TikaException {

long start = System.currentTimeMillis();

BodyContentHandler handler = new BodyContentHandler(10000000);

Metadata metadata = new Metadata();

//InputStream content = autodetectionex.class

// .getResourceAsStream(fileName);

FileInputStream content = new FileInputStream(new File(fileName));

parser.parse(content, handler, metadata, new ParseContext());

//System.out.println("Content of the file: -->"+handler.toString());
//readFileAsList();

for (String name : metadata.names()) {

System.out.println(name + ":\t" + metadata.get(name));

}

//getting the content of the document

System.out.println("Language of the document :" +
identifyLanguage(handler.toString()));

System.out.println(String.format(

"------------ Processing took %s millis\n\n",

System.currentTimeMillis() - start));
return handler.toString();
}

public static String identifyLanguage(String text) {

LanguageIdentifier identifier = new LanguageIdentifier(text);

return identifier.getLanguage();

}

private static void readFileAsList(String folder, String doc) {
    //String fileName = "C:/Users/EloyGonzales/Downloads/Agreementtolease[TEXT].txt";
    Path wiki_path = Paths.get(folder, doc);

    Charset charset = Charset.forName("ISO-8859-1");
    
    try {
       // URI uri = this.getClass().getResource(fileName).toURI();
        List<String> lines = Files.readAllLines(wiki_path, charset);
               // Charset.defaultCharset());

        for (String line : lines) {
            System.out.println(line);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


private static final Pattern SPACE = Pattern.compile("\\s+");

public static Set<String>words(String input) {
    return Arrays.stream(SPACE.split(input))
          .filter(word -> !word.isEmpty())
          .collect(Collectors.toSet());
}

private static List<Set<String>> composeParagraphs(String folder,String doc) throws IOException {
    Set<String> para = new HashSet<>();
    Charset charset = Charset.forName("ISO-8859-1");
    Path path = Paths.get(folder, doc);

    List<Set<String>> contents = new ArrayList<>();
    for (String line : Files.readAllLines(path,charset)) {
        if (line.trim().isEmpty()) {
            // indicates a new paragraph....
            if (!para.isEmpty()) {
                contents.add(para);
                para = new HashSet<>();
            }
        } else {
            para.addAll(words(line));
        }
    }
    if (!para.isEmpty()) {
        contents.add(para);
    }
    return contents;
}


}


