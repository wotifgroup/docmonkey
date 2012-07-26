# Introduction

The (Realities and concerns collide) application was developed to generate documentation. From a mix of automated and personal annotations to generate and public documentation.

The drop wizard example application was developed to, as its name implies, provide examples of some of the features
present in drop wizard.

# Overview

# Quick Start
- Startup Omnigraffle 5
- Run the Application (see below)
- curl -XPOST -H"Content-type: application/json" localhost:8080/generate -d@src/main/resources/sample.json

This will create a really simple diagram,

If it doesn't look like the picture, then check the default template configuration in Omnigraffle. The auto layout features of Omnigraffle are needed.

Html pages with pngs are now accessible via: (TBD)


# Running The Application

To try the example application run the following commands.

* To package the example run.

        mvn package

* To run the server run.

        java -jar target/dropwizard-DocMonkey-0.1.0-SNAPSHOT.jar server example.yml


* Acknowledgements
- good old posts in omnigraffle forums: sunnysight 2009; Charles-Axel Dein and others.
- from http://forums.omnigroup.com/showthread.php?t=106&highlight=export+posix

