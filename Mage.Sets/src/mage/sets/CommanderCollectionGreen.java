package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class CommanderCollectionGreen extends ExpansionSet {

    private static final CommanderCollectionGreen instance = new CommanderCollectionGreen();

    public static CommanderCollectionGreen getInstance() {
        return instance;
    }

    private CommanderCollectionGreen() {
        super("Commander Collection: Green", "CC1", ExpansionSet.buildDate(2020, 12, 4), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bane of Progress", 3, Rarity.RARE, mage.cards.b.BaneOfProgress.class));
        cards.add(new SetCardInfo("Command Tower", 8, Rarity.RARE, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Freyalise, Llanowar's Fury", 1, Rarity.MYTHIC, mage.cards.f.FreyaliseLlanowarsFury.class));
        cards.add(new SetCardInfo("Omnath, Locus of Mana", 2, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfMana.class));
        cards.add(new SetCardInfo("Seedborn Muse", 4, Rarity.RARE, mage.cards.s.SeedbornMuse.class));
        cards.add(new SetCardInfo("Sol Ring", 7, Rarity.RARE, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Sylvan Library", 5, Rarity.RARE, mage.cards.s.SylvanLibrary.class));
        cards.add(new SetCardInfo("Worldly Tutor", 6, Rarity.RARE, mage.cards.w.WorldlyTutor.class));
    }
}
