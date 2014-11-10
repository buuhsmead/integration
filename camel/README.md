# Camel project :
# - convert csv to soap

## usage
build the package using 'mvn package'
rename the package to load.jar
java -cp load.jar  integration.camel.csv2soap.CSV2SOAP
create a properties file 'csv2soap.properties' containing
import.dir=import
output.dir=output
template.dir=templates

url=YOURURL
user=WEBSERVICEUSER
password=WEBSERVICEUSEPASSWORD
