package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
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
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;
    
    public void setModel(Model model){
    	this.model=model;
    	this.boxPrimo.getItems().addAll(model.getAutori());
    }

    @FXML
    void handleCoautori(ActionEvent event) {
    	txtResult.clear();
    	Author autore=this.boxPrimo.getValue();
    	if(autore==null){
    		txtResult.setText("Errore: selezionare un autore nella prima comboBox");
    		return;
    	}
    	List<Author> coautori=model.getCoautori(autore);
    	if(coautori==null){
    		txtResult.setText("Nessun coautore per l'autore selezionato");
    		return;
    	}
    	txtResult.setText("Elenco coautori:\n");
    	for(Author a : coautori){
    		this.txtResult.appendText(a.toString()+"\n");
    	}
    	return;
    }
    
    @FXML
    void handleBoxSecondo(ActionEvent event) {
    	Author a =this.boxPrimo.getValue();
    	List<Author> notCoautori=new ArrayList<Author>();
    	List<Author> coautori=model.getCoautori(a);
    	for(Author autore : model.getAutori()){
    		if(!coautori.contains(autore) && !autore.equals(a))
    			notCoautori.add(autore);
    	}
    	this.boxSecondo.getItems().addAll(notCoautori);
    	return;
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.clear();
    	Author autore1=this.boxPrimo.getValue();
    	Author autore2=this.boxSecondo.getValue();
    	if(autore1==null){
    		txtResult.setText("Errore: selezionare un autore nella prima comboBox");
    		return;
    	}
    	if(autore2==null){
    		txtResult.setText("Errore: selezionare un autore nella seconda comboBox");
    		return;
    	}
    	List<Paper> articoli=model.getElencoArticoli(autore1, autore2);
    	if(articoli==null){
    		txtResult.setText("Non esiste alcun elenco di articoli che colleghi i due autori");
    		return;
    	}
    	else{
        	txtResult.setText("Elenco articoli:\n");
        	for(Paper p : articoli){
        		this.txtResult.appendText(p.toString()+"\n");
        	}
        	return;
    	}
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
}
