module de.unihannover.sestock {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    opens de.unihannover.sestock to javafx.fxml;
    exports de.unihannover.sestock;
}