module vu.ng.work.polygon_remover {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens vu.ng.work.polygon_remover to javafx.fxml;
    exports vu.ng.work.polygon_remover;
}