# XMage — Magic, Another Game Engine

[![Build Status](https://travis-ci.org/magefree/mage.svg?branch=master)](https://travis-ci.org/magefree/mage) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=JayDi85_mage&metric=coverage)](https://sonarcloud.io/dashboard?id=JayDi85_mage) [![latest release](https://img.shields.io/github/v/release/magefree/mage)](https://github.com/magefree/mage/releases/) [![commints since latest release](https://img.shields.io/github/commits-since/magefree/mage/latest)](https://github.com/magefree/mage/commits/) [![Join the chat at https://gitter.im/magefree/mage](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/magefree/mage?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

XMage allows you to play Magic against one or more online players or computer opponents. 
It includes full rules enforcement for over **20 000** unique cards and more than 50.000 reprints from different editions. 
You can also find custom sets like Star Wars. All regular sets have nearly all implemented cards.

It's support single matches and tournaments with dozens game modes like duel, multiplayer, standard, modern, commander, 
pauper, oathbreaker, historic, freeform and much more.

Local server supports a [special test mode](https://github.com/magefree/mage/wiki/Development-Testing-Tools) for 
testing combos and other game situations with pre-defined conditions.

There are [public servers](http://xmage.today/servers/) where you can play XMage against other players. 
You can also host your own server to play against the AI and/or your friends.

XMage community and resources:
* [Official XMage forum](http://www.slightlymagic.net/forum/viewforum.php?f=70);
* [Official XMage support and feature request on github](https://github.com/magefree/mage/issues);
* [Reddit XMage group](https://www.reddit.com/r/XMage/);
* [Reddit XMage discord channel](https://discord.gg/Pqf42yn);
* [Latest releases](https://github.com/magefree/mage/releases);
* [Latest news](https://jaydi85.github.io/xmage-web-news/news.html);
* [Project documentation](https://github.com/magefree/mage/wiki).

Servers status:
* http://xmage.today/servers/

Beta server with un-released or under development features:
* http://xmage.today/

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
There are another way to report bugs too:
* Feedback from xmage app's main menu;
* Bug thread in the [Official XMage forum](http://www.slightlymagic.net/forum/viewforum.php?f=70).  

## Installation / running

* Download [latest XMage launcher file](http://xmage.de) and put it to any folder like `D:\games\xmage`;
* You need to have [Java version 8 or later](http://java.com/) to run launcher;
* If you can't run it then create `run-LAUNCHER.cmd` text file in launcher folder and put that line to it and save as ANSI format:
  * `java -Djava.net.preferIPv4Stack=true -jar XMageLauncher-0.3.8.jar`
  * or just [downlod and unpack that archive](http://xmage.today/X/run.zip) to launcher folder.
  
Look [here](http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=13632) for more detailed instructions. 

[Wiki page](https://github.com/magefree/mage/wiki) contains detail information about private or public server setup.

### Server options

The XMage server locates by default the configuration file from the current working directory to the relative path `config/config.xml`
(`config\config.xml` in Windows). To change this location, start the server with the property `xmage.config.path` set
to the desired location, for example `-Dxmage.config.path=config/otherconfig.xml`. The option can be set from the 
XMageLauncher in `Settings > Java > Server java options`.

### Server connection

The server can be configured in two ways: with a single home or with multiple homes. An _home_ is a reachable host which
can be resolved by a client and which accepts connections from the client. For example, `xmage.de:17171` is a home, 
since `xmage.de` is domain which can be resolved to an ip and which has an xmage server backing the socket.

Single home configurations are adequate for LAN deployments and for the deployment of public servers. In fact, in a LAN
it is possible to define as home the ip of the server and in a public server it is possible to define as home the public domain.

Multi home configuration are ideal for NAT backed deployments. Multi home configurations allows, in fact, to bind the server
to a local socket but publish different resolvable IPs or domains for clients to connect to.

In order to configure a server in single home mode, alter the configuration file as follows:

```xml
<config>
    <server serverAddress="<reachable ip or domain>"
            port="<available port>"
            secondaryBindPort="<available port>"
     />
</config>
```

Replace the `serverAddress` with the reachable ip or domain, `port` for the port that servers the application. Depending
on the specific configuration, you might not need to specify a secondary port.

In order to configure a server in multi home mode, alter the configuration file as follows:

```xml
<config>
    <server multihome="true">
        <home internal="<interface of the server>" external="<reachable ip or domain>" port="<port>" />
        <home internal="<interface of the server>" 
              external="<reachable ip or domain>" 
              port="<available port>" 
              externalport="<available port>" 
              secondaryport="<available port>" />
    </server>
</config>
```

The minimal configuration requires to specify the `internal`, `external` and `port` attribute for a home. The way the
home is configured is by indicating as `external` an ip or domain reachable by the clients and as `externalport` an available
and open port on that address. The connections reaching that address are then directed to the interface associate to the socket `internal:port`.
The connection details in the `server` attributes are ignored when `multihome` is enabled.

To exemplify, let's assume that you want to host a server on a computer that sits behind a NAT. For illustration purposes,
let's assume that the lan IP of the server is `192.168.1.2`, the public IP of the server is `9.9.9.9` and that port forwarding
is configured to direct connections reaching port `17171` and `17172` at the public ip to `9.9.9.9` to `192.168.1.2`. Let's also assume
that a client `A` is connecting to the server from the lan and client `B` is connecting to the server from the public internet.

In this setup, the server configuration should be as follows:

```xml
<config>
    <server multihome="true">
        <home internal="0.0.0.0" external="192.168.1.2" port="17173" />
        <home internal="0.0.0.0" 
              external="9.9.9.9" 
              port="17171" 
              secondaryport="17172" />
    </server>
</config>
```

Since the homes don't have `externalport` defined, they are configured to replicate the `port` attribute (i.e. value `17173` nad `17173`).

Using `0.0.0.0` in the internal host binds the server to all interfaces, but you can limit it to a specific interface if you
wish to do so (in this case, `192.168.1.2` would be a valid bind).

Client `A` can then connect to the server using address `192.168.1.2:17173` while client `B` can connect to the server
using address `9.9.9.9:17171`.

The `external` address can be replaced with a valid domain that resolves to the ip if you own such a domain defined.

## Troubleshooting / FAQ

Github issues page contain [popular problems and fixes](https://github.com/magefree/mage/issues?q=is%3Aissue+label%3AFAQ+):
* [Program freezes on startup (white/blue/black screen)](https://github.com/magefree/mage/issues/4461#issuecomment-361108597);
* [Can't download images or it stops after some time](https://www.reddit.com/r/XMage/comments/agmcjf/new_xmage_release_with_ravnica_allegiance_rna/); 
* [MacOS can't open launcher](https://www.reddit.com/r/XMage/comments/kf8l34/updated_java_on_osx_xmage_not_working/ggej8cq/)
* [MacOS client freezes in GUI (on connect dialog, on new match)](https://github.com/magefree/mage/issues/4920#issuecomment-517944308);
* [Ugly cards and GUI drawing in games](https://github.com/magefree/mage/issues/4626#issuecomment-374640070);
* [No texts or small buttons in launcher](https://github.com/magefree/mage/issues/4126);
* [Can't run client, could not open ...jvm.cfg](https://github.com/magefree/mage/issues/1272#issuecomment-529789018).


## Performance tweaks

If you have a good GPU, it's very likely you'll be able increase performance by **a lot** through extra Java flags.

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
If you have a lot of RAM to spare, you might as well increase the initial heap size for good measure.
More details about [memory settings](https://stackoverflow.com/a/57839720/8401696):
* `-Xms1G -Xmx2G`

## Developer

Full project documentation and instructions for developers can be found in [wiki pages](http://github.com/magefree/mage/wiki/). 

First steps for Xmage's developers:
* [Setting up your Development Environment](https://github.com/magefree/mage/wiki/Setting-up-your-Development-Environment)
* [Development Testing Tools](https://github.com/magefree/mage/wiki/Development-Testing-Tools)
* [Development Workflow](https://github.com/magefree/mage/wiki/Development-Workflow)
* [Development HOWTO Guides](https://github.com/magefree/mage/wiki/Development-HOWTO-Guides)
