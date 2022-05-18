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
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Aarakocra Sneak", 54, Rarity.COMMON, mage.cards.a.AarakocraSneak.class));
        cards.add(new SetCardInfo("Ancient Brass Dragon", 111, Rarity.MYTHIC, mage.cards.a.AncientBrassDragon.class));
        cards.add(new SetCardInfo("Arcane Signet", 298, Rarity.UNCOMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Astral Confrontation", 6, Rarity.COMMON, mage.cards.a.AstralConfrontation.class));
        cards.add(new SetCardInfo("Baba Lysaga, Night Witch", 266, Rarity.RARE, mage.cards.b.BabaLysagaNightWitch.class));
        cards.add(new SetCardInfo("Baldur's Gate", 345, Rarity.RARE, mage.cards.b.BaldursGate.class));
        cards.add(new SetCardInfo("Bane's Invoker", 7, Rarity.COMMON, mage.cards.b.BanesInvoker.class));
        cards.add(new SetCardInfo("Bhaal's Invoker", 163, Rarity.COMMON, mage.cards.b.BhaalsInvoker.class));
        cards.add(new SetCardInfo("Bountiful Promenade", 348, Rarity.RARE, mage.cards.b.BountifulPromenade.class));
        cards.add(new SetCardInfo("Bramble Sovereign", 218, Rarity.MYTHIC, mage.cards.b.BrambleSovereign.class));
        cards.add(new SetCardInfo("Charcoal Diamond", 305, Rarity.COMMON, mage.cards.c.CharcoalDiamond.class));
        cards.add(new SetCardInfo("Command Tower", 351, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Cultist of the Absolute", 123, Rarity.RARE, mage.cards.c.CultistOfTheAbsolute.class));
        cards.add(new SetCardInfo("Dungeoneer's Pack", 312, Rarity.UNCOMMON, mage.cards.d.DungeoneersPack.class));
        cards.add(new SetCardInfo("Elder Brain", 125, Rarity.RARE, mage.cards.e.ElderBrain.class));
        cards.add(new SetCardInfo("Elminster's Simulacrum", 561, Rarity.MYTHIC, mage.cards.e.ElminstersSimulacrum.class));
        cards.add(new SetCardInfo("Faceless One", 1, Rarity.COMMON, mage.cards.f.FacelessOne.class));
        cards.add(new SetCardInfo("Fang Dragon", 173, Rarity.COMMON, mage.cards.f.FangDragon.class));
        cards.add(new SetCardInfo("Fire Diamond", 313, Rarity.COMMON, mage.cards.f.FireDiamond.class));
        cards.add(new SetCardInfo("Fireball", 175, Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Forest", 467, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goggles of Night", 74, Rarity.COMMON, mage.cards.g.GogglesOfNight.class));
        cards.add(new SetCardInfo("Gorion, Wise Mentor", 276, Rarity.RARE, mage.cards.g.GorionWiseMentor.class));
        cards.add(new SetCardInfo("Island", 455, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Bolt", 187, Rarity.COMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Luxury Suite", 355, Rarity.RARE, mage.cards.l.LuxurySuite.class));
        cards.add(new SetCardInfo("Marble Diamond", 320, Rarity.COMMON, mage.cards.m.MarbleDiamond.class));
        cards.add(new SetCardInfo("Minsc & Boo, Timeless Heroes", 285, Rarity.MYTHIC, mage.cards.m.MinscBooTimelessHeroes.class));
        cards.add(new SetCardInfo("Morphic Pool", 357, Rarity.RARE, mage.cards.m.MorphicPool.class));
        cards.add(new SetCardInfo("Moss Diamond", 327, Rarity.COMMON, mage.cards.m.MossDiamond.class));
        cards.add(new SetCardInfo("Mountain", 463, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Noble's Purse", 331, Rarity.UNCOMMON, mage.cards.n.NoblesPurse.class));
        cards.add(new SetCardInfo("Passageway Seer", 141, Rarity.UNCOMMON, mage.cards.p.PassagewaySeer.class));
        cards.add(new SetCardInfo("Plains", 451, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raised by Giants", 250, Rarity.RARE, mage.cards.r.RaisedByGiants.class));
        cards.add(new SetCardInfo("Reflecting Pool", 358, Rarity.RARE, mage.cards.r.ReflectingPool.class));
        cards.add(new SetCardInfo("Roving Harper", 40, Rarity.COMMON, mage.cards.r.RovingHarper.class));
        cards.add(new SetCardInfo("Sea Hag", 95, Rarity.COMMON, mage.cards.s.SeaHag.class));
        cards.add(new SetCardInfo("Sea of Clouds", 360, Rarity.RARE, mage.cards.s.SeaOfClouds.class));
        cards.add(new SetCardInfo("Sky Diamond", 337, Rarity.COMMON, mage.cards.s.SkyDiamond.class));
        cards.add(new SetCardInfo("Spire Garden", 361, Rarity.RARE, mage.cards.s.SpireGarden.class));
        cards.add(new SetCardInfo("Stirge", 150, Rarity.COMMON, mage.cards.s.Stirge.class));
        cards.add(new SetCardInfo("Swamp", 459, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftfoot Boots", 339, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Tymora's Invoker", 101, Rarity.COMMON, mage.cards.t.TymorasInvoker.class));
        cards.add(new SetCardInfo("Wand of Wonder", 204, Rarity.RARE, mage.cards.w.WandOfWonder.class));
        cards.add(new SetCardInfo("Wayfarer's Bauble", 344, Rarity.COMMON, mage.cards.w.WayfarersBauble.class));
        cards.add(new SetCardInfo("White Plume Adventurer", 49, Rarity.RARE, mage.cards.w.WhitePlumeAdventurer.class));
        cards.add(new SetCardInfo("Wilson, Refined Grizzly", 261, Rarity.UNCOMMON, mage.cards.w.WilsonRefinedGrizzly.class));
        cards.add(new SetCardInfo("Zevlor, Elturel Exile", 296, Rarity.RARE, mage.cards.z.ZevlorElturelExile.class));
    }
}
