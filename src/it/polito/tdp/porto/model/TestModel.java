package it.polito.tdp.porto.model;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		System.out.println("Elenco autori");
		for(Author a : model.getAutori()){
			System.out.println(a.toString());
		}
		
		System.out.println();
		model.creaGrafo();
		System.out.println("Grafo creato");
		System.out.println("Numero di vertici del grafo "+model.getGrafo().vertexSet().size());
		System.out.println("Numero di archi del grafo "+model.getGrafo().edgeSet().size());
		
		System.out.println();
		PortoDAO pdao=new PortoDAO();
		Author a=pdao.getAutore(85);
		System.out.println("Elenco coautori dell'autore "+a);
		for(Author aa : model.getCoautori(a)){
			System.out.println(aa.toString());
		}
	}

}
