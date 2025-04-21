package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pf25
 *
 * @author Jmlundeen
 */
public class MagicFest2025 extends ExpansionSet {

    private static final MagicFest2025 instance = new MagicFest2025();

    public static MagicFest2025 getInstance() {
        return instance;
    }

    private MagicFest2025() {
        super("MagicFest 2025", "PF25", ExpansionSet.buildDate(2025, 2, 3), SetType.PROMOTIONAL);

        cards.add(new SetCardInfo("Avacyn's Pilgrim", "1F", Rarity.RARE, mage.cards.a.AvacynsPilgrim.class, FULL_ART));
        cards.add(new SetCardInfo("Ponder", 2, Rarity.RARE, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Serra the Benevolent", 1, Rarity.MYTHIC, mage.cards.s.SerraTheBenevolent.class, RETRO_ART));
        cards.add(new SetCardInfo("The First Sliver", 3, Rarity.MYTHIC, mage.cards.t.TheFirstSliver.class));
        cards.add(new SetCardInfo("Yoshimaru, Ever Faithful", 4, Rarity.MYTHIC, mage.cards.y.YoshimaruEverFaithful.class));
    }
}
