package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwwk
 */
public class WorldwakePromos extends ExpansionSet {

    private static final WorldwakePromos instance = new WorldwakePromos();

    public static WorldwakePromos getInstance() {
        return instance;
    }

    private WorldwakePromos() {
        super("Worldwake Promos", "PWWK", ExpansionSet.buildDate(2010, 7, 30), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Celestial Colonnade", 133, Rarity.RARE, mage.cards.c.CelestialColonnade.class));
        cards.add(new SetCardInfo("Comet Storm", "76*", Rarity.MYTHIC, mage.cards.c.CometStorm.class));
        cards.add(new SetCardInfo("Joraga Warcaller", "106*", Rarity.RARE, mage.cards.j.JoragaWarcaller.class));
        cards.add(new SetCardInfo("Ruthless Cullblade", "65*", Rarity.COMMON, mage.cards.r.RuthlessCullblade.class));
    }
}
