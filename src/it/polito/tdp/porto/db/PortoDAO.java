package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AuthorIdMap;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}
			st.close();
			conn.close();

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	/*
	 * Ottengo tutti gli autori.
	 */
	public List<Author> getAllAutori(AuthorIdMap authorIdMap) {

		final String sql = "SELECT * "+
							"FROM author "+
							"ORDER BY lastname ASC ";
		
		List<Author> autori=new ArrayList<Author>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				autore=authorIdMap.put(autore);
				autori.add(autore);
			}
			st.close();
			conn.close();

			return autori;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	/*
	 * Ottengo tutti i coautori dato un autore.
	 */
	public List<Author> getCoautori(AuthorIdMap authorIdMap, Author autore) {

		final String sql = "SELECT DISTINCT coautori.id, coautori.lastname, coautori.firstname "+
							"FROM author AS coautori, creator AS acreatore, creator AS ccreatore "+
							"WHERE acreatore.authorid!=ccreatore.authorid AND acreatore.authorid=? AND acreatore.eprintid=ccreatore.eprintid AND ccreatore.authorid=coautori.id ";
		
		List<Author> coautori=new ArrayList<Author>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, autore.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author a = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				a=authorIdMap.put(a);
				coautori.add(a);
			}
			st.close();
			conn.close();

			return coautori;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}
			st.close();
			conn.close();

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	/*
	 * Dati due autori ottengo un articolo comune ad entrambi.
	 */
	public Paper getArticoloComune(Author a1, Author a2) {

		final String sql = "SELECT paper.eprintid, paper.title, paper.issn, paper.publication, paper.`type`, paper.types "+
							"FROM paper, creator AS c1, creator AS c2 "+
							"WHERE paper.eprintid=c1.eprintid AND c1.eprintid=c2.eprintid AND c1.authorid=? AND c2.authorid=? ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1.getId());
			st.setInt(2, a2.getId());

			ResultSet rs = st.executeQuery();

			//ottengo un solo articolo tra quelli in comune
			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}
			st.close();
			conn.close();

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}