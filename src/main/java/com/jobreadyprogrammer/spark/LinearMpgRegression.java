package com.jobreadyprogrammer.spark;

import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

public class LinearMpgRegression {

	public static void main(String[] args) {
		
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		
		SparkSession spark = new SparkSession.Builder()
				.appName("LinearRegressionMpgExample")
				.master("local")
				.getOrCreate();
		
		SparkSession sparkMDB = new SparkSession.Builder()
				.appName("MongoDB Stream")
				.master("local")
				.getOrCreate();
		
		Dataset<Row> autoMpgDf = spark.read()
				.option("header", "true")
				.option("inferSchema", "true")
				.format("csv")
				.load("/Users/rizwa/Documents/Spark Course/data/auto_mpg.csv");

		autoMpgDf = autoMpgDf.withColumnRenamed("mpg", "label")
			.drop("acceleration")
			.drop("modelYear")
			.drop("origin")
			.drop("carName")
			.drop("displacement");
		
		autoMpgDf = autoMpgDf.na().drop();
		
		String[] featureColumns = {"cylinders", "horsePower", "weight"};
		
		VectorAssembler assembler = new VectorAssembler()
				.setInputCols(featureColumns)
				.setOutputCol("features");
		
		autoMpgDf = assembler.transform(autoMpgDf).select("label", "features");
		
		LinearRegression lr = new LinearRegression();
		LinearRegressionModel lrm = lr.fit(autoMpgDf);
		
		Pipeline pl = new Pipeline()
				.setStages(new PipelineStage[] {lrm});
		
		Dataset<Row> [] splitData = autoMpgDf.randomSplit(new double[] {0.7, 0.3});
		
		Dataset<Row> trainingData = splitData[0];
		Dataset<Row> testData = splitData[1];
		
		PipelineModel model = pl.fit(trainingData);
		
		Dataset<Row> result = model.transform(testData);
		result.show();
		
		// Write to destination
				String dbConnectionUrl = "jdbc:postgresql://localhost/lmpgr_data";
				Properties prop = new Properties();
			    prop.setProperty("driver", "org.postgresql.Driver");
			    prop.setProperty("user", "postgres");
			    prop.setProperty("password", "password"); 
			    
			    
			    result.write()
			    .format("json")
			    .mode(SaveMode.Overwrite)
		    	.jdbc(dbConnectionUrl, "LMpgR", prop); 
		
		}
		
		

}


