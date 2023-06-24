![XMage logo](https://github.com/magefree/mage/blob/master/Mage.Client/src/main/resources/label-xmage.png)
# MAGE - Magic, Another Game Engine

XMage allows you to play Magic against one or more online players or computer opponents!  
It includes **full rules enforcement** for over **25,000** unique cards and more than 65,000 reprints from different editions.  
Starting with Eventide, all regular sets have nearly all their cards implemented ([detailed overview](https://github.com/magefree/mage/wiki/Set-implementation-list)).

You can visit the [XMage forum](http://www.slightlymagic.net/forum/viewforum.php?f=70) for more information.

## Features
* Two player duel or a multiplayer free-for-all
  * Up to 10 players!
* Supports all popular MTG formats
  * EDH/Commander, Modern, Pauper, Pioneer, Legacy, Vintage and Standard
* Supports special formats
  * Cube, Custom Cubes, Jumpstart, Super Standard, Historic Standard, Historic, Canadian Highlander, Richman Cube, Richman Draft, Custom Jumpstart, Freeform Commander
  * Even supports draft logs! [(supported deck formats)](https://github.com/magefree/mage/wiki/Supported-deck-formats)
* Deck editor, with easy to use import/export
  * Import from clipboard
* Simple AI computer opponent
* 2 supported tournament types with up to 16 players! (elimination or swiss)
  * Booster- and Cubedraft tournaments (4-16)
  * Sealed- and Cubedraft tournaments (2-16)
  * Draft from e.g. Zendikar, Khans of Tarkir, Worldwake
  * Cubedraft from e.g. Vintage Cube 2022, Legacy Cube 2020

There are public servers where you can play XMage against other players. You can also host your own server to play against the AI and/or your friends.  
Local server supports a [special test mode](https://github.com/magefree/mage/wiki/Development-Testing-Tools) for testing combos and other game situations with pre-defined conditions.

## Installation
* Install version 8 or later of the [Java Runtime Environment](http://java.com/en/).
* Download and install the [latest XMage release](http://xmage.today/).

Look here for more [detailed instructions](http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=13632).  
Look here for the latest [release changes](https://github.com/magefree/mage/wiki/Release-changes)

**If you just want to play, you can do that now without worrying about anything else on this page**  

## Server configuration
The server application can be run on your local computer, local or public network, cloud hosting and another environments. Look here for more details about [server configuration](https://github.com/magefree/mage/wiki/Server-configuration). You can find different examples below.

#### Running local server
* If you want to play with AI opponent on your own computer then no needs in any settings -- just run it from the launcher and connect to `localhost` or `127.0.0.1`;
* Look [here](https://github.com/magefree/mage/issues/5597#issuecomment-465647095) for instructions to setup local server and play it with friends (computers with router/NAT);
* Look [here](https://www.reddit.com/r/XMage/comments/g97vcn/guide_to_hosting_a_private_server/) for instructions to setup Windows machine and play it with friends;
* Look [here](https://github.com/magefree/mage/issues/6381#issuecomment-632513840) for instructions to setup server without domain name and host file modification for users;
* Look [here](https://www.reddit.com/r/XMage/comments/ebz4fd/guide_xmage_as_a_service_on_ubuntu/) for instructions to setup server as a service in linux base systems like Ubuntu;

#### Running public server
* Look [here](https://github.com/magefree/mage/issues/5305#issuecomment-419691369) for instructions to run cloud server with docker (like DigitalOcean);
* Look [here](https://www.slightlymagic.net/forum/viewtopic.php?f=70&t=15898) for instructions to run own server;
* Look [here](https://github.com/magefree/mage/issues/5388#issuecomment-429671118) for instructions to run admin panel to manage server's users and tables;
* Look [here](https://github.com/magefree/mage/issues/586#issuecomment-57984707) for recommended settings on big servers (with 50+ online users).

## Developer

If you are interested in developing XMage, here are some useful resources:
* [Project Overview](https://github.com/magefree/mage/wiki/Project-Overview)
* [Setting up your Development Environment](https://github.com/magefree/mage/wiki/Setting-up-your-Development-Environment)
* [Development Workflow](https://github.com/magefree/mage/wiki/Development-Workflow)
* [Development HOWTO Guides](https://github.com/magefree/mage/wiki/Development-HOWTO-Guides)
* [Developer Testing Tools](https://github.com/magefree/mage/wiki/Development-Testing-Tools)
* [Creating a new release](https://github.com/magefree/mage/wiki/Creating-a-new-release-for-xmage)
* [Mage Updater](https://github.com/magefree/mage/wiki/Mage-Updater)
* [Hosting an xMage server](https://github.com/magefree/mage/wiki/Hosting-an-XMage-server)

### Developer Extra
* [List of cards that will not implemented on xmage (but you can try)](https://github.com/magefree/mage/wiki/List-of-cards-that-will-not-be-implemented-on-Xmage) or [use github to find it](https://github.com/magefree/mage/issues?utf8=%E2%9C%93&q=is%3Aissue+label%3A%22tracking+set%22+is%3Aopen+)
* [Double Faced Cards](https://github.com/magefree/mage/wiki/Double-Faced-Cards)
* [Parse spoilers web page to get card data in mtg-cards-data.txt file format](https://github.com/magefree/mage/wiki/Parse-mtgsalvation-spoiler-data)
* [Card Requests](http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=4554)
* [Tournament Relevant Card Requests](http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=14062)
