package test1cortical;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class readfileDemo {
    public static void main(String[] args) {
        readfileDemo demo = new readfileDemo();
        demo.readFileAsList();
    }

    private void readFileAsList() {
        //String fileName = "C:/Users/EloyGonzales/Downloads/Agreementtolease[TEXT].txt";
        Path wiki_path = Paths.get("C:/Users/EloyGonzales/Downloads", "Agreementtolease[TEXT].txt");

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
}

