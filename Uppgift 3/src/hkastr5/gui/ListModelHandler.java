package hkastr5.gui;

/**
 * 
 * D0018D, Objektorienterad programmering i Java, Lp1-2, H20
 * Inlämningsuppgift 3
 * @author Håkan Strääf (hkastr-5@student.ltu.se)
 * 
 * Klassen ListModelHandler . //TO-DO
 * 
 */

import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class ListModelHandler<T> {

	private DefaultListModel<T> listModel = new DefaultListModel<>();

	public ListModelHandler() {
	}
	
	public void setListItems (ArrayList<T> items) {
		listModel.clear();
		for (T s : items) {
			listModel.addElement(s);
		}
	}
	
	public void clearListItems () {
		listModel.clear();
	}
	
	public DefaultListModel<T> getListModel() {
		return listModel;
	}
}
