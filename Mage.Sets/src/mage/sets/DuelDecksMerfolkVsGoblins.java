
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class DuelDecksMerfolkVsGoblins extends ExpansionSet {

    private static final DuelDecksMerfolkVsGoblins instance = new DuelDecksMerfolkVsGoblins();

    public static DuelDecksMerfolkVsGoblins getInstance() {
        return instance;
    }

    private DuelDecksMerfolkVsGoblins() {
        super("Duel Decks: Merfolk vs. Goblins", "DDT", ExpansionSet.buildDate(2017, 11, 10), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Aquitect's Will", 2, Rarity.COMMON, mage.cards.a.AquitectsWill.class));
        cards.add(new SetCardInfo("Battle Squadron", 33, Rarity.UNCOMMON, mage.cards.b.BattleSquadron.class));
        cards.add(new SetCardInfo("Blighted Cataract", 26, Rarity.UNCOMMON, mage.cards.b.BlightedCataract.class));
        cards.add(new SetCardInfo("Blighted Gorge", 58, Rarity.UNCOMMON, mage.cards.b.BlightedGorge.class));
        cards.add(new SetCardInfo("Boggart Brute", 34, Rarity.COMMON, mage.cards.b.BoggartBrute.class));
        cards.add(new SetCardInfo("Brittle Effigy", 56, Rarity.RARE, mage.cards.b.BrittleEffigy.class));
        cards.add(new SetCardInfo("Brute Strength", 35, Rarity.COMMON, mage.cards.b.BruteStrength.class));
        cards.add(new SetCardInfo("Claustrophobia", 3, Rarity.COMMON, mage.cards.c.Claustrophobia.class));
        cards.add(new SetCardInfo("Cleaver Riot", 36, Rarity.UNCOMMON, mage.cards.c.CleaverRiot.class));
        cards.add(new SetCardInfo("Cold-Eyed Selkie", 25, Rarity.RARE, mage.cards.c.ColdEyedSelkie.class));
        cards.add(new SetCardInfo("Concentrate", 4, Rarity.UNCOMMON, mage.cards.c.Concentrate.class));
        cards.add(new SetCardInfo("Ember Hauler", 37, Rarity.UNCOMMON, mage.cards.e.EmberHauler.class));
        cards.add(new SetCardInfo("Engulf the Shore", 5, Rarity.RARE, mage.cards.e.EngulfTheShore.class));
        cards.add(new SetCardInfo("Essence Scatter", 6, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Forgotten Cave", 59, Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Foundry Street Denizen", 38, Rarity.COMMON, mage.cards.f.FoundryStreetDenizen.class));
        cards.add(new SetCardInfo("Gempalm Incinerator", 39, Rarity.UNCOMMON, mage.cards.g.GempalmIncinerator.class));
        cards.add(new SetCardInfo("Ghostfire", 40, Rarity.COMMON, mage.cards.g.Ghostfire.class));
        cards.add(new SetCardInfo("Goblin Charbelcher", 57, Rarity.RARE, mage.cards.g.GoblinCharbelcher.class));
        cards.add(new SetCardInfo("Goblin Chieftain", 41, Rarity.RARE, mage.cards.g.GoblinChieftain.class));
        cards.add(new SetCardInfo("Goblin Diplomats", 42, Rarity.RARE, mage.cards.g.GoblinDiplomats.class));
        cards.add(new SetCardInfo("Goblin Glory Chaser", 43, Rarity.UNCOMMON, mage.cards.g.GoblinGloryChaser.class));
        cards.add(new SetCardInfo("Goblin Goon", 44, Rarity.RARE, mage.cards.g.GoblinGoon.class));
        cards.add(new SetCardInfo("Goblin Grenade", 45, Rarity.UNCOMMON, mage.cards.g.GoblinGrenade.class));
        cards.add(new SetCardInfo("Goblin Rabblemaster", 46, Rarity.RARE, mage.cards.g.GoblinRabblemaster.class));
        cards.add(new SetCardInfo("Goblin Razerunners", 47, Rarity.RARE, mage.cards.g.GoblinRazerunners.class));
        cards.add(new SetCardInfo("Goblin Ringleader", 48, Rarity.UNCOMMON, mage.cards.g.GoblinRingleader.class));
        cards.add(new SetCardInfo("Goblin Tunneler", 49, Rarity.COMMON, mage.cards.g.GoblinTunneler.class));
        cards.add(new SetCardInfo("Goblin Wardriver", 50, Rarity.UNCOMMON, mage.cards.g.GoblinWardriver.class));
        cards.add(new SetCardInfo("Harbinger of the Tides", 7, Rarity.RARE, mage.cards.h.HarbingerOfTheTides.class));
        cards.add(new SetCardInfo("Hordeling Outburst", 51, Rarity.UNCOMMON, mage.cards.h.HordelingOutburst.class));
        cards.add(new SetCardInfo("Inkfathom Divers", 8, Rarity.COMMON, mage.cards.i.InkfathomDivers.class));
        cards.add(new SetCardInfo("Island", 28, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 29, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 30, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 31, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krenko's Command", 53, Rarity.COMMON, mage.cards.k.KrenkosCommand.class));
        cards.add(new SetCardInfo("Krenko, Mob Boss", 52, Rarity.RARE, mage.cards.k.KrenkoMobBoss.class));
        cards.add(new SetCardInfo("Lonely Sandbar", 27, Rarity.COMMON, mage.cards.l.LonelySandbar.class));
        cards.add(new SetCardInfo("Master of Waves", 1, Rarity.MYTHIC, mage.cards.m.MasterOfWaves.class));
        cards.add(new SetCardInfo("Master of the Pearl Trident", 9, Rarity.RARE, mage.cards.m.MasterOfThePearlTrident.class));
        cards.add(new SetCardInfo("Merfolk Looter", 10, Rarity.UNCOMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Merfolk Sovereign", 11, Rarity.RARE, mage.cards.m.MerfolkSovereign.class));
        cards.add(new SetCardInfo("Merfolk Wayfinder", 12, Rarity.UNCOMMON, mage.cards.m.MerfolkWayfinder.class));
        cards.add(new SetCardInfo("Merrow Reejerey", 13, Rarity.UNCOMMON, mage.cards.m.MerrowReejerey.class));
        cards.add(new SetCardInfo("Mind Spring", 14, Rarity.RARE, mage.cards.m.MindSpring.class));
        cards.add(new SetCardInfo("Misdirection", 15, Rarity.RARE, mage.cards.m.Misdirection.class));
        cards.add(new SetCardInfo("Mountain", 60, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 61, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 62, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 63, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relentless Assault", 54, Rarity.RARE, mage.cards.r.RelentlessAssault.class));
        cards.add(new SetCardInfo("Rootwater Hunter", 16, Rarity.UNCOMMON, mage.cards.r.RootwaterHunter.class));
        cards.add(new SetCardInfo("Scroll Thief", 17, Rarity.COMMON, mage.cards.s.ScrollThief.class));
        cards.add(new SetCardInfo("Streambed Aquitects", 18, Rarity.COMMON, mage.cards.s.StreambedAquitects.class));
        cards.add(new SetCardInfo("Tarfire", 55, Rarity.COMMON, mage.cards.t.Tarfire.class));
        cards.add(new SetCardInfo("Tidal Courier", 19, Rarity.UNCOMMON, mage.cards.t.TidalCourier.class));
        cards.add(new SetCardInfo("Tidal Warrior", 20, Rarity.COMMON, mage.cards.t.TidalWarrior.class));
        cards.add(new SetCardInfo("Tidal Wave", 21, Rarity.COMMON, mage.cards.t.TidalWave.class));
        cards.add(new SetCardInfo("Tidebinder Mage", 22, Rarity.RARE, mage.cards.t.TidebinderMage.class));
        cards.add(new SetCardInfo("Triton Tactics", 23, Rarity.UNCOMMON, mage.cards.t.TritonTactics.class));
        cards.add(new SetCardInfo("Wake Thrasher", 24, Rarity.RARE, mage.cards.w.WakeThrasher.class));
        cards.add(new SetCardInfo("Warren Instigator", 32, Rarity.MYTHIC, mage.cards.w.WarrenInstigator.class));
    }
}
