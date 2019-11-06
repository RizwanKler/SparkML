package com.jobreadyprogrammer.spark;

//import java.io.File;
import java.util.Properties;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

public class KmeansClustering {

	public static void main(String[] args) {
			
		
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		
		SparkSession spark = new SparkSession.Builder()
				.appName("kmeans Clustering")
				.master("local")
				.getOrCreate();
		
		Dataset<Row> wholeSaleDf = spark.read()
				.option("header", "true")
				.option("inferSchema", "true")
				.format("csv")
				.load(args);
		
		
		
//				.load("/Users/rizwa/Documents/Spark Course/data/Wholesale-customers-data.csv");
		
		
		wholeSaleDf.show();
		Dataset<Row> featuresDf = wholeSaleDf.select("channel", "fresh", "milk", "grocery", "frozen", "detergents_paper", "delicassen");
		
		VectorAssembler assembler = new VectorAssembler();
		assembler = assembler.setInputCols(new String[] {"channel", "fresh", "milk", "grocery", "frozen", "detergents_paper", "delicassen"})
				.setOutputCol("features");
		
		Dataset<Row> trainingData = assembler.transform(featuresDf).select("features");
		
		KMeans kmeans = new KMeans().setK(4);
		
		KMeansModel model = kmeans.fit(trainingData);
		
		System.out.println(model.computeCost(trainingData));
		model.summary().predictions().show();
		
		String dbConnectionUrl = "jdbc:mysql://localhost/lr_data";
		Properties prop = new Properties();
		
			    prop.setProperty("driver", "com.mysql.jdbc.Driver");
			    prop.setProperty("user", "root");
			    prop.setProperty("password", "root"); 
			    
			    
			    model.summary().predictions().select("prediction").write()
		//	    .format("json")
			    .mode(SaveMode.Overwrite)
			    
			    .jdbc(dbConnectionUrl, "km_table", prop);		
	
	}
	

}
