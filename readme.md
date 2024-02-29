# XMage — Magic, Another Game Engine

[![Build Status](https://app.travis-ci.com/magefree/mage.svg?branch=master)](https://app.travis-ci.com/magefree/mage)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=magefree_mage&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=magefree_mage)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=magefree_mage&metric=coverage)](https://sonarcloud.io/summary/new_code?id=magefree_mage)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=magefree_mage&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=magefree_mage)
[![latest release](https://img.shields.io/github/v/release/magefree/mage)](https://github.com/magefree/mage/releases/)
[![commints since latest release](https://img.shields.io/github/commits-since/magefree/mage/latest)](https://github.com/magefree/mage/commits/)
[![Join the chat at https://gitter.im/magefree/mage](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/magefree/mage?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

XMage allows you to play Magic against one or more online players or computer opponents. 
It includes full rules enforcement for over **25 000** unique cards and more than 65 000 reprints from different editions. 
You can also find custom sets like Star Wars. All regular sets have nearly all implemented cards.

It supports single matches and tournaments with dozens of game modes like duel, multiplayer, standard, modern, commander, 
pauper, oathbreaker, historic, freeform, richman and much more.

Local server supports a [special test mode](https://github.com/magefree/mage/wiki/Development-Testing-Tools) for 
testing combos and other game situations with pre-defined conditions.

There are [public servers](http://xmage.today/servers/) where you can play XMage against other players. 
You can also host your own server to play against the AI and/or your friends.

XMage community and resources:
* [Official XMage support and feature request on github](https://github.com/magefree/mage/issues);
* [Official XMage forum](http://www.slightlymagic.net/forum/viewforum.php?f=70) (outdated);
* [Reddit XMage group](https://www.reddit.com/r/XMage/);
* [Reddit XMage discord channel](https://discord.gg/Pqf42yn);
* [Latest changes](https://github.com/magefree/mage/commits/master);
* [Latest news](https://jaydi85.github.io/xmage-web-news/news.html);
* [Latest releases](https://github.com/magefree/mage/releases); 
* [Project documentation](https://github.com/magefree/mage/wiki).

Servers status:
* http://xmage.today/servers/ (temporarily out of service)

Beta server with un-released or under development features:
* http://xmage.today/ (release version)

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

## Bug report / feature request

The best way to report bug or feature request is [github's issues page](https://github.com/magefree/mage/issues).

## Installation / running

* Download [latest XMage launcher and app files](http://xmage.today/) and un-pack it to any folder like `D:\games\xmage`;
* You need to have [Java version 8 or later](http://java.com/) to run launcher.
  
[Wiki page](https://github.com/magefree/mage/wiki) contains detail information about private or public server setup.

### Server options

The XMage server locates by default the configuration file from the current working directory to the relative path `config/config.xml`
(`config\config.xml` in Windows). To change this location, start the server with the property `xmage.config.path` set
to the desired location, for example `-Dxmage.config.path=config/otherconfig.xml`. The option can be set from the 
XMageLauncher in `Settings > Java > Server java options`.

## Troubleshooting / FAQ

Github issues page contain [popular problems and fixes](https://github.com/magefree/mage/issues?q=is%3Aissue+label%3AFAQ+):
* [Any: program freezes on startup (white/blue/black screen)](https://github.com/magefree/mage/issues/4461#issuecomment-361108597);
* [Any: can't download images or it stops after some time](https://www.reddit.com/r/XMage/comments/agmcjf/new_xmage_release_with_ravnica_allegiance_rna/); 
* [Any: can't run client, could not open ...jvm.cfg](https://github.com/magefree/mage/issues/1272#issuecomment-529789018);
* [Any: no texts or small buttons in launcher](https://github.com/magefree/mage/issues/4126);
* [Windows: ugly cards, buttons or other GUI drawing artifacts](https://github.com/magefree/mage/issues/4626#issuecomment-374640070);
* [MacOS: can't run on M1/M2](https://github.com/magefree/mage/issues/8406#issuecomment-1011720728);
* [MacOS: can't open launcher](https://www.reddit.com/r/XMage/comments/kf8l34/updated_java_on_osx_xmage_not_working/ggej8cq/);
* [MacOS: client freezes in GUI (on connect dialog, on new match)](https://github.com/magefree/mage/issues/4920#issuecomment-517944308);
* [Linux: run on non-standard OS or hardware like Raspberry Pi](https://github.com/magefree/mage/issues/11611#issuecomment-1879385151);
* [Linux: ugly GUI and drawing artifacts](https://github.com/magefree/mage/issues/11611#issuecomment-1879396921);

## Performance tweaks

If you have a good GPU, it's very likely you'll be able to increase performance by **a lot** through extra Java flags.

XMage runs on JRE 8, so [this link](https://docs.oracle.com/javase/8/docs/technotes/guides/2d/flags.html) should give you the available flags.

### How to enable the extra flags

1. Launch XMage
2. In the menu bar from the launcher, click on "Settings", which will open up the Settings Window
3. Go to the "Java" tab
4. You can pass extra flags by editing the "Client java options" text field

### Linux tweaks

#### Enable OpenGL

More info about [OpenGL](https://docs.oracle.com/javase/8/docs/technotes/guides/2d/flags.html#opengl):
* `-Dsun.java2d.opengl=true`

Possible problems:
* _**Caveat**_: [There's a bug](https://bugs.openjdk.java.net/browse/JDK-6545140) with the file chooser when OpenGL is enabled (you use the file chooser when you, for instance, try to load a deck from disk). The [suggested workaround](https://bugs.java.com/bugdatabase/view_bug.do?bug_id=6439320) will make the game crash, so it's not an option.
* _**Workaround**_: When using [i3](https://github.com/i3/i3), you're able to work around the bug by toggling the "floating" capabilities of the window and forcing it to re-render.

#### Enable XRender

More info about [XRender](https://docs.oracle.com/javase/8/docs/technotes/guides/2d/flags.html#xrender):
* `-Dsun.java2d.xrender=true`

#### Increase memory usage

This is not guaranteed to yield improvements, but it depends on your use-case. 
If you have a lot of RAM to spare, you can increase the initial heap size for good measure.
More details about [memory settings](https://stackoverflow.com/a/57839720/8401696):
* `-Xms1G -Xmx2G`

## Developer

Full project documentation and instructions for developers can be found in [wiki pages](http://github.com/magefree/mage/wiki/). 

First steps for Xmage's developers:
* [Setting up your Development Environment](https://github.com/magefree/mage/wiki/Setting-up-your-Development-Environment)
* [Development Testing Tools](https://github.com/magefree/mage/wiki/Development-Testing-Tools)
* [Development Workflow](https://github.com/magefree/mage/wiki/Development-Workflow)
* [Development HOWTO Guides](https://github.com/magefree/mage/wiki/Development-HOWTO-Guides)

[Torch icons created by Freepik - Flaticon](https://www.flaticon.com/free-icons/torch)
