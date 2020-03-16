package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/cp2
 */
public class FateReforgedClashPack extends ExpansionSet {

    private static final FateReforgedClashPack instance = new FateReforgedClashPack();

    public static FateReforgedClashPack getInstance() {
        return instance;
    }

    private FateReforgedClashPack() {
        super("Fate Reforged Clash Pack", "CP2", ExpansionSet.buildDate(2015, 1, 23), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Courser of Kruphix", 6, Rarity.RARE, mage.cards.c.CourserOfKruphix.class));
        cards.add(new SetCardInfo("Hero's Downfall", 2, Rarity.RARE, mage.cards.h.HerosDownfall.class));
        cards.add(new SetCardInfo("Necropolis Fiend", 1, Rarity.RARE, mage.cards.n.NecropolisFiend.class));
        cards.add(new SetCardInfo("Reaper of the Wilds", 4, Rarity.RARE, mage.cards.r.ReaperOfTheWilds.class));
        cards.add(new SetCardInfo("Sultai Ascendancy", 3, Rarity.RARE, mage.cards.s.SultaiAscendancy.class));
        cards.add(new SetCardInfo("Whip of Erebos", 5, Rarity.RARE, mage.cards.w.WhipOfErebos.class));
     }
}
