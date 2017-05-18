
import java.io.File;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;

public class InjectJavaScriptIntoPDF {

    private final static String PDF_FILE_PATH = "/home/jean-baptiste/Téléchargements/test_pdf_new.pdf";

    private static void addJavaScript(PDDocument doc) {

        System.out.println("--> injection de l'exécutable dans le pdf");

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("open.js");
        String content = convertStreamToString(is);

        System.out.println("--- Ajout du contenu Javascript ---");
        System.out.println(content);
        System.out.println("--- Ajout du contenu Javascript ---");

        PDActionJavaScript PDAjavascript = new PDActionJavaScript(content);
        doc.getDocumentCatalog().setOpenAction(PDAjavascript);

    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    public static void main(String[] args) throws Exception {

        try (PDDocument doc = PDDocument.load(new File(InjectJavaScriptIntoPDF.PDF_FILE_PATH))) {

            System.out.println("--- Début du script d'injection PDF ---");

            InjectJavaScriptIntoPDF.addJavaScript(doc);

            int index = PDF_FILE_PATH.lastIndexOf('.');
            String newPath = InjectJavaScriptIntoPDF.PDF_FILE_PATH.substring(0, index) + "_new"
                    + InjectJavaScriptIntoPDF.PDF_FILE_PATH.substring(index, InjectJavaScriptIntoPDF.PDF_FILE_PATH.length());
            doc.save(newPath);

            System.out.println("-- sauvegarde du document sous " + newPath + " ---");
            System.out.println("--- Fin du Script d'injection PDF ---");

        } catch (Exception e) {

            System.err.println("Une erreur est survenue pendant l'exécution du script");
            e.printStackTrace();

        }
    }


}

