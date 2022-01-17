module de.unihannover.sestock {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.json;


    opens de.unihannover.sestock to javafx.fxml;
    exports de.unihannover.sestock;
}