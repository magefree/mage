package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwor
 */
public class WorldChampionshipPromos extends ExpansionSet {

    private static final WorldChampionshipPromos instance = new WorldChampionshipPromos();

    public static WorldChampionshipPromos getInstance() {
        return instance;
    }

    private WorldChampionshipPromos() {
        super("World Championship Promos", "PWOR", ExpansionSet.buildDate(1999, 8, 4), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Balduvian Horde", 1, Rarity.RARE, mage.cards.b.BalduvianHorde.class, RETRO_ART));
        cards.add(new SetCardInfo("Crucible of Worlds", 2019, Rarity.MYTHIC, mage.cards.c.CrucibleOfWorlds.class));
     }
}
