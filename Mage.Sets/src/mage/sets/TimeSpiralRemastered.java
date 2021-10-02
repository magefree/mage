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
 * @author TheElk801
 */
public class TimeSpiralRemastered extends ExpansionSet {

    private static final TimeSpiralRemastered instance = new TimeSpiralRemastered();

    public static TimeSpiralRemastered getInstance() {
        return instance;
    }

    private final List<CardInfo> savedSpecialBonus = new ArrayList<>();

    private TimeSpiralRemastered() {
        super("Time Spiral Remastered", "TSR", ExpansionSet.buildDate(2021, 3, 19), SetType.SUPPLEMENTAL);
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.maxCardNumberInBooster = 410;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.numBoosterSpecial = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Abrupt Decay", 370, Rarity.SPECIAL, mage.cards.a.AbruptDecay.class));
        cards.add(new SetCardInfo("Aeon Chronicler", 51, Rarity.RARE, mage.cards.a.AeonChronicler.class));
        cards.add(new SetCardInfo("Ajani's Pridemate", 290, Rarity.SPECIAL, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Akroma's Memorial", 262, Rarity.MYTHIC, mage.cards.a.AkromasMemorial.class));
        cards.add(new SetCardInfo("Akroma, Angel of Fury", 150, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfFury.class));
        cards.add(new SetCardInfo("Alesha, Who Smiles at Death", 338, Rarity.SPECIAL, mage.cards.a.AleshaWhoSmilesAtDeath.class));
        cards.add(new SetCardInfo("Amrou Scout", 1, Rarity.COMMON, mage.cards.a.AmrouScout.class));
        cards.add(new SetCardInfo("Amrou Seekers", 2, Rarity.COMMON, mage.cards.a.AmrouSeekers.class));
        cards.add(new SetCardInfo("Ancestral Vision", 52, Rarity.MYTHIC, mage.cards.a.AncestralVision.class));
        cards.add(new SetCardInfo("Ancient Den", 403, Rarity.SPECIAL, mage.cards.a.AncientDen.class));
        cards.add(new SetCardInfo("Ancient Grudge", 151, Rarity.COMMON, mage.cards.a.AncientGrudge.class));
        cards.add(new SetCardInfo("Ancient Stirrings", 355, Rarity.SPECIAL, mage.cards.a.AncientStirrings.class));
        cards.add(new SetCardInfo("Angel of Salvation", 3, Rarity.RARE, mage.cards.a.AngelOfSalvation.class));
        cards.add(new SetCardInfo("Angel's Grace", 4, Rarity.RARE, mage.cards.a.AngelsGrace.class));
        cards.add(new SetCardInfo("Anger of the Gods", 339, Rarity.SPECIAL, mage.cards.a.AngerOfTheGods.class));
        cards.add(new SetCardInfo("Arc Blade", 152, Rarity.UNCOMMON, mage.cards.a.ArcBlade.class));
        cards.add(new SetCardInfo("Arcades, the Strategist", 371, Rarity.SPECIAL, mage.cards.a.ArcadesTheStrategist.class));
        cards.add(new SetCardInfo("Arch of Orazca", 404, Rarity.SPECIAL, mage.cards.a.ArchOfOrazca.class));
        cards.add(new SetCardInfo("Assassinate", 101, Rarity.COMMON, mage.cards.a.Assassinate.class));
        cards.add(new SetCardInfo("Aven Mindcensor", 5, Rarity.UNCOMMON, mage.cards.a.AvenMindcensor.class));
        cards.add(new SetCardInfo("Aven Riftwatcher", 6, Rarity.COMMON, mage.cards.a.AvenRiftwatcher.class));
        cards.add(new SetCardInfo("Banishing Light", 291, Rarity.SPECIAL, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Baral, Chief of Compliance", 306, Rarity.SPECIAL, mage.cards.b.BaralChiefOfCompliance.class));
        cards.add(new SetCardInfo("Basalt Gargoyle", 153, Rarity.UNCOMMON, mage.cards.b.BasaltGargoyle.class));
        cards.add(new SetCardInfo("Battering Sliver", 154, Rarity.COMMON, mage.cards.b.BatteringSliver.class));
        cards.add(new SetCardInfo("Beast Whisperer", 356, Rarity.SPECIAL, mage.cards.b.BeastWhisperer.class));
        cards.add(new SetCardInfo("Beast Within", 357, Rarity.SPECIAL, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Become Immense", 358, Rarity.SPECIAL, mage.cards.b.BecomeImmense.class));
        cards.add(new SetCardInfo("Bedlam Reveler", 340, Rarity.SPECIAL, mage.cards.b.BedlamReveler.class));
        cards.add(new SetCardInfo("Benalish Cavalry", 7, Rarity.COMMON, mage.cards.b.BenalishCavalry.class));
        cards.add(new SetCardInfo("Benalish Commander", 8, Rarity.RARE, mage.cards.b.BenalishCommander.class));
        cards.add(new SetCardInfo("Bewilder", 53, Rarity.COMMON, mage.cards.b.Bewilder.class));
        cards.add(new SetCardInfo("Big Game Hunter", 102, Rarity.UNCOMMON, mage.cards.b.BigGameHunter.class));
        cards.add(new SetCardInfo("Blade of the Sixth Pride", 9, Rarity.COMMON, mage.cards.b.BladeOfTheSixthPride.class));
        cards.add(new SetCardInfo("Blighted Woodland", 405, Rarity.SPECIAL, mage.cards.b.BlightedWoodland.class));
        cards.add(new SetCardInfo("Blightspeaker", 103, Rarity.COMMON, mage.cards.b.Blightspeaker.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", 372, Rarity.SPECIAL, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Bojuka Bog", 406, Rarity.SPECIAL, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Bonded Fetch", 54, Rarity.UNCOMMON, mage.cards.b.BondedFetch.class));
        cards.add(new SetCardInfo("Bonesplitter Sliver", 155, Rarity.COMMON, mage.cards.b.BonesplitterSliver.class));
        cards.add(new SetCardInfo("Boom // Bust", 156, Rarity.RARE, mage.cards.b.BoomBust.class));
        cards.add(new SetCardInfo("Bound in Silence", 10, Rarity.COMMON, mage.cards.b.BoundInSilence.class));
        cards.add(new SetCardInfo("Brine Elemental", 55, Rarity.UNCOMMON, mage.cards.b.BrineElemental.class));
        cards.add(new SetCardInfo("Brute Force", 157, Rarity.COMMON, mage.cards.b.BruteForce.class));
        cards.add(new SetCardInfo("Calciderm", 11, Rarity.UNCOMMON, mage.cards.c.Calciderm.class));
        cards.add(new SetCardInfo("Calciform Pools", 275, Rarity.UNCOMMON, mage.cards.c.CalciformPools.class));
        cards.add(new SetCardInfo("Careful Consideration", 56, Rarity.UNCOMMON, mage.cards.c.CarefulConsideration.class));
        cards.add(new SetCardInfo("Castle Raptors", 12, Rarity.COMMON, mage.cards.c.CastleRaptors.class));
        cards.add(new SetCardInfo("Cautery Sliver", 248, Rarity.UNCOMMON, mage.cards.c.CauterySliver.class));
        cards.add(new SetCardInfo("Celestial Crusader", 13, Rarity.UNCOMMON, mage.cards.c.CelestialCrusader.class));
        cards.add(new SetCardInfo("Chalice of the Void", 390, Rarity.SPECIAL, mage.cards.c.ChaliceOfTheVoid.class));
        cards.add(new SetCardInfo("Char-Rumbler", 158, Rarity.UNCOMMON, mage.cards.c.CharRumbler.class));
        cards.add(new SetCardInfo("Children of Korlis", 14, Rarity.COMMON, mage.cards.c.ChildrenOfKorlis.class));
        cards.add(new SetCardInfo("Chromatic Star", 263, Rarity.COMMON, mage.cards.c.ChromaticStar.class));
        cards.add(new SetCardInfo("Citanul Woodreaders", 199, Rarity.COMMON, mage.cards.c.CitanulWoodreaders.class));
        cards.add(new SetCardInfo("Clockwork Hydra", 264, Rarity.UNCOMMON, mage.cards.c.ClockworkHydra.class));
        cards.add(new SetCardInfo("Cloud Key", 265, Rarity.RARE, mage.cards.c.CloudKey.class));
        cards.add(new SetCardInfo("Cloudseeder", 57, Rarity.UNCOMMON, mage.cards.c.Cloudseeder.class));
        cards.add(new SetCardInfo("Cloudshredder Sliver", 373, Rarity.SPECIAL, mage.cards.c.CloudshredderSliver.class));
        cards.add(new SetCardInfo("Coal Stoker", 159, Rarity.COMMON, mage.cards.c.CoalStoker.class));
        cards.add(new SetCardInfo("Coalition Relic", 266, Rarity.RARE, mage.cards.c.CoalitionRelic.class));
        cards.add(new SetCardInfo("Conflagrate", 160, Rarity.UNCOMMON, mage.cards.c.Conflagrate.class));
        cards.add(new SetCardInfo("Consuming Aberration", 374, Rarity.SPECIAL, mage.cards.c.ConsumingAberration.class));
        cards.add(new SetCardInfo("Contagion Clasp", 391, Rarity.SPECIAL, mage.cards.c.ContagionClasp.class));
        cards.add(new SetCardInfo("Containment Priest", 292, Rarity.SPECIAL, mage.cards.c.ContainmentPriest.class));
        cards.add(new SetCardInfo("Coral Trickster", 58, Rarity.COMMON, mage.cards.c.CoralTrickster.class));
        cards.add(new SetCardInfo("Corpulent Corpse", 104, Rarity.COMMON, mage.cards.c.CorpulentCorpse.class));
        cards.add(new SetCardInfo("Courser of Kruphix", 359, Rarity.SPECIAL, mage.cards.c.CourserOfKruphix.class));
        cards.add(new SetCardInfo("Cranial Plating", 392, Rarity.SPECIAL, mage.cards.c.CranialPlating.class));
        cards.add(new SetCardInfo("Crookclaw Transmuter", 59, Rarity.COMMON, mage.cards.c.CrookclawTransmuter.class));
        cards.add(new SetCardInfo("Crovax, Ascendant Hero", 15, Rarity.MYTHIC, mage.cards.c.CrovaxAscendantHero.class));
        cards.add(new SetCardInfo("Cryptic Annelid", 60, Rarity.UNCOMMON, mage.cards.c.CrypticAnnelid.class));
        cards.add(new SetCardInfo("Crystal Shard", 393, Rarity.SPECIAL, mage.cards.c.CrystalShard.class));
        cards.add(new SetCardInfo("Cutthroat il-Dal", 105, Rarity.COMMON, mage.cards.c.CutthroatIlDal.class));
        cards.add(new SetCardInfo("Damnation", 106, Rarity.MYTHIC, mage.cards.d.Damnation.class));
        cards.add(new SetCardInfo("Dark Withering", 107, Rarity.COMMON, mage.cards.d.DarkWithering.class));
        cards.add(new SetCardInfo("Darkheart Sliver", 249, Rarity.UNCOMMON, mage.cards.d.DarkheartSliver.class));
        cards.add(new SetCardInfo("Dead // Gone", 161, Rarity.COMMON, mage.cards.d.DeadGone.class));
        cards.add(new SetCardInfo("Deadly Grub", 108, Rarity.COMMON, mage.cards.d.DeadlyGrub.class));
        cards.add(new SetCardInfo("Deathspore Thallid", 109, Rarity.COMMON, mage.cards.d.DeathsporeThallid.class));
        cards.add(new SetCardInfo("Deepcavern Imp", 110, Rarity.COMMON, mage.cards.d.DeepcavernImp.class));
        cards.add(new SetCardInfo("Delay", 61, Rarity.UNCOMMON, mage.cards.d.Delay.class));
        cards.add(new SetCardInfo("Disdainful Stroke", 307, Rarity.SPECIAL, mage.cards.d.DisdainfulStroke.class));
        cards.add(new SetCardInfo("Dismember", 322, Rarity.SPECIAL, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Dormant Sliver", 250, Rarity.UNCOMMON, mage.cards.d.DormantSliver.class));
        cards.add(new SetCardInfo("Dovin's Veto", 375, Rarity.SPECIAL, mage.cards.d.DovinsVeto.class));
        cards.add(new SetCardInfo("Draining Whelk", 62, Rarity.RARE, mage.cards.d.DrainingWhelk.class));
        cards.add(new SetCardInfo("Dralnu, Lich Lord", 251, Rarity.RARE, mage.cards.d.DralnuLichLord.class));
        cards.add(new SetCardInfo("Dread Return", 111, Rarity.UNCOMMON, mage.cards.d.DreadReturn.class));
        cards.add(new SetCardInfo("Dreadhorde Arcanist", 341, Rarity.SPECIAL, mage.cards.d.DreadhordeArcanist.class));
        cards.add(new SetCardInfo("Dreadship Reef", 276, Rarity.UNCOMMON, mage.cards.d.DreadshipReef.class));
        cards.add(new SetCardInfo("Dream Stalker", 63, Rarity.COMMON, mage.cards.d.DreamStalker.class));
        cards.add(new SetCardInfo("Dreamscape Artist", 64, Rarity.COMMON, mage.cards.d.DreamscapeArtist.class));
        cards.add(new SetCardInfo("Drifter il-Dal", 65, Rarity.COMMON, mage.cards.d.DrifterIlDal.class));
        cards.add(new SetCardInfo("Dryad Arbor", 277, Rarity.RARE, mage.cards.d.DryadArbor.class));
        cards.add(new SetCardInfo("Dunerider Outlaw", 112, Rarity.UNCOMMON, mage.cards.d.DuneriderOutlaw.class));
        cards.add(new SetCardInfo("Durkwood Baloth", 200, Rarity.COMMON, mage.cards.d.DurkwoodBaloth.class));
        cards.add(new SetCardInfo("Duskrider Peregrine", 16, Rarity.UNCOMMON, mage.cards.d.DuskriderPeregrine.class));
        cards.add(new SetCardInfo("Edge of Autumn", 201, Rarity.COMMON, mage.cards.e.EdgeOfAutumn.class));
        cards.add(new SetCardInfo("Elvish Mystic", 360, Rarity.SPECIAL, mage.cards.e.ElvishMystic.class));
        cards.add(new SetCardInfo("Empty the Warrens", 162, Rarity.COMMON, mage.cards.e.EmptyTheWarrens.class));
        cards.add(new SetCardInfo("Enslave", 113, Rarity.UNCOMMON, mage.cards.e.Enslave.class));
        cards.add(new SetCardInfo("Epic Experiment", 376, Rarity.SPECIAL, mage.cards.e.EpicExperiment.class));
        cards.add(new SetCardInfo("Errant Doomsayers", 17, Rarity.COMMON, mage.cards.e.ErrantDoomsayers.class));
        cards.add(new SetCardInfo("Errant Ephemeron", 66, Rarity.COMMON, mage.cards.e.ErrantEphemeron.class));
        cards.add(new SetCardInfo("Erratic Mutation", 67, Rarity.COMMON, mage.cards.e.ErraticMutation.class));
        cards.add(new SetCardInfo("Etali, Primal Storm", 342, Rarity.SPECIAL, mage.cards.e.EtaliPrimalStorm.class));
        cards.add(new SetCardInfo("Eternal Witness", 361, Rarity.SPECIAL, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Ethereal Armor", 293, Rarity.SPECIAL, mage.cards.e.EtherealArmor.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 394, Rarity.SPECIAL, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Evolution Charm", 202, Rarity.COMMON, mage.cards.e.EvolutionCharm.class));
        cards.add(new SetCardInfo("Evolutionary Leap", 362, Rarity.SPECIAL, mage.cards.e.EvolutionaryLeap.class));
        cards.add(new SetCardInfo("Exquisite Firecraft", 343, Rarity.SPECIAL, mage.cards.e.ExquisiteFirecraft.class));
        cards.add(new SetCardInfo("Extirpate", 114, Rarity.RARE, mage.cards.e.Extirpate.class));
        cards.add(new SetCardInfo("Faceless Devourer", 115, Rarity.UNCOMMON, mage.cards.f.FacelessDevourer.class));
        cards.add(new SetCardInfo("Farseek", 363, Rarity.SPECIAL, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Fathom Seer", 68, Rarity.COMMON, mage.cards.f.FathomSeer.class));
        cards.add(new SetCardInfo("Fblthp, the Lost", 308, Rarity.SPECIAL, mage.cards.f.FblthpTheLost.class));
        cards.add(new SetCardInfo("Feather, the Redeemed", 377, Rarity.SPECIAL, mage.cards.f.FeatherTheRedeemed.class));
        cards.add(new SetCardInfo("Feebleness", 116, Rarity.COMMON, mage.cards.f.Feebleness.class));
        cards.add(new SetCardInfo("Feldon of the Third Path", 344, Rarity.SPECIAL, mage.cards.f.FeldonOfTheThirdPath.class));
        cards.add(new SetCardInfo("Field of Ruin", 407, Rarity.SPECIAL, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Firemaw Kavu", 163, Rarity.UNCOMMON, mage.cards.f.FiremawKavu.class));
        cards.add(new SetCardInfo("Firewake Sliver", 252, Rarity.UNCOMMON, mage.cards.f.FirewakeSliver.class));
        cards.add(new SetCardInfo("Flagstones of Trokair", 278, Rarity.RARE, mage.cards.f.FlagstonesOfTrokair.class));
        cards.add(new SetCardInfo("Flickerwisp", 294, Rarity.SPECIAL, mage.cards.f.Flickerwisp.class));
        cards.add(new SetCardInfo("Foresee", 69, Rarity.COMMON, mage.cards.f.Foresee.class));
        cards.add(new SetCardInfo("Fortify", 18, Rarity.COMMON, mage.cards.f.Fortify.class));
        cards.add(new SetCardInfo("Fungal Reaches", 279, Rarity.UNCOMMON, mage.cards.f.FungalReaches.class));
        cards.add(new SetCardInfo("Fungus Sliver", 203, Rarity.RARE, mage.cards.f.FungusSliver.class));
        cards.add(new SetCardInfo("Fury Sliver", 164, Rarity.UNCOMMON, mage.cards.f.FurySliver.class));
        cards.add(new SetCardInfo("Gaea's Anthem", 204, Rarity.UNCOMMON, mage.cards.g.GaeasAnthem.class));
        cards.add(new SetCardInfo("Gathan Raiders", 165, Rarity.COMMON, mage.cards.g.GathanRaiders.class));
        cards.add(new SetCardInfo("Gauntlet of Power", 267, Rarity.MYTHIC, mage.cards.g.GauntletOfPower.class));
        cards.add(new SetCardInfo("Gemhide Sliver", 205, Rarity.COMMON, mage.cards.g.GemhideSliver.class));
        cards.add(new SetCardInfo("Gemstone Caverns", 280, Rarity.MYTHIC, mage.cards.g.GemstoneCaverns.class));
        cards.add(new SetCardInfo("Giant Dustwasp", 206, Rarity.COMMON, mage.cards.g.GiantDustwasp.class));
        cards.add(new SetCardInfo("Glittering Wish", 253, Rarity.RARE, mage.cards.g.GlitteringWish.class));
        cards.add(new SetCardInfo("Goblin Engineer", 345, Rarity.SPECIAL, mage.cards.g.GoblinEngineer.class));
        cards.add(new SetCardInfo("Gorgon Recluse", 117, Rarity.COMMON, mage.cards.g.GorgonRecluse.class));
        cards.add(new SetCardInfo("Gossamer Phantasm", 70, Rarity.COMMON, mage.cards.g.GossamerPhantasm.class));
        cards.add(new SetCardInfo("Grapeshot", 166, Rarity.COMMON, mage.cards.g.Grapeshot.class));
        cards.add(new SetCardInfo("Grave Scrabbler", 118, Rarity.COMMON, mage.cards.g.GraveScrabbler.class));
        cards.add(new SetCardInfo("Gray Merchant of Asphodel", 323, Rarity.SPECIAL, mage.cards.g.GrayMerchantOfAsphodel.class));
        cards.add(new SetCardInfo("Greater Gargadon", 167, Rarity.RARE, mage.cards.g.GreaterGargadon.class));
        cards.add(new SetCardInfo("Greenseeker", 207, Rarity.COMMON, mage.cards.g.Greenseeker.class));
        cards.add(new SetCardInfo("Grenzo, Dungeon Warden", 378, Rarity.SPECIAL, mage.cards.g.GrenzoDungeonWarden.class));
        cards.add(new SetCardInfo("Griffin Guide", 19, Rarity.UNCOMMON, mage.cards.g.GriffinGuide.class));
        cards.add(new SetCardInfo("Grinning Ignus", 168, Rarity.COMMON, mage.cards.g.GrinningIgnus.class));
        cards.add(new SetCardInfo("Gurmag Angler", 324, Rarity.SPECIAL, mage.cards.g.GurmagAngler.class));
        cards.add(new SetCardInfo("Harmonic Sliver", 254, Rarity.UNCOMMON, mage.cards.h.HarmonicSliver.class));
        cards.add(new SetCardInfo("Harmonize", 208, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Harvester of Souls", 325, Rarity.SPECIAL, mage.cards.h.HarvesterOfSouls.class));
        cards.add(new SetCardInfo("Haze of Rage", 169, Rarity.UNCOMMON, mage.cards.h.HazeOfRage.class));
        cards.add(new SetCardInfo("Heartwood Storyteller", 209, Rarity.RARE, mage.cards.h.HeartwoodStoryteller.class));
        cards.add(new SetCardInfo("Hedron Archive", 395, Rarity.SPECIAL, mage.cards.h.HedronArchive.class));
        cards.add(new SetCardInfo("Henchfiend of Ukor", 170, Rarity.UNCOMMON, mage.cards.h.HenchfiendOfUkor.class));
        cards.add(new SetCardInfo("Hivestone", 268, Rarity.RARE, mage.cards.h.Hivestone.class));
        cards.add(new SetCardInfo("Hollow One", 396, Rarity.SPECIAL, mage.cards.h.HollowOne.class));
        cards.add(new SetCardInfo("Homing Sliver", 171, Rarity.COMMON, mage.cards.h.HomingSliver.class));
        cards.add(new SetCardInfo("Hypergenesis", 210, Rarity.MYTHIC, mage.cards.h.Hypergenesis.class));
        cards.add(new SetCardInfo("Ichor Slick", 119, Rarity.COMMON, mage.cards.i.IchorSlick.class));
        cards.add(new SetCardInfo("Imperiosaur", 211, Rarity.UNCOMMON, mage.cards.i.Imperiosaur.class));
        cards.add(new SetCardInfo("Infiltrator il-Kor", 71, Rarity.COMMON, mage.cards.i.InfiltratorIlKor.class));
        cards.add(new SetCardInfo("Intangible Virtue", 295, Rarity.SPECIAL, mage.cards.i.IntangibleVirtue.class));
        cards.add(new SetCardInfo("Ith, High Arcanist", 255, Rarity.RARE, mage.cards.i.IthHighArcanist.class));
        cards.add(new SetCardInfo("Ivory Giant", 20, Rarity.COMMON, mage.cards.i.IvoryGiant.class));
        cards.add(new SetCardInfo("Jaya Ballard, Task Mage", 172, Rarity.RARE, mage.cards.j.JayaBallardTaskMage.class));
        cards.add(new SetCardInfo("Jhoira of the Ghitu", 256, Rarity.RARE, mage.cards.j.JhoiraOfTheGhitu.class));
        cards.add(new SetCardInfo("Jhoira's Timebug", 269, Rarity.COMMON, mage.cards.j.JhoirasTimebug.class));
        cards.add(new SetCardInfo("Jodah's Avenger", 72, Rarity.UNCOMMON, mage.cards.j.JodahsAvenger.class));
        cards.add(new SetCardInfo("Judge Unworthy", 21, Rarity.COMMON, mage.cards.j.JudgeUnworthy.class));
        cards.add(new SetCardInfo("Kaervek the Merciless", 257, Rarity.RARE, mage.cards.k.KaervekTheMerciless.class));
        cards.add(new SetCardInfo("Kavu Primarch", 212, Rarity.COMMON, mage.cards.k.KavuPrimarch.class));
        cards.add(new SetCardInfo("Keen Sense", 213, Rarity.UNCOMMON, mage.cards.k.KeenSense.class));
        cards.add(new SetCardInfo("Keldon Halberdier", 173, Rarity.COMMON, mage.cards.k.KeldonHalberdier.class));
        cards.add(new SetCardInfo("Kher Keep", 281, Rarity.RARE, mage.cards.k.KherKeep.class));
        cards.add(new SetCardInfo("Kiki-Jiki, Mirror Breaker", 346, Rarity.SPECIAL, mage.cards.k.KikiJikiMirrorBreaker.class));
        cards.add(new SetCardInfo("Knight of Sursi", 22, Rarity.COMMON, mage.cards.k.KnightOfSursi.class));
        cards.add(new SetCardInfo("Knight of the Holy Nimbus", 23, Rarity.UNCOMMON, mage.cards.k.KnightOfTheHolyNimbus.class));
        cards.add(new SetCardInfo("Knight of the Reliquary", 379, Rarity.SPECIAL, mage.cards.k.KnightOfTheReliquary.class));
        cards.add(new SetCardInfo("Kor Dirge", 120, Rarity.UNCOMMON, mage.cards.k.KorDirge.class));
        cards.add(new SetCardInfo("Krosan Grip", 214, Rarity.UNCOMMON, mage.cards.k.KrosanGrip.class));
        cards.add(new SetCardInfo("Laboratory Maniac", 309, Rarity.SPECIAL, mage.cards.l.LaboratoryManiac.class));
        cards.add(new SetCardInfo("Lava Spike", 347, Rarity.SPECIAL, mage.cards.l.LavaSpike.class));
        cards.add(new SetCardInfo("Lavinia, Azorius Renegade", 380, Rarity.SPECIAL, mage.cards.l.LaviniaAzoriusRenegade.class));
        cards.add(new SetCardInfo("Leveler", 397, Rarity.SPECIAL, mage.cards.l.Leveler.class));
        cards.add(new SetCardInfo("Leyline of the Void", 326, Rarity.SPECIAL, mage.cards.l.LeylineOfTheVoid.class));
        cards.add(new SetCardInfo("Life and Limb", 215, Rarity.RARE, mage.cards.l.LifeAndLimb.class));
        cards.add(new SetCardInfo("Lightning Axe", 174, Rarity.UNCOMMON, mage.cards.l.LightningAxe.class));
        cards.add(new SetCardInfo("Liliana's Triumph", 327, Rarity.SPECIAL, mage.cards.l.LilianasTriumph.class));
        cards.add(new SetCardInfo("Lingering Souls", 296, Rarity.SPECIAL, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Living End", 121, Rarity.MYTHIC, mage.cards.l.LivingEnd.class));
        cards.add(new SetCardInfo("Llanowar Mentor", 216, Rarity.UNCOMMON, mage.cards.l.LlanowarMentor.class));
        cards.add(new SetCardInfo("Logic Knot", 73, Rarity.COMMON, mage.cards.l.LogicKnot.class));
        cards.add(new SetCardInfo("Looter il-Kor", 74, Rarity.COMMON, mage.cards.l.LooterIlKor.class));
        cards.add(new SetCardInfo("Lost Auramancers", 24, Rarity.UNCOMMON, mage.cards.l.LostAuramancers.class));
        cards.add(new SetCardInfo("Lotus Bloom", 270, Rarity.RARE, mage.cards.l.LotusBloom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lotus Bloom", 411, Rarity.RARE, mage.cards.l.LotusBloom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lymph Sliver", 25, Rarity.COMMON, mage.cards.l.LymphSliver.class));
        cards.add(new SetCardInfo("Magus of the Future", 75, Rarity.RARE, mage.cards.m.MagusOfTheFuture.class));
        cards.add(new SetCardInfo("Magus of the Moon", 175, Rarity.RARE, mage.cards.m.MagusOfTheMoon.class));
        cards.add(new SetCardInfo("Mana Tithe", 26, Rarity.COMMON, mage.cards.m.ManaTithe.class));
        cards.add(new SetCardInfo("Mangara of Corondor", 27, Rarity.RARE, mage.cards.m.MangaraOfCorondor.class));
        cards.add(new SetCardInfo("Manifold Key", 398, Rarity.SPECIAL, mage.cards.m.ManifoldKey.class));
        cards.add(new SetCardInfo("Mass of Ghouls", 122, Rarity.COMMON, mage.cards.m.MassOfGhouls.class));
        cards.add(new SetCardInfo("Master of the Pearl Trident", 310, Rarity.SPECIAL, mage.cards.m.MasterOfThePearlTrident.class));
        cards.add(new SetCardInfo("Might Sliver", 218, Rarity.UNCOMMON, mage.cards.m.MightSliver.class));
        cards.add(new SetCardInfo("Might of Old Krosa", 217, Rarity.UNCOMMON, mage.cards.m.MightOfOldKrosa.class));
        cards.add(new SetCardInfo("Mindstab", 123, Rarity.COMMON, mage.cards.m.Mindstab.class));
        cards.add(new SetCardInfo("Minions' Murmurs", 124, Rarity.UNCOMMON, mage.cards.m.MinionsMurmurs.class));
        cards.add(new SetCardInfo("Mire Boa", 219, Rarity.UNCOMMON, mage.cards.m.MireBoa.class));
        cards.add(new SetCardInfo("Mirri the Cursed", 125, Rarity.RARE, mage.cards.m.MirriTheCursed.class));
        cards.add(new SetCardInfo("Mirror Entity", 297, Rarity.SPECIAL, mage.cards.m.MirrorEntity.class));
        cards.add(new SetCardInfo("Mogg War Marshal", 176, Rarity.COMMON, mage.cards.m.MoggWarMarshal.class));
        cards.add(new SetCardInfo("Molten Rain", 348, Rarity.SPECIAL, mage.cards.m.MoltenRain.class));
        cards.add(new SetCardInfo("Molten Slagheap", 282, Rarity.UNCOMMON, mage.cards.m.MoltenSlagheap.class));
        cards.add(new SetCardInfo("Momentary Blink", 28, Rarity.COMMON, mage.cards.m.MomentaryBlink.class));
        cards.add(new SetCardInfo("Monastery Swiftspear", 349, Rarity.SPECIAL, mage.cards.m.MonasterySwiftspear.class));
        cards.add(new SetCardInfo("Mortify", 381, Rarity.SPECIAL, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Muck Drubb", 126, Rarity.UNCOMMON, mage.cards.m.MuckDrubb.class));
        cards.add(new SetCardInfo("Mulldrifter", 311, Rarity.SPECIAL, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Muraganda Petroglyphs", 220, Rarity.RARE, mage.cards.m.MuragandaPetroglyphs.class));
        cards.add(new SetCardInfo("Mycologist", 29, Rarity.UNCOMMON, mage.cards.m.Mycologist.class));
        cards.add(new SetCardInfo("Mystic Confluence", 312, Rarity.SPECIAL, mage.cards.m.MysticConfluence.class));
        cards.add(new SetCardInfo("Mystic Sanctuary", 408, Rarity.SPECIAL, mage.cards.m.MysticSanctuary.class));
        cards.add(new SetCardInfo("Mystical Teachings", 76, Rarity.UNCOMMON, mage.cards.m.MysticalTeachings.class));
        cards.add(new SetCardInfo("Nantuko Shaman", 221, Rarity.COMMON, mage.cards.n.NantukoShaman.class));
        cards.add(new SetCardInfo("Nature's Claim", 364, Rarity.SPECIAL, mage.cards.n.NaturesClaim.class));
        cards.add(new SetCardInfo("Necrotic Sliver", 258, Rarity.UNCOMMON, mage.cards.n.NecroticSliver.class));
        cards.add(new SetCardInfo("Needlepeak Spider", 177, Rarity.COMMON, mage.cards.n.NeedlepeakSpider.class));
        cards.add(new SetCardInfo("Nether Traitor", 127, Rarity.RARE, mage.cards.n.NetherTraitor.class));
        cards.add(new SetCardInfo("Nightshade Assassin", 128, Rarity.UNCOMMON, mage.cards.n.NightshadeAssassin.class));
        cards.add(new SetCardInfo("Ninja of the Deep Hours", 313, Rarity.SPECIAL, mage.cards.n.NinjaOfTheDeepHours.class));
        cards.add(new SetCardInfo("Orcish Cannonade", 178, Rarity.COMMON, mage.cards.o.OrcishCannonade.class));
        cards.add(new SetCardInfo("Outrider en-Kor", 30, Rarity.UNCOMMON, mage.cards.o.OutriderEnKor.class));
        cards.add(new SetCardInfo("Pact of Negation", 77, Rarity.RARE, mage.cards.p.PactOfNegation.class));
        cards.add(new SetCardInfo("Pact of the Titan", 179, Rarity.RARE, mage.cards.p.PactOfTheTitan.class));
        cards.add(new SetCardInfo("Palace Jailer", 298, Rarity.SPECIAL, mage.cards.p.PalaceJailer.class));
        cards.add(new SetCardInfo("Pallid Mycoderm", 31, Rarity.COMMON, mage.cards.p.PallidMycoderm.class));
        cards.add(new SetCardInfo("Panharmonicon", 399, Rarity.SPECIAL, mage.cards.p.Panharmonicon.class));
        cards.add(new SetCardInfo("Paradise Plume", 271, Rarity.UNCOMMON, mage.cards.p.ParadisePlume.class));
        cards.add(new SetCardInfo("Paradoxical Outcome", 314, Rarity.SPECIAL, mage.cards.p.ParadoxicalOutcome.class));
        cards.add(new SetCardInfo("Past in Flames", 350, Rarity.SPECIAL, mage.cards.p.PastInFlames.class));
        cards.add(new SetCardInfo("Path to Exile", 299, Rarity.SPECIAL, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pendelhaven Elder", 222, Rarity.UNCOMMON, mage.cards.p.PendelhavenElder.class));
        cards.add(new SetCardInfo("Penumbra Spider", 223, Rarity.COMMON, mage.cards.p.PenumbraSpider.class));
        cards.add(new SetCardInfo("Phantom Wurm", 224, Rarity.UNCOMMON, mage.cards.p.PhantomWurm.class));
        cards.add(new SetCardInfo("Phthisis", 129, Rarity.UNCOMMON, mage.cards.p.Phthisis.class));
        cards.add(new SetCardInfo("Piracy Charm", 78, Rarity.COMMON, mage.cards.p.PiracyCharm.class));
        cards.add(new SetCardInfo("Pit Keeper", 130, Rarity.COMMON, mage.cards.p.PitKeeper.class));
        cards.add(new SetCardInfo("Ponder", 315, Rarity.SPECIAL, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Pongify", 79, Rarity.UNCOMMON, mage.cards.p.Pongify.class));
        cards.add(new SetCardInfo("Porphyry Nodes", 32, Rarity.RARE, mage.cards.p.PorphyryNodes.class));
        cards.add(new SetCardInfo("Poultice Sliver", 33, Rarity.UNCOMMON, mage.cards.p.PoulticeSliver.class));
        cards.add(new SetCardInfo("Premature Burial", 131, Rarity.UNCOMMON, mage.cards.p.PrematureBurial.class));
        cards.add(new SetCardInfo("Primal Forcemage", 225, Rarity.UNCOMMON, mage.cards.p.PrimalForcemage.class));
        cards.add(new SetCardInfo("Primal Plasma", 80, Rarity.COMMON, mage.cards.p.PrimalPlasma.class));
        cards.add(new SetCardInfo("Primeval Titan", 365, Rarity.SPECIAL, mage.cards.p.PrimevalTitan.class));
        cards.add(new SetCardInfo("Prismatic Lens", 272, Rarity.COMMON, mage.cards.p.PrismaticLens.class));
        cards.add(new SetCardInfo("Prized Amalgam", 382, Rarity.SPECIAL, mage.cards.p.PrizedAmalgam.class));
        cards.add(new SetCardInfo("Prodigal Pyromancer", 180, Rarity.UNCOMMON, mage.cards.p.ProdigalPyromancer.class));
        cards.add(new SetCardInfo("Psychotic Episode", 132, Rarity.COMMON, mage.cards.p.PsychoticEpisode.class));
        cards.add(new SetCardInfo("Pulmonic Sliver", 34, Rarity.RARE, mage.cards.p.PulmonicSliver.class));
        cards.add(new SetCardInfo("Qasali Pridemage", 383, Rarity.SPECIAL, mage.cards.q.QasaliPridemage.class));
        cards.add(new SetCardInfo("Radha, Heir to Keld", 259, Rarity.RARE, mage.cards.r.RadhaHeirToKeld.class));
        cards.add(new SetCardInfo("Rakdos Charm", 384, Rarity.SPECIAL, mage.cards.r.RakdosCharm.class));
        cards.add(new SetCardInfo("Ramunap Ruins", 409, Rarity.SPECIAL, mage.cards.r.RamunapRuins.class));
        cards.add(new SetCardInfo("Rathi Trapper", 133, Rarity.COMMON, mage.cards.r.RathiTrapper.class));
        cards.add(new SetCardInfo("Read the Bones", 328, Rarity.SPECIAL, mage.cards.r.ReadTheBones.class));
        cards.add(new SetCardInfo("Reality Acid", 81, Rarity.COMMON, mage.cards.r.RealityAcid.class));
        cards.add(new SetCardInfo("Rebuff the Wicked", 35, Rarity.UNCOMMON, mage.cards.r.RebuffTheWicked.class));
        cards.add(new SetCardInfo("Reckless Wurm", 181, Rarity.COMMON, mage.cards.r.RecklessWurm.class));
        cards.add(new SetCardInfo("Reclamation Sage", 366, Rarity.SPECIAL, mage.cards.r.ReclamationSage.class));
        cards.add(new SetCardInfo("Reflex Sliver", 226, Rarity.COMMON, mage.cards.r.ReflexSliver.class));
        cards.add(new SetCardInfo("Reiterate", 182, Rarity.RARE, mage.cards.r.Reiterate.class));
        cards.add(new SetCardInfo("Relentless Rats", 329, Rarity.SPECIAL, mage.cards.r.RelentlessRats.class));
        cards.add(new SetCardInfo("Remand", 316, Rarity.SPECIAL, mage.cards.r.Remand.class));
        cards.add(new SetCardInfo("Repeal", 317, Rarity.SPECIAL, mage.cards.r.Repeal.class));
        cards.add(new SetCardInfo("Restoration Angel", 300, Rarity.SPECIAL, mage.cards.r.RestorationAngel.class));
        cards.add(new SetCardInfo("Restore Balance", 36, Rarity.MYTHIC, mage.cards.r.RestoreBalance.class));
        cards.add(new SetCardInfo("Return to Dust", 37, Rarity.UNCOMMON, mage.cards.r.ReturnToDust.class));
        cards.add(new SetCardInfo("Riddle of Lightning", 183, Rarity.COMMON, mage.cards.r.RiddleOfLightning.class));
        cards.add(new SetCardInfo("Ridged Kusite", 134, Rarity.COMMON, mage.cards.r.RidgedKusite.class));
        cards.add(new SetCardInfo("Rift Bolt", 184, Rarity.COMMON, mage.cards.r.RiftBolt.class));
        cards.add(new SetCardInfo("Rift Elemental", 185, Rarity.COMMON, mage.cards.r.RiftElemental.class));
        cards.add(new SetCardInfo("Riftmarked Knight", 38, Rarity.UNCOMMON, mage.cards.r.RiftmarkedKnight.class));
        cards.add(new SetCardInfo("Riftwing Cloudskate", 82, Rarity.UNCOMMON, mage.cards.r.RiftwingCloudskate.class));
        cards.add(new SetCardInfo("Riptide Pilferer", 83, Rarity.UNCOMMON, mage.cards.r.RiptidePilferer.class));
        cards.add(new SetCardInfo("Rough // Tumble", 186, Rarity.UNCOMMON, mage.cards.r.RoughTumble.class));
        cards.add(new SetCardInfo("Saffi Eriksdotter", 260, Rarity.RARE, mage.cards.s.SaffiEriksdotter.class));
        cards.add(new SetCardInfo("Saltblast", 39, Rarity.UNCOMMON, mage.cards.s.Saltblast.class));
        cards.add(new SetCardInfo("Saltcrusted Steppe", 283, Rarity.UNCOMMON, mage.cards.s.SaltcrustedSteppe.class));
        cards.add(new SetCardInfo("Saltfield Recluse", 40, Rarity.UNCOMMON, mage.cards.s.SaltfieldRecluse.class));
        cards.add(new SetCardInfo("Sangrophage", 135, Rarity.COMMON, mage.cards.s.Sangrophage.class));
        cards.add(new SetCardInfo("Sanguine Bond", 330, Rarity.SPECIAL, mage.cards.s.SanguineBond.class));
        cards.add(new SetCardInfo("Sarcomite Myr", 84, Rarity.COMMON, mage.cards.s.SarcomiteMyr.class));
        cards.add(new SetCardInfo("Scryb Ranger", 227, Rarity.UNCOMMON, mage.cards.s.ScrybRanger.class));
        cards.add(new SetCardInfo("Seal of Primordium", 228, Rarity.COMMON, mage.cards.s.SealOfPrimordium.class));
        cards.add(new SetCardInfo("Search for Tomorrow", 229, Rarity.COMMON, mage.cards.s.SearchForTomorrow.class));
        cards.add(new SetCardInfo("Secret Plans", 385, Rarity.SPECIAL, mage.cards.s.SecretPlans.class));
        cards.add(new SetCardInfo("Sedge Sliver", 187, Rarity.RARE, mage.cards.s.SedgeSliver.class));
        cards.add(new SetCardInfo("Sengir Nosferatu", 136, Rarity.RARE, mage.cards.s.SengirNosferatu.class));
        cards.add(new SetCardInfo("Serra Avenger", 41, Rarity.RARE, mage.cards.s.SerraAvenger.class));
        cards.add(new SetCardInfo("Shade of Trokair", 42, Rarity.COMMON, mage.cards.s.ShadeOfTrokair.class));
        cards.add(new SetCardInfo("Shaper Parasite", 85, Rarity.UNCOMMON, mage.cards.s.ShaperParasite.class));
        cards.add(new SetCardInfo("Shivan Meteor", 188, Rarity.UNCOMMON, mage.cards.s.ShivanMeteor.class));
        cards.add(new SetCardInfo("Shivan Sand-Mage", 189, Rarity.UNCOMMON, mage.cards.s.ShivanSandMage.class));
        cards.add(new SetCardInfo("Shriekmaw", 331, Rarity.SPECIAL, mage.cards.s.Shriekmaw.class));
        cards.add(new SetCardInfo("Sidewinder Sliver", 43, Rarity.COMMON, mage.cards.s.SidewinderSliver.class));
        cards.add(new SetCardInfo("Sigil of the Empty Throne", 301, Rarity.SPECIAL, mage.cards.s.SigilOfTheEmptyThrone.class));
        cards.add(new SetCardInfo("Silence", 302, Rarity.SPECIAL, mage.cards.s.Silence.class));
        cards.add(new SetCardInfo("Simian Spirit Guide", 190, Rarity.COMMON, mage.cards.s.SimianSpiritGuide.class));
        cards.add(new SetCardInfo("Sinew Sliver", 44, Rarity.COMMON, mage.cards.s.SinewSliver.class));
        cards.add(new SetCardInfo("Skirk Shaman", 191, Rarity.COMMON, mage.cards.s.SkirkShaman.class));
        cards.add(new SetCardInfo("Skittering Monstrosity", 137, Rarity.UNCOMMON, mage.cards.s.SkitteringMonstrosity.class));
        cards.add(new SetCardInfo("Slaughter Pact", 138, Rarity.RARE, mage.cards.s.SlaughterPact.class));
        cards.add(new SetCardInfo("Slimefoot, the Stowaway", 386, Rarity.SPECIAL, mage.cards.s.SlimefootTheStowaway.class));
        cards.add(new SetCardInfo("Slipstream Serpent", 86, Rarity.COMMON, mage.cards.s.SlipstreamSerpent.class));
        cards.add(new SetCardInfo("Sliver Legion", 261, Rarity.MYTHIC, mage.cards.s.SliverLegion.class));
        cards.add(new SetCardInfo("Sliversmith", 273, Rarity.UNCOMMON, mage.cards.s.Sliversmith.class));
        cards.add(new SetCardInfo("Smallpox", 139, Rarity.UNCOMMON, mage.cards.s.Smallpox.class));
        cards.add(new SetCardInfo("Snapback", 87, Rarity.COMMON, mage.cards.s.Snapback.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 400, Rarity.SPECIAL, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Sorcerous Spyglass", 401, Rarity.SPECIAL, mage.cards.s.SorcerousSpyglass.class));
        cards.add(new SetCardInfo("Spell Burst", 88, Rarity.UNCOMMON, mage.cards.s.SpellBurst.class));
        cards.add(new SetCardInfo("Spiketail Drakeling", 89, Rarity.COMMON, mage.cards.s.SpiketailDrakeling.class));
        cards.add(new SetCardInfo("Spinneret Sliver", 230, Rarity.COMMON, mage.cards.s.SpinneretSliver.class));
        cards.add(new SetCardInfo("Sporesower Thallid", 231, Rarity.UNCOMMON, mage.cards.s.SporesowerThallid.class));
        cards.add(new SetCardInfo("Sporoloth Ancient", 232, Rarity.COMMON, mage.cards.s.SporolothAncient.class));
        cards.add(new SetCardInfo("Sram, Senior Edificer", 303, Rarity.SPECIAL, mage.cards.s.SramSeniorEdificer.class));
        cards.add(new SetCardInfo("Stingscourger", 192, Rarity.COMMON, mage.cards.s.Stingscourger.class));
        cards.add(new SetCardInfo("Stinkweed Imp", 332, Rarity.SPECIAL, mage.cards.s.StinkweedImp.class));
        cards.add(new SetCardInfo("Stonecloaker", 45, Rarity.UNCOMMON, mage.cards.s.Stonecloaker.class));
        cards.add(new SetCardInfo("Stonehorn Dignitary", 304, Rarity.SPECIAL, mage.cards.s.StonehornDignitary.class));
        cards.add(new SetCardInfo("Storm Entity", 193, Rarity.UNCOMMON, mage.cards.s.StormEntity.class));
        cards.add(new SetCardInfo("Stormcloud Djinn", 90, Rarity.UNCOMMON, mage.cards.s.StormcloudDjinn.class));
        cards.add(new SetCardInfo("Stormfront Riders", 46, Rarity.UNCOMMON, mage.cards.s.StormfrontRiders.class));
        cards.add(new SetCardInfo("Strangling Soot", 140, Rarity.COMMON, mage.cards.s.StranglingSoot.class));
        cards.add(new SetCardInfo("Street Wraith", 141, Rarity.UNCOMMON, mage.cards.s.StreetWraith.class));
        cards.add(new SetCardInfo("Strength in Numbers", 233, Rarity.COMMON, mage.cards.s.StrengthInNumbers.class));
        cards.add(new SetCardInfo("Stronghold Rats", 142, Rarity.UNCOMMON, mage.cards.s.StrongholdRats.class));
        cards.add(new SetCardInfo("Stuffy Doll", 274, Rarity.RARE, mage.cards.s.StuffyDoll.class));
        cards.add(new SetCardInfo("Sudden Death", 143, Rarity.UNCOMMON, mage.cards.s.SuddenDeath.class));
        cards.add(new SetCardInfo("Sudden Shock", 194, Rarity.UNCOMMON, mage.cards.s.SuddenShock.class));
        cards.add(new SetCardInfo("Sudden Spoiling", 144, Rarity.RARE, mage.cards.s.SuddenSpoiling.class));
        cards.add(new SetCardInfo("Sulfur Elemental", 195, Rarity.UNCOMMON, mage.cards.s.SulfurElemental.class));
        cards.add(new SetCardInfo("Summoner's Pact", 234, Rarity.RARE, mage.cards.s.SummonersPact.class));
        cards.add(new SetCardInfo("Sunlance", 47, Rarity.COMMON, mage.cards.s.Sunlance.class));
        cards.add(new SetCardInfo("Swarmyard", 284, Rarity.RARE, mage.cards.s.Swarmyard.class));
        cards.add(new SetCardInfo("Sylvan Scrying", 367, Rarity.SPECIAL, mage.cards.s.SylvanScrying.class));
        cards.add(new SetCardInfo("Talrand, Sky Summoner", 318, Rarity.SPECIAL, mage.cards.t.TalrandSkySummoner.class));
        cards.add(new SetCardInfo("Tarmogoyf", 235, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Tasigur, the Golden Fang", 333, Rarity.SPECIAL, mage.cards.t.TasigurTheGoldenFang.class));
        cards.add(new SetCardInfo("Teferi, Mage of Zhalfir", 91, Rarity.MYTHIC, mage.cards.t.TeferiMageOfZhalfir.class));
        cards.add(new SetCardInfo("Temporal Isolation", 48, Rarity.COMMON, mage.cards.t.TemporalIsolation.class));
        cards.add(new SetCardInfo("Temur Ascendancy", 387, Rarity.SPECIAL, mage.cards.t.TemurAscendancy.class));
        cards.add(new SetCardInfo("Temur Battle Rage", 351, Rarity.SPECIAL, mage.cards.t.TemurBattleRage.class));
        cards.add(new SetCardInfo("Tendrils of Corruption", 145, Rarity.COMMON, mage.cards.t.TendrilsOfCorruption.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 285, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Thallid Germinator", 236, Rarity.COMMON, mage.cards.t.ThallidGerminator.class));
        cards.add(new SetCardInfo("Thallid Shell-Dweller", 237, Rarity.COMMON, mage.cards.t.ThallidShellDweller.class));
        cards.add(new SetCardInfo("Thelon of Havenwood", 238, Rarity.RARE, mage.cards.t.ThelonOfHavenwood.class));
        cards.add(new SetCardInfo("Thelonite Hermit", 239, Rarity.RARE, mage.cards.t.TheloniteHermit.class));
        cards.add(new SetCardInfo("Thick-Skinned Goblin", 196, Rarity.UNCOMMON, mage.cards.t.ThickSkinnedGoblin.class));
        cards.add(new SetCardInfo("Think Twice", 92, Rarity.COMMON, mage.cards.t.ThinkTwice.class));
        cards.add(new SetCardInfo("Thornweald Archer", 240, Rarity.COMMON, mage.cards.t.ThornwealdArcher.class));
        cards.add(new SetCardInfo("Thoughtseize", 334, Rarity.SPECIAL, mage.cards.t.Thoughtseize.class));
        cards.add(new SetCardInfo("Thraben Inspector", 305, Rarity.SPECIAL, mage.cards.t.ThrabenInspector.class));
        cards.add(new SetCardInfo("Thragtusk", 368, Rarity.SPECIAL, mage.cards.t.Thragtusk.class));
        cards.add(new SetCardInfo("Thrill of the Hunt", 241, Rarity.COMMON, mage.cards.t.ThrillOfTheHunt.class));
        cards.add(new SetCardInfo("Tidehollow Sculler", 388, Rarity.SPECIAL, mage.cards.t.TidehollowSculler.class));
        cards.add(new SetCardInfo("Time of Need", 369, Rarity.SPECIAL, mage.cards.t.TimeOfNeed.class));
        cards.add(new SetCardInfo("Timebender", 93, Rarity.UNCOMMON, mage.cards.t.Timebender.class));
        cards.add(new SetCardInfo("Tolaria West", 286, Rarity.RARE, mage.cards.t.TolariaWest.class));
        cards.add(new SetCardInfo("Tolarian Sentinel", 94, Rarity.COMMON, mage.cards.t.TolarianSentinel.class));
        cards.add(new SetCardInfo("Tombstalker", 146, Rarity.RARE, mage.cards.t.Tombstalker.class));
        cards.add(new SetCardInfo("Treasure Cruise", 319, Rarity.SPECIAL, mage.cards.t.TreasureCruise.class));
        cards.add(new SetCardInfo("Trespasser il-Vec", 147, Rarity.COMMON, mage.cards.t.TrespasserIlVec.class));
        cards.add(new SetCardInfo("Trinket Mage", 320, Rarity.SPECIAL, mage.cards.t.TrinketMage.class));
        cards.add(new SetCardInfo("Tromp the Domains", 242, Rarity.UNCOMMON, mage.cards.t.TrompTheDomains.class));
        cards.add(new SetCardInfo("True-Name Nemesis", 321, Rarity.SPECIAL, mage.cards.t.TrueNameNemesis.class));
        cards.add(new SetCardInfo("Trygon Predator", 389, Rarity.SPECIAL, mage.cards.t.TrygonPredator.class));
        cards.add(new SetCardInfo("Two-Headed Sliver", 197, Rarity.COMMON, mage.cards.t.TwoHeadedSliver.class));
        cards.add(new SetCardInfo("Uktabi Drake", 243, Rarity.COMMON, mage.cards.u.UktabiDrake.class));
        cards.add(new SetCardInfo("Urborg Syphon-Mage", 148, Rarity.COMMON, mage.cards.u.UrborgSyphonMage.class));
        cards.add(new SetCardInfo("Urborg, Tomb of Yawgmoth", 287, Rarity.RARE, mage.cards.u.UrborgTombOfYawgmoth.class));
        cards.add(new SetCardInfo("Urza's Factory", 288, Rarity.UNCOMMON, mage.cards.u.UrzasFactory.class));
        cards.add(new SetCardInfo("Utopia Mycon", 244, Rarity.UNCOMMON, mage.cards.u.UtopiaMycon.class));
        cards.add(new SetCardInfo("Utopia Vow", 245, Rarity.COMMON, mage.cards.u.UtopiaVow.class));
        cards.add(new SetCardInfo("Vampire Hexmage", 335, Rarity.SPECIAL, mage.cards.v.VampireHexmage.class));
        cards.add(new SetCardInfo("Vandalblast", 352, Rarity.SPECIAL, mage.cards.v.Vandalblast.class));
        cards.add(new SetCardInfo("Vanquisher's Banner", 402, Rarity.SPECIAL, mage.cards.v.VanquishersBanner.class));
        cards.add(new SetCardInfo("Veiling Oddity", 95, Rarity.COMMON, mage.cards.v.VeilingOddity.class));
        cards.add(new SetCardInfo("Venser, Shaper Savant", 96, Rarity.RARE, mage.cards.v.VenserShaperSavant.class));
        cards.add(new SetCardInfo("Vesuva", 289, Rarity.MYTHIC, mage.cards.v.Vesuva.class));
        cards.add(new SetCardInfo("Vesuvan Shapeshifter", 97, Rarity.RARE, mage.cards.v.VesuvanShapeshifter.class));
        cards.add(new SetCardInfo("Virulent Sliver", 246, Rarity.COMMON, mage.cards.v.VirulentSliver.class));
        cards.add(new SetCardInfo("Walk the Aeons", 98, Rarity.RARE, mage.cards.w.WalkTheAeons.class));
        cards.add(new SetCardInfo("Wastes", 410, Rarity.SPECIAL, mage.cards.w.Wastes.class));
        cards.add(new SetCardInfo("Watcher Sliver", 49, Rarity.COMMON, mage.cards.w.WatcherSliver.class));
        cards.add(new SetCardInfo("Wheel of Fate", 198, Rarity.MYTHIC, mage.cards.w.WheelOfFate.class));
        cards.add(new SetCardInfo("Whip-Spine Drake", 99, Rarity.UNCOMMON, mage.cards.w.WhipSpineDrake.class));
        cards.add(new SetCardInfo("Whitemane Lion", 50, Rarity.COMMON, mage.cards.w.WhitemaneLion.class));
        cards.add(new SetCardInfo("Wipe Away", 100, Rarity.UNCOMMON, mage.cards.w.WipeAway.class));
        cards.add(new SetCardInfo("Yavimaya Dryad", 247, Rarity.UNCOMMON, mage.cards.y.YavimayaDryad.class));
        cards.add(new SetCardInfo("Yawgmoth, Thran Physician", 336, Rarity.SPECIAL, mage.cards.y.YawgmothThranPhysician.class));
        cards.add(new SetCardInfo("Yixlid Jailer", 149, Rarity.UNCOMMON, mage.cards.y.YixlidJailer.class));
        cards.add(new SetCardInfo("Young Pyromancer", 353, Rarity.SPECIAL, mage.cards.y.YoungPyromancer.class));
        cards.add(new SetCardInfo("Zealous Conscripts", 354, Rarity.SPECIAL, mage.cards.z.ZealousConscripts.class));
        cards.add(new SetCardInfo("Zulaport Cutthroat", 337, Rarity.SPECIAL, mage.cards.z.ZulaportCutthroat.class));
    }

    @Override
    public List<CardInfo> getSpecialBonus() {
        if (savedSpecialBonus.isEmpty()) {
            savedSpecialBonus.addAll(CardRepository.instance.findCards(new CardCriteria().setCodes(this.code)));
            savedSpecialBonus.removeIf(cardInfo -> cardInfo.getCardNumberAsInt() > 410);
            savedSpecialBonus.removeIf(cardInfo -> cardInfo.getCardNumberAsInt() <= 289);
        }

        return new ArrayList<>(savedSpecialBonus);
    }

    @Override
    public BoosterCollator createCollator() {
        return new TimeSpiralRemasteredCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/tsr.html
// Using USA collation for common/uncommon, rare and bonus sheet are standard
class TimeSpiralRemasteredCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(true, "176", "65", "177", "94", "192", "70", "86", "191", "84", "168", "80", "69", "173", "73", "154", "87", "197", "92", "166", "67", "183", "78", "171", "81", "155", "95", "190", "70", "191", "58", "168", "63", "162", "89", "157", "65", "173", "69", "159", "73", "176", "94", "192", "86", "197", "84", "166", "67", "177", "92", "171", "80", "162", "81", "155", "78", "154", "87", "157", "95", "190", "58", "159", "63", "183", "89");
    private final CardRun commonB = new CardRun(true, "110", "245", "135", "241", "104", "226", "233", "108", "236", "107", "207", "229", "101", "201", "116", "199", "123", "246", "130", "240", "109", "245", "103", "202", "118", "221", "117", "230", "105", "212", "148", "243", "134", "237", "135", "226", "110", "236", "108", "233", "107", "241", "101", "229", "116", "201", "118", "199", "104", "246", "109", "240", "130", "207", "103", "202", "117", "221", "105", "230", "134", "243", "148", "212", "123", "237");
    private final CardRun commonC1 = new CardRun(true, "44", "223", "20", "140", "53", "21", "181", "17", "184", "2", "205", "151", "42", "119", "74", "1", "161", "272", "10", "228", "12", "68", "6", "285", "7", "48", "53", "20", "133", "44", "223", "21", "140", "151", "2", "184", "17", "74", "42", "181", "228", "1", "10", "119", "12", "68", "285", "48", "272", "6", "205", "161", "7", "133", "185");
    private final CardRun commonC2 = new CardRun(true, "25", "147", "31", "64", "269", "47", "206", "43", "178", "132", "263", "18", "200", "59", "26", "145", "9", "66", "50", "14", "147", "28", "232", "49", "122", "71", "22", "25", "165", "132", "31", "206", "43", "47", "269", "64", "18", "178", "200", "26", "263", "59", "9", "145", "14", "66", "50", "165", "28", "122", "232", "22", "49", "71", "185");
    private final CardRun uncommonA = new CardRun(true, "217", "283", "196", "242", "126", "194", "13", "222", "248", "40", "128", "195", "204", "5", "189", "113", "56", "249", "218", "180", "149", "276", "16", "258", "224", "79", "163", "142", "19", "61", "273", "153", "85", "30", "111", "170", "57", "231", "24", "99", "143", "279", "100", "174", "213", "55", "131", "46", "244", "126", "196", "82", "219", "102", "188", "208", "33", "120", "60", "186", "217", "283", "169", "124", "242", "194", "61", "218", "16", "153", "142", "258", "13", "224", "249", "113", "189", "79", "248", "204", "19", "149", "163", "5", "276", "222", "195", "40", "56", "180", "128", "273", "85", "30", "111", "170", "231", "57", "24", "143", "99", "279", "174", "213", "100", "131", "46", "244", "124", "55", "208", "188", "120", "60", "186", "33", "219", "169", "102", "82");
    private final CardRun uncommonB = new CardRun(true, "38", "152", "141", "29", "72", "225", "11", "158", "88", "271", "23", "76", "247", "160", "139", "250", "216", "288", "264", "45", "93", "252", "137", "275", "214", "39", "54", "129", "193", "227", "35", "112", "83", "282", "38", "164", "254", "115", "90", "37", "211", "152", "72", "141", "29", "247", "250", "158", "88", "11", "271", "225", "76", "139", "23", "160", "216", "288", "93", "45", "252", "137", "275", "214", "54", "264", "39", "227", "129", "193", "35", "282", "83", "112", "37", "254", "211", "38", "115", "90", "164", "29", "72", "152", "141", "225", "11", "88", "271", "158", "247", "23", "139", "76", "250", "160", "288", "252", "216", "93", "45", "137", "264", "214", "54", "275", "227", "129", "39", "193", "112", "35", "282", "83", "254", "211", "115", "37", "164", "90");
    private final CardRun rare = new CardRun(false, "3", "4", "8", "27", "32", "34", "41", "51", "62", "75", "77", "96", "97", "98", "114", "125", "127", "136", "138", "144", "146", "156", "167", "172", "175", "179", "182", "187", "203", "209", "215", "220", "234", "238", "239", "251", "253", "255", "256", "257", "259", "260", "265", "266", "268", "270", "274", "277", "278", "281", "284", "286", "287", "3", "4", "8", "27", "32", "34", "41", "51", "62", "75", "77", "96", "97", "98", "114", "125", "127", "136", "138", "144", "146", "156", "167", "172", "175", "179", "182", "187", "203", "209", "215", "220", "234", "238", "239", "251", "253", "255", "256", "257", "259", "260", "265", "266", "268", "270", "274", "277", "278", "281", "284", "286", "287", "15", "36", "52", "91", "106", "121", "150", "198", "210", "235", "261", "262", "267", "280", "289");
    private final CardRun special = new CardRun(false, "290", "291", "292", "293", "294", "295", "296", "297", "298", "299", "300", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310", "311", "312", "313", "314", "315", "316", "317", "318", "319", "320", "321", "322", "323", "324", "325", "326", "327", "328", "329", "330", "331", "332", "333", "334", "335", "336", "337", "338", "339", "340", "341", "342", "343", "344", "345", "346", "347", "348", "349", "350", "351", "352", "353", "354", "355", "356", "357", "358", "359", "360", "361", "362", "363", "364", "365", "366", "367", "368", "369", "370", "371", "372", "373", "374", "375", "376", "377", "378", "379", "380", "381", "382", "383", "384", "385", "386", "387", "388", "389", "390", "391", "392", "393", "394", "395", "396", "397", "398", "399", "400", "401", "402", "403", "404", "405", "406", "407", "408", "409", "410");

    private final BoosterStructure AAABBBC1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AABBBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAABBC2C2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AABBBC2C2C2C2C2 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAA = new BoosterStructure(uncommonA, uncommonA, uncommonA);
    private final BoosterStructure BBB = new BoosterStructure(uncommonB, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure S1 = new BoosterStructure(special);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.73 A commons (30 / 11)
    // 2.73 B commons (30 / 11)
    // 2.27 C1 commons (25 / 11, or 50 / 11 in each C1 booster)
    // 2.27 C2 commons (25 / 11, or 50 / 11 in each C2 booster)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAABBBC1C1C1C1,
            AAABBBC1C1C1C1,
            AAABBBC1C1C1C1,
            AAABBBC1C1C1C1,
            AAABBBC1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AABBBC1C1C1C1C1,
            AABBBC1C1C1C1C1,
            AABBBC1C1C1C1C1,

            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBC2C2C2C2C2,
            AAABBC2C2C2C2C2,
            AAABBC2C2C2C2C2,
            AABBBC2C2C2C2C2,
            AABBBC2C2C2C2C2,
            AABBBC2C2C2C2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAA, AAA, AAA, BBB, BBB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration specialRuns = new RarityConfiguration(S1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(specialRuns.getNext().makeRun());
        return booster;
    }
}
