package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class SignatureSpellbookJace extends ExpansionSet {

    private static final SignatureSpellbookJace instance = new SignatureSpellbookJace();

    public static SignatureSpellbookJace getInstance() {
        return instance;
    }

    private SignatureSpellbookJace() {
        super("Signature Spellbook: Jace", "SS1", ExpansionSet.buildDate(2018, 6, 15), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Blue Elemental Blast", 2, Rarity.RARE, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Brainstorm", 3, Rarity.RARE, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Counterspell", 4, Rarity.RARE, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Gifts Ungiven", 5, Rarity.RARE, mage.cards.g.GiftsUngiven.class));
        cards.add(new SetCardInfo("Jace Beleren", 1, Rarity.MYTHIC, mage.cards.j.JaceBeleren.class));
        cards.add(new SetCardInfo("Mystical Tutor", 6, Rarity.RARE, mage.cards.m.MysticalTutor.class));
        cards.add(new SetCardInfo("Negate", 7, Rarity.RARE, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Threads of Disloyalty", 8, Rarity.RARE, mage.cards.t.ThreadsOfDisloyalty.class));
    }
}
