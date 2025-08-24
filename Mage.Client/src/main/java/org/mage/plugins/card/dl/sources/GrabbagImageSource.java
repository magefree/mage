package org.mage.plugins.card.dl.sources;

import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Images download source for imgur.com and direct links
 *
 * @author spjspj, JayDi85
 */
public enum GrabbagImageSource implements CardImageSource {

    instance;
    private static final Logger LOGGER = Logger.getLogger(GrabbagImageSource.class);

    private static final String IMGUR_IMAGE_URL = "https://i.imgur.com/";
    static final Pattern IMGUR_IMAGE_ID_PATTERN = Pattern.compile("imgur\\.com\\/(\\w+)");

    private static final Set<String> supportedSets = new LinkedHashSet<String>() {
        {
            add("SWS");
            add("ATC");
        }
    };

    @Override
    public String getSourceName() {
        return "Grabbag";
    }

    @Override
    public float getAverageSizeKb() {
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
            url = prepareFullUrl(card, url);
        }

        if (url != null) {
            return new CardImageUrls(url);
        }

        return null;
    }

    Map<String, String> singleLinks = null;

    private void setupLinks() {
        if (singleLinks != null) {
            return;
        }

        // can use:
        // - direct links like https://sample.site/image.jpg
        // - imgur.com links like CqmDY8V.jpeg (by image name)
        singleLinks = new HashMap<>();

        // lands
        // it uses workaround for various art by -a,-b,-c,-d postfix
        singleLinks.put("SWS/Forest-a", "LIpeeP9.jpeg");
        singleLinks.put("SWS/Forest-b", "jKwDwH7.jpeg");
        singleLinks.put("SWS/Forest-c", "CVb3582.jpeg");
        singleLinks.put("SWS/Forest-d", "q09fMW0.jpeg");
        singleLinks.put("SWS/Island-a", "GxITXBO.jpeg");
        singleLinks.put("SWS/Island-b", "vKdg4fG.jpeg");
        singleLinks.put("SWS/Island-c", "NPMxIew.jpeg");
        singleLinks.put("SWS/Island-d", "cnqFMa6.jpeg");
        singleLinks.put("SWS/Mountain-a", "MCii4g1.jpeg");
        singleLinks.put("SWS/Mountain-b", "Tb0ic31.jpeg");
        singleLinks.put("SWS/Mountain-c", "wqXTdsC.jpeg");
        singleLinks.put("SWS/Mountain-d", "9oBNCHk.jpeg");
        singleLinks.put("SWS/Plains-a", "HgXaAKh.jpeg");
        singleLinks.put("SWS/Plains-b", "Y0i7MBh.jpeg");
        singleLinks.put("SWS/Plains-c", "4grXQVd.jpeg");
        singleLinks.put("SWS/Plains-d", "kTmN8MM.jpeg");
        singleLinks.put("SWS/Swamp-a", "kBGj6vk.jpeg");
        singleLinks.put("SWS/Swamp-b", "BLJl2lf.jpeg");
        singleLinks.put("SWS/Swamp-c", "MLH5o2u.jpeg");
        singleLinks.put("SWS/Swamp-d", "Rmrv9tC.jpeg");

        // cards
        singleLinks.put("SWS/AAT-1", "CqmDY8V.jpeg");
        singleLinks.put("SWS/Acklay of the Arena", "ESVRm6F.jpeg");
        singleLinks.put("SWS/Acquire Target", "FOskB4q.jpeg");
        singleLinks.put("SWS/Admiral Ackbar", "JdGpP3p.jpeg");
        singleLinks.put("SWS/Adroit Hateflayer", "0gSIQ4K.jpeg");
        singleLinks.put("SWS/Anakin Skywalker", "3pGvZZEg.png");
        singleLinks.put("SWS/Ancient Holocron", "fH2dVP5.jpeg");
        singleLinks.put("SWS/Aqualish Bounty Hunter", "Wm2aKa2.jpeg");
        singleLinks.put("SWS/Armed Protocol Droid", "mywdKgN.jpeg");
        singleLinks.put("SWS/Arrest", "VXLnNUo.jpeg");
        singleLinks.put("SWS/Asajj Ventress", "rOXSIwO.jpeg");
        singleLinks.put("SWS/AT-ST", "9sMcy3C.jpeg");
        singleLinks.put("SWS/Aurra Sing, Bane of Jedi", "VgbndqZ.png");
        singleLinks.put("SWS/A-Wing", "4TaYoRO.jpeg");
        singleLinks.put("SWS/Bantha Herd", "9rLPE2a.jpeg");
        singleLinks.put("SWS/Bathe in Bacta", "sPynQAZ.jpeg");
        singleLinks.put("SWS/Battle Tactics", "zoon1p4.jpeg");
        singleLinks.put("SWS/Bib Fortuna", "AqAmOEw.jpeg");
        singleLinks.put("SWS/Black Market Dealer", "EJpIxna.jpeg");
        singleLinks.put("SWS/Blind Worship", "GonJyeF.jpeg");
        singleLinks.put("SWS/Boba Fett", "XE83Ks7.jpeg");
        singleLinks.put("SWS/Bossk", "m91vUdJ.jpeg");
        singleLinks.put("SWS/Bounty Collector", "GHHxvb0.jpeg");
        singleLinks.put("SWS/Bounty Sniper", "ANTNrsS.jpeg");
        singleLinks.put("SWS/Bounty Spotter", "aB6LAZs.jpeg");
        singleLinks.put("SWS/Bull Rancor", "eG4mJ7o.jpeg");
        singleLinks.put("SWS/C-3PO and R2D2", "RTv4ikx.jpeg");
        singleLinks.put("SWS/Cantina Band", "PqMQP0o.jpeg");
        singleLinks.put("SWS/Capture", "jxoTOyC.jpeg");
        singleLinks.put("SWS/Carbonite Chamber", "rqEr1gm.jpeg");
        singleLinks.put("SWS/Chewbacca", "D3D5T42.jpeg");
        singleLinks.put("SWS/Chief Chirpa", "Gx3hLsg.jpeg");
        singleLinks.put("SWS/Cloaking Device", "Vtz1NZU.jpeg");
        singleLinks.put("SWS/Commander Cody", "9PGV2pV.jpeg");
        singleLinks.put("SWS/Condemn", "36yejT2.jpeg");
        singleLinks.put("SWS/Corellian Corvette", "j8uPQDY.jpeg");
        singleLinks.put("SWS/Crossfire", "Iz9OdPh.jpeg");
        singleLinks.put("SWS/Cruelty of the Sith", "q3WIYAt.jpeg");
        singleLinks.put("SWS/Cunning Abduction", "CueTNo7.jpeg");
        singleLinks.put("SWS/Dagobah Maw Slug", "SqmdUMp.jpeg");
        singleLinks.put("SWS/Dark Apprenticeship", "yf5MthH.jpeg");
        singleLinks.put("SWS/Dark Decision", "2HB5lYN.jpeg");
        singleLinks.put("SWS/Dark Trooper", "atKdUTA.jpeg");
        singleLinks.put("SWS/Darth Maul", "EwC1e1Q.jpeg");
        singleLinks.put("SWS/Darth Sidious, Sith Lord", "UYk3KnH.png");
        singleLinks.put("SWS/Darth Tyranus, Count of Serenno", "AXUfNuO.png");
        singleLinks.put("SWS/Darth Vader", "3pGvZZE.png");
        singleLinks.put("SWS/Death Trooper", "j7lWmPJ.jpeg");
        singleLinks.put("SWS/Deploy The Troops", "QtcN0qV.jpeg");
        singleLinks.put("SWS/Doom Blade", "cSuxWUr.jpeg");
        singleLinks.put("SWS/Droid Commando", "HkKiaBQ.jpeg");
        singleLinks.put("SWS/Droid Factory", "34L3ykD.jpeg");
        singleLinks.put("SWS/Droid Foundry", "qYijxSk.jpeg");
        singleLinks.put("SWS/Droideka", "BXN7t1i.jpeg");
        singleLinks.put("SWS/Drone Holocron", "cHzqK4v.jpeg");
        singleLinks.put("SWS/Echo Base Commando", "AdLjV4Y.jpeg");
        singleLinks.put("SWS/EMP Blast", "Y0JWgRO.jpeg");
        singleLinks.put("SWS/Escape Pod", "vj8gQ1u.jpeg");
        singleLinks.put("SWS/Ewok Ambush", "219aufH.jpeg");
        singleLinks.put("SWS/Ewok Firedancers", "DFAB3h4.jpeg");
        singleLinks.put("SWS/Ewok Village", "rgQevhZ.jpeg");
        singleLinks.put("SWS/Exogorth", "cS6fq3u.jpeg");
        singleLinks.put("SWS/Ferocity of the Underworld", "lTqtVab.jpeg");
        singleLinks.put("SWS/Flames of Remembrance", "WAKhi9i.jpeg");
        singleLinks.put("SWS/Force Choke", "Uu1QUf9.jpeg");
        singleLinks.put("SWS/Force Denial", "qwYGiUg.jpeg");
        singleLinks.put("SWS/Force Drain", "prHdDXa.jpeg");
        singleLinks.put("SWS/Force Healing", "kDGRFoj.jpeg");
        singleLinks.put("SWS/Force Lightning", "DhAE9lZ.jpeg");
        singleLinks.put("SWS/Force Mastery", "XPCWaP8.jpeg");
        singleLinks.put("SWS/Force Pull", "rWWfkhX.jpeg");
        singleLinks.put("SWS/Force Push", "aN8n4sk.jpeg");
        singleLinks.put("SWS/Force Reflex", "RIlvXTz.jpeg");
        singleLinks.put("SWS/Force Scream", "EsagOnR.jpeg");
        singleLinks.put("SWS/Force Spark", "14MOM1y.jpeg");
        singleLinks.put("SWS/Fulfill Contract", "FtLMpHK.jpeg");
        singleLinks.put("SWS/Gamorrean Prison Guard", "4dgOMPA.jpeg");
        singleLinks.put("SWS/General Grievous", "tRLM8Hz.jpeg");
        singleLinks.put("SWS/Gifted Initiate", "NDePdLv.jpeg");
        singleLinks.put("SWS/Grand Moff Tarkin", "QXq1V40.jpeg");
        singleLinks.put("SWS/Greater Krayt Dragon", "dzIiXXg.jpeg");
        singleLinks.put("SWS/Greedo", "IRKwsX0.jpeg");
        singleLinks.put("SWS/Gundark", "zLxfLM8.jpeg");
        singleLinks.put("SWS/Gungan Captain", "1Q4DNWh.jpeg");
        singleLinks.put("SWS/Han Solo", "G0Awota.jpeg");
        singleLinks.put("SWS/Hazard Trooper", "ZOutamG.jpeg");
        singleLinks.put("SWS/Head Hunting", "7OT1bGZ.jpeg");
        singleLinks.put("SWS/Heavy Trooper", "HhZWs2N.jpeg");
        singleLinks.put("SWS/Hot Pursuit (Star Wars)", "ih1GT5Z.jpeg");
        singleLinks.put("SWS/Hungry Dragonsnake", "23v7RTm.jpeg");
        singleLinks.put("SWS/Hunt to Extinction", "3eJyfzZ.jpeg");
        singleLinks.put("SWS/Hutt Crime Lord", "NAzK7Hp.jpeg");
        singleLinks.put("SWS/Hutt Palace", "HEb2JN5.jpeg");
        singleLinks.put("SWS/IG-88B", "YZUZJC8.jpeg");
        singleLinks.put("SWS/Images of the Past", "sOXEk4Q.jpeg");
        singleLinks.put("SWS/Imperial Gunner", "9KpZ8AX.jpeg");
        singleLinks.put("SWS/Impulsive Wager", "lLutRRs.jpeg");
        singleLinks.put("SWS/Insatiable Rakghoul", "IYqBnTK.jpeg");
        singleLinks.put("SWS/Interrogation", "kI2bIbo.jpeg");
        singleLinks.put("SWS/Ion Cannon", "Tb546IK.jpeg");
        singleLinks.put("SWS/Iron Fist of the Empire", "9Ui7NRn.jpeg");
        singleLinks.put("SWS/Ithorian Initiate", "2RYpp5o.jpeg");
        singleLinks.put("SWS/Jabba the Hutt", "QMz0sKa.jpeg");
        singleLinks.put("SWS/Jango Fett", "nEnspQP.jpeg");
        singleLinks.put("SWS/Jar Jar Binks", "GCnx72b.jpeg");
        singleLinks.put("SWS/Jar'Kai Battle Stance", "GLavgj7.jpeg");
        singleLinks.put("SWS/Jedi Battle Healer", "RyIJON5.jpeg");
        singleLinks.put("SWS/Jedi Battle Mage", "V9qHRGq.jpeg");
        singleLinks.put("SWS/Jedi Battle Sage", "sZVlGWE.jpeg");
        singleLinks.put("SWS/Jedi Enclave", "FlibBhx.jpeg");
        singleLinks.put("SWS/Jedi Holocron", "ojbt2av.jpeg");
        singleLinks.put("SWS/Jedi Inquirer", "ghFQA76.jpeg");
        singleLinks.put("SWS/Jedi Instructor", "IwEpVkz.jpeg");
        singleLinks.put("SWS/Jedi Knight", "VXNHpZs.jpeg");
        singleLinks.put("SWS/Jedi Mind Trick", "aaVfgSA.jpeg");
        singleLinks.put("SWS/Jedi Sentinel", "cae3O1s.jpeg");
        singleLinks.put("SWS/Jedi Starfighter", "Ta20jjD.jpeg");
        singleLinks.put("SWS/Jedi Temple", "ZkOUJnM.jpeg");
        singleLinks.put("SWS/Jedi Training", "7L1LWie.jpeg");
        singleLinks.put("SWS/Jump Trooper", "62Pg5r4.jpeg");
        singleLinks.put("SWS/Jungle Village", "3a1KN8u.jpeg");
        singleLinks.put("SWS/Kamino Cloning Facility", "kwBudvf.jpeg");
        singleLinks.put("SWS/Ki-Adi-Mundi", "atqNF3H.jpeg");
        singleLinks.put("SWS/LAAT Gunship", "DEMXleY.jpeg");
        singleLinks.put("SWS/Lando Calrissian", "jcoTjGV.jpeg");
        singleLinks.put("SWS/Legacy of the Beloved", "Y0ObvQg.jpeg");
        singleLinks.put("SWS/Lightning Bolt", "XSNGQYi.jpeg");
        singleLinks.put("SWS/Lightsaber", "YrpWygk.jpeg");
        singleLinks.put("SWS/Loyal Tauntaun", "JKDV2WK.jpeg");
        singleLinks.put("SWS/Luke Skywalker", "9worW6q.jpeg");
        singleLinks.put("SWS/Mace Windu", "rmTuBGT.jpeg");
        singleLinks.put("SWS/Maintenance Droid", "A66IIC1.jpeg");
        singleLinks.put("SWS/Maintenance Hangar", "DzCYnH3.jpeg");
        singleLinks.put("SWS/Mantellian Savrip", "a9JIDEM.jpeg");
        singleLinks.put("SWS/March of the Droids", "A4HIQ5V.jpeg");
        singleLinks.put("SWS/Massiff Swarm", "0qRPfbC.jpeg");
        singleLinks.put("SWS/Might of the Wild", "eNXOdxp.jpeg");
        singleLinks.put("SWS/Millennium Falcon", "aZ6sIn2.jpeg");
        singleLinks.put("SWS/Miraculous Recovery", "DH6Cei8.jpeg");
        singleLinks.put("SWS/Moisture Farm", "S6kJHtL.jpeg");
        singleLinks.put("SWS/Mon Calamari Cruiser", "ZHdTV7p.jpeg");
        singleLinks.put("SWS/Mon Calamari Initiate", "GBuXdGP.jpeg");
        singleLinks.put("SWS/N-1 Starfighter", "UH3qd7x.jpeg");
        singleLinks.put("SWS/Nebulon-B Frigate", "F0yIR08.jpeg");
        singleLinks.put("SWS/Neophyte Hateflayer", "Has2AIW.jpeg");
        singleLinks.put("SWS/Nerf Herder", "VUX0LHV.jpeg");
        singleLinks.put("SWS/Nexu Stalker", "E1xxHe1.jpeg");
        singleLinks.put("SWS/Nightspider", "H1po0uV.jpeg");
        singleLinks.put("SWS/No Contest", "6SwC9ri.jpeg");
        singleLinks.put("SWS/Novice Bounty Hunter", "WfNSsLY.jpeg");
        singleLinks.put("SWS/Nute Gunray", "UZg12DD.jpeg");
        singleLinks.put("SWS/Obi-Wan Kenobi", "BHkbjdL.png");
        singleLinks.put("SWS/Open Season", "E4iM90K.jpeg");
        singleLinks.put("SWS/Orbital Bombardment", "Ov1mB3A.jpeg");
        singleLinks.put("SWS/Order 66", "jZituQQ.jpeg");
        singleLinks.put("SWS/Ortolan Keyboardist", "dYDgEUB.jpeg");
        singleLinks.put("SWS/Outer Rim Slaver", "xq8ozqq.jpeg");
        singleLinks.put("SWS/Outlaw Holocron", "mWbyX78.jpeg");
        singleLinks.put("SWS/Personal Energy Shield", "v6TGLne.jpeg");
        singleLinks.put("SWS/Plo Koon", "dDNi8CV.jpeg");
        singleLinks.put("SWS/Precipice of Mortis", "TRAPT86.jpeg");
        singleLinks.put("SWS/Predator's Strike", "pcBoUny.jpeg");
        singleLinks.put("SWS/Preordain", "u8nSNQW.jpeg");
        singleLinks.put("SWS/Primal Instinct", "bInouYH.jpeg");
        singleLinks.put("SWS/Princess Leia", "pnHyPWg.jpeg");
        singleLinks.put("SWS/Probe Droid", "0r84uUM.jpeg");
        singleLinks.put("SWS/Qui-Gon Jinn", "7DdumG2.jpeg");
        singleLinks.put("SWS/Raging Reek", "9maXaMR.jpeg");
        singleLinks.put("SWS/Rallying Fire", "wtaTlhd.jpeg");
        singleLinks.put("SWS/Ravenous Wampa", "rHDaKDD.jpeg");
        singleLinks.put("SWS/Regression", "j5z0TOE.jpeg");
        singleLinks.put("SWS/Republic Frigate", "LzNpjxP.jpeg");
        singleLinks.put("SWS/Repurpose", "BvRMy3f.jpeg");
        singleLinks.put("SWS/Revenge (Star Wars)", "xkKzMRX.jpeg");
        singleLinks.put("SWS/Riding Ronto", "xECBi7G.jpeg");
        singleLinks.put("SWS/Rocket Trooper", "94wUTH5.jpeg");
        singleLinks.put("SWS/Rogue's Passage", "UunpJPZ.jpeg");
        singleLinks.put("SWS/Rule of two", "wNqZWLJ.jpeg");
        singleLinks.put("SWS/Rumination", "nSD3UHQ.jpeg");
        singleLinks.put("SWS/Rumor Monger", "wSN6H6v.jpeg");
        singleLinks.put("SWS/Sabacc Game", "qIcFb3U.jpeg");
        singleLinks.put("SWS/Salvage Squad", "ThYSxmD.jpeg");
        singleLinks.put("SWS/Sand Trooper", "ysfpyL8.jpeg");
        singleLinks.put("SWS/Sarlacc Pit", "N4dcnln.png");
        singleLinks.put("SWS/Scout the Perimeter", "2cObUbz.jpeg");
        singleLinks.put("SWS/Scout Trooper", "9RAY4U1.jpeg");
        singleLinks.put("SWS/Security Droid", "dzy8m4v.jpeg");
        singleLinks.put("SWS/Senator Bail Organa", "BRkUuYU.jpeg");
        singleLinks.put("SWS/Senator Lott Dod", "yYQtXZo.jpeg");
        singleLinks.put("SWS/Senator Onaconda Farr", "oPez77z.png");
        singleLinks.put("SWS/Senator Padme Amidala", "287deD9.jpeg");
        singleLinks.put("SWS/Senator Passel Argente", "51qpnaE.jpeg");
        singleLinks.put("SWS/Shaak Herd", "PtnZD0I.jpeg");
        singleLinks.put("SWS/Shadow Trooper", "09NAiGa.jpeg");
        singleLinks.put("SWS/Shock Trooper", "UVNOxMR.jpeg");
        singleLinks.put("SWS/Show of Dominance", "ru2D3Qp.jpeg");
        singleLinks.put("SWS/Sith Assassin", "Nt8WUCj.jpeg");
        singleLinks.put("SWS/Sith Citadel", "bBCbK30.jpeg");
        singleLinks.put("SWS/Sith Evoker", "jwRzCEB.jpeg");
        singleLinks.put("SWS/Sith Holocron", "07Ufx5X.jpeg");
        singleLinks.put("SWS/Sith Inquisitor", "NivI1E5.jpeg");
        singleLinks.put("SWS/Sith Lord", "lWBfQoA.jpeg");
        singleLinks.put("SWS/Sith Magic", "cDPFeve.jpeg");
        singleLinks.put("SWS/Sith Manipulator", "Q7CIvyz.jpeg");
        singleLinks.put("SWS/Sith Marauder", "9zZUSJW.jpeg");
        singleLinks.put("SWS/Sith Mindseer", "Lmps3oO.jpeg");
        singleLinks.put("SWS/Sith Ravager", "nl9Dp41.jpeg");
        singleLinks.put("SWS/Sith Ruins", "oSqiYyO.jpeg");
        singleLinks.put("SWS/Sith Sorcerer", "Pq37iop.jpeg");
        singleLinks.put("SWS/Sith Thoughtseeker", "YzIY1di.jpeg");
        singleLinks.put("SWS/Slave I", "QUGpxlb.jpeg");
        singleLinks.put("SWS/Smash to Smithereens", "UlzDZWp.jpeg");
        singleLinks.put("SWS/Snow Trooper", "28Jp5JL.jpeg");
        singleLinks.put("SWS/Speeder Trooper", "BIEnTDL.jpeg");
        singleLinks.put("SWS/Star Destroyer", "DYUMXHZ.jpeg");
        singleLinks.put("SWS/Strike Team Commando", "783ZFsF.jpeg");
        singleLinks.put("SWS/Super Battle Droid", "T8IjrKD.jpeg");
        singleLinks.put("SWS/Surprise Maneuver", "uaAmzz8.jpeg");
        singleLinks.put("SWS/Swarm the Skies", "Ti1McaV.jpeg");
        singleLinks.put("SWS/Syndicate Enforcer", "xZ0g2Sx.jpeg");
        singleLinks.put("SWS/Tank Droid", "N4YyMje.jpeg");
        singleLinks.put("SWS/Terentatek Cub", "nbmBxst.jpeg");
        singleLinks.put("SWS/The Battle of Endor", "vhL6gdI.jpeg");
        singleLinks.put("SWS/The Battle of Geonosis", "7Fnr2MD.jpeg");
        singleLinks.put("SWS/The Battle of Hoth", "wcVJwKP.jpeg");
        singleLinks.put("SWS/The Battle of Naboo", "aesSN9E.jpeg");
        singleLinks.put("SWS/The Battle of Yavin", "AtGE49k.jpeg");
        singleLinks.put("SWS/The Death Star", "2LjSXa9.jpeg");
        singleLinks.put("SWS/TIE Bomber", "R2l7ZXQ.jpeg");
        singleLinks.put("SWS/TIE Interceptor", "fYY4PUR.jpeg");
        singleLinks.put("SWS/Trade Federation Battleship", "sKN9Gzv.jpeg");
        singleLinks.put("SWS/Tri-Fighter", "IhwHzqT.jpeg");
        singleLinks.put("SWS/Trooper Armor", "nqnFj9f.jpeg");
        singleLinks.put("SWS/Trooper Commando", "PiAYXJv.jpeg");
        singleLinks.put("SWS/Twi'lek Seductress", "iPhUxUV.jpeg");
        singleLinks.put("SWS/Ugnaught Scrap Worker", "fuuNN3n.jpeg");
        singleLinks.put("SWS/Underworld Slums", "o4CFq3x.jpeg");
        singleLinks.put("SWS/Unity of the Droids", "WFAIRy3.jpeg");
        singleLinks.put("SWS/Unruly Sureshot", "AHymfLc.jpeg");
        singleLinks.put("SWS/Vapor Snag", "8g3q0ny.jpeg");
        singleLinks.put("SWS/V-Wing", "7eThuU9.jpeg");
        singleLinks.put("SWS/Weequay Beastmaster", "metAs1p.jpeg");
        singleLinks.put("SWS/Wild Holocron", "adw7dFO.jpeg");
        singleLinks.put("SWS/Wisdom of the Jedi", "TgTj2Dd.jpeg");
        singleLinks.put("SWS/Womp Rat", "XKF79Hr.jpeg");
        singleLinks.put("SWS/Wookiee Bounty Hunter", "A76UGTJ.jpeg");
        singleLinks.put("SWS/Wookiee Mystic", "8DCkOVe.jpeg");
        singleLinks.put("SWS/Wookiee Raidleader", "ZZTduL5.jpeg");
        singleLinks.put("SWS/X-Wing", "AV1LPuZ.jpeg");
        singleLinks.put("SWS/Yoda, Jedi Master", "6arN1Hl.png");
        singleLinks.put("SWS/Y-Wing", "aQQ5zwA.jpeg");
        singleLinks.put("SWS/Zam Wesell", "ToG0C1r.jpeg");
        singleLinks.put("SWS/Astromech Droid", "v0TpHMh.jpeg");
        singleLinks.put("SWS/Buried Ruin", "QkmIWYg.png");
        singleLinks.put("SWS/Flame Trooper", "RkY7KFJ.jpeg");
        singleLinks.put("SWS/Force Stasis", "FavLrcY.jpeg");
        singleLinks.put("SWS/Salvage Trader", "qGwk7Bn.jpeg");
        singleLinks.put("SWS/Outer Rim Gang", "kEjKQGy.png");
        singleLinks.put("SWS/Rathtar", "CYhHRqF.png");
        singleLinks.put("SWS/Riot Trooper", "PusvaQB.jpeg");
        singleLinks.put("SWS/Sins of the Father", "32YHTPB.jpeg");
        singleLinks.put("SWS/Upsilon-class Shuttle", "Le3F3oW.jpeg");
        singleLinks.put("SWS/Finn", "TU2LI2q.jpeg");
        singleLinks.put("SWS/General Hux", "UpWfcV6.png");
        singleLinks.put("SWS/Poe Dameron", "v8i21dn.png");
        singleLinks.put("SWS/Rey", "7n5ZZFA.png");
        singleLinks.put("SWS/Kylo Ren", "fFzDMTz.png");
        singleLinks.put("SWS/TIE Striker", "6b5GDUQ.jpeg");
        singleLinks.put("SWS/Bludgeoning Pain", "ap5k3Wl.jpeg");
        singleLinks.put("SWS/Force Protection", "GrOQLHO.jpeg");
        singleLinks.put("SWS/Gerrera's Revolutionary", "FQFE1Jt.jpeg");
        singleLinks.put("SWS/Thermal Detonator", "gTPLM83.jpeg");
        singleLinks.put("SWS/Hammerhead Corvette", "IlhOAGv.jpeg");
        singleLinks.put("SWS/U-Wing", "FmoRCmG.jpeg");
        singleLinks.put("SWS/Bor Gullet", "jXafYHX.jpeg");
        singleLinks.put("SWS/Imperial Hovertank", "6X1wL4d.jpeg");
        singleLinks.put("SWS/Occupation", "h4mmkA5.jpeg");
        singleLinks.put("SWS/Resistance", "lbNhA59.jpeg");
        singleLinks.put("SWS/Jyn Erso and Cassian Andor", "o0SCGiJ.jpeg");
        singleLinks.put("SWS/Chirrut Imwe", "wgtXfUF.jpeg");
        singleLinks.put("SWS/Director Krennic", "52PGsH5.jpeg");
        singleLinks.put("SWS/Vader's Command", "7Lql6UT.jpeg");
        singleLinks.put("SWS/Delay Tactic", "ubmzD1m.jpeg");
        singleLinks.put("SWS/Resistance Bomber", "Sudfkd7.jpeg");
        singleLinks.put("SWS/Mouse Droid", "oO0p8QE.jpeg");
        singleLinks.put("SWS/First Order Dreadnought", "80pO9Cc.jpeg");
        singleLinks.put("SWS/TIE Silencer", "7yeYIjX.jpeg");
        singleLinks.put("SWS/Canto Bight Enforcer", "VKPQVsn.jpeg");
        singleLinks.put("SWS/Cantonica Casino", "7LiSvy6.jpeg");
        singleLinks.put("SWS/Fathier", "0oKquQp.jpeg");
        singleLinks.put("SWS/Code Slice", "7uNASji.jpeg");
        singleLinks.put("SWS/Captain Phasma", "LWujx1B.jpeg");
        singleLinks.put("SWS/Force Telepathy", "e90hswX.jpeg");
        singleLinks.put("SWS/Praetorian Trooper", "pjS1wyS.jpeg");
        singleLinks.put("SWS/Supreme Leader Snoke", "eewWiKE.jpeg");
        singleLinks.put("SWS/Sai Tok", "FVn29tT.jpeg");
        singleLinks.put("SWS/Porg Nest", "8DnNZKc.jpeg");
        singleLinks.put("SWS/Inspire", "7lIXhtd.jpeg");
        singleLinks.put("SWS/Force Projection", "5EfOwyn.jpeg");
        singleLinks.put("SWS/Luke Skywalker, the Last Jedi", "WMmQcyD.jpeg");
        singleLinks.put("SWS/Vulptex", "30WeCkw.jpeg");
        singleLinks.put("SWS/Glorious Charge", "yJwvKzk.jpeg");
        singleLinks.put("SWS/Plains-520b", "Fx59r9J.jpeg");
        singleLinks.put("SWS/Island-520a", "jIPpWp5.jpeg");
        singleLinks.put("SWS/Conscription", "An01yAe.jpeg");
        singleLinks.put("SWS/Afterburn", "2ydqSvT.jpeg");
        singleLinks.put("SWS/Corellian Gunship", "mZdDQWH.jpeg");
        singleLinks.put("SWS/Despair", "TLTddMI.jpeg");
        singleLinks.put("SWS/Dryden Vos", "6LbtUzN.jpeg");
        singleLinks.put("SWS/Droid Uprising", "aWuoxho.jpeg");
        singleLinks.put("SWS/Gamble", "Hwzr60O.jpeg");
        singleLinks.put("SWS/Han Solo, Scrumrat", "Hqj39dG.jpeg");
        singleLinks.put("SWS/Mud Trooper", "af8JaDy.jpeg");
        singleLinks.put("SWS/Enfys Nest", "pstVfQg.jpeg");
        singleLinks.put("SWS/Kalevan Star Yacht", "nHmSizp.jpeg");
        singleLinks.put("SWS/Maelstrom Blockade", "sUYT0pc.jpeg");
        singleLinks.put("SWS/Range Trooper", "kXGvTkE.jpeg");
        singleLinks.put("SWS/Tobias Beckett", "hzm6ilE.jpeg");
        singleLinks.put("SWS/Underground Forum", "FH2pRfU.jpeg");
        singleLinks.put("SWS/Chewbacca, the Beast", "Zb5TitZ.jpeg");
        singleLinks.put("SWS/A Jedi's Fervor", "5MjPOpE.jpeg");
        singleLinks.put("SWS/Allegiant General Pryde", "Ucithhc.jpeg");
        singleLinks.put("SWS/Balance", "EMdbTBj.jpeg");
        singleLinks.put("SWS/Band Together", "xc9dQaZ.jpeg");
        singleLinks.put("SWS/Ben Solo", "4GXbkI3.jpeg");
        singleLinks.put("SWS/Betray", "SjyF6Nq.jpeg");
        singleLinks.put("SWS/Brave the Elements", "z2etu3V.jpeg");
        singleLinks.put("SWS/Culling Dais", "NHRMrmo.jpeg");
        singleLinks.put("SWS/Droidsmith", "HYyiEI7.jpeg");
        singleLinks.put("SWS/Dyad Force Transfer", "dmyQ7Jm.jpeg");
        singleLinks.put("SWS/Festival of the Ancestors", "JUPnIEr.jpeg");
        singleLinks.put("SWS/First Order Jet Trooper", "wVqmWMK.jpeg");
        singleLinks.put("SWS/Force Lift", "46eGWAq.jpeg");
        singleLinks.put("SWS/General Organa", "YSv61yR.jpeg");
        singleLinks.put("SWS/Hidden Base", "M6BR6aH.jpeg");
        singleLinks.put("SWS/Hold Captive", "3dR542o.jpeg");
        singleLinks.put("SWS/Holochess", "lPS1mR6.jpeg");
        singleLinks.put("SWS/Knights of Ren", "vdXyRpy.jpeg");
        singleLinks.put("SWS/Lightspeed Skipping", "VRquVqA.jpeg");
        singleLinks.put("SWS/Luke's Lightsaber", "Ty3j9y3.jpeg");
        singleLinks.put("SWS/Mimic Vat", "9bAK1LC.jpeg");
        singleLinks.put("SWS/Orbak", "8RvSbhX.jpeg");
        singleLinks.put("SWS/Propaganda", "TBNlxqj.jpeg");
        singleLinks.put("SWS/Rey Skywalker", "dOyjNXc.jpeg");
        singleLinks.put("SWS/Rey's Lightsaber", "eMzKXAP.jpeg");
        singleLinks.put("SWS/Sith Eternal Lightning", "oaFkj3N.jpeg");
        singleLinks.put("SWS/Sith Wayfinder", "AznLRt0.jpeg");
        singleLinks.put("SWS/Swamp-e", "LrbqB3U.jpeg");
        singleLinks.put("SWS/Training Droid", "JCY9KOM.jpeg");
        singleLinks.put("SWS/Unpleasant Discovery", "lDKoeAu.jpeg");
        singleLinks.put("SWS/Vexis", "cNQX9Ue.jpeg");
        singleLinks.put("SWS/War Room (Star Wars)", "pqQ9kzt.jpeg");
        singleLinks.put("SWS/Xyston Star Destroyer", "oqbHtUC.jpeg");
        singleLinks.put("SWS/Zorii Bliss", "vOyNE39.jpeg");
        singleLinks.put("ATC/Blinding Radiance", "4B4lBl5.jpeg");
        singleLinks.put("ATC/Goblin Bruiser", "Di5I8tS.jpeg");
        singleLinks.put("ATC/Ogre Painbringer", "eA3zL7Y.jpeg");
        singleLinks.put("ATC/Titanic Pelagosaur", "KWSsiKD.jpeg");
        singleLinks.put("ATC/Treetop Recluse", "uHnQYpp.jpeg");

        // Emblems
        singleLinks.put("SWS/Emblem Obi-Wan Kenobi", "Qyc10aT.png");
        singleLinks.put("SWS/Emblem Aurra Sing", "BLWbVJC.png");
        singleLinks.put("SWS/Emblem Yoda", "zH0sYxg.png");
        singleLinks.put("SWS/Emblem Luke Skywalker", "kHELZDJ.jpeg");

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
        singleLinks.put("SWS/Trooper 2", "tcxvGOn.jpeg");
        singleLinks.put("SWS/Porg", "HBjt1A3.jpeg");
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
    public Map<String, String> getHttpRequestHeaders(String fullUrl) {
        Map<String, String> headers = CardImageSource.super.getHttpRequestHeaders(fullUrl);

        if (fullUrl.startsWith(IMGUR_IMAGE_URL)) {
            // imgur require page referer to download image
            Matcher matcher = IMGUR_IMAGE_ID_PATTERN.matcher(fullUrl);
            if (matcher.find()) {
                headers.put("Host", "imgur.com");
                headers.put("Referer", "https://imgur.com/" + matcher.group(1));
            } else {
                LOGGER.error("Can't find image id from url " + fullUrl);
            }
        }

        return headers;
    }

    @Override
    public boolean isCardSource() {
        return true;
    }

    private String prepareFullUrl(CardDownloadData card, String url) {
        if (url.startsWith("http")) {
            // from direct link
            return url;
        } else {
            // from imgur.com
            return IMGUR_IMAGE_URL + url;
        }
    }


    @Override
    public List<String> getSupportedSets() {
        return new ArrayList<>(supportedSets);
    }

    @Override
    public boolean isCardImageProvided(String setCode, String cardName) {
        if (singleLinks == null) {
            setupLinks();
        }
        return singleLinks.containsKey(setCode + "/" + cardName)
                || singleLinks.containsKey(setCode + "/" + cardName + "-a")
                || singleLinks.containsKey(setCode + "/" + cardName + "-b")
                || singleLinks.containsKey(setCode + "/" + cardName + "-c")
                || singleLinks.containsKey(setCode + "/" + cardName + "-d");
    }

    @Override
    public boolean isTokenImageProvided(String setCode, String cardName, Integer tokenNumber) {
        if (singleLinks == null) {
            setupLinks();
        }
        return singleLinks.containsKey(setCode + "/" + cardName);
    }
}
