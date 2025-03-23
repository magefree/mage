package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TarkirDragonstorm extends ExpansionSet {

    private static final TarkirDragonstorm instance = new TarkirDragonstorm();

    public static TarkirDragonstorm getInstance() {
        return instance;
    }

    private TarkirDragonstorm() {
        super("Tarkir: Dragonstorm", "TDM", ExpansionSet.buildDate(2025, 4, 11), SetType.EXPANSION);
        this.blockName = "Tarkir: Dragonstorm"; // for sorting in GUI
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abzan Devotee", 68, Rarity.COMMON, mage.cards.a.AbzanDevotee.class));
        cards.add(new SetCardInfo("Aegis Sculptor", 35, Rarity.UNCOMMON, mage.cards.a.AegisSculptor.class));
        cards.add(new SetCardInfo("Agent of Kotis", 36, Rarity.COMMON, mage.cards.a.AgentOfKotis.class));
        cards.add(new SetCardInfo("Alesha's Legacy", 72, Rarity.COMMON, mage.cards.a.AleshasLegacy.class));
        cards.add(new SetCardInfo("Anafenza, Unyielding Lineage", 2, Rarity.RARE, mage.cards.a.AnafenzaUnyieldingLineage.class));
        cards.add(new SetCardInfo("Auroral Procession", 169, Rarity.UNCOMMON, mage.cards.a.AuroralProcession.class));
        cards.add(new SetCardInfo("Awaken the Honored Dead", 170, Rarity.RARE, mage.cards.a.AwakenTheHonoredDead.class));
        cards.add(new SetCardInfo("Barrensteppe Siege", 171, Rarity.RARE, mage.cards.b.BarrensteppeSiege.class));
        cards.add(new SetCardInfo("Bearer of Glory", 4, Rarity.COMMON, mage.cards.b.BearerOfGlory.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 250, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Blossoming Sands", 251, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Bone-Cairn Butcher", 173, Rarity.UNCOMMON, mage.cards.b.BoneCairnButcher.class));
        cards.add(new SetCardInfo("Boulderborn Dragon", 239, Rarity.COMMON, mage.cards.b.BoulderbornDragon.class));
        cards.add(new SetCardInfo("Caustic Exhale", 74, Rarity.COMMON, mage.cards.c.CausticExhale.class));
        cards.add(new SetCardInfo("Cori Mountain Stalwart", 175, Rarity.UNCOMMON, mage.cards.c.CoriMountainStalwart.class));
        cards.add(new SetCardInfo("Craterhoof Behemoth", 138, Rarity.MYTHIC, mage.cards.c.CraterhoofBehemoth.class));
        cards.add(new SetCardInfo("Cruel Truths", 76, Rarity.COMMON, mage.cards.c.CruelTruths.class));
        cards.add(new SetCardInfo("Dalkovan Packbeasts", 7, Rarity.UNCOMMON, mage.cards.d.DalkovanPackbeasts.class));
        cards.add(new SetCardInfo("Descendant of Storms", 8, Rarity.UNCOMMON, mage.cards.d.DescendantOfStorms.class));
        cards.add(new SetCardInfo("Devoted Duelist", 104, Rarity.COMMON, mage.cards.d.DevotedDuelist.class));
        cards.add(new SetCardInfo("Dismal Backwater", 254, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Dracogenesis", 105, Rarity.MYTHIC, mage.cards.d.Dracogenesis.class));
        cards.add(new SetCardInfo("Dragonback Assault", 179, Rarity.MYTHIC, mage.cards.d.DragonbackAssault.class));
        cards.add(new SetCardInfo("Dragonback Lancer", 9, Rarity.COMMON, mage.cards.d.DragonbackLancer.class));
        cards.add(new SetCardInfo("Dragonologist", 42, Rarity.RARE, mage.cards.d.Dragonologist.class));
        cards.add(new SetCardInfo("Dragonstorm Forecaster", 43, Rarity.UNCOMMON, mage.cards.d.DragonstormForecaster.class));
        cards.add(new SetCardInfo("Dusyut Earthcarver", 141, Rarity.COMMON, mage.cards.d.DusyutEarthcarver.class));
        cards.add(new SetCardInfo("Equilibrium Adept", 106, Rarity.UNCOMMON, mage.cards.e.EquilibriumAdept.class));
        cards.add(new SetCardInfo("Evolving Wilds", 255, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Fangkeeper's Familiar", 183, Rarity.RARE, mage.cards.f.FangkeepersFamiliar.class));
        cards.add(new SetCardInfo("Forest", 285, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortress Kin-Guard", 12, Rarity.COMMON, mage.cards.f.FortressKinGuard.class));
        cards.add(new SetCardInfo("Fresh Start", 46, Rarity.UNCOMMON, mage.cards.f.FreshStart.class));
        cards.add(new SetCardInfo("Frontier Bivouac", 256, Rarity.UNCOMMON, mage.cards.f.FrontierBivouac.class));
        cards.add(new SetCardInfo("Heritage Reclamation", 145, Rarity.COMMON, mage.cards.h.HeritageReclamation.class));
        cards.add(new SetCardInfo("Inevitable Defeat", 194, Rarity.RARE, mage.cards.i.InevitableDefeat.class));
        cards.add(new SetCardInfo("Island", 279, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jade-Cast Sentinel", 243, Rarity.COMMON, mage.cards.j.JadeCastSentinel.class));
        cards.add(new SetCardInfo("Jeskai Devotee", 110, Rarity.COMMON, mage.cards.j.JeskaiDevotee.class));
        cards.add(new SetCardInfo("Jungle Hollow", 258, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Mardu Devotee", 16, Rarity.COMMON, mage.cards.m.MarduDevotee.class));
        cards.add(new SetCardInfo("Mountain", 283, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Jasper", 246, Rarity.MYTHIC, mage.cards.m.MoxJasper.class));
        cards.add(new SetCardInfo("Mystic Monastery", 262, Rarity.UNCOMMON, mage.cards.m.MysticMonastery.class));
        cards.add(new SetCardInfo("Narset, Jeskai Waymaster", 209, Rarity.RARE, mage.cards.n.NarsetJeskaiWaymaster.class));
        cards.add(new SetCardInfo("Nomad Outpost", 263, Rarity.UNCOMMON, mage.cards.n.NomadOutpost.class));
        cards.add(new SetCardInfo("Opulent Palace", 264, Rarity.UNCOMMON, mage.cards.o.OpulentPalace.class));
        cards.add(new SetCardInfo("Plains", 277, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Qarsi Revenant", 86, Rarity.RARE, mage.cards.q.QarsiRevenant.class));
        cards.add(new SetCardInfo("Rakshasa's Bargain", 214, Rarity.UNCOMMON, mage.cards.r.RakshasasBargain.class));
        cards.add(new SetCardInfo("Rally the Monastery", 19, Rarity.UNCOMMON, mage.cards.r.RallyTheMonastery.class));
        cards.add(new SetCardInfo("Rebellious Strike", 20, Rarity.COMMON, mage.cards.r.RebelliousStrike.class));
        cards.add(new SetCardInfo("Reigning Victor", 216, Rarity.COMMON, mage.cards.r.ReigningVictor.class));
        cards.add(new SetCardInfo("Ringing Strike Mastery", 53, Rarity.COMMON, mage.cards.r.RingingStrikeMastery.class));
        cards.add(new SetCardInfo("Roiling Dragonstorm", 55, Rarity.UNCOMMON, mage.cards.r.RoilingDragonstorm.class));
        cards.add(new SetCardInfo("Rugged Highlands", 265, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Sandsteppe Citadel", 266, Rarity.UNCOMMON, mage.cards.s.SandsteppeCitadel.class));
        cards.add(new SetCardInfo("Sarkhan's Resolve", 158, Rarity.COMMON, mage.cards.s.SarkhansResolve.class));
        cards.add(new SetCardInfo("Scoured Barrens", 267, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Shiko, Paragon of the Way", 223, Rarity.MYTHIC, mage.cards.s.ShikoParagonOfTheWay.class));
        cards.add(new SetCardInfo("Shock Brigade", 120, Rarity.COMMON, mage.cards.s.ShockBrigade.class));
        cards.add(new SetCardInfo("Skirmish Rhino", 224, Rarity.UNCOMMON, mage.cards.s.SkirmishRhino.class));
        cards.add(new SetCardInfo("Smile at Death", 24, Rarity.MYTHIC, mage.cards.s.SmileAtDeath.class));
        cards.add(new SetCardInfo("Snakeskin Veil", 159, Rarity.COMMON, mage.cards.s.SnakeskinVeil.class));
        cards.add(new SetCardInfo("Stormplain Detainment", 28, Rarity.COMMON, mage.cards.s.StormplainDetainment.class));
        cards.add(new SetCardInfo("Stormscale Scion", 123, Rarity.MYTHIC, mage.cards.s.StormscaleScion.class));
        cards.add(new SetCardInfo("Sultai Devotee", 160, Rarity.COMMON, mage.cards.s.SultaiDevotee.class));
        cards.add(new SetCardInfo("Summit Intimidator", 125, Rarity.COMMON, mage.cards.s.SummitIntimidator.class));
        cards.add(new SetCardInfo("Swamp", 281, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 268, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Tempest Hawk", 31, Rarity.COMMON, mage.cards.t.TempestHawk.class));
        cards.add(new SetCardInfo("Temur Devotee", 61, Rarity.COMMON, mage.cards.t.TemurDevotee.class));
        cards.add(new SetCardInfo("Thornwood Falls", 269, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Tranquil Cove", 270, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Undergrowth Leopard", 165, Rarity.COMMON, mage.cards.u.UndergrowthLeopard.class));
        cards.add(new SetCardInfo("Unrooted Ancestor", 96, Rarity.UNCOMMON, mage.cards.u.UnrootedAncestor.class));
        cards.add(new SetCardInfo("Venerated Stormsinger", 97, Rarity.UNCOMMON, mage.cards.v.VeneratedStormsinger.class));
        cards.add(new SetCardInfo("Voice of Victory", 33, Rarity.RARE, mage.cards.v.VoiceOfVictory.class));
        cards.add(new SetCardInfo("Watcher of the Wayside", 249, Rarity.COMMON, mage.cards.w.WatcherOfTheWayside.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 271, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
    }
}
