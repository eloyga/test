package test1cortical;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
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

public class MetaDataExtractionMSG {

//private static final Pattern SPACE = Pattern.compile("\\s+");

public static void main(final String[] args) throws IOException,

SAXException, TikaException {

Parser parser = new AutoDetectParser();

long start = System.currentTimeMillis();

//File dir = new File("C:/Users/EloyGonzales/Documents/Domitila Technologies/");
File dir = new File("C:/Users/EloyGonzales/Downloads/");
// READ THE DIRECTORY WHERE THE FILES TO BE ANALYZED ARE

//"C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/"

String[] extensions = new String[] { "msg" };

System.out.println("Getting all .msg files in " + dir.getCanonicalPath() + " including those in subdirectories");

List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);

System.out.println("Number of filtered files: --> "+files.size());

int c=0;
int np=0;


for (File file : files) {

System.out.println("file: " + file.getCanonicalPath()+"   ====> "+file.getAbsolutePath());

try{
//extractFromFile(parser, file.getCanonicalPath(),c);
extractFromMSG(file.getCanonicalPath(),c);
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
private static void extractFromMSG( final String fileName, int i) throws IOException{
	
	JSONObject json= new JSONObject();
	JSONArray extracteddata = new JSONArray();
	
MAPIMessage msg = null;
try {
	msg = new MAPIMessage(fileName);
	json.put("File Name:", fileName);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
OutlookTextExtactor ext = new OutlookTextExtactor(msg);
String text = ext.getText();

try {
	System.out.println("From: " + msg.getDisplayFrom());
	extracteddata.add("From --> " + msg.getDisplayFrom());
} catch (ChunkNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
try {
	System.out.println("To: " + msg.getDisplayTo());
	extracteddata.add("To --> " + msg.getDisplayTo());
} catch (ChunkNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
try {
	System.out.println("CC: " + msg.getDisplayCC());
	extracteddata.add("CC: --> " + msg.getDisplayCC());
} catch (ChunkNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
try {
	System.out.println("BCC: " + msg.getDisplayBCC());
	extracteddata.add("BCC: --> " + msg.getDisplayBCC());
} catch (ChunkNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
try {
	System.out.println("Subject: " + msg.getSubject());
	extracteddata.add("Subject: --> " + msg.getSubject());
} catch (ChunkNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
try {
	System.out.println("Text Content "+msg.getTextBody());
	extracteddata.add("Text Content -->"+msg.getTextBody());
} catch (ChunkNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
System.out.println("Text Content 2 "+text);
//if(msg.getTextBody().equals(text)){System.out.println("< ====== SAME TEXT =====>");}
try {
	System.out.println("Message Date "+msg.getMessageDate());
} catch (ChunkNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
/*
try {
	String conversation=msg.getConversationTopic();
	if (conversation != null){
	System.out.println("Conversation Topic "+ msg.getConversationTopic());
	}
} catch (ChunkNotFoundException e) {
	// TODO Auto-generated catch block
	System.out.println("No Conversation Topic FOUND");
	e.printStackTrace();
}
*/

AttachmentChunks[] attachments = msg.getAttachmentFiles();
extracteddata.add("Number of Attachments --> " + attachments.length);
if (attachments.length > 0)
{
	File d = new File("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/"+fileName.substring(fileName.lastIndexOf('\\') + 1));
	if (!d.exists()) {
	new File("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/"+fileName.substring(fileName.lastIndexOf('\\') + 1)).mkdir();}
	// DIRECTORY WHERE THE ATTACHMENTS ARE STORED
	
	JSONArray extractedattachment = new JSONArray();
	
	Path path = Paths.get("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/"+fileName.substring(fileName.lastIndexOf('\\') + 1));
    //if directory exists?
    if (!Files.exists(path)) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            //fail to create directory
            e.printStackTrace();
        }
    }
	
	
	
	if (d.exists() || d.mkdir())
	{
		for (AttachmentChunks attachment : attachments)
		{
			String fileName2 = attachment.attachFileName.toString();
			
			if (attachment.attachLongFileName != null)
			{
				fileName2 = attachment.attachLongFileName.toString();
				extractedattachment.add("Attachment -->"+ fileName2);
			}

			File f = new File(d, fileName2);
			OutputStream fileOut = null;
			try
			{
				fileOut = new FileOutputStream(f);
				fileOut.write(attachment.attachData.getValue());
			}
			finally
			{
				if (fileOut != null)
				{
					fileOut.close();
				}
			}
		}

	}
	json.put("Attachments", extractedattachment);
}
System.out.println("Attachment Extraction Done ...");
	

json.put("Data Extracted", extracteddata);

writeJson(json, fileName, i);

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

writeJson(json, fileName, i);

}

public static String identifyLanguage(String text) {

LanguageIdentifier identifier = new LanguageIdentifier(text);

return identifier.getLanguage();

}

public static void writeJson (JSONObject json,String filename,int i) throws IOException{

try (FileWriter file=new
FileWriter("C:/Users/EloyGonzales/Documents/Domitila Technologies/MccarthyFynch/SampleData/"+filename.substring(filename.lastIndexOf('\\') + 1)+ "_JSONResult_TE_MSG"+i+".json")) {
// DIRECTORY WHERE THE JSON IS STORED
	
file.write(json.toJSONString());

System.out.println("successfully copied JSON objet to file");

System.out.println("\nJSON Object:"+ json);

}

}

}

