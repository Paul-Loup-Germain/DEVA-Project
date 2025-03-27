module ch.etmles.srs.buisness {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ch.etmles.srs.buisness to javafx.fxml;
    exports ch.etmles.srs.buisness;
    exports ch.etmles.srs.view;
    opens ch.etmles.srs.view to javafx.fxml;
}