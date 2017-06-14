package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.Map;

public class AuthorIdMap {
	
	Map<Integer, Author> mappa;
	
	public AuthorIdMap(){
		mappa=new HashMap<>();
	}

	public Author getAutore(Integer id) {
		return mappa.get(id);
	}

	public Author put(Author autore) {
		Author old=mappa.get(autore.getId());
		if(old==null){
			mappa.put(autore.getId(), autore);
			return autore;
		}
		else
			return old;
	}
	
	
	

}
