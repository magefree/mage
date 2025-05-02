package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pw25
 */
public class WizardsPlayNetwork2025 extends ExpansionSet {

    private static final WizardsPlayNetwork2025 instance = new WizardsPlayNetwork2025();

    public static WizardsPlayNetwork2025 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2025() {
        super("Wizards Play Network 2025", "PW25", ExpansionSet.buildDate(2025, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Dragon's Hoard", 2, Rarity.RARE, mage.cards.d.DragonsHoard.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragonspeaker Shaman", 3, Rarity.RARE, mage.cards.d.DragonspeakerShaman.class));
        cards.add(new SetCardInfo("Rishkar's Expertise", 1, Rarity.RARE, mage.cards.r.RishkarsExpertise.class, RETRO_ART));
    }
}
