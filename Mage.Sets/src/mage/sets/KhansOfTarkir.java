package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LevelX2
 */
public final class KhansOfTarkir extends ExpansionSet {

    private static final KhansOfTarkir instance = new KhansOfTarkir();

    public static KhansOfTarkir getInstance() {
        return instance;
    }

    private KhansOfTarkir() {
        super("Khans of Tarkir", "KTK", ExpansionSet.buildDate(2014, 9, 26), SetType.EXPANSION);
        this.blockName = "Khans of Tarkir";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Abomination of Gudul", 159, Rarity.COMMON, mage.cards.a.AbominationOfGudul.class));
        cards.add(new SetCardInfo("Abzan Ascendancy", 160, Rarity.RARE, mage.cards.a.AbzanAscendancy.class));
        cards.add(new SetCardInfo("Abzan Banner", 215, Rarity.COMMON, mage.cards.a.AbzanBanner.class));
        cards.add(new SetCardInfo("Abzan Battle Priest", 1, Rarity.UNCOMMON, mage.cards.a.AbzanBattlePriest.class));
        cards.add(new SetCardInfo("Abzan Charm", 161, Rarity.UNCOMMON, mage.cards.a.AbzanCharm.class));
        cards.add(new SetCardInfo("Abzan Falconer", 2, Rarity.UNCOMMON, mage.cards.a.AbzanFalconer.class));
        cards.add(new SetCardInfo("Abzan Guide", 162, Rarity.COMMON, mage.cards.a.AbzanGuide.class));
        cards.add(new SetCardInfo("Act of Treason", 95, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Ainok Bond-Kin", 3, Rarity.COMMON, mage.cards.a.AinokBondKin.class));
        cards.add(new SetCardInfo("Ainok Tracker", 96, Rarity.COMMON, mage.cards.a.AinokTracker.class));
        cards.add(new SetCardInfo("Alabaster Kirin", 4, Rarity.COMMON, mage.cards.a.AlabasterKirin.class));
        cards.add(new SetCardInfo("Alpine Grizzly", 127, Rarity.COMMON, mage.cards.a.AlpineGrizzly.class));
        cards.add(new SetCardInfo("Altar of the Brood", 216, Rarity.RARE, mage.cards.a.AltarOfTheBrood.class));
        cards.add(new SetCardInfo("Anafenza, the Foremost", 163, Rarity.MYTHIC, mage.cards.a.AnafenzaTheForemost.class));
        cards.add(new SetCardInfo("Ankle Shanker", 164, Rarity.RARE, mage.cards.a.AnkleShanker.class));
        cards.add(new SetCardInfo("Archers' Parapet", 128, Rarity.COMMON, mage.cards.a.ArchersParapet.class));
        cards.add(new SetCardInfo("Arc Lightning", 97, Rarity.UNCOMMON, mage.cards.a.ArcLightning.class));
        cards.add(new SetCardInfo("Armament Corps", 165, Rarity.UNCOMMON, mage.cards.a.ArmamentCorps.class));
        cards.add(new SetCardInfo("Arrow Storm", 98, Rarity.COMMON, mage.cards.a.ArrowStorm.class));
        cards.add(new SetCardInfo("Ashcloud Phoenix", 99, Rarity.MYTHIC, mage.cards.a.AshcloudPhoenix.class));
        cards.add(new SetCardInfo("Avalanche Tusker", 166, Rarity.RARE, mage.cards.a.AvalancheTusker.class));
        cards.add(new SetCardInfo("Awaken the Bear", 129, Rarity.COMMON, mage.cards.a.AwakenTheBear.class));
        cards.add(new SetCardInfo("Barrage of Boulders", 100, Rarity.COMMON, mage.cards.b.BarrageOfBoulders.class));
        cards.add(new SetCardInfo("Bear's Companion", 167, Rarity.UNCOMMON, mage.cards.b.BearsCompanion.class));
        cards.add(new SetCardInfo("Become Immense", 130, Rarity.UNCOMMON, mage.cards.b.BecomeImmense.class));
        cards.add(new SetCardInfo("Bellowing Saddlebrute", 64, Rarity.UNCOMMON, mage.cards.b.BellowingSaddlebrute.class));
        cards.add(new SetCardInfo("Bitter Revelation", 65, Rarity.COMMON, mage.cards.b.BitterRevelation.class));
        cards.add(new SetCardInfo("Blinding Spray", 32, Rarity.UNCOMMON, mage.cards.b.BlindingSpray.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 229, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Bloodfire Expert", 101, Rarity.COMMON, mage.cards.b.BloodfireExpert.class));
        cards.add(new SetCardInfo("Bloodfire Mentor", 102, Rarity.COMMON, mage.cards.b.BloodfireMentor.class));
        cards.add(new SetCardInfo("Bloodsoaked Champion", 66, Rarity.RARE, mage.cards.b.BloodsoakedChampion.class));
        cards.add(new SetCardInfo("Bloodstained Mire", 230, Rarity.RARE, mage.cards.b.BloodstainedMire.class));
        cards.add(new SetCardInfo("Blossoming Sands", 231, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Brave the Sands", 5, Rarity.UNCOMMON, mage.cards.b.BraveTheSands.class));
        cards.add(new SetCardInfo("Briber's Purse", 217, Rarity.UNCOMMON, mage.cards.b.BribersPurse.class));
        cards.add(new SetCardInfo("Bring Low", 103, Rarity.COMMON, mage.cards.b.BringLow.class));
        cards.add(new SetCardInfo("Burn Away", 104, Rarity.UNCOMMON, mage.cards.b.BurnAway.class));
        cards.add(new SetCardInfo("Butcher of the Horde", 168, Rarity.RARE, mage.cards.b.ButcherOfTheHorde.class));
        cards.add(new SetCardInfo("Cancel", 33, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Canyon Lurkers", 105, Rarity.COMMON, mage.cards.c.CanyonLurkers.class));
        cards.add(new SetCardInfo("Chief of the Edge", 169, Rarity.UNCOMMON, mage.cards.c.ChiefOfTheEdge.class));
        cards.add(new SetCardInfo("Chief of the Scale", 170, Rarity.UNCOMMON, mage.cards.c.ChiefOfTheScale.class));
        cards.add(new SetCardInfo("Clever Impersonator", 34, Rarity.MYTHIC, mage.cards.c.CleverImpersonator.class));
        cards.add(new SetCardInfo("Crackling Doom", 171, Rarity.RARE, mage.cards.c.CracklingDoom.class));
        cards.add(new SetCardInfo("Cranial Archive", 218, Rarity.UNCOMMON, mage.cards.c.CranialArchive.class));
        cards.add(new SetCardInfo("Crater's Claws", 106, Rarity.RARE, mage.cards.c.CratersClaws.class));
        cards.add(new SetCardInfo("Crippling Chill", 35, Rarity.COMMON, mage.cards.c.CripplingChill.class));
        cards.add(new SetCardInfo("Dazzling Ramparts", 6, Rarity.UNCOMMON, mage.cards.d.DazzlingRamparts.class));
        cards.add(new SetCardInfo("Dead Drop", 67, Rarity.UNCOMMON, mage.cards.d.DeadDrop.class));
        cards.add(new SetCardInfo("Death Frenzy", 172, Rarity.UNCOMMON, mage.cards.d.DeathFrenzy.class));
        cards.add(new SetCardInfo("Debilitating Injury", 68, Rarity.COMMON, mage.cards.d.DebilitatingInjury.class));
        cards.add(new SetCardInfo("Defiant Strike", 7, Rarity.COMMON, mage.cards.d.DefiantStrike.class));
        cards.add(new SetCardInfo("Deflecting Palm", 173, Rarity.RARE, mage.cards.d.DeflectingPalm.class));
        cards.add(new SetCardInfo("Despise", 69, Rarity.UNCOMMON, mage.cards.d.Despise.class));
        cards.add(new SetCardInfo("Dig Through Time", 36, Rarity.RARE, mage.cards.d.DigThroughTime.class));
        cards.add(new SetCardInfo("Disdainful Stroke", 37, Rarity.COMMON, mage.cards.d.DisdainfulStroke.class));
        cards.add(new SetCardInfo("Dismal Backwater", 232, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Disowned Ancestor", 70, Rarity.COMMON, mage.cards.d.DisownedAncestor.class));
        cards.add(new SetCardInfo("Dragon Grip", 107, Rarity.UNCOMMON, mage.cards.d.DragonGrip.class));
        cards.add(new SetCardInfo("Dragonscale Boon", 131, Rarity.COMMON, mage.cards.d.DragonscaleBoon.class));
        cards.add(new SetCardInfo("Dragon's Eye Savants", 38, Rarity.UNCOMMON, mage.cards.d.DragonsEyeSavants.class));
        cards.add(new SetCardInfo("Dragon-Style Twins", 108, Rarity.RARE, mage.cards.d.DragonStyleTwins.class));
        cards.add(new SetCardInfo("Dragon Throne of Tarkir", 219, Rarity.RARE, mage.cards.d.DragonThroneOfTarkir.class));
        cards.add(new SetCardInfo("Duneblast", 174, Rarity.RARE, mage.cards.d.Duneblast.class));
        cards.add(new SetCardInfo("Dutiful Return", 71, Rarity.COMMON, mage.cards.d.DutifulReturn.class));
        cards.add(new SetCardInfo("Efreet Weaponmaster", 175, Rarity.COMMON, mage.cards.e.EfreetWeaponmaster.class));
        cards.add(new SetCardInfo("Embodiment of Spring", 39, Rarity.COMMON, mage.cards.e.EmbodimentOfSpring.class));
        cards.add(new SetCardInfo("Empty the Pits", 72, Rarity.MYTHIC, mage.cards.e.EmptyThePits.class));
        cards.add(new SetCardInfo("End Hostilities", 8, Rarity.RARE, mage.cards.e.EndHostilities.class));
        cards.add(new SetCardInfo("Erase", 9, Rarity.COMMON, mage.cards.e.Erase.class));
        cards.add(new SetCardInfo("Feat of Resistance", 10, Rarity.COMMON, mage.cards.f.FeatOfResistance.class));
        cards.add(new SetCardInfo("Feed the Clan", 132, Rarity.COMMON, mage.cards.f.FeedTheClan.class));
        cards.add(new SetCardInfo("Firehoof Cavalry", 11, Rarity.COMMON, mage.cards.f.FirehoofCavalry.class));
        cards.add(new SetCardInfo("Flooded Strand", 233, Rarity.RARE, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Flying Crane Technique", 176, Rarity.RARE, mage.cards.f.FlyingCraneTechnique.class));
        cards.add(new SetCardInfo("Force Away", 40, Rarity.COMMON, mage.cards.f.ForceAway.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 267, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 268, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 269, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frontier Bivouac", 234, Rarity.UNCOMMON, mage.cards.f.FrontierBivouac.class));
        cards.add(new SetCardInfo("Ghostfire Blade", 220, Rarity.RARE, mage.cards.g.GhostfireBlade.class));
        cards.add(new SetCardInfo("Glacial Stalker", 41, Rarity.COMMON, mage.cards.g.GlacialStalker.class));
        cards.add(new SetCardInfo("Goblinslide", 109, Rarity.UNCOMMON, mage.cards.g.Goblinslide.class));
        cards.add(new SetCardInfo("Grim Haruspex", 73, Rarity.RARE, mage.cards.g.GrimHaruspex.class));
        cards.add(new SetCardInfo("Gurmag Swiftwing", 74, Rarity.UNCOMMON, mage.cards.g.GurmagSwiftwing.class));
        cards.add(new SetCardInfo("Hardened Scales", 133, Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Heart-Piercer Bow", 221, Rarity.UNCOMMON, mage.cards.h.HeartPiercerBow.class));
        cards.add(new SetCardInfo("Heir of the Wilds", 134, Rarity.UNCOMMON, mage.cards.h.HeirOfTheWilds.class));
        cards.add(new SetCardInfo("Herald of Anafenza", 12, Rarity.RARE, mage.cards.h.HeraldOfAnafenza.class));
        cards.add(new SetCardInfo("Highland Game", 135, Rarity.COMMON, mage.cards.h.HighlandGame.class));
        cards.add(new SetCardInfo("High Sentinels of Arashin", 13, Rarity.RARE, mage.cards.h.HighSentinelsOfArashin.class));
        cards.add(new SetCardInfo("Highspire Mantis", 177, Rarity.UNCOMMON, mage.cards.h.HighspireMantis.class));
        cards.add(new SetCardInfo("Hooded Hydra", 136, Rarity.MYTHIC, mage.cards.h.HoodedHydra.class));
        cards.add(new SetCardInfo("Hooting Mandrills", 137, Rarity.COMMON, mage.cards.h.HootingMandrills.class));
        cards.add(new SetCardInfo("Horde Ambusher", 110, Rarity.UNCOMMON, mage.cards.h.HordeAmbusher.class));
        cards.add(new SetCardInfo("Hordeling Outburst", 111, Rarity.UNCOMMON, mage.cards.h.HordelingOutburst.class));
        cards.add(new SetCardInfo("Howl of the Horde", 112, Rarity.RARE, mage.cards.h.HowlOfTheHorde.class));
        cards.add(new SetCardInfo("Icefeather Aven", 178, Rarity.UNCOMMON, mage.cards.i.IcefeatherAven.class));
        cards.add(new SetCardInfo("Icy Blast", 42, Rarity.RARE, mage.cards.i.IcyBlast.class));
        cards.add(new SetCardInfo("Incremental Growth", 138, Rarity.UNCOMMON, mage.cards.i.IncrementalGrowth.class));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 256, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 257, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivorytusk Fortress", 179, Rarity.RARE, mage.cards.i.IvorytuskFortress.class));
        cards.add(new SetCardInfo("Jeering Instigator", 113, Rarity.RARE, mage.cards.j.JeeringInstigator.class));
        cards.add(new SetCardInfo("Jeskai Ascendancy", 180, Rarity.RARE, mage.cards.j.JeskaiAscendancy.class));
        cards.add(new SetCardInfo("Jeskai Banner", 222, Rarity.COMMON, mage.cards.j.JeskaiBanner.class));
        cards.add(new SetCardInfo("Jeskai Charm", 181, Rarity.UNCOMMON, mage.cards.j.JeskaiCharm.class));
        cards.add(new SetCardInfo("Jeskai Elder", 43, Rarity.UNCOMMON, mage.cards.j.JeskaiElder.class));
        cards.add(new SetCardInfo("Jeskai Student", 14, Rarity.COMMON, mage.cards.j.JeskaiStudent.class));
        cards.add(new SetCardInfo("Jeskai Windscout", 44, Rarity.COMMON, mage.cards.j.JeskaiWindscout.class));
        cards.add(new SetCardInfo("Jungle Hollow", 235, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kheru Bloodsucker", 75, Rarity.UNCOMMON, mage.cards.k.KheruBloodsucker.class));
        cards.add(new SetCardInfo("Kheru Dreadmaw", 76, Rarity.COMMON, mage.cards.k.KheruDreadmaw.class));
        cards.add(new SetCardInfo("Kheru Lich Lord", 182, Rarity.RARE, mage.cards.k.KheruLichLord.class));
        cards.add(new SetCardInfo("Kheru Spellsnatcher", 45, Rarity.RARE, mage.cards.k.KheruSpellsnatcher.class));
        cards.add(new SetCardInfo("Kill Shot", 15, Rarity.COMMON, mage.cards.k.KillShot.class));
        cards.add(new SetCardInfo("Kin-Tree Invocation", 183, Rarity.UNCOMMON, mage.cards.k.KinTreeInvocation.class));
        cards.add(new SetCardInfo("Kin-Tree Warden", 139, Rarity.COMMON, mage.cards.k.KinTreeWarden.class));
        cards.add(new SetCardInfo("Krumar Bond-Kin", 77, Rarity.COMMON, mage.cards.k.KrumarBondKin.class));
        cards.add(new SetCardInfo("Leaping Master", 114, Rarity.COMMON, mage.cards.l.LeapingMaster.class));
        cards.add(new SetCardInfo("Lens of Clarity", 223, Rarity.COMMON, mage.cards.l.LensOfClarity.class));
        cards.add(new SetCardInfo("Longshot Squad", 140, Rarity.COMMON, mage.cards.l.LongshotSquad.class));
        cards.add(new SetCardInfo("Mantis Rider", 184, Rarity.RARE, mage.cards.m.MantisRider.class));
        cards.add(new SetCardInfo("Mardu Ascendancy", 185, Rarity.RARE, mage.cards.m.MarduAscendancy.class));
        cards.add(new SetCardInfo("Mardu Banner", 224, Rarity.COMMON, mage.cards.m.MarduBanner.class));
        cards.add(new SetCardInfo("Mardu Blazebringer", 115, Rarity.UNCOMMON, mage.cards.m.MarduBlazebringer.class));
        cards.add(new SetCardInfo("Mardu Charm", 186, Rarity.UNCOMMON, mage.cards.m.MarduCharm.class));
        cards.add(new SetCardInfo("Mardu Hateblade", 16, Rarity.COMMON, mage.cards.m.MarduHateblade.class));
        cards.add(new SetCardInfo("Mardu Heart-Piercer", 116, Rarity.UNCOMMON, mage.cards.m.MarduHeartPiercer.class));
        cards.add(new SetCardInfo("Mardu Hordechief", 17, Rarity.COMMON, mage.cards.m.MarduHordechief.class));
        cards.add(new SetCardInfo("Mardu Roughrider", 187, Rarity.UNCOMMON, mage.cards.m.MarduRoughrider.class));
        cards.add(new SetCardInfo("Mardu Skullhunter", 78, Rarity.COMMON, mage.cards.m.MarduSkullhunter.class));
        cards.add(new SetCardInfo("Mardu Warshrieker", 117, Rarity.COMMON, mage.cards.m.MarduWarshrieker.class));
        cards.add(new SetCardInfo("Master of Pearls", 18, Rarity.RARE, mage.cards.m.MasterOfPearls.class));
        cards.add(new SetCardInfo("Master the Way", 188, Rarity.UNCOMMON, mage.cards.m.MasterTheWay.class));
        cards.add(new SetCardInfo("Meandering Towershell", 141, Rarity.RARE, mage.cards.m.MeanderingTowershell.class));
        cards.add(new SetCardInfo("Mer-Ek Nightblade", 79, Rarity.UNCOMMON, mage.cards.m.MerEkNightblade.class));
        cards.add(new SetCardInfo("Mindswipe", 189, Rarity.RARE, mage.cards.m.Mindswipe.class));
        cards.add(new SetCardInfo("Mistfire Weaver", 46, Rarity.UNCOMMON, mage.cards.m.MistfireWeaver.class));
        cards.add(new SetCardInfo("Molting Snakeskin", 80, Rarity.COMMON, mage.cards.m.MoltingSnakeskin.class));
        cards.add(new SetCardInfo("Monastery Flock", 47, Rarity.COMMON, mage.cards.m.MonasteryFlock.class));
        cards.add(new SetCardInfo("Monastery Swiftspear", 118, Rarity.UNCOMMON, mage.cards.m.MonasterySwiftspear.class));
        cards.add(new SetCardInfo("Mountain", 262, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 263, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 264, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murderous Cut", 81, Rarity.UNCOMMON, mage.cards.m.MurderousCut.class));
        cards.add(new SetCardInfo("Mystic Monastery", 236, Rarity.UNCOMMON, mage.cards.m.MysticMonastery.class));
        cards.add(new SetCardInfo("Mystic of the Hidden Way", 48, Rarity.COMMON, mage.cards.m.MysticOfTheHiddenWay.class));
        cards.add(new SetCardInfo("Narset, Enlightened Master", 190, Rarity.MYTHIC, mage.cards.n.NarsetEnlightenedMaster.class));
        cards.add(new SetCardInfo("Naturalize", 142, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Necropolis Fiend", 82, Rarity.RARE, mage.cards.n.NecropolisFiend.class));
        cards.add(new SetCardInfo("Nomad Outpost", 237, Rarity.UNCOMMON, mage.cards.n.NomadOutpost.class));
        cards.add(new SetCardInfo("Opulent Palace", 238, Rarity.UNCOMMON, mage.cards.o.OpulentPalace.class));
        cards.add(new SetCardInfo("Pearl Lake Ancient", 49, Rarity.MYTHIC, mage.cards.p.PearlLakeAncient.class));
        cards.add(new SetCardInfo("Pine Walker", 143, Rarity.UNCOMMON, mage.cards.p.PineWalker.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Polluted Delta", 239, Rarity.RARE, mage.cards.p.PollutedDelta.class));
        cards.add(new SetCardInfo("Ponyback Brigade", 191, Rarity.COMMON, mage.cards.p.PonybackBrigade.class));
        cards.add(new SetCardInfo("Quiet Contemplation", 50, Rarity.UNCOMMON, mage.cards.q.QuietContemplation.class));
        cards.add(new SetCardInfo("Raiders' Spoils", 83, Rarity.UNCOMMON, mage.cards.r.RaidersSpoils.class));
        cards.add(new SetCardInfo("Rakshasa Deathdealer", 192, Rarity.RARE, mage.cards.r.RakshasaDeathdealer.class));
        cards.add(new SetCardInfo("Rakshasa's Secret", 84, Rarity.COMMON, mage.cards.r.RakshasasSecret.class));
        cards.add(new SetCardInfo("Rakshasa Vizier", 193, Rarity.RARE, mage.cards.r.RakshasaVizier.class));
        cards.add(new SetCardInfo("Rattleclaw Mystic", 144, Rarity.RARE, mage.cards.r.RattleclawMystic.class));
        cards.add(new SetCardInfo("Retribution of the Ancients", 85, Rarity.RARE, mage.cards.r.RetributionOfTheAncients.class));
        cards.add(new SetCardInfo("Ride Down", 194, Rarity.UNCOMMON, mage.cards.r.RideDown.class));
        cards.add(new SetCardInfo("Rite of the Serpent", 86, Rarity.COMMON, mage.cards.r.RiteOfTheSerpent.class));
        cards.add(new SetCardInfo("Riverwheel Aerialists", 51, Rarity.UNCOMMON, mage.cards.r.RiverwheelAerialists.class));
        cards.add(new SetCardInfo("Roar of Challenge", 145, Rarity.UNCOMMON, mage.cards.r.RoarOfChallenge.class));
        cards.add(new SetCardInfo("Rotting Mastodon", 87, Rarity.COMMON, mage.cards.r.RottingMastodon.class));
        cards.add(new SetCardInfo("Rugged Highlands", 240, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Rush of Battle", 19, Rarity.COMMON, mage.cards.r.RushOfBattle.class));
        cards.add(new SetCardInfo("Ruthless Ripper", 88, Rarity.UNCOMMON, mage.cards.r.RuthlessRipper.class));
        cards.add(new SetCardInfo("Sage-Eye Harrier", 20, Rarity.COMMON, mage.cards.s.SageEyeHarrier.class));
        cards.add(new SetCardInfo("Sage of the Inward Eye", 195, Rarity.RARE, mage.cards.s.SageOfTheInwardEye.class));
        cards.add(new SetCardInfo("Sagu Archer", 146, Rarity.COMMON, mage.cards.s.SaguArcher.class));
        cards.add(new SetCardInfo("Sagu Mauler", 196, Rarity.RARE, mage.cards.s.SaguMauler.class));
        cards.add(new SetCardInfo("Salt Road Patrol", 21, Rarity.COMMON, mage.cards.s.SaltRoadPatrol.class));
        cards.add(new SetCardInfo("Sandsteppe Citadel", 241, Rarity.UNCOMMON, mage.cards.s.SandsteppeCitadel.class));
        cards.add(new SetCardInfo("Sarkhan, the Dragonspeaker", 119, Rarity.MYTHIC, mage.cards.s.SarkhanTheDragonspeaker.class));
        cards.add(new SetCardInfo("Savage Knuckleblade", 197, Rarity.RARE, mage.cards.s.SavageKnuckleblade.class));
        cards.add(new SetCardInfo("Savage Punch", 147, Rarity.COMMON, mage.cards.s.SavagePunch.class));
        cards.add(new SetCardInfo("Scaldkin", 52, Rarity.COMMON, mage.cards.s.Scaldkin.class));
        cards.add(new SetCardInfo("Scion of Glaciers", 53, Rarity.UNCOMMON, mage.cards.s.ScionOfGlaciers.class));
        cards.add(new SetCardInfo("Scoured Barrens", 242, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Scout the Borders", 148, Rarity.COMMON, mage.cards.s.ScoutTheBorders.class));
        cards.add(new SetCardInfo("Secret Plans", 198, Rarity.UNCOMMON, mage.cards.s.SecretPlans.class));
        cards.add(new SetCardInfo("Seeker of the Way", 22, Rarity.UNCOMMON, mage.cards.s.SeekerOfTheWay.class));
        cards.add(new SetCardInfo("Seek the Horizon", 150, Rarity.UNCOMMON, mage.cards.s.SeekTheHorizon.class));
        cards.add(new SetCardInfo("See the Unwritten", 149, Rarity.MYTHIC, mage.cards.s.SeeTheUnwritten.class));
        cards.add(new SetCardInfo("Set Adrift", 54, Rarity.UNCOMMON, mage.cards.s.SetAdrift.class));
        cards.add(new SetCardInfo("Shambling Attendants", 89, Rarity.COMMON, mage.cards.s.ShamblingAttendants.class));
        cards.add(new SetCardInfo("Shatter", 120, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Sidisi, Brood Tyrant", 199, Rarity.MYTHIC, mage.cards.s.SidisiBroodTyrant.class));
        cards.add(new SetCardInfo("Sidisi's Pet", 90, Rarity.COMMON, mage.cards.s.SidisisPet.class));
        cards.add(new SetCardInfo("Siegecraft", 23, Rarity.COMMON, mage.cards.s.Siegecraft.class));
        cards.add(new SetCardInfo("Siege Rhino", 200, Rarity.RARE, mage.cards.s.SiegeRhino.class));
        cards.add(new SetCardInfo("Singing Bell Strike", 55, Rarity.COMMON, mage.cards.s.SingingBellStrike.class));
        cards.add(new SetCardInfo("Smite the Monstrous", 24, Rarity.COMMON, mage.cards.s.SmiteTheMonstrous.class));
        cards.add(new SetCardInfo("Smoke Teller", 151, Rarity.COMMON, mage.cards.s.SmokeTeller.class));
        cards.add(new SetCardInfo("Snowhorn Rider", 201, Rarity.COMMON, mage.cards.s.SnowhornRider.class));
        cards.add(new SetCardInfo("Sorin, Solemn Visitor", 202, Rarity.MYTHIC, mage.cards.s.SorinSolemnVisitor.class));
        cards.add(new SetCardInfo("Stubborn Denial", 56, Rarity.UNCOMMON, mage.cards.s.StubbornDenial.class));
        cards.add(new SetCardInfo("Sultai Ascendancy", 203, Rarity.RARE, mage.cards.s.SultaiAscendancy.class));
        cards.add(new SetCardInfo("Sultai Banner", 225, Rarity.COMMON, mage.cards.s.SultaiBanner.class));
        cards.add(new SetCardInfo("Sultai Charm", 204, Rarity.UNCOMMON, mage.cards.s.SultaiCharm.class));
        cards.add(new SetCardInfo("Sultai Flayer", 152, Rarity.UNCOMMON, mage.cards.s.SultaiFlayer.class));
        cards.add(new SetCardInfo("Sultai Scavenger", 91, Rarity.COMMON, mage.cards.s.SultaiScavenger.class));
        cards.add(new SetCardInfo("Sultai Soothsayer", 205, Rarity.UNCOMMON, mage.cards.s.SultaiSoothsayer.class));
        cards.add(new SetCardInfo("Summit Prowler", 121, Rarity.COMMON, mage.cards.s.SummitProwler.class));
        cards.add(new SetCardInfo("Surrak Dragonclaw", 206, Rarity.MYTHIC, mage.cards.s.SurrakDragonclaw.class));
        cards.add(new SetCardInfo("Suspension Field", 25, Rarity.UNCOMMON, mage.cards.s.SuspensionField.class));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 259, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 260, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm of Bloodflies", 92, Rarity.UNCOMMON, mage.cards.s.SwarmOfBloodflies.class));
        cards.add(new SetCardInfo("Swift Kick", 122, Rarity.COMMON, mage.cards.s.SwiftKick.class));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 243, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Taigam's Scheming", 57, Rarity.COMMON, mage.cards.t.TaigamsScheming.class));
        cards.add(new SetCardInfo("Take Up Arms", 26, Rarity.UNCOMMON, mage.cards.t.TakeUpArms.class));
        cards.add(new SetCardInfo("Temur Ascendancy", 207, Rarity.RARE, mage.cards.t.TemurAscendancy.class));
        cards.add(new SetCardInfo("Temur Banner", 226, Rarity.COMMON, mage.cards.t.TemurBanner.class));
        cards.add(new SetCardInfo("Temur Charger", 153, Rarity.UNCOMMON, mage.cards.t.TemurCharger.class));
        cards.add(new SetCardInfo("Temur Charm", 208, Rarity.UNCOMMON, mage.cards.t.TemurCharm.class));
        cards.add(new SetCardInfo("Thornwood Falls", 244, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thousand Winds", 58, Rarity.RARE, mage.cards.t.ThousandWinds.class));
        cards.add(new SetCardInfo("Throttle", 93, Rarity.COMMON, mage.cards.t.Throttle.class));
        cards.add(new SetCardInfo("Timely Hordemate", 27, Rarity.UNCOMMON, mage.cards.t.TimelyHordemate.class));
        cards.add(new SetCardInfo("Tomb of the Spirit Dragon", 245, Rarity.UNCOMMON, mage.cards.t.TombOfTheSpiritDragon.class));
        cards.add(new SetCardInfo("Tormenting Voice", 123, Rarity.COMMON, mage.cards.t.TormentingVoice.class));
        cards.add(new SetCardInfo("Trail of Mystery", 154, Rarity.RARE, mage.cards.t.TrailOfMystery.class));
        cards.add(new SetCardInfo("Tranquil Cove", 246, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Trap Essence", 209, Rarity.RARE, mage.cards.t.TrapEssence.class));
        cards.add(new SetCardInfo("Treasure Cruise", 59, Rarity.COMMON, mage.cards.t.TreasureCruise.class));
        cards.add(new SetCardInfo("Trumpet Blast", 124, Rarity.COMMON, mage.cards.t.TrumpetBlast.class));
        cards.add(new SetCardInfo("Tusked Colossodon", 155, Rarity.COMMON, mage.cards.t.TuskedColossodon.class));
        cards.add(new SetCardInfo("Tuskguard Captain", 156, Rarity.UNCOMMON, mage.cards.t.TuskguardCaptain.class));
        cards.add(new SetCardInfo("Ugin's Nexus", 227, Rarity.MYTHIC, mage.cards.u.UginsNexus.class));
        cards.add(new SetCardInfo("Unyielding Krumar", 94, Rarity.COMMON, mage.cards.u.UnyieldingKrumar.class));
        cards.add(new SetCardInfo("Utter End", 210, Rarity.RARE, mage.cards.u.UtterEnd.class));
        cards.add(new SetCardInfo("Valley Dasher", 125, Rarity.COMMON, mage.cards.v.ValleyDasher.class));
        cards.add(new SetCardInfo("Venerable Lammasu", 28, Rarity.UNCOMMON, mage.cards.v.VenerableLammasu.class));
        cards.add(new SetCardInfo("Villainous Wealth", 211, Rarity.RARE, mage.cards.v.VillainousWealth.class));
        cards.add(new SetCardInfo("War Behemoth", 29, Rarity.COMMON, mage.cards.w.WarBehemoth.class));
        cards.add(new SetCardInfo("Warden of the Eye", 212, Rarity.UNCOMMON, mage.cards.w.WardenOfTheEye.class));
        cards.add(new SetCardInfo("War-Name Aspirant", 126, Rarity.UNCOMMON, mage.cards.w.WarNameAspirant.class));
        cards.add(new SetCardInfo("Watcher of the Roost", 30, Rarity.UNCOMMON, mage.cards.w.WatcherOfTheRoost.class));
        cards.add(new SetCardInfo("Waterwhirl", 60, Rarity.UNCOMMON, mage.cards.w.Waterwhirl.class));
        cards.add(new SetCardInfo("Weave Fate", 61, Rarity.COMMON, mage.cards.w.WeaveFate.class));
        cards.add(new SetCardInfo("Wetland Sambar", 62, Rarity.COMMON, mage.cards.w.WetlandSambar.class));
        cards.add(new SetCardInfo("Whirlwind Adept", 63, Rarity.COMMON, mage.cards.w.WhirlwindAdept.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 247, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Windstorm", 157, Rarity.UNCOMMON, mage.cards.w.Windstorm.class));
        cards.add(new SetCardInfo("Windswept Heath", 248, Rarity.RARE, mage.cards.w.WindsweptHeath.class));
        cards.add(new SetCardInfo("Wingmate Roc", 31, Rarity.MYTHIC, mage.cards.w.WingmateRoc.class));
        cards.add(new SetCardInfo("Winterflame", 213, Rarity.UNCOMMON, mage.cards.w.Winterflame.class));
        cards.add(new SetCardInfo("Witness of the Ages", 228, Rarity.UNCOMMON, mage.cards.w.WitnessOfTheAges.class));
        cards.add(new SetCardInfo("Wooded Foothills", 249, Rarity.RARE, mage.cards.w.WoodedFoothills.class));
        cards.add(new SetCardInfo("Woolly Loxodon", 158, Rarity.COMMON, mage.cards.w.WoollyLoxodon.class));
        cards.add(new SetCardInfo("Zurgo Helmsmasher", 214, Rarity.MYTHIC, mage.cards.z.ZurgoHelmsmasher.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new KhansOfTarkirCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/ktk.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class KhansOfTarkirCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "37", "105", "24", "59", "98", "17", "41", "120", "11", "62", "125", "9", "57", "124", "3", "63", "117", "7", "35", "96", "10", "33", "100", "16", "47", "114", "19", "37", "122", "20", "61", "101", "24", "41", "105", "21", "52", "98", "17", "63", "120", "3", "62", "125", "11", "47", "114", "16", "59", "117", "9", "57", "100", "20", "35", "96", "7", "52", "101", "10", "33", "122", "19", "61", "124", "21");
    private final CardRun commonB = new CardRun(true, "91", "129", "71", "137", "70", "128", "86", "131", "80", "158", "77", "139", "78", "132", "87", "147", "68", "151", "90", "129", "65", "146", "71", "128", "70", "127", "91", "137", "86", "131", "80", "158", "77", "132", "65", "139", "87", "128", "71", "147", "68", "151", "91", "137", "86", "146", "78", "129", "80", "131", "90", "127", "87", "151", "70", "158", "65", "139", "78", "132", "68", "146", "77", "147", "90", "127");
    private final CardRun commonC1 = new CardRun(true, "215", "39", "232", "123", "175", "29", "229", "48", "121", "225", "4", "244", "44", "201", "95", "247", "15", "226", "40", "103", "240", "14", "159", "55", "232", "102", "223", "29", "242", "39", "95", "175", "23", "244", "44", "215", "123", "229", "4", "159", "48", "121", "240", "15", "225", "40", "247", "103", "201", "23", "226", "55", "102", "242", "14");
    private final CardRun commonC2 = new CardRun(true, "94", "135", "246", "84", "222", "142", "231", "76", "191", "140", "243", "93", "148", "223", "89", "235", "135", "162", "94", "155", "246", "84", "224", "142", "231", "93", "191", "148", "222", "76", "140", "235", "94", "224", "135", "243", "76", "162", "155", "246", "89", "222", "142", "235", "84", "191", "140", "231", "93", "148", "224", "89", "243", "155", "162");
    private final CardRun uncommonA = new CardRun(true, "32", "170", "27", "97", "169", "1", "56", "28", "51", "104", "213", "111", "188", "115", "32", "109", "27", "56", "97", "6", "107", "46", "1", "183", "115", "5", "213", "28", "116", "169", "104", "53", "188", "97", "183", "46", "111", "5", "51", "213", "109", "1", "53", "170", "116", "6", "46", "188", "107", "27", "51", "115", "28", "32", "169", "111", "170", "56", "109", "5", "116", "53", "107", "183", "104", "6");
    private final CardRun uncommonB = new CardRun(true, "161", "88", "145", "208", "130", "186", "138", "79", "218", "75", "238", "150", "64", "161", "74", "152", "88", "217", "165", "130", "181", "143", "92", "238", "79", "208", "75", "145", "236", "74", "138", "181", "241", "150", "161", "79", "152", "64", "217", "130", "238", "143", "218", "165", "241", "74", "208", "186", "152", "181", "88", "236", "92", "138", "218", "64", "145", "165", "143", "186", "241", "75", "150", "92", "217", "236");
    private final CardRun uncommonC = new CardRun(true, "194", "60", "26", "110", "118", "198", "30", "126", "177", "50", "2", "38", "194", "25", "178", "26", "172", "30", "54", "22", "177", "110", "198", "126", "43", "178", "38", "25", "50", "26", "194", "30", "118", "60", "172", "54", "110", "2", "198", "38", "22", "177", "43", "25", "60", "118", "50", "178", "54", "2", "126", "172", "43", "22");
    private final CardRun uncommonD = new CardRun(true, "67", "228", "212", "234", "81", "156", "205", "134", "245", "204", "221", "153", "187", "69", "167", "228", "212", "234", "134", "83", "81", "245", "153", "67", "156", "237", "167", "221", "187", "212", "69", "204", "157", "245", "205", "67", "134", "167", "83", "237", "153", "69", "228", "157", "234", "187", "156", "221", "81", "205", "83", "204", "237", "157");
    private final CardRun rare = new CardRun(true, "214", "18", "163", "108", "209", "219", "180", "73", "164", "141", "166", "133", "210", "248", "85", "206", "42", "173", "31", "182", "230", "195", "113", "203", "133", "199", "249", "211", "219", "197", "34", "171", "12", "106", "192", "149", "179", "8", "233", "202", "82", "112", "211", "42", "230", "196", "106", "173", "239", "174", "216", "185", "13", "171", "36", "144", "184", "119", "164", "233", "192", "73", "168", "49", "174", "13", "249", "179", "108", "18", "190", "58", "200", "220", "193", "112", "239", "196", "45", "160", "248", "168", "113", "176", "66", "58", "193", "154", "200", "8", "160", "36", "185", "82", "184", "220", "207", "141", "182", "227", "189", "154", "210", "85", "180", "216", "203", "45", "166", "99", "207", "72", "209", "136", "176", "144", "197", "12", "189", "66", "195");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267", "268", "269");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure ABC = new BoosterStructure(uncommonA, uncommonB, uncommonC);
    private final BoosterStructure ABD = new BoosterStructure(uncommonA, uncommonB, uncommonD);
    private final BoosterStructure ACD = new BoosterStructure(uncommonA, uncommonC, uncommonD);
    private final BoosterStructure BCD = new BoosterStructure(uncommonB, uncommonC, uncommonD);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.825 A uncommons (33 / 40)
    // 0.825 B uncommons (33 / 40)
    // 0.675 C uncommons (27 / 40)
    // 0.675 D uncommons (27 / 40)
    // These numbers are the same for all sets with 80 uncommons in asymmetrical A/B/C/D print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC,
            ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD,
            ACD, BCD, ACD, BCD, ACD, BCD, ACD,
            BCD, ACD, BCD, ACD, BCD, ACD, BCD
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
