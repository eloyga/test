package test1cortical;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.apache.poi.hsmf.extractor.OutlookTextExtactor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

@SuppressWarnings("deprecation")

public class MetaDataExtractionAutoDetection {

//private static final Pattern SPACE = Pattern.compile("\\s+");

public static void main(final String[] args) throws IOException,

SAXException, TikaException {

Parser parser = new AutoDetectParser();

long start = System.currentTimeMillis();

//int countdocprocessed=0;

//File folder = new File("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/");

//File[] listOfFiles = folder.listFiles();

// List<File> mylist =listf("E:/Users/67elgo/various/data");

/*

File[] txts = folder.listFiles(

(dir, name) -> {

return name.toLowerCase().endsWith(".json");

}

);

*/

//File dir = new File("C:/Users/EloyGonzales/Documents/Domitila Technologies/");
File dir = new File("C:/Users/EloyGonzales/Downloads/");


//"C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/"

String[] extensions = new String[] { "msg", "pdf", "doc", "docx"};

System.out.println("Getting all .txt and .jsp files in " + dir.getCanonicalPath() + " including those in subdirectories");

List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);

System.out.println("Number of filtered files: --> "+files.size());

int c=0;
int np=0;


for (File file : files) {

System.out.println("file: " + file.getCanonicalPath());

try{
extractFromFile(parser, file.getCanonicalPath(),c);

c++;

} catch(Exception e){
	System.out.println ("Cannot process [encrypted file] -->"+ file);
	np++;

}
}

//File dir = new File("E:/Users/67elgo/various/");

//File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

//File[] pdffiles= folder.listFiles(pdfFilter);

System.out.println("Selected files -->"+files.size());

//List<File> mylist =listf("E:/Users/67elgo/various/data");

//System.out.println("Total files in directories: "+mylist.size());

//File folder2= new File("E:/Users/67elgo/various/data");

//displayDirectoryContents(folder2);
/*
for (int i = 0; i < listOfFiles.length; i++) {

if (listOfFiles[i].isFile()) {

System.out.println("File: --> " + listOfFiles[i].getName());

String document=folder+"/"+listOfFiles[i].getName();

System.out.println ("Number of documents processed: ==> "+(i+1));

countdocprocessed++;

// extractFromFile(parser, document, i);

} else if (listOfFiles[i].isDirectory()) {

System.out.println("Directory " + listOfFiles[i].getName());

}

}
*/

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

/*

try (FileWriter file=new
FileWriter("E:/Users/67elgo/various/data/JsonResult.json")) {

file.write(json.toJSONString());

System.out.println("successfully copied JSON objet to file");

System.out.println("\nJSON Object:"+ json);

}

*/

System.out.println("TOTAL number of documents processed : "+ c+" NOT processed docs: "+np);

System.out.println(String.format(

"------------ TOTAL Processing took %s millis\n\n",

System.currentTimeMillis() - start));

}

@SuppressWarnings("unchecked")

private static void extractFromFile(final Parser parser,

final String fileName, int i) throws IOException, SAXException,

TikaException {

long start = System.currentTimeMillis();

BodyContentHandler handler = new BodyContentHandler(10000000);

Metadata metadata = new Metadata();

//InputStream content = autodetectionex.class

// .getResourceAsStream(fileName);

FileInputStream content = new FileInputStream(new File(fileName));

parser.parse(content, handler, metadata, new ParseContext());

//System.out.println("Content of the file: -->"+handler.toString());

JSONObject json= new JSONObject();

json.put("File Name:", fileName);

JSONArray extractedmetadata = new JSONArray();

for (String name : metadata.names()) {

System.out.println(name + ":\t" + metadata.get(name));

extractedmetadata.add(name+" --> "+metadata.get(name));

}

//getting the content of the document

System.out.println("Language of the document :" +
identifyLanguage(handler.toString()));

extractedmetadata.add("Language of the document --> "+identifyLanguage(handler.toString()));

System.out.println(String.format(

"------------ Processing took %s millis\n\n",

System.currentTimeMillis() - start));

json.put("Metadata Extracted", extractedmetadata);

writeJson(json, i);

}

public static String identifyLanguage(String text) {

LanguageIdentifier identifier = new LanguageIdentifier(text);

return identifier.getLanguage();

}

public static void writeJson (JSONObject json,int i) throws IOException{

try (FileWriter file=new
FileWriter("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/"+"JsonResult"+i+".json")) {

file.write(json.toJSONString());

System.out.println("successfully copied JSON objet to file");

System.out.println("\nJSON Object:"+ json);

}

}

/*
public static List<File> listf(String directoryName) {

File directory = new File(directoryName);

List<File> resultList = new ArrayList<File>();

// get all the files from a directory

File[] fList = directory.listFiles();

/*

File[] fList = directory.listFiles(

(dir, name) -> {

return name.toLowerCase().endsWith(".json");

}

);



//File[] fList2 = FileFilter();

resultList.addAll(Arrays.asList(fList));

for (File file : fList) {

if (file.isFile()) {

System.out.println(file.getAbsolutePath());

} else if (file.isDirectory()) {

resultList.addAll(listf(file.getAbsolutePath()));

}

}

//System.out.println(fList);

return resultList;

}
*/

/*
public static void displayDirectoryContents(File directory) {

try {

//File[] files = directory.listFiles();

File[] files = directory.listFiles(

(dir, name) -> {

return name.toLowerCase().endsWith(".json");

}

);

for (File file : files) {

if (file.isDirectory()) {

System.out.println("directory:" + file.getCanonicalPath());

displayDirectoryContents(file);

} else {

System.out.println(" file:" + file.getCanonicalPath());

}

}

} catch (IOException e) {

e.printStackTrace();

}

}
*/

/*
static FileFilter pdfFilter = new FileFilter() {

@Override

public boolean accept(File file) {

if (file.isDirectory()) {

return true; // return directories for recursion

}

return file.getName().endsWith(".pdf"); // return .url files

}

};
*/

/*
private static int superSetParagraph(List<Set<String>> paras) {

// get all words from all paragraphs.

Set<String> allwords = paras.stream().flatMap(p ->
p.stream()).collect(Collectors.toSet());

// which paragraph has all words.

for (int i = 0; i < paras.size(); i++) {

if (paras.get(i).size() == allwords.size()) {

return i + 1;

}

}

return 0;

}
*/
/*
public static Set<String>words(String input) {

return Arrays.stream(SPACE.split(input))

.filter(word -> !word.isEmpty())

.collect(Collectors.toSet());

}

private static List<Set<String>> composeParagraphs(Path filepath)
throws IOException {

Set<String> para = new HashSet<>();

List<Set<String>> contents = new ArrayList<>();

for (String line : Files.readAllLines(filepath)) {

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
*/

}

