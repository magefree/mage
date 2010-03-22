MAGE - is an acronym for Magic, Another Game Engine

MAGE is a client/server implementation of a popular CCG without the collecting part.
The server hosts games and enforces the rules.  The client creates or joins games, 
displays the current state of a game in progress and sends user events to the server.

You will need to have the Java Runtime Environment Version 6 Update 10 or greater.
You can download this from:  http://java.com/

-----------------------------------------------------------------------------------

You will need to download both the client and the server applications.  These can be
obtained from http://code.google.com/p/magic--another-game-engine/downloads/list

To play a game you can either connect to a server or start your own server.  To
connect to a server you will need to know the server name or IP address and the port.
To start a server run the startServer.bat command.  If you want to use a different 
port or change the timeout setting then modify the config.properties file in the 
config folder.

To launch the client run the startClient.bat command.  Click on the connect button on
the toolbar and enter the server name/IP address and port.  Then click on the tables
button.  This will bring up a list of active and completed games.  Click on join to
join an existing game that hasn't started yet or you can create a new table by
clicking the new button.

-----------------------------------------------------------------------------------