package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/slu
 */
public class SecretLairUltimateEdition extends ExpansionSet {

    private static final SecretLairUltimateEdition instance = new SecretLairUltimateEdition();

    public static SecretLairUltimateEdition getInstance() {
        return instance;
    }

    private SecretLairUltimateEdition() {
        super("Secret Lair: Ultimate Edition", "SLU", ExpansionSet.buildDate(2020, 5, 29), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arid Mesa", 4, Rarity.RARE, mage.cards.a.AridMesa.class));
        cards.add(new SetCardInfo("Barkchannel Pathway", 11, Rarity.RARE, mage.cards.b.BarkchannelPathway.class));
        cards.add(new SetCardInfo("Blast Zone", 504, Rarity.RARE, mage.cards.b.BlastZone.class));
        cards.add(new SetCardInfo("Blightstep Pathway", 12, Rarity.RARE, mage.cards.b.BlightstepPathway.class));
        cards.add(new SetCardInfo("Branchloft Pathway", 13, Rarity.RARE, mage.cards.b.BranchloftPathway.class));
        cards.add(new SetCardInfo("Brightclimb Pathway", 14, Rarity.RARE, mage.cards.b.BrightclimbPathway.class));
        cards.add(new SetCardInfo("Clearwater Pathway", 15, Rarity.RARE, mage.cards.c.ClearwaterPathway.class));
        cards.add(new SetCardInfo("Cragcrown Pathway", 16, Rarity.RARE, mage.cards.c.CragcrownPathway.class));
        cards.add(new SetCardInfo("Darkbore Pathway", 17, Rarity.RARE, mage.cards.d.DarkborePathway.class));
        cards.add(new SetCardInfo("Hengegate Pathway", 18, Rarity.RARE, mage.cards.h.HengegatePathway.class));
        cards.add(new SetCardInfo("Marsh Flats", 1, Rarity.RARE, mage.cards.m.MarshFlats.class));
        cards.add(new SetCardInfo("Misty Rainforest", 5, Rarity.RARE, mage.cards.m.MistyRainforest.class));
        cards.add(new SetCardInfo("Needleverge Pathway", 19, Rarity.RARE, mage.cards.n.NeedlevergePathway.class));
        cards.add(new SetCardInfo("Riverglide Pathway", 20, Rarity.RARE, mage.cards.r.RiverglidePathway.class));
        cards.add(new SetCardInfo("Scalding Tarn", 2, Rarity.RARE, mage.cards.s.ScaldingTarn.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 3, Rarity.RARE, mage.cards.v.VerdantCatacombs.class));
    }
}
