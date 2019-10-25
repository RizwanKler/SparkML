package com.jobreadyprogrammer.spark;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class myCSVReader {
	
	
	public void loadCSVHeader(ListView<String> listView, File upload) { 
		
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
		
		try {
	            String[] headers = null;
	            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Users/rizwa/Documents/Spark Course/data/cryotherapy.csv"));
	            String header = bufferedReader.readLine();
	            if (header != null) {
	                headers = header.split(",");
	            }
	            for (int i = 0; i < headers.length; i++) {
	                System.out.println("Headers " + headers[i]);
	                
	                list.add(headers[i]);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
//	listView.setItems(list);
		
		

}
