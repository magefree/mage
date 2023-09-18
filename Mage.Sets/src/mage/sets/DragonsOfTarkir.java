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
 * @author fireshoes
 */
public final class DragonsOfTarkir extends ExpansionSet {

    private static final DragonsOfTarkir instance = new DragonsOfTarkir();

    public static DragonsOfTarkir getInstance() {
        return instance;
    }

    private DragonsOfTarkir() {
        super("Dragons of Tarkir", "DTK", ExpansionSet.buildDate(2015, 3, 27), SetType.EXPANSION);
        this.blockName = "Khans of Tarkir";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Acid-Spewer Dragon", 86, Rarity.UNCOMMON, mage.cards.a.AcidSpewerDragon.class));
        cards.add(new SetCardInfo("Aerie Bowmasters", 170, Rarity.COMMON, mage.cards.a.AerieBowmasters.class));
        cards.add(new SetCardInfo("Ainok Artillerist", 171, Rarity.COMMON, mage.cards.a.AinokArtillerist.class));
        cards.add(new SetCardInfo("Ainok Survivalist", 172, Rarity.UNCOMMON, mage.cards.a.AinokSurvivalist.class));
        cards.add(new SetCardInfo("Ambuscade Shaman", 87, Rarity.UNCOMMON, mage.cards.a.AmbuscadeShaman.class));
        cards.add(new SetCardInfo("Anafenza, Kin-Tree Spirit", 2, Rarity.RARE, mage.cards.a.AnafenzaKinTreeSpirit.class));
        cards.add(new SetCardInfo("Ancestral Statue", 234, Rarity.COMMON, mage.cards.a.AncestralStatue.class));
        cards.add(new SetCardInfo("Ancient Carp", 44, Rarity.COMMON, mage.cards.a.AncientCarp.class));
        cards.add(new SetCardInfo("Anticipate", 45, Rarity.COMMON, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Arashin Foremost", 3, Rarity.RARE, mage.cards.a.ArashinForemost.class));
        cards.add(new SetCardInfo("Arashin Sovereign", 212, Rarity.RARE, mage.cards.a.ArashinSovereign.class));
        cards.add(new SetCardInfo("Artful Maneuver", 4, Rarity.COMMON, mage.cards.a.ArtfulManeuver.class));
        cards.add(new SetCardInfo("Assault Formation", 173, Rarity.RARE, mage.cards.a.AssaultFormation.class));
        cards.add(new SetCardInfo("Atarka Beastbreaker", 174, Rarity.COMMON, mage.cards.a.AtarkaBeastbreaker.class));
        cards.add(new SetCardInfo("Atarka Efreet", 128, Rarity.COMMON, mage.cards.a.AtarkaEfreet.class));
        cards.add(new SetCardInfo("Atarka Monument", 235, Rarity.UNCOMMON, mage.cards.a.AtarkaMonument.class));
        cards.add(new SetCardInfo("Atarka Pummeler", 129, Rarity.UNCOMMON, mage.cards.a.AtarkaPummeler.class));
        cards.add(new SetCardInfo("Atarka's Command", 213, Rarity.RARE, mage.cards.a.AtarkasCommand.class));
        cards.add(new SetCardInfo("Avatar of the Resolute", 175, Rarity.RARE, mage.cards.a.AvatarOfTheResolute.class));
        cards.add(new SetCardInfo("Aven Sunstriker", 5, Rarity.UNCOMMON, mage.cards.a.AvenSunstriker.class));
        cards.add(new SetCardInfo("Aven Tactician", 6, Rarity.COMMON, mage.cards.a.AvenTactician.class));
        cards.add(new SetCardInfo("Battle Mastery", 7, Rarity.UNCOMMON, mage.cards.b.BattleMastery.class));
        cards.add(new SetCardInfo("Belltoll Dragon", 46, Rarity.UNCOMMON, mage.cards.b.BelltollDragon.class));
        cards.add(new SetCardInfo("Berserkers' Onslaught", 130, Rarity.RARE, mage.cards.b.BerserkersOnslaught.class));
        cards.add(new SetCardInfo("Blessed Reincarnation", 47, Rarity.RARE, mage.cards.b.BlessedReincarnation.class));
        cards.add(new SetCardInfo("Blood-Chin Fanatic", 88, Rarity.RARE, mage.cards.b.BloodChinFanatic.class));
        cards.add(new SetCardInfo("Blood-Chin Rager", 89, Rarity.UNCOMMON, mage.cards.b.BloodChinRager.class));
        cards.add(new SetCardInfo("Boltwing Marauder", 214, Rarity.RARE, mage.cards.b.BoltwingMarauder.class));
        cards.add(new SetCardInfo("Butcher's Glee", 90, Rarity.COMMON, mage.cards.b.ButchersGlee.class));
        cards.add(new SetCardInfo("Center Soul", 8, Rarity.COMMON, mage.cards.c.CenterSoul.class));
        cards.add(new SetCardInfo("Champion of Arashin", 9, Rarity.COMMON, mage.cards.c.ChampionOfArashin.class));
        cards.add(new SetCardInfo("Circle of Elders", 176, Rarity.UNCOMMON, mage.cards.c.CircleOfElders.class));
        cards.add(new SetCardInfo("Clone Legion", 48, Rarity.MYTHIC, mage.cards.c.CloneLegion.class));
        cards.add(new SetCardInfo("Coat with Venom", 91, Rarity.COMMON, mage.cards.c.CoatWithVenom.class));
        cards.add(new SetCardInfo("Collected Company", 177, Rarity.RARE, mage.cards.c.CollectedCompany.class));
        cards.add(new SetCardInfo("Colossodon Yearling", 178, Rarity.COMMON, mage.cards.c.ColossodonYearling.class));
        cards.add(new SetCardInfo("Commune with Lava", 131, Rarity.RARE, mage.cards.c.CommuneWithLava.class));
        cards.add(new SetCardInfo("Conifer Strider", 179, Rarity.COMMON, mage.cards.c.ConiferStrider.class));
        cards.add(new SetCardInfo("Contradict", 49, Rarity.COMMON, mage.cards.c.Contradict.class));
        cards.add(new SetCardInfo("Corpseweft", 92, Rarity.RARE, mage.cards.c.Corpseweft.class));
        cards.add(new SetCardInfo("Crater Elemental", 132, Rarity.RARE, mage.cards.c.CraterElemental.class));
        cards.add(new SetCardInfo("Cunning Breezedancer", 215, Rarity.UNCOMMON, mage.cards.c.CunningBreezedancer.class));
        cards.add(new SetCardInfo("Custodian of the Trove", 236, Rarity.COMMON, mage.cards.c.CustodianOfTheTrove.class));
        cards.add(new SetCardInfo("Damnable Pact", 93, Rarity.RARE, mage.cards.d.DamnablePact.class));
        cards.add(new SetCardInfo("Dance of the Skywise", 50, Rarity.UNCOMMON, mage.cards.d.DanceOfTheSkywise.class));
        cards.add(new SetCardInfo("Deadly Wanderings", 94, Rarity.UNCOMMON, mage.cards.d.DeadlyWanderings.class));
        cards.add(new SetCardInfo("Deathbringer Regent", 96, Rarity.RARE, mage.cards.d.DeathbringerRegent.class));
        cards.add(new SetCardInfo("Deathmist Raptor", 180, Rarity.MYTHIC, mage.cards.d.DeathmistRaptor.class));
        cards.add(new SetCardInfo("Death Wind", 95, Rarity.UNCOMMON, mage.cards.d.DeathWind.class));
        cards.add(new SetCardInfo("Defeat", 97, Rarity.COMMON, mage.cards.d.Defeat.class));
        cards.add(new SetCardInfo("Den Protector", 181, Rarity.RARE, mage.cards.d.DenProtector.class));
        cards.add(new SetCardInfo("Descent of the Dragons", 133, Rarity.MYTHIC, mage.cards.d.DescentOfTheDragons.class));
        cards.add(new SetCardInfo("Dirgur Nemesis", 51, Rarity.COMMON, mage.cards.d.DirgurNemesis.class));
        cards.add(new SetCardInfo("Display of Dominance", 182, Rarity.UNCOMMON, mage.cards.d.DisplayOfDominance.class));
        cards.add(new SetCardInfo("Draconic Roar", 134, Rarity.UNCOMMON, mage.cards.d.DraconicRoar.class));
        cards.add(new SetCardInfo("Dragon Fodder", 135, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dragon Hunter", 10, Rarity.UNCOMMON, mage.cards.d.DragonHunter.class));
        cards.add(new SetCardInfo("Dragonloft Idol", 237, Rarity.UNCOMMON, mage.cards.d.DragonloftIdol.class));
        cards.add(new SetCardInfo("Dragonlord Atarka", 216, Rarity.MYTHIC, mage.cards.d.DragonlordAtarka.class));
        cards.add(new SetCardInfo("Dragonlord Dromoka", 217, Rarity.MYTHIC, mage.cards.d.DragonlordDromoka.class));
        cards.add(new SetCardInfo("Dragonlord Kolaghan", 218, Rarity.MYTHIC, mage.cards.d.DragonlordKolaghan.class));
        cards.add(new SetCardInfo("Dragonlord Ojutai", 219, Rarity.MYTHIC, mage.cards.d.DragonlordOjutai.class));
        cards.add(new SetCardInfo("Dragonlord Silumgar", 220, Rarity.MYTHIC, mage.cards.d.DragonlordSilumgar.class));
        cards.add(new SetCardInfo("Dragonlord's Prerogative", 52, Rarity.RARE, mage.cards.d.DragonlordsPrerogative.class));
        cards.add(new SetCardInfo("Dragonlord's Servant", 138, Rarity.UNCOMMON, mage.cards.d.DragonlordsServant.class));
        cards.add(new SetCardInfo("Dragon-Scarred Bear", 183, Rarity.COMMON, mage.cards.d.DragonScarredBear.class));
        cards.add(new SetCardInfo("Dragon's Eye Sentry", 11, Rarity.COMMON, mage.cards.d.DragonsEyeSentry.class));
        cards.add(new SetCardInfo("Dragon Tempest", 136, Rarity.RARE, mage.cards.d.DragonTempest.class));
        cards.add(new SetCardInfo("Dragon Whisperer", 137, Rarity.MYTHIC, mage.cards.d.DragonWhisperer.class));
        cards.add(new SetCardInfo("Dromoka Captain", 12, Rarity.UNCOMMON, mage.cards.d.DromokaCaptain.class));
        cards.add(new SetCardInfo("Dromoka Dunecaster", 13, Rarity.COMMON, mage.cards.d.DromokaDunecaster.class));
        cards.add(new SetCardInfo("Dromoka Monument", 238, Rarity.UNCOMMON, mage.cards.d.DromokaMonument.class));
        cards.add(new SetCardInfo("Dromoka's Command", 221, Rarity.RARE, mage.cards.d.DromokasCommand.class));
        cards.add(new SetCardInfo("Dromoka's Gift", 184, Rarity.UNCOMMON, mage.cards.d.DromokasGift.class));
        cards.add(new SetCardInfo("Dromoka Warrior", 14, Rarity.COMMON, mage.cards.d.DromokaWarrior.class));
        cards.add(new SetCardInfo("Duress", 98, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Dutiful Attendant", 99, Rarity.COMMON, mage.cards.d.DutifulAttendant.class));
        cards.add(new SetCardInfo("Echoes of the Kin Tree", 15, Rarity.UNCOMMON, mage.cards.e.EchoesOfTheKinTree.class));
        cards.add(new SetCardInfo("Elusive Spellfist", 53, Rarity.COMMON, mage.cards.e.ElusiveSpellfist.class));
        cards.add(new SetCardInfo("Encase in Ice", 54, Rarity.UNCOMMON, mage.cards.e.EncaseInIce.class));
        cards.add(new SetCardInfo("Enduring Scalelord", 222, Rarity.UNCOMMON, mage.cards.e.EnduringScalelord.class));
        cards.add(new SetCardInfo("Enduring Victory", 16, Rarity.COMMON, mage.cards.e.EnduringVictory.class));
        cards.add(new SetCardInfo("Epic Confrontation", 185, Rarity.COMMON, mage.cards.e.EpicConfrontation.class));
        cards.add(new SetCardInfo("Evolving Wilds", 248, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Explosive Vegetation", 186, Rarity.UNCOMMON, mage.cards.e.ExplosiveVegetation.class));
        cards.add(new SetCardInfo("Fate Forgotten", 17, Rarity.COMMON, mage.cards.f.FateForgotten.class));
        cards.add(new SetCardInfo("Flatten", 100, Rarity.COMMON, mage.cards.f.Flatten.class));
        cards.add(new SetCardInfo("Foe-Razer Regent", 187, Rarity.RARE, mage.cards.f.FoeRazerRegent.class));
        cards.add(new SetCardInfo("Forest", 262, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 263, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 264, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foul Renewal", 101, Rarity.RARE, mage.cards.f.FoulRenewal.class));
        cards.add(new SetCardInfo("Foul-Tongue Invocation", 102, Rarity.UNCOMMON, mage.cards.f.FoulTongueInvocation.class));
        cards.add(new SetCardInfo("Foul-Tongue Shriek", 103, Rarity.COMMON, mage.cards.f.FoulTongueShriek.class));
        cards.add(new SetCardInfo("Gate Smasher", 239, Rarity.UNCOMMON, mage.cards.g.GateSmasher.class));
        cards.add(new SetCardInfo("Glade Watcher", 188, Rarity.COMMON, mage.cards.g.GladeWatcher.class));
        cards.add(new SetCardInfo("Glaring Aegis", 18, Rarity.COMMON, mage.cards.g.GlaringAegis.class));
        cards.add(new SetCardInfo("Gleam of Authority", 19, Rarity.RARE, mage.cards.g.GleamOfAuthority.class));
        cards.add(new SetCardInfo("Glint", 55, Rarity.COMMON, mage.cards.g.Glint.class));
        cards.add(new SetCardInfo("Graceblade Artisan", 20, Rarity.UNCOMMON, mage.cards.g.GracebladeArtisan.class));
        cards.add(new SetCardInfo("Gravepurge", 104, Rarity.COMMON, mage.cards.g.Gravepurge.class));
        cards.add(new SetCardInfo("Great Teacher's Decree", 21, Rarity.UNCOMMON, mage.cards.g.GreatTeachersDecree.class));
        cards.add(new SetCardInfo("Guardian Shield-Bearer", 189, Rarity.COMMON, mage.cards.g.GuardianShieldBearer.class));
        cards.add(new SetCardInfo("Gudul Lurker", 56, Rarity.UNCOMMON, mage.cards.g.GudulLurker.class));
        cards.add(new SetCardInfo("Gurmag Drowner", 57, Rarity.COMMON, mage.cards.g.GurmagDrowner.class));
        cards.add(new SetCardInfo("Hand of Silumgar", 105, Rarity.COMMON, mage.cards.h.HandOfSilumgar.class));
        cards.add(new SetCardInfo("Harbinger of the Hunt", 223, Rarity.RARE, mage.cards.h.HarbingerOfTheHunt.class));
        cards.add(new SetCardInfo("Hardened Berserker", 139, Rarity.COMMON, mage.cards.h.HardenedBerserker.class));
        cards.add(new SetCardInfo("Haven of the Spirit Dragon", 249, Rarity.RARE, mage.cards.h.HavenOfTheSpiritDragon.class));
        cards.add(new SetCardInfo("Hedonist's Trove", 106, Rarity.RARE, mage.cards.h.HedonistsTrove.class));
        cards.add(new SetCardInfo("Herald of Dromoka", 22, Rarity.COMMON, mage.cards.h.HeraldOfDromoka.class));
        cards.add(new SetCardInfo("Herdchaser Dragon", 190, Rarity.UNCOMMON, mage.cards.h.HerdchaserDragon.class));
        cards.add(new SetCardInfo("Hidden Dragonslayer", 23, Rarity.RARE, mage.cards.h.HiddenDragonslayer.class));
        cards.add(new SetCardInfo("Icefall Regent", 58, Rarity.RARE, mage.cards.i.IcefallRegent.class));
        cards.add(new SetCardInfo("Illusory Gains", 59, Rarity.RARE, mage.cards.i.IllusoryGains.class));
        cards.add(new SetCardInfo("Impact Tremors", 140, Rarity.COMMON, mage.cards.i.ImpactTremors.class));
        cards.add(new SetCardInfo("Inspiring Call", 191, Rarity.UNCOMMON, mage.cards.i.InspiringCall.class));
        cards.add(new SetCardInfo("Ire Shaman", 141, Rarity.RARE, mage.cards.i.IreShaman.class));
        cards.add(new SetCardInfo("Island", 253, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keeper of the Lens", 240, Rarity.COMMON, mage.cards.k.KeeperOfTheLens.class));
        cards.add(new SetCardInfo("Kindled Fury", 142, Rarity.COMMON, mage.cards.k.KindledFury.class));
        cards.add(new SetCardInfo("Kolaghan Aspirant", 143, Rarity.COMMON, mage.cards.k.KolaghanAspirant.class));
        cards.add(new SetCardInfo("Kolaghan Forerunners", 144, Rarity.UNCOMMON, mage.cards.k.KolaghanForerunners.class));
        cards.add(new SetCardInfo("Kolaghan Monument", 241, Rarity.UNCOMMON, mage.cards.k.KolaghanMonument.class));
        cards.add(new SetCardInfo("Kolaghan's Command", 224, Rarity.RARE, mage.cards.k.KolaghansCommand.class));
        cards.add(new SetCardInfo("Kolaghan Skirmisher", 107, Rarity.COMMON, mage.cards.k.KolaghanSkirmisher.class));
        cards.add(new SetCardInfo("Kolaghan Stormsinger", 145, Rarity.COMMON, mage.cards.k.KolaghanStormsinger.class));
        cards.add(new SetCardInfo("Learn from the Past", 60, Rarity.UNCOMMON, mage.cards.l.LearnFromThePast.class));
        cards.add(new SetCardInfo("Lightning Berserker", 146, Rarity.UNCOMMON, mage.cards.l.LightningBerserker.class));
        cards.add(new SetCardInfo("Lightwalker", 24, Rarity.COMMON, mage.cards.l.Lightwalker.class));
        cards.add(new SetCardInfo("Living Lore", 61, Rarity.RARE, mage.cards.l.LivingLore.class));
        cards.add(new SetCardInfo("Lose Calm", 147, Rarity.COMMON, mage.cards.l.LoseCalm.class));
        cards.add(new SetCardInfo("Lurking Arynx", 192, Rarity.UNCOMMON, mage.cards.l.LurkingArynx.class));
        cards.add(new SetCardInfo("Magmatic Chasm", 148, Rarity.COMMON, mage.cards.m.MagmaticChasm.class));
        cards.add(new SetCardInfo("Marang River Skeleton", 108, Rarity.UNCOMMON, mage.cards.m.MarangRiverSkeleton.class));
        cards.add(new SetCardInfo("Marsh Hulk", 109, Rarity.COMMON, mage.cards.m.MarshHulk.class));
        cards.add(new SetCardInfo("Mind Rot", 110, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Minister of Pain", 111, Rarity.UNCOMMON, mage.cards.m.MinisterOfPain.class));
        cards.add(new SetCardInfo("Mirror Mockery", 62, Rarity.RARE, mage.cards.m.MirrorMockery.class));
        cards.add(new SetCardInfo("Misthoof Kirin", 25, Rarity.COMMON, mage.cards.m.MisthoofKirin.class));
        cards.add(new SetCardInfo("Monastery Loremaster", 63, Rarity.COMMON, mage.cards.m.MonasteryLoremaster.class));
        cards.add(new SetCardInfo("Mountain", 259, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 260, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 261, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Meditation", 64, Rarity.COMMON, mage.cards.m.MysticMeditation.class));
        cards.add(new SetCardInfo("Myth Realized", 26, Rarity.RARE, mage.cards.m.MythRealized.class));
        cards.add(new SetCardInfo("Narset Transcendent", 225, Rarity.MYTHIC, mage.cards.n.NarsetTranscendent.class));
        cards.add(new SetCardInfo("Naturalize", 193, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Necromaster Dragon", 226, Rarity.RARE, mage.cards.n.NecromasterDragon.class));
        cards.add(new SetCardInfo("Negate", 65, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Obscuring Aether", 194, Rarity.RARE, mage.cards.o.ObscuringAether.class));
        cards.add(new SetCardInfo("Ojutai Exemplars", 27, Rarity.MYTHIC, mage.cards.o.OjutaiExemplars.class));
        cards.add(new SetCardInfo("Ojutai Interceptor", 66, Rarity.COMMON, mage.cards.o.OjutaiInterceptor.class));
        cards.add(new SetCardInfo("Ojutai Monument", 242, Rarity.UNCOMMON, mage.cards.o.OjutaiMonument.class));
        cards.add(new SetCardInfo("Ojutai's Breath", 67, Rarity.COMMON, mage.cards.o.OjutaisBreath.class));
        cards.add(new SetCardInfo("Ojutai's Command", 227, Rarity.RARE, mage.cards.o.OjutaisCommand.class));
        cards.add(new SetCardInfo("Ojutai's Summons", 68, Rarity.COMMON, mage.cards.o.OjutaisSummons.class));
        cards.add(new SetCardInfo("Orator of Ojutai", 28, Rarity.UNCOMMON, mage.cards.o.OratorOfOjutai.class));
        cards.add(new SetCardInfo("Pacifism", 29, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Palace Familiar", 69, Rarity.COMMON, mage.cards.p.PalaceFamiliar.class));
        cards.add(new SetCardInfo("Pinion Feast", 195, Rarity.COMMON, mage.cards.p.PinionFeast.class));
        cards.add(new SetCardInfo("Pitiless Horde", 112, Rarity.RARE, mage.cards.p.PitilessHorde.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Press the Advantage", 196, Rarity.UNCOMMON, mage.cards.p.PressTheAdvantage.class));
        cards.add(new SetCardInfo("Pristine Skywise", 228, Rarity.RARE, mage.cards.p.PristineSkywise.class));
        cards.add(new SetCardInfo("Profaner of the Dead", 70, Rarity.RARE, mage.cards.p.ProfanerOfTheDead.class));
        cards.add(new SetCardInfo("Profound Journey", 30, Rarity.RARE, mage.cards.p.ProfoundJourney.class));
        cards.add(new SetCardInfo("Qal Sisma Behemoth", 149, Rarity.UNCOMMON, mage.cards.q.QalSismaBehemoth.class));
        cards.add(new SetCardInfo("Qarsi Deceiver", 71, Rarity.UNCOMMON, mage.cards.q.QarsiDeceiver.class));
        cards.add(new SetCardInfo("Qarsi Sadist", 113, Rarity.COMMON, mage.cards.q.QarsiSadist.class));
        cards.add(new SetCardInfo("Radiant Purge", 31, Rarity.RARE, mage.cards.r.RadiantPurge.class));
        cards.add(new SetCardInfo("Rakshasa Gravecaller", 114, Rarity.UNCOMMON, mage.cards.r.RakshasaGravecaller.class));
        cards.add(new SetCardInfo("Reckless Imp", 115, Rarity.COMMON, mage.cards.r.RecklessImp.class));
        cards.add(new SetCardInfo("Reduce in Stature", 72, Rarity.COMMON, mage.cards.r.ReduceInStature.class));
        cards.add(new SetCardInfo("Rending Volley", 150, Rarity.UNCOMMON, mage.cards.r.RendingVolley.class));
        cards.add(new SetCardInfo("Resupply", 32, Rarity.COMMON, mage.cards.r.Resupply.class));
        cards.add(new SetCardInfo("Revealing Wind", 197, Rarity.COMMON, mage.cards.r.RevealingWind.class));
        cards.add(new SetCardInfo("Risen Executioner", 116, Rarity.MYTHIC, mage.cards.r.RisenExecutioner.class));
        cards.add(new SetCardInfo("Roast", 151, Rarity.UNCOMMON, mage.cards.r.Roast.class));
        cards.add(new SetCardInfo("Ruthless Deathfang", 229, Rarity.UNCOMMON, mage.cards.r.RuthlessDeathfang.class));
        cards.add(new SetCardInfo("Sabertooth Outrider", 152, Rarity.COMMON, mage.cards.s.SabertoothOutrider.class));
        cards.add(new SetCardInfo("Salt Road Ambushers", 198, Rarity.UNCOMMON, mage.cards.s.SaltRoadAmbushers.class));
        cards.add(new SetCardInfo("Salt Road Quartermasters", 199, Rarity.UNCOMMON, mage.cards.s.SaltRoadQuartermasters.class));
        cards.add(new SetCardInfo("Sandcrafter Mage", 33, Rarity.COMMON, mage.cards.s.SandcrafterMage.class));
        cards.add(new SetCardInfo("Sandsteppe Scavenger", 200, Rarity.COMMON, mage.cards.s.SandsteppeScavenger.class));
        cards.add(new SetCardInfo("Sandstorm Charger", 34, Rarity.COMMON, mage.cards.s.SandstormCharger.class));
        cards.add(new SetCardInfo("Sarkhan's Rage", 153, Rarity.COMMON, mage.cards.s.SarkhansRage.class));
        cards.add(new SetCardInfo("Sarkhan's Triumph", 154, Rarity.UNCOMMON, mage.cards.s.SarkhansTriumph.class));
        cards.add(new SetCardInfo("Sarkhan Unbroken", 230, Rarity.MYTHIC, mage.cards.s.SarkhanUnbroken.class));
        cards.add(new SetCardInfo("Savage Ventmaw", 231, Rarity.UNCOMMON, mage.cards.s.SavageVentmaw.class));
        cards.add(new SetCardInfo("Scale Blessing", 35, Rarity.UNCOMMON, mage.cards.s.ScaleBlessing.class));
        cards.add(new SetCardInfo("Scaleguard Sentinels", 201, Rarity.UNCOMMON, mage.cards.s.ScaleguardSentinels.class));
        cards.add(new SetCardInfo("Scion of Ugin", 1, Rarity.UNCOMMON, mage.cards.s.ScionOfUgin.class));
        cards.add(new SetCardInfo("Screamreach Brawler", 155, Rarity.COMMON, mage.cards.s.ScreamreachBrawler.class));
        cards.add(new SetCardInfo("Secure the Wastes", 36, Rarity.RARE, mage.cards.s.SecureTheWastes.class));
        cards.add(new SetCardInfo("Segmented Krotiq", 202, Rarity.COMMON, mage.cards.s.SegmentedKrotiq.class));
        cards.add(new SetCardInfo("Seismic Rupture", 156, Rarity.UNCOMMON, mage.cards.s.SeismicRupture.class));
        cards.add(new SetCardInfo("Self-Inflicted Wound", 117, Rarity.UNCOMMON, mage.cards.s.SelfInflictedWound.class));
        cards.add(new SetCardInfo("Servant of the Scale", 203, Rarity.COMMON, mage.cards.s.ServantOfTheScale.class));
        cards.add(new SetCardInfo("Shaman of Forgotten Ways", 204, Rarity.MYTHIC, mage.cards.s.ShamanOfForgottenWays.class));
        cards.add(new SetCardInfo("Shambling Goblin", 118, Rarity.COMMON, mage.cards.s.ShamblingGoblin.class));
        cards.add(new SetCardInfo("Shape the Sands", 205, Rarity.COMMON, mage.cards.s.ShapeTheSands.class));
        cards.add(new SetCardInfo("Sheltered Aerie", 206, Rarity.COMMON, mage.cards.s.ShelteredAerie.class));
        cards.add(new SetCardInfo("Shieldhide Dragon", 37, Rarity.UNCOMMON, mage.cards.s.ShieldhideDragon.class));
        cards.add(new SetCardInfo("Shorecrasher Elemental", 73, Rarity.MYTHIC, mage.cards.s.ShorecrasherElemental.class));
        cards.add(new SetCardInfo("Sibsig Icebreakers", 119, Rarity.COMMON, mage.cards.s.SibsigIcebreakers.class));
        cards.add(new SetCardInfo("Sidisi's Faithful", 74, Rarity.COMMON, mage.cards.s.SidisisFaithful.class));
        cards.add(new SetCardInfo("Sidisi, Undead Vizier", 120, Rarity.RARE, mage.cards.s.SidisiUndeadVizier.class));
        cards.add(new SetCardInfo("Sight Beyond Sight", 75, Rarity.UNCOMMON, mage.cards.s.SightBeyondSight.class));
        cards.add(new SetCardInfo("Sight of the Scalelords", 207, Rarity.UNCOMMON, mage.cards.s.SightOfTheScalelords.class));
        cards.add(new SetCardInfo("Silkwrap", 38, Rarity.UNCOMMON, mage.cards.s.Silkwrap.class));
        cards.add(new SetCardInfo("Silumgar Assassin", 121, Rarity.RARE, mage.cards.s.SilumgarAssassin.class));
        cards.add(new SetCardInfo("Silumgar Butcher", 122, Rarity.COMMON, mage.cards.s.SilumgarButcher.class));
        cards.add(new SetCardInfo("Silumgar Monument", 243, Rarity.UNCOMMON, mage.cards.s.SilumgarMonument.class));
        cards.add(new SetCardInfo("Silumgar's Command", 232, Rarity.RARE, mage.cards.s.SilumgarsCommand.class));
        cards.add(new SetCardInfo("Silumgar Sorcerer", 76, Rarity.UNCOMMON, mage.cards.s.SilumgarSorcerer.class));
        cards.add(new SetCardInfo("Silumgar Spell-Eater", 77, Rarity.UNCOMMON, mage.cards.s.SilumgarSpellEater.class));
        cards.add(new SetCardInfo("Silumgar's Scorn", 78, Rarity.UNCOMMON, mage.cards.s.SilumgarsScorn.class));
        cards.add(new SetCardInfo("Skywise Teachings", 79, Rarity.UNCOMMON, mage.cards.s.SkywiseTeachings.class));
        cards.add(new SetCardInfo("Spidersilk Net", 244, Rarity.COMMON, mage.cards.s.SpidersilkNet.class));
        cards.add(new SetCardInfo("Sprinting Warbrute", 157, Rarity.COMMON, mage.cards.s.SprintingWarbrute.class));
        cards.add(new SetCardInfo("Stampeding Elk Herd", 208, Rarity.COMMON, mage.cards.s.StampedingElkHerd.class));
        cards.add(new SetCardInfo("Stormcrag Elemental", 158, Rarity.UNCOMMON, mage.cards.s.StormcragElemental.class));
        cards.add(new SetCardInfo("Stormrider Rig", 245, Rarity.UNCOMMON, mage.cards.s.StormriderRig.class));
        cards.add(new SetCardInfo("Stormwing Dragon", 159, Rarity.UNCOMMON, mage.cards.s.StormwingDragon.class));
        cards.add(new SetCardInfo("Stratus Dancer", 80, Rarity.RARE, mage.cards.s.StratusDancer.class));
        cards.add(new SetCardInfo("Strongarm Monk", 39, Rarity.UNCOMMON, mage.cards.s.StrongarmMonk.class));
        cards.add(new SetCardInfo("Student of Ojutai", 40, Rarity.COMMON, mage.cards.s.StudentOfOjutai.class));
        cards.add(new SetCardInfo("Summit Prowler", 160, Rarity.COMMON, mage.cards.s.SummitProwler.class));
        cards.add(new SetCardInfo("Sunbringer's Touch", 209, Rarity.RARE, mage.cards.s.SunbringersTouch.class));
        cards.add(new SetCardInfo("Sunscorch Regent", 41, Rarity.RARE, mage.cards.s.SunscorchRegent.class));
        cards.add(new SetCardInfo("Surge of Righteousness", 42, Rarity.UNCOMMON, mage.cards.s.SurgeOfRighteousness.class));
        cards.add(new SetCardInfo("Surrak, the Hunt Caller", 210, Rarity.RARE, mage.cards.s.SurrakTheHuntCaller.class));
        cards.add(new SetCardInfo("Swamp", 256, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 257, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swift Warkite", 233, Rarity.UNCOMMON, mage.cards.s.SwiftWarkite.class));
        cards.add(new SetCardInfo("Taigam's Strike", 81, Rarity.COMMON, mage.cards.t.TaigamsStrike.class));
        cards.add(new SetCardInfo("Tail Slash", 161, Rarity.COMMON, mage.cards.t.TailSlash.class));
        cards.add(new SetCardInfo("Tapestry of the Ages", 246, Rarity.UNCOMMON, mage.cards.t.TapestryOfTheAges.class));
        cards.add(new SetCardInfo("Territorial Roc", 43, Rarity.COMMON, mage.cards.t.TerritorialRoc.class));
        cards.add(new SetCardInfo("Thunderbreak Regent", 162, Rarity.RARE, mage.cards.t.ThunderbreakRegent.class));
        cards.add(new SetCardInfo("Tormenting Voice", 163, Rarity.COMMON, mage.cards.t.TormentingVoice.class));
        cards.add(new SetCardInfo("Tread Upon", 211, Rarity.COMMON, mage.cards.t.TreadUpon.class));
        cards.add(new SetCardInfo("Twin Bolt", 164, Rarity.COMMON, mage.cards.t.TwinBolt.class));
        cards.add(new SetCardInfo("Ukud Cobra", 123, Rarity.UNCOMMON, mage.cards.u.UkudCobra.class));
        cards.add(new SetCardInfo("Ultimate Price", 124, Rarity.UNCOMMON, mage.cards.u.UltimatePrice.class));
        cards.add(new SetCardInfo("Updraft Elemental", 82, Rarity.COMMON, mage.cards.u.UpdraftElemental.class));
        cards.add(new SetCardInfo("Vandalize", 165, Rarity.COMMON, mage.cards.v.Vandalize.class));
        cards.add(new SetCardInfo("Vial of Dragonfire", 247, Rarity.COMMON, mage.cards.v.VialOfDragonfire.class));
        cards.add(new SetCardInfo("Virulent Plague", 125, Rarity.UNCOMMON, mage.cards.v.VirulentPlague.class));
        cards.add(new SetCardInfo("Void Squall", 83, Rarity.UNCOMMON, mage.cards.v.VoidSquall.class));
        cards.add(new SetCardInfo("Volcanic Rush", 166, Rarity.COMMON, mage.cards.v.VolcanicRush.class));
        cards.add(new SetCardInfo("Volcanic Vision", 167, Rarity.RARE, mage.cards.v.VolcanicVision.class));
        cards.add(new SetCardInfo("Vulturous Aven", 126, Rarity.COMMON, mage.cards.v.VulturousAven.class));
        cards.add(new SetCardInfo("Wandering Tombshell", 127, Rarity.COMMON, mage.cards.w.WanderingTombshell.class));
        cards.add(new SetCardInfo("Warbringer", 168, Rarity.UNCOMMON, mage.cards.w.Warbringer.class));
        cards.add(new SetCardInfo("Youthful Scholar", 84, Rarity.UNCOMMON, mage.cards.y.YouthfulScholar.class));
        cards.add(new SetCardInfo("Zephyr Scribe", 85, Rarity.COMMON, mage.cards.z.ZephyrScribe.class));
        cards.add(new SetCardInfo("Zurgo Bellstriker", 169, Rarity.RARE, mage.cards.z.ZurgoBellstriker.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new DragonsOfTarkirCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/dtk.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class DragonsOfTarkirCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "119", "148", "8", "91", "145", "40", "118", "140", "18", "113", "147", "9", "104", "135", "6", "110", "148", "32", "105", "139", "43", "109", "142", "34", "119", "155", "24", "127", "166", "14", "91", "143", "13", "99", "140", "40", "107", "145", "8", "113", "152", "9", "118", "135", "18", "109", "147", "43", "110", "142", "6", "105", "155", "32", "104", "139", "34", "107", "143", "24", "99", "166", "14", "127", "152", "13");
    private final CardRun commonB = new CardRun(true, "69", "200", "82", "188", "63", "178", "57", "179", "45", "174", "64", "211", "65", "183", "85", "206", "49", "195", "74", "203", "51", "183", "69", "179", "64", "171", "82", "174", "45", "200", "65", "211", "49", "188", "57", "178", "51", "206", "63", "203", "74", "171", "85", "195", "64", "200", "69", "174", "57", "211", "82", "183", "45", "178", "65", "188", "51", "203", "49", "171", "63", "179", "74", "206", "85", "195");
    private final CardRun commonC1 = new CardRun(true, "72", "11", "189", "153", "247", "90", "66", "22", "202", "161", "97", "72", "16", "185", "163", "240", "115", "53", "4", "170", "165", "103", "66", "11", "197", "153", "247", "90", "55", "29", "189", "128", "100", "53", "22", "170", "163", "240", "115", "44", "16", "202", "161", "97", "55", "4", "197", "165", "17", "100", "44", "29", "185", "128", "103");
    private final CardRun commonC2 = new CardRun(true, "157", "208", "234", "98", "25", "67", "244", "164", "81", "122", "248", "33", "98", "205", "68", "234", "157", "193", "122", "205", "67", "236", "160", "208", "244", "126", "25", "68", "248", "164", "193", "98", "234", "33", "67", "157", "208", "236", "126", "17", "25", "81", "160", "244", "164", "126", "205", "68", "248", "160", "193", "122", "236", "33", "81");
    private final CardRun uncommonA = new CardRun(true, "222", "239", "182", "75", "229", "7", "154", "50", "233", "241", "78", "1", "60", "123", "12", "196", "215", "125", "129", "191", "114", "182", "15", "154", "233", "75", "7", "241", "222", "239", "50", "78", "215", "191", "196", "182", "229", "1", "239", "123", "114", "12", "15", "129", "60", "229", "78", "50", "154", "241", "125", "75", "222", "7", "233", "129", "60", "123", "215", "191", "1", "125", "114", "196", "12", "15");
    private final CardRun uncommonB = new CardRun(true, "156", "199", "168", "186", "151", "176", "150", "231", "146", "190", "158", "172", "134", "207", "159", "184", "156", "201", "168", "192", "138", "198", "144", "199", "149", "186", "146", "190", "159", "231", "134", "176", "138", "207", "151", "172", "150", "184", "156", "201", "149", "198", "158", "186", "144", "192", "168", "199", "146", "192", "158", "231", "151", "176", "150", "190", "159", "172", "144", "207", "138", "198", "134", "201", "149", "184");
    private final CardRun uncommonC = new CardRun(true, "28", "77", "35", "79", "38", "83", "39", "56", "42", "76", "37", "54", "20", "79", "38", "71", "21", "84", "10", "76", "35", "46", "42", "77", "28", "83", "37", "56", "39", "54", "20", "84", "21", "46", "35", "79", "28", "71", "38", "83", "10", "76", "42", "56", "39", "77", "37", "46", "20", "54", "21", "84", "10", "71");
    private final CardRun uncommonD = new CardRun(true, "86", "243", "111", "235", "117", "246", "124", "245", "102", "5", "94", "95", "89", "237", "87", "238", "108", "242", "124", "243", "86", "95", "111", "5", "102", "246", "94", "245", "117", "238", "87", "235", "86", "242", "89", "237", "108", "243", "111", "246", "102", "245", "94", "124", "117", "5", "89", "235", "95", "238", "87", "242", "108", "237");
    private final CardRun rare = new CardRun(true, "137", "181", "31", "212", "19", "2", "106", "173", "216", "47", "210", "58", "92", "112", "213", "23", "27", "36", "175", "221", "93", "3", "59", "70", "218", "214", "26", "187", "249", "177", "223", "96", "116", "61", "88", "132", "221", "30", "131", "120", "220", "224", "101", "52", "62", "41", "136", "223", "73", "169", "92", "167", "226", "130", "58", "194", "230", "141", "224", "80", "181", "2", "209", "227", "180", "59", "121", "173", "162", "226", "19", "210", "217", "31", "228", "106", "61", "249", "175", "52", "48", "23", "47", "93", "3", "232", "132", "62", "219", "177", "112", "227", "26", "36", "96", "70", "133", "136", "214", "120", "131", "41", "228", "30", "225", "101", "209", "212", "141", "88", "167", "80", "204", "232", "169", "194", "130", "121", "213", "162", "187");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264");

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
