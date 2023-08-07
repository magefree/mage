
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author nantuko84
 */
public final class MirrodinBesieged extends ExpansionSet {

    private static final MirrodinBesieged instance = new MirrodinBesieged();

    public static MirrodinBesieged getInstance() {
        return instance;
    }

    private MirrodinBesieged() {
        super("Mirrodin Besieged", "MBS", ExpansionSet.buildDate(2011, 1, 4), SetType.EXPANSION);
        this.blockName = "Scars of Mirrodin";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Accorder Paladin", 1, Rarity.UNCOMMON, mage.cards.a.AccorderPaladin.class));
        cards.add(new SetCardInfo("Ardent Recruit", 2, Rarity.COMMON, mage.cards.a.ArdentRecruit.class));
        cards.add(new SetCardInfo("Banishment Decree", 3, Rarity.COMMON, mage.cards.b.BanishmentDecree.class));
        cards.add(new SetCardInfo("Black Sun's Zenith", 39, Rarity.RARE, mage.cards.b.BlackSunsZenith.class));
        cards.add(new SetCardInfo("Bladed Sentinel", 98, Rarity.COMMON, mage.cards.b.BladedSentinel.class));
        cards.add(new SetCardInfo("Blightsteel Colossus", 99, Rarity.MYTHIC, mage.cards.b.BlightsteelColossus.class));
        cards.add(new SetCardInfo("Blightwidow", 77, Rarity.COMMON, mage.cards.b.Blightwidow.class));
        cards.add(new SetCardInfo("Blisterstick Shaman", 58, Rarity.COMMON, mage.cards.b.BlisterstickShaman.class));
        cards.add(new SetCardInfo("Blue Sun's Zenith", 20, Rarity.RARE, mage.cards.b.BlueSunsZenith.class));
        cards.add(new SetCardInfo("Bonehoard", 100, Rarity.RARE, mage.cards.b.Bonehoard.class));
        cards.add(new SetCardInfo("Brass Squire", 101, Rarity.UNCOMMON, mage.cards.b.BrassSquire.class));
        cards.add(new SetCardInfo("Burn the Impure", 59, Rarity.COMMON, mage.cards.b.BurnTheImpure.class));
        cards.add(new SetCardInfo("Caustic Hound", 40, Rarity.COMMON, mage.cards.c.CausticHound.class));
        cards.add(new SetCardInfo("Choking Fumes", 4, Rarity.UNCOMMON, mage.cards.c.ChokingFumes.class));
        cards.add(new SetCardInfo("Concussive Bolt", 60, Rarity.COMMON, mage.cards.c.ConcussiveBolt.class));
        cards.add(new SetCardInfo("Consecrated Sphinx", 21, Rarity.MYTHIC, mage.cards.c.ConsecratedSphinx.class));
        cards.add(new SetCardInfo("Contested War Zone", 144, Rarity.RARE, mage.cards.c.ContestedWarZone.class));
        cards.add(new SetCardInfo("Copper Carapace", 102, Rarity.COMMON, mage.cards.c.CopperCarapace.class));
        cards.add(new SetCardInfo("Core Prowler", 103, Rarity.UNCOMMON, mage.cards.c.CoreProwler.class));
        cards.add(new SetCardInfo("Corrupted Conscience", 22, Rarity.UNCOMMON, mage.cards.c.CorruptedConscience.class));
        cards.add(new SetCardInfo("Creeping Corrosion", 78, Rarity.RARE, mage.cards.c.CreepingCorrosion.class));
        cards.add(new SetCardInfo("Crush", 61, Rarity.COMMON, mage.cards.c.Crush.class));
        cards.add(new SetCardInfo("Cryptoplasm", 23, Rarity.RARE, mage.cards.c.Cryptoplasm.class));
        cards.add(new SetCardInfo("Darksteel Plate", 104, Rarity.RARE, mage.cards.d.DarksteelPlate.class));
        cards.add(new SetCardInfo("Decimator Web", 105, Rarity.RARE, mage.cards.d.DecimatorWeb.class));
        cards.add(new SetCardInfo("Distant Memories", 24, Rarity.RARE, mage.cards.d.DistantMemories.class));
        cards.add(new SetCardInfo("Divine Offering", 5, Rarity.COMMON, mage.cards.d.DivineOffering.class));
        cards.add(new SetCardInfo("Dross Ripper", 106, Rarity.COMMON, mage.cards.d.DrossRipper.class));
        cards.add(new SetCardInfo("Fangren Marauder", 79, Rarity.COMMON, mage.cards.f.FangrenMarauder.class));
        cards.add(new SetCardInfo("Flayer Husk", 107, Rarity.COMMON, mage.cards.f.FlayerHusk.class));
        cards.add(new SetCardInfo("Flensermite", 41, Rarity.COMMON, mage.cards.f.Flensermite.class));
        cards.add(new SetCardInfo("Flesh-Eater Imp", 42, Rarity.UNCOMMON, mage.cards.f.FleshEaterImp.class));
        cards.add(new SetCardInfo("Forest", 154, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 155, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frantic Salvage", 6, Rarity.COMMON, mage.cards.f.FranticSalvage.class));
        cards.add(new SetCardInfo("Fuel for the Cause", 25, Rarity.COMMON, mage.cards.f.FuelForTheCause.class));
        cards.add(new SetCardInfo("Galvanoth", 62, Rarity.RARE, mage.cards.g.Galvanoth.class));
        cards.add(new SetCardInfo("Glissa's Courier", 80, Rarity.COMMON, mage.cards.g.GlissasCourier.class));
        cards.add(new SetCardInfo("Glissa, the Traitor", 96, Rarity.MYTHIC, mage.cards.g.GlissaTheTraitor.class));
        cards.add(new SetCardInfo("Gnathosaur", 63, Rarity.COMMON, mage.cards.g.Gnathosaur.class));
        cards.add(new SetCardInfo("Goblin Wardriver", 64, Rarity.UNCOMMON, mage.cards.g.GoblinWardriver.class));
        cards.add(new SetCardInfo("Go for the Throat", 43, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Gore Vassal", 7, Rarity.UNCOMMON, mage.cards.g.GoreVassal.class));
        cards.add(new SetCardInfo("Green Sun's Zenith", 81, Rarity.RARE, mage.cards.g.GreenSunsZenith.class));
        cards.add(new SetCardInfo("Gruesome Encore", 44, Rarity.UNCOMMON, mage.cards.g.GruesomeEncore.class));
        cards.add(new SetCardInfo("Gust-Skimmer", 108, Rarity.COMMON, mage.cards.g.GustSkimmer.class));
        cards.add(new SetCardInfo("Hellkite Igniter", 65, Rarity.RARE, mage.cards.h.HellkiteIgniter.class));
        cards.add(new SetCardInfo("Hero of Bladehold", 8, Rarity.MYTHIC, mage.cards.h.HeroOfBladehold.class));
        cards.add(new SetCardInfo("Hero of Oxid Ridge", 66, Rarity.MYTHIC, mage.cards.h.HeroOfOxidRidge.class));
        cards.add(new SetCardInfo("Hexplate Golem", 109, Rarity.COMMON, mage.cards.h.HexplateGolem.class));
        cards.add(new SetCardInfo("Horrifying Revelation", 45, Rarity.COMMON, mage.cards.h.HorrifyingRevelation.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 110, Rarity.COMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("Inkmoth Nexus", 145, Rarity.RARE, mage.cards.i.InkmothNexus.class));
        cards.add(new SetCardInfo("Into the Core", 67, Rarity.UNCOMMON, mage.cards.i.IntoTheCore.class));
        cards.add(new SetCardInfo("Island", 148, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 149, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kemba's Legion", 9, Rarity.UNCOMMON, mage.cards.k.KembasLegion.class));
        cards.add(new SetCardInfo("Knowledge Pool", 111, Rarity.RARE, mage.cards.k.KnowledgePool.class));
        cards.add(new SetCardInfo("Koth's Courier", 68, Rarity.COMMON, mage.cards.k.KothsCourier.class));
        cards.add(new SetCardInfo("Kuldotha Flamefiend", 69, Rarity.UNCOMMON, mage.cards.k.KuldothaFlamefiend.class));
        cards.add(new SetCardInfo("Kuldotha Ringleader", 70, Rarity.COMMON, mage.cards.k.KuldothaRingleader.class));
        cards.add(new SetCardInfo("Lead the Stampede", 82, Rarity.UNCOMMON, mage.cards.l.LeadTheStampede.class));
        cards.add(new SetCardInfo("Leonin Relic-Warder", 10, Rarity.UNCOMMON, mage.cards.l.LeoninRelicWarder.class));
        cards.add(new SetCardInfo("Leonin Skyhunter", 11, Rarity.COMMON, mage.cards.l.LeoninSkyhunter.class));
        cards.add(new SetCardInfo("Loxodon Partisan", 12, Rarity.COMMON, mage.cards.l.LoxodonPartisan.class));
        cards.add(new SetCardInfo("Lumengrid Gargoyle", 112, Rarity.UNCOMMON, mage.cards.l.LumengridGargoyle.class));
        cards.add(new SetCardInfo("Magnetic Mine", 113, Rarity.RARE, mage.cards.m.MagneticMine.class));
        cards.add(new SetCardInfo("Massacre Wurm", 46, Rarity.MYTHIC, mage.cards.m.MassacreWurm.class));
        cards.add(new SetCardInfo("Master's Call", 13, Rarity.COMMON, mage.cards.m.MastersCall.class));
        cards.add(new SetCardInfo("Melira's Keepers", 83, Rarity.UNCOMMON, mage.cards.m.MelirasKeepers.class));
        cards.add(new SetCardInfo("Metallic Mastery", 71, Rarity.UNCOMMON, mage.cards.m.MetallicMastery.class));
        cards.add(new SetCardInfo("Mirran Crusader", 14, Rarity.RARE, mage.cards.m.MirranCrusader.class));
        cards.add(new SetCardInfo("Mirran Mettle", 84, Rarity.COMMON, mage.cards.m.MirranMettle.class));
        cards.add(new SetCardInfo("Mirran Spy", 26, Rarity.COMMON, mage.cards.m.MirranSpy.class));
        cards.add(new SetCardInfo("Mirrorworks", 114, Rarity.RARE, mage.cards.m.Mirrorworks.class));
        cards.add(new SetCardInfo("Mitotic Manipulation", 27, Rarity.RARE, mage.cards.m.MitoticManipulation.class));
        cards.add(new SetCardInfo("Morbid Plunder", 47, Rarity.COMMON, mage.cards.m.MorbidPlunder.class));
        cards.add(new SetCardInfo("Mortarpod", 115, Rarity.UNCOMMON, mage.cards.m.Mortarpod.class));
        cards.add(new SetCardInfo("Mountain", 152, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 153, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Myr Sire", 116, Rarity.COMMON, mage.cards.m.MyrSire.class));
        cards.add(new SetCardInfo("Myr Turbine", 117, Rarity.RARE, mage.cards.m.MyrTurbine.class));
        cards.add(new SetCardInfo("Myr Welder", 118, Rarity.RARE, mage.cards.m.MyrWelder.class));
        cards.add(new SetCardInfo("Nested Ghoul", 48, Rarity.UNCOMMON, mage.cards.n.NestedGhoul.class));
        cards.add(new SetCardInfo("Neurok Commando", 28, Rarity.UNCOMMON, mage.cards.n.NeurokCommando.class));
        cards.add(new SetCardInfo("Oculus", 29, Rarity.COMMON, mage.cards.o.Oculus.class));
        cards.add(new SetCardInfo("Ogre Resister", 72, Rarity.COMMON, mage.cards.o.OgreResister.class));
        cards.add(new SetCardInfo("Peace Strider", 119, Rarity.UNCOMMON, mage.cards.p.PeaceStrider.class));
        cards.add(new SetCardInfo("Phyresis", 49, Rarity.COMMON, mage.cards.p.Phyresis.class));
        cards.add(new SetCardInfo("Phyrexian Crusader", 50, Rarity.RARE, mage.cards.p.PhyrexianCrusader.class));
        cards.add(new SetCardInfo("Phyrexian Digester", 120, Rarity.COMMON, mage.cards.p.PhyrexianDigester.class));
        cards.add(new SetCardInfo("Phyrexian Hydra", 85, Rarity.RARE, mage.cards.p.PhyrexianHydra.class));
        cards.add(new SetCardInfo("Phyrexian Juggernaut", 121, Rarity.UNCOMMON, mage.cards.p.PhyrexianJuggernaut.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 51, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Phyrexian Rebirth", 15, Rarity.RARE, mage.cards.p.PhyrexianRebirth.class));
        cards.add(new SetCardInfo("Phyrexian Revoker", 122, Rarity.RARE, mage.cards.p.PhyrexianRevoker.class));
        cards.add(new SetCardInfo("Phyrexian Vatmother", 52, Rarity.RARE, mage.cards.p.PhyrexianVatmother.class));
        cards.add(new SetCardInfo("Pierce Strider", 123, Rarity.UNCOMMON, mage.cards.p.PierceStrider.class));
        cards.add(new SetCardInfo("Piston Sledge", 124, Rarity.UNCOMMON, mage.cards.p.PistonSledge.class));
        cards.add(new SetCardInfo("Pistus Strike", 86, Rarity.COMMON, mage.cards.p.PistusStrike.class));
        cards.add(new SetCardInfo("Plaguemaw Beast", 87, Rarity.UNCOMMON, mage.cards.p.PlaguemawBeast.class));
        cards.add(new SetCardInfo("Plague Myr", 125, Rarity.UNCOMMON, mage.cards.p.PlagueMyr.class));
        cards.add(new SetCardInfo("Plains", 146, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 147, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Praetor's Counsel", 88, Rarity.MYTHIC, mage.cards.p.PraetorsCounsel.class));
        cards.add(new SetCardInfo("Priests of Norn", 16, Rarity.COMMON, mage.cards.p.PriestsOfNorn.class));
        cards.add(new SetCardInfo("Psychosis Crawler", 126, Rarity.RARE, mage.cards.p.PsychosisCrawler.class));
        cards.add(new SetCardInfo("Quicksilver Geyser", 30, Rarity.COMMON, mage.cards.q.QuicksilverGeyser.class));
        cards.add(new SetCardInfo("Quilled Slagwurm", 89, Rarity.UNCOMMON, mage.cards.q.QuilledSlagwurm.class));
        cards.add(new SetCardInfo("Rally the Forces", 73, Rarity.COMMON, mage.cards.r.RallyTheForces.class));
        cards.add(new SetCardInfo("Razorfield Rhino", 127, Rarity.COMMON, mage.cards.r.RazorfieldRhino.class));
        cards.add(new SetCardInfo("Red Sun's Zenith", 74, Rarity.RARE, mage.cards.r.RedSunsZenith.class));
        cards.add(new SetCardInfo("Rot Wolf", 90, Rarity.COMMON, mage.cards.r.RotWolf.class));
        cards.add(new SetCardInfo("Rusted Slasher", 128, Rarity.COMMON, mage.cards.r.RustedSlasher.class));
        cards.add(new SetCardInfo("Sangromancer", 53, Rarity.RARE, mage.cards.s.Sangromancer.class));
        cards.add(new SetCardInfo("Scourge Servant", 54, Rarity.COMMON, mage.cards.s.ScourgeServant.class));
        cards.add(new SetCardInfo("Septic Rats", 55, Rarity.UNCOMMON, mage.cards.s.SepticRats.class));
        cards.add(new SetCardInfo("Serum Raker", 31, Rarity.COMMON, mage.cards.s.SerumRaker.class));
        cards.add(new SetCardInfo("Shimmer Myr", 129, Rarity.RARE, mage.cards.s.ShimmerMyr.class));
        cards.add(new SetCardInfo("Shriekhorn", 130, Rarity.COMMON, mage.cards.s.Shriekhorn.class));
        cards.add(new SetCardInfo("Signal Pest", 131, Rarity.UNCOMMON, mage.cards.s.SignalPest.class));
        cards.add(new SetCardInfo("Silverskin Armor", 132, Rarity.UNCOMMON, mage.cards.s.SilverskinArmor.class));
        cards.add(new SetCardInfo("Skinwing", 133, Rarity.UNCOMMON, mage.cards.s.Skinwing.class));
        cards.add(new SetCardInfo("Slagstorm", 75, Rarity.RARE, mage.cards.s.Slagstorm.class));
        cards.add(new SetCardInfo("Sphere of the Suns", 134, Rarity.UNCOMMON, mage.cards.s.SphereOfTheSuns.class));
        cards.add(new SetCardInfo("Spin Engine", 135, Rarity.COMMON, mage.cards.s.SpinEngine.class));
        cards.add(new SetCardInfo("Spine of Ish Sah", 136, Rarity.RARE, mage.cards.s.SpineOfIshSah.class));
        cards.add(new SetCardInfo("Spiraling Duelist", 76, Rarity.UNCOMMON, mage.cards.s.SpiralingDuelist.class));
        cards.add(new SetCardInfo("Spire Serpent", 32, Rarity.COMMON, mage.cards.s.SpireSerpent.class));
        cards.add(new SetCardInfo("Spread the Sickness", 56, Rarity.COMMON, mage.cards.s.SpreadTheSickness.class));
        cards.add(new SetCardInfo("Steel Sabotage", 33, Rarity.COMMON, mage.cards.s.SteelSabotage.class));
        cards.add(new SetCardInfo("Strandwalker", 137, Rarity.UNCOMMON, mage.cards.s.Strandwalker.class));
        cards.add(new SetCardInfo("Swamp", 150, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 151, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 138, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class));
        cards.add(new SetCardInfo("Tangle Hulk", 139, Rarity.COMMON, mage.cards.t.TangleHulk.class));
        cards.add(new SetCardInfo("Tangle Mantis", 91, Rarity.COMMON, mage.cards.t.TangleMantis.class));
        cards.add(new SetCardInfo("Tezzeret, Agent of Bolas", 97, Rarity.MYTHIC, mage.cards.t.TezzeretAgentOfBolas.class));
        cards.add(new SetCardInfo("Thopter Assembly", 140, Rarity.RARE, mage.cards.t.ThopterAssembly.class));
        cards.add(new SetCardInfo("Thrun, the Last Troll", 92, Rarity.MYTHIC, mage.cards.t.ThrunTheLastTroll.class));
        cards.add(new SetCardInfo("Tine Shrike", 17, Rarity.COMMON, mage.cards.t.TineShrike.class));
        cards.add(new SetCardInfo("Titan Forge", 141, Rarity.RARE, mage.cards.t.TitanForge.class));
        cards.add(new SetCardInfo("Training Drone", 142, Rarity.COMMON, mage.cards.t.TrainingDrone.class));
        cards.add(new SetCardInfo("Treasure Mage", 34, Rarity.UNCOMMON, mage.cards.t.TreasureMage.class));
        cards.add(new SetCardInfo("Turn the Tide", 35, Rarity.COMMON, mage.cards.t.TurnTheTide.class));
        cards.add(new SetCardInfo("Unnatural Predation", 93, Rarity.COMMON, mage.cards.u.UnnaturalPredation.class));
        cards.add(new SetCardInfo("Vedalken Anatomist", 36, Rarity.UNCOMMON, mage.cards.v.VedalkenAnatomist.class));
        cards.add(new SetCardInfo("Vedalken Infuser", 37, Rarity.UNCOMMON, mage.cards.v.VedalkenInfuser.class));
        cards.add(new SetCardInfo("Victory's Herald", 18, Rarity.RARE, mage.cards.v.VictorysHerald.class));
        cards.add(new SetCardInfo("Viridian Claw", 143, Rarity.UNCOMMON, mage.cards.v.ViridianClaw.class));
        cards.add(new SetCardInfo("Viridian Corrupter", 94, Rarity.UNCOMMON, mage.cards.v.ViridianCorrupter.class));
        cards.add(new SetCardInfo("Viridian Emissary", 95, Rarity.COMMON, mage.cards.v.ViridianEmissary.class));
        cards.add(new SetCardInfo("Virulent Wound", 57, Rarity.COMMON, mage.cards.v.VirulentWound.class));
        cards.add(new SetCardInfo("Vivisection", 38, Rarity.COMMON, mage.cards.v.Vivisection.class));
        cards.add(new SetCardInfo("White Sun's Zenith", 19, Rarity.RARE, mage.cards.w.WhiteSunsZenith.class));
    }

}
