package fxml;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.SelectableChannel;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import entities.User;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.WebServer;
import service.WebServer_Service;
import method.Outil;
import service.Exception_Exception;

public  class UserController implements Initializable{
		
		
     WebServer ws = new WebServer_Service().getWebServerPort();
     
		//@FXML
		//private Label params;
	 	@FXML
	    private TextField name_txt;
	 	@FXML
	 	private TextField login_txt;
	 	@FXML
	 	private TextField password_txt;	
	 	@FXML
	    private Button submit_bt;

	    @FXML
	    private Button cancel_bt;

	    @FXML
	    private TableView<service.User> user_table;

	    @FXML
	    private TableColumn<User, Integer> id_column;

	    @FXML
	    private TableColumn<User, String> name_column;

	    @FXML
	    private TableColumn<User, String> login_column;

	    @FXML
	    private TableColumn<User, String> password_column;

	    @FXML
	    private Button modify_bt;

	    @FXML
	    private Button delete_bt;
	    @FXML 
	    private Button logout_bt;
	    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//params.setText(User.connexion_params);		
		id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
		name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
		login_column.setCellValueFactory(new PropertyValueFactory<>("login"));
		password_column.setCellValueFactory(new PropertyValueFactory<>("password"));
         try {	
             loadTable();
         } catch (Exception ex) {
             Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
         }
	    }
	

	
	@FXML
    void cancel(ActionEvent event) {
		if(name_txt.getText().trim().equals("") && login_txt.getText().trim().equals("") && password_txt.getText().trim().equals("")) {
		Outil.showErrorMessage("Error", "The fields are empty");	
		}else {
		clean();
    }
	}
    @FXML
    void delete(ActionEvent event) throws Exception_Exception, Exception {
    	if(user_table.getSelectionModel().getSelectedItem()!=null) {
    	Alert a=new Alert(AlertType.CONFIRMATION);
   		a.setTitle("Confirm");
   		a.setContentText("Are you sure you want to delete selected. ");
    	Optional<ButtonType> action=a.showAndWait();
    	if(action.get() == ButtonType.OK)ws.delete(user_table.getSelectionModel().getSelectedItem().getId());
    	loadTable();
    	clean();
    }else {
		Outil.showErrorMessage("Error", "Select a line");
	}
    }

    @FXML
    void modify(ActionEvent event) throws Exception {
    	if(user_table.getSelectionModel().getSelectedItem()!=null) {
    	Alert a=new Alert(AlertType.CONFIRMATION);
   		a.setTitle("Confirm");
   		a.setContentText("Are you sure you want to update selected. ");
    	Optional<ButtonType> action=a.showAndWait();
    	
    	String name=name_txt.getText();
    	String log=login_txt.getText();
    	String pwd=password_txt.getText();
    	if(name.trim().equals("") || log.trim().equals("") || pwd.trim().equals("")) {
    		Outil.showErrorMessage("Error", "fill in all the fields");
    		clean();
    	}else {
    	
        int lastid = user_table.getSelectionModel().getSelectedItem().getId();
        
    	if(action.get() == ButtonType.OK)ws.update(lastid,name,log,pwd);
    	loadTable();
    	clean();
    	}
    	}else {
    		Outil.showErrorMessage("Error", "Select a line");
    	}
    }

    @FXML
    void submit(ActionEvent event) {
    	String name=name_txt.getText();
    	String log=login_txt.getText();
    	String pwd=password_txt.getText();
    	if(name.trim().equals("") || log.trim().equals("") || pwd.trim().equals("")) {
    		Outil.showErrorMessage("Error", "fill in all the fields");
    		clean();
    	}else {
            
    		int t = ws.add(name,log,pwd);
    		if(t !=0) {
    		Outil.showConfirmationMessage("@2LK", "Added data");
                        try {
                            loadTable();
                        } catch (Exception ex) {
                            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
    		clean();
    		}
    	else {
    		Outil.showErrorMessage("Erreur", "Date not Added");
    	}
    	}
    }

	private void loadTable() throws Exception{
                List<service.User> liste = ws.liste();
                ObservableList<service.User> l_user = FXCollections.observableArrayList(liste);
		
		//Data loading
		user_table.setItems(l_user);
	}
    @FXML
    void tableclick(MouseEvent event) {
    	name_txt.setText(user_table.getSelectionModel().getSelectedItem().getName());
    	login_txt.setText(user_table.getSelectionModel().getSelectedItem().getLogin());
    	password_txt.setText(user_table.getSelectionModel().getSelectedItem().getPassword());
    }
    @FXML
    void logout(ActionEvent event) throws IOException {
    	String url="/clientws/Login.fxml";
		Outil.load(event, url);
    }
    
    void clean() {
    	name_txt.setText("");
		login_txt.setText("");
		password_txt.setText("");
    }

}

