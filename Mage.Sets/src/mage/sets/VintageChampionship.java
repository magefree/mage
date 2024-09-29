package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ovnt
 */
public class VintageChampionship extends ExpansionSet {

    private static final VintageChampionship instance = new VintageChampionship();

    public static VintageChampionship getInstance() {
        return instance;
    }

    private VintageChampionship() {
        super("Vintage Championship", "OVNT", ExpansionSet.buildDate(2019, 11, 3), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancestral Recall", 2005, Rarity.RARE, mage.cards.a.AncestralRecall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancestral Recall", 2013, Rarity.RARE, mage.cards.a.AncestralRecall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancestral Recall", 2018, Rarity.SPECIAL, mage.cards.a.AncestralRecall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Black Lotus", 2003, Rarity.RARE, mage.cards.b.BlackLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Black Lotus", "2017NA", Rarity.SPECIAL, mage.cards.b.BlackLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Emerald", 2009, Rarity.RARE, mage.cards.m.MoxEmerald.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Emerald", 2015, Rarity.SPECIAL, mage.cards.m.MoxEmerald.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Jet", 2007, Rarity.RARE, mage.cards.m.MoxJet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Jet", "2016EU", Rarity.SPECIAL, mage.cards.m.MoxJet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Jet", "2019NA", Rarity.SPECIAL, mage.cards.m.MoxJet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Pearl", 2006, Rarity.RARE, mage.cards.m.MoxPearl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Pearl", 2014, Rarity.SPECIAL, mage.cards.m.MoxPearl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Ruby", 2008, Rarity.RARE, mage.cards.m.MoxRuby.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Ruby", "2017EU", Rarity.SPECIAL, mage.cards.m.MoxRuby.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Sapphire", 2010, Rarity.RARE, mage.cards.m.MoxSapphire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Sapphire", "2016NA", Rarity.SPECIAL, mage.cards.m.MoxSapphire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Sapphire", "2019A", Rarity.MYTHIC, mage.cards.m.MoxSapphire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Time Walk", 2011, Rarity.RARE, mage.cards.t.TimeWalk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Time Walk", "2018NA", Rarity.SPECIAL, mage.cards.t.TimeWalk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timetwister", 2004, Rarity.RARE, mage.cards.t.Timetwister.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timetwister", 2012, Rarity.RARE, mage.cards.t.Timetwister.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timetwister", "2018A", Rarity.SPECIAL, mage.cards.t.Timetwister.class, NON_FULL_USE_VARIOUS));
     }
}
