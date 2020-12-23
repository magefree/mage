package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Kaldheim extends ExpansionSet {

    private static final Kaldheim instance = new Kaldheim();

    public static Kaldheim getInstance() {
        return instance;
    }

    private Kaldheim() {
        super("Kaldheim", "KHM", ExpansionSet.buildDate(2021, 2, 5), SetType.EXPANSION);
        this.blockName = "Kaldheim";
        this.hasBasicLands = false; // TODO: change after more cards added
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;
        this.maxCardNumberInBooster = 285;

        cards.add(new SetCardInfo("Absorb Identity", 383, Rarity.UNCOMMON, mage.cards.a.AbsorbIdentity.class));
        cards.add(new SetCardInfo("Armed and Armored", 379, Rarity.UNCOMMON, mage.cards.a.ArmedAndArmored.class));
        cards.add(new SetCardInfo("Barkchannel Pathway", 251, Rarity.RARE, mage.cards.b.BarkchannelPathway.class));
        cards.add(new SetCardInfo("Bearded Axe", 388, Rarity.UNCOMMON, mage.cards.b.BeardedAxe.class));
        cards.add(new SetCardInfo("Blightstep Pathway", 252, Rarity.RARE, mage.cards.b.BlightstepPathway.class));
        cards.add(new SetCardInfo("Canopy Tactician", 378, Rarity.RARE, mage.cards.c.CanopyTactician.class));
        cards.add(new SetCardInfo("Cleaving Reaper", 376, Rarity.RARE, mage.cards.c.CleavingReaper.class));
        cards.add(new SetCardInfo("Darkbore Pathway", 254, Rarity.RARE, mage.cards.d.DarkborePathway.class));
        cards.add(new SetCardInfo("Elderfang Ritualist", 385, Rarity.UNCOMMON, mage.cards.e.ElderfangRitualist.class));
        cards.add(new SetCardInfo("Elven Ambush", 391, Rarity.UNCOMMON, mage.cards.e.ElvenAmbush.class));
        cards.add(new SetCardInfo("Giant's Grasp", 384, Rarity.UNCOMMON, mage.cards.g.GiantsGrasp.class));
        cards.add(new SetCardInfo("Gilded Assault Cart", 390, Rarity.UNCOMMON, mage.cards.g.GildedAssaultCart.class));
        cards.add(new SetCardInfo("Gladewalker Ritualist", 392, Rarity.UNCOMMON, mage.cards.g.GladewalkerRitualist.class));
        cards.add(new SetCardInfo("Hengegate Pathway", 260, Rarity.RARE, mage.cards.h.HengegatePathway.class));
        cards.add(new SetCardInfo("Kaya the Inexorable", 218, Rarity.MYTHIC, mage.cards.k.KayaTheInexorable.class));
        cards.add(new SetCardInfo("Rampage of the Valkyries", 393, Rarity.UNCOMMON, mage.cards.r.RampageOfTheValkyries.class));
        cards.add(new SetCardInfo("Renegade Reaper", 386, Rarity.UNCOMMON, mage.cards.r.RenegadeReaper.class));
        cards.add(new SetCardInfo("Showdown of the Skalds", 229, Rarity.RARE, mage.cards.s.ShowdownOfTheSkalds.class));
        cards.add(new SetCardInfo("Starnheim Aspirant", 380, Rarity.UNCOMMON, mage.cards.s.StarnheimAspirant.class));
        cards.add(new SetCardInfo("Surtland Flinger", 377, Rarity.RARE, mage.cards.s.SurtlandFlinger.class));
    }
}
