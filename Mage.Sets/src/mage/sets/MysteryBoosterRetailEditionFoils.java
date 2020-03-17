package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/fmb1
 */
public class MysteryBoosterRetailEditionFoils extends ExpansionSet {

    private static final MysteryBoosterRetailEditionFoils instance = new MysteryBoosterRetailEditionFoils();

    public static MysteryBoosterRetailEditionFoils getInstance() {
        return instance;
    }

    private MysteryBoosterRetailEditionFoils() {
        super("Mystery Booster Retail Edition Foils", "FMB1", ExpansionSet.buildDate(2020, 3, 8), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Alchemist's Refuge", 117, Rarity.RARE, mage.cards.a.AlchemistsRefuge.class));
        cards.add(new SetCardInfo("Allosaurus Rider", 61, Rarity.RARE, mage.cards.a.AllosaurusRider.class));
        cards.add(new SetCardInfo("Amulet of Vigor", 98, Rarity.RARE, mage.cards.a.AmuletOfVigor.class));
        cards.add(new SetCardInfo("Archetype of Endurance", 62, Rarity.UNCOMMON, mage.cards.a.ArchetypeOfEndurance.class));
        cards.add(new SetCardInfo("Aurelia's Fury", 83, Rarity.MYTHIC, mage.cards.a.AureliasFury.class));
        cards.add(new SetCardInfo("Balduvian Rage", 46, Rarity.UNCOMMON, mage.cards.b.BalduvianRage.class));
        cards.add(new SetCardInfo("Balefire Liege", 93, Rarity.RARE, mage.cards.b.BalefireLiege.class));
        cards.add(new SetCardInfo("Blasting Station", 99, Rarity.UNCOMMON, mage.cards.b.BlastingStation.class));
        cards.add(new SetCardInfo("Blighted Agent", 20, Rarity.COMMON, mage.cards.b.BlightedAgent.class));
        cards.add(new SetCardInfo("Boreal Druid", 63, Rarity.COMMON, mage.cards.b.BorealDruid.class));
        cards.add(new SetCardInfo("Boundless Realms", 64, Rarity.RARE, mage.cards.b.BoundlessRealms.class));
        cards.add(new SetCardInfo("Braid of Fire", 47, Rarity.RARE, mage.cards.b.BraidOfFire.class));
        cards.add(new SetCardInfo("Bramblewood Paragon", 65, Rarity.UNCOMMON, mage.cards.b.BramblewoodParagon.class));
        cards.add(new SetCardInfo("Bringer of the Black Dawn", 33, Rarity.RARE, mage.cards.b.BringerOfTheBlackDawn.class));
        cards.add(new SetCardInfo("Burning Inquiry", 48, Rarity.COMMON, mage.cards.b.BurningInquiry.class));
        cards.add(new SetCardInfo("Celestial Dawn", 2, Rarity.RARE, mage.cards.c.CelestialDawn.class));
        cards.add(new SetCardInfo("Celestial Kirin", 3, Rarity.RARE, mage.cards.c.CelestialKirin.class));
        cards.add(new SetCardInfo("Changeling Hero", 4, Rarity.UNCOMMON, mage.cards.c.ChangelingHero.class));
        cards.add(new SetCardInfo("Chimney Imp", 34, Rarity.COMMON, mage.cards.c.ChimneyImp.class));
        cards.add(new SetCardInfo("Codex Shredder", 100, Rarity.UNCOMMON, mage.cards.c.CodexShredder.class));
        cards.add(new SetCardInfo("Conspiracy", 35, Rarity.RARE, mage.cards.c.Conspiracy.class));
        cards.add(new SetCardInfo("Council Guardian", 5, Rarity.UNCOMMON, mage.cards.c.CouncilGuardian.class));
        cards.add(new SetCardInfo("Delay", 21, Rarity.UNCOMMON, mage.cards.d.Delay.class));
        cards.add(new SetCardInfo("Drogskol Captain", 84, Rarity.UNCOMMON, mage.cards.d.DrogskolCaptain.class));
        cards.add(new SetCardInfo("Echoing Decay", 36, Rarity.COMMON, mage.cards.e.EchoingDecay.class));
        cards.add(new SetCardInfo("Eidolon of Rhetoric", 6, Rarity.UNCOMMON, mage.cards.e.EidolonOfRhetoric.class));
        cards.add(new SetCardInfo("Fatespinner", 22, Rarity.RARE, mage.cards.f.Fatespinner.class));
        cards.add(new SetCardInfo("Fiery Gambit", 49, Rarity.RARE, mage.cards.f.FieryGambit.class));
        cards.add(new SetCardInfo("Flamekin Harbinger", 50, Rarity.UNCOMMON, mage.cards.f.FlamekinHarbinger.class));
        cards.add(new SetCardInfo("Form of the Dragon", 51, Rarity.RARE, mage.cards.f.FormOfTheDragon.class));
        cards.add(new SetCardInfo("Frozen Aether", 23, Rarity.UNCOMMON, mage.cards.f.FrozenAether.class));
        cards.add(new SetCardInfo("Funeral Charm", 37, Rarity.RARE, mage.cards.f.FuneralCharm.class));
        cards.add(new SetCardInfo("Fungusaur", 66, Rarity.RARE, mage.cards.f.Fungusaur.class));
        cards.add(new SetCardInfo("Game-Trail Changeling", 67, Rarity.COMMON, mage.cards.g.GameTrailChangeling.class));
        cards.add(new SetCardInfo("Geth's Grimoire", 101, Rarity.UNCOMMON, mage.cards.g.GethsGrimoire.class));
        cards.add(new SetCardInfo("Gilder Bairn", 94, Rarity.UNCOMMON, mage.cards.g.GilderBairn.class));
        cards.add(new SetCardInfo("Gleeful Sabotage", 68, Rarity.COMMON, mage.cards.g.GleefulSabotage.class));
        cards.add(new SetCardInfo("Glittering Wish", 85, Rarity.RARE, mage.cards.g.GlitteringWish.class));
        cards.add(new SetCardInfo("Goblin Bushwhacker", 52, Rarity.COMMON, mage.cards.g.GoblinBushwhacker.class));
        cards.add(new SetCardInfo("Grand Architect", 24, Rarity.RARE, mage.cards.g.GrandArchitect.class));
        cards.add(new SetCardInfo("Greater Mossdog", 69, Rarity.COMMON, mage.cards.g.GreaterMossdog.class));
        cards.add(new SetCardInfo("Guerrilla Tactics", 53, Rarity.UNCOMMON, mage.cards.g.GuerrillaTactics.class));
        cards.add(new SetCardInfo("Harmonic Sliver", 86, Rarity.UNCOMMON, mage.cards.h.HarmonicSliver.class));
        cards.add(new SetCardInfo("Helix Pinnacle", 70, Rarity.RARE, mage.cards.h.HelixPinnacle.class));
        cards.add(new SetCardInfo("Herald of Leshrac", 38, Rarity.RARE, mage.cards.h.HeraldOfLeshrac.class));
        cards.add(new SetCardInfo("Hornet Sting", 71, Rarity.COMMON, mage.cards.h.HornetSting.class));
        cards.add(new SetCardInfo("Intruder Alarm", 25, Rarity.RARE, mage.cards.i.IntruderAlarm.class));
        cards.add(new SetCardInfo("Iron Myr", 102, Rarity.COMMON, mage.cards.i.IronMyr.class));
        cards.add(new SetCardInfo("Isamaru, Hound of Konda", 7, Rarity.RARE, mage.cards.i.IsamaruHoundOfKonda.class));
        cards.add(new SetCardInfo("Karrthus, Tyrant of Jund", 87, Rarity.MYTHIC, mage.cards.k.KarrthusTyrantOfJund.class));
        cards.add(new SetCardInfo("Knowledge Pool", 103, Rarity.RARE, mage.cards.k.KnowledgePool.class));
        cards.add(new SetCardInfo("Kulrath Knight", 95, Rarity.UNCOMMON, mage.cards.k.KulrathKnight.class));
        cards.add(new SetCardInfo("Lantern of Insight", 104, Rarity.UNCOMMON, mage.cards.l.LanternOfInsight.class));
        cards.add(new SetCardInfo("Lapse of Certainty", 8, Rarity.COMMON, mage.cards.l.LapseOfCertainty.class));
        cards.add(new SetCardInfo("Leveler", 105, Rarity.RARE, mage.cards.l.Leveler.class));
        cards.add(new SetCardInfo("Lich's Mirror", 106, Rarity.MYTHIC, mage.cards.l.LichsMirror.class));
        cards.add(new SetCardInfo("Lightning Storm", 54, Rarity.UNCOMMON, mage.cards.l.LightningStorm.class));
        cards.add(new SetCardInfo("Lumithread Field", 9, Rarity.COMMON, mage.cards.l.LumithreadField.class));
        cards.add(new SetCardInfo("Maelstrom Nexus", 88, Rarity.MYTHIC, mage.cards.m.MaelstromNexus.class));
        cards.add(new SetCardInfo("Magewright's Stone", 107, Rarity.UNCOMMON, mage.cards.m.MagewrightsStone.class));
        cards.add(new SetCardInfo("Manaweft Sliver", 72, Rarity.UNCOMMON, mage.cards.m.ManaweftSliver.class));
        cards.add(new SetCardInfo("Maro", 73, Rarity.RARE, mage.cards.m.Maro.class));
        cards.add(new SetCardInfo("Marrow-Gnawer", 39, Rarity.RARE, mage.cards.m.MarrowGnawer.class));
        cards.add(new SetCardInfo("Memnite", 108, Rarity.UNCOMMON, mage.cards.m.Memnite.class));
        cards.add(new SetCardInfo("Minamo, School at Water's Edge", 118, Rarity.RARE, mage.cards.m.MinamoSchoolAtWatersEdge.class));
        cards.add(new SetCardInfo("Mind Funeral", 89, Rarity.UNCOMMON, mage.cards.m.MindFuneral.class));
        cards.add(new SetCardInfo("Mindslaver", 109, Rarity.RARE, mage.cards.m.Mindslaver.class));
        cards.add(new SetCardInfo("Mirrodin's Core", 119, Rarity.UNCOMMON, mage.cards.m.MirrodinsCore.class));
        cards.add(new SetCardInfo("Misthollow Griffin", 26, Rarity.MYTHIC, mage.cards.m.MisthollowGriffin.class));
        cards.add(new SetCardInfo("Myojin of Life's Web", 74, Rarity.RARE, mage.cards.m.MyojinOfLifesWeb.class));
        cards.add(new SetCardInfo("Nezumi Shortfang", 40, Rarity.RARE, mage.cards.n.NezumiShortfang.class));
        cards.add(new SetCardInfo("Noggle Bandit", 96, Rarity.COMMON, mage.cards.n.NoggleBandit.class));
        cards.add(new SetCardInfo("Norin the Wary", 55, Rarity.RARE, mage.cards.n.NorinTheWary.class));
        cards.add(new SetCardInfo("Norn's Annex", 10, Rarity.RARE, mage.cards.n.NornsAnnex.class));
        cards.add(new SetCardInfo("Not of This World", 1, Rarity.UNCOMMON, mage.cards.n.NotOfThisWorld.class));
        cards.add(new SetCardInfo("Ogre Gatecrasher", 56, Rarity.COMMON, mage.cards.o.OgreGatecrasher.class));
        cards.add(new SetCardInfo("One with Nothing", 41, Rarity.RARE, mage.cards.o.OneWithNothing.class));
        cards.add(new SetCardInfo("Panglacial Wurm", 75, Rarity.RARE, mage.cards.p.PanglacialWurm.class));
        cards.add(new SetCardInfo("Paradox Haze", 27, Rarity.UNCOMMON, mage.cards.p.ParadoxHaze.class));
        cards.add(new SetCardInfo("Patron of the Moon", 28, Rarity.RARE, mage.cards.p.PatronOfTheMoon.class));
        cards.add(new SetCardInfo("Pili-Pala", 110, Rarity.COMMON, mage.cards.p.PiliPala.class));
        cards.add(new SetCardInfo("Proclamation of Rebirth", 11, Rarity.RARE, mage.cards.p.ProclamationOfRebirth.class));
        cards.add(new SetCardInfo("Puca's Mischief", 29, Rarity.RARE, mage.cards.p.PucasMischief.class));
        cards.add(new SetCardInfo("Pull from Eternity", 12, Rarity.UNCOMMON, mage.cards.p.PullFromEternity.class));
        cards.add(new SetCardInfo("Pyretic Ritual", 57, Rarity.COMMON, mage.cards.p.PyreticRitual.class));
        cards.add(new SetCardInfo("Ravenous Trap", 42, Rarity.UNCOMMON, mage.cards.r.RavenousTrap.class));
        cards.add(new SetCardInfo("Reaper King", 111, Rarity.RARE, mage.cards.r.ReaperKing.class));
        cards.add(new SetCardInfo("Reki, the History of Kamigawa", 76, Rarity.RARE, mage.cards.r.RekiTheHistoryOfKamigawa.class));
        cards.add(new SetCardInfo("Rescue from the Underworld", 43, Rarity.UNCOMMON, mage.cards.r.RescueFromTheUnderworld.class));
        cards.add(new SetCardInfo("Rhox", 77, Rarity.RARE, mage.cards.r.Rhox.class));
        cards.add(new SetCardInfo("Rune-Tail, Kitsune Ascendant", 13, Rarity.RARE, mage.cards.r.RuneTailKitsuneAscendant.class));
        cards.add(new SetCardInfo("Sakura-Tribe Scout", 78, Rarity.COMMON, mage.cards.s.SakuraTribeScout.class));
        cards.add(new SetCardInfo("Sarkhan the Mad", 90, Rarity.MYTHIC, mage.cards.s.SarkhanTheMad.class));
        cards.add(new SetCardInfo("Scourge of the Throne", 58, Rarity.MYTHIC, mage.cards.s.ScourgeOfTheThrone.class));
        cards.add(new SetCardInfo("Scryb Ranger", 79, Rarity.UNCOMMON, mage.cards.s.ScrybRanger.class));
        cards.add(new SetCardInfo("Sen Triplets", 91, Rarity.MYTHIC, mage.cards.s.SenTriplets.class));
        cards.add(new SetCardInfo("Sheltering Ancient", 80, Rarity.UNCOMMON, mage.cards.s.ShelteringAncient.class));
        cards.add(new SetCardInfo("Shizo, Death's Storehouse", 120, Rarity.RARE, mage.cards.s.ShizoDeathsStorehouse.class));
        cards.add(new SetCardInfo("Sinew Sliver", 14, Rarity.COMMON, mage.cards.s.SinewSliver.class));
        cards.add(new SetCardInfo("Sosuke, Son of Seshiro", 81, Rarity.UNCOMMON, mage.cards.s.SosukeSonOfSeshiro.class));
        cards.add(new SetCardInfo("Soul's Attendant", 15, Rarity.COMMON, mage.cards.s.SoulsAttendant.class));
        cards.add(new SetCardInfo("Spelltithe Enforcer", 16, Rarity.RARE, mage.cards.s.SpelltitheEnforcer.class));
        cards.add(new SetCardInfo("Spellweaver Volute", 30, Rarity.RARE, mage.cards.s.SpellweaverVolute.class));
        cards.add(new SetCardInfo("Spike Feeder", 82, Rarity.RARE, mage.cards.s.SpikeFeeder.class));
        cards.add(new SetCardInfo("Springjack Shepherd", 17, Rarity.UNCOMMON, mage.cards.s.SpringjackShepherd.class));
        cards.add(new SetCardInfo("Stalking Stones", 121, Rarity.UNCOMMON, mage.cards.s.StalkingStones.class));
        cards.add(new SetCardInfo("Stigma Lasher", 59, Rarity.RARE, mage.cards.s.StigmaLasher.class));
        cards.add(new SetCardInfo("Storm Crow", 31, Rarity.COMMON, mage.cards.s.StormCrow.class));
        cards.add(new SetCardInfo("Sundial of the Infinite", 112, Rarity.RARE, mage.cards.s.SundialOfTheInfinite.class));
        cards.add(new SetCardInfo("Teferi's Puzzle Box", 113, Rarity.RARE, mage.cards.t.TeferisPuzzleBox.class));
        cards.add(new SetCardInfo("Trailblazer's Boots", 114, Rarity.UNCOMMON, mage.cards.t.TrailblazersBoots.class));
        cards.add(new SetCardInfo("Treasonous Ogre", 60, Rarity.UNCOMMON, mage.cards.t.TreasonousOgre.class));
        cards.add(new SetCardInfo("Triskelion", 115, Rarity.RARE, mage.cards.t.Triskelion.class));
        cards.add(new SetCardInfo("Undead Warchief", 44, Rarity.RARE, mage.cards.u.UndeadWarchief.class));
        cards.add(new SetCardInfo("Viscera Seer", 45, Rarity.COMMON, mage.cards.v.VisceraSeer.class));
        cards.add(new SetCardInfo("Wall of Shards", 18, Rarity.UNCOMMON, mage.cards.w.WallOfShards.class));
        cards.add(new SetCardInfo("Wear // Tear", 97, Rarity.UNCOMMON, mage.cards.w.WearTear.class));
        cards.add(new SetCardInfo("White Knight", 19, Rarity.UNCOMMON, mage.cards.w.WhiteKnight.class));
        cards.add(new SetCardInfo("Witchbane Orb", 116, Rarity.RARE, mage.cards.w.WitchbaneOrb.class));
        cards.add(new SetCardInfo("Yore-Tiller Nephilim", 92, Rarity.RARE, mage.cards.y.YoreTillerNephilim.class));
        cards.add(new SetCardInfo("Zur's Weirding", 32, Rarity.RARE, mage.cards.z.ZursWeirding.class));
    }
}
