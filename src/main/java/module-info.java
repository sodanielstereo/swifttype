module com.example.swifttype {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.swifttype.controller to javafx.fxml;
    exports com.example.swifttype;
}