package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AssassinsCreed extends ExpansionSet {

    private static final AssassinsCreed instance = new AssassinsCreed();

    public static AssassinsCreed getInstance() {
        return instance;
    }

    private AssassinsCreed() {
        super("Assassin's Creed", "ACR", ExpansionSet.buildDate(2024, 7, 5), SetType.EXPANSION);
        this.blockName = "Assassin's Creed"; // for sorting in GUI
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Cleopatra, Exiled Pharaoh", 52, Rarity.MYTHIC, mage.cards.c.CleopatraExiledPharaoh.class));
        cards.add(new SetCardInfo("Conspiracy", 162, Rarity.RARE, mage.cards.c.Conspiracy.class));
        cards.add(new SetCardInfo("Cover of Darkness", 89, Rarity.RARE, mage.cards.c.CoverOfDarkness.class));
        cards.add(new SetCardInfo("Eivor, Battle-Ready", 274, Rarity.MYTHIC, mage.cards.e.EivorBattleReady.class));
        cards.add(new SetCardInfo("Ezio, Blade of Vengeance", 275, Rarity.MYTHIC, mage.cards.e.EzioBladeOfVengeance.class));
        cards.add(new SetCardInfo("Haystack", 175, Rarity.UNCOMMON, mage.cards.h.Haystack.class));
        cards.add(new SetCardInfo("Hidden Blade", 73, Rarity.UNCOMMON, mage.cards.h.HiddenBlade.class));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 99, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class));
        cards.add(new SetCardInfo("Temporal Trespass", 86, Rarity.MYTHIC, mage.cards.t.TemporalTrespass.class));
        cards.add(new SetCardInfo("The Animus", 69, Rarity.RARE, mage.cards.t.TheAnimus.class));
        cards.add(new SetCardInfo("The Spear of Leonidas", 165, Rarity.RARE, mage.cards.t.TheSpearOfLeonidas.class));
    }
}
