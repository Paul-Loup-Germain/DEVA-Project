module devaproject.devaproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens devaproject.devaproject to javafx.fxml;
    exports devaproject.devaproject;
}