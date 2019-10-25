package com.jobreadyprogrammer.spark;

import java.util.Properties;

import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.lit;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

public class LinearMarketingVsSales {

	public static void main(String[] args) {
		
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		
		SparkSession spark = new SparkSession.Builder()
				.appName("LinearRegressionExample")
				.master("local")
				.getOrCreate();
		
		SparkSession sparkMDB = new SparkSession.Builder()
				.appName("MongoDB Stream")
				.master("local")
				.getOrCreate();
		
		Dataset<Row> markVsSalesDf = spark.read()
			.option("header", "true")
			.option("inferSchema", "true")
			.format("csv")
	
			.load(args);
		
//		.load("/Users/rizwa/Documents/Spark Course/data/marketing-vs-sales.csv");
		
		
		markVsSalesDf.show();
		

		Dataset<Row> mldf = markVsSalesDf.withColumnRenamed("sales", "label")
		.select("label", "marketing_spend","bad_day");
		
		String[] featureColumns = {"marketing_spend", "bad_day"};
		
		VectorAssembler assembler = new VectorAssembler()
						.setInputCols(featureColumns)
						.setOutputCol("features");
		
		Dataset<Row> lblFeaturesDf = assembler.transform(mldf).select("label", "features");
		lblFeaturesDf = lblFeaturesDf.na().drop();
		lblFeaturesDf.show();
		
		// next we need to create a linear regression model object
		LinearRegression lr = new LinearRegression();
		LinearRegressionModel learningModel = lr.fit(lblFeaturesDf);
		
		learningModel.summary().predictions().show();
		
		
		System.out.println("R Squared: "+ learningModel.summary().r2());
		
		// Write to destination
				String dbConnectionUrl = "jdbc:mysql://localhost/lr_data";
				Properties prop = new Properties();
				
			    prop.setProperty("driver", "com.mysql.jdbc.Driver");
			    prop.setProperty("user", "root");
			    prop.setProperty("password", "root"); 
			    
	//    markVsSalesDf.write()
			    learningModel.summary().predictions().select("label", "prediction").write()
	//		    .option("header", "true")
			    .format("json")
			    .mode(SaveMode.Overwrite)
			 //   .save("Users/rizwa/Documents/Spark Course/data/IncomingStockFiles/linregModel");
		    	.jdbc(dbConnectionUrl, "linr_table", prop); 
			    
			    learningModel.summary().predictions().select("label", "prediction").write()
						    .option("header", "true")
						    .format("json")
						    .mode(SaveMode.Overwrite)
						    .save("Users/rizwa/Documents/Spark Course/data/IncomingStockFiles/linregModel");
		
	}
}
