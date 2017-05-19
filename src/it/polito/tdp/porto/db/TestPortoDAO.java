package it.polito.tdp.porto.db;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AuthorIdMap;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		PortoDAO pd = new PortoDAO();
		Author p=pd.getAutore(85);
		System.out.println(p);
		System.out.println(pd.getArticolo(2293546));
		System.out.println(pd.getArticolo(1941144));
		
		AuthorIdMap a=new AuthorIdMap();
		
		System.out.println();
		System.out.print("Elenco autori");
		for(Author aa : pd.getAllAutori(a)){
			System.out.println(aa.toString());
		}
		
		System.out.println("\n");
		System.out.println("Elenco cautori dell'autore con codice 85");
		for(Author aa : pd.getCoautori(a, p)){
			System.out.println(aa.toString());
		}
		
		System.out.println();
		Author p1=pd.getAutore(719);
		Author p2=pd.getAutore(2185);
		System.out.println("Articoli comuni agli autori "+p1+" e "+p2);
		System.out.println(pd.getArticoloComune(p1, p2));
	}

}
