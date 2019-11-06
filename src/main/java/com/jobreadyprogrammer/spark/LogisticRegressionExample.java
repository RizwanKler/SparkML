package com.jobreadyprogrammer.spark;

//import static org.apache.spark.sql.functions.concat;
//import static org.apache.spark.sql.functions.lit;


import java.util.Properties;

//import javax.activation.FileDataSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
//import org.apache.spark.sql.execution.datasources.csv.CSVDataSource;
//import org.javafxdata.datasources.reader.FileSource;  
//import org.javafxdata.datasources.provider.CSVDataSource;


public class LogisticRegressionExample {

	public static void main(String[] args) {

		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		
		// Just loading the file...  
	//       FileDataSource fs = new FileDataSource("/Users/rizwa/Documents/Spark Course/data/cryotherapy.csv");  
	       // Now creating my datasource 
		// need to check and test tableview and csvdatasource type compatibility and it should work
	       // check the use of taleview with arraystring
	       
	 //      String[] columns = new String[7];
	       
	//	CSVDataSource dataSource = new CSVDataSource("/Users/rizwa/Documents/Spark Course/data/cryotherapy.csv", columns);
				
			
				///(fs, "Result_of_Treatment", "sex","age","time","number_of_warts","type","area");  
		
		
		SparkSession spark = new SparkSession.Builder()
				.appName("Logistic-Regression MLlib")
				.master("local")
				.getOrCreate();
		
		Dataset<Row> treatmentDf = spark.read()
				.option("header", "true")
				.option("inferSchema", "true")
				.format("csv")
		//		.load(args);
		
		
				.load("/Users/rizwa/Documents/Spark Course/data/cryotherapy.csv");
		
		Dataset<Row> lblFeatureDf = treatmentDf.withColumnRenamed("Result_of_Treatment", "label")
			.select("label", "sex","age","time","number_of_warts","type","area");
		
		lblFeatureDf = lblFeatureDf.na().drop();
		
		StringIndexer genderIndexer = new StringIndexer()
				.setInputCol("sex").setOutputCol("sexIndex");
	
		VectorAssembler assembler = new VectorAssembler()
				.setInputCols(new String [] {"sexIndex", "age", "time", "number_of_warts", "type", "area"})
				.setOutputCol("features");
		
		
		Dataset<Row> [] splitData = lblFeatureDf.randomSplit(new double[] {.7, .3});
		Dataset<Row> trainingDf = splitData[0];
		Dataset<Row> testingDf = splitData[1];
		
		LogisticRegression logReg = new LogisticRegression();
		
		Pipeline pl = new Pipeline();
		pl.setStages(new PipelineStage [] {genderIndexer, assembler, logReg});
		
		PipelineModel model = pl.fit(trainingDf);
		Dataset<Row> results = model.transform(testingDf);
		
	//	WebView.results.
		results.show();
		
		// Write to destination
				String dbConnectionUrl = "jdbc:mysql://localhost/lr_data";
				
				Properties prop = new Properties();
			    prop.setProperty("driver", "com.mysql.jdbc.Driver");
			    prop.setProperty("user", "root");
			    prop.setProperty("password", "root");
	
		
		//Transformation
			    
			    
	//	resultsDB = spark.sql(dbConnectionUrl)
		results.select("sex", "age", "number_of_warts", "area", "type", "sexIndex", "prediction")
		.write()
	//	.format("csv")
		.mode(SaveMode.Overwrite)
	
		 .jdbc(dbConnectionUrl, "lr_table", prop);
		
	 

	    results.write()
	    
	    
	    .mode(SaveMode.Overwrite)
	    .format("json")
//	    .format("csv")
//	    
	  .save("/Users/rizwa/Documents/Spark Course/data/IncomingStockFiles/logregModel");

		
	}

}
