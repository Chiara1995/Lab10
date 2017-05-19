package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
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
	
	public List<Paper> getElencoArticoli(Author partenza, Author destinazione){
		UndirectedGraph<Author,DefaultEdge> g=this.getGrafo();
		DijkstraShortestPath<Author, DefaultEdge> dsp=new DijkstraShortestPath<Author, DefaultEdge>(g, partenza, destinazione);
		GraphPath<Author,DefaultEdge> percorso=dsp.getPath();
		List<Author> listVertex=new ArrayList<Author>();
		if(percorso==null)
			return null;
		for(DefaultEdge dwe : percorso.getEdgeList()){
			if(!listVertex.contains(g.getEdgeSource(dwe)))
				listVertex.add(g.getEdgeSource(dwe));
			if(!listVertex.contains(g.getEdgeTarget(dwe)))
				listVertex.add(g.getEdgeTarget(dwe));
		}
		List<Paper> elencoArticoli=new ArrayList<Paper>();
		for(int i=0; i<listVertex.size()-1; i++){
			PortoDAO pdao=new PortoDAO();
			Paper p=pdao.getArticoloComune(listVertex.get(i), listVertex.get(i+1));
			elencoArticoli.add(p);
		}
		return elencoArticoli;
	}
			
}
