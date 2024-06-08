package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ModernHorizons3 extends ExpansionSet {

    private static final ModernHorizons3 instance = new ModernHorizons3();

    public static ModernHorizons3 getInstance() {
        return instance;
    }

    private ModernHorizons3() {
        super("Modern Horizons 3", "MH3", ExpansionSet.buildDate(2024, 6, 7), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Modern Horizons 3";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.maxCardNumberInBooster = 319;
        this.numBoosterDoubleFaced = -1;

        cards.add(new SetCardInfo("Abstruse Appropriation", 177, Rarity.RARE, mage.cards.a.AbstruseAppropriation.class));
        cards.add(new SetCardInfo("Accursed Marauder", 80, Rarity.COMMON, mage.cards.a.AccursedMarauder.class));
        cards.add(new SetCardInfo("Aerie Auxiliary", 18, Rarity.COMMON, mage.cards.a.AerieAuxiliary.class));
        cards.add(new SetCardInfo("Aether Revolt", 113, Rarity.RARE, mage.cards.a.AetherRevolt.class));
        cards.add(new SetCardInfo("Aether Spike", 50, Rarity.COMMON, mage.cards.a.AetherSpike.class));
        cards.add(new SetCardInfo("Ajani Fells the Godsire", 19, Rarity.UNCOMMON, mage.cards.a.AjaniFellsTheGodsire.class));
        cards.add(new SetCardInfo("Ajani, Nacatl Avenger", 237, Rarity.MYTHIC, mage.cards.a.AjaniNacatlAvenger.class));
        cards.add(new SetCardInfo("Ajani, Nacatl Pariah", 237, Rarity.MYTHIC, mage.cards.a.AjaniNacatlPariah.class));
        cards.add(new SetCardInfo("Amped Raptor", 114, Rarity.UNCOMMON, mage.cards.a.AmpedRaptor.class));
        cards.add(new SetCardInfo("Amphibian Downpour", 51, Rarity.RARE, mage.cards.a.AmphibianDownpour.class));
        cards.add(new SetCardInfo("Angel of the Ruins", 262, Rarity.UNCOMMON, mage.cards.a.AngelOfTheRuins.class));
        cards.add(new SetCardInfo("Annoyed Altisaur", 284, Rarity.UNCOMMON, mage.cards.a.AnnoyedAltisaur.class));
        cards.add(new SetCardInfo("Arcbound Condor", 81, Rarity.UNCOMMON, mage.cards.a.ArcboundCondor.class));
        cards.add(new SetCardInfo("Archway of Innovation", 214, Rarity.RARE, mage.cards.a.ArchwayOfInnovation.class));
        cards.add(new SetCardInfo("Arena of Glory", 215, Rarity.RARE, mage.cards.a.ArenaOfGlory.class));
        cards.add(new SetCardInfo("Argent Dais", 20, Rarity.RARE, mage.cards.a.ArgentDais.class));
        cards.add(new SetCardInfo("Arna Kennerud, Skycaptain", 178, Rarity.MYTHIC, mage.cards.a.ArnaKennerudSkycaptain.class));
        cards.add(new SetCardInfo("Ashling, Flame Dancer", 115, Rarity.MYTHIC, mage.cards.a.AshlingFlameDancer.class));
        cards.add(new SetCardInfo("Barbarian Ring", 299, Rarity.UNCOMMON, mage.cards.b.BarbarianRing.class));
        cards.add(new SetCardInfo("Basking Broodscale", 145, Rarity.COMMON, mage.cards.b.BaskingBroodscale.class));
        cards.add(new SetCardInfo("Bespoke Battlewagon", 52, Rarity.UNCOMMON, mage.cards.b.BespokeBattlewagon.class));
        cards.add(new SetCardInfo("Birthing Ritual", 146, Rarity.MYTHIC, mage.cards.b.BirthingRitual.class));
        cards.add(new SetCardInfo("Bloodsoaked Insight", 252, Rarity.UNCOMMON, mage.cards.b.BloodsoakedInsight.class));
        cards.add(new SetCardInfo("Bloodstained Mire", 216, Rarity.RARE, mage.cards.b.BloodstainedMire.class));
        cards.add(new SetCardInfo("Boggart Trawler", 243, Rarity.UNCOMMON, mage.cards.b.BoggartTrawler.class));
        cards.add(new SetCardInfo("Bountiful Landscape", 217, Rarity.COMMON, mage.cards.b.BountifulLandscape.class));
        cards.add(new SetCardInfo("Brainsurge", 53, Rarity.UNCOMMON, mage.cards.b.Brainsurge.class));
        cards.add(new SetCardInfo("Branching Evolution", 285, Rarity.RARE, mage.cards.b.BranchingEvolution.class));
        cards.add(new SetCardInfo("Breaker of Creation", 1, Rarity.UNCOMMON, mage.cards.b.BreakerOfCreation.class));
        cards.add(new SetCardInfo("Breathe Your Last", 82, Rarity.COMMON, mage.cards.b.BreatheYourLast.class));
        cards.add(new SetCardInfo("Breya, Etherium Shaper", 289, Rarity.MYTHIC, mage.cards.b.BreyaEtheriumShaper.class));
        cards.add(new SetCardInfo("Bridgeworks Battle", 249, Rarity.UNCOMMON, mage.cards.b.BridgeworksBattle.class));
        cards.add(new SetCardInfo("Buried Alive", 273, Rarity.UNCOMMON, mage.cards.b.BuriedAlive.class));
        cards.add(new SetCardInfo("Cephalid Coliseum", 300, Rarity.UNCOMMON, mage.cards.c.CephalidColiseum.class));
        cards.add(new SetCardInfo("Charitable Levy", 21, Rarity.UNCOMMON, mage.cards.c.CharitableLevy.class));
        cards.add(new SetCardInfo("Chthonian Nightmare", 83, Rarity.RARE, mage.cards.c.ChthonianNightmare.class));
        cards.add(new SetCardInfo("Collective Resistance", 147, Rarity.UNCOMMON, mage.cards.c.CollectiveResistance.class));
        cards.add(new SetCardInfo("Colossal Dreadmask", 148, Rarity.COMMON, mage.cards.c.ColossalDreadmask.class));
        cards.add(new SetCardInfo("Conduit Goblin", 179, Rarity.COMMON, mage.cards.c.ConduitGoblin.class));
        cards.add(new SetCardInfo("Consign to Memory", 54, Rarity.UNCOMMON, mage.cards.c.ConsignToMemory.class));
        cards.add(new SetCardInfo("Consuming Corruption", 84, Rarity.UNCOMMON, mage.cards.c.ConsumingCorruption.class));
        cards.add(new SetCardInfo("Contaminated Landscape", 218, Rarity.COMMON, mage.cards.c.ContaminatedLandscape.class));
        cards.add(new SetCardInfo("Copycrook", 55, Rarity.UNCOMMON, mage.cards.c.Copycrook.class));
        cards.add(new SetCardInfo("Corrupted Shapeshifter", 56, Rarity.COMMON, mage.cards.c.CorruptedShapeshifter.class));
        cards.add(new SetCardInfo("Crabomination", 85, Rarity.RARE, mage.cards.c.Crabomination.class));
        cards.add(new SetCardInfo("Cranial Ram", 180, Rarity.COMMON, mage.cards.c.CranialRam.class));
        cards.add(new SetCardInfo("Cursed Mirror", 279, Rarity.RARE, mage.cards.c.CursedMirror.class));
        cards.add(new SetCardInfo("Cursed Wombat", 181, Rarity.UNCOMMON, mage.cards.c.CursedWombat.class));
        cards.add(new SetCardInfo("Cyclops Superconductor", 182, Rarity.COMMON, mage.cards.c.CyclopsSuperconductor.class));
        cards.add(new SetCardInfo("Deceptive Landscape", 219, Rarity.COMMON, mage.cards.d.DeceptiveLandscape.class));
        cards.add(new SetCardInfo("Decree of Justice", 263, Rarity.UNCOMMON, mage.cards.d.DecreeOfJustice.class));
        cards.add(new SetCardInfo("Deem Inferior", 57, Rarity.COMMON, mage.cards.d.DeemInferior.class));
        cards.add(new SetCardInfo("Deep Analysis", 268, Rarity.UNCOMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Depth Defiler", 58, Rarity.UNCOMMON, mage.cards.d.DepthDefiler.class));
        cards.add(new SetCardInfo("Deserted Temple", 301, Rarity.RARE, mage.cards.d.DesertedTemple.class));
        cards.add(new SetCardInfo("Detective's Phoenix", 116, Rarity.RARE, mage.cards.d.DetectivesPhoenix.class));
        cards.add(new SetCardInfo("Devourer of Destiny", 2, Rarity.RARE, mage.cards.d.DevourerOfDestiny.class));
        cards.add(new SetCardInfo("Disciple of Freyalise", 250, Rarity.UNCOMMON, mage.cards.d.DiscipleOfFreyalise.class));
        cards.add(new SetCardInfo("Disruptor Flute", 209, Rarity.RARE, mage.cards.d.DisruptorFlute.class));
        cards.add(new SetCardInfo("Distinguished Conjurer", 264, Rarity.UNCOMMON, mage.cards.d.DistinguishedConjurer.class));
        cards.add(new SetCardInfo("Dog Umbra", 22, Rarity.COMMON, mage.cards.d.DogUmbra.class));
        cards.add(new SetCardInfo("Dreadmobile", 87, Rarity.UNCOMMON, mage.cards.d.Dreadmobile.class));
        cards.add(new SetCardInfo("Dreamdrinker Vampire", 88, Rarity.COMMON, mage.cards.d.DreamdrinkerVampire.class));
        cards.add(new SetCardInfo("Dreamtide Whale", 59, Rarity.RARE, mage.cards.d.DreamtideWhale.class));
        cards.add(new SetCardInfo("Drossclaw", 89, Rarity.COMMON, mage.cards.d.Drossclaw.class));
        cards.add(new SetCardInfo("Drowner of Truth", 253, Rarity.UNCOMMON, mage.cards.d.DrownerOfTruth.class));
        cards.add(new SetCardInfo("Drownyard Lurker", 3, Rarity.COMMON, mage.cards.d.DrownyardLurker.class));
        cards.add(new SetCardInfo("Echoes of Eternity", 4, Rarity.RARE, mage.cards.e.EchoesOfEternity.class));
        cards.add(new SetCardInfo("Eladamri, Korvecdal", 149, Rarity.MYTHIC, mage.cards.e.EladamriKorvecdal.class));
        cards.add(new SetCardInfo("Eldrazi Linebreaker", 117, Rarity.RARE, mage.cards.e.EldraziLinebreaker.class));
        cards.add(new SetCardInfo("Eldrazi Ravager", 5, Rarity.UNCOMMON, mage.cards.e.EldraziRavager.class));
        cards.add(new SetCardInfo("Eldrazi Repurposer", 150, Rarity.COMMON, mage.cards.e.EldraziRepurposer.class));
        cards.add(new SetCardInfo("Electrozoa", 60, Rarity.COMMON, mage.cards.e.Electrozoa.class));
        cards.add(new SetCardInfo("Emerald Medallion", 291, Rarity.RARE, mage.cards.e.EmeraldMedallion.class));
        cards.add(new SetCardInfo("Emissary of Soulfire", 183, Rarity.UNCOMMON, mage.cards.e.EmissaryOfSoulfire.class));
        cards.add(new SetCardInfo("Emperor of Bones", 90, Rarity.RARE, mage.cards.e.EmperorOfBones.class));
        cards.add(new SetCardInfo("Emrakul's Messenger", 61, Rarity.UNCOMMON, mage.cards.e.EmrakulsMessenger.class));
        cards.add(new SetCardInfo("Emrakul, the World Anew", 6, Rarity.MYTHIC, mage.cards.e.EmrakulTheWorldAnew.class));
        cards.add(new SetCardInfo("Envoy of the Ancestors", 23, Rarity.UNCOMMON, mage.cards.e.EnvoyOfTheAncestors.class));
        cards.add(new SetCardInfo("Essence Reliquary", 24, Rarity.UNCOMMON, mage.cards.e.EssenceReliquary.class));
        cards.add(new SetCardInfo("Estrid's Invocation", 269, Rarity.RARE, mage.cards.e.EstridsInvocation.class));
        cards.add(new SetCardInfo("Etched Slith", 91, Rarity.UNCOMMON, mage.cards.e.EtchedSlith.class));
        cards.add(new SetCardInfo("Etherium Pteramander", 92, Rarity.UNCOMMON, mage.cards.e.EtheriumPteramander.class));
        cards.add(new SetCardInfo("Eviscerator's Insight", 93, Rarity.COMMON, mage.cards.e.EvisceratorsInsight.class));
        cards.add(new SetCardInfo("Evolution Witness", 151, Rarity.COMMON, mage.cards.e.EvolutionWitness.class));
        cards.add(new SetCardInfo("Expanding Ooze", 184, Rarity.COMMON, mage.cards.e.ExpandingOoze.class));
        cards.add(new SetCardInfo("Expel the Unworthy", 25, Rarity.COMMON, mage.cards.e.ExpelTheUnworthy.class));
        cards.add(new SetCardInfo("Faithful Watchdog", 185, Rarity.COMMON, mage.cards.f.FaithfulWatchdog.class));
        cards.add(new SetCardInfo("Fanatic of Rhonas", 152, Rarity.RARE, mage.cards.f.FanaticOfRhonas.class));
        cards.add(new SetCardInfo("Fanged Flames", 118, Rarity.COMMON, mage.cards.f.FangedFlames.class));
        cards.add(new SetCardInfo("Fangs of Kalonia", 153, Rarity.UNCOMMON, mage.cards.f.FangsOfKalonia.class));
        cards.add(new SetCardInfo("Fell the Profane", 244, Rarity.UNCOMMON, mage.cards.f.FellTheProfane.class));
        cards.add(new SetCardInfo("Fetid Gargantua", 94, Rarity.COMMON, mage.cards.f.FetidGargantua.class));
        cards.add(new SetCardInfo("Flare of Cultivation", 154, Rarity.RARE, mage.cards.f.FlareOfCultivation.class));
        cards.add(new SetCardInfo("Flare of Denial", 62, Rarity.RARE, mage.cards.f.FlareOfDenial.class));
        cards.add(new SetCardInfo("Flare of Duplication", 119, Rarity.RARE, mage.cards.f.FlareOfDuplication.class));
        cards.add(new SetCardInfo("Flare of Fortitude", 26, Rarity.RARE, mage.cards.f.FlareOfFortitude.class));
        cards.add(new SetCardInfo("Flare of Malice", 95, Rarity.RARE, mage.cards.f.FlareOfMalice.class));
        cards.add(new SetCardInfo("Fledgling Dragon", 280, Rarity.UNCOMMON, mage.cards.f.FledglingDragon.class));
        cards.add(new SetCardInfo("Flooded Strand", 220, Rarity.RARE, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Flusterstorm", 496, Rarity.RARE, mage.cards.f.Flusterstorm.class));
        cards.add(new SetCardInfo("Foreboding Landscape", 221, Rarity.COMMON, mage.cards.f.ForebodingLandscape.class));
        cards.add(new SetCardInfo("Forest", 308, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Fowl Strike", 155, Rarity.COMMON, mage.cards.f.FowlStrike.class));
        cards.add(new SetCardInfo("Frogmyr Enforcer", 120, Rarity.UNCOMMON, mage.cards.f.FrogmyrEnforcer.class));
        cards.add(new SetCardInfo("Furnace Hellkite", 121, Rarity.UNCOMMON, mage.cards.f.FurnaceHellkite.class));
        cards.add(new SetCardInfo("Galvanic Discharge", 122, Rarity.COMMON, mage.cards.g.GalvanicDischarge.class));
        cards.add(new SetCardInfo("Genku, Future Shaper", 186, Rarity.RARE, mage.cards.g.GenkuFutureShaper.class));
        cards.add(new SetCardInfo("Ghostfire Slice", 123, Rarity.UNCOMMON, mage.cards.g.GhostfireSlice.class));
        cards.add(new SetCardInfo("Gift of the Viper", 156, Rarity.COMMON, mage.cards.g.GiftOfTheViper.class));
        cards.add(new SetCardInfo("Glaring Fleshraker", 7, Rarity.UNCOMMON, mage.cards.g.GlaringFleshraker.class));
        cards.add(new SetCardInfo("Glasswing Grace", 254, Rarity.UNCOMMON, mage.cards.g.GlasswingGrace.class));
        cards.add(new SetCardInfo("Glimpse the Impossible", 124, Rarity.COMMON, mage.cards.g.GlimpseTheImpossible.class));
        cards.add(new SetCardInfo("Glyph Elemental", 27, Rarity.UNCOMMON, mage.cards.g.GlyphElemental.class));
        cards.add(new SetCardInfo("Golden-Tail Trainer", 187, Rarity.UNCOMMON, mage.cards.g.GoldenTailTrainer.class));
        cards.add(new SetCardInfo("Gravedig", 96, Rarity.COMMON, mage.cards.g.Gravedig.class));
        cards.add(new SetCardInfo("Grim Servant", 97, Rarity.UNCOMMON, mage.cards.g.GrimServant.class));
        cards.add(new SetCardInfo("Grist, Voracious Larva", 251, Rarity.MYTHIC, mage.cards.g.GristVoraciousLarva.class));
        cards.add(new SetCardInfo("Grist, the Plague Swarm", 251, Rarity.MYTHIC, mage.cards.g.GristThePlagueSwarm.class));
        cards.add(new SetCardInfo("Guardian of the Forgotten", 28, Rarity.UNCOMMON, mage.cards.g.GuardianOfTheForgotten.class));
        cards.add(new SetCardInfo("Guide of Souls", 29, Rarity.RARE, mage.cards.g.GuideOfSouls.class));
        cards.add(new SetCardInfo("Harbinger of the Seas", 63, Rarity.RARE, mage.cards.h.HarbingerOfTheSeas.class));
        cards.add(new SetCardInfo("Hexgold Slith", 30, Rarity.COMMON, mage.cards.h.HexgoldSlith.class));
        cards.add(new SetCardInfo("Hope-Ender Coatl", 64, Rarity.UNCOMMON, mage.cards.h.HopeEnderCoatl.class));
        cards.add(new SetCardInfo("Horrid Shadowspinner", 188, Rarity.UNCOMMON, mage.cards.h.HorridShadowspinner.class));
        cards.add(new SetCardInfo("Horrific Assault", 157, Rarity.COMMON, mage.cards.h.HorrificAssault.class));
        cards.add(new SetCardInfo("Hydra Trainer", 159, Rarity.UNCOMMON, mage.cards.h.HydraTrainer.class));
        cards.add(new SetCardInfo("Hydroelectric Specimen", 240, Rarity.UNCOMMON, mage.cards.h.HydroelectricSpecimen.class));
        cards.add(new SetCardInfo("Idol of False Gods", 210, Rarity.UNCOMMON, mage.cards.i.IdolOfFalseGods.class));
        cards.add(new SetCardInfo("Imskir Iron-Eater", 189, Rarity.RARE, mage.cards.i.ImskirIronEater.class));
        cards.add(new SetCardInfo("Indebted Spirit", 31, Rarity.UNCOMMON, mage.cards.i.IndebtedSpirit.class));
        cards.add(new SetCardInfo("Infernal Captor", 125, Rarity.COMMON, mage.cards.i.InfernalCaptor.class));
        cards.add(new SetCardInfo("Inspired Inventor", 32, Rarity.COMMON, mage.cards.i.InspiredInventor.class));
        cards.add(new SetCardInfo("Inventor's Axe", 126, Rarity.COMMON, mage.cards.i.InventorsAxe.class));
        cards.add(new SetCardInfo("Invert Polarity", 190, Rarity.RARE, mage.cards.i.InvertPolarity.class));
        cards.add(new SetCardInfo("Island", 305, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("It That Heralds the End", 9, Rarity.UNCOMMON, mage.cards.i.ItThatHeraldsTheEnd.class));
        cards.add(new SetCardInfo("Izzet Generatorium", 191, Rarity.UNCOMMON, mage.cards.i.IzzetGeneratorium.class));
        cards.add(new SetCardInfo("Jet Medallion", 292, Rarity.RARE, mage.cards.j.JetMedallion.class));
        cards.add(new SetCardInfo("Jolted Awake", 33, Rarity.COMMON, mage.cards.j.JoltedAwake.class));
        cards.add(new SetCardInfo("Junk Diver", 293, Rarity.UNCOMMON, mage.cards.j.JunkDiver.class));
        cards.add(new SetCardInfo("K'rrik, Son of Yawgmoth", 274, Rarity.RARE, mage.cards.k.KrrikSonOfYawgmoth.class));
        cards.add(new SetCardInfo("Kaalia of the Vast", 290, Rarity.MYTHIC, mage.cards.k.KaaliaOfTheVast.class));
        cards.add(new SetCardInfo("Kami of Jealous Thirst", 98, Rarity.COMMON, mage.cards.k.KamiOfJealousThirst.class));
        cards.add(new SetCardInfo("Kappa Cannoneer", 270, Rarity.RARE, mage.cards.k.KappaCannoneer.class));
        cards.add(new SetCardInfo("Kozilek's Command", 11, Rarity.RARE, mage.cards.k.KozileksCommand.class));
        cards.add(new SetCardInfo("Kozilek's Unsealing", 65, Rarity.UNCOMMON, mage.cards.k.KozileksUnsealing.class));
        cards.add(new SetCardInfo("Kozilek, the Broken Reality", 10, Rarity.MYTHIC, mage.cards.k.KozilekTheBrokenReality.class));
        cards.add(new SetCardInfo("Kudo, King Among Bears", 192, Rarity.RARE, mage.cards.k.KudoKingAmongBears.class));
        cards.add(new SetCardInfo("Laelia, the Blade Reforged", 281, Rarity.RARE, mage.cards.l.LaeliaTheBladeReforged.class));
        cards.add(new SetCardInfo("Legion Leadership", 255, Rarity.UNCOMMON, mage.cards.l.LegionLeadership.class));
        cards.add(new SetCardInfo("Lethal Throwdown", 99, Rarity.UNCOMMON, mage.cards.l.LethalThrowdown.class));
        cards.add(new SetCardInfo("Lion Umbra", 160, Rarity.UNCOMMON, mage.cards.l.LionUmbra.class));
        cards.add(new SetCardInfo("Malevolent Rumble", 161, Rarity.COMMON, mage.cards.m.MalevolentRumble.class));
        cards.add(new SetCardInfo("Mandibular Kite", 34, Rarity.COMMON, mage.cards.m.MandibularKite.class));
        cards.add(new SetCardInfo("Marionette Apprentice", 100, Rarity.UNCOMMON, mage.cards.m.MarionetteApprentice.class));
        cards.add(new SetCardInfo("Meltdown", 282, Rarity.UNCOMMON, mage.cards.m.Meltdown.class));
        cards.add(new SetCardInfo("Metastatic Evangel", 35, Rarity.UNCOMMON, mage.cards.m.MetastaticEvangel.class));
        cards.add(new SetCardInfo("Meteoric Mace", 283, Rarity.UNCOMMON, mage.cards.m.MeteoricMace.class));
        cards.add(new SetCardInfo("Mindless Conscription", 101, Rarity.UNCOMMON, mage.cards.m.MindlessConscription.class));
        cards.add(new SetCardInfo("Mogg Mob", 127, Rarity.UNCOMMON, mage.cards.m.MoggMob.class));
        cards.add(new SetCardInfo("Molten Gatekeeper", 128, Rarity.COMMON, mage.cards.m.MoltenGatekeeper.class));
        cards.add(new SetCardInfo("Monstrous Vortex", 162, Rarity.UNCOMMON, mage.cards.m.MonstrousVortex.class));
        cards.add(new SetCardInfo("Monumental Henge", 222, Rarity.RARE, mage.cards.m.MonumentalHenge.class));
        cards.add(new SetCardInfo("Mountain", 307, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Muster the Departed", 36, Rarity.UNCOMMON, mage.cards.m.MusterTheDeparted.class));
        cards.add(new SetCardInfo("Nadier's Nightblade", 275, Rarity.UNCOMMON, mage.cards.n.NadiersNightblade.class));
        cards.add(new SetCardInfo("Nadu, Winged Wisdom", 193, Rarity.RARE, mage.cards.n.NaduWingedWisdom.class));
        cards.add(new SetCardInfo("Necrodominance", 102, Rarity.MYTHIC, mage.cards.n.Necrodominance.class));
        cards.add(new SetCardInfo("Nesting Grounds", 302, Rarity.UNCOMMON, mage.cards.n.NestingGrounds.class));
        cards.add(new SetCardInfo("Nethergoyf", 103, Rarity.MYTHIC, mage.cards.n.Nethergoyf.class));
        cards.add(new SetCardInfo("Nightshade Dryad", 163, Rarity.COMMON, mage.cards.n.NightshadeDryad.class));
        cards.add(new SetCardInfo("Null Elemental Blast", 12, Rarity.UNCOMMON, mage.cards.n.NullElementalBlast.class));
        cards.add(new SetCardInfo("Nulldrifter", 13, Rarity.RARE, mage.cards.n.Nulldrifter.class));
        cards.add(new SetCardInfo("Nyxborn Hydra", 164, Rarity.COMMON, mage.cards.n.NyxbornHydra.class));
        cards.add(new SetCardInfo("Nyxborn Unicorn", 37, Rarity.COMMON, mage.cards.n.NyxbornUnicorn.class));
        cards.add(new SetCardInfo("Obstinate Gargoyle", 195, Rarity.COMMON, mage.cards.o.ObstinateGargoyle.class));
        cards.add(new SetCardInfo("Ocelot Pride", 38, Rarity.MYTHIC, mage.cards.o.OcelotPride.class));
        cards.add(new SetCardInfo("Ondu Knotmaster", 196, Rarity.UNCOMMON, mage.cards.o.OnduKnotmaster.class));
        cards.add(new SetCardInfo("Ophiomancer", 276, Rarity.RARE, mage.cards.o.Ophiomancer.class));
        cards.add(new SetCardInfo("Orim's Chant", 265, Rarity.RARE, mage.cards.o.OrimsChant.class));
        cards.add(new SetCardInfo("Party Thrasher", 129, Rarity.RARE, mage.cards.p.PartyThrasher.class));
        cards.add(new SetCardInfo("Path of Annihilation", 165, Rarity.UNCOMMON, mage.cards.p.PathOfAnnihilation.class));
        cards.add(new SetCardInfo("Pearl Medallion", 294, Rarity.RARE, mage.cards.p.PearlMedallion.class));
        cards.add(new SetCardInfo("Pearl-Ear, Imperial Advisor", 39, Rarity.RARE, mage.cards.p.PearlEarImperialAdvisor.class));
        cards.add(new SetCardInfo("Perilous Landscape", 223, Rarity.COMMON, mage.cards.p.PerilousLandscape.class));
        cards.add(new SetCardInfo("Petrifying Meddler", 66, Rarity.COMMON, mage.cards.p.PetrifyingMeddler.class));
        cards.add(new SetCardInfo("Phelia, Exuberant Shepherd", 40, Rarity.RARE, mage.cards.p.PheliaExuberantShepherd.class));
        cards.add(new SetCardInfo("Phlage, Titan of Fire's Fury", 197, Rarity.MYTHIC, mage.cards.p.PhlageTitanOfFiresFury.class));
        cards.add(new SetCardInfo("Phyrexian Ironworks", 130, Rarity.UNCOMMON, mage.cards.p.PhyrexianIronworks.class));
        cards.add(new SetCardInfo("Phyrexian Tower", 303, Rarity.MYTHIC, mage.cards.p.PhyrexianTower.class));
        cards.add(new SetCardInfo("Pinnacle Monk", 246, Rarity.UNCOMMON, mage.cards.p.PinnacleMonk.class));
        cards.add(new SetCardInfo("Plains", 304, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Planar Genesis", 198, Rarity.UNCOMMON, mage.cards.p.PlanarGenesis.class));
        cards.add(new SetCardInfo("Polluted Delta", 224, Rarity.RARE, mage.cards.p.PollutedDelta.class));
        cards.add(new SetCardInfo("Powerbalance", 131, Rarity.RARE, mage.cards.p.Powerbalance.class));
        cards.add(new SetCardInfo("Priest of Titania", 286, Rarity.UNCOMMON, mage.cards.p.PriestOfTitania.class));
        cards.add(new SetCardInfo("Propagator Drone", 167, Rarity.UNCOMMON, mage.cards.p.PropagatorDrone.class));
        cards.add(new SetCardInfo("Proud Pack-Rhino", 41, Rarity.UNCOMMON, mage.cards.p.ProudPackRhino.class));
        cards.add(new SetCardInfo("Psychic Frog", 199, Rarity.RARE, mage.cards.p.PsychicFrog.class));
        cards.add(new SetCardInfo("Pyretic Rebirth", 200, Rarity.UNCOMMON, mage.cards.p.PyreticRebirth.class));
        cards.add(new SetCardInfo("Quest for the Necropolis", 104, Rarity.UNCOMMON, mage.cards.q.QuestForTheNecropolis.class));
        cards.add(new SetCardInfo("Ral and the Implicit Maze", 132, Rarity.UNCOMMON, mage.cards.r.RalAndTheImplicitMaze.class));
        cards.add(new SetCardInfo("Ral, Leyline Prodigy", 247, Rarity.MYTHIC, mage.cards.r.RalLeylineProdigy.class));
        cards.add(new SetCardInfo("Ral, Monsoon Mage", 247, Rarity.MYTHIC, mage.cards.r.RalMonsoonMage.class));
        cards.add(new SetCardInfo("Razorgrass Ambush", 238, Rarity.UNCOMMON, mage.cards.r.RazorgrassAmbush.class));
        cards.add(new SetCardInfo("Reckless Pyrosurfer", 133, Rarity.UNCOMMON, mage.cards.r.RecklessPyrosurfer.class));
        cards.add(new SetCardInfo("Recruiter of the Guard", 266, Rarity.MYTHIC, mage.cards.r.RecruiterOfTheGuard.class));
        cards.add(new SetCardInfo("Reef Worm", 271, Rarity.UNCOMMON, mage.cards.r.ReefWorm.class));
        cards.add(new SetCardInfo("Refurbished Familiar", 105, Rarity.COMMON, mage.cards.r.RefurbishedFamiliar.class));
        cards.add(new SetCardInfo("Reiterating Bolt", 134, Rarity.UNCOMMON, mage.cards.r.ReiteratingBolt.class));
        cards.add(new SetCardInfo("Retrofitted Transmogrant", 106, Rarity.COMMON, mage.cards.r.RetrofittedTransmogrant.class));
        cards.add(new SetCardInfo("Revitalizing Repast", 256, Rarity.UNCOMMON, mage.cards.r.RevitalizingRepast.class));
        cards.add(new SetCardInfo("Riddle Gate Gargoyle", 201, Rarity.COMMON, mage.cards.r.RiddleGateGargoyle.class));
        cards.add(new SetCardInfo("Ripples of Undeath", 107, Rarity.RARE, mage.cards.r.RipplesOfUndeath.class));
        cards.add(new SetCardInfo("Roil Cartographer", 67, Rarity.UNCOMMON, mage.cards.r.RoilCartographer.class));
        cards.add(new SetCardInfo("Rosecot Knight", 42, Rarity.COMMON, mage.cards.r.RosecotKnight.class));
        cards.add(new SetCardInfo("Rosheen, Roaring Prophet", 202, Rarity.RARE, mage.cards.r.RosheenRoaringProphet.class));
        cards.add(new SetCardInfo("Ruby Medallion", 295, Rarity.RARE, mage.cards.r.RubyMedallion.class));
        cards.add(new SetCardInfo("Rush of Inspiration", 257, Rarity.UNCOMMON, mage.cards.r.RushOfInspiration.class));
        cards.add(new SetCardInfo("Sage of the Unknowable", 68, Rarity.COMMON, mage.cards.s.SageOfTheUnknowable.class));
        cards.add(new SetCardInfo("Sapphire Medallion", 296, Rarity.RARE, mage.cards.s.SapphireMedallion.class));
        cards.add(new SetCardInfo("Sarpadian Simulacrum", 135, Rarity.COMMON, mage.cards.s.SarpadianSimulacrum.class));
        cards.add(new SetCardInfo("Scurrilous Sentry", 108, Rarity.COMMON, mage.cards.s.ScurrilousSentry.class));
        cards.add(new SetCardInfo("Scurry of Gremlins", 203, Rarity.UNCOMMON, mage.cards.s.ScurryOfGremlins.class));
        cards.add(new SetCardInfo("Seething Landscape", 225, Rarity.COMMON, mage.cards.s.SeethingLandscape.class));
        cards.add(new SetCardInfo("Serum Visionary", 69, Rarity.COMMON, mage.cards.s.SerumVisionary.class));
        cards.add(new SetCardInfo("Sevinne's Reclamation", 267, Rarity.RARE, mage.cards.s.SevinnesReclamation.class));
        cards.add(new SetCardInfo("Shadow of the Second Sun", 70, Rarity.MYTHIC, mage.cards.s.ShadowOfTheSecondSun.class));
        cards.add(new SetCardInfo("Shattered Landscape", 226, Rarity.COMMON, mage.cards.s.ShatteredLandscape.class));
        cards.add(new SetCardInfo("Sheltering Landscape", 227, Rarity.COMMON, mage.cards.s.ShelteringLandscape.class));
        cards.add(new SetCardInfo("Shifting Woodland", 228, Rarity.RARE, mage.cards.s.ShiftingWoodland.class));
        cards.add(new SetCardInfo("Shilgengar, Sire of Famine", 109, Rarity.RARE, mage.cards.s.ShilgengarSireOfFamine.class));
        cards.add(new SetCardInfo("Shrieking Drake", 272, Rarity.UNCOMMON, mage.cards.s.ShriekingDrake.class));
        cards.add(new SetCardInfo("Siege Smash", 136, Rarity.COMMON, mage.cards.s.SiegeSmash.class));
        cards.add(new SetCardInfo("Signature Slam", 168, Rarity.UNCOMMON, mage.cards.s.SignatureSlam.class));
        cards.add(new SetCardInfo("Sink into Stupor", 241, Rarity.UNCOMMON, mage.cards.s.SinkIntoStupor.class));
        cards.add(new SetCardInfo("Six", 169, Rarity.RARE, mage.cards.s.Six.class));
        cards.add(new SetCardInfo("Skittering Precursor", 137, Rarity.UNCOMMON, mage.cards.s.SkitteringPrecursor.class));
        cards.add(new SetCardInfo("Skoa, Embermage", 138, Rarity.COMMON, mage.cards.s.SkoaEmbermage.class));
        cards.add(new SetCardInfo("Smelted Chargebug", 139, Rarity.COMMON, mage.cards.s.SmeltedChargebug.class));
        cards.add(new SetCardInfo("Snapping Voidcraw", 204, Rarity.COMMON, mage.cards.s.SnappingVoidcraw.class));
        cards.add(new SetCardInfo("Sneaky Snacker", 205, Rarity.COMMON, mage.cards.s.SneakySnacker.class));
        cards.add(new SetCardInfo("Snow-Covered Wastes", 229, Rarity.UNCOMMON, mage.cards.s.SnowCoveredWastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Solar Transformer", 211, Rarity.UNCOMMON, mage.cards.s.SolarTransformer.class));
        cards.add(new SetCardInfo("Solstice Zealot", 43, Rarity.COMMON, mage.cards.s.SolsticeZealot.class));
        cards.add(new SetCardInfo("Sorin of House Markov", 245, Rarity.MYTHIC, mage.cards.s.SorinOfHouseMarkov.class));
        cards.add(new SetCardInfo("Sorin, Ravenous Neonate", 245, Rarity.MYTHIC, mage.cards.s.SorinRavenousNeonate.class));
        cards.add(new SetCardInfo("Sowing Mycospawn", 170, Rarity.RARE, mage.cards.s.SowingMycospawn.class));
        cards.add(new SetCardInfo("Spawn-Gang Commander", 140, Rarity.UNCOMMON, mage.cards.s.SpawnGangCommander.class));
        cards.add(new SetCardInfo("Springheart Nantuko", 171, Rarity.RARE, mage.cards.s.SpringheartNantuko.class));
        cards.add(new SetCardInfo("Spymaster's Vault", 230, Rarity.RARE, mage.cards.s.SpymastersVault.class));
        cards.add(new SetCardInfo("Static Prison", 44, Rarity.UNCOMMON, mage.cards.s.StaticPrison.class));
        cards.add(new SetCardInfo("Strength of the Harvest", 258, Rarity.UNCOMMON, mage.cards.s.StrengthOfTheHarvest.class));
        cards.add(new SetCardInfo("Strix Serenade", 71, Rarity.RARE, mage.cards.s.StrixSerenade.class));
        cards.add(new SetCardInfo("Stump Stomp", 259, Rarity.UNCOMMON, mage.cards.s.StumpStomp.class));
        cards.add(new SetCardInfo("Sundering Eruption", 248, Rarity.UNCOMMON, mage.cards.s.SunderingEruption.class));
        cards.add(new SetCardInfo("Suppression Ray", 260, Rarity.UNCOMMON, mage.cards.s.SuppressionRay.class));
        cards.add(new SetCardInfo("Swamp", 306, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Safekeeper", 287, Rarity.RARE, mage.cards.s.SylvanSafekeeper.class));
        cards.add(new SetCardInfo("Tamiyo Meets the Story Circle", 72, Rarity.UNCOMMON, mage.cards.t.TamiyoMeetsTheStoryCircle.class));
        cards.add(new SetCardInfo("Tamiyo, Inquisitive Student", 242, Rarity.MYTHIC, mage.cards.t.TamiyoInquisitiveStudent.class));
        cards.add(new SetCardInfo("Tamiyo, Seasoned Scholar", 242, Rarity.MYTHIC, mage.cards.t.TamiyoSeasonedScholar.class));
        cards.add(new SetCardInfo("Temperamental Oozewagg", 172, Rarity.COMMON, mage.cards.t.TemperamentalOozewagg.class));
        cards.add(new SetCardInfo("Tempest Harvester", 73, Rarity.COMMON, mage.cards.t.TempestHarvester.class));
        cards.add(new SetCardInfo("Territory Culler", 173, Rarity.UNCOMMON, mage.cards.t.TerritoryCuller.class));
        cards.add(new SetCardInfo("The Creation of Avacyn", 86, Rarity.UNCOMMON, mage.cards.t.TheCreationOfAvacyn.class));
        cards.add(new SetCardInfo("The Hunger Tide Rises", 158, Rarity.UNCOMMON, mage.cards.t.TheHungerTideRises.class));
        cards.add(new SetCardInfo("The Necrobloom", 194, Rarity.RARE, mage.cards.t.TheNecrobloom.class));
        cards.add(new SetCardInfo("Thief of Existence", 174, Rarity.RARE, mage.cards.t.ThiefOfExistence.class));
        cards.add(new SetCardInfo("Thraben Charm", 45, Rarity.COMMON, mage.cards.t.ThrabenCharm.class));
        cards.add(new SetCardInfo("Thriving Skyclaw", 141, Rarity.COMMON, mage.cards.t.ThrivingSkyclaw.class));
        cards.add(new SetCardInfo("Titans' Vanguard", 206, Rarity.UNCOMMON, mage.cards.t.TitansVanguard.class));
        cards.add(new SetCardInfo("Toxic Deluge", 277, Rarity.RARE, mage.cards.t.ToxicDeluge.class));
        cards.add(new SetCardInfo("Tranquil Landscape", 231, Rarity.COMMON, mage.cards.t.TranquilLandscape.class));
        cards.add(new SetCardInfo("Trickster's Elk", 175, Rarity.UNCOMMON, mage.cards.t.TrickstersElk.class));
        cards.add(new SetCardInfo("Triton Wavebreaker", 74, Rarity.UNCOMMON, mage.cards.t.TritonWavebreaker.class));
        cards.add(new SetCardInfo("Tune the Narrative", 75, Rarity.COMMON, mage.cards.t.TuneTheNarrative.class));
        cards.add(new SetCardInfo("Twisted Landscape", 232, Rarity.COMMON, mage.cards.t.TwistedLandscape.class));
        cards.add(new SetCardInfo("Twisted Riddlekeeper", 14, Rarity.UNCOMMON, mage.cards.t.TwistedRiddlekeeper.class));
        cards.add(new SetCardInfo("Ugin's Binding", 76, Rarity.MYTHIC, mage.cards.u.UginsBinding.class));
        cards.add(new SetCardInfo("Ugin's Labyrinth", 233, Rarity.MYTHIC, mage.cards.u.UginsLabyrinth.class));
        cards.add(new SetCardInfo("Ulamog, the Defiler", 15, Rarity.MYTHIC, mage.cards.u.UlamogTheDefiler.class));
        cards.add(new SetCardInfo("Unfathomable Truths", 77, Rarity.COMMON, mage.cards.u.UnfathomableTruths.class));
        cards.add(new SetCardInfo("Unstable Amulet", 142, Rarity.UNCOMMON, mage.cards.u.UnstableAmulet.class));
        cards.add(new SetCardInfo("Urza's Cave", 234, Rarity.UNCOMMON, mage.cards.u.UrzasCave.class));
        cards.add(new SetCardInfo("Urza's Incubator", 297, Rarity.RARE, mage.cards.u.UrzasIncubator.class));
        cards.add(new SetCardInfo("Utter Insignificance", 78, Rarity.COMMON, mage.cards.u.UtterInsignificance.class));
        cards.add(new SetCardInfo("Vexing Bauble", 212, Rarity.UNCOMMON, mage.cards.v.VexingBauble.class));
        cards.add(new SetCardInfo("Victimize", 278, Rarity.UNCOMMON, mage.cards.v.Victimize.class));
        cards.add(new SetCardInfo("Voidpouncer", 143, Rarity.COMMON, mage.cards.v.Voidpouncer.class));
        cards.add(new SetCardInfo("Volatile Stormdrake", 79, Rarity.RARE, mage.cards.v.VolatileStormdrake.class));
        cards.add(new SetCardInfo("Voltstorm Angel", 46, Rarity.UNCOMMON, mage.cards.v.VoltstormAngel.class));
        cards.add(new SetCardInfo("Warped Tusker", 16, Rarity.COMMON, mage.cards.w.WarpedTusker.class));
        cards.add(new SetCardInfo("Warren Soultrader", 110, Rarity.RARE, mage.cards.w.WarrenSoultrader.class));
        cards.add(new SetCardInfo("Wastescape Battlemage", 17, Rarity.UNCOMMON, mage.cards.w.WastescapeBattlemage.class));
        cards.add(new SetCardInfo("Waterlogged Teachings", 261, Rarity.UNCOMMON, mage.cards.w.WaterloggedTeachings.class));
        cards.add(new SetCardInfo("Wheel of Potential", 144, Rarity.RARE, mage.cards.w.WheelOfPotential.class));
        cards.add(new SetCardInfo("White Orchid Phantom", 47, Rarity.RARE, mage.cards.w.WhiteOrchidPhantom.class));
        cards.add(new SetCardInfo("Wight of the Reliquary", 207, Rarity.RARE, mage.cards.w.WightOfTheReliquary.class));
        cards.add(new SetCardInfo("Windswept Heath", 235, Rarity.RARE, mage.cards.w.WindsweptHeath.class));
        cards.add(new SetCardInfo("Wing It", 48, Rarity.COMMON, mage.cards.w.WingIt.class));
        cards.add(new SetCardInfo("Winter Moon", 213, Rarity.RARE, mage.cards.w.WinterMoon.class));
        cards.add(new SetCardInfo("Wirewood Symbiote", 288, Rarity.UNCOMMON, mage.cards.w.WirewoodSymbiote.class));
        cards.add(new SetCardInfo("Witch Enchanter", 239, Rarity.UNCOMMON, mage.cards.w.WitchEnchanter.class));
        cards.add(new SetCardInfo("Wither and Bloom", 111, Rarity.COMMON, mage.cards.w.WitherAndBloom.class));
        cards.add(new SetCardInfo("Wooded Foothills", 236, Rarity.RARE, mage.cards.w.WoodedFoothills.class));
        cards.add(new SetCardInfo("Worn Powerstone", 298, Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class));
        cards.add(new SetCardInfo("Wrath of the Skies", 49, Rarity.RARE, mage.cards.w.WrathOfTheSkies.class));
        cards.add(new SetCardInfo("Writhing Chrysalis", 208, Rarity.COMMON, mage.cards.w.WrithingChrysalis.class));
        cards.add(new SetCardInfo("Wumpus Aberration", 176, Rarity.UNCOMMON, mage.cards.w.WumpusAberration.class));
        cards.add(new SetCardInfo("Wurmcoil Larva", 112, Rarity.UNCOMMON, mage.cards.w.WurmcoilLarva.class));
    }

    @Override
    public List<Card> tryBooster() {
        // TODO: make part of this more generic, this is the second try at a play booster generation so we try to see what can be shared.
        // source https://magic.wizards.com/en/news/feature/collecting-modern-horizons-3

        // We start by deciding the various slots.
        // some slots have guarantee rarity
        int rareOrMythic = 1;
        int newToModern = 1;
        int uncommon = 3;
        int common = 5; // 6 common slots, one being sometimes replaced by a secret guest

        // Land Slot: 1/2 chance for a basic, 1/2 chance for a common
        int basicLand = 0;
        {
            if (RandomUtil.nextDouble() <= 0.5) {
                basicLand++;
            } else {
                common++;
            }
        }

        // 1 slot is 1/64 chance to be spg, and 1/5 - 1/64 to be otp, 4/5 to be common
        int spg = 0;
        {
            double rollSpg = RandomUtil.nextDouble();
            if (rollSpg <= 1.0 / 64.0) { // know probability of 1/64 to be spg
                spg++;
            } else {
                common++;
            }
        }

        int commander = 0;
        // 1 slot is a wildcard:
        // 41.7% chance to be among 80 common
        // 41.7% chance to be among 101 uncommons
        // 7.8% chance to be among rare/mythic (with weight 2:1 individually)
        // 4.2% chance to be among 8 Commander Mythic Rares (4.2% in total).
        // TODO: only that part has been generated (up to 95.6%).
        //       miss some new to modern (not all) that have retro frames, (4.2%),
        //       some variants (0.4%), and full art snow covered wastes (<0.1%)
        {
            double total = 0.417 * 2 + 0.078 + 0.042;
            double rollWildcard = RandomUtil.nextDouble() * total;
            if (rollWildcard <= 0.417) {
                common++;
            } else if (rollWildcard <= 0.417 * 2) {
                uncommon++;
            } else if (rollWildcard <= 0.417 * 2 + 0.078) {
                rareOrMythic++;
            } else {
                commander++;
            }
        }

        // wildcard foil slot, reusing the previous wildcard slot, altough maybe it is different.
        {
            double total = 0.417 * 2 + 0.078 + 0.042;
            double rollWildcard = RandomUtil.nextDouble() * total;
            if (rollWildcard <= 0.417) {
                common++;
            } else if (rollWildcard <= 0.417 * 2) {
                uncommon++;
            } else if (rollWildcard <= 0.417 * 2 + 0.078) {
                rareOrMythic++;
            } else {
                commander++;
            }
        }

        // The booster we are building
        List<Card> booster = new ArrayList<>();

        // 1 -> 261 are the 261 regular common/uncommon/rare/mythic
        // 262 -> 303 are the 42 new to modern reprint cards (uncommon/rare/mythic)
        // 304 -> 319 are basics lands (with 309 being Snow-Covered Waste)
        List<CardInfo> list_MH3_C =
                getCardsByRarity(Rarity.COMMON).stream()
                        .filter(info -> info.getCardNumberAsInt() <= 261)
                        .collect(Collectors.toList());
        List<CardInfo> list_MH3_U =
                getCardsByRarity(Rarity.UNCOMMON).stream()
                        .filter(info -> info.getCardNumberAsInt() <= 261)
                        .collect(Collectors.toList());
        List<CardInfo> list_MH3_R =
                getCardsByRarity(Rarity.RARE)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= 261)
                        .collect(Collectors.toList());
        List<CardInfo> list_MH3_M =
                getCardsByRarity(Rarity.MYTHIC)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= 261)
                        .collect(Collectors.toList());
        List<CardInfo> list_MH3_Basic =
                getCardsByRarity(Rarity.LAND)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= maxCardNumberInBooster)
                        .collect(Collectors.toList());
        List<CardInfo> list_RTM_U =
                getCardsByRarity(Rarity.UNCOMMON)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() >= 262 && info.getCardNumberAsInt() <= 303)
                        .collect(Collectors.toList());
        List<CardInfo> list_RTM_R =
                getCardsByRarity(Rarity.RARE)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() >= 262 && info.getCardNumberAsInt() <= 303)
                        .collect(Collectors.toList());
        List<CardInfo> list_RTM_M =
                getCardsByRarity(Rarity.MYTHIC)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() >= 262 && info.getCardNumberAsInt() <= 303)
                        .collect(Collectors.toList());
        List<CardInfo> list_SPG =
                SpecialGuests.getInstance().getCardsByRarity(Rarity.MYTHIC)
                        .stream()
                        .filter(info -> {
                            int cn = info.getCardNumberAsInt();
                            return cn >= 39 && cn <= 48;
                        })
                        .collect(Collectors.toList());
        List<CardInfo> list_M3C =
                ModernHorizons3Commander.getInstance().getCardsByRarity(Rarity.MYTHIC)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= 8)
                        .collect(Collectors.toList());

        for (int i = 0; i < spg; i++) {
            addToBooster(booster, list_SPG);
        }
        double ratioMythic = (double) list_MH3_M.size() / (double) (list_MH3_M.size() + list_MH3_R.size() * 2);
        for (int i = 0; i < rareOrMythic; i++) {
            if (RandomUtil.nextDouble() <= ratioMythic) {
                addToBooster(booster, list_MH3_M);
            } else {
                addToBooster(booster, list_MH3_R);
            }
        }
        for (int i = 0; i < commander; i++) {
            addToBooster(booster, list_M3C);
        }
        double ratioUncommonRTM = 0.75; // Uncommons relative to rare+mythic
        double ratioMythicRTM = (double) list_RTM_M.size() / (double) (list_RTM_M.size() + list_RTM_R.size() * 2); // mythics relative to rare
        for (int i = 0; i < newToModern; i++) {
            if (RandomUtil.nextDouble() <= ratioUncommonRTM) {
                addToBooster(booster, list_RTM_U);
            } else if (RandomUtil.nextDouble() <= ratioMythicRTM) {
                addToBooster(booster, list_RTM_M);
            } else {
                addToBooster(booster, list_RTM_R);
            }
        }
        for (int i = 0; i < uncommon; i++) {
            addToBooster(booster, list_MH3_U);
        }
        for (int i = 0; i < common; i++) {
            addToBooster(booster, list_MH3_C);
        }
        for (int i = 0; i < basicLand; i++) {
            addToBooster(booster, list_MH3_Basic);
        }

        return booster;
    }
}
