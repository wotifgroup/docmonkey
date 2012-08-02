# Introduction

docmonkey makes diagrams from a tiny bit of your input, to let you get on with real work.

This app is an Omnigraffle Firehose!!

# Overview

# Quick Start
- Startup Omnigraffle 5
- Run the Application (see below)
- curl -XPOST -H"Content-type: application/json" localhost:8080/generate -d@src/main/resources/sample.json

This will *eventually* create basic diagrams in the exports dir, without styles: in the following formats:
- png
- html referencing pngs
- ascii art
- pdf
- svg

*NOTE*: On first run, you are likely to get a warning about file saving.
*NOTE*: No effort has been spent on supporting concurrent requests, queueing up jobs, ie. multi-user support for document generation.

If it doesn't look like the picture, then check the default template configuration in Omnigraffle. The auto layout features of Omnigraffle are needed.

Html pages with pngs are now accessible via: (TBD)

* Acknowledgements
- good old posts in omnigraffle forums: sunnysight 2009; Charles-Axel Dein and others.
- from http://forums.omnigroup.com/showthread.php?t=106&highlight=export+posix

# Configure

# Running The Application

To try the example application run the following commands.

* To package the example run.

        mvn package

* To run the server run.

        java -jar target/dropwizard-DocMonkey-0.1.0-SNAPSHOT.jar server example.yml


* Put the monkey to work
- curl -XPOST -H"Accept: text/html" -H"Content-type: application/json" localhost:8080/generate -d@src/main/resources/sample.json
- curl localhost:8080/exports/index.html
