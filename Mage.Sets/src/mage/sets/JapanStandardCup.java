package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pjsc
 *
 * @author DarioBarrabinoDev
 */
public final class JapanStandardCup extends ExpansionSet {

    private static final JapanStandardCup instance = new JapanStandardCup();

    public static JapanStandardCup getInstance() {
        return instance;
    }

    private JapanStandardCup() {
        super("Japan Standard Cup", "PJSC", ExpansionSet.buildDate(2025, 2, 9), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Brotherhood's End", "2025-2", Rarity.RARE, mage.cards.b.BrotherhoodsEnd.class));
        cards.add(new SetCardInfo("Sheltered by Ghosts", "2026-1", Rarity.RARE, mage.cards.s.ShelteredByGhosts.class));
        cards.add(new SetCardInfo("Tishana's Tidebinder", "2025-1", Rarity.RARE, mage.cards.t.TishanasTidebinder.class));
        cards.add(new SetCardInfo("Unstoppable Slasher", "2026-2", Rarity.RARE, mage.cards.u.UnstoppableSlasher.class));
    }
}
