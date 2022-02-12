package mage.sets;

import mage.abilities.Ability;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class Battlebond extends ExpansionSet {

    private static final Battlebond instance = new Battlebond();

    public static Battlebond getInstance() {
        return instance;
    }

    private Battlebond() {
        super("Battlebond", "BBD", ExpansionSet.buildDate(2018, 6, 8), SetType.SUPPLEMENTAL);
        this.blockName = "Battlebond";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        this.maxCardNumberInBooster = 254; // Don't use the 2 foil mystics copies because it would influence a random ratio

        cards.add(new SetCardInfo("Aim High", 189, Rarity.UNCOMMON, mage.cards.a.AimHigh.class));
        cards.add(new SetCardInfo("Angel of Retribution", 86, Rarity.UNCOMMON, mage.cards.a.AngelOfRetribution.class));
        cards.add(new SetCardInfo("Angelic Chorus", 87, Rarity.RARE, mage.cards.a.AngelicChorus.class));
        cards.add(new SetCardInfo("Angelic Gift", 88, Rarity.COMMON, mage.cards.a.AngelicGift.class));
        cards.add(new SetCardInfo("Apocalypse Hydra", 217, Rarity.RARE, mage.cards.a.ApocalypseHydra.class));
        cards.add(new SetCardInfo("Arcane Artisan", 33, Rarity.MYTHIC, mage.cards.a.ArcaneArtisan.class));
        cards.add(new SetCardInfo("Archfiend of Despair", 44, Rarity.MYTHIC, mage.cards.a.ArchfiendOfDespair.class));
        cards.add(new SetCardInfo("Archon of Valor's Reach", 74, Rarity.RARE, mage.cards.a.ArchonOfValorsReach.class));
        cards.add(new SetCardInfo("Arena Rector", 23, Rarity.MYTHIC, mage.cards.a.ArenaRector.class));
        cards.add(new SetCardInfo("Assassin's Strike", 138, Rarity.UNCOMMON, mage.cards.a.AssassinsStrike.class));
        cards.add(new SetCardInfo("Assassinate", 139, Rarity.COMMON, mage.cards.a.Assassinate.class));
        cards.add(new SetCardInfo("Auger Spree", 218, Rarity.COMMON, mage.cards.a.AugerSpree.class));
        cards.add(new SetCardInfo("Aurora Champion", 24, Rarity.COMMON, mage.cards.a.AuroraChampion.class));
        cards.add(new SetCardInfo("Azra Bladeseeker", 55, Rarity.COMMON, mage.cards.a.AzraBladeseeker.class));
        cards.add(new SetCardInfo("Azra Oddsmaker", 75, Rarity.UNCOMMON, mage.cards.a.AzraOddsmaker.class));
        cards.add(new SetCardInfo("Bathe in Dragonfire", 164, Rarity.COMMON, mage.cards.b.BatheInDragonfire.class));
        cards.add(new SetCardInfo("Battle Mastery", 89, Rarity.UNCOMMON, mage.cards.b.BattleMastery.class));
        cards.add(new SetCardInfo("Battle Rampart", 165, Rarity.COMMON, mage.cards.b.BattleRampart.class));
        cards.add(new SetCardInfo("Battle-Rattle Shaman", 166, Rarity.UNCOMMON, mage.cards.b.BattleRattleShaman.class));
        cards.add(new SetCardInfo("Beast Within", 190, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Benthic Giant", 113, Rarity.COMMON, mage.cards.b.BenthicGiant.class));
        cards.add(new SetCardInfo("Blaring Captain", 14, Rarity.UNCOMMON, mage.cards.b.BlaringCaptain.class));
        cards.add(new SetCardInfo("Blaring Recruiter", 13, Rarity.UNCOMMON, mage.cards.b.BlaringRecruiter.class));
        cards.add(new SetCardInfo("Blaze", 167, Rarity.UNCOMMON, mage.cards.b.Blaze.class));
        cards.add(new SetCardInfo("Blood Feud", 168, Rarity.UNCOMMON, mage.cards.b.BloodFeud.class));
        cards.add(new SetCardInfo("Bloodborn Scoundrels", 45, Rarity.COMMON, mage.cards.b.BloodbornScoundrels.class));
        cards.add(new SetCardInfo("Boldwyr Intimidator", 169, Rarity.UNCOMMON, mage.cards.b.BoldwyrIntimidator.class));
        cards.add(new SetCardInfo("Bonus Round", 56, Rarity.RARE, mage.cards.b.BonusRound.class));
        cards.add(new SetCardInfo("Borderland Marauder", 170, Rarity.COMMON, mage.cards.b.BorderlandMarauder.class));
        cards.add(new SetCardInfo("Bountiful Promenade", 81, Rarity.RARE, mage.cards.b.BountifulPromenade.class));
        cards.add(new SetCardInfo("Bramble Sovereign", 65, Rarity.MYTHIC, mage.cards.b.BrambleSovereign.class));
        cards.add(new SetCardInfo("Brightling", 25, Rarity.MYTHIC, mage.cards.b.Brightling.class));
        cards.add(new SetCardInfo("Bring Down", 26, Rarity.UNCOMMON, mage.cards.b.BringDown.class));
        cards.add(new SetCardInfo("Bull-Rush Bruiser", 57, Rarity.COMMON, mage.cards.b.BullRushBruiser.class));
        cards.add(new SetCardInfo("Call to Heel", 114, Rarity.COMMON, mage.cards.c.CallToHeel.class));
        cards.add(new SetCardInfo("Canopy Spider", 191, Rarity.COMMON, mage.cards.c.CanopySpider.class));
        cards.add(new SetCardInfo("Centaur Healer", 219, Rarity.COMMON, mage.cards.c.CentaurHealer.class));
        cards.add(new SetCardInfo("Chain Lightning", 171, Rarity.UNCOMMON, mage.cards.c.ChainLightning.class));
        cards.add(new SetCardInfo("Chakram Retriever", 15, Rarity.UNCOMMON, mage.cards.c.ChakramRetriever.class));
        cards.add(new SetCardInfo("Chakram Slinger", 16, Rarity.UNCOMMON, mage.cards.c.ChakramSlinger.class));
        cards.add(new SetCardInfo("Champion of Arashin", 90, Rarity.COMMON, mage.cards.c.ChampionOfArashin.class));
        cards.add(new SetCardInfo("Charging Binox", 66, Rarity.COMMON, mage.cards.c.ChargingBinox.class));
        cards.add(new SetCardInfo("Charging Rhino", 192, Rarity.COMMON, mage.cards.c.ChargingRhino.class));
        cards.add(new SetCardInfo("Cheering Fanatic", 58, Rarity.UNCOMMON, mage.cards.c.CheeringFanatic.class));
        cards.add(new SetCardInfo("Claustrophobia", 115, Rarity.COMMON, mage.cards.c.Claustrophobia.class));
        cards.add(new SetCardInfo("Combo Attack", 67, Rarity.COMMON, mage.cards.c.ComboAttack.class));
        cards.add(new SetCardInfo("Consulate Skygate", 232, Rarity.COMMON, mage.cards.c.ConsulateSkygate.class));
        cards.add(new SetCardInfo("Coralhelm Guide", 116, Rarity.COMMON, mage.cards.c.CoralhelmGuide.class));
        cards.add(new SetCardInfo("Cowl Prowler", 193, Rarity.COMMON, mage.cards.c.CowlProwler.class));
        cards.add(new SetCardInfo("Culling Dais", 233, Rarity.UNCOMMON, mage.cards.c.CullingDais.class));
        cards.add(new SetCardInfo("Daggerback Basilisk", 194, Rarity.COMMON, mage.cards.d.DaggerbackBasilisk.class));
        cards.add(new SetCardInfo("Daggerdrome Imp", 140, Rarity.COMMON, mage.cards.d.DaggerdromeImp.class));
        cards.add(new SetCardInfo("Decorated Champion", 69, Rarity.UNCOMMON, mage.cards.d.DecoratedChampion.class));
        cards.add(new SetCardInfo("Diabolic Intent", 141, Rarity.RARE, mage.cards.d.DiabolicIntent.class));
        cards.add(new SetCardInfo("Dinrova Horror", 220, Rarity.UNCOMMON, mage.cards.d.DinrovaHorror.class));
        cards.add(new SetCardInfo("Doomed Dissenter", 142, Rarity.COMMON, mage.cards.d.DoomedDissenter.class));
        cards.add(new SetCardInfo("Doomed Traveler", 91, Rarity.COMMON, mage.cards.d.DoomedTraveler.class));
        cards.add(new SetCardInfo("Doubling Season", 195, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class));
        cards.add(new SetCardInfo("Dragon Breath", 172, Rarity.UNCOMMON, mage.cards.d.DragonBreath.class));
        cards.add(new SetCardInfo("Dragon Hatchling", 173, Rarity.COMMON, mage.cards.d.DragonHatchling.class));
        cards.add(new SetCardInfo("Dwarven Lightsmith", 27, Rarity.COMMON, mage.cards.d.DwarvenLightsmith.class));
        cards.add(new SetCardInfo("Eager Construct", 234, Rarity.COMMON, mage.cards.e.EagerConstruct.class));
        cards.add(new SetCardInfo("Earth Elemental", 174, Rarity.COMMON, mage.cards.e.EarthElemental.class));
        cards.add(new SetCardInfo("Elvish Visionary", 196, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Ember Beast", 175, Rarity.COMMON, mage.cards.e.EmberBeast.class));
        cards.add(new SetCardInfo("Enduring Scalelord", 221, Rarity.UNCOMMON, mage.cards.e.EnduringScalelord.class));
        cards.add(new SetCardInfo("Enthralling Victor", 176, Rarity.UNCOMMON, mage.cards.e.EnthrallingVictor.class));
        cards.add(new SetCardInfo("Evil Twin", 222, Rarity.RARE, mage.cards.e.EvilTwin.class));
        cards.add(new SetCardInfo("Expedite", 177, Rarity.COMMON, mage.cards.e.Expedite.class));
        cards.add(new SetCardInfo("Expedition Raptor", 92, Rarity.COMMON, mage.cards.e.ExpeditionRaptor.class));
        cards.add(new SetCardInfo("Eyeblight Assassin", 143, Rarity.COMMON, mage.cards.e.EyeblightAssassin.class));
        cards.add(new SetCardInfo("Fan Favorite", 46, Rarity.COMMON, mage.cards.f.FanFavorite.class));
        cards.add(new SetCardInfo("Feral Hydra", 197, Rarity.UNCOMMON, mage.cards.f.FeralHydra.class));
        cards.add(new SetCardInfo("Fertile Ground", 198, Rarity.COMMON, mage.cards.f.FertileGround.class));
        cards.add(new SetCardInfo("Fertilid", 199, Rarity.UNCOMMON, mage.cards.f.Fertilid.class));
        cards.add(new SetCardInfo("Fill with Fright", 144, Rarity.COMMON, mage.cards.f.FillWithFright.class));
        cards.add(new SetCardInfo("Flamewave Invoker", 178, Rarity.UNCOMMON, mage.cards.f.FlamewaveInvoker.class));
        cards.add(new SetCardInfo("Fog Bank", 117, Rarity.UNCOMMON, mage.cards.f.FogBank.class));
        cards.add(new SetCardInfo("Forest", 254, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frost Lynx", 118, Rarity.COMMON, mage.cards.f.FrostLynx.class));
        cards.add(new SetCardInfo("Fumble", 34, Rarity.UNCOMMON, mage.cards.f.Fumble.class));
        cards.add(new SetCardInfo("Game Plan", 35, Rarity.RARE, mage.cards.g.GamePlan.class));
        cards.add(new SetCardInfo("Gang Up", 47, Rarity.UNCOMMON, mage.cards.g.GangUp.class));
        cards.add(new SetCardInfo("Generous Patron", 70, Rarity.RARE, mage.cards.g.GenerousPatron.class));
        cards.add(new SetCardInfo("Genesis Chamber", 235, Rarity.UNCOMMON, mage.cards.g.GenesisChamber.class));
        cards.add(new SetCardInfo("Giant Growth", 200, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Goblin Razerunners", 179, Rarity.RARE, mage.cards.g.GoblinRazerunners.class));
        cards.add(new SetCardInfo("Gold-Forged Sentinel", 236, Rarity.UNCOMMON, mage.cards.g.GoldForgedSentinel.class));
        cards.add(new SetCardInfo("Gorm the Great", 8, Rarity.RARE, mage.cards.g.GormTheGreat.class));
        cards.add(new SetCardInfo("Greater Good", 201, Rarity.RARE, mage.cards.g.GreaterGood.class));
        cards.add(new SetCardInfo("Grotesque Mutation", 145, Rarity.COMMON, mage.cards.g.GrotesqueMutation.class));
        cards.add(new SetCardInfo("Grothama, All-Devouring", 71, Rarity.MYTHIC, mage.cards.g.GrothamaAllDevouring.class));
        cards.add(new SetCardInfo("Gwafa Hazid, Profiteer", 223, Rarity.RARE, mage.cards.g.GwafaHazidProfiteer.class));
        cards.add(new SetCardInfo("Hand of Silumgar", 146, Rarity.COMMON, mage.cards.h.HandOfSilumgar.class));
        cards.add(new SetCardInfo("Hexplate Golem", 237, Rarity.COMMON, mage.cards.h.HexplateGolem.class));
        cards.add(new SetCardInfo("Huddle Up", 36, Rarity.COMMON, mage.cards.h.HuddleUp.class));
        cards.add(new SetCardInfo("Hunted Wumpus", 202, Rarity.UNCOMMON, mage.cards.h.HuntedWumpus.class));
        cards.add(new SetCardInfo("Impetuous Protege", 19, Rarity.UNCOMMON, mage.cards.i.ImpetuousProtege.class));
        cards.add(new SetCardInfo("Impulse", 119, Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Inner Demon", 48, Rarity.UNCOMMON, mage.cards.i.InnerDemon.class));
        cards.add(new SetCardInfo("Island", 251, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jelenn Sphinx", 224, Rarity.UNCOMMON, mage.cards.j.JelennSphinx.class));
        cards.add(new SetCardInfo("Jubilant Mascot", 28, Rarity.UNCOMMON, mage.cards.j.JubilantMascot.class));
        cards.add(new SetCardInfo("Juggernaut", 238, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Jungle Wayfinder", 72, Rarity.COMMON, mage.cards.j.JungleWayfinder.class));
        cards.add(new SetCardInfo("Karametra's Favor", 203, Rarity.UNCOMMON, mage.cards.k.KarametrasFavor.class));
        cards.add(new SetCardInfo("Khorvath Brightflame", 9, Rarity.RARE, mage.cards.k.KhorvathBrightflame.class));
        cards.add(new SetCardInfo("Khorvath's Fury", 59, Rarity.RARE, mage.cards.k.KhorvathsFury.class));
        cards.add(new SetCardInfo("Kiss of the Amesha", 225, Rarity.UNCOMMON, mage.cards.k.KissOfTheAmesha.class));
        cards.add(new SetCardInfo("Kitesail Corsair", 120, Rarity.COMMON, mage.cards.k.KitesailCorsair.class));
        cards.add(new SetCardInfo("Kor Spiritdancer", 93, Rarity.RARE, mage.cards.k.KorSpiritdancer.class));
        cards.add(new SetCardInfo("Kraken Hatchling", 121, Rarity.COMMON, mage.cards.k.KrakenHatchling.class));
        cards.add(new SetCardInfo("Kraul Warrior", 204, Rarity.COMMON, mage.cards.k.KraulWarrior.class));
        cards.add(new SetCardInfo("Krav, the Unredeemed", 4, Rarity.RARE, mage.cards.k.KravTheUnredeemed.class));
        cards.add(new SetCardInfo("Land Tax", 94, Rarity.MYTHIC, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Last Gasp", 147, Rarity.COMMON, mage.cards.l.LastGasp.class));
        cards.add(new SetCardInfo("Last One Standing", 76, Rarity.RARE, mage.cards.l.LastOneStanding.class));
        cards.add(new SetCardInfo("Lava-Field Overlord", 60, Rarity.UNCOMMON, mage.cards.l.LavaFieldOverlord.class));
        cards.add(new SetCardInfo("Lead by Example", 205, Rarity.COMMON, mage.cards.l.LeadByExample.class));
        cards.add(new SetCardInfo("Ley Weaver", 21, Rarity.UNCOMMON, mage.cards.l.LeyWeaver.class));
        cards.add(new SetCardInfo("Lightning Talons", 180, Rarity.COMMON, mage.cards.l.LightningTalons.class));
        cards.add(new SetCardInfo("Lightwalker", 95, Rarity.COMMON, mage.cards.l.Lightwalker.class));
        cards.add(new SetCardInfo("Liturgy of Blood", 148, Rarity.COMMON, mage.cards.l.LiturgyOfBlood.class));
        cards.add(new SetCardInfo("Long Road Home", 96, Rarity.UNCOMMON, mage.cards.l.LongRoadHome.class));
        cards.add(new SetCardInfo("Lore Weaver", 22, Rarity.UNCOMMON, mage.cards.l.LoreWeaver.class));
        cards.add(new SetCardInfo("Loyal Pegasus", 97, Rarity.UNCOMMON, mage.cards.l.LoyalPegasus.class));
        cards.add(new SetCardInfo("Luxury Suite", 82, Rarity.RARE, mage.cards.l.LuxurySuite.class));
        cards.add(new SetCardInfo("Magma Hellion", 61, Rarity.COMMON, mage.cards.m.MagmaHellion.class));
        cards.add(new SetCardInfo("Magmatic Force", 181, Rarity.RARE, mage.cards.m.MagmaticForce.class));
        cards.add(new SetCardInfo("Magus of the Candelabra", 206, Rarity.RARE, mage.cards.m.MagusOfTheCandelabra.class));
        cards.add(new SetCardInfo("Mangara of Corondor", 98, Rarity.RARE, mage.cards.m.MangaraOfCorondor.class));
        cards.add(new SetCardInfo("Midnight Guard", 99, Rarity.COMMON, mage.cards.m.MidnightGuard.class));
        cards.add(new SetCardInfo("Millennial Gargoyle", 239, Rarity.COMMON, mage.cards.m.MillennialGargoyle.class));
        cards.add(new SetCardInfo("Mind's Eye", 240, Rarity.RARE, mage.cards.m.MindsEye.class));
        cards.add(new SetCardInfo("Mindblade Render", 49, Rarity.RARE, mage.cards.m.MindbladeRender.class));
        cards.add(new SetCardInfo("Morbid Curiosity", 149, Rarity.UNCOMMON, mage.cards.m.MorbidCuriosity.class));
        cards.add(new SetCardInfo("Morphic Pool", 83, Rarity.RARE, mage.cards.m.MorphicPool.class));
        cards.add(new SetCardInfo("Mountain", 253, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mycosynth Lattice", 241, Rarity.MYTHIC, mage.cards.m.MycosynthLattice.class));
        cards.add(new SetCardInfo("Mystic Confluence", 122, Rarity.RARE, mage.cards.m.MysticConfluence.class));
        cards.add(new SetCardInfo("Najeela, the Blade-Blossom", 62, Rarity.MYTHIC, mage.cards.n.NajeelaTheBladeBlossom.class));
        cards.add(new SetCardInfo("Negate", 123, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Night Market Guard", 242, Rarity.COMMON, mage.cards.n.NightMarketGuard.class));
        cards.add(new SetCardInfo("Nimbus Champion", 37, Rarity.UNCOMMON, mage.cards.n.NimbusChampion.class));
        cards.add(new SetCardInfo("Nimbus of the Isles", 124, Rarity.COMMON, mage.cards.n.NimbusOfTheIsles.class));
        cards.add(new SetCardInfo("Nirkana Revenant", 150, Rarity.MYTHIC, mage.cards.n.NirkanaRevenant.class));
        cards.add(new SetCardInfo("Noosegraf Mob", 151, Rarity.RARE, mage.cards.n.NoosegrafMob.class));
        cards.add(new SetCardInfo("Noxious Dragon", 152, Rarity.UNCOMMON, mage.cards.n.NoxiousDragon.class));
        cards.add(new SetCardInfo("Nyxathid", 153, Rarity.RARE, mage.cards.n.Nyxathid.class));
        cards.add(new SetCardInfo("Okaun, Eye of Chaos", 6, Rarity.RARE, mage.cards.o.OkaunEyeOfChaos.class));
        cards.add(new SetCardInfo("Omenspeaker", 125, Rarity.COMMON, mage.cards.o.Omenspeaker.class));
        cards.add(new SetCardInfo("Opportunity", 126, Rarity.UNCOMMON, mage.cards.o.Opportunity.class));
        cards.add(new SetCardInfo("Oracle's Insight", 127, Rarity.UNCOMMON, mage.cards.o.OraclesInsight.class));
        cards.add(new SetCardInfo("Oreskos Explorer", 100, Rarity.UNCOMMON, mage.cards.o.OreskosExplorer.class));
        cards.add(new SetCardInfo("Out of Bounds", 38, Rarity.UNCOMMON, mage.cards.o.OutOfBounds.class));
        cards.add(new SetCardInfo("Pacifism", 101, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Painful Lesson", 154, Rarity.COMMON, mage.cards.p.PainfulLesson.class));
        cards.add(new SetCardInfo("Pathmaker Initiate", 182, Rarity.COMMON, mage.cards.p.PathmakerInitiate.class));
        cards.add(new SetCardInfo("Peace Strider", 243, Rarity.COMMON, mage.cards.p.PeaceStrider.class));
        cards.add(new SetCardInfo("Peregrine Drake", 128, Rarity.UNCOMMON, mage.cards.p.PeregrineDrake.class));
        cards.add(new SetCardInfo("Phantom Warrior", 129, Rarity.UNCOMMON, mage.cards.p.PhantomWarrior.class));
        cards.add(new SetCardInfo("Pierce Strider", 244, Rarity.COMMON, mage.cards.p.PierceStrider.class));
        cards.add(new SetCardInfo("Pir's Whim", 73, Rarity.RARE, mage.cards.p.PirsWhim.class));
        cards.add(new SetCardInfo("Pir, Imaginative Rascal", 11, Rarity.RARE, mage.cards.p.PirImaginativeRascal.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plated Crusher", 207, Rarity.UNCOMMON, mage.cards.p.PlatedCrusher.class));
        cards.add(new SetCardInfo("Play of the Game", 29, Rarity.RARE, mage.cards.p.PlayOfTheGame.class));
        cards.add(new SetCardInfo("Prakhata Club Security", 155, Rarity.COMMON, mage.cards.p.PrakhataClubSecurity.class));
        cards.add(new SetCardInfo("Primal Huntbeast", 208, Rarity.COMMON, mage.cards.p.PrimalHuntbeast.class));
        cards.add(new SetCardInfo("Proud Mentor", 20, Rarity.UNCOMMON, mage.cards.p.ProudMentor.class));
        cards.add(new SetCardInfo("Pulse of Murasa", 209, Rarity.UNCOMMON, mage.cards.p.PulseOfMurasa.class));
        cards.add(new SetCardInfo("Quest for the Gravelord", 156, Rarity.UNCOMMON, mage.cards.q.QuestForTheGravelord.class));
        cards.add(new SetCardInfo("Raptor Companion", 102, Rarity.COMMON, mage.cards.r.RaptorCompanion.class));
        cards.add(new SetCardInfo("Rebuke", 103, Rarity.COMMON, mage.cards.r.Rebuke.class));
        cards.add(new SetCardInfo("Reckless Reveler", 183, Rarity.COMMON, mage.cards.r.RecklessReveler.class));
        cards.add(new SetCardInfo("Reckless Scholar", 130, Rarity.UNCOMMON, mage.cards.r.RecklessScholar.class));
        cards.add(new SetCardInfo("Regna's Sanction", 30, Rarity.RARE, mage.cards.r.RegnasSanction.class));
        cards.add(new SetCardInfo("Regna, the Redeemer", 3, Rarity.RARE, mage.cards.r.RegnaTheRedeemer.class));
        cards.add(new SetCardInfo("Relentless Hunter", 226, Rarity.UNCOMMON, mage.cards.r.RelentlessHunter.class));
        cards.add(new SetCardInfo("Return to the Earth", 210, Rarity.COMMON, mage.cards.r.ReturnToTheEarth.class));
        cards.add(new SetCardInfo("Rhox Brute", 227, Rarity.COMMON, mage.cards.r.RhoxBrute.class));
        cards.add(new SetCardInfo("Riptide Crab", 228, Rarity.COMMON, mage.cards.r.RiptideCrab.class));
        cards.add(new SetCardInfo("Rotfeaster Maggot", 157, Rarity.COMMON, mage.cards.r.RotfeasterMaggot.class));
        cards.add(new SetCardInfo("Rowan Kenrith", 2, Rarity.MYTHIC, mage.cards.r.RowanKenrith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rowan Kenrith", 256, Rarity.MYTHIC, mage.cards.r.RowanKenrith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Royal Trooper", 104, Rarity.COMMON, mage.cards.r.RoyalTrooper.class));
        cards.add(new SetCardInfo("Rushblade Commander", 77, Rarity.UNCOMMON, mage.cards.r.RushbladeCommander.class));
        cards.add(new SetCardInfo("Saddleback Lagac", 211, Rarity.COMMON, mage.cards.s.SaddlebackLagac.class));
        cards.add(new SetCardInfo("Saltwater Stalwart", 39, Rarity.COMMON, mage.cards.s.SaltwaterStalwart.class));
        cards.add(new SetCardInfo("Savage Ventmaw", 229, Rarity.UNCOMMON, mage.cards.s.SavageVentmaw.class));
        cards.add(new SetCardInfo("Screeching Buzzard", 158, Rarity.COMMON, mage.cards.s.ScreechingBuzzard.class));
        cards.add(new SetCardInfo("Sea of Clouds", 84, Rarity.RARE, mage.cards.s.SeaOfClouds.class));
        cards.add(new SetCardInfo("Seedborn Muse", 212, Rarity.RARE, mage.cards.s.SeedbornMuse.class));
        cards.add(new SetCardInfo("Seer's Lantern", 245, Rarity.COMMON, mage.cards.s.SeersLantern.class));
        cards.add(new SetCardInfo("Sentinel Tower", 79, Rarity.RARE, mage.cards.s.SentinelTower.class));
        cards.add(new SetCardInfo("Shambling Ghoul", 159, Rarity.COMMON, mage.cards.s.ShamblingGhoul.class));
        cards.add(new SetCardInfo("Shock", 184, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Shoulder to Shoulder", 105, Rarity.COMMON, mage.cards.s.ShoulderToShoulder.class));
        cards.add(new SetCardInfo("Sickle Dancer", 50, Rarity.COMMON, mage.cards.s.SickleDancer.class));
        cards.add(new SetCardInfo("Silverchase Fox", 106, Rarity.COMMON, mage.cards.s.SilverchaseFox.class));
        cards.add(new SetCardInfo("Skyshroud Claim", 213, Rarity.COMMON, mage.cards.s.SkyshroudClaim.class));
        cards.add(new SetCardInfo("Skystreamer", 31, Rarity.COMMON, mage.cards.s.Skystreamer.class));
        cards.add(new SetCardInfo("Slum Reaper", 160, Rarity.UNCOMMON, mage.cards.s.SlumReaper.class));
        cards.add(new SetCardInfo("Soaring Show-Off", 40, Rarity.COMMON, mage.cards.s.SoaringShowOff.class));
        cards.add(new SetCardInfo("Solemn Offering", 107, Rarity.UNCOMMON, mage.cards.s.SolemnOffering.class));
        cards.add(new SetCardInfo("Soulblade Corrupter", 17, Rarity.UNCOMMON, mage.cards.s.SoulbladeCorrupter.class));
        cards.add(new SetCardInfo("Soulblade Renewer", 18, Rarity.UNCOMMON, mage.cards.s.SoulbladeRenewer.class));
        cards.add(new SetCardInfo("Sower of Temptation", 131, Rarity.RARE, mage.cards.s.SowerOfTemptation.class));
        cards.add(new SetCardInfo("Sparring Mummy", 108, Rarity.COMMON, mage.cards.s.SparringMummy.class));
        cards.add(new SetCardInfo("Spectral Searchlight", 246, Rarity.UNCOMMON, mage.cards.s.SpectralSearchlight.class));
        cards.add(new SetCardInfo("Spell Snare", 132, Rarity.UNCOMMON, mage.cards.s.SpellSnare.class));
        cards.add(new SetCardInfo("Spellseeker", 41, Rarity.RARE, mage.cards.s.Spellseeker.class));
        cards.add(new SetCardInfo("Spellweaver Duo", 42, Rarity.COMMON, mage.cards.s.SpellweaverDuo.class));
        cards.add(new SetCardInfo("Spire Garden", 85, Rarity.RARE, mage.cards.s.SpireGarden.class));
        cards.add(new SetCardInfo("Stadium Vendors", 63, Rarity.COMMON, mage.cards.s.StadiumVendors.class));
        cards.add(new SetCardInfo("Steppe Glider", 109, Rarity.UNCOMMON, mage.cards.s.SteppeGlider.class));
        cards.add(new SetCardInfo("Stolen Strategy", 64, Rarity.RARE, mage.cards.s.StolenStrategy.class));
        cards.add(new SetCardInfo("Stone Golem", 247, Rarity.COMMON, mage.cards.s.StoneGolem.class));
        cards.add(new SetCardInfo("Stunning Reversal", 51, Rarity.MYTHIC, mage.cards.s.StunningReversal.class));
        cards.add(new SetCardInfo("Swamp", 252, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm of Bloodflies", 161, Rarity.UNCOMMON, mage.cards.s.SwarmOfBloodflies.class));
        cards.add(new SetCardInfo("Switcheroo", 133, Rarity.UNCOMMON, mage.cards.s.Switcheroo.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 110, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvia Brightspear", 10, Rarity.RARE, mage.cards.s.SylviaBrightspear.class));
        cards.add(new SetCardInfo("Take Up Arms", 111, Rarity.UNCOMMON, mage.cards.t.TakeUpArms.class));
        cards.add(new SetCardInfo("Tandem Tactics", 112, Rarity.COMMON, mage.cards.t.TandemTactics.class));
        cards.add(new SetCardInfo("Tavern Swindler", 162, Rarity.UNCOMMON, mage.cards.t.TavernSwindler.class));
        cards.add(new SetCardInfo("Tenacious Dead", 163, Rarity.UNCOMMON, mage.cards.t.TenaciousDead.class));
        cards.add(new SetCardInfo("The Crowd Goes Wild", 68, Rarity.UNCOMMON, mage.cards.t.TheCrowdGoesWild.class));
        cards.add(new SetCardInfo("Thrasher Brute", 52, Rarity.UNCOMMON, mage.cards.t.ThrasherBrute.class));
        cards.add(new SetCardInfo("Thrilling Encore", 53, Rarity.RARE, mage.cards.t.ThrillingEncore.class));
        cards.add(new SetCardInfo("Thunder Strike", 185, Rarity.COMMON, mage.cards.t.ThunderStrike.class));
        cards.add(new SetCardInfo("Tidespout Tyrant", 134, Rarity.RARE, mage.cards.t.TidespoutTyrant.class));
        cards.add(new SetCardInfo("Together Forever", 32, Rarity.RARE, mage.cards.t.TogetherForever.class));
        cards.add(new SetCardInfo("Toothy, Imaginary Friend", 12, Rarity.RARE, mage.cards.t.ToothyImaginaryFriend.class));
        cards.add(new SetCardInfo("Totally Lost", 135, Rarity.COMMON, mage.cards.t.TotallyLost.class));
        cards.add(new SetCardInfo("True-Name Nemesis", 136, Rarity.MYTHIC, mage.cards.t.TrueNameNemesis.class));
        cards.add(new SetCardInfo("Trumpet Blast", 186, Rarity.UNCOMMON, mage.cards.t.TrumpetBlast.class));
        cards.add(new SetCardInfo("Tyrant's Machine", 248, Rarity.COMMON, mage.cards.t.TyrantsMachine.class));
        cards.add(new SetCardInfo("Unflinching Courage", 230, Rarity.UNCOMMON, mage.cards.u.UnflinchingCourage.class));
        cards.add(new SetCardInfo("Urborg Drake", 231, Rarity.COMMON, mage.cards.u.UrborgDrake.class));
        cards.add(new SetCardInfo("Vampire Charmseeker", 78, Rarity.UNCOMMON, mage.cards.v.VampireCharmseeker.class));
        cards.add(new SetCardInfo("Veteran Explorer", 214, Rarity.UNCOMMON, mage.cards.v.VeteranExplorer.class));
        cards.add(new SetCardInfo("Victory Chimes", 80, Rarity.RARE, mage.cards.v.VictoryChimes.class));
        cards.add(new SetCardInfo("Vigor", 215, Rarity.RARE, mage.cards.v.Vigor.class));
        cards.add(new SetCardInfo("Virtus the Veiled", 7, Rarity.RARE, mage.cards.v.VirtusTheVeiled.class));
        cards.add(new SetCardInfo("Virtus's Maneuver", 54, Rarity.RARE, mage.cards.v.VirtussManeuver.class));
        cards.add(new SetCardInfo("Wandering Wolf", 216, Rarity.COMMON, mage.cards.w.WanderingWolf.class));
        cards.add(new SetCardInfo("War's Toll", 187, Rarity.RARE, mage.cards.w.WarsToll.class));
        cards.add(new SetCardInfo("Watercourser", 137, Rarity.COMMON, mage.cards.w.Watercourser.class));
        cards.add(new SetCardInfo("Will Kenrith", 1, Rarity.MYTHIC, mage.cards.w.WillKenrith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Will Kenrith", 255, Rarity.MYTHIC, mage.cards.w.WillKenrith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrap in Flames", 188, Rarity.COMMON, mage.cards.w.WrapInFlames.class));
        cards.add(new SetCardInfo("Yotian Soldier", 249, Rarity.COMMON, mage.cards.y.YotianSoldier.class));
        cards.add(new SetCardInfo("Zndrsplt's Judgment", 43, Rarity.RARE, mage.cards.z.ZndrspltsJudgment.class));
        cards.add(new SetCardInfo("Zndrsplt, Eye of Wisdom", 5, Rarity.RARE, mage.cards.z.ZndrspltEyeOfWisdom.class));
    }

    @Override
    public List<Card> tryBooster() {
        List<Card> booster = new ArrayList<>();
        boolean partnerAllowed = true;
        List<CardInfo> uncommons = getCardsByRarity(Rarity.UNCOMMON);
        for (int i = 0; i < numBoosterUncommon; i++) {
            while (true) {
                addToBooster(booster, uncommons);
                int check = addMissingPartner(booster, partnerAllowed, numBoosterUncommon - 1, i);
                if (check == 1) {
                    break;
                }
                if (check == 2) {
                    partnerAllowed = false;
                    //Be sure to account for the added card
                    if (i != numBoosterUncommon - 1) {
                        i += 1;
                    }
                    break;
                }
            }
        }

        List<CardInfo> commons = getCardsByRarity(Rarity.COMMON);
        for (int i = 0; i < numBoosterCommon; i++) {
            addToBooster(booster, commons);
        }

        List<CardInfo> rares = getCardsByRarity(Rarity.RARE);
        List<CardInfo> mythics = getCardsByRarity(Rarity.MYTHIC);
        for (int i = 0; i < numBoosterRare; i++) {
            List<CardInfo> cards = (checkMythic() ? mythics : rares);
            while (true) {
                addToBooster(booster, cards);
                int check = addMissingPartner(booster, partnerAllowed, -1, 1);
                if (check == 1) {
                    break;
                }
                if (check == 2) {
                    partnerAllowed = false;
                    break;
                }
            }
        }

        List<CardInfo> basicLands = getCardsByRarity(Rarity.LAND);
        for (int i = 0; i < numBoosterLands; i++) {
            addToBooster(booster, basicLands);
        }

        return booster;
    }

    private int addMissingPartner(List<Card> booster, boolean partnerAllowed, int max, int i) {

        Card sourceCard = booster.get(booster.size() - 1);
        for (Ability ability : sourceCard.getAbilities()) {

            //Check if fetched card has the PartnerWithAbility
            if (ability instanceof PartnerWithAbility) {
                String partnerName = ((PartnerWithAbility) ability).getPartnerName();
                //Check if the pack already contains a partner pair
                if (partnerAllowed) {
                    //Added card always replaces an uncommon card
                    Card card = CardRepository.instance.findCardWPreferredSet(partnerName, sourceCard.getExpansionSetCode(), false).getCard();
                    if (i < max) {
                        booster.add(card);
                    } else {
                        booster.set(0, card);
                    }
                    //2 return value indicates found partner
                    return 2;
                } else {
                    //If partner already exists, remove card and loop again
                    booster.remove(booster.size() - 1);
                    return 0;
                }
            }
        }
        return 1;
    }
}
