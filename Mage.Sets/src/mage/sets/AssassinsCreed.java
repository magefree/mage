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
        this.hasBasicLands = true;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Cover of Darkness", 89, Rarity.RARE, mage.cards.c.CoverOfDarkness.class));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 99, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class));
        cards.add(new SetCardInfo("Temporal Trespass", 86, Rarity.MYTHIC, mage.cards.t.TemporalTrespass.class));
    }
}
