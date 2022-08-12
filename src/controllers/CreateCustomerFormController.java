package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.PrimaryStageCommon;

import java.io.IOException;

public class CreateCustomerFormController {
    public AnchorPane createNewCustomerAccountFormId;
    public JFXTextField txtGenerateCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerContactNo;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerUserName;
    public JFXTextField txtCustomerPassword;
    public ImageView imgBackToLoginBtn;

    public void saveCustomer(ActionEvent actionEvent) {
    }

    public void updateCustomer(ActionEvent actionEvent) {
    }

    public void deleteCustomer(ActionEvent actionEvent) {
    }

    public void imgBackToLoginBtnOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        PrimaryStageCommon<CreateCustomerFormController> s = new PrimaryStageCommon().setStage(createNewCustomerAccountFormId, new CreateCustomerFormController(), "Login Form","../views/LoginForm.fxml");
    }
}
