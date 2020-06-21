#!/usr/bin/env bash
java -jar /usr/local/webapps/application.jar --server.port=8080
sleep 5000   #Hvis applikasjonen krasjer ved startup kan man legge til sleep for å undersøke kva som gjekk feil