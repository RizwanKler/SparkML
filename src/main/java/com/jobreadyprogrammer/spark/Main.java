package com.jobreadyprogrammer.spark;

import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.lit;

import static org.apache.spark.sql.functions.col;


import java.util.Properties;

import javax.activation.FileDataSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
//import org.apache.spark.SparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
//import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
//import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
//import org.apache.spark.sql.execution.datasources.csv.CSVDataSource;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.ListIterator;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Scanner;
//import java.util.concurrent.TimeUnit;

//import javax.activation.FileDataSource;
//import javax.swing.filechooser.FileView;

//import java.nio.file.Paths;
//import java.nio.file.FileSystems;

//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;

import org.apache.commons.io.FileUtils;
//import org.apache.kafka.common.record.Record;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.spark.ml.Pipeline;
//import org.apache.spark.ml.PipelineModel;
//import org.apache.spark.ml.PipelineStage;
//import org.apache.spark.ml.classification.LogisticRegression;
//import org.apache.spark.ml.feature.StringIndexer;
//import org.apache.spark.ml.feature.VectorAssembler;
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SaveMode;
//import org.apache.spark.sql.SparkSession;
//import org.apache.spark.sql.catalog.Table;
//import org.apache.spark.sql.execution.datasources.csv.CSVDataSource;
//import org.apache.spark.sql.sources.v2.DataSourceOptions;
//import org.apache.spark.sql.sources.v2.ReadSupport;
//import org.apache.spark.sql.sources.v2.reader.*;
//import org.apache.spark.sql.types.TimestampType;
//import org.datafx.reader.DataReader;
//import org.spark_project.guava.collect.ImmutableMap;

import javafx.application.Application;
//import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
//import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
//import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import scala.collection.JavaConverters.*;
import scala.collection.Seq;

//import com.jobreadyprogrammer.spark.KmeansClustering;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;



