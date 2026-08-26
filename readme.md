# XMage — Magic, Another Game Engine

[![Build Status](https://github.com/magefree/mage/actions/workflows/maven.yml/badge.svg)](https://github.com/magefree/mage/actions/workflows/maven.yml)
[![Latest release](https://img.shields.io/github/v/release/magefree/mage)](https://github.com/magefree/mage/releases/)
[![Commits since latest release](https://img.shields.io/github/commits-since/magefree/mage/latest)](https://github.com/magefree/mage/commits/)

XMage is a full rules-enforcement engine for playing Magic against other players or computer AI,
online or on your own server. It supports over **32 000** unique cards and more than 91 000 reprints across official sets, plus custom sets like Star Wars.

Local server supports a [special test mode](https://github.com/magefree/mage/wiki/Development-Testing-Tools) for testing combos and other game situations with pre-defined conditions.

There are public servers where you can play XMage against other players.
You can also host your own server to play against the AI and/or your friends — including fully offline, with no internet connection required.

XMage community and resources:
* [Official XMage support and feature request on github](https://github.com/magefree/mage/issues);
* [Reddit XMage group](https://www.reddit.com/r/XMage/);
* [Reddit XMage discord channel](https://discord.gg/Pqf42yn);
* [Latest news](https://jaydi85.github.io/xmage-web-news/news.html);
* [Latest releases](https://github.com/magefree/mage/releases);
* [Project documentation](https://github.com/magefree/mage/wiki).

Official public server with released version:
* http://xmage.today/

Other servers and status:
* http://xmage.today/servers/ (temporarily out of service)

## Features

* The most tested open-source rules enforcement engine: ~9000 unit tests, ~80% coverage;
* Cross-platform support: Windows, Linux, MacOS;
* Two-player duels or multiplayer games (Commander and others) with up to 10 players;
* Drafts, tournaments and single games with sideboarding supported;
* Smart and fast AI opponents for both games and draft/deck-building;
* Cheat-proof by design: all rules and hidden information are enforced server-side — clients never see or act on data they're not authorized to;
* Deck editor (support deck import from multiple apps and services);
* Players rating system (Glicko);
* Supports dozens of formats and game modes like Commander, Oathbreaker, Cube, Tiny Leaders, Super Standard, Historic Standard and more;
* Single matches or tournaments supported (elimination or swiss type handling), which can be played with up to 16 players;

## Bug report / feature request

The best way to report bug or feature request is [github's issues page](https://github.com/magefree/mage/issues).

## Installation / running

* Download files and follow install instructions on http://xmage.today/
* You need to have [Java version 8 or later](http://java.com/) to run launcher;
* Recommended folder to unpack/install: `D:\games\xmage` (don't use Program Files or Download folders);
* If you want to play with AI opponents then must run "server" app via the launcher and connect to it via localhost;

[Wiki page](https://github.com/magefree/mage/wiki) contains detailed information about private or public server setup, developer onboarding and other useful things

## Troubleshooting / FAQ

Github issues page contain [popular problems and fixes](https://github.com/magefree/mage/issues?q=is%3Aissue+label%3AFAQ+):
* [Any: program freezes on startup (white/blue/black screen)](https://github.com/magefree/mage/issues/4461#issuecomment-361108597);
* [Any: can't download images or it stops after some time](https://www.reddit.com/r/XMage/comments/agmcjf/new_xmage_release_with_ravnica_allegiance_rna/);
* [Any: can't run client, could not open ...jvm.cfg](https://github.com/magefree/mage/issues/1272#issuecomment-529789018);
* [Any: no texts or small buttons in launcher](https://github.com/magefree/mage/issues/4126);
* [Windows: ugly cards, buttons or other GUI drawing artifacts](https://github.com/magefree/mage/issues/4626#issuecomment-374640070);
* [Windows: pixilated images, icons and texts](https://github.com/magefree/mage/issues/12768#issuecomment-2347125602);
* [MacOS: can't open launcher](https://www.reddit.com/r/XMage/comments/1u2ezx6/comment/or38wjd/);
* [MacOS: can't run on M1/M2](https://github.com/magefree/mage/issues/8406#issuecomment-1011720728);
* [MacOS: client freezes in GUI (on connect dialog, on new match)](https://github.com/magefree/mage/issues/4920#issuecomment-517944308);
* [Linux: run on non-standard OS or hardware like Raspberry Pi](https://github.com/magefree/mage/issues/11611#issuecomment-1879385151);
* [Linux: ugly GUI and drawing artifacts](https://github.com/magefree/mage/issues/11611#issuecomment-1879396921);

## Performance tweaks

The main way to increase performance or fix micro-freezes -- increase max memory usage by special java flag:
1. Run launcher -> Settings -> Java tab -> Client java options text field;
2. Add or change existing flag to ` -Xmx3000m` or another value based on your free memory stats (recommended settings: 2000m for 1080p desktop, 3000m for 4k desktop). It's useless to give 5+ GB memory;
3. If you play many AI games then it's recommended to change Server java options with same value too;

## Development

Full project documentation and instructions for developers can be found in [wiki pages](http://github.com/magefree/mage/wiki/).

First steps for XMage's developers:
* [Setting up your Development Environment](https://github.com/magefree/mage/wiki/Setting-up-your-Development-Environment)
* [Development Testing Tools](https://github.com/magefree/mage/wiki/Development-Testing-Tools)
* [Development Workflow](https://github.com/magefree/mage/wiki/Development-Workflow)
* [Development HOWTO Guides](https://github.com/magefree/mage/wiki/Development-HOWTO-Guides)

[Torch icons created by Freepik - Flaticon](https://www.flaticon.com/free-icons/torch)
