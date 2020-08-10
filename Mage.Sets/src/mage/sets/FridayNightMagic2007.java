package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f07
 */
public class FridayNightMagic2007 extends ExpansionSet {

    private static final FridayNightMagic2007 instance = new FridayNightMagic2007();

    public static FridayNightMagic2007 getInstance() {
        return instance;
    }

    private FridayNightMagic2007() {
        super("Friday Night Magic 2007", "F07", ExpansionSet.buildDate(2007, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Basking Rootwalla", 4, Rarity.RARE, mage.cards.b.BaskingRootwalla.class));
        cards.add(new SetCardInfo("Cabal Coffers", 10, Rarity.RARE, mage.cards.c.CabalCoffers.class));
        cards.add(new SetCardInfo("Deep Analysis", 2, Rarity.RARE, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Engineered Plague", 7, Rarity.RARE, mage.cards.e.EngineeredPlague.class));
        cards.add(new SetCardInfo("Firebolt", 1, Rarity.RARE, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Force Spike", 12, Rarity.RARE, mage.cards.f.ForceSpike.class));
        cards.add(new SetCardInfo("Gerrard's Verdict", 3, Rarity.RARE, mage.cards.g.GerrardsVerdict.class));
        cards.add(new SetCardInfo("Goblin Legionnaire", 6, Rarity.RARE, mage.cards.g.GoblinLegionnaire.class));
        cards.add(new SetCardInfo("Goblin Ringleader", 8, Rarity.RARE, mage.cards.g.GoblinRingleader.class));
        cards.add(new SetCardInfo("Roar of the Wurm", 11, Rarity.RARE, mage.cards.r.RoarOfTheWurm.class));
        cards.add(new SetCardInfo("Wing Shards", 9, Rarity.RARE, mage.cards.w.WingShards.class));
        cards.add(new SetCardInfo("Wonder", 5, Rarity.RARE, mage.cards.w.Wonder.class));
     }
}
