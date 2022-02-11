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
 * @author North
 */
public final class AvacynRestored extends ExpansionSet {

    private static final AvacynRestored instance = new AvacynRestored();

    public static AvacynRestored getInstance() {
        return instance;
    }

    private AvacynRestored() {
        super("Avacyn Restored", "AVR", ExpansionSet.buildDate(2012, 4, 4), SetType.EXPANSION);
        this.blockName = "Innistrad";
        this.parentSet = Innistrad.getInstance();
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Abundant Growth", 167, Rarity.COMMON, mage.cards.a.AbundantGrowth.class));
        cards.add(new SetCardInfo("Aggravate", 125, Rarity.UNCOMMON, mage.cards.a.Aggravate.class));
        cards.add(new SetCardInfo("Alchemist's Apprentice", 42, Rarity.COMMON, mage.cards.a.AlchemistsApprentice.class));
        cards.add(new SetCardInfo("Alchemist's Refuge", 225, Rarity.RARE, mage.cards.a.AlchemistsRefuge.class));
        cards.add(new SetCardInfo("Amass the Components", 43, Rarity.COMMON, mage.cards.a.AmassTheComponents.class));
        cards.add(new SetCardInfo("Angelic Armaments", 212, Rarity.UNCOMMON, mage.cards.a.AngelicArmaments.class));
        cards.add(new SetCardInfo("Angelic Wall", 4, Rarity.COMMON, mage.cards.a.AngelicWall.class));
        cards.add(new SetCardInfo("Angel of Glory's Rise", 1, Rarity.RARE, mage.cards.a.AngelOfGlorysRise.class));
        cards.add(new SetCardInfo("Angel of Jubilation", 2, Rarity.RARE, mage.cards.a.AngelOfJubilation.class));
        cards.add(new SetCardInfo("Angel's Mercy", 3, Rarity.COMMON, mage.cards.a.AngelsMercy.class));
        cards.add(new SetCardInfo("Angel's Tomb", 211, Rarity.UNCOMMON, mage.cards.a.AngelsTomb.class));
        cards.add(new SetCardInfo("Appetite for Brains", 84, Rarity.UNCOMMON, mage.cards.a.AppetiteForBrains.class));
        cards.add(new SetCardInfo("Arcane Melee", 44, Rarity.RARE, mage.cards.a.ArcaneMelee.class));
        cards.add(new SetCardInfo("Archangel", 5, Rarity.UNCOMMON, mage.cards.a.Archangel.class));
        cards.add(new SetCardInfo("Archwing Dragon", 126, Rarity.RARE, mage.cards.a.ArchwingDragon.class));
        cards.add(new SetCardInfo("Avacyn, Angel of Hope", 6, Rarity.MYTHIC, mage.cards.a.AvacynAngelOfHope.class));
        cards.add(new SetCardInfo("Banishing Stroke", 7, Rarity.UNCOMMON, mage.cards.b.BanishingStroke.class));
        cards.add(new SetCardInfo("Banners Raised", 127, Rarity.COMMON, mage.cards.b.BannersRaised.class));
        cards.add(new SetCardInfo("Barter in Blood", 85, Rarity.UNCOMMON, mage.cards.b.BarterInBlood.class));
        cards.add(new SetCardInfo("Battle Hymn", 128, Rarity.COMMON, mage.cards.b.BattleHymn.class));
        cards.add(new SetCardInfo("Bladed Bracers", 213, Rarity.COMMON, mage.cards.b.BladedBracers.class));
        cards.add(new SetCardInfo("Blessings of Nature", 168, Rarity.UNCOMMON, mage.cards.b.BlessingsOfNature.class));
        cards.add(new SetCardInfo("Blood Artist", 86, Rarity.UNCOMMON, mage.cards.b.BloodArtist.class));
        cards.add(new SetCardInfo("Bloodflow Connoisseur", 87, Rarity.COMMON, mage.cards.b.BloodflowConnoisseur.class));
        cards.add(new SetCardInfo("Bone Splinters", 88, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Bonfire of the Damned", 129, Rarity.MYTHIC, mage.cards.b.BonfireOfTheDamned.class));
        cards.add(new SetCardInfo("Borderland Ranger", 169, Rarity.COMMON, mage.cards.b.BorderlandRanger.class));
        cards.add(new SetCardInfo("Bower Passage", 170, Rarity.UNCOMMON, mage.cards.b.BowerPassage.class));
        cards.add(new SetCardInfo("Bruna, Light of Alabaster", 208, Rarity.MYTHIC, mage.cards.b.BrunaLightOfAlabaster.class));
        cards.add(new SetCardInfo("Builder's Blessing", 8, Rarity.UNCOMMON, mage.cards.b.BuildersBlessing.class));
        cards.add(new SetCardInfo("Burn at the Stake", 130, Rarity.RARE, mage.cards.b.BurnAtTheStake.class));
        cards.add(new SetCardInfo("Butcher Ghoul", 89, Rarity.COMMON, mage.cards.b.ButcherGhoul.class));
        cards.add(new SetCardInfo("Call to Serve", 9, Rarity.COMMON, mage.cards.c.CallToServe.class));
        cards.add(new SetCardInfo("Captain of the Mists", 45, Rarity.RARE, mage.cards.c.CaptainOfTheMists.class));
        cards.add(new SetCardInfo("Cathars' Crusade", 10, Rarity.RARE, mage.cards.c.CatharsCrusade.class));
        cards.add(new SetCardInfo("Cathedral Sanctifier", 11, Rarity.COMMON, mage.cards.c.CathedralSanctifier.class));
        cards.add(new SetCardInfo("Cavern of Souls", 226, Rarity.RARE, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Champion of Lambholt", 171, Rarity.RARE, mage.cards.c.ChampionOfLambholt.class));
        cards.add(new SetCardInfo("Cloudshift", 12, Rarity.COMMON, mage.cards.c.Cloudshift.class));
        cards.add(new SetCardInfo("Commander's Authority", 13, Rarity.UNCOMMON, mage.cards.c.CommandersAuthority.class));
        cards.add(new SetCardInfo("Conjurer's Closet", 214, Rarity.RARE, mage.cards.c.ConjurersCloset.class));
        cards.add(new SetCardInfo("Corpse Traders", 90, Rarity.UNCOMMON, mage.cards.c.CorpseTraders.class));
        cards.add(new SetCardInfo("Craterhoof Behemoth", 172, Rarity.MYTHIC, mage.cards.c.CraterhoofBehemoth.class));
        cards.add(new SetCardInfo("Crippling Chill", 46, Rarity.COMMON, mage.cards.c.CripplingChill.class));
        cards.add(new SetCardInfo("Crypt Creeper", 91, Rarity.COMMON, mage.cards.c.CryptCreeper.class));
        cards.add(new SetCardInfo("Cursebreak", 14, Rarity.COMMON, mage.cards.c.Cursebreak.class));
        cards.add(new SetCardInfo("Dangerous Wager", 131, Rarity.COMMON, mage.cards.d.DangerousWager.class));
        cards.add(new SetCardInfo("Dark Impostor", 92, Rarity.RARE, mage.cards.d.DarkImpostor.class));
        cards.add(new SetCardInfo("Deadeye Navigator", 47, Rarity.RARE, mage.cards.d.DeadeyeNavigator.class));
        cards.add(new SetCardInfo("Death Wind", 93, Rarity.COMMON, mage.cards.d.DeathWind.class));
        cards.add(new SetCardInfo("Defang", 15, Rarity.COMMON, mage.cards.d.Defang.class));
        cards.add(new SetCardInfo("Defy Death", 16, Rarity.UNCOMMON, mage.cards.d.DefyDeath.class));
        cards.add(new SetCardInfo("Demolish", 132, Rarity.COMMON, mage.cards.d.Demolish.class));
        cards.add(new SetCardInfo("Demonic Rising", 94, Rarity.RARE, mage.cards.d.DemonicRising.class));
        cards.add(new SetCardInfo("Demonic Taskmaster", 95, Rarity.UNCOMMON, mage.cards.d.DemonicTaskmaster.class));
        cards.add(new SetCardInfo("Demonlord of Ashmouth", 96, Rarity.RARE, mage.cards.d.DemonlordOfAshmouth.class));
        cards.add(new SetCardInfo("Descendants' Path", 173, Rarity.RARE, mage.cards.d.DescendantsPath.class));
        cards.add(new SetCardInfo("Descent into Madness", 97, Rarity.MYTHIC, mage.cards.d.DescentIntoMadness.class));
        cards.add(new SetCardInfo("Desolate Lighthouse", 227, Rarity.RARE, mage.cards.d.DesolateLighthouse.class));
        cards.add(new SetCardInfo("Devastation Tide", 48, Rarity.RARE, mage.cards.d.DevastationTide.class));
        cards.add(new SetCardInfo("Devout Chaplain", 17, Rarity.UNCOMMON, mage.cards.d.DevoutChaplain.class));
        cards.add(new SetCardInfo("Diregraf Escort", 174, Rarity.COMMON, mage.cards.d.DiregrafEscort.class));
        cards.add(new SetCardInfo("Divine Deflection", 18, Rarity.RARE, mage.cards.d.DivineDeflection.class));
        cards.add(new SetCardInfo("Dread Slaver", 98, Rarity.RARE, mage.cards.d.DreadSlaver.class));
        cards.add(new SetCardInfo("Dreadwaters", 49, Rarity.COMMON, mage.cards.d.Dreadwaters.class));
        cards.add(new SetCardInfo("Driver of the Dead", 99, Rarity.COMMON, mage.cards.d.DriverOfTheDead.class));
        cards.add(new SetCardInfo("Druid's Familiar", 175, Rarity.UNCOMMON, mage.cards.d.DruidsFamiliar.class));
        cards.add(new SetCardInfo("Druids' Repository", 176, Rarity.RARE, mage.cards.d.DruidsRepository.class));
        cards.add(new SetCardInfo("Dual Casting", 133, Rarity.RARE, mage.cards.d.DualCasting.class));
        cards.add(new SetCardInfo("Eaten by Spiders", 177, Rarity.UNCOMMON, mage.cards.e.EatenBySpiders.class));
        cards.add(new SetCardInfo("Elgaud Shieldmate", 50, Rarity.COMMON, mage.cards.e.ElgaudShieldmate.class));
        cards.add(new SetCardInfo("Emancipation Angel", 19, Rarity.UNCOMMON, mage.cards.e.EmancipationAngel.class));
        cards.add(new SetCardInfo("Entreat the Angels", 20, Rarity.MYTHIC, mage.cards.e.EntreatTheAngels.class));
        cards.add(new SetCardInfo("Essence Harvest", 100, Rarity.COMMON, mage.cards.e.EssenceHarvest.class));
        cards.add(new SetCardInfo("Evernight Shade", 101, Rarity.UNCOMMON, mage.cards.e.EvernightShade.class));
        cards.add(new SetCardInfo("Exquisite Blood", 102, Rarity.RARE, mage.cards.e.ExquisiteBlood.class));
        cards.add(new SetCardInfo("Falkenrath Exterminator", 134, Rarity.UNCOMMON, mage.cards.f.FalkenrathExterminator.class));
        cards.add(new SetCardInfo("Farbog Explorer", 21, Rarity.COMMON, mage.cards.f.FarbogExplorer.class));
        cards.add(new SetCardInfo("Favorable Winds", 51, Rarity.UNCOMMON, mage.cards.f.FavorableWinds.class));
        cards.add(new SetCardInfo("Fervent Cathar", 135, Rarity.COMMON, mage.cards.f.FerventCathar.class));
        cards.add(new SetCardInfo("Fettergeist", 52, Rarity.UNCOMMON, mage.cards.f.Fettergeist.class));
        cards.add(new SetCardInfo("Fleeting Distraction", 53, Rarity.COMMON, mage.cards.f.FleetingDistraction.class));
        cards.add(new SetCardInfo("Flowering Lumberknot", 178, Rarity.COMMON, mage.cards.f.FloweringLumberknot.class));
        cards.add(new SetCardInfo("Forest", 242, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 243, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 244, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gallows at Willow Hill", 215, Rarity.RARE, mage.cards.g.GallowsAtWillowHill.class));
        cards.add(new SetCardInfo("Galvanic Alchemist", 54, Rarity.COMMON, mage.cards.g.GalvanicAlchemist.class));
        cards.add(new SetCardInfo("Gang of Devils", 136, Rarity.UNCOMMON, mage.cards.g.GangOfDevils.class));
        cards.add(new SetCardInfo("Geist Snatch", 55, Rarity.COMMON, mage.cards.g.GeistSnatch.class));
        cards.add(new SetCardInfo("Geist Trappers", 179, Rarity.COMMON, mage.cards.g.GeistTrappers.class));
        cards.add(new SetCardInfo("Ghostform", 56, Rarity.COMMON, mage.cards.g.Ghostform.class));
        cards.add(new SetCardInfo("Ghostly Flicker", 57, Rarity.COMMON, mage.cards.g.GhostlyFlicker.class));
        cards.add(new SetCardInfo("Ghostly Touch", 58, Rarity.UNCOMMON, mage.cards.g.GhostlyTouch.class));
        cards.add(new SetCardInfo("Ghoulflesh", 103, Rarity.COMMON, mage.cards.g.Ghoulflesh.class));
        cards.add(new SetCardInfo("Gisela, Blade of Goldnight", 209, Rarity.MYTHIC, mage.cards.g.GiselaBladeOfGoldnight.class));
        cards.add(new SetCardInfo("Gloom Surgeon", 104, Rarity.RARE, mage.cards.g.GloomSurgeon.class));
        cards.add(new SetCardInfo("Gloomwidow", 180, Rarity.UNCOMMON, mage.cards.g.Gloomwidow.class));
        cards.add(new SetCardInfo("Goldnight Commander", 22, Rarity.UNCOMMON, mage.cards.g.GoldnightCommander.class));
        cards.add(new SetCardInfo("Goldnight Redeemer", 23, Rarity.UNCOMMON, mage.cards.g.GoldnightRedeemer.class));
        cards.add(new SetCardInfo("Grave Exchange", 105, Rarity.COMMON, mage.cards.g.GraveExchange.class));
        cards.add(new SetCardInfo("Griselbrand", 106, Rarity.MYTHIC, mage.cards.g.Griselbrand.class));
        cards.add(new SetCardInfo("Grounded", 181, Rarity.COMMON, mage.cards.g.Grounded.class));
        cards.add(new SetCardInfo("Gryff Vanguard", 59, Rarity.COMMON, mage.cards.g.GryffVanguard.class));
        cards.add(new SetCardInfo("Guise of Fire", 137, Rarity.COMMON, mage.cards.g.GuiseOfFire.class));
        cards.add(new SetCardInfo("Hanweir Lancer", 138, Rarity.COMMON, mage.cards.h.HanweirLancer.class));
        cards.add(new SetCardInfo("Harvester of Souls", 107, Rarity.RARE, mage.cards.h.HarvesterOfSouls.class));
        cards.add(new SetCardInfo("Haunted Guardian", 216, Rarity.UNCOMMON, mage.cards.h.HauntedGuardian.class));
        cards.add(new SetCardInfo("Havengul Skaab", 60, Rarity.COMMON, mage.cards.h.HavengulSkaab.class));
        cards.add(new SetCardInfo("Havengul Vampire", 139, Rarity.UNCOMMON, mage.cards.h.HavengulVampire.class));
        cards.add(new SetCardInfo("Heirs of Stromkirk", 140, Rarity.COMMON, mage.cards.h.HeirsOfStromkirk.class));
        cards.add(new SetCardInfo("Herald of War", 24, Rarity.RARE, mage.cards.h.HeraldOfWar.class));
        cards.add(new SetCardInfo("Holy Justiciar", 25, Rarity.UNCOMMON, mage.cards.h.HolyJusticiar.class));
        cards.add(new SetCardInfo("Homicidal Seclusion", 108, Rarity.UNCOMMON, mage.cards.h.HomicidalSeclusion.class));
        cards.add(new SetCardInfo("Hound of Griselbrand", 141, Rarity.RARE, mage.cards.h.HoundOfGriselbrand.class));
        cards.add(new SetCardInfo("Howlgeist", 182, Rarity.UNCOMMON, mage.cards.h.Howlgeist.class));
        cards.add(new SetCardInfo("Human Frailty", 109, Rarity.UNCOMMON, mage.cards.h.HumanFrailty.class));
        cards.add(new SetCardInfo("Hunted Ghoul", 110, Rarity.COMMON, mage.cards.h.HuntedGhoul.class));
        cards.add(new SetCardInfo("Infinite Reflection", 61, Rarity.RARE, mage.cards.i.InfiniteReflection.class));
        cards.add(new SetCardInfo("Into the Void", 62, Rarity.UNCOMMON, mage.cards.i.IntoTheVoid.class));
        cards.add(new SetCardInfo("Island", 233, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 234, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 235, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Joint Assault", 183, Rarity.COMMON, mage.cards.j.JointAssault.class));
        cards.add(new SetCardInfo("Kessig Malcontents", 142, Rarity.UNCOMMON, mage.cards.k.KessigMalcontents.class));
        cards.add(new SetCardInfo("Killing Wave", 111, Rarity.RARE, mage.cards.k.KillingWave.class));
        cards.add(new SetCardInfo("Kruin Striker", 143, Rarity.COMMON, mage.cards.k.KruinStriker.class));
        cards.add(new SetCardInfo("Lair Delve", 184, Rarity.COMMON, mage.cards.l.LairDelve.class));
        cards.add(new SetCardInfo("Latch Seeker", 63, Rarity.UNCOMMON, mage.cards.l.LatchSeeker.class));
        cards.add(new SetCardInfo("Leap of Faith", 26, Rarity.COMMON, mage.cards.l.LeapOfFaith.class));
        cards.add(new SetCardInfo("Lightning Mauler", 144, Rarity.UNCOMMON, mage.cards.l.LightningMauler.class));
        cards.add(new SetCardInfo("Lightning Prowess", 145, Rarity.UNCOMMON, mage.cards.l.LightningProwess.class));
        cards.add(new SetCardInfo("Lone Revenant", 64, Rarity.RARE, mage.cards.l.LoneRevenant.class));
        cards.add(new SetCardInfo("Lunar Mystic", 65, Rarity.RARE, mage.cards.l.LunarMystic.class));
        cards.add(new SetCardInfo("Maalfeld Twins", 112, Rarity.UNCOMMON, mage.cards.m.MaalfeldTwins.class));
        cards.add(new SetCardInfo("Mad Prophet", 146, Rarity.COMMON, mage.cards.m.MadProphet.class));
        cards.add(new SetCardInfo("Malicious Intent", 147, Rarity.COMMON, mage.cards.m.MaliciousIntent.class));
        cards.add(new SetCardInfo("Malignus", 148, Rarity.MYTHIC, mage.cards.m.Malignus.class));
        cards.add(new SetCardInfo("Marrow Bats", 113, Rarity.UNCOMMON, mage.cards.m.MarrowBats.class));
        cards.add(new SetCardInfo("Mass Appeal", 66, Rarity.UNCOMMON, mage.cards.m.MassAppeal.class));
        cards.add(new SetCardInfo("Mental Agony", 114, Rarity.COMMON, mage.cards.m.MentalAgony.class));
        cards.add(new SetCardInfo("Midnight Duelist", 27, Rarity.COMMON, mage.cards.m.MidnightDuelist.class));
        cards.add(new SetCardInfo("Midvast Protector", 28, Rarity.COMMON, mage.cards.m.MidvastProtector.class));
        cards.add(new SetCardInfo("Misthollow Griffin", 68, Rarity.MYTHIC, mage.cards.m.MisthollowGriffin.class));
        cards.add(new SetCardInfo("Mist Raven", 67, Rarity.COMMON, mage.cards.m.MistRaven.class));
        cards.add(new SetCardInfo("Moonlight Geist", 29, Rarity.COMMON, mage.cards.m.MoonlightGeist.class));
        cards.add(new SetCardInfo("Moonsilver Spear", 217, Rarity.RARE, mage.cards.m.MoonsilverSpear.class));
        cards.add(new SetCardInfo("Moorland Inquisitor", 30, Rarity.COMMON, mage.cards.m.MoorlandInquisitor.class));
        cards.add(new SetCardInfo("Mountain", 239, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 240, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 241, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Narstad Scrapper", 218, Rarity.COMMON, mage.cards.n.NarstadScrapper.class));
        cards.add(new SetCardInfo("Natural End", 185, Rarity.COMMON, mage.cards.n.NaturalEnd.class));
        cards.add(new SetCardInfo("Nearheath Pilgrim", 31, Rarity.UNCOMMON, mage.cards.n.NearheathPilgrim.class));
        cards.add(new SetCardInfo("Necrobite", 115, Rarity.COMMON, mage.cards.n.Necrobite.class));
        cards.add(new SetCardInfo("Nephalia Smuggler", 69, Rarity.UNCOMMON, mage.cards.n.NephaliaSmuggler.class));
        cards.add(new SetCardInfo("Nettle Swine", 186, Rarity.COMMON, mage.cards.n.NettleSwine.class));
        cards.add(new SetCardInfo("Nightshade Peddler", 187, Rarity.COMMON, mage.cards.n.NightshadePeddler.class));
        cards.add(new SetCardInfo("Otherworld Atlas", 219, Rarity.RARE, mage.cards.o.OtherworldAtlas.class));
        cards.add(new SetCardInfo("Outwit", 70, Rarity.COMMON, mage.cards.o.Outwit.class));
        cards.add(new SetCardInfo("Pathbreaker Wurm", 188, Rarity.COMMON, mage.cards.p.PathbreakerWurm.class));
        cards.add(new SetCardInfo("Peel from Reality", 71, Rarity.COMMON, mage.cards.p.PeelFromReality.class));
        cards.add(new SetCardInfo("Pillar of Flame", 149, Rarity.COMMON, mage.cards.p.PillarOfFlame.class));
        cards.add(new SetCardInfo("Plains", 230, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 231, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 232, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Polluted Dead", 116, Rarity.COMMON, mage.cards.p.PollutedDead.class));
        cards.add(new SetCardInfo("Predator's Gambit", 117, Rarity.COMMON, mage.cards.p.PredatorsGambit.class));
        cards.add(new SetCardInfo("Primal Surge", 189, Rarity.MYTHIC, mage.cards.p.PrimalSurge.class));
        cards.add(new SetCardInfo("Raging Poltergeist", 150, Rarity.COMMON, mage.cards.r.RagingPoltergeist.class));
        cards.add(new SetCardInfo("Rain of Thorns", 190, Rarity.UNCOMMON, mage.cards.r.RainOfThorns.class));
        cards.add(new SetCardInfo("Reforge the Soul", 151, Rarity.RARE, mage.cards.r.ReforgeTheSoul.class));
        cards.add(new SetCardInfo("Renegade Demon", 118, Rarity.COMMON, mage.cards.r.RenegadeDemon.class));
        cards.add(new SetCardInfo("Restoration Angel", 32, Rarity.RARE, mage.cards.r.RestorationAngel.class));
        cards.add(new SetCardInfo("Revenge of the Hunted", 191, Rarity.RARE, mage.cards.r.RevengeOfTheHunted.class));
        cards.add(new SetCardInfo("Riders of Gavony", 33, Rarity.RARE, mage.cards.r.RidersOfGavony.class));
        cards.add(new SetCardInfo("Righteous Blow", 34, Rarity.COMMON, mage.cards.r.RighteousBlow.class));
        cards.add(new SetCardInfo("Riot Ringleader", 152, Rarity.COMMON, mage.cards.r.RiotRingleader.class));
        cards.add(new SetCardInfo("Rite of Ruin", 153, Rarity.RARE, mage.cards.r.RiteOfRuin.class));
        cards.add(new SetCardInfo("Rotcrown Ghoul", 72, Rarity.COMMON, mage.cards.r.RotcrownGhoul.class));
        cards.add(new SetCardInfo("Rush of Blood", 154, Rarity.UNCOMMON, mage.cards.r.RushOfBlood.class));
        cards.add(new SetCardInfo("Scalding Devil", 155, Rarity.COMMON, mage.cards.s.ScaldingDevil.class));
        cards.add(new SetCardInfo("Scrapskin Drake", 73, Rarity.COMMON, mage.cards.s.ScrapskinDrake.class));
        cards.add(new SetCardInfo("Scroll of Avacyn", 220, Rarity.COMMON, mage.cards.s.ScrollOfAvacyn.class));
        cards.add(new SetCardInfo("Scroll of Griselbrand", 221, Rarity.COMMON, mage.cards.s.ScrollOfGriselbrand.class));
        cards.add(new SetCardInfo("Searchlight Geist", 119, Rarity.COMMON, mage.cards.s.SearchlightGeist.class));
        cards.add(new SetCardInfo("Second Guess", 74, Rarity.UNCOMMON, mage.cards.s.SecondGuess.class));
        cards.add(new SetCardInfo("Seraph of Dawn", 35, Rarity.COMMON, mage.cards.s.SeraphOfDawn.class));
        cards.add(new SetCardInfo("Seraph Sanctuary", 228, Rarity.COMMON, mage.cards.s.SeraphSanctuary.class));
        cards.add(new SetCardInfo("Sheltering Word", 192, Rarity.COMMON, mage.cards.s.ShelteringWord.class));
        cards.add(new SetCardInfo("Sigarda, Host of Herons", 210, Rarity.MYTHIC, mage.cards.s.SigardaHostOfHerons.class));
        cards.add(new SetCardInfo("Silverblade Paladin", 36, Rarity.RARE, mage.cards.s.SilverbladePaladin.class));
        cards.add(new SetCardInfo("Slayers' Stronghold", 229, Rarity.RARE, mage.cards.s.SlayersStronghold.class));
        cards.add(new SetCardInfo("Snare the Skies", 193, Rarity.COMMON, mage.cards.s.SnareTheSkies.class));
        cards.add(new SetCardInfo("Somberwald Sage", 194, Rarity.RARE, mage.cards.s.SomberwaldSage.class));
        cards.add(new SetCardInfo("Somberwald Vigilante", 156, Rarity.COMMON, mage.cards.s.SomberwaldVigilante.class));
        cards.add(new SetCardInfo("Soulcage Fiend", 120, Rarity.COMMON, mage.cards.s.SoulcageFiend.class));
        cards.add(new SetCardInfo("Soul of the Harvest", 195, Rarity.RARE, mage.cards.s.SoulOfTheHarvest.class));
        cards.add(new SetCardInfo("Spectral Gateguards", 37, Rarity.COMMON, mage.cards.s.SpectralGateguards.class));
        cards.add(new SetCardInfo("Spectral Prison", 75, Rarity.COMMON, mage.cards.s.SpectralPrison.class));
        cards.add(new SetCardInfo("Spirit Away", 76, Rarity.RARE, mage.cards.s.SpiritAway.class));
        cards.add(new SetCardInfo("Stern Mentor", 77, Rarity.UNCOMMON, mage.cards.s.SternMentor.class));
        cards.add(new SetCardInfo("Stolen Goods", 78, Rarity.RARE, mage.cards.s.StolenGoods.class));
        cards.add(new SetCardInfo("Stonewright", 157, Rarity.UNCOMMON, mage.cards.s.Stonewright.class));
        cards.add(new SetCardInfo("Swamp", 236, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 237, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 238, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tamiyo, the Moon Sage", 79, Rarity.MYTHIC, mage.cards.t.TamiyoTheMoonSage.class));
        cards.add(new SetCardInfo("Tandem Lookout", 80, Rarity.UNCOMMON, mage.cards.t.TandemLookout.class));
        cards.add(new SetCardInfo("Temporal Mastery", 81, Rarity.MYTHIC, mage.cards.t.TemporalMastery.class));
        cards.add(new SetCardInfo("Terminus", 38, Rarity.RARE, mage.cards.t.Terminus.class));
        cards.add(new SetCardInfo("Terrifying Presence", 196, Rarity.COMMON, mage.cards.t.TerrifyingPresence.class));
        cards.add(new SetCardInfo("Thatcher Revolt", 158, Rarity.COMMON, mage.cards.t.ThatcherRevolt.class));
        cards.add(new SetCardInfo("Thraben Valiant", 39, Rarity.COMMON, mage.cards.t.ThrabenValiant.class));
        cards.add(new SetCardInfo("Thunderbolt", 159, Rarity.COMMON, mage.cards.t.Thunderbolt.class));
        cards.add(new SetCardInfo("Thunderous Wrath", 160, Rarity.UNCOMMON, mage.cards.t.ThunderousWrath.class));
        cards.add(new SetCardInfo("Tibalt, the Fiend-Blooded", 161, Rarity.MYTHIC, mage.cards.t.TibaltTheFiendBlooded.class));
        cards.add(new SetCardInfo("Timberland Guide", 197, Rarity.COMMON, mage.cards.t.TimberlandGuide.class));
        cards.add(new SetCardInfo("Tormentor's Trident", 222, Rarity.UNCOMMON, mage.cards.t.TormentorsTrident.class));
        cards.add(new SetCardInfo("Treacherous Pit-Dweller", 121, Rarity.RARE, mage.cards.t.TreacherousPitDweller.class));
        cards.add(new SetCardInfo("Triumph of Cruelty", 122, Rarity.UNCOMMON, mage.cards.t.TriumphOfCruelty.class));
        cards.add(new SetCardInfo("Triumph of Ferocity", 198, Rarity.UNCOMMON, mage.cards.t.TriumphOfFerocity.class));
        cards.add(new SetCardInfo("Trusted Forcemage", 199, Rarity.COMMON, mage.cards.t.TrustedForcemage.class));
        cards.add(new SetCardInfo("Tyrant of Discord", 162, Rarity.RARE, mage.cards.t.TyrantOfDiscord.class));
        cards.add(new SetCardInfo("Ulvenwald Tracker", 200, Rarity.RARE, mage.cards.u.UlvenwaldTracker.class));
        cards.add(new SetCardInfo("Uncanny Speed", 163, Rarity.COMMON, mage.cards.u.UncannySpeed.class));
        cards.add(new SetCardInfo("Undead Executioner", 123, Rarity.COMMON, mage.cards.u.UndeadExecutioner.class));
        cards.add(new SetCardInfo("Unhallowed Pact", 124, Rarity.COMMON, mage.cards.u.UnhallowedPact.class));
        cards.add(new SetCardInfo("Vanguard's Shield", 223, Rarity.COMMON, mage.cards.v.VanguardsShield.class));
        cards.add(new SetCardInfo("Vanishment", 82, Rarity.UNCOMMON, mage.cards.v.Vanishment.class));
        cards.add(new SetCardInfo("Vessel of Endless Rest", 224, Rarity.UNCOMMON, mage.cards.v.VesselOfEndlessRest.class));
        cards.add(new SetCardInfo("Vexing Devil", 164, Rarity.RARE, mage.cards.v.VexingDevil.class));
        cards.add(new SetCardInfo("Vigilante Justice", 165, Rarity.UNCOMMON, mage.cards.v.VigilanteJustice.class));
        cards.add(new SetCardInfo("Voice of the Provinces", 40, Rarity.COMMON, mage.cards.v.VoiceOfTheProvinces.class));
        cards.add(new SetCardInfo("Vorstclaw", 201, Rarity.UNCOMMON, mage.cards.v.Vorstclaw.class));
        cards.add(new SetCardInfo("Wandering Wolf", 202, Rarity.COMMON, mage.cards.w.WanderingWolf.class));
        cards.add(new SetCardInfo("Wild Defiance", 203, Rarity.RARE, mage.cards.w.WildDefiance.class));
        cards.add(new SetCardInfo("Wildwood Geist", 204, Rarity.COMMON, mage.cards.w.WildwoodGeist.class));
        cards.add(new SetCardInfo("Wingcrafter", 83, Rarity.COMMON, mage.cards.w.Wingcrafter.class));
        cards.add(new SetCardInfo("Wolfir Avenger", 205, Rarity.UNCOMMON, mage.cards.w.WolfirAvenger.class));
        cards.add(new SetCardInfo("Wolfir Silverheart", 206, Rarity.RARE, mage.cards.w.WolfirSilverheart.class));
        cards.add(new SetCardInfo("Yew Spirit", 207, Rarity.UNCOMMON, mage.cards.y.YewSpirit.class));
        cards.add(new SetCardInfo("Zealous Conscripts", 166, Rarity.RARE, mage.cards.z.ZealousConscripts.class));
        cards.add(new SetCardInfo("Zealous Strike", 41, Rarity.COMMON, mage.cards.z.ZealousStrike.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new AvacynRestoredCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/avr.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class AvacynRestoredCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "159", "89", "11", "178", "147", "50", "110", "199", "27", "137", "87", "53", "163", "11", "179", "184", "67", "120", "127", "72", "39", "137", "199", "119", "188", "59", "131", "60", "120", "163", "184", "186", "91", "50", "131", "70", "110", "159", "204", "179", "89", "27", "128", "60", "4", "91", "178", "186", "59", "127", "30", "70", "39", "87", "204", "128", "185", "53", "30", "119", "147", "188", "72", "4", "185", "67");
    private final CardRun commonB = new CardRun(true, "12", "218", "181", "143", "40", "220", "88", "43", "37", "152", "105", "57", "34", "181", "49", "88", "138", "55", "12", "103", "167", "152", "35", "105", "49", "43", "34", "220", "196", "152", "57", "117", "135", "40", "35", "167", "218", "55", "103", "143", "57", "37", "196", "135", "12", "117", "181", "220", "138", "34", "218", "105", "37", "55", "196", "88", "135", "43", "40", "49", "35", "143", "167", "117", "103", "138");
    private final CardRun commonC1 = new CardRun(true, "221", "123", "28", "155", "202", "169", "100", "42", "29", "93", "3", "158", "174", "228", "123", "73", "213", "21", "202", "26", "156", "100", "99", "54", "197", "21", "187", "158", "28", "116", "83", "118", "169", "54", "156", "29", "187", "116", "213", "83", "93", "132", "26", "174", "223", "155", "42", "118", "99", "3", "228", "197", "132", "223", "73");
    private final CardRun commonC2 = new CardRun(true, "221", "115", "192", "9", "140", "71", "75", "15", "193", "114", "150", "41", "146", "192", "46", "75", "9", "114", "124", "150", "71", "14", "149", "183", "56", "115", "41", "140", "192", "15", "149", "124", "75", "183", "9", "71", "193", "146", "114", "56", "41", "124", "140", "46", "193", "14", "150", "183", "115", "15", "56", "149", "46", "14", "146");
    private final CardRun uncommonA = new CardRun(true, "207", "82", "224", "23", "212", "160", "51", "113", "180", "22", "136", "90", "19", "62", "145", "182", "86", "17", "157", "207", "101", "74", "90", "165", "205", "25", "157", "95", "201", "58", "66", "125", "175", "145", "17", "95", "212", "51", "109", "23", "201", "165", "82", "22", "224", "154", "180", "19", "109", "74", "58", "205", "125", "113", "182", "31", "160", "66", "25", "101", "175", "62", "86", "136", "31", "154");
    private final CardRun uncommonB = new CardRun(true, "80", "170", "144", "16", "122", "63", "13", "190", "216", "69", "84", "5", "198", "142", "85", "77", "168", "16", "139", "216", "80", "122", "144", "52", "13", "177", "198", "222", "84", "112", "142", "69", "7", "134", "177", "8", "108", "211", "85", "63", "139", "77", "190", "7", "8", "112", "211", "170", "52", "168", "108", "5", "134", "222");
    private final CardRun rare = new CardRun(false, "1", "2", "10", "18", "24", "32", "33", "36", "38", "44", "45", "47", "48", "61", "64", "65", "76", "78", "92", "94", "96", "98", "102", "104", "107", "111", "121", "126", "130", "133", "141", "151", "153", "162", "164", "166", "171", "173", "176", "191", "194", "195", "200", "203", "206", "214", "215", "217", "219", "225", "226", "227", "229", "1", "2", "10", "18", "24", "32", "33", "36", "38", "44", "45", "47", "48", "61", "64", "65", "76", "78", "92", "94", "96", "98", "102", "104", "107", "111", "121", "126", "130", "133", "141", "151", "153", "162", "164", "166", "171", "173", "176", "191", "194", "195", "200", "203", "206", "214", "215", "217", "219", "225", "226", "227", "229", "6", "20", "68", "79", "81", "97", "106", "129", "148", "161", "172", "189", "208", "209", "210");
    private final CardRun land = new CardRun(false, "230", "231", "232", "233", "234", "235", "236", "237", "238", "239", "240", "241", "242", "243", "244");

    private final BoosterStructure AAABC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAABBBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2, commonC2
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
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
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
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBBBC2C2C2,
            AAABBBBC2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 1.65 A uncommons (33 / 20)
    // 1.35 B uncommons (27 / 20)
    // These numbers are the same for all sets with 60 uncommons in asymmetrical A/B print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            ABB, ABB, ABB, ABB, ABB, ABB, ABB
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
