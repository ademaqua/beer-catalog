# BEER CATALOG
Fancy a beer? We want you to be informed on what beers and manufacturers we have available in our app.

You can follow the steps to have your own beer catalog running!

## Building the aplication
### Using your local java environment
If you have your JDK (at least 11+) and Maven (3+) running on your local machine, you can compile and run the app!
Just follow the next steps:

1. Go to the root folder where you have cloned this repository using the command line prompter of your preference
2. Compile the code using the following command:


    mvn clean install


3. You will see that a new **_target_** folder has been created. Go inside. 
4. After the previous step has been completed, you can run the following command:


    java -jar beer-catalog-*.jar


5. And that's it! Congratulations. You can use a faster way of starting the application using on the project root folder
    
    
    mvn spring-boot:start


### Using Dockerfile
If you don't have JDK installed in your computer, DON'T PANIC! You can use Docker as well to use this application.

To be able to use it, you will need Docker installed (duh!) and follow the next steps:

1. Go to the root folder of the project you have cloned and you will se a Dockerfile file. Just write


    docker build -t beer-catalog .


2. With this, all necessary technologies will be included in the image and we can run the catalog writing:


    docker run -p{external_port}:9091 beer-catalog


3. Where external_port is the port you want to use for this application.
4. You will have a functional containerized application working!
5. If you don't want to download the code and want to use this catalog as well, you will need to do the following:


    docker run -p{external_port}:9091 ademaqua/beer-catalog

6. And the Docker image will be automatically pushed into your computer and executed.