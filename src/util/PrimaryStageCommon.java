package util;

import controllers.CreateCustomerFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PrimaryStageCommon<T> {
    public PrimaryStageCommon<CreateCustomerFormController> setStage(AnchorPane pane, T t, String title,String path) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(t.getClass().getResource(path)));
        Stage primaryStage = (Stage) pane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.centerOnScreen();
        primaryStage.show();
        return new PrimaryStageCommon<>();
    }
}
