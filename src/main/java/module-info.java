module com.example.spge_sts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.spge_sts to javafx.fxml;
    exports com.example.spge_sts;
}