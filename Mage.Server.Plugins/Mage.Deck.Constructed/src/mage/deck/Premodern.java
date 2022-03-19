package mage.deck;

import mage.cards.decks.Constructed;

/**
 * @author jmharmon
 */

public class Premodern extends Constructed {

    public Premodern() {
        super("Constructed - Premodern");

        // Legal sets
        setCodes.add(mage.sets.FourthEdition.getInstance().getCode());
        setCodes.add(mage.sets.IceAge.getInstance().getCode());
        setCodes.add(mage.sets.Chronicles.getInstance().getCode());
        setCodes.add(mage.sets.Homelands.getInstance().getCode());
        setCodes.add(mage.sets.Alliances.getInstance().getCode());
        setCodes.add(mage.sets.Mirage.getInstance().getCode());
        setCodes.add(mage.sets.Visions.getInstance().getCode());
        setCodes.add(mage.sets.FifthEdition.getInstance().getCode());
        setCodes.add(mage.sets.Weatherlight.getInstance().getCode());
        setCodes.add(mage.sets.Tempest.getInstance().getCode());
        setCodes.add(mage.sets.Stronghold.getInstance().getCode());
        setCodes.add(mage.sets.Exodus.getInstance().getCode());
        setCodes.add(mage.sets.UrzasSaga.getInstance().getCode());
        setCodes.add(mage.sets.UrzasLegacy.getInstance().getCode());
        setCodes.add(mage.sets.ClassicSixthEdition.getInstance().getCode());
        setCodes.add(mage.sets.UrzasDestiny.getInstance().getCode());
        setCodes.add(mage.sets.MercadianMasques.getInstance().getCode());
        setCodes.add(mage.sets.Nemesis.getInstance().getCode());
        setCodes.add(mage.sets.Prophecy.getInstance().getCode());
        setCodes.add(mage.sets.Invasion.getInstance().getCode());
        setCodes.add(mage.sets.Planeshift.getInstance().getCode());
        setCodes.add(mage.sets.SeventhEdition.getInstance().getCode());
        setCodes.add(mage.sets.Apocalypse.getInstance().getCode());
        setCodes.add(mage.sets.Odyssey.getInstance().getCode());
        setCodes.add(mage.sets.Torment.getInstance().getCode());
        setCodes.add(mage.sets.Judgment.getInstance().getCode());
        setCodes.add(mage.sets.Onslaught.getInstance().getCode());
        setCodes.add(mage.sets.Legions.getInstance().getCode());
        setCodes.add(mage.sets.Scourge.getInstance().getCode());

        // Ban List
        banned.add("Balance");
        banned.add("Brainstorm");
        banned.add("Channel");
        banned.add("Demonic Consultation");
        banned.add("Earthcraft");
        banned.add("Entomb");
        banned.add("Flash");
        banned.add("Force of Will");
        banned.add("Goblin Recruiter");
        banned.add("Grim Monolith");
        banned.add("Mana Vault");
        banned.add("Memory Jar");
        banned.add("Mind's Desire");
        banned.add("Mind Twist");
        banned.add("Mystical Tutor");
        banned.add("Necropotence");
        banned.add("Show and Tell");
        banned.add("Strip Mine");
        banned.add("Tendrils of Agony");
        banned.add("Time Spiral");
        banned.add("Tolarian Academy");
        banned.add("Vampiric Tutor");
        banned.add("Windfall");
        banned.add("Worldgorger Dragon");
        banned.add("Yawgmoth's Bargain");
        banned.add("Yawgmoth's Will");
    }
}
