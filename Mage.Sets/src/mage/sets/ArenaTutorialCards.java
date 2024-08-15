
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * Unofficial set with removed MTGA tutorial cards
 * (it was introduced and later removed from Arena New Player Experience Extras)
 *
 * @author Svyatoslav28
 */
public final class ArenaTutorialCards extends ExpansionSet {

    private static final ArenaTutorialCards instance = new ArenaTutorialCards();

    public static ArenaTutorialCards getInstance() {
        return instance;
    }

    private ArenaTutorialCards() {
        super("Arena Tutorial Cards", "ATC", ExpansionSet.buildDate(2024, 5, 18), SetType.CUSTOM_SET);
        this.blockName = "Arena Tutorial Cards";
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Blinding Radiance", 1, Rarity.UNCOMMON, mage.cards.b.BlindingRadiance.class));
        cards.add(new SetCardInfo("Goblin Bruiser", 3, Rarity.UNCOMMON, mage.cards.g.GoblinBruiser.class));
        cards.add(new SetCardInfo("Ogre Painbringer", 4, Rarity.RARE, mage.cards.o.OgrePainbringer.class));
        cards.add(new SetCardInfo("Titanic Pelagosaur", 2, Rarity.UNCOMMON, mage.cards.t.TitanicPelagosaur.class));
        cards.add(new SetCardInfo("Treetop Recluse", 5, Rarity.COMMON, mage.cards.t.TreetopRecluse.class));
    }
}