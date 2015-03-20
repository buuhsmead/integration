#  Simple Timer #
 
## Build and deploy on fuse server

1) create the file FUSE_DIR/etc/simpletimer.cfg with contents: 
simpletimer.period=1000

2) mvn clean install

3) cp simpletimer-1.0.0-SNAPSHOT.jar  FUSE_DIR/deploy/

4) the FUSE_DIR/data/log/fuse.log will show the messages


## Creating additional unit tests
a) Each unit test class should extend from integration.camel.CamelIntegrationTestSupport

b) implement the method "routes()" to return an instance of your routes

c) implement the method "propertyLocation() to specify your property file for this test.