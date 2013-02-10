hawkthorne-client-java
======================
# Setting up Your Environment


1. Download the most recent version of [The Eclipse IDE for Java EE Developers](http://www.eclipse.org/downloads/)
2. Navigate to http://code.google.com/p/libgdx/wiki/ProjectSetupNew to set up your environment
3. Fork this repository by clicking the 'Fork' button on the top of this page
4. Clone this repository by clicking the 'Clone ...' button on this page
5. Open up Eclipse
6. Go to File > New > Project 
7. Select Java Project and click `Next`
8. Unselect "Use default location" and browse to wherever you cloned this repository
9. Click Finish
10. In the menu bar go to Windows > Show View > Other > General > Project Explorer
11. Right click the project and go to Properties
12. In the Java Build Path Tab add gdx, gdx-audio, gdx-backends-lwjgl, and gdx-maps
13. Select OK

# Running the Client without Eclipse:

[Multiplayer Hawkthorne](http://nimbusbp1729.github.com/hawkthorne-server-lua/)

## Required Files

1. [client.love](https://dl.dropbox.com/u/13978314/hawkthorne/client.love)
2. [server.love](https://dl.dropbox.com/u/13978314/hawkthorne/server.love)

## Optional Files

1. [javaClient.zip](https://dl.dropbox.com/u/13978314/hawkthorne/javaClient.zip)


## Execution instructions
1. put server.love and client.love in an empty folder
2. double click server.love
3. double click client.love

## Optional(assumes you've done step 2)
4. extract javaClient.zip's 2 files into another empty folder
5. double click client.jar
