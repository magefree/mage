package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class CommanderLegendsBattleForBaldursGate extends ExpansionSet {

    private static final CommanderLegendsBattleForBaldursGate instance = new CommanderLegendsBattleForBaldursGate();

    public static CommanderLegendsBattleForBaldursGate getInstance() {
        return instance;
    }

    private CommanderLegendsBattleForBaldursGate() {
        super("Commander Legends: Battle for Baldur's Gate", "CLB", ExpansionSet.buildDate(2022, 6, 10), SetType.SUPPLEMENTAL);
        this.blockName = "Commander Legends";
        this.hasBasicLands = false;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Ancient Brass Dragon", 111, Rarity.MYTHIC, mage.cards.a.AncientBrassDragon.class));
        cards.add(new SetCardInfo("Bountiful Promenade", 348, Rarity.RARE, mage.cards.b.BountifulPromenade.class));
        cards.add(new SetCardInfo("Elder Brain", 125, Rarity.RARE, mage.cards.e.ElderBrain.class));
        cards.add(new SetCardInfo("Faceless One", 1, Rarity.COMMON, mage.cards.f.FacelessOne.class));
        cards.add(new SetCardInfo("Fireball", 175, Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Lightning Bolt", 187, Rarity.COMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Luxury Suite", 355, Rarity.RARE, mage.cards.l.LuxurySuite.class));
        cards.add(new SetCardInfo("Minsc & Boo, Timeless Heroes", 285, Rarity.MYTHIC, mage.cards.m.MinscBooTimelessHeroes.class));
        cards.add(new SetCardInfo("Morphic Pool", 357, Rarity.RARE, mage.cards.m.MorphicPool.class));
        cards.add(new SetCardInfo("Reflecting Pool", 358, Rarity.RARE, mage.cards.r.ReflectingPool.class));
        cards.add(new SetCardInfo("Sea of Clouds", 360, Rarity.RARE, mage.cards.s.SeaOfClouds.class));
        cards.add(new SetCardInfo("Spire Garden", 361, Rarity.RARE, mage.cards.s.SpireGarden.class));
        cards.add(new SetCardInfo("Wand of Wonder", 204, Rarity.RARE, mage.cards.w.WandOfWonder.class));
        cards.add(new SetCardInfo("Zevlor, Elturel Exile", 296, Rarity.RARE, mage.cards.z.ZevlorElturelExile.class));
    }
}
