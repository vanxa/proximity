module com.vanxacloud.appstudio.proximity {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.web;
    requires fr.brouillard.oss.cssfx;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens com.vanxacloud.appstudio.proximity to javafx.fxml;
    exports com.vanxacloud.appstudio.proximity;
}