#       Project: eFactory
#     Component: Apache Spark
# Sub-component: Grafana
# 
# Installing:
# 		jdk 1.8.1
#		Apache Spark
#		Spark MLlib
#		Java App
#
#

FROM tomcat:latest


RUN mkdir -p /usr/lib/app

WORKDIR /usr/lib/app

#FROM httpd:2.4
#COPY /src/ /usr/local/apache2/htdocs/

#FROM bde2020/spark-submit:2.1.0-hadoop2.8-hive-java8

# Install maven
RUN apt-get update
RUN apt-get install -y maven

# Install MySQL
#RUN apt-get update
#RUN apt-get install -y mysql

# Install apt-utils
RUN apt-get update
RUN apt-get install -y apt-utils
RUN apt-get install -y curl


# Prepare by downloading dependencies
ADD pom.xml /usr/lib/app/pom.xml

RUN ["mvn", "clean"]
RUN ["mvn", "install"]
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD src /usr/lib/app/src
RUN ["mvn", "package"]


COPY target/project9-0.0.1-SNAPSHOT.jar usr/lib/app/riz-spark-project.jar

ENV SPARK_MASTER_NAME spark-master
ENV SPARK_MASTER_PORT 4567
ENV SPARK_APPLICATION_JAR_LOCATION /usr/lib/app/riz-spark-project.jar
ENV SPARK_APPLICATION_MAIN_CLASS com.jobreadyprogrammer.spark
ENV SPARK_APPLICATION_ARGS ""

# Folder to store the graphs & models from the ML algorithms
COPY images /usr/lib/app/images
COPY models /usr/lib/app/models
COPY temp /usr/lib/app/temp

# Make port 80 available to the world outside this container
EXPOSE 4567
CMD ["usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/project9-0.0.1-SNAPSHOT.jar"]