public class Main extends Application{

@SuppressWarnings("unchecked")		
@Override
  public void start(final Stage primaryStage) throws Exception{
	
	
	// final File upload;// = null; // remove this null to test
	
	Label greeting = new Label("Make Your ML Selections Below");
	greeting.setTextFill(Color.GREEN);
	greeting.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
    
	Button btn1 = new Button("Select Datafile");
	final Button btn2 = new Button("Run ML Algorithm");
	
	// Create a WebView
	final WebView browser = new WebView();
	 
	// Get WebEngine via WebView
	final WebEngine webEngine = browser.getEngine();
	 
	// Load page
	
    final ImageView img1 = new ImageView();
     
     final ListView<String> listView = new ListView<String>();
     
     
  
	Image image1 = new Image("file:/Users/rizwa/Documents/Spark Course/workspace/sparkwithjava-master/project9/images/ml2.jpeg");
	
	img1.setImage(image1);
		

    TilePane tile = new TilePane();
    final BorderPane pane = new BorderPane();
    
    final FileChooser file = new FileChooser();
    
  //  File f = new File(new File(".").getCanonicalPath());
  //  file.setCurrentDirectory(f);
  //  file.setCurrentDirectory(null);
  //  file.showOpenDialog(null);
  //  File curDir = file.getCurrentDirectory()
    
    // File upload = new File();
    
    ToggleGroup group = new ToggleGroup();
    final RadioButton rb1 = new RadioButton("kmeans");
    rb1.setToggleGroup(group);
    rb1.setSelected(false);
 //   rb1.setDisable(true);
    final RadioButton rb2 = new RadioButton("Linear Regression");  
    rb2.setToggleGroup(group);
    rb2.setSelected(false);
  //  rb2.setDisable(true);
    final RadioButton rb3 = new RadioButton("Logistic Regression");
    rb3.setToggleGroup(group);
    rb3.setSelected(true);
    
 
  //  file.setInitialDirectory(fileDir);
    
    file.setTitle("Select File for Training & Testing Data");
    file.getExtensionFilters().addAll( new ExtensionFilter("CSV Files", "*.csv"));
   
    
    btn1.setOnAction(new EventHandler<ActionEvent>() {
    	@FXML
		public void handle(ActionEvent e) {	
			
			File upload = file.showOpenDialog(primaryStage);
			
		    if (upload !=null) {
		    	Desktop desktop = Desktop.getDesktop();
		    	 pane.setCenter(browser);
		    	 		    	
		    	if (rb1.isSelected()){
		    		
		    		readCSV(listView, upload);
					   
					   runML(upload);
		    				    		 	    			
		    			webEngine.load("http://localhost:3000/d/-S_J6QtWk/db-upload?openVizPicker&orgId=1");
		    			
		    		//	kmeansC(upload);				    	
				    }
				    
				    if (rb2.isSelected()) {
				    	
				    	readCSV(listView, upload);
						   
						   runML(upload);
					    	
				  //  	webEngine.load("http://localhost:3000/d/wVCMrQpZz/dashboard-1?openVizPicker&orgId=1&fullscreen&edit&panelId=6");				    	
				 //   	linearR(upload);			    					    	
				    }
				    
				   if (rb3.isSelected()) {
					   
					   
					   // gets the headers oS
					   readCSV(listView, upload);
					   
					   runML(upload);
					   
					   webEngine.load("http://localhost:3000/d/BhqEhITZz/new-dashboard-copy2?orgId=1");	
					   
			//		   webEngine.load("https://grafana.com/orgs/riz");
					   
				    }
				   
				   try {
						desktop.open(upload);
					
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					   				   
		    }		   		    
		 }
    
		private void runML(final File upload) {
			// TODO Auto-generated method stub
			
			btn2.setOnAction(new EventHandler<ActionEvent>() {
		    	 @FXML 	
				public void handle(ActionEvent e) {
					
				//	File upload =  file.showOpenDialog(primaryStage);
					
					if (listView != null && rb3.isSelected()) {
					ObservableList<String> selectedItems =  listView.getSelectionModel().getSelectedItems();	
						
					if (!selectedItems.isEmpty()) {
		            for(String s : selectedItems){
		                System.out.println("selected columns " + s);
		            }		            
					logisticRegression(selectedItems,upload);
					}
					}
					
					if (listView != null && rb2.isSelected()) {
						ObservableList<String> selectedItems =  listView.getSelectionModel().getSelectedItems();	
							
						if (!selectedItems.isEmpty()) {
			            for(String s : selectedItems){
			                System.out.println("selected columns " + s);
			            }		            
						logisticRegression(selectedItems,upload);
						}
						}
					
					if (listView != null && rb1.isSelected()) {
						ObservableList<String> selectedItems =  listView.getSelectionModel().getSelectedItems();	
							
						if (!selectedItems.isEmpty()) {
			            for(String s : selectedItems){
			                System.out.println("selected columns " + s);
			            }		            
						logisticRegression(selectedItems,upload);
						}
						}				
					
			}});			
		}});  
        
    tile.setPadding(new Insets(10, 10, 10, 10));
    tile.setPrefColumns(2);
    tile.setStyle("-fx-background-color: DAE6F3;");
    
    VBox vbox = new VBox(8); // spacing = 8
    vbox.getChildren().addAll(rb1, rb2, rb3, btn1, btn2, img1);
    
    HBox hbox = new HBox();

    ObservableList selectedIndices =
    	    listView.getSelectionModel().getSelectedIndices(); 
    
    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    
   hbox.getChildren().add(listView);
    
    tile.getChildren().addAll(vbox,hbox);
    
    
    pane.setTop(greeting);
    pane.setLeft(tile);
    pane.setCenter(img1);
    
    Scene scene = new Scene(pane, 900, 500);
    
    primaryStage.setTitle("Apache Spark MLlib Riz Kler ICE");
    primaryStage.setResizable(true);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.setScene(scene);

    primaryStage.show();     
  
  }

public static void main(String[] args) {
    launch(args);
  }

//@SuppressWarnings("unchecked")
private void readCSV(ListView<String> listView,  File upload) {
	
	ObservableList<String> list = FXCollections.observableArrayList();
	
	 try {
         String[] headers = null;
         BufferedReader bufferedReader = new BufferedReader (new FileReader(upload));
         String header = bufferedReader.readLine();
         if (header != null) {
             headers = header.split(",");
         }
         for (int i = 0; i < headers.length; i++) {
             System.out.println(headers[i]);         
        	       	 
        	 list.add(headers[i].toLowerCase());
        	 	
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
	 
	 listView.setItems(list);		 	 
}

private void kmeansC(File upload) {
			
		String[] uploadStr1 = null;
		try {
			uploadStr1 = new String[] {FileUtils.readFileToString(upload)};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	//KmeansClustering.main(uploadStr1);
	
	
	System.out.println("KMeans Training & Testing Datafile Uploaded");	
	
}

private void linearR(File upload) {
	
	String[] uploadStr2 = null;
	
	try {
		uploadStr2 = new String[] {FileUtils.readFileToString(upload)};
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//LinearMarketingVsSales.main(uploadStr2);
		
	System.out.println("Linear Regression Training & Testing Datafile Uploaded");	
	
}

private void logisticR(File upload) {

	String[] uploadStr3 = null;
	try {
		uploadStr3 = new String[] {FileUtils.readFileToString(upload)};
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	//LogisticRegressionExample.main(null);
	
	System.out.println("Logistic Regression Training & Testing Datafile Uploaded");	
	
}

@SuppressWarnings("null")
private void logisticRegression(ObservableList<String> selectedItems, File upload) {
	 	
	Logger.getLogger("org").setLevel(Level.ERROR);
	Logger.getLogger("akka").setLevel(Level.ERROR);
	
	//System.out.println("after Logger before Sparksession");
	
	SparkSession spark = new SparkSession.Builder()
			.appName("Logistic-Regression MLlib")
			.master("local")
			.getOrCreate();
	
	//System.out.println("after Sparksession before loading file model");
	
		
	Dataset<Row> treatmentDf = spark.read()
			.option("header", "true")
			.option("inferSchema", "true")
			.format("csv")
			
						
			.load(upload.getAbsolutePath());
	
	System.out.println("Datafile uploaded");
	
	 for(String s : selectedItems){
       //  System.out.println("selected item " + s);
	 }
	 
	 String [] str = new String [selectedItems.size()];
	 
	 List<String> filterColumns = new ArrayList<String>();
	 
	// Seq<String> seqStr;
	 
	 for (int i =0; i<= selectedItems.size()-2; i++)
	 {
		 str[i] = selectedItems.get(i).toLowerCase();
		 
		 filterColumns.add(new String(selectedItems.get(i)));
		 
	 }
	  
	Dataset<Row> lblFeatureDf = treatmentDf.withColumnRenamed(selectedItems.get(selectedItems.size()-1), "label")
		
.select("label", JavaConversions.asScalaBuffer(filterColumns).seq());

	String [] filterColumns2 = new String[filterColumns.size()];
	for (int i=0; i < filterColumns.size (); i++) {
		//System.out.println("count loop " +filterColumns.get(i).toString().toLowerCase());
		filterColumns2[i] = filterColumns.get(i).toString().toLowerCase();
	}
	
	lblFeatureDf = lblFeatureDf.na().drop();
	
	//StringIndexer genderIndexer = new StringIndexer()
	//		.setInputCol("sex").setOutputCol("sexIndex");
	
	VectorAssembler assembler = new VectorAssembler()

			.setInputCols(filterColumns2)
			.setOutputCol("features");
	
//	System.out.println("VectorAssembler Created");
	
	Dataset<Row> [] splitData = lblFeatureDf.randomSplit(new double[] {.7, .3});
	Dataset<Row> trainingDf = splitData[0];
	Dataset<Row> testingDf = splitData[1];
	
	
	LogisticRegression logReg = new LogisticRegression();
	
	System.out.println("Logisitic Regression Created");
	
	Pipeline pl = new Pipeline();
	//pl.setStages(new PipelineStage [] {genderIndexer, assembler, logReg});
	
	pl.setStages(new PipelineStage [] {assembler, logReg});
	
//	System.out.println("PipelineStages Created");
	
	PipelineModel model = pl.fit(trainingDf);
	
//	System.out.println("PipeLineModel Created");
	
	Dataset<Row> results = model.transform(testingDf);
	
	System.out.println("results created");
	
	results.show();
	
	//TimestampType time;
	
	// Write to destination
			String dbConnectionUrl = "jdbc:mysql://localhost/lr_data";
			
			Properties prop = new Properties();
		    prop.setProperty("driver", "com.mysql.jdbc.Driver");
		    prop.setProperty("user", "root");
		    prop.setProperty("password", "root");
		    
		results.select("prediction", JavaConversions.asScalaBuffer(filterColumns).seq())
	.write()
//	.format("csv")
	.mode(SaveMode.Overwrite)
	
	.jdbc(dbConnectionUrl, "lr_table", prop);
    results.write()    
    .mode(SaveMode.Overwrite)
    
    .format("json")
//    .format("csv")
//  
    .save("/Users/rizwa/Documents/workspace/sparkwithjava-master/project9/logregModel/LogisticRegression");	
    
    System.out.println("saving model");
	
}
}

