
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public final class Legions extends ExpansionSet {

    private static final Legions instance = new Legions();

    public static Legions getInstance() {
        return instance;
    }

    private Legions() {
        super("Legions", "LGN", ExpansionSet.buildDate(2003, 1, 25), SetType.EXPANSION);
        this.blockName = "Onslaught";
        this.parentSet = Onslaught.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Akroma, Angel of Wrath", 1, Rarity.RARE, mage.cards.a.AkromaAngelOfWrath.class, RETRO_ART));
        cards.add(new SetCardInfo("Akroma's Devoted", 2, Rarity.UNCOMMON, mage.cards.a.AkromasDevoted.class, RETRO_ART));
        cards.add(new SetCardInfo("Aphetto Exterminator", 59, Rarity.UNCOMMON, mage.cards.a.AphettoExterminator.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Envoy", 30, Rarity.COMMON, mage.cards.a.AvenEnvoy.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Redeemer", 3, Rarity.COMMON, mage.cards.a.AvenRedeemer.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Warhawk", 4, Rarity.UNCOMMON, mage.cards.a.AvenWarhawk.class, RETRO_ART));
        cards.add(new SetCardInfo("Bane of the Living", 60, Rarity.RARE, mage.cards.b.BaneOfTheLiving.class, RETRO_ART));
        cards.add(new SetCardInfo("Beacon of Destiny", 5, Rarity.RARE, mage.cards.b.BeaconOfDestiny.class, RETRO_ART));
        cards.add(new SetCardInfo("Berserk Murlodont", 117, Rarity.COMMON, mage.cards.b.BerserkMurlodont.class, RETRO_ART));
        cards.add(new SetCardInfo("Blade Sliver", 88, Rarity.UNCOMMON, mage.cards.b.BladeSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Blood Celebrant", 61, Rarity.COMMON, mage.cards.b.BloodCelebrant.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloodstoke Howler", 89, Rarity.COMMON, mage.cards.b.BloodstokeHowler.class, RETRO_ART));
        cards.add(new SetCardInfo("Branchsnap Lorian", 118, Rarity.UNCOMMON, mage.cards.b.BranchsnapLorian.class, RETRO_ART));
        cards.add(new SetCardInfo("Brontotherium", 119, Rarity.UNCOMMON, mage.cards.b.Brontotherium.class, RETRO_ART));
        cards.add(new SetCardInfo("Brood Sliver", 120, Rarity.RARE, mage.cards.b.BroodSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Caller of the Claw", 121, Rarity.RARE, mage.cards.c.CallerOfTheClaw.class, RETRO_ART));
        cards.add(new SetCardInfo("Canopy Crawler", 122, Rarity.UNCOMMON, mage.cards.c.CanopyCrawler.class, RETRO_ART));
        cards.add(new SetCardInfo("Celestial Gatekeeper", 6, Rarity.RARE, mage.cards.c.CelestialGatekeeper.class, RETRO_ART));
        cards.add(new SetCardInfo("Cephalid Pathmage", 31, Rarity.COMMON, mage.cards.c.CephalidPathmage.class, RETRO_ART));
        cards.add(new SetCardInfo("Chromeshell Crab", 32, Rarity.RARE, mage.cards.c.ChromeshellCrab.class, RETRO_ART));
        cards.add(new SetCardInfo("Clickslither", 90, Rarity.RARE, mage.cards.c.Clickslither.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloudreach Cavalry", 7, Rarity.UNCOMMON, mage.cards.c.CloudreachCavalry.class, RETRO_ART));
        cards.add(new SetCardInfo("Corpse Harvester", 62, Rarity.UNCOMMON, mage.cards.c.CorpseHarvester.class, RETRO_ART));
        cards.add(new SetCardInfo("Covert Operative", 33, Rarity.COMMON, mage.cards.c.CovertOperative.class, RETRO_ART));
        cards.add(new SetCardInfo("Crested Craghorn", 91, Rarity.COMMON, mage.cards.c.CrestedCraghorn.class, RETRO_ART));
        cards.add(new SetCardInfo("Crookclaw Elder", 34, Rarity.UNCOMMON, mage.cards.c.CrookclawElder.class, RETRO_ART));
        cards.add(new SetCardInfo("Crypt Sliver", 63, Rarity.COMMON, mage.cards.c.CryptSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Dark Supplicant", 64, Rarity.UNCOMMON, mage.cards.d.DarkSupplicant.class, RETRO_ART));
        cards.add(new SetCardInfo("Daru Mender", 8, Rarity.UNCOMMON, mage.cards.d.DaruMender.class, RETRO_ART));
        cards.add(new SetCardInfo("Daru Sanctifier", 9, Rarity.COMMON, mage.cards.d.DaruSanctifier.class, RETRO_ART));
        cards.add(new SetCardInfo("Daru Stinger", 10, Rarity.COMMON, mage.cards.d.DaruStinger.class, RETRO_ART));
        cards.add(new SetCardInfo("Deathmark Prelate", 65, Rarity.UNCOMMON, mage.cards.d.DeathmarkPrelate.class, RETRO_ART));
        cards.add(new SetCardInfo("Defender of the Order", 11, Rarity.RARE, mage.cards.d.DefenderOfTheOrder.class, RETRO_ART));
        cards.add(new SetCardInfo("Defiant Elf", 123, Rarity.COMMON, mage.cards.d.DefiantElf.class, RETRO_ART));
        cards.add(new SetCardInfo("Deftblade Elite", 12, Rarity.COMMON, mage.cards.d.DeftbladeElite.class, RETRO_ART));
        cards.add(new SetCardInfo("Dermoplasm", 35, Rarity.RARE, mage.cards.d.Dermoplasm.class, RETRO_ART));
        cards.add(new SetCardInfo("Dreamborn Muse", 36, Rarity.RARE, mage.cards.d.DreambornMuse.class, RETRO_ART));
        cards.add(new SetCardInfo("Drinker of Sorrow", 66, Rarity.RARE, mage.cards.d.DrinkerOfSorrow.class, RETRO_ART));
        cards.add(new SetCardInfo("Dripping Dead", 67, Rarity.COMMON, mage.cards.d.DrippingDead.class, RETRO_ART));
        cards.add(new SetCardInfo("Earthblighter", 68, Rarity.UNCOMMON, mage.cards.e.Earthblighter.class, RETRO_ART));
        cards.add(new SetCardInfo("Echo Tracer", 37, Rarity.COMMON, mage.cards.e.EchoTracer.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Soultiller", 124, Rarity.RARE, mage.cards.e.ElvishSoultiller.class, RETRO_ART));
        cards.add(new SetCardInfo("Embalmed Brawler", 69, Rarity.COMMON, mage.cards.e.EmbalmedBrawler.class, RETRO_ART));
        cards.add(new SetCardInfo("Enormous Baloth", 125, Rarity.UNCOMMON, mage.cards.e.EnormousBaloth.class, RETRO_ART));
        cards.add(new SetCardInfo("Essence Sliver", 13, Rarity.RARE, mage.cards.e.EssenceSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Feral Throwback", 126, Rarity.RARE, mage.cards.f.FeralThrowback.class, RETRO_ART));
        cards.add(new SetCardInfo("Flamewave Invoker", 92, Rarity.COMMON, mage.cards.f.FlamewaveInvoker.class, RETRO_ART));
        cards.add(new SetCardInfo("Frenetic Raptor", 93, Rarity.UNCOMMON, mage.cards.f.FreneticRaptor.class, RETRO_ART));
        cards.add(new SetCardInfo("Fugitive Wizard", 38, Rarity.COMMON, mage.cards.f.FugitiveWizard.class, RETRO_ART));
        cards.add(new SetCardInfo("Gempalm Avenger", 14, Rarity.COMMON, mage.cards.g.GempalmAvenger.class, RETRO_ART));
        cards.add(new SetCardInfo("Gempalm Incinerator", 94, Rarity.UNCOMMON, mage.cards.g.GempalmIncinerator.class, RETRO_ART));
        cards.add(new SetCardInfo("Gempalm Polluter", 70, Rarity.COMMON, mage.cards.g.GempalmPolluter.class, RETRO_ART));
        cards.add(new SetCardInfo("Gempalm Sorcerer", 39, Rarity.UNCOMMON, mage.cards.g.GempalmSorcerer.class, RETRO_ART));
        cards.add(new SetCardInfo("Gempalm Strider", 127, Rarity.UNCOMMON, mage.cards.g.GempalmStrider.class, RETRO_ART));
        cards.add(new SetCardInfo("Ghastly Remains", 71, Rarity.RARE, mage.cards.g.GhastlyRemains.class, RETRO_ART));
        cards.add(new SetCardInfo("Glintwing Invoker", 40, Rarity.COMMON, mage.cards.g.GlintwingInvoker.class, RETRO_ART));
        cards.add(new SetCardInfo("Glowering Rogon", 128, Rarity.COMMON, mage.cards.g.GloweringRogon.class, RETRO_ART));
        cards.add(new SetCardInfo("Glowrider", 15, Rarity.RARE, mage.cards.g.Glowrider.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Assassin", 95, Rarity.UNCOMMON, mage.cards.g.GoblinAssassin.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Clearcutter", 96, Rarity.UNCOMMON, mage.cards.g.GoblinClearcutter.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Dynamo", 97, Rarity.UNCOMMON, mage.cards.g.GoblinDynamo.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Firebug", 98, Rarity.COMMON, mage.cards.g.GoblinFirebug.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Goon", 99, Rarity.RARE, mage.cards.g.GoblinGoon.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Grappler", 100, Rarity.COMMON, mage.cards.g.GoblinGrappler.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Lookout", 101, Rarity.COMMON, mage.cards.g.GoblinLookout.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Turncoat", 72, Rarity.COMMON, mage.cards.g.GoblinTurncoat.class, RETRO_ART));
        cards.add(new SetCardInfo("Graveborn Muse", 73, Rarity.RARE, mage.cards.g.GravebornMuse.class, RETRO_ART));
        cards.add(new SetCardInfo("Havoc Demon", 74, Rarity.RARE, mage.cards.h.HavocDemon.class, RETRO_ART));
        cards.add(new SetCardInfo("Hollow Specter", 75, Rarity.RARE, mage.cards.h.HollowSpecter.class, RETRO_ART));
        cards.add(new SetCardInfo("Hundroog", 129, Rarity.COMMON, mage.cards.h.Hundroog.class, RETRO_ART));
        cards.add(new SetCardInfo("Hunter Sliver", 102, Rarity.COMMON, mage.cards.h.HunterSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Imperial Hellkite", 103, Rarity.RARE, mage.cards.i.ImperialHellkite.class, RETRO_ART));
        cards.add(new SetCardInfo("Infernal Caretaker", 76, Rarity.COMMON, mage.cards.i.InfernalCaretaker.class, RETRO_ART));
        cards.add(new SetCardInfo("Keeneye Aven", 41, Rarity.COMMON, mage.cards.k.KeeneyeAven.class, RETRO_ART));
        cards.add(new SetCardInfo("Keeper of the Nine Gales", 42, Rarity.RARE, mage.cards.k.KeeperOfTheNineGales.class, RETRO_ART));
        cards.add(new SetCardInfo("Kilnmouth Dragon", 104, Rarity.RARE, mage.cards.k.KilnmouthDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Cloudscraper", 130, Rarity.RARE, mage.cards.k.KrosanCloudscraper.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Vorine", 131, Rarity.COMMON, mage.cards.k.KrosanVorine.class, RETRO_ART));
        cards.add(new SetCardInfo("Lavaborn Muse", 105, Rarity.RARE, mage.cards.l.LavabornMuse.class, RETRO_ART));
        cards.add(new SetCardInfo("Liege of the Axe", 16, Rarity.UNCOMMON, mage.cards.l.LiegeOfTheAxe.class, RETRO_ART));
        cards.add(new SetCardInfo("Lowland Tracker", 17, Rarity.COMMON, mage.cards.l.LowlandTracker.class, RETRO_ART));
        cards.add(new SetCardInfo("Macetail Hystrodon", 106, Rarity.COMMON, mage.cards.m.MacetailHystrodon.class, RETRO_ART));
        cards.add(new SetCardInfo("Magma Sliver", 107, Rarity.RARE, mage.cards.m.MagmaSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Master of the Veil", 43, Rarity.UNCOMMON, mage.cards.m.MasterOfTheVeil.class, RETRO_ART));
        cards.add(new SetCardInfo("Merchant of Secrets", 44, Rarity.COMMON, mage.cards.m.MerchantOfSecrets.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Seaswift", 45, Rarity.COMMON, mage.cards.m.MistformSeaswift.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Sliver", 46, Rarity.COMMON, mage.cards.m.MistformSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Ultimus", 47, Rarity.RARE, mage.cards.m.MistformUltimus.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Wakecaster", 48, Rarity.UNCOMMON, mage.cards.m.MistformWakecaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Nantuko Vigilante", 132, Rarity.COMMON, mage.cards.n.NantukoVigilante.class, RETRO_ART));
        cards.add(new SetCardInfo("Needleshot Gourna", 133, Rarity.COMMON, mage.cards.n.NeedleshotGourna.class, RETRO_ART));
        cards.add(new SetCardInfo("Noxious Ghoul", 77, Rarity.UNCOMMON, mage.cards.n.NoxiousGhoul.class, RETRO_ART));
        cards.add(new SetCardInfo("Patron of the Wild", 134, Rarity.COMMON, mage.cards.p.PatronOfTheWild.class, RETRO_ART));
        cards.add(new SetCardInfo("Phage the Untouchable", 78, Rarity.RARE, mage.cards.p.PhageTheUntouchable.class, RETRO_ART));
        cards.add(new SetCardInfo("Planar Guide", 18, Rarity.RARE, mage.cards.p.PlanarGuide.class, RETRO_ART));
        cards.add(new SetCardInfo("Plated Sliver", 19, Rarity.COMMON, mage.cards.p.PlatedSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Primal Whisperer", 135, Rarity.RARE, mage.cards.p.PrimalWhisperer.class, RETRO_ART));
        cards.add(new SetCardInfo("Primoc Escapee", 49, Rarity.UNCOMMON, mage.cards.p.PrimocEscapee.class, RETRO_ART));
        cards.add(new SetCardInfo("Quick Sliver", 136, Rarity.COMMON, mage.cards.q.QuickSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Ridgetop Raptor", 108, Rarity.UNCOMMON, mage.cards.r.RidgetopRaptor.class, RETRO_ART));
        cards.add(new SetCardInfo("Riptide Director", 50, Rarity.RARE, mage.cards.r.RiptideDirector.class, RETRO_ART));
        cards.add(new SetCardInfo("Riptide Mangler", 51, Rarity.RARE, mage.cards.r.RiptideMangler.class, RETRO_ART));
        cards.add(new SetCardInfo("Rockshard Elemental", 109, Rarity.RARE, mage.cards.r.RockshardElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Root Sliver", 137, Rarity.UNCOMMON, mage.cards.r.RootSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Scion of Darkness", 79, Rarity.RARE, mage.cards.s.ScionOfDarkness.class, RETRO_ART));
        cards.add(new SetCardInfo("Seedborn Muse", 138, Rarity.RARE, mage.cards.s.SeedbornMuse.class, RETRO_ART));
        cards.add(new SetCardInfo("Shaleskin Plower", 110, Rarity.COMMON, mage.cards.s.ShaleskinPlower.class, RETRO_ART));
        cards.add(new SetCardInfo("Shifting Sliver", 52, Rarity.UNCOMMON, mage.cards.s.ShiftingSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Skinthinner", 80, Rarity.COMMON, mage.cards.s.Skinthinner.class, RETRO_ART));
        cards.add(new SetCardInfo("Skirk Alarmist", 111, Rarity.RARE, mage.cards.s.SkirkAlarmist.class, RETRO_ART));
        cards.add(new SetCardInfo("Skirk Drill Sergeant", 112, Rarity.UNCOMMON, mage.cards.s.SkirkDrillSergeant.class, RETRO_ART));
        cards.add(new SetCardInfo("Skirk Marauder", 113, Rarity.COMMON, mage.cards.s.SkirkMarauder.class, RETRO_ART));
        cards.add(new SetCardInfo("Skirk Outrider", 114, Rarity.COMMON, mage.cards.s.SkirkOutrider.class, RETRO_ART));
        cards.add(new SetCardInfo("Smokespew Invoker", 81, Rarity.COMMON, mage.cards.s.SmokespewInvoker.class, RETRO_ART));
        cards.add(new SetCardInfo("Sootfeather Flock", 82, Rarity.COMMON, mage.cards.s.SootfeatherFlock.class, RETRO_ART));
        cards.add(new SetCardInfo("Spectral Sliver", 83, Rarity.UNCOMMON, mage.cards.s.SpectralSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Starlight Invoker", 20, Rarity.COMMON, mage.cards.s.StarlightInvoker.class, RETRO_ART));
        cards.add(new SetCardInfo("Stoic Champion", 21, Rarity.UNCOMMON, mage.cards.s.StoicChampion.class, RETRO_ART));
        cards.add(new SetCardInfo("Stonewood Invoker", 139, Rarity.COMMON, mage.cards.s.StonewoodInvoker.class, RETRO_ART));
        cards.add(new SetCardInfo("Sunstrike Legionnaire", 22, Rarity.RARE, mage.cards.s.SunstrikeLegionnaire.class, RETRO_ART));
        cards.add(new SetCardInfo("Swooping Talon", 23, Rarity.UNCOMMON, mage.cards.s.SwoopingTalon.class, RETRO_ART));
        cards.add(new SetCardInfo("Synapse Sliver", 53, Rarity.RARE, mage.cards.s.SynapseSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Timberwatch Elf", 140, Rarity.COMMON, mage.cards.t.TimberwatchElf.class, RETRO_ART));
        cards.add(new SetCardInfo("Totem Speaker", 141, Rarity.UNCOMMON, mage.cards.t.TotemSpeaker.class, RETRO_ART));
        cards.add(new SetCardInfo("Toxin Sliver", 84, Rarity.RARE, mage.cards.t.ToxinSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Tribal Forcemage", 142, Rarity.RARE, mage.cards.t.TribalForcemage.class, RETRO_ART));
        cards.add(new SetCardInfo("Unstable Hulk", 115, Rarity.RARE, mage.cards.u.UnstableHulk.class, RETRO_ART));
        cards.add(new SetCardInfo("Vexing Beetle", 143, Rarity.RARE, mage.cards.v.VexingBeetle.class, RETRO_ART));
        cards.add(new SetCardInfo("Vile Deacon", 85, Rarity.COMMON, mage.cards.v.VileDeacon.class, RETRO_ART));
        cards.add(new SetCardInfo("Voidmage Apprentice", 54, Rarity.COMMON, mage.cards.v.VoidmageApprentice.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Deceit", 55, Rarity.UNCOMMON, mage.cards.w.WallOfDeceit.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Hope", 24, Rarity.COMMON, mage.cards.w.WallOfHope.class, RETRO_ART));
        cards.add(new SetCardInfo("Warbreak Trumpeter", 116, Rarity.UNCOMMON, mage.cards.w.WarbreakTrumpeter.class, RETRO_ART));
        cards.add(new SetCardInfo("Ward Sliver", 25, Rarity.UNCOMMON, mage.cards.w.WardSliver.class, RETRO_ART));
        cards.add(new SetCardInfo("Warped Researcher", 56, Rarity.UNCOMMON, mage.cards.w.WarpedResearcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Weaver of Lies", 57, Rarity.RARE, mage.cards.w.WeaverOfLies.class, RETRO_ART));
        cards.add(new SetCardInfo("Whipgrass Entangler", 26, Rarity.COMMON, mage.cards.w.WhipgrassEntangler.class, RETRO_ART));
        cards.add(new SetCardInfo("White Knight", 27, Rarity.UNCOMMON, mage.cards.w.WhiteKnight.class, RETRO_ART));
        cards.add(new SetCardInfo("Willbender", 58, Rarity.UNCOMMON, mage.cards.w.Willbender.class, RETRO_ART));
        cards.add(new SetCardInfo("Windborn Muse", 28, Rarity.RARE, mage.cards.w.WindbornMuse.class, RETRO_ART));
        cards.add(new SetCardInfo("Wingbeat Warrior", 29, Rarity.COMMON, mage.cards.w.WingbeatWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirewood Channeler", 144, Rarity.UNCOMMON, mage.cards.w.WirewoodChanneler.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirewood Hivemaster", 145, Rarity.UNCOMMON, mage.cards.w.WirewoodHivemaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Withered Wretch", 86, Rarity.UNCOMMON, mage.cards.w.WitheredWretch.class, RETRO_ART));
        cards.add(new SetCardInfo("Zombie Brute", 87, Rarity.UNCOMMON, mage.cards.z.ZombieBrute.class, RETRO_ART));
    }
}
