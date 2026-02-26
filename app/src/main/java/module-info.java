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
    requires org.apache.commons.lang3;
    requires org.slf4j;
    requires java.desktop;

    opens com.vanxacloud.appstudio.proximity to javafx.fxml, javafx.graphics;
    opens com.vanxacloud.appstudio.proximity.boot to javafx.fxml;
    opens com.vanxacloud.appstudio.proximity.fx.control.wizard to javafx.fxml;
    opens com.vanxacloud.appstudio.proximity.fx.control.wizard.page to javafx.fxml;
}