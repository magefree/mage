package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdgm
 */
public class DragonsMazePromos extends ExpansionSet {

    private static final DragonsMazePromos instance = new DragonsMazePromos();

    public static DragonsMazePromos getInstance() {
        return instance;
    }

    private DragonsMazePromos() {
        super("Dragon's Maze Promos", "PDGM", ExpansionSet.buildDate(2013, 4, 27), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Breaking // Entering", "124*", Rarity.RARE, mage.cards.b.BreakingEntering.class));
        cards.add(new SetCardInfo("Maze's End", "152*", Rarity.MYTHIC, mage.cards.m.MazesEnd.class));
        cards.add(new SetCardInfo("Melek, Izzet Paragon", 84, Rarity.RARE, mage.cards.m.MelekIzzetParagon.class));
        cards.add(new SetCardInfo("Plains", "157*", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Render Silent", "96*", Rarity.RARE, mage.cards.r.RenderSilent.class));
        cards.add(new SetCardInfo("Trostani's Summoner", 110, Rarity.UNCOMMON, mage.cards.t.TrostanisSummoner.class));
    }
}
