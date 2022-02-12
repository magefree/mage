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
 * @author LevelX2
 */
public final class ModernMasters extends ExpansionSet {

    private static final ModernMasters instance = new ModernMasters();

    public static ModernMasters getInstance() {
        return instance;
    }

    private ModernMasters() {
        super("Modern Masters", "MMA", ExpansionSet.buildDate(2013, 6, 7), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Reprint";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Absorb Vis", 71, Rarity.COMMON, mage.cards.a.AbsorbVis.class));
        cards.add(new SetCardInfo("Academy Ruins", 219, Rarity.RARE, mage.cards.a.AcademyRuins.class));
        cards.add(new SetCardInfo("Adarkar Valkyrie", 1, Rarity.RARE, mage.cards.a.AdarkarValkyrie.class));
        cards.add(new SetCardInfo("Aethersnipe", 36, Rarity.COMMON, mage.cards.a.Aethersnipe.class));
        cards.add(new SetCardInfo("Aether Spellbomb", 196, Rarity.COMMON, mage.cards.a.AetherSpellbomb.class));
        cards.add(new SetCardInfo("Aether Vial", 197, Rarity.RARE, mage.cards.a.AetherVial.class));
        cards.add(new SetCardInfo("Amrou Scout", 2, Rarity.COMMON, mage.cards.a.AmrouScout.class));
        cards.add(new SetCardInfo("Amrou Seekers", 3, Rarity.COMMON, mage.cards.a.AmrouSeekers.class));
        cards.add(new SetCardInfo("Angel's Grace", 4, Rarity.RARE, mage.cards.a.AngelsGrace.class));
        cards.add(new SetCardInfo("Arcbound Ravager", 198, Rarity.RARE, mage.cards.a.ArcboundRavager.class));
        cards.add(new SetCardInfo("Arcbound Stinger", 199, Rarity.COMMON, mage.cards.a.ArcboundStinger.class));
        cards.add(new SetCardInfo("Arcbound Wanderer", 200, Rarity.COMMON, mage.cards.a.ArcboundWanderer.class));
        cards.add(new SetCardInfo("Arcbound Worker", 201, Rarity.COMMON, mage.cards.a.ArcboundWorker.class));
        cards.add(new SetCardInfo("Auntie's Snitch", 72, Rarity.UNCOMMON, mage.cards.a.AuntiesSnitch.class));
        cards.add(new SetCardInfo("Auriok Salvagers", 5, Rarity.RARE, mage.cards.a.AuriokSalvagers.class));
        cards.add(new SetCardInfo("Avian Changeling", 6, Rarity.COMMON, mage.cards.a.AvianChangeling.class));
        cards.add(new SetCardInfo("Blightspeaker", 73, Rarity.COMMON, mage.cards.b.Blightspeaker.class));
        cards.add(new SetCardInfo("Blinding Beam", 7, Rarity.COMMON, mage.cards.b.BlindingBeam.class));
        cards.add(new SetCardInfo("Blind-Spot Giant", 105, Rarity.COMMON, mage.cards.b.BlindSpotGiant.class));
        cards.add(new SetCardInfo("Blinkmoth Nexus", 220, Rarity.RARE, mage.cards.b.BlinkmothNexus.class));
        cards.add(new SetCardInfo("Blood Moon", 106, Rarity.RARE, mage.cards.b.BloodMoon.class));
        cards.add(new SetCardInfo("Bonesplitter", 202, Rarity.COMMON, mage.cards.b.Bonesplitter.class));
        cards.add(new SetCardInfo("Bound in Silence", 8, Rarity.COMMON, mage.cards.b.BoundInSilence.class));
        cards.add(new SetCardInfo("Bridge from Below", 74, Rarity.RARE, mage.cards.b.BridgeFromBelow.class));
        cards.add(new SetCardInfo("Brute Force", 107, Rarity.COMMON, mage.cards.b.BruteForce.class));
        cards.add(new SetCardInfo("Careful Consideration", 37, Rarity.UNCOMMON, mage.cards.c.CarefulConsideration.class));
        cards.add(new SetCardInfo("Cenn's Enlistment", 9, Rarity.COMMON, mage.cards.c.CennsEnlistment.class));
        cards.add(new SetCardInfo("Chalice of the Void", 203, Rarity.RARE, mage.cards.c.ChaliceOfTheVoid.class));
        cards.add(new SetCardInfo("Citanul Woodreaders", 140, Rarity.COMMON, mage.cards.c.CitanulWoodreaders.class));
        cards.add(new SetCardInfo("City of Brass", 221, Rarity.RARE, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Cloudgoat Ranger", 10, Rarity.UNCOMMON, mage.cards.c.CloudgoatRanger.class));
        cards.add(new SetCardInfo("Cold-Eyed Selkie", 186, Rarity.RARE, mage.cards.c.ColdEyedSelkie.class));
        cards.add(new SetCardInfo("Countryside Crusher", 108, Rarity.RARE, mage.cards.c.CountrysideCrusher.class));
        cards.add(new SetCardInfo("Court Homunculus", 11, Rarity.COMMON, mage.cards.c.CourtHomunculus.class));
        cards.add(new SetCardInfo("Crush Underfoot", 109, Rarity.COMMON, mage.cards.c.CrushUnderfoot.class));
        cards.add(new SetCardInfo("Cryptic Command", 38, Rarity.RARE, mage.cards.c.CrypticCommand.class));
        cards.add(new SetCardInfo("Dakmor Salvage", 222, Rarity.UNCOMMON, mage.cards.d.DakmorSalvage.class));
        cards.add(new SetCardInfo("Dampen Thought", 39, Rarity.COMMON, mage.cards.d.DampenThought.class));
        cards.add(new SetCardInfo("Dark Confidant", 75, Rarity.MYTHIC, mage.cards.d.DarkConfidant.class));
        cards.add(new SetCardInfo("Death Cloud", 76, Rarity.RARE, mage.cards.d.DeathCloud.class));
        cards.add(new SetCardInfo("Death Denied", 77, Rarity.COMMON, mage.cards.d.DeathDenied.class));
        cards.add(new SetCardInfo("Death Rattle", 78, Rarity.UNCOMMON, mage.cards.d.DeathRattle.class));
        cards.add(new SetCardInfo("Deepcavern Imp", 79, Rarity.COMMON, mage.cards.d.DeepcavernImp.class));
        cards.add(new SetCardInfo("Demigod of Revenge", 187, Rarity.RARE, mage.cards.d.DemigodOfRevenge.class));
        cards.add(new SetCardInfo("Desperate Ritual", 110, Rarity.UNCOMMON, mage.cards.d.DesperateRitual.class));
        cards.add(new SetCardInfo("Dispeller's Capsule", 12, Rarity.COMMON, mage.cards.d.DispellersCapsule.class));
        cards.add(new SetCardInfo("Divinity of Pride", 188, Rarity.RARE, mage.cards.d.DivinityOfPride.class));
        cards.add(new SetCardInfo("Doubling Season", 141, Rarity.RARE, mage.cards.d.DoublingSeason.class));
        cards.add(new SetCardInfo("Drag Down", 80, Rarity.COMMON, mage.cards.d.DragDown.class));
        cards.add(new SetCardInfo("Dragonstorm", 111, Rarity.RARE, mage.cards.d.Dragonstorm.class));
        cards.add(new SetCardInfo("Dreamspoiler Witches", 81, Rarity.COMMON, mage.cards.d.DreamspoilerWitches.class));
        cards.add(new SetCardInfo("Durkwood Baloth", 142, Rarity.COMMON, mage.cards.d.DurkwoodBaloth.class));
        cards.add(new SetCardInfo("Earwig Squad", 82, Rarity.RARE, mage.cards.e.EarwigSquad.class));
        cards.add(new SetCardInfo("Echoing Courage", 143, Rarity.COMMON, mage.cards.e.EchoingCourage.class));
        cards.add(new SetCardInfo("Echoing Truth", 40, Rarity.COMMON, mage.cards.e.EchoingTruth.class));
        cards.add(new SetCardInfo("Electrolyze", 175, Rarity.UNCOMMON, mage.cards.e.Electrolyze.class));
        cards.add(new SetCardInfo("Elspeth, Knight-Errant", 13, Rarity.MYTHIC, mage.cards.e.ElspethKnightErrant.class));
        cards.add(new SetCardInfo("Empty the Warrens", 112, Rarity.COMMON, mage.cards.e.EmptyTheWarrens.class));
        cards.add(new SetCardInfo("Engineered Explosives", 204, Rarity.RARE, mage.cards.e.EngineeredExplosives.class));
        cards.add(new SetCardInfo("Epochrasite", 205, Rarity.UNCOMMON, mage.cards.e.Epochrasite.class));
        cards.add(new SetCardInfo("Errant Ephemeron", 41, Rarity.COMMON, mage.cards.e.ErrantEphemeron.class));
        cards.add(new SetCardInfo("Erratic Mutation", 42, Rarity.COMMON, mage.cards.e.ErraticMutation.class));
        cards.add(new SetCardInfo("Esperzoa", 43, Rarity.UNCOMMON, mage.cards.e.Esperzoa.class));
        cards.add(new SetCardInfo("Etched Oracle", 206, Rarity.UNCOMMON, mage.cards.e.EtchedOracle.class));
        cards.add(new SetCardInfo("Eternal Witness", 144, Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Etherium Sculptor", 44, Rarity.COMMON, mage.cards.e.EtheriumSculptor.class));
        cards.add(new SetCardInfo("Ethersworn Canonist", 14, Rarity.RARE, mage.cards.e.EtherswornCanonist.class));
        cards.add(new SetCardInfo("Executioner's Capsule", 83, Rarity.UNCOMMON, mage.cards.e.ExecutionersCapsule.class));
        cards.add(new SetCardInfo("Extirpate", 84, Rarity.RARE, mage.cards.e.Extirpate.class));
        cards.add(new SetCardInfo("Facevaulter", 85, Rarity.COMMON, mage.cards.f.Facevaulter.class));
        cards.add(new SetCardInfo("Faerie Macabre", 86, Rarity.COMMON, mage.cards.f.FaerieMacabre.class));
        cards.add(new SetCardInfo("Faerie Mechanist", 45, Rarity.COMMON, mage.cards.f.FaerieMechanist.class));
        cards.add(new SetCardInfo("Festering Goblin", 87, Rarity.COMMON, mage.cards.f.FesteringGoblin.class));
        cards.add(new SetCardInfo("Feudkiller's Verdict", 15, Rarity.UNCOMMON, mage.cards.f.FeudkillersVerdict.class));
        cards.add(new SetCardInfo("Fiery Fall", 113, Rarity.COMMON, mage.cards.f.FieryFall.class));
        cards.add(new SetCardInfo("Figure of Destiny", 189, Rarity.RARE, mage.cards.f.FigureOfDestiny.class));
        cards.add(new SetCardInfo("Flickerwisp", 16, Rarity.UNCOMMON, mage.cards.f.Flickerwisp.class));
        cards.add(new SetCardInfo("Frogmite", 207, Rarity.COMMON, mage.cards.f.Frogmite.class));
        cards.add(new SetCardInfo("Fury Charm", 114, Rarity.COMMON, mage.cards.f.FuryCharm.class));
        cards.add(new SetCardInfo("Giant Dustwasp", 145, Rarity.COMMON, mage.cards.g.GiantDustwasp.class));
        cards.add(new SetCardInfo("Gifts Ungiven", 46, Rarity.RARE, mage.cards.g.GiftsUngiven.class));
        cards.add(new SetCardInfo("Glacial Ray", 115, Rarity.COMMON, mage.cards.g.GlacialRay.class));
        cards.add(new SetCardInfo("Gleam of Resistance", 17, Rarity.COMMON, mage.cards.g.GleamOfResistance.class));
        cards.add(new SetCardInfo("Glen Elendra Archmage", 47, Rarity.RARE, mage.cards.g.GlenElendraArchmage.class));
        cards.add(new SetCardInfo("Glimmervoid", 223, Rarity.RARE, mage.cards.g.Glimmervoid.class));
        cards.add(new SetCardInfo("Grand Arbiter Augustin IV", 176, Rarity.RARE, mage.cards.g.GrandArbiterAugustinIV.class));
        cards.add(new SetCardInfo("Grapeshot", 116, Rarity.COMMON, mage.cards.g.Grapeshot.class));
        cards.add(new SetCardInfo("Greater Gargadon", 117, Rarity.RARE, mage.cards.g.GreaterGargadon.class));
        cards.add(new SetCardInfo("Greater Mossdog", 146, Rarity.COMMON, mage.cards.g.GreaterMossdog.class));
        cards.add(new SetCardInfo("Grinning Ignus", 118, Rarity.UNCOMMON, mage.cards.g.GrinningIgnus.class));
        cards.add(new SetCardInfo("Hammerheim Deadeye", 119, Rarity.COMMON, mage.cards.h.HammerheimDeadeye.class));
        cards.add(new SetCardInfo("Hana Kami", 147, Rarity.COMMON, mage.cards.h.HanaKami.class));
        cards.add(new SetCardInfo("Hillcomber Giant", 18, Rarity.COMMON, mage.cards.h.HillcomberGiant.class));
        cards.add(new SetCardInfo("Horobi's Whisper", 88, Rarity.UNCOMMON, mage.cards.h.HorobisWhisper.class));
        cards.add(new SetCardInfo("Imperiosaur", 148, Rarity.COMMON, mage.cards.i.Imperiosaur.class));
        cards.add(new SetCardInfo("Incremental Growth", 149, Rarity.UNCOMMON, mage.cards.i.IncrementalGrowth.class));
        cards.add(new SetCardInfo("Ivory Giant", 19, Rarity.COMMON, mage.cards.i.IvoryGiant.class));
        cards.add(new SetCardInfo("Jhoira of the Ghitu", 177, Rarity.RARE, mage.cards.j.JhoiraOfTheGhitu.class));
        cards.add(new SetCardInfo("Jugan, the Rising Star", 150, Rarity.MYTHIC, mage.cards.j.JuganTheRisingStar.class));
        cards.add(new SetCardInfo("Kataki, War's Wage", 20, Rarity.RARE, mage.cards.k.KatakiWarsWage.class));
        cards.add(new SetCardInfo("Keiga, the Tide Star", 48, Rarity.MYTHIC, mage.cards.k.KeigaTheTideStar.class));
        cards.add(new SetCardInfo("Kiki-Jiki, Mirror Breaker", 120, Rarity.MYTHIC, mage.cards.k.KikiJikiMirrorBreaker.class));
        cards.add(new SetCardInfo("Kira, Great Glass-Spinner", 49, Rarity.RARE, mage.cards.k.KiraGreatGlassSpinner.class));
        cards.add(new SetCardInfo("Kitchen Finks", 190, Rarity.UNCOMMON, mage.cards.k.KitchenFinks.class));
        cards.add(new SetCardInfo("Kithkin Greatheart", 21, Rarity.COMMON, mage.cards.k.KithkinGreatheart.class));
        cards.add(new SetCardInfo("Knight of the Reliquary", 178, Rarity.RARE, mage.cards.k.KnightOfTheReliquary.class));
        cards.add(new SetCardInfo("Kodama's Reach", 151, Rarity.COMMON, mage.cards.k.KodamasReach.class));
        cards.add(new SetCardInfo("Kokusho, the Evening Star", 89, Rarity.MYTHIC, mage.cards.k.KokushoTheEveningStar.class));
        cards.add(new SetCardInfo("Krosan Grip", 152, Rarity.UNCOMMON, mage.cards.k.KrosanGrip.class));
        cards.add(new SetCardInfo("Latchkey Faerie", 50, Rarity.COMMON, mage.cards.l.LatchkeyFaerie.class));
        cards.add(new SetCardInfo("Lava Spike", 121, Rarity.COMMON, mage.cards.l.LavaSpike.class));
        cards.add(new SetCardInfo("Life from the Loam", 153, Rarity.RARE, mage.cards.l.LifeFromTheLoam.class));
        cards.add(new SetCardInfo("Lightning Helix", 179, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Logic Knot", 51, Rarity.COMMON, mage.cards.l.LogicKnot.class));
        cards.add(new SetCardInfo("Lotus Bloom", 208, Rarity.RARE, mage.cards.l.LotusBloom.class));
        cards.add(new SetCardInfo("Mad Auntie", 90, Rarity.UNCOMMON, mage.cards.m.MadAuntie.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 180, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Manamorphose", 191, Rarity.UNCOMMON, mage.cards.m.Manamorphose.class));
        cards.add(new SetCardInfo("Marsh Flitter", 91, Rarity.UNCOMMON, mage.cards.m.MarshFlitter.class));
        cards.add(new SetCardInfo("Masked Admirers", 154, Rarity.UNCOMMON, mage.cards.m.MaskedAdmirers.class));
        cards.add(new SetCardInfo("Meadowboon", 22, Rarity.UNCOMMON, mage.cards.m.Meadowboon.class));
        cards.add(new SetCardInfo("Meloku the Clouded Mirror", 52, Rarity.RARE, mage.cards.m.MelokuTheCloudedMirror.class));
        cards.add(new SetCardInfo("Mind Funeral", 181, Rarity.UNCOMMON, mage.cards.m.MindFuneral.class));
        cards.add(new SetCardInfo("Mogg War Marshal", 122, Rarity.COMMON, mage.cards.m.MoggWarMarshal.class));
        cards.add(new SetCardInfo("Moldervine Cloak", 155, Rarity.COMMON, mage.cards.m.MoldervineCloak.class));
        cards.add(new SetCardInfo("Molten Disaster", 123, Rarity.RARE, mage.cards.m.MoltenDisaster.class));
        cards.add(new SetCardInfo("Mothdust Changeling", 53, Rarity.COMMON, mage.cards.m.MothdustChangeling.class));
        cards.add(new SetCardInfo("Mulldrifter", 54, Rarity.UNCOMMON, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Murderous Redcap", 192, Rarity.UNCOMMON, mage.cards.m.MurderousRedcap.class));
        cards.add(new SetCardInfo("Myr Enforcer", 209, Rarity.COMMON, mage.cards.m.MyrEnforcer.class));
        cards.add(new SetCardInfo("Myr Retriever", 210, Rarity.UNCOMMON, mage.cards.m.MyrRetriever.class));
        cards.add(new SetCardInfo("Nantuko Shaman", 156, Rarity.COMMON, mage.cards.n.NantukoShaman.class));
        cards.add(new SetCardInfo("Narcomoeba", 55, Rarity.UNCOMMON, mage.cards.n.Narcomoeba.class));
        cards.add(new SetCardInfo("Oona, Queen of the Fae", 193, Rarity.RARE, mage.cards.o.OonaQueenOfTheFae.class));
        cards.add(new SetCardInfo("Otherworldly Journey", 23, Rarity.COMMON, mage.cards.o.OtherworldlyJourney.class));
        cards.add(new SetCardInfo("Pact of Negation", 56, Rarity.RARE, mage.cards.p.PactOfNegation.class));
        cards.add(new SetCardInfo("Pallid Mycoderm", 24, Rarity.COMMON, mage.cards.p.PallidMycoderm.class));
        cards.add(new SetCardInfo("Paradise Mantle", 211, Rarity.UNCOMMON, mage.cards.p.ParadiseMantle.class));
        cards.add(new SetCardInfo("Pardic Dragon", 124, Rarity.UNCOMMON, mage.cards.p.PardicDragon.class));
        cards.add(new SetCardInfo("Path to Exile", 25, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Peer Through Depths", 57, Rarity.COMMON, mage.cards.p.PeerThroughDepths.class));
        cards.add(new SetCardInfo("Penumbra Spider", 157, Rarity.COMMON, mage.cards.p.PenumbraSpider.class));
        cards.add(new SetCardInfo("Peppersmoke", 92, Rarity.COMMON, mage.cards.p.Peppersmoke.class));
        cards.add(new SetCardInfo("Perilous Research", 58, Rarity.COMMON, mage.cards.p.PerilousResearch.class));
        cards.add(new SetCardInfo("Pestermite", 59, Rarity.COMMON, mage.cards.p.Pestermite.class));
        cards.add(new SetCardInfo("Petals of Insight", 60, Rarity.COMMON, mage.cards.p.PetalsOfInsight.class));
        cards.add(new SetCardInfo("Phthisis", 93, Rarity.UNCOMMON, mage.cards.p.Phthisis.class));
        cards.add(new SetCardInfo("Plumeveil", 194, Rarity.UNCOMMON, mage.cards.p.Plumeveil.class));
        cards.add(new SetCardInfo("Progenitus", 182, Rarity.MYTHIC, mage.cards.p.Progenitus.class));
        cards.add(new SetCardInfo("Pyrite Spellbomb", 212, Rarity.COMMON, mage.cards.p.PyriteSpellbomb.class));
        cards.add(new SetCardInfo("Pyromancer's Swath", 125, Rarity.RARE, mage.cards.p.PyromancersSwath.class));
        cards.add(new SetCardInfo("Rathi Trapper", 94, Rarity.COMMON, mage.cards.r.RathiTrapper.class));
        cards.add(new SetCardInfo("Raven's Crime", 95, Rarity.COMMON, mage.cards.r.RavensCrime.class));
        cards.add(new SetCardInfo("Reach of Branches", 158, Rarity.UNCOMMON, mage.cards.r.ReachOfBranches.class));
        cards.add(new SetCardInfo("Reach Through Mists", 61, Rarity.COMMON, mage.cards.r.ReachThroughMists.class));
        cards.add(new SetCardInfo("Relic of Progenitus", 213, Rarity.UNCOMMON, mage.cards.r.RelicOfProgenitus.class));
        cards.add(new SetCardInfo("Reveillark", 26, Rarity.RARE, mage.cards.r.Reveillark.class));
        cards.add(new SetCardInfo("Rift Bolt", 126, Rarity.COMMON, mage.cards.r.RiftBolt.class));
        cards.add(new SetCardInfo("Rift Elemental", 127, Rarity.COMMON, mage.cards.r.RiftElemental.class));
        cards.add(new SetCardInfo("Riftsweeper", 159, Rarity.UNCOMMON, mage.cards.r.Riftsweeper.class));
        cards.add(new SetCardInfo("Riftwing Cloudskate", 62, Rarity.UNCOMMON, mage.cards.r.RiftwingCloudskate.class));
        cards.add(new SetCardInfo("Rude Awakening", 160, Rarity.RARE, mage.cards.r.RudeAwakening.class));
        cards.add(new SetCardInfo("Runed Stalactite", 214, Rarity.COMMON, mage.cards.r.RunedStalactite.class));
        cards.add(new SetCardInfo("Ryusei, the Falling Star", 128, Rarity.MYTHIC, mage.cards.r.RyuseiTheFallingStar.class));
        cards.add(new SetCardInfo("Saltfield Recluse", 27, Rarity.COMMON, mage.cards.s.SaltfieldRecluse.class));
        cards.add(new SetCardInfo("Sanctum Gargoyle", 28, Rarity.COMMON, mage.cards.s.SanctumGargoyle.class));
        cards.add(new SetCardInfo("Sandsower", 29, Rarity.UNCOMMON, mage.cards.s.Sandsower.class));
        cards.add(new SetCardInfo("Sarkhan Vol", 183, Rarity.MYTHIC, mage.cards.s.SarkhanVol.class));
        cards.add(new SetCardInfo("Scion of Oona", 63, Rarity.RARE, mage.cards.s.ScionOfOona.class));
        cards.add(new SetCardInfo("Search for Tomorrow", 161, Rarity.COMMON, mage.cards.s.SearchForTomorrow.class));
        cards.add(new SetCardInfo("Shrapnel Blast", 129, Rarity.UNCOMMON, mage.cards.s.ShrapnelBlast.class));
        cards.add(new SetCardInfo("Skeletal Vampire", 96, Rarity.RARE, mage.cards.s.SkeletalVampire.class));
        cards.add(new SetCardInfo("Skyreach Manta", 215, Rarity.COMMON, mage.cards.s.SkyreachManta.class));
        cards.add(new SetCardInfo("Slaughter Pact", 97, Rarity.RARE, mage.cards.s.SlaughterPact.class));
        cards.add(new SetCardInfo("Spell Snare", 64, Rarity.UNCOMMON, mage.cards.s.SpellSnare.class));
        cards.add(new SetCardInfo("Spellstutter Sprite", 65, Rarity.COMMON, mage.cards.s.SpellstutterSprite.class));
        cards.add(new SetCardInfo("Sporesower Thallid", 162, Rarity.UNCOMMON, mage.cards.s.SporesowerThallid.class));
        cards.add(new SetCardInfo("Sporoloth Ancient", 163, Rarity.COMMON, mage.cards.s.SporolothAncient.class));
        cards.add(new SetCardInfo("Squee, Goblin Nabob", 130, Rarity.RARE, mage.cards.s.SqueeGoblinNabob.class));
        cards.add(new SetCardInfo("Stingscourger", 131, Rarity.COMMON, mage.cards.s.Stingscourger.class));
        cards.add(new SetCardInfo("Stinkdrinker Daredevil", 132, Rarity.COMMON, mage.cards.s.StinkdrinkerDaredevil.class));
        cards.add(new SetCardInfo("Stinkweed Imp", 98, Rarity.COMMON, mage.cards.s.StinkweedImp.class));
        cards.add(new SetCardInfo("Stir the Pride", 30, Rarity.UNCOMMON, mage.cards.s.StirThePride.class));
        cards.add(new SetCardInfo("Stonehewer Giant", 31, Rarity.RARE, mage.cards.s.StonehewerGiant.class));
        cards.add(new SetCardInfo("Street Wraith", 99, Rarity.COMMON, mage.cards.s.StreetWraith.class));
        cards.add(new SetCardInfo("Sudden Shock", 133, Rarity.UNCOMMON, mage.cards.s.SuddenShock.class));
        cards.add(new SetCardInfo("Summoner's Pact", 164, Rarity.RARE, mage.cards.s.SummonersPact.class));
        cards.add(new SetCardInfo("Sword of Fire and Ice", 216, Rarity.MYTHIC, mage.cards.s.SwordOfFireAndIce.class));
        cards.add(new SetCardInfo("Sword of Light and Shadow", 217, Rarity.MYTHIC, mage.cards.s.SwordOfLightAndShadow.class));
        cards.add(new SetCardInfo("Sylvan Bounty", 165, Rarity.COMMON, mage.cards.s.SylvanBounty.class));
        cards.add(new SetCardInfo("Syphon Life", 100, Rarity.COMMON, mage.cards.s.SyphonLife.class));
        cards.add(new SetCardInfo("Take Possession", 66, Rarity.UNCOMMON, mage.cards.t.TakePossession.class));
        cards.add(new SetCardInfo("Tarmogoyf", 166, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Tar Pitcher", 134, Rarity.UNCOMMON, mage.cards.t.TarPitcher.class));
        cards.add(new SetCardInfo("Terashi's Grasp", 32, Rarity.UNCOMMON, mage.cards.t.TerashisGrasp.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 224, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Test of Faith", 33, Rarity.COMMON, mage.cards.t.TestOfFaith.class));
        cards.add(new SetCardInfo("Thallid", 167, Rarity.COMMON, mage.cards.t.Thallid.class));
        cards.add(new SetCardInfo("Thallid Germinator", 168, Rarity.COMMON, mage.cards.t.ThallidGerminator.class));
        cards.add(new SetCardInfo("Thallid Shell-Dweller", 169, Rarity.COMMON, mage.cards.t.ThallidShellDweller.class));
        cards.add(new SetCardInfo("Thieving Sprite", 101, Rarity.COMMON, mage.cards.t.ThievingSprite.class));
        cards.add(new SetCardInfo("Thirst for Knowledge", 67, Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Thundercloud Shaman", 135, Rarity.UNCOMMON, mage.cards.t.ThundercloudShaman.class));
        cards.add(new SetCardInfo("Thundering Giant", 136, Rarity.COMMON, mage.cards.t.ThunderingGiant.class));
        cards.add(new SetCardInfo("Tidehollow Sculler", 184, Rarity.UNCOMMON, mage.cards.t.TidehollowSculler.class));
        cards.add(new SetCardInfo("Tombstalker", 102, Rarity.RARE, mage.cards.t.Tombstalker.class));
        cards.add(new SetCardInfo("Tooth and Nail", 170, Rarity.RARE, mage.cards.t.ToothAndNail.class));
        cards.add(new SetCardInfo("Torrent of Stone", 137, Rarity.COMMON, mage.cards.t.TorrentOfStone.class));
        cards.add(new SetCardInfo("Traumatic Visions", 68, Rarity.COMMON, mage.cards.t.TraumaticVisions.class));
        cards.add(new SetCardInfo("Tribal Flames", 138, Rarity.UNCOMMON, mage.cards.t.TribalFlames.class));
        cards.add(new SetCardInfo("Tromp the Domains", 171, Rarity.UNCOMMON, mage.cards.t.TrompTheDomains.class));
        cards.add(new SetCardInfo("Trygon Predator", 185, Rarity.UNCOMMON, mage.cards.t.TrygonPredator.class));
        cards.add(new SetCardInfo("Vedalken Dismisser", 69, Rarity.COMMON, mage.cards.v.VedalkenDismisser.class));
        cards.add(new SetCardInfo("Vedalken Shackles", 218, Rarity.MYTHIC, mage.cards.v.VedalkenShackles.class));
        cards.add(new SetCardInfo("Vendilion Clique", 70, Rarity.MYTHIC, mage.cards.v.VendilionClique.class));
        cards.add(new SetCardInfo("Verdeloth the Ancient", 172, Rarity.RARE, mage.cards.v.VerdelothTheAncient.class));
        cards.add(new SetCardInfo("Veteran Armorer", 34, Rarity.COMMON, mage.cards.v.VeteranArmorer.class));
        cards.add(new SetCardInfo("Vivid Crag", 225, Rarity.UNCOMMON, mage.cards.v.VividCrag.class));
        cards.add(new SetCardInfo("Vivid Creek", 226, Rarity.UNCOMMON, mage.cards.v.VividCreek.class));
        cards.add(new SetCardInfo("Vivid Grove", 227, Rarity.UNCOMMON, mage.cards.v.VividGrove.class));
        cards.add(new SetCardInfo("Vivid Marsh", 228, Rarity.UNCOMMON, mage.cards.v.VividMarsh.class));
        cards.add(new SetCardInfo("Vivid Meadow", 229, Rarity.UNCOMMON, mage.cards.v.VividMeadow.class));
        cards.add(new SetCardInfo("Walker of the Grove", 173, Rarity.COMMON, mage.cards.w.WalkerOfTheGrove.class));
        cards.add(new SetCardInfo("Warren Pilferers", 103, Rarity.COMMON, mage.cards.w.WarrenPilferers.class));
        cards.add(new SetCardInfo("Warren Weirding", 104, Rarity.COMMON, mage.cards.w.WarrenWeirding.class));
        cards.add(new SetCardInfo("War-Spike Changeling", 139, Rarity.COMMON, mage.cards.w.WarSpikeChangeling.class));
        cards.add(new SetCardInfo("Woodfall Primus", 174, Rarity.RARE, mage.cards.w.WoodfallPrimus.class));
        cards.add(new SetCardInfo("Worm Harvest", 195, Rarity.UNCOMMON, mage.cards.w.WormHarvest.class));
        cards.add(new SetCardInfo("Yosei, the Morning Star", 35, Rarity.MYTHIC, mage.cards.y.YoseiTheMorningStar.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ModernMastersCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/mma.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class ModernMastersCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "156", "6", "207", "69", "215", "81", "61", "148", "3", "200", "112", "157", "36", "79", "11", "21", "107", "41", "146", "86", "207", "2", "50", "119", "156", "101", "34", "139", "41", "145", "79", "3", "121", "98", "69", "157", "21", "112", "101", "146", "50", "86", "34", "119", "145", "136", "61", "103", "6", "215", "168", "81", "39", "200", "11", "107", "148", "98", "36", "121", "2", "168", "136", "39", "103", "139");
    private final CardRun commonB = new CardRun(true, "73", "51", "113", "140", "18", "94", "68", "212", "169", "87", "113", "42", "140", "7", "224", "126", "73", "19", "167", "60", "169", "116", "85", "224", "147", "126", "18", "60", "94", "137", "212", "12", "51", "116", "85", "18", "68", "140", "137", "73", "42", "167", "7", "87", "51", "126", "19", "224", "42", "94", "113", "169", "7", "212", "87", "147", "60", "12", "137", "167", "85", "19", "68", "116", "147", "12");
    private final CardRun commonC1 = new CardRun(true, "77", "9", "122", "44", "151", "127", "161", "23", "71", "53", "105", "214", "143", "8", "104", "199", "131", "45", "17", "80", "59", "161", "132", "71", "100", "33", "44", "155", "201", "127", "17", "77", "65", "105", "165", "53", "9", "80", "131", "201", "151", "45", "104", "23", "122", "165", "100", "65", "33", "143", "132", "8", "199", "59", "155");
    private final CardRun commonC2 = new CardRun(true, "202", "163", "40", "58", "114", "57", "196", "173", "109", "95", "209", "163", "58", "115", "196", "92", "142", "40", "114", "95", "163", "27", "214", "99", "109", "24", "142", "202", "115", "57", "28", "173", "58", "27", "99", "209", "114", "92", "28", "202", "109", "196", "27", "57", "95", "173", "24", "92", "115", "40", "28", "209", "142", "99", "24");
    private final CardRun uncommonA = new CardRun(true, "66", "191", "211", "227", "144", "90", "192", "29", "64", "133", "228", "91", "184", "159", "10", "67", "227", "190", "162", "211", "206", "133", "181", "225", "159", "110", "66", "22", "191", "184", "154", "226", "37", "72", "194", "129", "144", "213", "138", "192", "206", "64", "16", "225", "179", "90", "162", "110", "10", "190", "213", "37", "194", "91", "138", "29", "226", "181", "67", "16", "154", "72", "179", "228", "22", "129");
    private final CardRun uncommonB = new CardRun(true, "83", "229", "55", "124", "25", "222", "78", "195", "62", "171", "32", "93", "210", "124", "54", "15", "222", "149", "88", "118", "55", "30", "185", "152", "62", "134", "205", "25", "158", "83", "43", "135", "175", "15", "152", "88", "54", "134", "185", "78", "171", "229", "32", "118", "195", "93", "158", "210", "43", "175", "205", "135", "30", "149");
    private final CardRun rare = new CardRun(true, "26", "96", "117", "178", "218", "141", "150", "111", "219", "197", "89", "204", "47", "70", "178", "198", "84", "13", "108", "75", "123", "177", "172", "4", "164", "120", "74", "31", "46", "186", "153", "97", "26", "197", "187", "76", "170", "160", "221", "188", "5", "56", "102", "38", "1", "193", "203", "97", "4", "141", "180", "125", "84", "20", "49", "189", "52", "123", "76", "172", "5", "106", "56", "31", "174", "47", "208", "220", "177", "38", "223", "49", "108", "117", "102", "82", "176", "48", "128", "96", "187", "63", "223", "182", "111", "188", "153", "203", "204", "130", "216", "189", "14", "1", "52", "166", "46", "217", "174", "183", "198", "74", "63", "125", "219", "160", "180", "130", "176", "20", "193", "220", "164", "106", "208", "170", "14", "221", "82", "35", "186");
    private final CardRun foilCommon = new CardRun(true, "201", "79", "132", "8", "168", "41", "80", "12", "139", "103", "50", "215", "24", "148", "92", "142", "196", "59", "114", "212", "104", "146", "169", "28", "81", "40", "21", "207", "131", "101", "58", "19", "157", "73", "119", "140", "200", "57", "27", "199", "112", "151", "77", "224", "107", "33", "61", "94", "202", "121", "143", "87", "23", "60", "98", "214", "65", "173", "3", "105", "39", "209", "7", "36", "115", "167", "99", "69", "6", "116", "163", "95", "145", "9", "53", "136", "155", "113", "161", "68", "34", "100", "156", "45", "18", "122", "71", "51", "126", "165", "2", "85", "127", "147", "42", "17", "137", "44", "109", "86", "11");
    private final CardRun foilUncommon = new CardRun(true, "144", "206", "190", "124", "158", "67", "22", "83", "227", "25", "175", "210", "229", "78", "211", "37", "10", "162", "213", "134", "90", "29", "185", "171", "88", "64", "15", "228", "30", "159", "37", "191", "124", "195", "67", "91", "25", "78", "134", "162", "62", "149", "229", "22", "64", "138", "110", "175", "192", "90", "152", "91", "135", "154", "129", "15", "55", "30", "184", "54", "225", "133", "152", "228", "110", "83", "149", "93", "226", "154", "210", "181", "138", "54", "158", "184", "225", "72", "66", "227", "16", "205", "118", "62", "194", "179", "10", "211", "185", "222", "43", "206", "192", "144", "29", "195", "88", "205", "179", "191", "32", "129", "213", "226", "55", "222", "133", "190", "72", "135", "93", "32", "43", "171", "194", "181", "66", "118", "159", "16");

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
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure FC = new BoosterStructure(foilCommon);
    private final BoosterStructure FU = new BoosterStructure(foilUncommon);

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
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
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
    private final RarityConfiguration foilRuns = new RarityConfiguration(
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FU, FU, FU,
            R1
    );

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(foilRuns.getNext().makeRun());
        return booster;
    }
}
