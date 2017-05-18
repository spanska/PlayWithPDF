
import java.io.*;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;

public class AddAttachementToPDF {

    private final static String PDF_FILE_PATH = "/home/jean-baptiste/Téléchargements/test_pdf.pdf";

    private final static String ATTACHED_FILE_PATH = "/home/jean-baptiste/Téléchargements/test.pdf";

    private static void addFileToPDF(PDDocument doc) throws IOException {

        System.out.println("--> ajout du fichier " + ATTACHED_FILE_PATH);

        PDEmbeddedFilesNameTreeNode efTree = new PDEmbeddedFilesNameTreeNode();
        PDComplexFileSpecification fs = new PDComplexFileSpecification();
        fs.setFile(AddAttachementToPDF.getFileName(ATTACHED_FILE_PATH));

        try (InputStream is = new FileInputStream(ATTACHED_FILE_PATH)) {
            PDEmbeddedFile ef = new PDEmbeddedFile(doc, is);
            ef.setSubtype("text/plain");
            ef.setSize(AddAttachementToPDF.getFileSize(ATTACHED_FILE_PATH));
            ef.setCreationDate(new GregorianCalendar());

            fs.setEmbeddedFile(ef);
        }

        Map efMap = new HashMap();
        efMap.put("attachment", fs);
        efTree.setNames(efMap);

        PDDocumentNameDictionary names = new PDDocumentNameDictionary(doc.getDocumentCatalog());
        names.setEmbeddedFiles(efTree);
        doc.getDocumentCatalog().setNames(names);

    }

    private static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    private static int getFileSize(String filePath) {
        File file = new File(filePath);
        return (int) file.length();
    }

    public static void main(String[] args) throws Exception {

        try (PDDocument doc = PDDocument.load(new File(AddAttachementToPDF.PDF_FILE_PATH))) {

            System.out.println("--- Début du script d'injection PDF ---");

            AddAttachementToPDF.addFileToPDF(doc);

            int index = PDF_FILE_PATH.lastIndexOf('.');
            String newPath = AddAttachementToPDF.PDF_FILE_PATH.substring(0, index) + "_new"
                    + AddAttachementToPDF.PDF_FILE_PATH.substring(index, AddAttachementToPDF.PDF_FILE_PATH.length());
            doc.save(newPath);

            System.out.println("-- sauvegarde du document sous " + newPath + " ---");
            System.out.println("--- Fin du Script d'injection PDF ---");

        } catch (Exception e) {

            System.err.println("Une erreur est survenue pendant l'exécution du script");
            e.printStackTrace();

        }
    }


}
