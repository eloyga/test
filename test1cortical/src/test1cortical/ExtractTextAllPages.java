package test1cortical;


import java.io.File;
import java.io.FileOutputStream;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.Page;
import com.snowtide.pdf.OutputTarget;
import com.snowtide.pdf.PDFTextStream;
import com.snowtide.pdf.annot.Annotation;
import com.snowtide.pdf.annot.LinkAnnotation;
import com.snowtide.pdf.layout.Image;
 
public class ExtractTextAllPages {
  public static void main (String[] args) throws java.io.IOException {
    String pdfFilePath = args[0];
 
    Document pdf = PDF.open(pdfFilePath);
    
    //Document doc = PDF.open(pdfFilePath);
    for (String key : pdf.getAttributeKeys()) {
        System.out.printf("%s: %s", key, pdf.getAttribute(key));
        System.out.println();
    }
    PDFTextStream stream = new PDFTextStream(pdfFilePath);
    // print the value of the Author attribute to System.out
    String authorName = (String)stream.getAttribute(Document.ATTR_AUTHOR);
    System.out.println("Author: " + authorName);
     
    //doc.close();
    //Document doc = PDF.open(pdfFilePath);
    for (Annotation a : pdf.getAllAnnotations()) {
        if (a instanceof LinkAnnotation) {
            LinkAnnotation link = (LinkAnnotation)a;
            if (link.getURI() != null) {
                System.out.printf(
                        "Found outgoing link on page %s, bounds %s, uri: %s",
                        link.pageNumber(), link.bounds(), link.getURI());
                System.out.println();
            }
        }
    }
     
    //doc.close();
    File outputDir = new File(args[1]);
    if (!outputDir.exists()) outputDir.mkdirs();
    //Document pdf = PDF.open(pdfFilePath);
    for (Page p : pdf.getPages()) {
        int i = 0;
        for (Image img : p.getImages()) {
            FileOutputStream out = new FileOutputStream(
                    new File(outputDir, String.format("%s-%s.%s",
                            p.getPageNumber(), i, img.dataFormat().name().toLowerCase())));
            out.write(img.data());
            out.close();
            i++;
        }
        System.out.printf("Found %s images on page %s", p.getImages().size(), p.getPageNumber());
        System.out.println();
    }
    
    
    StringBuilder text = new StringBuilder(1024);
    pdf.pipe(new OutputTarget(text));
    pdf.close();
    System.out.println(text);
    
  }
}
