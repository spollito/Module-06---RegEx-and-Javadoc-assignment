module org.example.javafxreg {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javafxreg to javafx.fxml;
    exports org.example.javafxreg;
}