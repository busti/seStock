package de.unihannover.sestock;

import javafx.event.ActionEvent;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Locale;

public class PrintController {

    public void onPrintButtonClick(ActionEvent actionEvent) throws IOException, PrintException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Label lb = (Label) stage.getScene().lookup("#stock");

        String stock = lb.getText();
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        printerJob.showPrintDialog(stage);

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(printerJob.getJobSettings().getCopies()));
        printRequestAttributeSet.add(new JobName("Print Stock", Locale.getDefault()));
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

        Doc mydoc = new SimpleDoc(new StringBufferInputStream(stock), flavor, null);
        DocPrintJob job = getPrintService(printerJob.getPrinter().getName()).createPrintJob();
        job.print(mydoc, printRequestAttributeSet);

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
