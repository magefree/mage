package mage.deck;

import mage.cards.decks.Constructed;

/**
 * This class validates a deck for the Old School 93/94 format, specifically for
 * the EC Rules.
 * <p>
 * This was originally made to follow the deck construction rules found at the
 * Old School Mtg blog found at:
 * http://oldschool-mtg.blogspot.com/p/banrestriction.html
 * <p>
 * There is no mana burn in this version of old school
 *
 * @author jmharmon
 */
public class OldSchool9394EC extends Constructed {

    public OldSchool9394EC() {
        super("Constructed - Old School 93/94 - EC Rules");

        // use the set instances to make sure that we get the correct set codes
        setCodes.add(mage.sets.LimitedEditionAlpha.getInstance().getCode());
        setCodes.add(mage.sets.LimitedEditionBeta.getInstance().getCode());
        setCodes.add(mage.sets.UnlimitedEdition.getInstance().getCode());
        setCodes.add(mage.sets.ArabianNights.getInstance().getCode());
        setCodes.add(mage.sets.Antiquities.getInstance().getCode());
        setCodes.add(mage.sets.Legends.getInstance().getCode());
        setCodes.add(mage.sets.TheDark.getInstance().getCode());
        setCodes.add(mage.sets.FallenEmpires.getInstance().getCode());

        //Let Media Inserts Arena and Sewers of Estark being only cards playable
        banned.add("Acquire");
        banned.add("Aeronaut Tinkerer");
        banned.add("Ajani, Caller of the Pride");
        banned.add("Ajani Steadfast");
        banned.add("Alhammarret, High Arbiter");
        banned.add("Angelic Skirmisher");
        banned.add("Angel of Glory's Rise");
        banned.add("Ankle Shanker");
        banned.add("Arashin Sovereign");
        banned.add("Archfiend of Depravity");
        banned.add("Archfiend of Ifnir");
        banned.add("Arrest");
        banned.add("Assembled Alphas");
        banned.add("Avalanche Tusker");
        banned.add("Barrage Tyrant");
        banned.add("Bloodthrone Vampire");
        banned.add("Boltwing Marauder");
        banned.add("Bonescythe Sliver");
        banned.add("Breath of Malfegor");
        banned.add("Brion Stoutarm");
        banned.add("Broodmate Dragon");
        banned.add("Burning Sun's Avatar");
        banned.add("Canopy Vista");
        banned.add("Cathedral of War");
        banned.add("Celestial Colonnade");
        banned.add("Chandra, Fire of Kaladesh");
        banned.add("Chandra, Flamecaller");
        banned.add("Chandra, Pyromaster");
        banned.add("Chandra, Pyromaster");
        banned.add("Chandra, Roaring Flame");
        banned.add("Chandra, Torch of Defiance");
        banned.add("Chandra's Fury");
        banned.add("Chandra's Phoenix");
        banned.add("Cinder Glade");
        banned.add("Consume Spirit");
        banned.add("Corrupt");
        banned.add("Day of Judgment");
        banned.add("Deepfathom Skulker");
        banned.add("Defiant Bloodlord");
        banned.add("Devil's Play");
        banned.add("Dragon Fodder");
        banned.add("Dragonlord's Servant");
        banned.add("Dragonscale General");
        banned.add("Dread Defiler");
        banned.add("Dreg Mangler");
        banned.add("Drogskol Cavalry");
        banned.add("Dromoka, the Eternal");
        banned.add("Drowner of Hope");
        banned.add("Duress");
        banned.add("Dwynen, Gilt-Leaf Daen");
        banned.add("Eidolon of Blossoms");
        banned.add("Electrolyze");
        banned.add("Elusive Tormentor");
        banned.add("Emrakul, the Aeons Torn");
        banned.add("Evolving Wilds");
        banned.add("Faithless Looting");
        banned.add("Fated Conflagration");
        banned.add("Feast of Blood");
        banned.add("Flameblade Angel");
        banned.add("Flamerush Rider");
        banned.add("Foe-Razer Regent");
        banned.add("Frost Titan");
        banned.add("Garruk, Apex Predator");
        banned.add("Garruk, Caller of Beasts");
        banned.add("Garruk Wildspeaker");
        banned.add("Gaze of Granite");
        banned.add("Genesis Hydra");
        banned.add("Gideon of the Trials");
        banned.add("Gideon, Ally of Zendikar");
        banned.add("Gideon, Battle-Forged");
        banned.add("Gladehart Cavalry");
        banned.add("Goblin Dark-Dwellers");
        banned.add("Goblin Rabblemaster");
        banned.add("Gravecrawler");
        banned.add("Grave Titan");
        banned.add("Guul Draz Assassin");
        banned.add("Hamletback Goliath");
        banned.add("Harbinger of the Hunt");
        banned.add("Hero of Goma Fada");
        banned.add("Hixus, Prison Warden");
        banned.add("Honored Hierarch");
        banned.add("Honor of the Pure");
        banned.add("Inferno Titan");
        banned.add("Insidious Mist");
        banned.add("Ivorytusk Fortress");
        banned.add("Jace Beleren");
        banned.add("Jace, Memory Adept");
        banned.add("Jace, Telepath Unbound");
        banned.add("Jace, the Living Guildpact");
        banned.add("Jace, Unraveler of Secrets");
        banned.add("Jace, Unraveler of Secrets");
        banned.add("Jace, Vryn's Prodigy");
        banned.add("Jaya Ballard, Task Mage");
        banned.add("Karametra's Acolyte");
        banned.add("Knight Exemplar");
        banned.add("Kor Skyfisher");
        banned.add("Kothophed, Soul Hoarder");
        banned.add("Kytheon, Hero of Akros");
        banned.add("Lightning Hounds");
        banned.add("Liliana of the Dark Realms");
        banned.add("Liliana, Death's Majesty");
        banned.add("Liliana, Defiant Necromancer");
        banned.add("Liliana, the Last Hope");
        banned.add("Liliana Vess");
        banned.add("Liliana Vess");
        banned.add("Magister of Worth");
        banned.add("Markov Dreadknight");
        banned.add("Memoricide");
        banned.add("Merfolk Mesmerist");
        banned.add("Mirran Crusader");
        banned.add("Munda's Vanguard");
        banned.add("Necromaster Dragon");
        banned.add("Nephalia Moondrakes");
        banned.add("Niblis of Frost");
        banned.add("Nicol Bolas, God-Pharaoh");
        banned.add("Nightveil Specter");
        banned.add("Nissa Revane");
        banned.add("Nissa, Sage Animist");
        banned.add("Nissa, Steward of Elements");
        banned.add("Nissa, Vastwood Seer");
        banned.add("Nissa, Voice of Zendikar");
        banned.add("Nissa, Worldwaker");
        banned.add("Noosegraf Mob");
        banned.add("Ogre Arsonist");
        banned.add("Ogre Battledriver");
        banned.add("Ojutai's Command");
        banned.add("Oran-Rief Hydra");
        banned.add("Phyrexian Rager");
        banned.add("Pia and Kiran Nalaar");
        banned.add("Prairie Stream");
        banned.add("Primordial Hydra");
        banned.add("Pristine Skywise");
        banned.add("Rakshasa Vizier");
        banned.add("Ratchet Bomb");
        banned.add("Rattleclaw Mystic");
        banned.add("Ravenous Bloodseeker");
        banned.add("Relic Seeker");
        banned.add("Render Silent");
        banned.add("Retaliator Griffin");
        banned.add("Ruinous Path");
        banned.add("Sage-Eye Avengers");
        banned.add("Sage of the Inward Eye");
        banned.add("Sanctifier of Souls");
        banned.add("Sandsteppe Citadel");
        banned.add("Scavenging Ooze");
        banned.add("Scrap Trawler");
        banned.add("Scythe Leopard");
        banned.add("Seeker of the Way");
        banned.add("Serra Avatar");
        banned.add("Shamanic Revelation");
        banned.add("Siege Rhino");
        banned.add("Silverblade Paladin");
        banned.add("Silver Drake");
        banned.add("Skyship Stalker");
        banned.add("Smoldering Marsh");
        banned.add("Soul of Ravnica");
        banned.add("Soul of Zendikar");
        banned.add("Soul Swallower");
        banned.add("Spined Wurm");
        banned.add("Standstill");
        banned.add("Stealer of Secrets");
        banned.add("Steward of Valeron");
        banned.add("Sultai Charm");
        banned.add("Sunblast Angel");
        banned.add("Sunken Hollow");
        banned.add("Supreme Verdict");
        banned.add("Surgical Extraction");
        banned.add("Sylvan Caryatid");
        banned.add("Temur War Shaman");
        banned.add("Terastodon");
        banned.add("Thalia, Heretic Cathar");
        banned.add("Treasure Hunt");
        banned.add("Turnabout");
        banned.add("Tyrant of Valakut");
        banned.add("Ulvenwald Observer");
        banned.add("Valorous Stance");
        banned.add("Vampire Nocturnus");
        banned.add("Voidmage Husher");
        banned.add("Warmonger");
        banned.add("Wash Out");
        banned.add("Wildfire Eternal");
        banned.add("Xathrid Necromancer");

        restricted.add("Ancestral Recall");
        restricted.add("Balance");
        restricted.add("Black Lotus");
        restricted.add("Braingeyser");
        restricted.add("Channel");
        restricted.add("Chaos Orb");
        restricted.add("Demonic Tutor");
        restricted.add("Library of Alexandria");
        restricted.add("Mana Drain");
        restricted.add("Maze of Ith");
        restricted.add("Mind Twist");
        restricted.add("Mox Emerald");
        restricted.add("Mox Jet");
        restricted.add("Mox Pearl");
        restricted.add("Mox Ruby");
        restricted.add("Mox Sapphire");
        restricted.add("Recall");
        restricted.add("Regrowth");
        restricted.add("Sol Ring");
        restricted.add("Time Vault");
        restricted.add("Time Walk");
        restricted.add("Timetwister");
        restricted.add("Wheel of Fortune");
    }
}
