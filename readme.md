# XMage — Magic, Another Game Engine

[![Join the chat at https://gitter.im/magefree/mage](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/magefree/mage?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Build Status](https://travis-ci.org/magefree/mage.svg?branch=master)](https://travis-ci.org/magefree/mage)

XMage allows you to play Magic against one or more online players or computer opponents. It includes full rules enforcement for over **19.200** unique cards (over 37.400 counting all cards from different editions, you can find event custom sets like Star Wars). All regular sets have nearly all the cards implemented. A more detailed information which cards are implemented can be found [here](https://github.com/magefree/mage/wiki/Set-implementation-list). 

It's support single matches and tournaments with dozens game modes like duel, multiplayer, standard, modern, commander, pauper, oathbreaker, freeform and much more.

There are [public servers](http://xmageservers.online/) where you can play XMage against other players. You can also host your own server to play against the AI and/or your friends.

XMage community:
* [Official XMage forum](http://www.slightlymagic.net/forum/viewforum.php?f=70);
* [Official XMage support and feature request on github](https://github.com/magefree/mage/issues);
* [Reddit XMage group](https://www.reddit.com/r/XMage/);
* [Reddit XMage discord channel](https://discord.gg/Pqf42yn).

Servers status:
* http://xmageservers.online/

## Features

* Multiplatform app: Windows, Linux, MacOS;
* Deck editor (support multiple deck formats and deck sources);
* Two player duel or a multiplayer free-for-all game with up to 10 players;
* Computer AI opponents;
* Players rating system (Glicko);
* Supports special formats like Commander (up to 10 players), Oathbreaker, Cube, Tiny Leaders, Super Standard, Historic Standard and more;
* Single matches or tournaments supported (elimination or swiss type handling), which can be played with up to 16 players:
    * Booster (also Cube) draft tournaments (4-16)
    * Sealed (also from Cube) tournaments (2-16)

## Issues / bugs

Before you create a new issue, take a look at the [List of things already fixed but not yet released](https://github.com/magefree/mage/wiki/Features-and-fixes-not-released-yet#features-and-fixes-not-released-yet) to avoid creating uneccessary new issues.
Also there is always a bug thread in the [Official XMage forum](http://www.slightlymagic.net/forum/viewforum.php?f=70) which we check regularly.

## Installation

* Download [latest XMage launcher file](http://xmage.de) and put it to any folder like `D:\games\xmage`;
* You need to have [Java version 8 or later](http://java.com/) to run it;
* If you can't run it then create `run-LAUNCHER.cmd` text file in launcher folder and put that line to it and save as ANSI format:
  * `java -Djava.net.preferIPv4Stack=true -jar XMageLauncher-0.3.8.jar` 

Look [here](http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=13632) for more detailed instructions.
[Here](http://github.com/magefree/mage/wiki/Release-changes) you can find a log of the latest changes.

## Developer

If you are interested in developing XMage, here are some useful resources:

* [Developer Getting Started](http://github.com/magefree/mage/wiki/Developer-Getting-Started)
* [Developer Notes](http://github.com/magefree/mage/wiki/Developer-Notes)
* [Developer Testing Tools](http://github.com/magefree/mage/wiki/Developer-Testing-Tools)
* [Double Faced Cards](http://github.com/magefree/mage/wiki/Double-Faced-Cards)
* [Card Requests](https://www.slightlymagic.net/forum/viewtopic.php?f=70&t=20685)
* [Tournament Relevant Card Requests](http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=14062)
