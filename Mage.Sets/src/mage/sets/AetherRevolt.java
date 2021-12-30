package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fireshoes
 */
public final class AetherRevolt extends ExpansionSet {

    private static final AetherRevolt instance = new AetherRevolt();

    public static AetherRevolt getInstance() {
        return instance;
    }

    private AetherRevolt() {
        super("Aether Revolt", "AER", ExpansionSet.buildDate(2017, 1, 20), SetType.EXPANSION);
        this.blockName = "Kaladesh";
        this.parentSet = Kaladesh.getInstance();
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialCommon = 144;
        this.maxCardNumberInBooster = 184;

        cards.add(new SetCardInfo("Aegis Automaton", 141, Rarity.COMMON, mage.cards.a.AegisAutomaton.class));
        cards.add(new SetCardInfo("Aerial Modification", 1, Rarity.UNCOMMON, mage.cards.a.AerialModification.class));
        cards.add(new SetCardInfo("Aeronaut Admiral", 2, Rarity.UNCOMMON, mage.cards.a.AeronautAdmiral.class));
        cards.add(new SetCardInfo("Aether Chaser", 76, Rarity.COMMON, mage.cards.a.AetherChaser.class));
        cards.add(new SetCardInfo("Aether Herder", 102, Rarity.COMMON, mage.cards.a.AetherHerder.class));
        cards.add(new SetCardInfo("Aether Inspector", 3, Rarity.COMMON, mage.cards.a.AetherInspector.class));
        cards.add(new SetCardInfo("Aether Poisoner", 51, Rarity.COMMON, mage.cards.a.AetherPoisoner.class));
        cards.add(new SetCardInfo("Aether Swooper", 26, Rarity.COMMON, mage.cards.a.AetherSwooper.class));
        cards.add(new SetCardInfo("Aethergeode Miner", 4, Rarity.RARE, mage.cards.a.AethergeodeMiner.class));
        cards.add(new SetCardInfo("Aethersphere Harvester", 142, Rarity.RARE, mage.cards.a.AethersphereHarvester.class));
        cards.add(new SetCardInfo("Aetherstream Leopard", 103, Rarity.COMMON, mage.cards.a.AetherstreamLeopard.class));
        cards.add(new SetCardInfo("Aethertide Whale", 27, Rarity.RARE, mage.cards.a.AethertideWhale.class));
        cards.add(new SetCardInfo("Aetherwind Basker", 104, Rarity.MYTHIC, mage.cards.a.AetherwindBasker.class));
        cards.add(new SetCardInfo("Aid from the Cowl", 105, Rarity.RARE, mage.cards.a.AidFromTheCowl.class));
        cards.add(new SetCardInfo("Airdrop Aeronauts", 5, Rarity.UNCOMMON, mage.cards.a.AirdropAeronauts.class));
        cards.add(new SetCardInfo("Ajani Unyielding", 127, Rarity.MYTHIC, mage.cards.a.AjaniUnyielding.class));
        cards.add(new SetCardInfo("Ajani's Aid", 188, Rarity.RARE, mage.cards.a.AjanisAid.class));
        cards.add(new SetCardInfo("Ajani's Comrade", 187, Rarity.UNCOMMON, mage.cards.a.AjanisComrade.class));
        cards.add(new SetCardInfo("Ajani, Valiant Protector", 185, Rarity.MYTHIC, mage.cards.a.AjaniValiantProtector.class));
        cards.add(new SetCardInfo("Alley Evasion", 6, Rarity.COMMON, mage.cards.a.AlleyEvasion.class));
        cards.add(new SetCardInfo("Alley Strangler", "52+", Rarity.COMMON, mage.cards.a.AlleyStrangler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alley Strangler", 52, Rarity.COMMON, mage.cards.a.AlleyStrangler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Audacious Infiltrator", 7, Rarity.COMMON, mage.cards.a.AudaciousInfiltrator.class));
        cards.add(new SetCardInfo("Augmenting Automaton", 143, Rarity.COMMON, mage.cards.a.AugmentingAutomaton.class));
        cards.add(new SetCardInfo("Baral's Expertise", 29, Rarity.RARE, mage.cards.b.BaralsExpertise.class));
        cards.add(new SetCardInfo("Baral, Chief of Compliance", 28, Rarity.RARE, mage.cards.b.BaralChiefOfCompliance.class));
        cards.add(new SetCardInfo("Barricade Breaker", 144, Rarity.UNCOMMON, mage.cards.b.BarricadeBreaker.class));
        cards.add(new SetCardInfo("Bastion Enforcer", 8, Rarity.COMMON, mage.cards.b.BastionEnforcer.class));
        cards.add(new SetCardInfo("Bastion Inventor", 30, Rarity.COMMON, mage.cards.b.BastionInventor.class));
        cards.add(new SetCardInfo("Battle at the Bridge", 53, Rarity.RARE, mage.cards.b.BattleAtTheBridge.class));
        cards.add(new SetCardInfo("Call for Unity", 9, Rarity.RARE, mage.cards.c.CallForUnity.class));
        cards.add(new SetCardInfo("Caught in the Brights", 10, Rarity.COMMON, mage.cards.c.CaughtInTheBrights.class));
        cards.add(new SetCardInfo("Chandra's Revolution", 77, Rarity.COMMON, mage.cards.c.ChandrasRevolution.class));
        cards.add(new SetCardInfo("Cogwork Assembler", 145, Rarity.UNCOMMON, mage.cards.c.CogworkAssembler.class));
        cards.add(new SetCardInfo("Consulate Crackdown", 11, Rarity.RARE, mage.cards.c.ConsulateCrackdown.class));
        cards.add(new SetCardInfo("Consulate Dreadnought", 146, Rarity.UNCOMMON, mage.cards.c.ConsulateDreadnought.class));
        cards.add(new SetCardInfo("Consulate Turret", 147, Rarity.COMMON, mage.cards.c.ConsulateTurret.class));
        cards.add(new SetCardInfo("Conviction", 12, Rarity.COMMON, mage.cards.c.Conviction.class));
        cards.add(new SetCardInfo("Countless Gears Renegade", 13, Rarity.COMMON, mage.cards.c.CountlessGearsRenegade.class));
        cards.add(new SetCardInfo("Crackdown Construct", 148, Rarity.UNCOMMON, mage.cards.c.CrackdownConstruct.class));
        cards.add(new SetCardInfo("Cruel Finality", 54, Rarity.COMMON, mage.cards.c.CruelFinality.class));
        cards.add(new SetCardInfo("Daredevil Dragster", 149, Rarity.UNCOMMON, mage.cards.d.DaredevilDragster.class));
        cards.add(new SetCardInfo("Daring Demolition", 55, Rarity.COMMON, mage.cards.d.DaringDemolition.class));
        cards.add(new SetCardInfo("Dark Intimations", 128, Rarity.RARE, mage.cards.d.DarkIntimations.class));
        cards.add(new SetCardInfo("Dawnfeather Eagle", "14+", Rarity.COMMON, mage.cards.d.DawnfeatherEagle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dawnfeather Eagle", 14, Rarity.COMMON, mage.cards.d.DawnfeatherEagle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deadeye Harpooner", 15, Rarity.UNCOMMON, mage.cards.d.DeadeyeHarpooner.class));
        cards.add(new SetCardInfo("Decommission", 16, Rarity.COMMON, mage.cards.d.Decommission.class));
        cards.add(new SetCardInfo("Defiant Salvager", 56, Rarity.COMMON, mage.cards.d.DefiantSalvager.class));
        cards.add(new SetCardInfo("Deft Dismissal", 17, Rarity.UNCOMMON, mage.cards.d.DeftDismissal.class));
        cards.add(new SetCardInfo("Destructive Tampering", 78, Rarity.COMMON, mage.cards.d.DestructiveTampering.class));
        cards.add(new SetCardInfo("Disallow", 31, Rarity.RARE, mage.cards.d.Disallow.class));
        cards.add(new SetCardInfo("Dispersal Technician", 32, Rarity.COMMON, mage.cards.d.DispersalTechnician.class));
        cards.add(new SetCardInfo("Druid of the Cowl", 106, Rarity.COMMON, mage.cards.d.DruidOfTheCowl.class));
        cards.add(new SetCardInfo("Efficient Construction", 33, Rarity.UNCOMMON, mage.cards.e.EfficientConstruction.class));
        cards.add(new SetCardInfo("Embraal Gear-Smasher", 79, Rarity.COMMON, mage.cards.e.EmbraalGearSmasher.class));
        cards.add(new SetCardInfo("Enraged Giant", 80, Rarity.UNCOMMON, mage.cards.e.EnragedGiant.class));
        cards.add(new SetCardInfo("Exquisite Archangel", 18, Rarity.MYTHIC, mage.cards.e.ExquisiteArchangel.class));
        cards.add(new SetCardInfo("Fatal Push", 57, Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Felidar Guardian", 19, Rarity.UNCOMMON, mage.cards.f.FelidarGuardian.class));
        cards.add(new SetCardInfo("Fen Hauler", 58, Rarity.COMMON, mage.cards.f.FenHauler.class));
        cards.add(new SetCardInfo("Filigree Crawler", 150, Rarity.COMMON, mage.cards.f.FiligreeCrawler.class));
        cards.add(new SetCardInfo("Foundry Assembler", 151, Rarity.COMMON, mage.cards.f.FoundryAssembler.class));
        cards.add(new SetCardInfo("Foundry Hornet", 59, Rarity.UNCOMMON, mage.cards.f.FoundryHornet.class));
        cards.add(new SetCardInfo("Fourth Bridge Prowler", 60, Rarity.COMMON, mage.cards.f.FourthBridgeProwler.class));
        cards.add(new SetCardInfo("Freejam Regent", 81, Rarity.RARE, mage.cards.f.FreejamRegent.class));
        cards.add(new SetCardInfo("Frontline Rebel", 82, Rarity.COMMON, mage.cards.f.FrontlineRebel.class));
        cards.add(new SetCardInfo("Ghirapur Osprey", 20, Rarity.COMMON, mage.cards.g.GhirapurOsprey.class));
        cards.add(new SetCardInfo("Gifted Aetherborn", 61, Rarity.UNCOMMON, mage.cards.g.GiftedAetherborn.class));
        cards.add(new SetCardInfo("Glint-Sleeve Siphoner", 62, Rarity.RARE, mage.cards.g.GlintSleeveSiphoner.class));
        cards.add(new SetCardInfo("Gonti's Aether Heart", 152, Rarity.MYTHIC, mage.cards.g.GontisAetherHeart.class));
        cards.add(new SetCardInfo("Gonti's Machinations", 63, Rarity.UNCOMMON, mage.cards.g.GontisMachinations.class));
        cards.add(new SetCardInfo("Greenbelt Rampager", 107, Rarity.RARE, mage.cards.g.GreenbeltRampager.class));
        cards.add(new SetCardInfo("Greenwheel Liberator", 108, Rarity.RARE, mage.cards.g.GreenwheelLiberator.class));
        cards.add(new SetCardInfo("Gremlin Infestation", 83, Rarity.UNCOMMON, mage.cards.g.GremlinInfestation.class));
        cards.add(new SetCardInfo("Heart of Kiran", 153, Rarity.MYTHIC, mage.cards.h.HeartOfKiran.class));
        cards.add(new SetCardInfo("Herald of Anguish", 64, Rarity.MYTHIC, mage.cards.h.HeraldOfAnguish.class));
        cards.add(new SetCardInfo("Heroic Intervention", 109, Rarity.RARE, mage.cards.h.HeroicIntervention.class));
        cards.add(new SetCardInfo("Hidden Herbalists", 110, Rarity.UNCOMMON, mage.cards.h.HiddenHerbalists.class));
        cards.add(new SetCardInfo("Hidden Stockpile", 129, Rarity.UNCOMMON, mage.cards.h.HiddenStockpile.class));
        cards.add(new SetCardInfo("Highspire Infusion", 111, Rarity.COMMON, mage.cards.h.HighspireInfusion.class));
        cards.add(new SetCardInfo("Hinterland Drake", 34, Rarity.COMMON, mage.cards.h.HinterlandDrake.class));
        cards.add(new SetCardInfo("Hope of Ghirapur", 154, Rarity.RARE, mage.cards.h.HopeOfGhirapur.class));
        cards.add(new SetCardInfo("Hungry Flames", 84, Rarity.UNCOMMON, mage.cards.h.HungryFlames.class));
        cards.add(new SetCardInfo("Ice Over", 35, Rarity.COMMON, mage.cards.i.IceOver.class));
        cards.add(new SetCardInfo("Illusionist's Stratagem", 36, Rarity.UNCOMMON, mage.cards.i.IllusionistsStratagem.class));
        cards.add(new SetCardInfo("Implement of Combustion", 155, Rarity.COMMON, mage.cards.i.ImplementOfCombustion.class));
        cards.add(new SetCardInfo("Implement of Examination", 156, Rarity.COMMON, mage.cards.i.ImplementOfExamination.class));
        cards.add(new SetCardInfo("Implement of Ferocity", 157, Rarity.COMMON, mage.cards.i.ImplementOfFerocity.class));
        cards.add(new SetCardInfo("Implement of Improvement", 158, Rarity.COMMON, mage.cards.i.ImplementOfImprovement.class));
        cards.add(new SetCardInfo("Implement of Malice", 159, Rarity.COMMON, mage.cards.i.ImplementOfMalice.class));
        cards.add(new SetCardInfo("Indomitable Creativity", 85, Rarity.MYTHIC, mage.cards.i.IndomitableCreativity.class));
        cards.add(new SetCardInfo("Inspiring Roar", 186, Rarity.COMMON, mage.cards.i.InspiringRoar.class));
        cards.add(new SetCardInfo("Inspiring Statuary", 160, Rarity.RARE, mage.cards.i.InspiringStatuary.class));
        cards.add(new SetCardInfo("Invigorated Rampage", 86, Rarity.UNCOMMON, mage.cards.i.InvigoratedRampage.class));
        cards.add(new SetCardInfo("Ironclad Revolutionary", 65, Rarity.UNCOMMON, mage.cards.i.IroncladRevolutionary.class));
        cards.add(new SetCardInfo("Irontread Crusher", 161, Rarity.COMMON, mage.cards.i.IrontreadCrusher.class));
        cards.add(new SetCardInfo("Kari Zev's Expertise", 88, Rarity.RARE, mage.cards.k.KariZevsExpertise.class));
        cards.add(new SetCardInfo("Kari Zev, Skyship Raider", 87, Rarity.RARE, mage.cards.k.KariZevSkyshipRaider.class));
        cards.add(new SetCardInfo("Lathnu Sailback", 89, Rarity.COMMON, mage.cards.l.LathnuSailback.class));
        cards.add(new SetCardInfo("Leave in the Dust", 37, Rarity.COMMON, mage.cards.l.LeaveInTheDust.class));
        cards.add(new SetCardInfo("Lifecraft Awakening", 112, Rarity.UNCOMMON, mage.cards.l.LifecraftAwakening.class));
        cards.add(new SetCardInfo("Lifecraft Cavalry", 113, Rarity.COMMON, mage.cards.l.LifecraftCavalry.class));
        cards.add(new SetCardInfo("Lifecrafter's Bestiary", 162, Rarity.RARE, mage.cards.l.LifecraftersBestiary.class));
        cards.add(new SetCardInfo("Lifecrafter's Gift", 114, Rarity.UNCOMMON, mage.cards.l.LifecraftersGift.class));
        cards.add(new SetCardInfo("Lightning Runner", 90, Rarity.MYTHIC, mage.cards.l.LightningRunner.class));
        cards.add(new SetCardInfo("Maulfist Revolutionary", 115, Rarity.UNCOMMON, mage.cards.m.MaulfistRevolutionary.class));
        cards.add(new SetCardInfo("Maverick Thopterist", 130, Rarity.UNCOMMON, mage.cards.m.MaverickThopterist.class));
        cards.add(new SetCardInfo("Mechanized Production", 38, Rarity.MYTHIC, mage.cards.m.MechanizedProduction.class));
        cards.add(new SetCardInfo("Merchant's Dockhand", 163, Rarity.RARE, mage.cards.m.MerchantsDockhand.class));
        cards.add(new SetCardInfo("Metallic Mimic", 164, Rarity.RARE, mage.cards.m.MetallicMimic.class));
        cards.add(new SetCardInfo("Metallic Rebuke", 39, Rarity.COMMON, mage.cards.m.MetallicRebuke.class));
        cards.add(new SetCardInfo("Midnight Entourage", 66, Rarity.RARE, mage.cards.m.MidnightEntourage.class));
        cards.add(new SetCardInfo("Mobile Garrison", 165, Rarity.COMMON, mage.cards.m.MobileGarrison.class));
        cards.add(new SetCardInfo("Monstrous Onslaught", 116, Rarity.UNCOMMON, mage.cards.m.MonstrousOnslaught.class));
        cards.add(new SetCardInfo("Narnam Renegade", 117, Rarity.UNCOMMON, mage.cards.n.NarnamRenegade.class));
        cards.add(new SetCardInfo("Natural Obsolescence", 118, Rarity.COMMON, mage.cards.n.NaturalObsolescence.class));
        cards.add(new SetCardInfo("Negate", 40, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Night Market Aeronaut", 67, Rarity.COMMON, mage.cards.n.NightMarketAeronaut.class));
        cards.add(new SetCardInfo("Night Market Guard", 166, Rarity.COMMON, mage.cards.n.NightMarketGuard.class));
        cards.add(new SetCardInfo("Oath of Ajani", 131, Rarity.RARE, mage.cards.o.OathOfAjani.class));
        cards.add(new SetCardInfo("Ornithopter", 167, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Outland Boar", 132, Rarity.UNCOMMON, mage.cards.o.OutlandBoar.class));
        cards.add(new SetCardInfo("Pacification Array", 168, Rarity.UNCOMMON, mage.cards.p.PacificationArray.class));
        cards.add(new SetCardInfo("Paradox Engine", 169, Rarity.MYTHIC, mage.cards.p.ParadoxEngine.class));
        cards.add(new SetCardInfo("Peacewalker Colossus", 170, Rarity.RARE, mage.cards.p.PeacewalkerColossus.class));
        cards.add(new SetCardInfo("Peema Aether-Seer", 119, Rarity.UNCOMMON, mage.cards.p.PeemaAetherSeer.class));
        cards.add(new SetCardInfo("Pendulum of Patterns", 192, Rarity.COMMON, mage.cards.p.PendulumOfPatterns.class));
        cards.add(new SetCardInfo("Perilous Predicament", 68, Rarity.UNCOMMON, mage.cards.p.PerilousPredicament.class));
        cards.add(new SetCardInfo("Pia's Revolution", 91, Rarity.RARE, mage.cards.p.PiasRevolution.class));
        cards.add(new SetCardInfo("Planar Bridge", 171, Rarity.MYTHIC, mage.cards.p.PlanarBridge.class));
        cards.add(new SetCardInfo("Precise Strike", 92, Rarity.COMMON, mage.cards.p.PreciseStrike.class));
        cards.add(new SetCardInfo("Prey Upon", 120, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Prizefighter Construct", 172, Rarity.COMMON, mage.cards.p.PrizefighterConstruct.class));
        cards.add(new SetCardInfo("Quicksmith Rebel", 93, Rarity.RARE, mage.cards.q.QuicksmithRebel.class));
        cards.add(new SetCardInfo("Quicksmith Spy", 41, Rarity.RARE, mage.cards.q.QuicksmithSpy.class));
        cards.add(new SetCardInfo("Ravenous Intruder", 94, Rarity.UNCOMMON, mage.cards.r.RavenousIntruder.class));
        cards.add(new SetCardInfo("Reckless Racer", 95, Rarity.UNCOMMON, mage.cards.r.RecklessRacer.class));
        cards.add(new SetCardInfo("Release the Gremlins", 96, Rarity.RARE, mage.cards.r.ReleaseTheGremlins.class));
        cards.add(new SetCardInfo("Renegade Map", 173, Rarity.COMMON, mage.cards.r.RenegadeMap.class));
        cards.add(new SetCardInfo("Renegade Rallier", 133, Rarity.UNCOMMON, mage.cards.r.RenegadeRallier.class));
        cards.add(new SetCardInfo("Renegade Wheelsmith", 134, Rarity.UNCOMMON, mage.cards.r.RenegadeWheelsmith.class));
        cards.add(new SetCardInfo("Renegade's Getaway", 69, Rarity.COMMON, mage.cards.r.RenegadesGetaway.class));
        cards.add(new SetCardInfo("Reservoir Walker", 174, Rarity.COMMON, mage.cards.r.ReservoirWalker.class));
        cards.add(new SetCardInfo("Resourceful Return", 70, Rarity.COMMON, mage.cards.r.ResourcefulReturn.class));
        cards.add(new SetCardInfo("Restoration Specialist", 21, Rarity.UNCOMMON, mage.cards.r.RestorationSpecialist.class));
        cards.add(new SetCardInfo("Reverse Engineer", 42, Rarity.UNCOMMON, mage.cards.r.ReverseEngineer.class));
        cards.add(new SetCardInfo("Ridgescale Tusker", 121, Rarity.UNCOMMON, mage.cards.r.RidgescaleTusker.class));
        cards.add(new SetCardInfo("Rishkar's Expertise", 123, Rarity.RARE, mage.cards.r.RishkarsExpertise.class));
        cards.add(new SetCardInfo("Rishkar, Peema Renegade", 122, Rarity.RARE, mage.cards.r.RishkarPeemaRenegade.class));
        cards.add(new SetCardInfo("Rogue Refiner", 135, Rarity.UNCOMMON, mage.cards.r.RogueRefiner.class));
        cards.add(new SetCardInfo("Salvage Scuttler", 43, Rarity.UNCOMMON, mage.cards.s.SalvageScuttler.class));
        cards.add(new SetCardInfo("Scrap Trawler", 175, Rarity.RARE, mage.cards.s.ScrapTrawler.class));
        cards.add(new SetCardInfo("Scrapper Champion", 97, Rarity.UNCOMMON, mage.cards.s.ScrapperChampion.class));
        cards.add(new SetCardInfo("Scrounging Bandar", 124, Rarity.COMMON, mage.cards.s.ScroungingBandar.class));
        cards.add(new SetCardInfo("Secret Salvage", 71, Rarity.RARE, mage.cards.s.SecretSalvage.class));
        cards.add(new SetCardInfo("Servo Schematic", 176, Rarity.UNCOMMON, mage.cards.s.ServoSchematic.class));
        cards.add(new SetCardInfo("Shielded Aether Thief", 44, Rarity.UNCOMMON, mage.cards.s.ShieldedAetherThief.class));
        cards.add(new SetCardInfo("Shipwreck Moray", 45, Rarity.COMMON, mage.cards.s.ShipwreckMoray.class));
        cards.add(new SetCardInfo("Shock", 98, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Siege Modification", 99, Rarity.UNCOMMON, mage.cards.s.SiegeModification.class));
        cards.add(new SetCardInfo("Silkweaver Elite", 125, Rarity.COMMON, mage.cards.s.SilkweaverElite.class));
        cards.add(new SetCardInfo("Skyship Plunderer", 46, Rarity.UNCOMMON, mage.cards.s.SkyshipPlunderer.class));
        cards.add(new SetCardInfo("Sly Requisitioner", 72, Rarity.UNCOMMON, mage.cards.s.SlyRequisitioner.class));
        cards.add(new SetCardInfo("Solemn Recruit", 22, Rarity.RARE, mage.cards.s.SolemnRecruit.class));
        cards.add(new SetCardInfo("Spire of Industry", 184, Rarity.RARE, mage.cards.s.SpireOfIndustry.class));
        cards.add(new SetCardInfo("Spire Patrol", 136, Rarity.UNCOMMON, mage.cards.s.SpirePatrol.class));
        cards.add(new SetCardInfo("Sram's Expertise", 24, Rarity.RARE, mage.cards.s.SramsExpertise.class));
        cards.add(new SetCardInfo("Sram, Senior Edificer", 23, Rarity.RARE, mage.cards.s.SramSeniorEdificer.class));
        cards.add(new SetCardInfo("Submerged Boneyard", 194, Rarity.COMMON, mage.cards.s.SubmergedBoneyard.class));
        cards.add(new SetCardInfo("Sweatworks Brawler", 100, Rarity.COMMON, mage.cards.s.SweatworksBrawler.class));
        cards.add(new SetCardInfo("Take into Custody", 47, Rarity.COMMON, mage.cards.t.TakeIntoCustody.class));
        cards.add(new SetCardInfo("Tezzeret the Schemer", 137, Rarity.MYTHIC, mage.cards.t.TezzeretTheSchemer.class));
        cards.add(new SetCardInfo("Tezzeret's Betrayal", 191, Rarity.RARE, mage.cards.t.TezzeretsBetrayal.class));
        cards.add(new SetCardInfo("Tezzeret's Simulacrum", 193, Rarity.UNCOMMON, mage.cards.t.TezzeretsSimulacrum.class));
        cards.add(new SetCardInfo("Tezzeret's Touch", 138, Rarity.UNCOMMON, mage.cards.t.TezzeretsTouch.class));
        cards.add(new SetCardInfo("Tezzeret, Master of Metal", 190, Rarity.MYTHIC, mage.cards.t.TezzeretMasterOfMetal.class));
        cards.add(new SetCardInfo("Thopter Arrest", 25, Rarity.UNCOMMON, mage.cards.t.ThopterArrest.class));
        cards.add(new SetCardInfo("Tranquil Expanse", 189, Rarity.COMMON, mage.cards.t.TranquilExpanse.class));
        cards.add(new SetCardInfo("Treasure Keeper", 177, Rarity.UNCOMMON, mage.cards.t.TreasureKeeper.class));
        cards.add(new SetCardInfo("Trophy Mage", 48, Rarity.UNCOMMON, mage.cards.t.TrophyMage.class));
        cards.add(new SetCardInfo("Unbridled Growth", 126, Rarity.COMMON, mage.cards.u.UnbridledGrowth.class));
        cards.add(new SetCardInfo("Universal Solvent", 178, Rarity.COMMON, mage.cards.u.UniversalSolvent.class));
        cards.add(new SetCardInfo("Untethered Express", 179, Rarity.UNCOMMON, mage.cards.u.UntetheredExpress.class));
        cards.add(new SetCardInfo("Vengeful Rebel", 73, Rarity.UNCOMMON, mage.cards.v.VengefulRebel.class));
        cards.add(new SetCardInfo("Verdant Automaton", 180, Rarity.COMMON, mage.cards.v.VerdantAutomaton.class));
        cards.add(new SetCardInfo("Walking Ballista", 181, Rarity.RARE, mage.cards.w.WalkingBallista.class));
        cards.add(new SetCardInfo("Watchful Automaton", 182, Rarity.COMMON, mage.cards.w.WatchfulAutomaton.class));
        cards.add(new SetCardInfo("Welder Automaton", 183, Rarity.COMMON, mage.cards.w.WelderAutomaton.class));
        cards.add(new SetCardInfo("Weldfast Engineer", 139, Rarity.UNCOMMON, mage.cards.w.WeldfastEngineer.class));
        cards.add(new SetCardInfo("Whir of Invention", 49, Rarity.RARE, mage.cards.w.WhirOfInvention.class));
        cards.add(new SetCardInfo("Wind-Kin Raiders", 50, Rarity.UNCOMMON, mage.cards.w.WindKinRaiders.class));
        cards.add(new SetCardInfo("Winding Constrictor", 140, Rarity.UNCOMMON, mage.cards.w.WindingConstrictor.class));
        cards.add(new SetCardInfo("Wrangle", "101+", Rarity.COMMON, mage.cards.w.Wrangle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrangle", 101, Rarity.COMMON, mage.cards.w.Wrangle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yahenni's Expertise", 75, Rarity.RARE, mage.cards.y.YahennisExpertise.class));
        cards.add(new SetCardInfo("Yahenni, Undying Partisan", 74, Rarity.RARE, mage.cards.y.YahenniUndyingPartisan.class));
    }

    @Override
    protected List<CardInfo> findCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findCardsByRarity(rarity);
        if (rarity == Rarity.SPECIAL) {
            cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria().setCodes("MPS").minCardNumber(31)));
        }
        return cardInfos;
    }

    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        CardRepository
                .instance
                .findCards(new CardCriteria().setCodes("MPS").minCardNumber(31))
                .stream()
                .forEach(cardInfo -> inBoosterMap.put("MPS_" + cardInfo.getCardNumber(), cardInfo));
    }

    @Override
    public BoosterCollator createCollator() {
        return new AetherRevoltCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/aer.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class AetherRevoltCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "10", "92", "26", "6", "77", "39", "14", "79", "37", "16", "82", "32", "7", "98", "47", "20", "101", "8", "12", "76", "45", "10", "92", "34", "6", "77", "39", "7", "82", "32", "14", "79", "26", "16", "76", "37", "12", "98", "47", "20", "101", "34", "8", "77", "45", "7", "82", "39", "10", "92", "26", "6", "76", "37", "16", "79", "32", "14", "101", "34", "12", "20", "47", "8", "98", "45");
    private final CardRun commonB = new CardRun(true, "54", "111", "70", "106", "52", "126", "67", "102", "58", "125", "60", "120", "56", "124", "55", "118", "70", "113", "69", "111", "54", "126", "52", "106", "60", "102", "67", "125", "56", "120", "69", "113", "58", "118", "70", "106", "52", "124", "55", "120", "56", "111", "54", "126", "60", "113", "67", "102", "69", "125", "55", "118", "58", "124");
    private final CardRun commonC1 = new CardRun(true, "165", "147", "166", "51", "158", "103", "172", "30", "173", "183", "89", "161", "178", "166", "78", "151", "40", "172", "100", "173", "158", "51", "178", "30", "165", "89", "161", "40", "147", "103", "183", "151", "51", "166", "172", "165", "100", "173", "30", "178", "89", "158", "183", "103", "161", "147", "166", "40", "173", "78", "172", "51", "151", "158", "100", "147", "30", "183", "89", "165", "103", "178", "100", "161", "151", "40");
    private final CardRun commonC2 = new CardRun(true, "155", "180", "143", "3", "156", "141", "157", "13", "159", "174", "78", "150", "143", "155", "35", "180", "182", "141", "13", "157", "156", "3", "159", "174", "150", "35", "155", "141", "180", "13", "182", "143", "3", "157", "156", "174", "78", "150", "159", "141", "13", "155", "180", "35", "182", "143", "156", "3", "157", "174", "159", "150", "182", "35");
    private final CardRun uncommonA = new CardRun(true, "110", "59", "133", "86", "44", "21", "177", "135", "114", "140", "94", "48", "17", "132", "65", "149", "139", "83", "46", "2", "167", "61", "117", "133", "97", "43", "21", "145", "63", "115", "135", "86", "179", "15", "167", "59", "110", "140", "94", "44", "2", "177", "61", "115", "133", "97", "48", "17", "145", "65", "110", "132", "86", "43", "15", "149", "59", "114", "139", "83", "46", "2", "179", "61", "115", "135", "97", "48", "21", "177", "63", "117", "140", "94", "44", "149", "65", "114", "132", "83", "46", "17", "167", "59", "110", "133", "94", "44", "21", "145", "63", "117", "139", "177", "86", "43", "15", "179", "65", "115", "135", "83", "48", "17", "140", "149", "63", "114", "132", "97", "46", "2", "167", "61", "117", "139", "145", "43", "15", "179");
    private final CardRun uncommonB = new CardRun(true, "119", "130", "84", "36", "5", "148", "68", "116", "129", "80", "146", "1", "168", "72", "121", "138", "95", "42", "19", "176", "73", "112", "136", "99", "50", "144", "25", "72", "116", "134", "84", "33", "5", "148", "57", "119", "130", "95", "36", "25", "146", "68", "121", "129", "80", "42", "176", "5", "68", "116", "138", "99", "36", "144", "73", "136", "112", "84", "50", "1", "148", "57", "119", "130", "80", "42", "19", "168", "72", "121", "129", "146", "95", "33", "25", "176", "73", "112", "136", "99", "50", "1", "130", "144", "57", "119", "134", "84", "36", "19", "168", "68", "116", "129", "148", "33", "5", "176", "73", "112", "136", "95", "42", "1", "146", "138", "121", "134", "80", "33", "25", "138", "72", "168", "134", "99", "50", "19", "144", "57");
    private final CardRun rare = new CardRun(true, "93", "23", "184", "104", "41", "123", "53", "22", "9", "170", "62", "96", "4", "160", "18", "122", "128", "49", "24", "11", "171", "66", "105", "109", "162", "163", "169", "131", "175", "27", "28", "29", "87", "107", "9", "153", "164", "91", "142", "181", "64", "123", "31", "93", "108", "11", "24", "137", "62", "154", "22", "75", "128", "71", "96", "28", "152", "27", "29", "66", "160", "74", "4", "131", "163", "105", "175", "87", "81", "31", "90", "162", "88", "23", "142", "164", "107", "181", "184", "38", "41", "170", "49", "122", "85", "154", "109", "108", "91", "88", "127", "81", "53", "71", "75", "74");
    private final CardRun masterpiece = new CardRun(false, "MPS_31", "MPS_32", "MPS_33", "MPS_34", "MPS_35", "MPS_36", "MPS_37", "MPS_38", "MPS_39", "MPS_40", "MPS_41", "MPS_42", "MPS_43", "MPS_44", "MPS_45", "MPS_46", "MPS_47", "MPS_48", "MPS_49", "MPS_50", "MPS_51", "MPS_52", "MPS_53", "MPS_54");
    private final CardRun land = new CardRun(false, "KLD_250", "KLD_251", "KLD_252", "KLD_253", "KLD_254", "KLD_255", "KLD_256", "KLD_257", "KLD_258", "KLD_259", "KLD_260", "KLD_261", "KLD_262", "KLD_263", "KLD_264");

    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBBC1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1M = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1,
            masterpiece
    );
    private final BoosterStructure AAABBBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.14 A commons (44 / 14)  (rounded to 455/145)
    // 2.57 B commons (36 / 14)  (rounded to 373/145)
    // 2.36 C1 commons (33 / 14) (rounded to 341/145)
    // 1.93 C2 commons (27 / 14) (rounded to 280/145)
    // These numbers are the same for all sets with 70 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1,
            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1,
            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1,
            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1,
            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1,
            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1,
            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBC1C1C1C1M,

            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,

            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
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
