package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<?> boxSecondo;

    @FXML
    private TextArea txtResult;
    
    public void setModel(Model model){
    	this.model=model;
    	this.boxPrimo.getItems().addAll(model.getAutori());
    	if(this.boxPrimo.getItems().size()>0)
    		this.boxPrimo.setValue(boxPrimo.getItems().get(0));
    }

    @FXML
    void handleCoautori(ActionEvent event) {
    	txtResult.clear();
    	Author autore=this.boxPrimo.getValue();
    	List<Author> coautori=model.getCoautori(autore);
    	txtResult.setText("Elenco coautori:\n");
    	for(Author a : coautori){
    		this.txtResult.appendText(a.toString()+"\n");
    	}
    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
}
