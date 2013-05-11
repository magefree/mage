MAGE - is an acronym for Magic, Another Game Engine

MAGE is a client/server implementation of a popular CCG without the collecting part.
The server hosts games and enforces the rules.  The client creates or joins games, 
displays the current state of a game in progress and sends user events to the server.

You will need to have the Java Runtime Environment Version 6 Update 33 or greater.
You can download this from:  http://java.com/

-----------------------------------------------------------------------------------
Installing and running MAGE

You will need to download both the client and the server applications.  These can be
obtained from 
http://download.magefree.com
or
http://176.31.186.181/
Extact the client and the server to separate folders.

To play a game you can either connect to a server or start your own server.  To
connect to a server you will need to know the server name or IP address and the port.
To start a server run the startServer.bat command.  If you want to use a different 
port or change the timeout setting then modify the config.properties file in the 
config folder.

To launch the client run the startClient.bat command.  Click on the connect button on
the toolbar and enter the server name/IP address and port.  Then click on the tables
button.  This will bring up a list of active and completed games.  Click on join to
join an existing game that hasn't started yet or you can create a new table by
clicking the New button.

-----------------------------------------------------------------------------------
Playing a game

Playing a game should be fairly self evident.  Your hand is displayed at the bottom
of the screen.  The battlefield is the central area.  Click on cards in your hand to
play them.  Click on cards in the battlefield to activate abilities.  A popup menu
will be presented if you have more than one choice.  To pass priority for the turn
hold down the ctrl key while clicking done.  You will still receive priority if
your opponent casts a spell or activates an ability.  Target cards by clicking on
them.  To target a player click on the player name.  You can see the cards in any
graveyard by clicking on the graveyard count.

-----------------------------------------------------------------------------------
Deck editor

A simple deck editor is available by clicking on the Deck Editor button on the 
toolbar.  All the available cards are displayed in the top section.  Your deck
and sideboard are displayed at the bottom.  To add a card to your deck double
click on the card in the top section.  To remove it from your deck double click
on the card in the deck area.  The sideboard section is not ready yet (don't 
worry it's coming soon).

You can save or load a deck  using the Save or Load buttons.

-----------------------------------------------------------------------------------
Notes

MAGE is still very much in the testing phase so there can be lots of bugs and/or
missing functionality.  Please be patient.  If you notice anything or want to
make suggestions goto https://github.com/magefree/mage/issues

