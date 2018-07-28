
package org.mage.plugins.card.dl.sources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.mage.plugins.card.images.CardDownloadData;

/**
 * @author spjspj
 */
public enum GrabbagImageSource implements CardImageSource {

    instance;
    private static final Logger logger = Logger.getLogger(GrabbagImageSource.class);

    private static final Set<String> supportedSets = new LinkedHashSet<String>() {
        {
            add("PTC");
            add("SWS");
            add("DDSC");
        }
    };

    @Override
    public String getSourceName() {
        return "";
    }

    @Override
    public float getAverageSize() {
        return 26.7f;
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
    public CardImageUrls generateURL(CardDownloadData card) throws Exception {
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

    HashMap<String, String> singleLinks = null;

    private void setupLinks() {
        if (singleLinks != null) {
            return;
        }
        singleLinks = new HashMap<>();
        singleLinks.put("PTC/Arbiter of the Ideal", "MTG/BNG/en/promo/ArbiterOfTheIdeal.jpg");
        singleLinks.put("PTC/Courser of Kruphix", "MTG/BNG/en/promo/CourserOfKruphix.jpg");
        singleLinks.put("PTC/Eater of Hope", "MTG/BNG/en/promo/EaterOfHope.jpg");
        singleLinks.put("PTC/Fated Return", "MTG/BNG/en/promo/FatedReturn.jpg");
        singleLinks.put("PTC/Forgestoker Dragon", "MTG/BNG/en/promo/ForgestokerDragon.jpg");
        singleLinks.put("PTC/Nessian Wilds Ravager", "MTG/BNG/en/promo/NessianWildsRavager.jpg");
        singleLinks.put("PTC/Pain Seer", "MTG/BNG/en/promo/PainSeer.jpg");
        singleLinks.put("PTC/Silent Sentinel", "MTG/BNG/en/promo/SilentSentinel.jpg");
        singleLinks.put("PTC/Tromokratis", "MTG/BNG/en/promo/Tromokratis.jpg");

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
        singleLinks.put("SWS/Senator Padmé Amidala", "287deD9.jpg");
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
        // Emblems
        singleLinks.put("SWS/Emblem Obi-Wan Kenobi", "Qyc10aT.png");
        singleLinks.put("SWS/Aurra Sing", "BLWbVJC.png");
        singleLinks.put("SWS/Yoda", "zH0sYxg.png");
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

        // Duel Decks: StarCraft
        singleLinks.put("DDSC/Aggressive Mutation", "PQxO3eL.jpg");
        singleLinks.put("DDSC/Baneling", "BAmTZI6.jpg");
        singleLinks.put("DDSC/Brood Lord", "BuDGuJv.jpg");
        singleLinks.put("DDSC/Changeling", "No69iVp.jpg");
        singleLinks.put("DDSC/Consume", "sV52eeO.jpg");
        singleLinks.put("DDSC/Contaminate", "z9YK5mS.jpg");
        singleLinks.put("DDSC/Corruptor", "nYCuDmF.jpg");
        singleLinks.put("DDSC/Creep Colony", "pJFYnnM.jpg");
        singleLinks.put("DDSC/Dark Swarm", "XBXV1ws.jpg");
        singleLinks.put("DDSC/Defiler", "2bSFc3B.jpg");
        singleLinks.put("DDSC/Drone", "D6HPGl3.jpg");
        singleLinks.put("DDSC/Evolution Chamber", "rGk08gP.jpg");
        singleLinks.put("DDSC/Forest-a", "GG87THT.jpg");
        singleLinks.put("DDSC/Forest-b", "Vk9fy1d.jpg");
        singleLinks.put("DDSC/Forest-c", "bmvdFpy.jpg");
        singleLinks.put("DDSC/Forest-d", "cOEslyA.jpg");
        singleLinks.put("DDSC/Gamete Meiosis", "bIn7NdG.jpg");
        singleLinks.put("DDSC/Hive Cluster", "5CFZef8.jpg");
        singleLinks.put("DDSC/Hydralisk", "M4EMhwP.jpg");
        singleLinks.put("DDSC/Infested Terran", "IKkM9uR.jpg");
        singleLinks.put("DDSC/Infestor", "0BvPwPv.jpg");
        singleLinks.put("DDSC/Kerrigan, Queen of Blades", "CWve1XR.jpg");
        singleLinks.put("DDSC/Larva", "6TDAq0L.jpg");
        singleLinks.put("DDSC/Metabolic Boost", "xHg5zto.jpg");
        singleLinks.put("DDSC/Mutalisk", "VROPdgH.jpg");
        singleLinks.put("DDSC/Nydus Network", "z09rjCo.jpg");
        singleLinks.put("DDSC/Nydus Worm", "e4GPq4a.jpg");
        singleLinks.put("DDSC/Overlord", "TyJ79Op.jpg");
        singleLinks.put("DDSC/Parasite", "Qa29BZ6.jpg");
        singleLinks.put("DDSC/Queen", "vyxpuHX.jpg");
        singleLinks.put("DDSC/Roach", "XM6UZKc.jpg");
        singleLinks.put("DDSC/Scourge", "46f6fwI.jpg");
        singleLinks.put("DDSC/Spawn Broodling", "XyfsyrW.jpg");
        singleLinks.put("DDSC/Spore Crawler", "9bQk32j.jpg");
        singleLinks.put("DDSC/Swamp-a", "0OTWXTN.jpg");
        singleLinks.put("DDSC/Swamp-b", "R7DgDYd.jpg");
        singleLinks.put("DDSC/Swamp-c", "bWzi309.jpg");
        singleLinks.put("DDSC/Swarm Host", "K8Ic7Pa.jpg");
        singleLinks.put("DDSC/Ultralisk", "EbxD3Lw.jpg");
        singleLinks.put("DDSC/Unburrow", "eY3gzVL.jpg");
        singleLinks.put("DDSC/Viper", "bQ3tI0m.jpg");
        singleLinks.put("DDSC/Volatile Burst", "0G7W0Vg.jpg");
        singleLinks.put("DDSC/Zerg Egg", "ZFMVs4u.jpg");
        singleLinks.put("DDSC/Zerg Lurker", "JjqLERW.jpg");
        singleLinks.put("DDSC/Zergling", "Tti5XjV.jpg");
        singleLinks.put("DDSC/Auto-Turret", "myKuM1r.jpg");
        singleLinks.put("DDSC/Battlecruiser", "dV8iqZS.jpg");
        singleLinks.put("DDSC/Bunker", "yTWGZEc.jpg");
        singleLinks.put("DDSC/Calldown", "2y88QyC.jpg");
        singleLinks.put("DDSC/Command Center", "uhS8XdY.jpg");
        singleLinks.put("DDSC/Covert Ops", "VWPa6Cx.jpg");
        singleLinks.put("DDSC/Defense Matrix", "gCpaNQa.jpg");
        singleLinks.put("DDSC/EMP Round", "QVQuzf1.jpg");
        singleLinks.put("DDSC/Firebat", "I7GfoED.jpg");
        singleLinks.put("DDSC/Gauss Rifle", "NMwZuJ1.jpg");
        singleLinks.put("DDSC/Ghost Academy", "9eEkHYw.jpg");
        singleLinks.put("DDSC/Ghost", "byYwmDR.jpg");
        singleLinks.put("DDSC/Hellbat", "u2bDlW7.jpg");
        singleLinks.put("DDSC/Hellion", "3Cnxjfi.jpg");
        singleLinks.put("DDSC/Irradiation", "V4hqDqO.jpg");
        singleLinks.put("DDSC/Liftoff", "VAI9Gqf.jpg");
        singleLinks.put("DDSC/Lockdown", "osZq2Th.jpg");
        singleLinks.put("DDSC/Marauder", "amKHHXg.jpg");
        singleLinks.put("DDSC/Marine", "ozAc6pG.jpg");
        singleLinks.put("DDSC/Medic", "TR92u3w.jpg");
        singleLinks.put("DDSC/Mountain-a", "bh9c1Xf.jpg");
        singleLinks.put("DDSC/Mountain-b", "PEll2a4.jpg");
        singleLinks.put("DDSC/Mountain-c", "WKRB3nn.jpg");
        singleLinks.put("DDSC/Mountain-d", "5MjHURX.jpg");
        singleLinks.put("DDSC/Nuclear Strike", "pPcwv1O.jpg");
        singleLinks.put("DDSC/Plains-a", "sIFYF0Y.jpg");
        singleLinks.put("DDSC/Plains-b", "bJQ4YdD.jpg");
        singleLinks.put("DDSC/Plains-c", "YBjD4xd.jpg");
        singleLinks.put("DDSC/Raven", "Q8ROLtP.jpg");
        singleLinks.put("DDSC/Raynor, Rebel Commander", "WDg3G5k.jpg");
        singleLinks.put("DDSC/Reaper", "fmGf0Xw.jpg");
        singleLinks.put("DDSC/Repair", "X5qj8MQ.jpg");
        singleLinks.put("DDSC/Scanner Sweep", "vdYRLwJ.jpg");
        singleLinks.put("DDSC/SCV", "s0DirXH.jpg");
        singleLinks.put("DDSC/Siege Tank", "azwzFiu.jpg");
        singleLinks.put("DDSC/Stimpack", "BhwPDh4.jpg");
        singleLinks.put("DDSC/Strike Cannon", "0wlKiEN.jpg");
        singleLinks.put("DDSC/Supply Depot", "FGbjWF8.jpg");
        singleLinks.put("DDSC/Terran Banshee", "aSY3vB3.jpg");
        singleLinks.put("DDSC/Thor", "GxG0342.jpg");
        singleLinks.put("DDSC/Viking", "buB9dT3.jpg");
        singleLinks.put("DDSC/Vulture", "yLIbwwn.jpg");
        singleLinks.put("DDSC/Widow Mine", "W0oXqxW.jpg");
        singleLinks.put("DDSC/Wraith", "mGnXIJt.jpg");
        singleLinks.put("DDSC/Arbiter", "b0d0d56.jpg");
        singleLinks.put("DDSC/Archon", "KZI5hjy.jpg");
        singleLinks.put("DDSC/Carrier", "UXium2U.jpg");
        singleLinks.put("DDSC/Chrono Boost", "j0ei7KF.jpg");
        singleLinks.put("DDSC/Corsair", "sojXtp3.jpg");
        singleLinks.put("DDSC/Dark Templar", "SjwmjUv.jpg");
        singleLinks.put("DDSC/Disruption Web", "GbSTfD1.jpg");
        singleLinks.put("DDSC/Disruptor", "HmbM3gu.jpg");
        singleLinks.put("DDSC/Dragoon", "rIsZH5w.jpg");
        singleLinks.put("DDSC/Envision", "AtSrrza.jpg");
        singleLinks.put("DDSC/Force Field", "DjzG7Ia.jpg");
        singleLinks.put("DDSC/Hallucination", "2dcbZuo.jpg");
        singleLinks.put("DDSC/High Templar", "8GhZML6.jpg");
        singleLinks.put("DDSC/Immortal", "MdYxWvF.jpg");
        singleLinks.put("DDSC/Island-a", "F0NUqFT.jpg");
        singleLinks.put("DDSC/Island-b", "sSH04Ly.jpg");
        singleLinks.put("DDSC/Island-c", "LUypLo3.jpg");
        singleLinks.put("DDSC/Island-d", "ta3UEbT.jpg");
        singleLinks.put("DDSC/Mass Recall", "wWTROHY.jpg");
        singleLinks.put("DDSC/Mind Control", "pF7Vb31.jpg");
        singleLinks.put("DDSC/Mothership", "ApxDV9w.jpg");
        singleLinks.put("DDSC/Nexus", "Hi4nTR2.jpg");
        singleLinks.put("DDSC/Observatory", "8u8pk5e.jpg");
        singleLinks.put("DDSC/Photon Overcharge", "JTXanSD.jpg");
        singleLinks.put("DDSC/Plains-d", "FPtBSMw.jpg");
        singleLinks.put("DDSC/Plains-e", "WqWvLl1.jpg");
        singleLinks.put("DDSC/Plains-f", "jLygP1t.jpg");
        singleLinks.put("DDSC/Protoss Probe", "Xf83L01.jpg");
        singleLinks.put("DDSC/Psionic Feedback", "PNxAmfI.jpg");
        singleLinks.put("DDSC/Psionic Storm", "UhdAzje.jpg");
        singleLinks.put("DDSC/Pylon", "wcNWmKb.jpg");
        singleLinks.put("DDSC/Reaver", "ulcDJlj.jpg");
        singleLinks.put("DDSC/Recharge", "n7fSE82.jpg");
        singleLinks.put("DDSC/Sensor Array", "45YagzO.jpg");
        singleLinks.put("DDSC/Sentry", "4none6Y.jpg");
        singleLinks.put("DDSC/Stalker", "sBuWOOo.jpg");
        singleLinks.put("DDSC/Stasis Ward", "GVEJoRe.jpg");
        singleLinks.put("DDSC/Templar Archives", "BzZWxFS.jpg");
        singleLinks.put("DDSC/Void Ray", "W1n4lJ6.jpg");
        singleLinks.put("DDSC/Vortex", "d3VAL3L.jpg");
        singleLinks.put("DDSC/Warp Gate", "u6cBL4C.jpg");
        singleLinks.put("DDSC/Warp Prism", "nyTMa9O.jpg");
        singleLinks.put("DDSC/Zealot", "yemVJoK.jpg");
        singleLinks.put("DDSC/Zeratul, Nezarim Prelate", "7Fv5A2J.jpg");
        singleLinks.put("DDSC/Mineral Field", "r8nIBdG.jpg");

        // Emblems
        singleLinks.put("DDSC/Emblem Zeratul", "B9ybuPh.png");
        // Tokens
        singleLinks.put("DDSC/Zerg", "uAkstaB.jpg");
        singleLinks.put("DDSC/Terran Soldier", "pCMSeMJ.jpg");
        singleLinks.put("DDSC/Protoss", "3GrmY63.jpg");
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws IOException {
        try {
            return generateURL(card);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(GrabbagImageSource.class.getName()).log(Level.SEVERE, null, ex);
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

    private String getSourceName(CardDownloadData card, String httpImageUrl) {
        if (card.getSet().equals("MTG")) {
            return "http://static.starcitygames.com/sales/cardscans/";
        } else if (card.getSet().equals("SWS") || card.getSet().equals("DDSC")) {
            return "http://i.imgur.com/";
        } else {
            return "http://magiccards.info/scans/en/";
        }
    }

    @Override
    public ArrayList<String> getSupportedSets() {
        ArrayList<String> supportedSetsCopy = new ArrayList<>();
        supportedSetsCopy.addAll(supportedSets);
        return supportedSetsCopy;
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
    public boolean isImageProvided(String setCode, String cardName) {
        if (singleLinks == null) {
            setupLinks();
        }
        return singleLinks.containsKey(setCode + "/" + cardName) || singleLinks.containsKey(setCode + "/" + cardName + "-a");
    }

    @Override
    public boolean isSetSupportedComplete(String setCode) {
        return false;
    }

}
