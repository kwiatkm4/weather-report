module pl.edu.pw.mwo1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.net.http;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens pl.edu.pw.mwo1 to javafx.fxml;
    exports pl.edu.pw.mwo1;
    exports pl.edu.pw.mwo1.models;
}