package it.polito.tdp.porto.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	//Grafo semplice, non orientato e non pesato
	private SimpleGraph<Author,DefaultEdge> grafo;
	private AuthorIdMap authorIdMap;
	private List<Author> autori;
	
	public Model(){
		authorIdMap=new AuthorIdMap();
	}
	
	public List<Author> getAutori(){
		if(autori==null){
			PortoDAO pdao=new PortoDAO();
			autori=pdao.getAllAutori(authorIdMap);
		}
		return autori;
	}
	
	public List<Author> getCoautori(Author autore){
		List<Author> list=autore.getCoautori();
		if(list==null){
			PortoDAO pdao=new PortoDAO();
			list=pdao.getCoautori(authorIdMap, autore);
			autore.setCoautori(list);
		}
		return list;
	}
	
	public void creaGrafo(){
		grafo=new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		//creazione dei vertici
		Graphs.addAllVertices(grafo, this.getAutori());
		//creazione degli archi
		for(Author a : grafo.vertexSet()){
			for(Author c : this.getCoautori(a)){
				grafo.addEdge(a, c);
			}
		}
	}
	
	public SimpleGraph<Author,DefaultEdge> getGrafo(){
		if(grafo==null)
			creaGrafo();
		return grafo;
	}

}
