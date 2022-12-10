package org.mage.plugins.card.dl.sources;

import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author spjspj
 */
public enum GrabbagImageSource implements CardImageSource {

    instance;
    private static final Logger LOGGER = Logger.getLogger(GrabbagImageSource.class);

    private static final Set<String> supportedSets = new LinkedHashSet<String>() {
        {
            add("SWS");
        }
    };

    @Override
    public String getSourceName() {
        return "Grabbag";
    }

    @Override
    public float getAverageSize() {
        return 74.8f;
    }

    @Override
    public String getNextHttpImageUrl() {
        return null;
    }

    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        return null;
    }

    @Override
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        return true;
    }

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        if (singleLinks == null) {
            setupLinks();
        }

        // multiple images card use special a,b,c,d versions
        String url;
        String postfix = card.getCollectorIdPostfix();
        if (postfix.isEmpty()) {
            url = singleLinks.get(card.getSet() + "/" + card.getName());
        } else {
            url = singleLinks.get(card.getSet() + "/" + card.getName() + "-" + postfix);
        }

        if (url != null) {
            return new CardImageUrls(getSourceName(card, url) + url);
        }
        return null;
    }

    Map<String, String> singleLinks = null;

    private void setupLinks() {
        if (singleLinks != null) {
            return;
        }
        singleLinks = new HashMap<>();
        singleLinks.put("SWS/AAT-1", "CqmDY8V.jpg");
        singleLinks.put("SWS/Acklay of the Arena", "ESVRm6F.jpg");
        singleLinks.put("SWS/Acquire Target", "FOskB4q.jpg");
        singleLinks.put("SWS/Admiral Ackbar", "JdGpP3p.jpg");
        singleLinks.put("SWS/Adroit Hateflayer", "0gSIQ4K.jpg");
        singleLinks.put("SWS/Anakin Skywalker", "3pGvZZEg.png");
        singleLinks.put("SWS/Ancient Holocron", "fH2dVP5.jpg");
        singleLinks.put("SWS/Aqualish Bounty Hunter", "Wm2aKa2.jpg");
        singleLinks.put("SWS/Armed Protocol Droid", "mywdKgN.jpg");
        singleLinks.put("SWS/Arrest", "VXLnNUo.jpg");
        singleLinks.put("SWS/Asajj Ventress", "rOXSIwO.jpg");
        singleLinks.put("SWS/AT-ST", "9sMcy3C.jpg");
        singleLinks.put("SWS/Aurra Sing, Bane of Jedi", "VgbndqZ.png");
        singleLinks.put("SWS/A-Wing", "4TaYoRO.jpg");
        singleLinks.put("SWS/Bantha Herd", "9rLPE2a.jpg");
        singleLinks.put("SWS/Bathe in Bacta", "sPynQAZ.jpg");
        singleLinks.put("SWS/Battle Tactics", "zoon1p4.jpg");
        singleLinks.put("SWS/Bib Fortuna", "AqAmOEw.jpg");
        singleLinks.put("SWS/Black Market Dealer", "EJpIxna.jpg");
        singleLinks.put("SWS/Blind Worship", "GonJyeF.jpg");
        singleLinks.put("SWS/Boba Fett", "XE83Ks7.jpg");
        singleLinks.put("SWS/Bossk", "m91vUdJ.jpg");
        singleLinks.put("SWS/Bounty Collector", "GHHxvb0.jpg");
        singleLinks.put("SWS/Bounty Sniper", "ANTNrsS.jpg");
        singleLinks.put("SWS/Bounty Spotter", "aB6LAZs.jpg");
        singleLinks.put("SWS/Bull Rancor", "eG4mJ7o.jpg");
        singleLinks.put("SWS/C-3PO and R2D2", "RTv4ikx.jpg");
        singleLinks.put("SWS/Cantina Band", "PqMQP0o.jpg");
        singleLinks.put("SWS/Capture", "jxoTOyC.jpg");
        singleLinks.put("SWS/Carbonite Chamber", "rqEr1gm.jpg");
        singleLinks.put("SWS/Chewbacca", "D3D5T42.jpg");
        singleLinks.put("SWS/Chief Chirpa", "Gx3hLsg.jpg");
        singleLinks.put("SWS/Cloaking Device", "Vtz1NZU.jpg");
        singleLinks.put("SWS/Commander Cody", "9PGV2pV.jpg");
        singleLinks.put("SWS/Condemn", "36yejT2.jpg");
        singleLinks.put("SWS/Corellian Corvette", "j8uPQDY.jpg");
        singleLinks.put("SWS/Crossfire", "Iz9OdPh.jpg");
        singleLinks.put("SWS/Cruelty of the Sith", "q3WIYAt.jpg");
        singleLinks.put("SWS/Cunning Abduction", "CueTNo7.jpg");
        singleLinks.put("SWS/Dagobah Maw Slug", "SqmdUMp.jpg");
        singleLinks.put("SWS/Dark Apprenticeship", "yf5MthH.jpg");
        singleLinks.put("SWS/Dark Decision", "2HB5lYN.jpg");
        singleLinks.put("SWS/Dark Trooper", "atKdUTA.jpg");
        singleLinks.put("SWS/Darth Maul", "EwC1e1Q.jpg");
        singleLinks.put("SWS/Darth Sidious, Sith Lord", "UYk3KnH.png");
        singleLinks.put("SWS/Darth Tyranus, Count of Serenno", "AXUfNuO.png");
        singleLinks.put("SWS/Darth Vader", "3pGvZZE.png");
        singleLinks.put("SWS/Death Trooper", "j7lWmPJ.jpg");
        singleLinks.put("SWS/Deploy The Troops", "QtcN0qV.jpg");
        singleLinks.put("SWS/Doom Blade", "cSuxWUr.jpg");
        singleLinks.put("SWS/Droid Commando", "HkKiaBQ.jpg");
        singleLinks.put("SWS/Droid Factory", "34L3ykD.jpg");
        singleLinks.put("SWS/Droid Foundry", "qYijxSk.jpg");
        singleLinks.put("SWS/Droideka", "BXN7t1i.jpg");
        singleLinks.put("SWS/Drone Holocron", "cHzqK4v.jpg");
        singleLinks.put("SWS/Echo Base Commando", "AdLjV4Y.jpg");
        singleLinks.put("SWS/EMP Blast", "Y0JWgRO.jpg");
        singleLinks.put("SWS/Escape Pod", "vj8gQ1u.jpg");
        singleLinks.put("SWS/Ewok Ambush", "219aufH.jpg");
        singleLinks.put("SWS/Ewok Firedancers", "DFAB3h4.jpg");
        singleLinks.put("SWS/Ewok Village", "rgQevhZ.jpg");
        singleLinks.put("SWS/Exogorth", "cS6fq3u.jpg");
        singleLinks.put("SWS/Ferocity of the Underworld", "lTqtVab.jpg");
        singleLinks.put("SWS/Flames of Remembrance", "WAKhi9i.jpg");
        singleLinks.put("SWS/Force Choke", "Uu1QUf9.jpg");
        singleLinks.put("SWS/Force Denial", "qwYGiUg.jpg");
        singleLinks.put("SWS/Force Drain", "prHdDXa.jpg");
        singleLinks.put("SWS/Force Healing", "kDGRFoj.jpg");
        singleLinks.put("SWS/Force Lightning", "DhAE9lZ.jpg");
        singleLinks.put("SWS/Force Mastery", "XPCWaP8.jpg");
        singleLinks.put("SWS/Force Pull", "rWWfkhX.jpg");
        singleLinks.put("SWS/Force Push", "aN8n4sk.jpg");
        singleLinks.put("SWS/Force Reflex", "RIlvXTz.jpg");
        singleLinks.put("SWS/Force Scream", "EsagOnR.jpg");
        singleLinks.put("SWS/Force Spark", "14MOM1y.jpg");
        singleLinks.put("SWS/Forest-a", "LIpeeP9.jpg");
        singleLinks.put("SWS/Forest-b", "jKwDwH7.jpg");
        singleLinks.put("SWS/Forest-c", "CVb3582.jpg");
        singleLinks.put("SWS/Forest-d", "q09fMW0.jpg");
        singleLinks.put("SWS/Fulfill Contract", "FtLMpHK.jpg");
        singleLinks.put("SWS/Gamorrean Prison Guard", "4dgOMPA.jpg");
        singleLinks.put("SWS/General Grievous", "tRLM8Hz.jpg");
        singleLinks.put("SWS/Gifted Initiate", "NDePdLv.jpg");
        singleLinks.put("SWS/Grand Moff Tarkin", "QXq1V40.jpg");
        singleLinks.put("SWS/Greater Krayt Dragon", "dzIiXXg.jpg");
        singleLinks.put("SWS/Greedo", "IRKwsX0.jpg");
        singleLinks.put("SWS/Gundark", "zLxfLM8.jpg");
        singleLinks.put("SWS/Gungan Captain", "1Q4DNWh.jpg");
        singleLinks.put("SWS/Han Solo", "G0Awota.jpg");
        singleLinks.put("SWS/Hazard Trooper", "ZOutamG.jpg");
        singleLinks.put("SWS/Head Hunting", "7OT1bGZ.jpg");
        singleLinks.put("SWS/Heavy Trooper", "HhZWs2N.jpg");
        singleLinks.put("SWS/Hot Pursuit", "ih1GT5Z.jpg");
        singleLinks.put("SWS/Hungry Dragonsnake", "23v7RTm.jpg");
        singleLinks.put("SWS/Hunt to Extinction", "3eJyfzZ.jpg");
        singleLinks.put("SWS/Hutt Crime Lord", "NAzK7Hp.jpg");
        singleLinks.put("SWS/Hutt Palace", "HEb2JN5.jpg");
        singleLinks.put("SWS/IG-88B", "YZUZJC8.jpg");
        singleLinks.put("SWS/Images of the Past", "sOXEk4Q.jpg");
        singleLinks.put("SWS/Imperial Gunner", "9KpZ8AX.jpg");
        singleLinks.put("SWS/Impulsive Wager", "lLutRRs.jpg");
        singleLinks.put("SWS/Insatiable Rakghoul", "IYqBnTK.jpg");
        singleLinks.put("SWS/Interrogation", "kI2bIbo.jpg");
        singleLinks.put("SWS/Ion Cannon", "Tb546IK.jpg");
        singleLinks.put("SWS/Iron Fist of the Empire", "9Ui7NRn.jpg");
        singleLinks.put("SWS/Island-a", "GxITXBO.jpg");
        singleLinks.put("SWS/Island-b", "vKdg4fG.jpg");
        singleLinks.put("SWS/Island-c", "NPMxIew.jpg");
        singleLinks.put("SWS/Island-d", "cnqFMa6.jpg");
        singleLinks.put("SWS/Ithorian Initiate", "2RYpp5o.jpg");
        singleLinks.put("SWS/Jabba the Hutt", "QMz0sKa.jpg");
        singleLinks.put("SWS/Jango Fett", "nEnspQP.jpg");
        singleLinks.put("SWS/Jar Jar Binks", "GCnx72b.jpg");
        singleLinks.put("SWS/Jar'Kai Battle Stance", "GLavgj7.jpg");
        singleLinks.put("SWS/Jedi Battle Healer", "RyIJON5.jpg");
        singleLinks.put("SWS/Jedi Battle Mage", "V9qHRGq.jpg");
        singleLinks.put("SWS/Jedi Battle Sage", "sZVlGWE.jpg");
        singleLinks.put("SWS/Jedi Enclave", "FlibBhx.jpg");
        singleLinks.put("SWS/Jedi Holocron", "ojbt2av.jpg");
        singleLinks.put("SWS/Jedi Inquirer", "ghFQA76.jpg");
        singleLinks.put("SWS/Jedi Instructor", "IwEpVkz.jpg");
        singleLinks.put("SWS/Jedi Knight", "VXNHpZs.jpg");
        singleLinks.put("SWS/Jedi Mind Trick", "aaVfgSA.jpg");
        singleLinks.put("SWS/Jedi Sentinel", "cae3O1s.jpg");
        singleLinks.put("SWS/Jedi Starfighter", "Ta20jjD.jpg");
        singleLinks.put("SWS/Jedi Temple", "ZkOUJnM.jpg");
        singleLinks.put("SWS/Jedi Training", "7L1LWie.jpg");
        singleLinks.put("SWS/Jump Trooper", "62Pg5r4.jpg");
        singleLinks.put("SWS/Jungle Village", "3a1KN8u.jpg");
        singleLinks.put("SWS/Kamino Cloning Facility", "kwBudvf.jpg");
        singleLinks.put("SWS/Ki-Adi-Mundi", "atqNF3H.jpg");
        singleLinks.put("SWS/LAAT Gunship", "DEMXleY.jpg");
        singleLinks.put("SWS/Lando Calrissian", "jcoTjGV.jpg");
        singleLinks.put("SWS/Legacy of the Beloved", "Y0ObvQg.jpg");
        singleLinks.put("SWS/Lightning Bolt", "XSNGQYi.jpg");
        singleLinks.put("SWS/Lightsaber", "YrpWygk.jpg");
        singleLinks.put("SWS/Loyal Tauntaun", "JKDV2WK.jpg");
        singleLinks.put("SWS/Luke Skywalker", "9worW6q.jpg");
        singleLinks.put("SWS/Mace Windu", "rmTuBGT.jpg");
        singleLinks.put("SWS/Maintenance Droid", "A66IIC1.jpg");
        singleLinks.put("SWS/Maintenance Hangar", "DzCYnH3.jpg");
        singleLinks.put("SWS/Mantellian Savrip", "a9JIDEM.jpg");
        singleLinks.put("SWS/March of the Droids", "A4HIQ5V.jpg");
        singleLinks.put("SWS/Massiff Swarm", "0qRPfbC.jpg");
        singleLinks.put("SWS/Might of the Wild", "eNXOdxp.jpg");
        singleLinks.put("SWS/Millennium Falcon", "aZ6sIn2.jpg");
        singleLinks.put("SWS/Miraculous Recovery", "DH6Cei8.jpg");
        singleLinks.put("SWS/Moisture Farm", "S6kJHtL.jpg");
        singleLinks.put("SWS/Mon Calamari Cruiser", "ZHdTV7p.jpg");
        singleLinks.put("SWS/Mon Calamari Initiate", "GBuXdGP.jpg");
        singleLinks.put("SWS/Mountain-a", "MCii4g1.jpg");
        singleLinks.put("SWS/Mountain-b", "Tb0ic31.jpg");
        singleLinks.put("SWS/Mountain-c", "wqXTdsC.jpg");
        singleLinks.put("SWS/Mountain-d", "9oBNCHk.jpg");
        singleLinks.put("SWS/N-1 Starfighter", "UH3qd7x.jpg");
        singleLinks.put("SWS/Nebulon-B Frigate", "IwEpVkz.jpg");
        singleLinks.put("SWS/Neophyte Hateflayer", "Has2AIW.jpg");
        singleLinks.put("SWS/Nerf Herder", "VUX0LHV.jpg");
        singleLinks.put("SWS/Nexu Stalker", "E1xxHe1.jpg");
        singleLinks.put("SWS/Nightspider", "H1po0uV.jpg");
        singleLinks.put("SWS/No Contest", "6SwC9ri.jpg");
        singleLinks.put("SWS/Novice Bounty Hunter", "WfNSsLY.jpg");
        singleLinks.put("SWS/Nute Gunray", "UZg12DD.jpg");
        singleLinks.put("SWS/Obi-Wan Kenobi", "BHkbjdL.png");
        singleLinks.put("SWS/Open Season", "E4iM90K.jpg");
        singleLinks.put("SWS/Orbital Bombardment", "Ov1mB3A.jpg");
        singleLinks.put("SWS/Order 66", "jZituQQ.jpg");
        singleLinks.put("SWS/Ortolan Keyboardist", "dYDgEUB.jpg");
        singleLinks.put("SWS/Outer Rim Slaver", "xq8ozqq.jpg");
        singleLinks.put("SWS/Outlaw Holocron", "mWbyX78.jpg");
        singleLinks.put("SWS/Personal Energy Shield", "v6TGLne.jpg");
        singleLinks.put("SWS/Plains-a", "HgXaAKh.jpg");
        singleLinks.put("SWS/Plains-b", "Y0i7MBh.jpg");
        singleLinks.put("SWS/Plains-c", "4grXQVd.jpg");
        singleLinks.put("SWS/Plains-d", "kTmN8MM.jpg");
        singleLinks.put("SWS/Plo Koon", "dDNi8CV.jpg");
        singleLinks.put("SWS/Precipice of Mortis", "TRAPT86.jpg");
        singleLinks.put("SWS/Predator's Strike", "pcBoUny.jpg");
        singleLinks.put("SWS/Preordain", "u8nSNQW.jpg");
        singleLinks.put("SWS/Primal Instinct", "bInouYH.jpg");
        singleLinks.put("SWS/Princess Leia", "pnHyPWg.jpg");
        singleLinks.put("SWS/Probe Droid", "0r84uUM.jpg");
        singleLinks.put("SWS/Qui-Gon Jinn", "7DdumG2.jpg");
        singleLinks.put("SWS/Raging Reek", "9maXaMR.jpg");
        singleLinks.put("SWS/Rallying Fire", "wtaTlhd.jpg");
        singleLinks.put("SWS/Ravenous Wampa", "rHDaKDD.jpg");
        singleLinks.put("SWS/Regression", "j5z0TOE.jpg");
        singleLinks.put("SWS/Republic Frigate", "LzNpjxP.jpg");
        singleLinks.put("SWS/Repurpose", "BvRMy3f.jpg");
        singleLinks.put("SWS/Revenge", "xkKzMRX.jpg");
        singleLinks.put("SWS/Riding Ronto", "xECBi7G.jpg");
        singleLinks.put("SWS/Rocket Trooper", "94wUTH5.jpg");
        singleLinks.put("SWS/Rogue's Passage", "UunpJPZ.jpg");
        singleLinks.put("SWS/Rule of two", "wNqZWLJ.jpg");
        singleLinks.put("SWS/Rumination", "nSD3UHQ.jpg");
        singleLinks.put("SWS/Rumor Monger", "wSN6H6v.jpg");
        singleLinks.put("SWS/Sabacc Game", "qIcFb3U.jpg");
        singleLinks.put("SWS/Salvage Squad", "ThYSxmD.jpg");
        singleLinks.put("SWS/Sand Trooper", "ysfpyL8.jpg");
        singleLinks.put("SWS/Sarlacc Pit", "N4dcnln.png");
        singleLinks.put("SWS/Scout the Perimeter", "2cObUbz.jpg");
        singleLinks.put("SWS/Scout Trooper", "9RAY4U1.jpg");
        singleLinks.put("SWS/Security Droid", "dzy8m4v.jpg");
        singleLinks.put("SWS/Senator Bail Organa", "BRkUuYU.jpg");
        singleLinks.put("SWS/Senator Lott Dod", "yYQtXZo.jpg");
        singleLinks.put("SWS/Senator Onaconda Farr", "oPez77z.png");
        singleLinks.put("SWS/Senator Padme Amidala", "287deD9.jpg");
        singleLinks.put("SWS/Senator Passel Argente", "51qpnaE.jpg");
        singleLinks.put("SWS/Shaak Herd", "PtnZD0I.jpg");
        singleLinks.put("SWS/Shadow Trooper", "09NAiGa.jpg");
        singleLinks.put("SWS/Shock Trooper", "UVNOxMR.jpg");
        singleLinks.put("SWS/Show of Dominance", "ru2D3Qp.jpg");
        singleLinks.put("SWS/Sith Assassin", "Nt8WUCj.jpg");
        singleLinks.put("SWS/Sith Citadel", "bBCbK30.jpg");
        singleLinks.put("SWS/Sith Evoker", "jwRzCEB.jpg");
        singleLinks.put("SWS/Sith Holocron", "07Ufx5X.jpg");
        singleLinks.put("SWS/Sith Inquisitor", "NivI1E5.jpg");
        singleLinks.put("SWS/Sith Lord", "lWBfQoA.jpg");
        singleLinks.put("SWS/Sith Magic", "cDPFeve.jpg");
        singleLinks.put("SWS/Sith Manipulator", "Q7CIvyz.jpg");
        singleLinks.put("SWS/Sith Marauder", "9zZUSJW.jpg");
        singleLinks.put("SWS/Sith Mindseer", "Lmps3oO.jpg");
        singleLinks.put("SWS/Sith Ravager", "nl9Dp41.jpg");
        singleLinks.put("SWS/Sith Ruins", "oSqiYyO.jpg");
        singleLinks.put("SWS/Sith Sorcerer", "Pq37iop.jpg");
        singleLinks.put("SWS/Sith Thoughtseeker", "YzIY1di.jpg");
        singleLinks.put("SWS/Slave I", "QUGpxlb.jpg");
        singleLinks.put("SWS/Smash to Smithereens", "UlzDZWp.jpg");
        singleLinks.put("SWS/Snow Trooper", "28Jp5JL.jpg");
        singleLinks.put("SWS/Speeder Trooper", "BIEnTDL.jpg");
        singleLinks.put("SWS/Star Destroyer", "DYUMXHZ.jpg");
        singleLinks.put("SWS/Strike Team Commando", "783ZFsF.jpg");
        singleLinks.put("SWS/Super Battle Droid", "T8IjrKD.jpg");
        singleLinks.put("SWS/Surprise Maneuver", "uaAmzz8.jpg");
        singleLinks.put("SWS/Swamp-a", "kBGj6vk.jpg");
        singleLinks.put("SWS/Swamp-b", "BLJl2lf.jpg");
        singleLinks.put("SWS/Swamp-c", "MLH5o2u.jpg");
        singleLinks.put("SWS/Swamp-d", "Rmrv9tC.jpg");
        singleLinks.put("SWS/Swarm the Skies", "Ti1McaV.jpg");
        singleLinks.put("SWS/Syndicate Enforcer", "xZ0g2Sx.jpg");
        singleLinks.put("SWS/Tank Droid", "N4YyMje.jpg");
        singleLinks.put("SWS/Terentatek Cub", "nbmBxst.jpg");
        singleLinks.put("SWS/The Battle of Endor", "vhL6gdI.jpg");
        singleLinks.put("SWS/The Battle of Geonosis", "7Fnr2MD.jpg");
        singleLinks.put("SWS/The Battle of Hoth", "wcVJwKP.jpg");
        singleLinks.put("SWS/The Battle of Naboo", "aesSN9E.jpg");
        singleLinks.put("SWS/The Battle of Yavin", "AtGE49k.jpg");
        singleLinks.put("SWS/The Death Star", "2LjSXa9.jpg");
        singleLinks.put("SWS/TIE Bomber", "R2l7ZXQ.jpg");
        singleLinks.put("SWS/TIE Interceptor", "fYY4PUR.jpg");
        singleLinks.put("SWS/Trade Federation Battleship", "sKN9Gzv.jpg");
        singleLinks.put("SWS/Tri-Fighter", "IhwHzqT.jpg");
        singleLinks.put("SWS/Trooper Armor", "nqnFj9f.jpg");
        singleLinks.put("SWS/Trooper Commando", "PiAYXJv.jpg");
        singleLinks.put("SWS/Twi'lek Seductress", "iPhUxUV.jpg");
        singleLinks.put("SWS/Ugnaught Scrap Worker", "fuuNN3n.jpg");
        singleLinks.put("SWS/Underworld Slums", "o4CFq3x.jpg");
        singleLinks.put("SWS/Unity of the Droids", "WFAIRy3.jpg");
        singleLinks.put("SWS/Unruly Sureshot", "AHymfLc.jpg");
        singleLinks.put("SWS/Vapor Snag", "8g3q0ny.jpg");
        singleLinks.put("SWS/V-Wing", "7eThuU9.jpg");
        singleLinks.put("SWS/Weequay Beastmaster", "metAs1p.jpg");
        singleLinks.put("SWS/Wild Holocron", "adw7dFO.jpg");
        singleLinks.put("SWS/Wisdom of the Jedi", "TgTj2Dd.jpg");
        singleLinks.put("SWS/Womp Rat", "XKF79Hr.jpg");
        singleLinks.put("SWS/Wookiee Bounty Hunter", "A76UGTJ.jpg");
        singleLinks.put("SWS/Wookiee Mystic", "8DCkOVe.jpg");
        singleLinks.put("SWS/Wookiee Raidleader", "ZZTduL5.jpg");
        singleLinks.put("SWS/X-Wing", "AV1LPuZ.jpg");
        singleLinks.put("SWS/Yoda, Jedi Master", "6arN1Hl.png");
        singleLinks.put("SWS/Y-Wing", "aQQ5zwA.jpg");
        singleLinks.put("SWS/Zam Wesell", "ToG0C1r.jpg");
        singleLinks.put("SWS/Astromech Droid", "v0TpHMh.jpg");
        singleLinks.put("SWS/Buried Ruin", "QkmIWYg.png");
        singleLinks.put("SWS/Flame Trooper", "RkY7KFJ.jpg");
        singleLinks.put("SWS/Force Stasis", "FavLrcY.jpg");
        singleLinks.put("SWS/Salvage Trader", "qGwk7Bn.jpg");
        singleLinks.put("SWS/Outer Rim Gang", "kEjKQGy.png");
        singleLinks.put("SWS/Rathtar", "CYhHRqF.png");
        singleLinks.put("SWS/Riot Trooper", "PusvaQB.jpg");
        singleLinks.put("SWS/Sins of the Father", "32YHTPB.jpg");
        singleLinks.put("SWS/Upsilon-class Shuttle", "Le3F3oW.jpg");
        singleLinks.put("SWS/Finn", "TU2LI2q.jpg");
        singleLinks.put("SWS/General Hux", "UpWfcV6.png");
        singleLinks.put("SWS/Poe Dameron", "v8i21dn.png");
        singleLinks.put("SWS/Rey", "7n5ZZFA.png");
        singleLinks.put("SWS/Kylo Ren", "fFzDMTz.png");
        singleLinks.put("SWS/TIE Striker", "6b5GDUQ.jpg");
        singleLinks.put("SWS/Bludgeoning Pain", "ap5k3Wl.jpg");
        singleLinks.put("SWS/Force Protection", "GrOQLHO.jpg");
        singleLinks.put("SWS/Gerrera's Revolutionary", "FQFE1Jt.jpg");
        singleLinks.put("SWS/Thermal Detonator", "gTPLM83.jpg");
        singleLinks.put("SWS/Hammerhead Corvette", "IlhOAGv.jpg");
        singleLinks.put("SWS/U-Wing", "FmoRCmG.jpg");
        singleLinks.put("SWS/Bor Gullet", "jXafYHX.jpg");
        singleLinks.put("SWS/Imperial Hovertank", "6X1wL4d.jpg");
        singleLinks.put("SWS/Occupation", "h4mmkA5.jpg");
        singleLinks.put("SWS/Resistance", "lbNhA59.jpg");
        singleLinks.put("SWS/Jyn Erso and Cassian Andor", "o0SCGiJ.jpg");
        singleLinks.put("SWS/Chirrut Imwe", "wgtXfUF.jpg");
        singleLinks.put("SWS/Director Krennic", "52PGsH5.jpg");
        singleLinks.put("SWS/Vader's Command", "7Lql6UT.jpg");
        singleLinks.put("SWS/Delay Tactic", "ubmzD1m.jpg");
        singleLinks.put("SWS/Resistance Bomber", "Sudfkd7.jpg");
        singleLinks.put("SWS/Mouse Droid", "oO0p8QE.jpg");
        singleLinks.put("SWS/First Order Dreadnought", "80pO9Cc.jpg");
        singleLinks.put("SWS/TIE Silencer", "7yeYIjX.jpg");
        singleLinks.put("SWS/Canto Bight Enforcer", "VKPQVsn.jpg");
        singleLinks.put("SWS/Cantonica Casino", "7LiSvy6.jpg");
        singleLinks.put("SWS/Fathier", "0oKquQp.jpg");
        singleLinks.put("SWS/Code Slice", "7uNASji.jpg");
        singleLinks.put("SWS/Captain Phasma", "LWujx1B.jpg");
        singleLinks.put("SWS/Force Telepathy", "e90hswX.jpg");
        singleLinks.put("SWS/Praetorian Trooper", "pjS1wyS.jpg");
        singleLinks.put("SWS/Supreme Leader Snoke", "eewWiKE.jpg");
        singleLinks.put("SWS/Sai Tok", "FVn29tT.jpg");
        singleLinks.put("SWS/Porg Nest", "8DnNZKc.jpg");
        singleLinks.put("SWS/Inspire", "7lIXhtd.jpg");
        singleLinks.put("SWS/Force Projection", "5EfOwyn.jpg");
        singleLinks.put("SWS/Luke Skywalker, the Last Jedi", "WMmQcyD.jpg");
        singleLinks.put("SWS/Vulptex", "30WeCkw.jpg");
        singleLinks.put("SWS/Glorious Charge", "yJwvKzk.jpg");
        singleLinks.put("SWS/Plains-520b", "Fx59r9J.jpg");
        singleLinks.put("SWS/Island-520a", "jIPpWp5.jpg");
        singleLinks.put("SWS/Conscription", "An01yAe.jpg");
        singleLinks.put("SWS/Afterburn", "2ydqSvT.jpg");
        singleLinks.put("SWS/Corellian Gunship", "mZdDQWH.jpg");
        singleLinks.put("SWS/Despair", "TLTddMI.jpg");
        singleLinks.put("SWS/Dryden Vos", "6LbtUzN.jpg");
        singleLinks.put("SWS/Droid Uprising", "aWuoxho.jpg");
        singleLinks.put("SWS/Gamble", "Hwzr60O.jpg");
        singleLinks.put("SWS/Han Solo, Scrumrat", "Hqj39dG.jpg");
        singleLinks.put("SWS/Mud Trooper", "af8JaDy.jpg");
        singleLinks.put("SWS/Enfys Nest", "pstVfQg.jpg");
        singleLinks.put("SWS/Kalevan Star Yacht", "nHmSizp.jpg");
        singleLinks.put("SWS/Maelstrom Blockade", "sUYT0pc.jpg");
        singleLinks.put("SWS/Range Trooper", "kXGvTkE.jpg");
        singleLinks.put("SWS/Tobias Beckett", "hzm6ilE.jpg");
        singleLinks.put("SWS/Underground Forum", "FH2pRfU.jpg");
        singleLinks.put("SWS/Chewbacca, the Beast", "Zb5TitZ.jpg");
        singleLinks.put("SWS/A Jedi's Fervor", "5MjPOpE.jpg");
        singleLinks.put("SWS/Allegiant General Pryde", "Ucithhc.jpg");
        singleLinks.put("SWS/Balance", "EMdbTBj.jpg");
        singleLinks.put("SWS/Band Together", "xc9dQaZ.jpg");
        singleLinks.put("SWS/Ben Solo", "4GXbkI3.jpg");
        singleLinks.put("SWS/Betray", "SjyF6Nq.jpg");
        singleLinks.put("SWS/Brave the Elements", "z2etu3V.jpg");
        singleLinks.put("SWS/Culling Dais", "NHRMrmo.jpg");
        singleLinks.put("SWS/Droidsmith", "HYyiEI7.jpg");
        singleLinks.put("SWS/Dyad Force Transfer", "dmyQ7Jm.jpg");
        singleLinks.put("SWS/Festival of the Ancestors", "JUPnIEr.jpg");
        singleLinks.put("SWS/First Order Jet Trooper", "wVqmWMK.jpg");
        singleLinks.put("SWS/Force Lift", "46eGWAq.jpg");
        singleLinks.put("SWS/General Organa", "YSv61yR.jpg");
        singleLinks.put("SWS/Hidden Base", "M6BR6aH.jpg");
        singleLinks.put("SWS/Hold Captive", "3dR542o.jpg");
        singleLinks.put("SWS/Holochess", "lPS1mR6.jpg");
        singleLinks.put("SWS/Knights of Ren", "vdXyRpy.jpg");
        singleLinks.put("SWS/Lightspeed Skipping", "VRquVqA.jpg");
        singleLinks.put("SWS/Luke's Lightsaber", "Ty3j9y3.jpg");
        singleLinks.put("SWS/Mimic Vat", "9bAK1LC.jpg");
        singleLinks.put("SWS/Orbak", "8RvSbhX.jpg");
        singleLinks.put("SWS/Propaganda", "TBNlxqj.jpg");
        singleLinks.put("SWS/Rey Skywalker", "dOyjNXc.jpg");
        singleLinks.put("SWS/Rey's Lightsaber", "eMzKXAP.jpg");
        singleLinks.put("SWS/Sith Eternal Lightning", "oaFkj3N.jpg");
        singleLinks.put("SWS/Sith Wayfinder", "AznLRt0.jpg");
        singleLinks.put("SWS/Swamp-e", "LrbqB3U.jpg");
        singleLinks.put("SWS/Training Droid", "JCY9KOM.jpg");
        singleLinks.put("SWS/Unpleasant Discovery", "lDKoeAu.jpg");
        singleLinks.put("SWS/Vexis", "cNQX9Ue.jpg");
        singleLinks.put("SWS/War Room", "pqQ9kzt.jpg");
        singleLinks.put("SWS/Xyston Star Destroyer", "oqbHtUC.jpg");
        singleLinks.put("SWS/Zorii Bliss", "vOyNE39.jpg");

        // Emblems
        singleLinks.put("SWS/Emblem Obi-Wan Kenobi", "Qyc10aT.png");
        singleLinks.put("SWS/Aurra Sing", "BLWbVJC.png");
        singleLinks.put("SWS/Yoda", "zH0sYxg.png");
        singleLinks.put("SWS/Emblem Luke Skywalker", "kHELZDJ.jpg");

        // Tokens
        singleLinks.put("SWS/Ewok", "N2MvJyr.png");
        singleLinks.put("SWS/B-Wing", "oH62AUD.png");
        singleLinks.put("SWS/Hunter", "oJiawFA.png");
        singleLinks.put("SWS/TIE Fighter", "CLOuJ05.png");
        singleLinks.put("SWS/Trooper", "2XKqdX5.png");
        singleLinks.put("SWS/AT-AT", "Tv5b7h1.png");
        singleLinks.put("SWS/Rebel", "pVoShnF.png");
        singleLinks.put("SWS/Royal Guard", "9tqE8vL.png");
        singleLinks.put("SWS/Tusken Raider", "gPMiSmP.png");
        singleLinks.put("SWS/Droid", "4PRrWFF.png");
        singleLinks.put("SWS/Trooper 2", "tcxvGOn.jpg");
        singleLinks.put("SWS/Porg", "HBjt1A3.jpg");
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws IOException {
        try {
            return generateCardUrl(card);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public int getTotalImages() {
        if (singleLinks == null) {
            setupLinks();
        }
        if (singleLinks != null) {
            return singleLinks.size();
        }
        return -1;
    }

    @Override
    public boolean isTokenSource() {
        return true;
    }

    @Override
    public boolean isCardSource() {
        return true;
    }

    private String getSourceName(CardDownloadData card, String httpImageUrl) {
        if (card.getSet().equals("MTG")) {
            return "http://static.starcitygames.com/sales/cardscans/";
        } else if (card.getSet().equals("SWS")) {
            return "https://i.imgur.com/";
        } else {
            return "http://magiccards.info/scans/en/";
        }
    }

    @Override
    public List<String> getSupportedSets() {
        return new ArrayList<>(supportedSets);
    }

    @Override
    public void doPause(String httpImageUrl) {
        if (!httpImageUrl.startsWith("/MTG")) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public boolean isCardImageProvided(String setCode, String cardName) {
        if (singleLinks == null) {
            setupLinks();
        }
        return singleLinks.containsKey(setCode + "/" + cardName) || singleLinks.containsKey(setCode + "/" + cardName + "-a");
    }
}
