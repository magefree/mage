package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/psdc
 */
public class SanDiegoComicCon2013 extends ExpansionSet {

    private static final SanDiegoComicCon2013 instance = new SanDiegoComicCon2013();

    public static SanDiegoComicCon2013 getInstance() {
        return instance;
    }

    private SanDiegoComicCon2013() {
        super("San Diego Comic-Con 2013", "PSDC", ExpansionSet.buildDate(2013, 7, 18), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ajani, Caller of the Pride", "1*", Rarity.MYTHIC, mage.cards.a.AjaniCallerOfThePride.class));
        cards.add(new SetCardInfo("Chandra, Pyromaster", "132*", Rarity.MYTHIC, mage.cards.c.ChandraPyromaster.class));
        cards.add(new SetCardInfo("Garruk, Caller of Beasts", "172*", Rarity.MYTHIC, mage.cards.g.GarrukCallerOfBeasts.class));
        cards.add(new SetCardInfo("Jace, Memory Adept", "60*", Rarity.MYTHIC, mage.cards.j.JaceMemoryAdept.class));
        cards.add(new SetCardInfo("Liliana of the Dark Realms", "102*", Rarity.MYTHIC, mage.cards.l.LilianaOfTheDarkRealms.class));
    }
}
