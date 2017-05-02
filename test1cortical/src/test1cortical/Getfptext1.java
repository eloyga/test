package test1cortical;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.cortical.retina.client.FullClient;
import io.cortical.retina.client.LiteClient;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.ExpressionFactory;
import io.cortical.retina.model.ExpressionFactory.ExpressionModel;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Language;
import io.cortical.retina.model.Retina;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import io.cortical.retina.rest.ApiException;

public class Getfptext1 {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		LiteClient lite=  new io.cortical.retina.client.LiteClient("d2e5a870-0833-11e7-b22d-93a4ae922ff1");

	
		try{
		double similarity = lite.compare("apple", "microsoft");
		System.out.println("similarity between words: "+similarity);
		
		List<String> terms = lite.getSimilarTerms("java");
		for (int i=0;i<terms.size();i++){
		System.out.println("similar terms from a word java: "+terms.get(i));	
		}
		
		
		int[] appleFP = lite.getFingerprint("apple");
		int[] microsoftFP = lite.getFingerprint("microsoft");
		double fingerprintSimilarity = lite.compare(appleFP, microsoftFP);
		System.out.println("fingerprint comparison between words: "+fingerprintSimilarity);
		
		// Compute the similarity between a text and a fingerprint
		double textFingerprintSimilarity = lite.compare("Microsoft Corporation is an American multinational technology company headquartered in Redmond, Washington", lite.getFingerprint("apple"));
		System.out.println("similarity between text and a fingerprint: "+textFingerprintSimilarity);
		
		// Construct a composite Fingerprint from an array of texts to use for semantic filtering
		int[] neurologyFilter = lite.createCategoryFilter(Arrays.asList("neuron", "synapse", "neocortex"));
		// [6, 77, 78, 85, 119, 120, 124, 125, 128, 150, 163, 164, 167, 212, 242, 246, 253, 371, ..., 16376, 16381]

		// Use the neurologyFilter computed above to compare and classify new texts.
		double similaritySkylab = lite.compare("skylab", neurologyFilter);
		// 0.056544622895956895 -- low semantic similarity -> negative classification
		System.out.println("similarity of one word respect to an array of texts: "+similaritySkylab);
		double similarityCorticalColumn = lite.compare("cortical column", neurologyFilter);
		// 0.35455851788907006 -- high semantic similarity -> positive classification
		System.out.println("similarity of the other word respect to an array of texts: "+similarityCorticalColumn);
		}
		catch (ApiException e)
		{
			System.out.println(e.getMessage());
		}
		catch (JsonProcessingException e)
		{
			System.out.println(e.getMessage());
		}

		
		// Create FullClient instance
		FullClient fullClient = new FullClient("d2e5a870-0833-11e7-b22d-93a4ae922ff1");
		// Retrieve an array of all available Retinas
		try {
			List<Retina> retinas = fullClient.getRetinas();
			for (int i=0;i<retinas.size();i++){
			System.out.println("value in the retina list: "+ retinas.get(i).getRetinaDescription());
			}
			// Retrieve information about a specific term
			//Term term = (Term) fullClient.getTerms("java");
			
			// Get contexts for a given term
			List<Context> contextsForTerm = fullClient.getContextsForTerm("java");
			for (int i=0;i<contextsForTerm.size();i++){
			System.out.println("context from a term java: "+contextsForTerm.get(i).getContextLabel());
			}
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExpressionModel orExpression = ExpressionFactory.or(ExpressionFactory.term("brain"), ExpressionFactory.text("a region of the cerebral cortex"));
		try {
			List<Term> terms = fullClient.getSimilarTermsForExpression(orExpression);
			for (int i=0;i<terms.size();i++){
			System.out.println("list of similar terms for brain: "+terms.get(i).getTerm());
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//String javaText="Microsoft Corporation is an American multinational technology company headquartered in Redmond, Washington";
		//String javaText="La Paz is dizzying in every respect, not only for its well-publicized altitude (3660m), but for its quirky beauty. Most travelers enter this extraordinary city via the flat sparse plains of the sprawling city of El Alto, an approach that hides the sensational surprises of the valley below. The first glimpse of La Paz will, literally, take your breath away. The city’s buildings cling to the sides of the canyon and spill spectacularly downwards. On a clear day, the imposing showy, snowy Mt Illimani (6402m) looms in the background.";
		String javaText="Aun así, para Albert Einstein, la señorita Noether fue el genio matemático creativo más importante que haya existido desde que comenzó la educación superior para las mujeres";
		// Detect the language for an input text
		try {
			Language languageForText = fullClient.getLanguageForText(javaText);
			System.out.println("language of text: "+languageForText);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the Fingerprint for an input expression
		try {
			Fingerprint fingerprintForExpression = fullClient.getFingerprintForExpression(new Text(javaText));
			System.out.println("fingerprint for an input expresion (text): "+fingerprintForExpression.toJson());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return contexts for an input expression
		try {
			List<Context> contextsForExpression = fullClient.getContextsForExpression(new Text(javaText));
			for (int i=0;i<contextsForExpression.size();i++){
			System.out.println("context for an input expression (text): "+ contextsForExpression.get(i).getContextLabel());
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return similar terms for an input expression
		try {
			List<Term> similarTermsForExpression = fullClient.getSimilarTermsForExpression(new Text(javaText));
			for (int i=0;i<similarTermsForExpression.size();i++){
			System.out.println("similar terms for an input expression (text): "+similarTermsForExpression.get(i).getTerm());
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//Test for parsing PDF files
		
		// Create a new empty document
		PDDocument document = new PDDocument();
		
		PDPage page = new PDPage();
		document.addPage( page );

		// Create a new font object selecting one of the PDF base fonts
		PDFont font = PDType1Font.HELVETICA_BOLD;

		// Start a new content stream which will "hold" the to be created content
		PDPageContentStream contentStream;
		try {
			contentStream = new PDPageContentStream(document, page);

			// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
			contentStream.beginText();
			contentStream.setFont( font, 12 );
			contentStream.moveTextPositionByAmount( 100, 700 );
			contentStream.drawString( "Hello World" );
			contentStream.endText();

			// Make sure that the content stream is closed:
			contentStream.close();

			
			// Save the results and ensure that the document is properly closed:
			document.save( "Hello World.pdf");
			document.close();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		try {
		    //String text = getText(new File("Hello World.pdf"));
		    String text = getText(new File("C:/Users/EloyGonzales/Downloads/Agreementtolease.pdf"));
		    System.out.println("Text Content in given PDF: " + text);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		// Return contexts for an input expression
				try {
					
					String text = getText(new File("C:/Users/EloyGonzales/Downloads/Agreementtolease.pdf"));
					List<Context> contextsForExpression = fullClient.getContextsForExpression(new Text(text));
					for (int i=0;i<contextsForExpression.size();i++){
					System.out.println("DOCUMENT context for an input expression: "+ contextsForExpression.get(i).getContextLabel());
					}
					
					List<Term> similarTermsForExpression = fullClient.getSimilarTermsForExpression(new Text(text));
					for (int i=0;i<similarTermsForExpression.size();i++){
					System.out.println("DOCUMENT similar terms for an input expression: "+similarTermsForExpression.get(i).getTerm());
					}

					List<String> keywordsForExpression = fullClient.getKeywordsForText(text);
					for (int i=0;i<keywordsForExpression.size();i++){
					System.out.println("DOCUMENT keywords for an input expression: "+similarTermsForExpression.get(i).getTerm());
					}
					
					
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		
}
	
static String getText(File pdfFile) throws IOException {
	    PDDocument doc = PDDocument.load(pdfFile);
	    //return new PDFTextStripper().getText(doc);
	    PDFTextStripper pdfStripper = new PDFTextStripper();
	    pdfStripper.setStartPage(1);
        pdfStripper.setEndPage(1);
        String parsedText = pdfStripper.getText(doc);
        doc.close();
        return parsedText;
	}	
	
}
