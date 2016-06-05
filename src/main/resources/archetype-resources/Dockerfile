FROM java:8
EXPOSE 8081
ADD /target/${artifactId}.jar ${artifactId}.jar
ENTRYPOINT ["java","-jar","${artifactId}.jar"]