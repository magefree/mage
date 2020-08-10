package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pal02
 */
public class ArenaLeague2002 extends ExpansionSet {

    private static final ArenaLeague2002 instance = new ArenaLeague2002();

    public static ArenaLeague2002 getInstance() {
        return instance;
    }

    private ArenaLeague2002() {
        super("Arena League 2002", "PAL02", ExpansionSet.buildDate(2002, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Arc Lightning", 3, Rarity.RARE, mage.cards.a.ArcLightning.class));
        cards.add(new SetCardInfo("Dauthi Slayer", 4, Rarity.RARE, mage.cards.d.DauthiSlayer.class));
        cards.add(new SetCardInfo("Island", 1, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Man-o'-War", 2, Rarity.RARE, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Mana Leak", 5, Rarity.RARE, mage.cards.m.ManaLeak.class));
     }
}
