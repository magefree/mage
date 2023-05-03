package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarchOfTheMachineTheAftermath extends ExpansionSet {

    private static final MarchOfTheMachineTheAftermath instance = new MarchOfTheMachineTheAftermath();

    public static MarchOfTheMachineTheAftermath getInstance() {
        return instance;
    }

    private MarchOfTheMachineTheAftermath() {
        super("March of the Machine: The Aftermath", "MAT", ExpansionSet.buildDate(2023, 5, 12), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.blockName = "March of the Machine";
        this.hasBasicLands = false;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Campus Renovation", 27, Rarity.UNCOMMON, mage.cards.c.CampusRenovation.class));
        cards.add(new SetCardInfo("Coppercoat Vanguard", 1, Rarity.UNCOMMON, mage.cards.c.CoppercoatVanguard.class));
        cards.add(new SetCardInfo("Drannith Ruins", 50, Rarity.RARE, mage.cards.d.DrannithRuins.class));
        cards.add(new SetCardInfo("Filter Out", 7, Rarity.UNCOMMON, mage.cards.f.FilterOut.class));
        cards.add(new SetCardInfo("Gold-Forged Thopteryx", 31, Rarity.UNCOMMON, mage.cards.g.GoldForgedThopteryx.class));
        cards.add(new SetCardInfo("Harnessed Snubhorn", 3, Rarity.UNCOMMON, mage.cards.h.HarnessedSnubhorn.class));
        cards.add(new SetCardInfo("Jolrael, Voice of Zhalfir", 33, Rarity.RARE, mage.cards.j.JolraelVoiceOfZhalfir.class));
        cards.add(new SetCardInfo("Kolaghan Warmonger", 17, Rarity.UNCOMMON, mage.cards.k.KolaghanWarmonger.class));
        cards.add(new SetCardInfo("Markov Baron", 14, Rarity.UNCOMMON, mage.cards.m.MarkovBaron.class));
        cards.add(new SetCardInfo("Reckless Handling", 19, Rarity.UNCOMMON, mage.cards.r.RecklessHandling.class));
        cards.add(new SetCardInfo("Samut, Vizier of Naktamun", 45, Rarity.MYTHIC, mage.cards.s.SamutVizierOfNaktamun.class));
        cards.add(new SetCardInfo("Sigarda, Font of Blessings", 47, Rarity.RARE, mage.cards.s.SigardaFontOfBlessings.class));
        cards.add(new SetCardInfo("Spark Rupture", 5, Rarity.RARE, mage.cards.s.SparkRupture.class));
        cards.add(new SetCardInfo("The Kenriths' Royal Funeral", 34, Rarity.RARE, mage.cards.t.TheKenrithsRoyalFuneral.class));
        cards.add(new SetCardInfo("Training Grounds", 9, Rarity.RARE, mage.cards.t.TrainingGrounds.class));
    }
}
