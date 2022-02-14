package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class NeonDynastyCommander extends ExpansionSet {

    private static final NeonDynastyCommander instance = new NeonDynastyCommander();

    public static NeonDynastyCommander getInstance() {
        return instance;
    }

    private NeonDynastyCommander() {
        super("Neon Dynasty Commander", "NEC", ExpansionSet.buildDate(2022, 2, 18), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Access Denied", 11, Rarity.RARE, mage.cards.a.AccessDenied.class));
        cards.add(new SetCardInfo("Acidic Slime", 112, Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Aerial Surveyor", 5, Rarity.RARE, mage.cards.a.AerialSurveyor.class));
        cards.add(new SetCardInfo("Aeronaut Admiral", 79, Rarity.UNCOMMON, mage.cards.a.AeronautAdmiral.class));
        cards.add(new SetCardInfo("Agitator Ant", 102, Rarity.RARE, mage.cards.a.AgitatorAnt.class));
        cards.add(new SetCardInfo("Akki Battle Squad", 18, Rarity.RARE, mage.cards.a.AkkiBattleSquad.class));
        cards.add(new SetCardInfo("Arcane Signet", 144, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Arcanist's Owl", 135, Rarity.UNCOMMON, mage.cards.a.ArcanistsOwl.class));
        cards.add(new SetCardInfo("Armed and Armored", 80, Rarity.UNCOMMON, mage.cards.a.ArmedAndArmored.class));
        cards.add(new SetCardInfo("Ascendant Acolyte", 24, Rarity.RARE, mage.cards.a.AscendantAcolyte.class));
        cards.add(new SetCardInfo("Azorius Signet", 145, Rarity.UNCOMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Bear Umbra", 113, Rarity.RARE, mage.cards.b.BearUmbra.class));
        cards.add(new SetCardInfo("Beast Within", 114, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Blackblade Reforged", 146, Rarity.RARE, mage.cards.b.BlackbladeReforged.class));
        cards.add(new SetCardInfo("Bonehoard", 147, Rarity.RARE, mage.cards.b.Bonehoard.class));
        cards.add(new SetCardInfo("Cataclysmic Gearhulk", 81, Rarity.MYTHIC, mage.cards.c.CataclysmicGearhulk.class));
        cards.add(new SetCardInfo("Chain Reaction", 103, Rarity.RARE, mage.cards.c.ChainReaction.class));
        cards.add(new SetCardInfo("Champion of Lambholt", 115, Rarity.RARE, mage.cards.c.ChampionOfLambholt.class));
        cards.add(new SetCardInfo("Chaos Warp", 104, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Chishiro, the Shattered Blade", 1, Rarity.MYTHIC, mage.cards.c.ChishiroTheShatteredBlade.class));
        cards.add(new SetCardInfo("Cinder Glade", 166, Rarity.RARE, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Colossal Plow", 148, Rarity.UNCOMMON, mage.cards.c.ColossalPlow.class));
        cards.add(new SetCardInfo("Command Tower", 167, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Concord with the Kami", 25, Rarity.RARE, mage.cards.c.ConcordWithTheKami.class));
        cards.add(new SetCardInfo("Crush Contraband", 82, Rarity.UNCOMMON, mage.cards.c.CrushContraband.class));
        cards.add(new SetCardInfo("Cultivator's Caravan", 149, Rarity.RARE, mage.cards.c.CultivatorsCaravan.class));
        cards.add(new SetCardInfo("Dance of the Manse", 136, Rarity.RARE, mage.cards.d.DanceOfTheManse.class));
        cards.add(new SetCardInfo("Decimate", 137, Rarity.RARE, mage.cards.d.Decimate.class));
        cards.add(new SetCardInfo("Dispatch", 83, Rarity.UNCOMMON, mage.cards.d.Dispatch.class));
        cards.add(new SetCardInfo("Drumbellower", 6, Rarity.RARE, mage.cards.d.Drumbellower.class));
        cards.add(new SetCardInfo("Elemental Mastery", 105, Rarity.RARE, mage.cards.e.ElementalMastery.class));
        cards.add(new SetCardInfo("Emry, Lurker of the Loch", 91, Rarity.RARE, mage.cards.e.EmryLurkerOfTheLoch.class));
        cards.add(new SetCardInfo("Etherium Sculptor", 92, Rarity.COMMON, mage.cards.e.EtheriumSculptor.class));
        cards.add(new SetCardInfo("Exotic Orchard", 168, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Fellwar Stone", 150, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Fertilid", 116, Rarity.COMMON, mage.cards.f.Fertilid.class));
        cards.add(new SetCardInfo("Fireshrieker", 151, Rarity.UNCOMMON, mage.cards.f.Fireshrieker.class));
        cards.add(new SetCardInfo("Forgotten Ancient", 117, Rarity.RARE, mage.cards.f.ForgottenAncient.class));
        cards.add(new SetCardInfo("Foundry Inspector", 152, Rarity.COMMON, mage.cards.f.FoundryInspector.class));
        cards.add(new SetCardInfo("Game Trail", 169, Rarity.RARE, mage.cards.g.GameTrail.class));
        cards.add(new SetCardInfo("Generous Gift", 84, Rarity.UNCOMMON, mage.cards.g.GenerousGift.class));
        cards.add(new SetCardInfo("Genesis Hydra", 118, Rarity.RARE, mage.cards.g.GenesisHydra.class));
        cards.add(new SetCardInfo("Go-Shintai of Life's Origin", 37, Rarity.MYTHIC, mage.cards.g.GoShintaiOfLifesOrigin.class));
        cards.add(new SetCardInfo("Goblin Razerunners", 106, Rarity.RARE, mage.cards.g.GoblinRazerunners.class));
        cards.add(new SetCardInfo("Gold Myr", 153, Rarity.COMMON, mage.cards.g.GoldMyr.class));
        cards.add(new SetCardInfo("Grumgully, the Generous", 138, Rarity.UNCOMMON, mage.cards.g.GrumgullyTheGenerous.class));
        cards.add(new SetCardInfo("Gruul Turf", 170, Rarity.UNCOMMON, mage.cards.g.GruulTurf.class));
        cards.add(new SetCardInfo("Hanna, Ship's Navigator", 139, Rarity.RARE, mage.cards.h.HannaShipsNavigator.class));
        cards.add(new SetCardInfo("Hunter's Insight", 119, Rarity.UNCOMMON, mage.cards.h.HuntersInsight.class));
        cards.add(new SetCardInfo("Imposter Mech", 13, Rarity.RARE, mage.cards.i.ImposterMech.class));
        cards.add(new SetCardInfo("Indomitable Archangel", 85, Rarity.MYTHIC, mage.cards.i.IndomitableArchangel.class));
        cards.add(new SetCardInfo("Ironsoul Enforcer", 7, Rarity.RARE, mage.cards.i.IronsoulEnforcer.class));
        cards.add(new SetCardInfo("Jace, Architect of Thought", 93, Rarity.MYTHIC, mage.cards.j.JaceArchitectOfThought.class));
        cards.add(new SetCardInfo("Kappa Cannoneer", 14, Rarity.RARE, mage.cards.k.KappaCannoneer.class));
        cards.add(new SetCardInfo("Kodama's Reach", 120, Rarity.COMMON, mage.cards.k.KodamasReach.class));
        cards.add(new SetCardInfo("Kosei, Penitent Warlord", 26, Rarity.RARE, mage.cards.k.KoseiPenitentWarlord.class));
        cards.add(new SetCardInfo("Kotori, Pilot Prodigy", 2, Rarity.MYTHIC, mage.cards.k.KotoriPilotProdigy.class));
        cards.add(new SetCardInfo("Krenko, Tin Street Kingpin", 107, Rarity.RARE, mage.cards.k.KrenkoTinStreetKingpin.class));
        cards.add(new SetCardInfo("Loyal Guardian", 121, Rarity.UNCOMMON, mage.cards.l.LoyalGuardian.class));
        cards.add(new SetCardInfo("Mage Slayer", 140, Rarity.UNCOMMON, mage.cards.m.MageSlayer.class));
        cards.add(new SetCardInfo("Master of Etherium", 94, Rarity.RARE, mage.cards.m.MasterOfEtherium.class));
        cards.add(new SetCardInfo("Mirage Mirror", 154, Rarity.RARE, mage.cards.m.MirageMirror.class));
        cards.add(new SetCardInfo("Mossfire Valley", 171, Rarity.RARE, mage.cards.m.MossfireValley.class));
        cards.add(new SetCardInfo("Myojin of Blooming Dawn", 31, Rarity.RARE, mage.cards.m.MyojinOfBloomingDawn.class));
        cards.add(new SetCardInfo("Myojin of Cryptic Dreams", 33, Rarity.RARE, mage.cards.m.MyojinOfCrypticDreams.class));
        cards.add(new SetCardInfo("Myojin of Grim Betrayal", 34, Rarity.RARE, mage.cards.m.MyojinOfGrimBetrayal.class));
        cards.add(new SetCardInfo("Myojin of Roaring Blades", 36, Rarity.RARE, mage.cards.m.MyojinOfRoaringBlades.class));
        cards.add(new SetCardInfo("Myojin of Towering Might", 38, Rarity.RARE, mage.cards.m.MyojinOfToweringMight.class));
        cards.add(new SetCardInfo("Myrsmith", 86, Rarity.UNCOMMON, mage.cards.m.Myrsmith.class));
        cards.add(new SetCardInfo("Nissa, Voice of Zendikar", 122, Rarity.MYTHIC, mage.cards.n.NissaVoiceOfZendikar.class));
        cards.add(new SetCardInfo("Opal Palace", 172, Rarity.COMMON, mage.cards.o.OpalPalace.class));
        cards.add(new SetCardInfo("Oran-Rief, the Vastwood", 173, Rarity.RARE, mage.cards.o.OranRiefTheVastwood.class));
        cards.add(new SetCardInfo("Ordeal of Nylea", 123, Rarity.UNCOMMON, mage.cards.o.OrdealOfNylea.class));
        cards.add(new SetCardInfo("Organic Extinction", 8, Rarity.RARE, mage.cards.o.OrganicExtinction.class));
        cards.add(new SetCardInfo("Ox of Agonas", 108, Rarity.MYTHIC, mage.cards.o.OxOfAgonas.class));
        cards.add(new SetCardInfo("Parhelion II", 87, Rarity.RARE, mage.cards.p.ParhelionII.class));
        cards.add(new SetCardInfo("Peacewalker Colossus", 155, Rarity.RARE, mage.cards.p.PeacewalkerColossus.class));
        cards.add(new SetCardInfo("Port Town", 174, Rarity.RARE, mage.cards.p.PortTown.class));
        cards.add(new SetCardInfo("Prairie Stream", 175, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Primeval Protector", 124, Rarity.RARE, mage.cards.p.PrimevalProtector.class));
        cards.add(new SetCardInfo("Raff Capashen, Ship's Mage", 141, Rarity.UNCOMMON, mage.cards.r.RaffCapashenShipsMage.class));
        cards.add(new SetCardInfo("Raging Ravine", 176, Rarity.RARE, mage.cards.r.RagingRavine.class));
        cards.add(new SetCardInfo("Raiders' Karve", 156, Rarity.COMMON, mage.cards.r.RaidersKarve.class));
        cards.add(new SetCardInfo("Rampant Growth", 125, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Rampant Rejuvenator", 28, Rarity.RARE, mage.cards.r.RampantRejuvenator.class));
        cards.add(new SetCardInfo("Reality Shift", 95, Rarity.UNCOMMON, mage.cards.r.RealityShift.class));
        cards.add(new SetCardInfo("Release to Memory", 9, Rarity.RARE, mage.cards.r.ReleaseToMemory.class));
        cards.add(new SetCardInfo("Research Thief", 16, Rarity.RARE, mage.cards.r.ResearchThief.class));
        cards.add(new SetCardInfo("Rhythm of the Wild", 142, Rarity.UNCOMMON, mage.cards.r.RhythmOfTheWild.class));
        cards.add(new SetCardInfo("Riddlesmith", 96, Rarity.UNCOMMON, mage.cards.r.Riddlesmith.class));
        cards.add(new SetCardInfo("Rishkar's Expertise", 127, Rarity.RARE, mage.cards.r.RishkarsExpertise.class));
        cards.add(new SetCardInfo("Rishkar, Peema Renegade", 126, Rarity.RARE, mage.cards.r.RishkarPeemaRenegade.class));
        cards.add(new SetCardInfo("Sai, Master Thopterist", 97, Rarity.RARE, mage.cards.s.SaiMasterThopterist.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 128, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Shamanic Revelation", 129, Rarity.RARE, mage.cards.s.ShamanicRevelation.class));
        cards.add(new SetCardInfo("Shifting Shadow", 109, Rarity.RARE, mage.cards.s.ShiftingShadow.class));
        cards.add(new SetCardInfo("Shimmer Myr", 157, Rarity.UNCOMMON, mage.cards.s.ShimmerMyr.class));
        cards.add(new SetCardInfo("Shorikai, Genesis Engine", 4, Rarity.MYTHIC, mage.cards.s.ShorikaiGenesisEngine.class));
        cards.add(new SetCardInfo("Silkguard", 29, Rarity.RARE, mage.cards.s.Silkguard.class));
        cards.add(new SetCardInfo("Silver Myr", 158, Rarity.COMMON, mage.cards.s.SilverMyr.class));
        cards.add(new SetCardInfo("Skycloud Expanse", 177, Rarity.RARE, mage.cards.s.SkycloudExpanse.class));
        cards.add(new SetCardInfo("Skysovereign, Consul Flagship", 159, Rarity.MYTHIC, mage.cards.s.SkysovereignConsulFlagship.class));
        cards.add(new SetCardInfo("Smuggler's Copter", 160, Rarity.RARE, mage.cards.s.SmugglersCopter.class));
        cards.add(new SetCardInfo("Snake Umbra", 130, Rarity.UNCOMMON, mage.cards.s.SnakeUmbra.class));
        cards.add(new SetCardInfo("Sol Ring", 161, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 162, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Soul's Majesty", 131, Rarity.RARE, mage.cards.s.SoulsMajesty.class));
        cards.add(new SetCardInfo("Spearbreaker Behemoth", 132, Rarity.RARE, mage.cards.s.SpearbreakerBehemoth.class));
        cards.add(new SetCardInfo("Spire of Industry", 178, Rarity.RARE, mage.cards.s.SpireOfIndustry.class));
        cards.add(new SetCardInfo("Sram, Senior Edificer", 88, Rarity.RARE, mage.cards.s.SramSeniorEdificer.class));
        cards.add(new SetCardInfo("Starstorm", 110, Rarity.RARE, mage.cards.s.Starstorm.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 163, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Sword of Vengeance", 164, Rarity.RARE, mage.cards.s.SwordOfVengeance.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 89, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Tanuki Transplanter", 30, Rarity.RARE, mage.cards.t.TanukiTransplanter.class));
        cards.add(new SetCardInfo("Taurean Mauler", 111, Rarity.RARE, mage.cards.t.TaureanMauler.class));
        cards.add(new SetCardInfo("Temple of Abandon", 179, Rarity.RARE, mage.cards.t.TempleOfAbandon.class));
        cards.add(new SetCardInfo("Temple of Enlightenment", 180, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class));
        cards.add(new SetCardInfo("Teshar, Ancestor's Apostle", 90, Rarity.RARE, mage.cards.t.TesharAncestorsApostle.class));
        cards.add(new SetCardInfo("Thopter Spy Network", 98, Rarity.RARE, mage.cards.t.ThopterSpyNetwork.class));
        cards.add(new SetCardInfo("Thoughtcast", 99, Rarity.COMMON, mage.cards.t.Thoughtcast.class));
        cards.add(new SetCardInfo("Ulasht, the Hate Seed", 143, Rarity.RARE, mage.cards.u.UlashtTheHateSeed.class));
        cards.add(new SetCardInfo("Universal Surveillance", 17, Rarity.RARE, mage.cards.u.UniversalSurveillance.class));
        cards.add(new SetCardInfo("Vastwood Surge", 133, Rarity.UNCOMMON, mage.cards.v.VastwoodSurge.class));
        cards.add(new SetCardInfo("Vedalken Engineer", 100, Rarity.COMMON, mage.cards.v.VedalkenEngineer.class));
        cards.add(new SetCardInfo("Weatherlight", 165, Rarity.MYTHIC, mage.cards.w.Weatherlight.class));
        cards.add(new SetCardInfo("Whiptongue Hydra", 134, Rarity.RARE, mage.cards.w.WhiptongueHydra.class));
        cards.add(new SetCardInfo("Whirler Rogue", 101, Rarity.UNCOMMON, mage.cards.w.WhirlerRogue.class));
        cards.add(new SetCardInfo("Yoshimaru, Ever Faithful", 32, Rarity.MYTHIC, mage.cards.y.YoshimaruEverFaithful.class));
    }
}
