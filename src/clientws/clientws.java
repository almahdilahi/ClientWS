package clientws;
	
import java.io.IOException;

import entities.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import method.Outil;
import service.WebServer;
import service.WebServer_Service;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class clientws extends Application {
    
        WebServer ws = new WebServer_Service().getWebServerPort();
	@FXML
	private TextField login_txt;
	@FXML
	private TextField username_txt;
	@FXML
	private PasswordField password_txt;
	@FXML
	private Button connexion_bt;
	@FXML
	private Button connect_btn;
	@FXML
	public void getConnexion(ActionEvent  event) throws IOException {
		String log=username_txt.getText();
		String pwd=password_txt.getText();
		if(log.trim().equals("") || pwd.trim().equals("")) {
			Outil.showErrorMessage("Error", "fill in all the fields!");
		}
		else {
                        
			service.User us = new service.User();
                        us = ws.getLogin(log, pwd);
                        if(us != null) {
                                    
				User.connexion_params="Bienvenue : "+us.getName();
				String url="/fxml/Userfx.fxml";
				Outil.load(event, url);
			}
			else {
				Outil.showErrorMessage("Error", "Login or Password !!");
			}
		}
		
		
		/*
		 * Pour afficher un message a l'ecran
		 * 
	 	Alert a=new Alert(AlertType.INFORMATION);
		a.setTitle("Limamou Laye Ka => @2lK");
		a.setContentText("Limamou Laye Ka Java Fx :"+log);
		a.showAndWait();
		*/
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root=FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		//DB db=new DB();
		//ouzdeville@gmail.com
		//db.getConnexion();
	}
}
