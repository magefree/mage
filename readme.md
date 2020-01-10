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

## Performance tweaks

If you have a good GPU, it's very likely you'll be able increase performance by **a lot** through extra Java flags.

XMage runs on JRE 8, so [this link](https://docs.oracle.com/javase/8/docs/technotes/guides/2d/flags.html) should give you the available flags.

### How to enable the extra flags

1. Launch XMage
2. In the menu bar from the launcher, click on "Settings", which will open up the Settings Window
3. Go to the "Java" tab
4. You can pass extra flags by editing the "Client java options" text field

### Linux guide

#### Enable OpenGL

[Link](https://docs.oracle.com/javase/8/docs/technotes/guides/2d/flags.html#opengl)

`-Dsun.java2d.opengl=true`

_**Caveat**_: [There's a bug](https://bugs.openjdk.java.net/browse/JDK-6545140) with the file chooser when OpenGL is enabled (you use the file chooser when you, for instance, try to load a deck from disk). The [suggested workaround](https://bugs.java.com/bugdatabase/view_bug.do?bug_id=6439320) will make the game crash, so it's not an option.

_**Workaround**_: When using [i3](https://github.com/i3/i3), you're able to work around the bug by toggling the "floating" capabilities of the window and forcing it to re-render.

#### Enable XRender

[Link](https://docs.oracle.com/javase/8/docs/technotes/guides/2d/flags.html#xrender)

`-Dsun.java2d.xrender=true`

#### Bigger upfront heap size

[SO explanation](https://stackoverflow.com/a/57839720/8401696)

This is not guaranteed to yield improvements, but it depends on your use-case. If you have a lot of RAM to spare, you might as well increase the initial heap size for good measure.

`-Xms1G -Xmx2G`

## Developer

If you are interested in developing XMage, here are some useful resources:

* [Developer Getting Started](http://github.com/magefree/mage/wiki/Developer-Getting-Started)
* [Developer Notes](http://github.com/magefree/mage/wiki/Developer-Notes)
* [Developer Testing Tools](http://github.com/magefree/mage/wiki/Developer-Testing-Tools)
* [Double Faced Cards](http://github.com/magefree/mage/wiki/Double-Faced-Cards)
* [Card Requests](https://www.slightlymagic.net/forum/viewtopic.php?f=70&t=20685)
* [Tournament Relevant Card Requests](http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=14062)
