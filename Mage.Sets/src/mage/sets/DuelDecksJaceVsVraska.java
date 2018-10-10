
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author LevelX2
 */
public final class DuelDecksJaceVsVraska extends ExpansionSet {

    private static final DuelDecksJaceVsVraska instance = new DuelDecksJaceVsVraska();

    public static DuelDecksJaceVsVraska getInstance() {
        return instance;
    }

    private DuelDecksJaceVsVraska() {
        super("Duel Decks: Jace vs. Vraska", "DDM", ExpansionSet.buildDate(2014, 3, 14), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Acidic Slime", 64, Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Aeon Chronicler", 17, Rarity.RARE, mage.cards.a.AeonChronicler.class));
        cards.add(new SetCardInfo("Aether Adept", 12, Rarity.COMMON, mage.cards.a.AetherAdept.class));
        cards.add(new SetCardInfo("Aether Figment", 5, Rarity.UNCOMMON, mage.cards.a.AetherFigment.class));
        cards.add(new SetCardInfo("Agoraphobia", 22, Rarity.UNCOMMON, mage.cards.a.Agoraphobia.class));
        cards.add(new SetCardInfo("Archaeomancer", 13, Rarity.COMMON, mage.cards.a.Archaeomancer.class));
        cards.add(new SetCardInfo("Body Double", 15, Rarity.RARE, mage.cards.b.BodyDouble.class));
        cards.add(new SetCardInfo("Chronomaton", 2, Rarity.UNCOMMON, mage.cards.c.Chronomaton.class));
        cards.add(new SetCardInfo("Claustrophobia", 27, Rarity.COMMON, mage.cards.c.Claustrophobia.class));
        cards.add(new SetCardInfo("Consume Strength", 74, Rarity.COMMON, mage.cards.c.ConsumeStrength.class));
        cards.add(new SetCardInfo("Control Magic", 30, Rarity.UNCOMMON, mage.cards.c.ControlMagic.class));
        cards.add(new SetCardInfo("Corpse Traders", 58, Rarity.UNCOMMON, mage.cards.c.CorpseTraders.class));
        cards.add(new SetCardInfo("Crosstown Courier", 6, Rarity.COMMON, mage.cards.c.CrosstownCourier.class));
        cards.add(new SetCardInfo("Death-Hood Cobra", 47, Rarity.COMMON, mage.cards.d.DeathHoodCobra.class));
        cards.add(new SetCardInfo("Dread Statuary", 35, Rarity.UNCOMMON, mage.cards.d.DreadStatuary.class));
        cards.add(new SetCardInfo("Dream Stalker", 7, Rarity.COMMON, mage.cards.d.DreamStalker.class));
        cards.add(new SetCardInfo("Drooling Groodion", 65, Rarity.UNCOMMON, mage.cards.d.DroolingGroodion.class));
        cards.add(new SetCardInfo("Errant Ephemeron", 20, Rarity.COMMON, mage.cards.e.ErrantEphemeron.class));
        cards.add(new SetCardInfo("Festerhide Boar", 59, Rarity.COMMON, mage.cards.f.FesterhideBoar.class));
        cards.add(new SetCardInfo("Forest", 84, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 85, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 86, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 87, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 88, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Future Sight", 33, Rarity.RARE, mage.cards.f.FutureSight.class));
        cards.add(new SetCardInfo("Gatecreeper Vine", 48, Rarity.COMMON, mage.cards.g.GatecreeperVine.class));
        cards.add(new SetCardInfo("Golgari Guildgate", 76, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class));
        cards.add(new SetCardInfo("Griptide", 28, Rarity.COMMON, mage.cards.g.Griptide.class));
        cards.add(new SetCardInfo("Grisly Spectacle", 75, Rarity.COMMON, mage.cards.g.GrislySpectacle.class));
        cards.add(new SetCardInfo("Halimar Depths", 36, Rarity.COMMON, mage.cards.h.HalimarDepths.class));
        cards.add(new SetCardInfo("Highway Robber", 61, Rarity.COMMON, mage.cards.h.HighwayRobber.class));
        cards.add(new SetCardInfo("Hypnotic Cloud", 67, Rarity.COMMON, mage.cards.h.HypnoticCloud.class));
        cards.add(new SetCardInfo("Into the Roil", 23, Rarity.COMMON, mage.cards.i.IntoTheRoil.class));
        cards.add(new SetCardInfo("Island", 37, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 38, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 39, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 40, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 41, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace, Architect of Thought", 1, Rarity.MYTHIC, mage.cards.j.JaceArchitectOfThought.class));
        cards.add(new SetCardInfo("Jace's Ingenuity", 32, Rarity.UNCOMMON, mage.cards.j.JacesIngenuity.class));
        cards.add(new SetCardInfo("Jace's Mindseeker", 19, Rarity.RARE, mage.cards.j.JacesMindseeker.class));
        cards.add(new SetCardInfo("Jace's Phantasm", 3, Rarity.UNCOMMON, mage.cards.j.JacesPhantasm.class));
        cards.add(new SetCardInfo("Krovikan Mist", 8, Rarity.COMMON, mage.cards.k.KrovikanMist.class));
        cards.add(new SetCardInfo("Last Kiss", 71, Rarity.COMMON, mage.cards.l.LastKiss.class));
        cards.add(new SetCardInfo("Leyline Phantom", 16, Rarity.COMMON, mage.cards.l.LeylinePhantom.class));
        cards.add(new SetCardInfo("Marsh Casualties", 69, Rarity.UNCOMMON, mage.cards.m.MarshCasualties.class));
        cards.add(new SetCardInfo("Memory Lapse", 24, Rarity.COMMON, mage.cards.m.MemoryLapse.class));
        cards.add(new SetCardInfo("Merfolk Wayfinder", 9, Rarity.UNCOMMON, mage.cards.m.MerfolkWayfinder.class));
        cards.add(new SetCardInfo("Mold Shambler", 60, Rarity.COMMON, mage.cards.m.MoldShambler.class));
        cards.add(new SetCardInfo("Nekrataal", 62, Rarity.UNCOMMON, mage.cards.n.Nekrataal.class));
        cards.add(new SetCardInfo("Night's Whisper", 68, Rarity.UNCOMMON, mage.cards.n.NightsWhisper.class));
        cards.add(new SetCardInfo("Ohran Viper", 57, Rarity.RARE, mage.cards.o.OhranViper.class));
        cards.add(new SetCardInfo("Oran-Rief Recluse", 54, Rarity.COMMON, mage.cards.o.OranRiefRecluse.class));
        cards.add(new SetCardInfo("Phantasmal Bear", 4, Rarity.COMMON, mage.cards.p.PhantasmalBear.class));
        cards.add(new SetCardInfo("Phantasmal Dragon", 14, Rarity.UNCOMMON, mage.cards.p.PhantasmalDragon.class));
        cards.add(new SetCardInfo("Prohibit", 25, Rarity.COMMON, mage.cards.p.Prohibit.class));
        cards.add(new SetCardInfo("Pulse Tracker", 43, Rarity.COMMON, mage.cards.p.PulseTracker.class));
        cards.add(new SetCardInfo("Putrid Leech", 51, Rarity.COMMON, mage.cards.p.PutridLeech.class));
        cards.add(new SetCardInfo("Ray of Command", 29, Rarity.COMMON, mage.cards.r.RayOfCommand.class));
        cards.add(new SetCardInfo("Reaper of the Wilds", 63, Rarity.RARE, mage.cards.r.ReaperOfTheWilds.class));
        cards.add(new SetCardInfo("Remand", 26, Rarity.UNCOMMON, mage.cards.r.Remand.class));
        cards.add(new SetCardInfo("Riftwing Cloudskate", 18, Rarity.UNCOMMON, mage.cards.r.RiftwingCloudskate.class));
        cards.add(new SetCardInfo("River Boa", 49, Rarity.UNCOMMON, mage.cards.r.RiverBoa.class));
        cards.add(new SetCardInfo("Rogue's Passage", 77, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Sadistic Augermage", 52, Rarity.COMMON, mage.cards.s.SadisticAugermage.class));
        cards.add(new SetCardInfo("Sea Gate Oracle", 10, Rarity.COMMON, mage.cards.s.SeaGateOracle.class));
        cards.add(new SetCardInfo("Shadow Alley Denizen", 44, Rarity.COMMON, mage.cards.s.ShadowAlleyDenizen.class));
        cards.add(new SetCardInfo("Slate Street Ruffian", 53, Rarity.COMMON, mage.cards.s.SlateStreetRuffian.class));
        cards.add(new SetCardInfo("Spawnwrithe", 55, Rarity.RARE, mage.cards.s.Spawnwrithe.class));
        cards.add(new SetCardInfo("Spelltwine", 34, Rarity.RARE, mage.cards.s.Spelltwine.class));
        cards.add(new SetCardInfo("Stab Wound", 72, Rarity.COMMON, mage.cards.s.StabWound.class));
        cards.add(new SetCardInfo("Stealer of Secrets", 11, Rarity.COMMON, mage.cards.s.StealerOfSecrets.class));
        cards.add(new SetCardInfo("Stonefare Crocodile", 56, Rarity.COMMON, mage.cards.s.StonefareCrocodile.class));
        cards.add(new SetCardInfo("Summoner's Bane", 31, Rarity.UNCOMMON, mage.cards.s.SummonersBane.class));
        cards.add(new SetCardInfo("Swamp", 79, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 80, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 81, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 82, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 83, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tainted Wood", 78, Rarity.UNCOMMON, mage.cards.t.TaintedWood.class));
        cards.add(new SetCardInfo("Tavern Swindler", 45, Rarity.UNCOMMON, mage.cards.t.TavernSwindler.class));
        cards.add(new SetCardInfo("Thought Scour", 21, Rarity.COMMON, mage.cards.t.ThoughtScour.class));
        cards.add(new SetCardInfo("Tragic Slip", 66, Rarity.COMMON, mage.cards.t.TragicSlip.class));
        cards.add(new SetCardInfo("Treasured Find", 70, Rarity.UNCOMMON, mage.cards.t.TreasuredFind.class));
        cards.add(new SetCardInfo("Underworld Connections", 73, Rarity.RARE, mage.cards.u.UnderworldConnections.class));
        cards.add(new SetCardInfo("Vinelasher Kudzu", 50, Rarity.RARE, mage.cards.v.VinelasherKudzu.class));
        cards.add(new SetCardInfo("Vraska the Unseen", 42, Rarity.MYTHIC, mage.cards.v.VraskaTheUnseen.class));
        cards.add(new SetCardInfo("Wight of Precinct Six", 46, Rarity.UNCOMMON, mage.cards.w.WightOfPrecinctSix.class));
    }
}
