package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pf26
 *
 * @author muz
 */
public class MagicFest2026 extends ExpansionSet {

    private static final MagicFest2026 instance = new MagicFest2026();

    public static MagicFest2026 getInstance() {
        return instance;
    }

    private MagicFest2026() {
        super("MagicFest 2026", "PF26", ExpansionSet.buildDate(2026, 1, 1), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Wayfarer's Bauble", "1", Rarity.RARE, mage.cards.w.WayfarersBauble.class, FULL_ART));
        cards.add(new SetCardInfo("Fyndhorn Elves", "2", Rarity.RARE, mage.cards.f.FyndhornElves.class, FULL_ART));
        cards.add(new SetCardInfo("Prismatic Ending", "3", Rarity.RARE, mage.cards.p.PrismaticEnding.class, FULL_ART));
        cards.add(new SetCardInfo("Consecrated Sphinx", "4", Rarity.MYTHIC, mage.cards.c.ConsecratedSphinx.class, RETRO_ART));
        cards.add(new SetCardInfo("Counterspell", "5", Rarity.RARE, mage.cards.c.Counterspell.class, RETRO_ART));
        cards.add(new SetCardInfo("Preordain", "7", Rarity.RARE, mage.cards.p.Preordain.class, FULL_ART));
        cards.add(new SetCardInfo("Llanowar Elves", "8", Rarity.RARE, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Utopia Sprawl", "9", Rarity.RARE, mage.cards.u.UtopiaSprawl.class, FULL_ART));
        cards.add(new SetCardInfo("Atraxa, Praetors' Voice", "10", Rarity.MYTHIC, mage.cards.a.AtraxaPraetorsVoice.class));
        cards.add(new SetCardInfo("Fact or Fiction", "12", Rarity.RARE, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Explore", "13", Rarity.RARE, mage.cards.e.Explore.class));
    }
}
