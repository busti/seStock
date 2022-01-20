package de.unihannover.sestock;

import javafx.event.ActionEvent;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.print.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PrintController {

    public static String getPrintValue(String symbol) throws IOException, InterruptedException {
        AlphavantageApi api = AlphavantageApi.getInstance();
        AlphavantageApi.TimeSeriesWrapper data = api.time_series_daily(symbol);
        List<AlphavantageApi.TimeSeriesEntry> sorted = data.timeSeriesSorted();
        double val = sorted.get(sorted.size()-1).timeSeries.close;
        return symbol+"("+val+")";
    }

    public void onPDFButtonClick(ActionEvent actionEvent) throws IOException, PrintException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Label lb = (Label) stage.getScene().lookup("#stock");
        String stock = lb.getText();
        try{
            stock = getPrintValue(stock);
        } catch (Exception e){
            System.out.println("Error.");
        }
        lb.setText(stock);
        String path = System.getProperty("java.io.tmpdir") + "/printjob.txt";
        String path2 = System.getProperty("java.io.tmpdir") + "/export/printjob.pdf";
        File file = new File(path2);
        file.getParentFile().mkdirs();
        Files.write( Paths.get(path), stock.getBytes());
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream content = new PDPageContentStream(document, page);
        int dy_Offset = 775;
        while (reader.ready()) {
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 10);
            content.newLineAtOffset(75, dy_Offset);
            content.showText(reader.readLine());
            dy_Offset -= 12;
            content.endText();
        }
        content.close();
        document.save(path2);
        document.close();
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File(path2));
    }

    public void onPrintButtonClick(ActionEvent actionEvent) throws IOException, PrintException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Label lb = (Label) stage.getScene().lookup("#stock");
        String stock = lb.getText();
        try{
            stock = getPrintValue(stock);
        } catch (Exception e){
            System.out.println("Error.");
        }
        lb.setText(stock);
        String path = System.getProperty("java.io.tmpdir") + "/printjob.txt";
        String path2 = System.getProperty("java.io.tmpdir") + "/printjob.pdf";
        Files.write( Paths.get(path), stock.getBytes());
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream content = new PDPageContentStream(document, page);
        int dy_Offset = 775;
        while (reader.ready()) {
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 10);
            content.newLineAtOffset(75, dy_Offset);
            content.showText(reader.readLine());
            dy_Offset -= 12;
            content.endText();
        }
        content.close();
        document.save(path2);
        document.close();
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        printerJob.showPrintDialog(stage);
        FileInputStream in = new FileInputStream(path2);
        Doc doc = new SimpleDoc(in, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        DocPrintJob job = getPrintService(printerJob.getPrinter().getName()).createPrintJob();
        job.print(doc, null);
        in.close();
    }


    private PrintService getPrintService(String name) {
        for (PrintService printService : java.awt.print.PrinterJob.lookupPrintServices()) {
            if (name.equalsIgnoreCase(printService.getName())) {
                return printService;
            }
        }
        return null;
    }

}
