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
 * @author TheElk801
 */
public final class DoubleMasters extends ExpansionSet {

    private static final DoubleMasters instance = new DoubleMasters();

    public static DoubleMasters getInstance() {
        return instance;
    }

    private DoubleMasters() {
        super("Double Masters", "2XM", ExpansionSet.buildDate(2020, 8, 7), SetType.SUPPLEMENTAL);
        this.blockName = "Reprint";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 2;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Abrade", 114, Rarity.COMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Academy Ruins", 309, Rarity.RARE, mage.cards.a.AcademyRuins.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Academy Ruins", 369, Rarity.RARE, mage.cards.a.AcademyRuins.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Accomplished Automaton", 230, Rarity.COMMON, mage.cards.a.AccomplishedAutomaton.class));
        cards.add(new SetCardInfo("Ad Nauseam", 76, Rarity.RARE, mage.cards.a.AdNauseam.class));
        cards.add(new SetCardInfo("Adaptive Automaton", 231, Rarity.RARE, mage.cards.a.AdaptiveAutomaton.class));
        cards.add(new SetCardInfo("Alabaster Mage", 2, Rarity.COMMON, mage.cards.a.AlabasterMage.class));
        cards.add(new SetCardInfo("Ancestral Blade", 3, Rarity.COMMON, mage.cards.a.AncestralBlade.class));
        cards.add(new SetCardInfo("Ancient Stirrings", 151, Rarity.COMMON, mage.cards.a.AncientStirrings.class));
        cards.add(new SetCardInfo("Angel of the Dawn", 4, Rarity.COMMON, mage.cards.a.AngelOfTheDawn.class));
        cards.add(new SetCardInfo("Apprentice Wizard", 40, Rarity.COMMON, mage.cards.a.ApprenticeWizard.class));
        cards.add(new SetCardInfo("Archangel of Thune", 5, Rarity.MYTHIC, mage.cards.a.ArchangelOfThune.class));
        cards.add(new SetCardInfo("Arcum Dagsson", 41, Rarity.MYTHIC, mage.cards.a.ArcumDagsson.class));
        cards.add(new SetCardInfo("Argivian Restoration", 42, Rarity.COMMON, mage.cards.a.ArgivianRestoration.class));
        cards.add(new SetCardInfo("Arixmethes, Slumbering Isle", 189, Rarity.RARE, mage.cards.a.ArixmethesSlumberingIsle.class));
        cards.add(new SetCardInfo("Ash Barrens", 310, Rarity.UNCOMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Atraxa, Praetors' Voice", 190, Rarity.MYTHIC, mage.cards.a.AtraxaPraetorsVoice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Atraxa, Praetors' Voice", 353, Rarity.MYTHIC, mage.cards.a.AtraxaPraetorsVoice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Auriok Salvagers", 6, Rarity.UNCOMMON, mage.cards.a.AuriokSalvagers.class));
        cards.add(new SetCardInfo("Austere Command", 7, Rarity.RARE, mage.cards.a.AustereCommand.class));
        cards.add(new SetCardInfo("Avacyn, Angel of Hope", 335, Rarity.MYTHIC, mage.cards.a.AvacynAngelOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avacyn, Angel of Hope", 8, Rarity.MYTHIC, mage.cards.a.AvacynAngelOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avenger of Zendikar", 152, Rarity.MYTHIC, mage.cards.a.AvengerOfZendikar.class));
        cards.add(new SetCardInfo("Awakening Zone", 153, Rarity.RARE, mage.cards.a.AwakeningZone.class));
        cards.add(new SetCardInfo("Balduvian Rage", 115, Rarity.COMMON, mage.cards.b.BalduvianRage.class));
        cards.add(new SetCardInfo("Baleful Strix", 191, Rarity.RARE, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Basalt Monolith", 232, Rarity.UNCOMMON, mage.cards.b.BasaltMonolith.class));
        cards.add(new SetCardInfo("Basilisk Collar", 233, Rarity.RARE, mage.cards.b.BasiliskCollar.class));
        cards.add(new SetCardInfo("Batterskull", 234, Rarity.MYTHIC, mage.cards.b.Batterskull.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Batterskull", 356, Rarity.MYTHIC, mage.cards.b.Batterskull.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Battle-Rattle Shaman", 116, Rarity.COMMON, mage.cards.b.BattleRattleShaman.class));
        cards.add(new SetCardInfo("Beacon of Unrest", 77, Rarity.RARE, mage.cards.b.BeaconOfUnrest.class));
        cards.add(new SetCardInfo("Blade Splicer", 9, Rarity.RARE, mage.cards.b.BladeSplicer.class));
        cards.add(new SetCardInfo("Blasphemous Act", 117, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Blightsteel Colossus", 235, Rarity.MYTHIC, mage.cards.b.BlightsteelColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blightsteel Colossus", 357, Rarity.MYTHIC, mage.cards.b.BlightsteelColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blinkmoth Nexus", 311, Rarity.RARE, mage.cards.b.BlinkmothNexus.class));
        cards.add(new SetCardInfo("Blood Moon", 118, Rarity.RARE, mage.cards.b.BloodMoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Moon", 346, Rarity.RARE, mage.cards.b.BloodMoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodbriar", 154, Rarity.COMMON, mage.cards.b.Bloodbriar.class));
        cards.add(new SetCardInfo("Bloodshot Trainee", 119, Rarity.UNCOMMON, mage.cards.b.BloodshotTrainee.class));
        cards.add(new SetCardInfo("Bloodspore Thrinax", 155, Rarity.RARE, mage.cards.b.BloodsporeThrinax.class));
        cards.add(new SetCardInfo("Bone Picker", 78, Rarity.COMMON, mage.cards.b.BonePicker.class));
        cards.add(new SetCardInfo("Boon Reflection", 10, Rarity.RARE, mage.cards.b.BoonReflection.class));
        cards.add(new SetCardInfo("Bosh, Iron Golem", 236, Rarity.RARE, mage.cards.b.BoshIronGolem.class));
        cards.add(new SetCardInfo("Braids, Conjurer Adept", 43, Rarity.RARE, mage.cards.b.BraidsConjurerAdept.class));
        cards.add(new SetCardInfo("Brainstorm", 338, Rarity.RARE, mage.cards.b.Brainstorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brainstorm", 44, Rarity.COMMON, mage.cards.b.Brainstorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breya, Etherium Shaper", 192, Rarity.MYTHIC, mage.cards.b.BreyaEtheriumShaper.class));
        cards.add(new SetCardInfo("Brimstone Volley", 120, Rarity.UNCOMMON, mage.cards.b.BrimstoneVolley.class));
        cards.add(new SetCardInfo("Brudiclad, Telchor Engineer", 193, Rarity.RARE, mage.cards.b.BrudicladTelchorEngineer.class));
        cards.add(new SetCardInfo("Buried Ruin", 312, Rarity.UNCOMMON, mage.cards.b.BuriedRuin.class));
        cards.add(new SetCardInfo("Cascade Bluffs", 313, Rarity.RARE, mage.cards.c.CascadeBluffs.class));
        cards.add(new SetCardInfo("Cast Down", 79, Rarity.COMMON, mage.cards.c.CastDown.class));
        cards.add(new SetCardInfo("Cathartic Reunion", 121, Rarity.COMMON, mage.cards.c.CatharticReunion.class));
        cards.add(new SetCardInfo("Cathodion", 237, Rarity.COMMON, mage.cards.c.Cathodion.class));
        cards.add(new SetCardInfo("Champion of Lambholt", 156, Rarity.RARE, mage.cards.c.ChampionOfLambholt.class));
        cards.add(new SetCardInfo("Chatter of the Squirrel", 157, Rarity.COMMON, mage.cards.c.ChatterOfTheSquirrel.class));
        cards.add(new SetCardInfo("Chief of the Foundry", 238, Rarity.UNCOMMON, mage.cards.c.ChiefOfTheFoundry.class));
        cards.add(new SetCardInfo("Chord of Calling", 158, Rarity.RARE, mage.cards.c.ChordOfCalling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chord of Calling", 384, Rarity.RARE, mage.cards.c.ChordOfCalling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chromatic Star", 239, Rarity.COMMON, mage.cards.c.ChromaticStar.class));
        cards.add(new SetCardInfo("Chrome Mox", 240, Rarity.MYTHIC, mage.cards.c.ChromeMox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chrome Mox", 358, Rarity.MYTHIC, mage.cards.c.ChromeMox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clear Shot", 159, Rarity.COMMON, mage.cards.c.ClearShot.class));
        cards.add(new SetCardInfo("Clone Shell", 241, Rarity.UNCOMMON, mage.cards.c.CloneShell.class));
        cards.add(new SetCardInfo("Cloudreader Sphinx", 45, Rarity.COMMON, mage.cards.c.CloudreaderSphinx.class));
        cards.add(new SetCardInfo("Cogwork Assembler", 242, Rarity.UNCOMMON, mage.cards.c.CogworkAssembler.class));
        cards.add(new SetCardInfo("Conclave Naturalists", 160, Rarity.COMMON, mage.cards.c.ConclaveNaturalists.class));
        cards.add(new SetCardInfo("Conjurer's Closet", 243, Rarity.RARE, mage.cards.c.ConjurersCloset.class));
        cards.add(new SetCardInfo("Coretapper", 244, Rarity.UNCOMMON, mage.cards.c.Coretapper.class));
        cards.add(new SetCardInfo("Corridor Monitor", 46, Rarity.COMMON, mage.cards.c.CorridorMonitor.class));
        cards.add(new SetCardInfo("Costly Plunder", 80, Rarity.COMMON, mage.cards.c.CostlyPlunder.class));
        cards.add(new SetCardInfo("Council's Judgment", 11, Rarity.RARE, mage.cards.c.CouncilsJudgment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Council's Judgment", 336, Rarity.RARE, mage.cards.c.CouncilsJudgment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cragganwick Cremator", 122, Rarity.RARE, mage.cards.c.CragganwickCremator.class));
        cards.add(new SetCardInfo("Cranial Plating", 245, Rarity.UNCOMMON, mage.cards.c.CranialPlating.class));
        cards.add(new SetCardInfo("Crib Swap", 12, Rarity.COMMON, mage.cards.c.CribSwap.class));
        cards.add(new SetCardInfo("Crop Rotation", 161, Rarity.UNCOMMON, mage.cards.c.CropRotation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crop Rotation", 349, Rarity.RARE, mage.cards.c.CropRotation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crusader of Odric", 13, Rarity.COMMON, mage.cards.c.CrusaderOfOdric.class));
        cards.add(new SetCardInfo("Crushing Vines", 162, Rarity.COMMON, mage.cards.c.CrushingVines.class));
        cards.add(new SetCardInfo("Culling Dais", 246, Rarity.UNCOMMON, mage.cards.c.CullingDais.class));
        cards.add(new SetCardInfo("Cyclonic Rift", 339, Rarity.RARE, mage.cards.c.CyclonicRift.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cyclonic Rift", 47, Rarity.RARE, mage.cards.c.CyclonicRift.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Confidant", 342, Rarity.MYTHIC, mage.cards.d.DarkConfidant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Confidant", 81, Rarity.MYTHIC, mage.cards.d.DarkConfidant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Depths", 314, Rarity.MYTHIC, mage.cards.d.DarkDepths.class));
        cards.add(new SetCardInfo("Darksteel Axe", 247, Rarity.COMMON, mage.cards.d.DarksteelAxe.class));
        cards.add(new SetCardInfo("Darksteel Citadel", 315, Rarity.UNCOMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Darksteel Forge", 248, Rarity.MYTHIC, mage.cards.d.DarksteelForge.class));
        cards.add(new SetCardInfo("Death's Shadow", 82, Rarity.RARE, mage.cards.d.DeathsShadow.class));
        cards.add(new SetCardInfo("Death-Hood Cobra", 163, Rarity.COMMON, mage.cards.d.DeathHoodCobra.class));
        cards.add(new SetCardInfo("Deathreap Ritual", 194, Rarity.UNCOMMON, mage.cards.d.DeathreapRitual.class));
        cards.add(new SetCardInfo("Deepglow Skate", 48, Rarity.RARE, mage.cards.d.DeepglowSkate.class));
        cards.add(new SetCardInfo("Defiant Salvager", 83, Rarity.COMMON, mage.cards.d.DefiantSalvager.class));
        cards.add(new SetCardInfo("Dire Fleet Hoarder", 84, Rarity.COMMON, mage.cards.d.DireFleetHoarder.class));
        cards.add(new SetCardInfo("Disciple of Bolas", 85, Rarity.RARE, mage.cards.d.DiscipleOfBolas.class));
        cards.add(new SetCardInfo("Disciple of the Vault", 86, Rarity.UNCOMMON, mage.cards.d.DiscipleOfTheVault.class));
        cards.add(new SetCardInfo("Dismantle", 123, Rarity.UNCOMMON, mage.cards.d.Dismantle.class));
        cards.add(new SetCardInfo("Divest", 87, Rarity.COMMON, mage.cards.d.Divest.class));
        cards.add(new SetCardInfo("Doomed Necromancer", 88, Rarity.RARE, mage.cards.d.DoomedNecromancer.class));
        cards.add(new SetCardInfo("Doubling Season", 164, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doubling Season", 350, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dread Return", 89, Rarity.UNCOMMON, mage.cards.d.DreadReturn.class));
        cards.add(new SetCardInfo("Driver of the Dead", 90, Rarity.COMMON, mage.cards.d.DriverOfTheDead.class));
        cards.add(new SetCardInfo("Drown in Sorrow", 91, Rarity.UNCOMMON, mage.cards.d.DrownInSorrow.class));
        cards.add(new SetCardInfo("Dualcaster Mage", 124, Rarity.RARE, mage.cards.d.DualcasterMage.class));
        cards.add(new SetCardInfo("Duplicant", 249, Rarity.RARE, mage.cards.d.Duplicant.class));
        cards.add(new SetCardInfo("Eager Construct", 250, Rarity.COMMON, mage.cards.e.EagerConstruct.class));
        cards.add(new SetCardInfo("Elvish Aberration", 165, Rarity.COMMON, mage.cards.e.ElvishAberration.class));
        cards.add(new SetCardInfo("Endless Atlas", 251, Rarity.RARE, mage.cards.e.EndlessAtlas.class));
        cards.add(new SetCardInfo("Engineered Explosives", 252, Rarity.RARE, mage.cards.e.EngineeredExplosives.class));
        cards.add(new SetCardInfo("Enlarge", 166, Rarity.UNCOMMON, mage.cards.e.Enlarge.class));
        cards.add(new SetCardInfo("Ensnaring Bridge", 253, Rarity.MYTHIC, mage.cards.e.EnsnaringBridge.class));
        cards.add(new SetCardInfo("Esperzoa", 49, Rarity.UNCOMMON, mage.cards.e.Esperzoa.class));
        cards.add(new SetCardInfo("Ethersworn Canonist", 14, Rarity.RARE, mage.cards.e.EtherswornCanonist.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 254, Rarity.COMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Executioner's Capsule", 92, Rarity.COMMON, mage.cards.e.ExecutionersCapsule.class));
        cards.add(new SetCardInfo("Expedition Map", 255, Rarity.COMMON, mage.cards.e.ExpeditionMap.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Expedition Map", 359, Rarity.RARE, mage.cards.e.ExpeditionMap.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exploration", 167, Rarity.RARE, mage.cards.e.Exploration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exploration", 351, Rarity.RARE, mage.cards.e.Exploration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faerie Mechanist", 50, Rarity.COMMON, mage.cards.f.FaerieMechanist.class));
        cards.add(new SetCardInfo("Falkenrath Aristocrat", 195, Rarity.RARE, mage.cards.f.FalkenrathAristocrat.class));
        cards.add(new SetCardInfo("Fatal Push", 343, Rarity.RARE, mage.cards.f.FatalPush.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fatal Push", 93, Rarity.UNCOMMON, mage.cards.f.FatalPush.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fencing Ace", 15, Rarity.UNCOMMON, mage.cards.f.FencingAce.class));
        cards.add(new SetCardInfo("Fetid Heath", 316, Rarity.RARE, mage.cards.f.FetidHeath.class));
        cards.add(new SetCardInfo("Fierce Empath", 168, Rarity.COMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Fire-Lit Thicket", 317, Rarity.RARE, mage.cards.f.FireLitThicket.class));
        cards.add(new SetCardInfo("Flayer Husk", 256, Rarity.COMMON, mage.cards.f.FlayerHusk.class));
        cards.add(new SetCardInfo("Flickerwisp", 16, Rarity.UNCOMMON, mage.cards.f.Flickerwisp.class));
        cards.add(new SetCardInfo("Flooded Grove", 318, Rarity.RARE, mage.cards.f.FloodedGrove.class));
        cards.add(new SetCardInfo("Force of Will", 340, Rarity.MYTHIC, mage.cards.f.ForceOfWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Force of Will", 51, Rarity.MYTHIC, mage.cards.f.ForceOfWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 381, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 382, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Fortify", 17, Rarity.COMMON, mage.cards.f.Fortify.class));
        cards.add(new SetCardInfo("Frogify", 52, Rarity.COMMON, mage.cards.f.Frogify.class));
        cards.add(new SetCardInfo("Fulminator Mage", 196, Rarity.RARE, mage.cards.f.FulminatorMage.class));
        cards.add(new SetCardInfo("Galvanic Blast", 125, Rarity.UNCOMMON, mage.cards.g.GalvanicBlast.class));
        cards.add(new SetCardInfo("Geist of Saint Traft", 197, Rarity.MYTHIC, mage.cards.g.GeistOfSaintTraft.class));
        cards.add(new SetCardInfo("Gelatinous Genesis", 169, Rarity.UNCOMMON, mage.cards.g.GelatinousGenesis.class));
        cards.add(new SetCardInfo("Geth, Lord of the Vault", 94, Rarity.MYTHIC, mage.cards.g.GethLordOfTheVault.class));
        cards.add(new SetCardInfo("Ghor-Clan Rampager", 198, Rarity.UNCOMMON, mage.cards.g.GhorClanRampager.class));
        cards.add(new SetCardInfo("Glassdust Hulk", 199, Rarity.UNCOMMON, mage.cards.g.GlassdustHulk.class));
        cards.add(new SetCardInfo("Glaze Fiend", 95, Rarity.COMMON, mage.cards.g.GlazeFiend.class));
        cards.add(new SetCardInfo("Gleaming Barrier", 257, Rarity.COMMON, mage.cards.g.GleamingBarrier.class));
        cards.add(new SetCardInfo("Glimmervoid", 319, Rarity.RARE, mage.cards.g.Glimmervoid.class));
        cards.add(new SetCardInfo("Glint-Sleeve Artisan", 18, Rarity.COMMON, mage.cards.g.GlintSleeveArtisan.class));
        cards.add(new SetCardInfo("Goblin Gaveleer", 126, Rarity.COMMON, mage.cards.g.GoblinGaveleer.class));
        cards.add(new SetCardInfo("Goblin Guide", 127, Rarity.RARE, mage.cards.g.GoblinGuide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Guide", 347, Rarity.RARE, mage.cards.g.GoblinGuide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godo, Bandit Warlord", 128, Rarity.RARE, mage.cards.g.GodoBanditWarlord.class));
        cards.add(new SetCardInfo("Golem Artisan", 258, Rarity.UNCOMMON, mage.cards.g.GolemArtisan.class));
        cards.add(new SetCardInfo("Golem-Skin Gauntlets", 259, Rarity.COMMON, mage.cards.g.GolemSkinGauntlets.class));
        cards.add(new SetCardInfo("Grand Architect", 53, Rarity.RARE, mage.cards.g.GrandArchitect.class));
        cards.add(new SetCardInfo("Graven Cairns", 320, Rarity.RARE, mage.cards.g.GravenCairns.class));
        cards.add(new SetCardInfo("Greater Good", 170, Rarity.RARE, mage.cards.g.GreaterGood.class));
        cards.add(new SetCardInfo("Grim Lavamancer", 129, Rarity.RARE, mage.cards.g.GrimLavamancer.class));
        cards.add(new SetCardInfo("Hammer of Nazahn", 260, Rarity.RARE, mage.cards.h.HammerOfNazahn.class));
        cards.add(new SetCardInfo("Hanna, Ship's Navigator", 200, Rarity.RARE, mage.cards.h.HannaShipsNavigator.class));
        cards.add(new SetCardInfo("Heartbeat of Spring", 171, Rarity.RARE, mage.cards.h.HeartbeatOfSpring.class));
        cards.add(new SetCardInfo("Heartless Pillage", 96, Rarity.COMMON, mage.cards.h.HeartlessPillage.class));
        cards.add(new SetCardInfo("Heat Shimmer", 130, Rarity.RARE, mage.cards.h.HeatShimmer.class));
        cards.add(new SetCardInfo("Hidden Stockpile", 201, Rarity.UNCOMMON, mage.cards.h.HiddenStockpile.class));
        cards.add(new SetCardInfo("High Market", 321, Rarity.RARE, mage.cards.h.HighMarket.class));
        cards.add(new SetCardInfo("Hinder", 54, Rarity.UNCOMMON, mage.cards.h.Hinder.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 261, Rarity.COMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("Imperial Recruiter", 131, Rarity.MYTHIC, mage.cards.i.ImperialRecruiter.class));
        cards.add(new SetCardInfo("Inkwell Leviathan", 55, Rarity.RARE, mage.cards.i.InkwellLeviathan.class));
        cards.add(new SetCardInfo("Invigorate", 172, Rarity.UNCOMMON, mage.cards.i.Invigorate.class));
        cards.add(new SetCardInfo("Ion Storm", 132, Rarity.RARE, mage.cards.i.IonStorm.class));
        cards.add(new SetCardInfo("Iron Bully", 262, Rarity.COMMON, mage.cards.i.IronBully.class));
        cards.add(new SetCardInfo("Iron League Steed", 263, Rarity.COMMON, mage.cards.i.IronLeagueSteed.class));
        cards.add(new SetCardInfo("Island", 375, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 376, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Isochron Scepter", 264, Rarity.RARE, mage.cards.i.IsochronScepter.class));
        cards.add(new SetCardInfo("Izzet Charm", 202, Rarity.UNCOMMON, mage.cards.i.IzzetCharm.class));
        cards.add(new SetCardInfo("Jace, the Mind Sculptor", 334, Rarity.MYTHIC, mage.cards.j.JaceTheMindSculptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace, the Mind Sculptor", 56, Rarity.MYTHIC, mage.cards.j.JaceTheMindSculptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jhoira's Familiar", 265, Rarity.UNCOMMON, mage.cards.j.JhoirasFamiliar.class));
        cards.add(new SetCardInfo("Jhoira, Weatherlight Captain", 203, Rarity.RARE, mage.cards.j.JhoiraWeatherlightCaptain.class));
        cards.add(new SetCardInfo("Kaalia of the Vast", 204, Rarity.MYTHIC, mage.cards.k.KaaliaOfTheVast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaalia of the Vast", 354, Rarity.MYTHIC, mage.cards.k.KaaliaOfTheVast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karn Liberated", 1, Rarity.MYTHIC, mage.cards.k.KarnLiberated.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karn Liberated", 333, Rarity.MYTHIC, mage.cards.k.KarnLiberated.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karrthus, Tyrant of Jund", 205, Rarity.MYTHIC, mage.cards.k.KarrthusTyrantOfJund.class));
        cards.add(new SetCardInfo("Kazuul's Toll Collector", 133, Rarity.COMMON, mage.cards.k.KazuulsTollCollector.class));
        cards.add(new SetCardInfo("Kemba, Kha Regent", 19, Rarity.RARE, mage.cards.k.KembaKhaRegent.class));
        cards.add(new SetCardInfo("Kozilek's Predator", 173, Rarity.COMMON, mage.cards.k.KozileksPredator.class));
        cards.add(new SetCardInfo("Kuldotha Flamefiend", 134, Rarity.UNCOMMON, mage.cards.k.KuldothaFlamefiend.class));
        cards.add(new SetCardInfo("Kuldotha Forgemaster", 266, Rarity.RARE, mage.cards.k.KuldothaForgemaster.class));
        cards.add(new SetCardInfo("Land Tax", 20, Rarity.MYTHIC, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Leonin Abunas", 21, Rarity.RARE, mage.cards.l.LeoninAbunas.class));
        cards.add(new SetCardInfo("Liege of the Tangle", 174, Rarity.RARE, mage.cards.l.LiegeOfTheTangle.class));
        cards.add(new SetCardInfo("Lightning Axe", 135, Rarity.COMMON, mage.cards.l.LightningAxe.class));
        cards.add(new SetCardInfo("Lightning Greaves", 267, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Greaves", 360, Rarity.RARE, mage.cards.l.LightningGreaves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lux Cannon", 268, Rarity.RARE, mage.cards.l.LuxCannon.class));
        cards.add(new SetCardInfo("Maelstrom Nexus", 206, Rarity.MYTHIC, mage.cards.m.MaelstromNexus.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 207, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Magnifying Glass", 269, Rarity.COMMON, mage.cards.m.MagnifyingGlass.class));
        cards.add(new SetCardInfo("Magus of the Abyss", 97, Rarity.RARE, mage.cards.m.MagusOfTheAbyss.class));
        cards.add(new SetCardInfo("Magus of the Will", 98, Rarity.RARE, mage.cards.m.MagusOfTheWill.class));
        cards.add(new SetCardInfo("Mana Crypt", 270, Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", 361, Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Echoes", 136, Rarity.MYTHIC, mage.cards.m.ManaEchoes.class));
        cards.add(new SetCardInfo("Mana Reflection", 175, Rarity.RARE, mage.cards.m.ManaReflection.class));
        cards.add(new SetCardInfo("Manamorphose", 208, Rarity.UNCOMMON, mage.cards.m.Manamorphose.class));
        cards.add(new SetCardInfo("Master Splicer", 22, Rarity.UNCOMMON, mage.cards.m.MasterSplicer.class));
        cards.add(new SetCardInfo("Master Transmuter", 58, Rarity.RARE, mage.cards.m.MasterTransmuter.class));
        cards.add(new SetCardInfo("Master of Etherium", 57, Rarity.RARE, mage.cards.m.MasterOfEtherium.class));
        cards.add(new SetCardInfo("Masterwork of Ingenuity", 271, Rarity.RARE, mage.cards.m.MasterworkOfIngenuity.class));
        cards.add(new SetCardInfo("Maze of Ith", 322, Rarity.RARE, mage.cards.m.MazeOfIth.class));
        cards.add(new SetCardInfo("Mazirek, Kraul Death Priest", 209, Rarity.RARE, mage.cards.m.MazirekKraulDeathPriest.class));
        cards.add(new SetCardInfo("Meddling Mage", 210, Rarity.RARE, mage.cards.m.MeddlingMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meddling Mage", 355, Rarity.RARE, mage.cards.m.MeddlingMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Merciless Eviction", 211, Rarity.RARE, mage.cards.m.MercilessEviction.class));
        cards.add(new SetCardInfo("Mesmeric Orb", 272, Rarity.RARE, mage.cards.m.MesmericOrb.class));
        cards.add(new SetCardInfo("Metallic Rebuke", 59, Rarity.COMMON, mage.cards.m.MetallicRebuke.class));
        cards.add(new SetCardInfo("Metalspinner's Puzzleknot", 273, Rarity.COMMON, mage.cards.m.MetalspinnersPuzzleknot.class));
        cards.add(new SetCardInfo("Might of the Masses", 176, Rarity.COMMON, mage.cards.m.MightOfTheMasses.class));
        cards.add(new SetCardInfo("Mishra's Bauble", 274, Rarity.UNCOMMON, mage.cards.m.MishrasBauble.class));
        cards.add(new SetCardInfo("Mishra's Factory", 323, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Morkrut Banshee", 99, Rarity.UNCOMMON, mage.cards.m.MorkrutBanshee.class));
        cards.add(new SetCardInfo("Mountain", 379, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 380, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mox Opal", 275, Rarity.MYTHIC, mage.cards.m.MoxOpal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Opal", 362, Rarity.MYTHIC, mage.cards.m.MoxOpal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Myr Battlesphere", 276, Rarity.RARE, mage.cards.m.MyrBattlesphere.class));
        cards.add(new SetCardInfo("Myr Retriever", 277, Rarity.COMMON, mage.cards.m.MyrRetriever.class));
        cards.add(new SetCardInfo("Myrsmith", 23, Rarity.UNCOMMON, mage.cards.m.Myrsmith.class));
        cards.add(new SetCardInfo("Mystic Gate", 324, Rarity.RARE, mage.cards.m.MysticGate.class));
        cards.add(new SetCardInfo("Noble Hierarch", 177, Rarity.RARE, mage.cards.n.NobleHierarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Noble Hierarch", 352, Rarity.RARE, mage.cards.n.NobleHierarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("O-Naginata", 278, Rarity.UNCOMMON, mage.cards.o.ONaginata.class));
        cards.add(new SetCardInfo("Oblivion Stone", 279, Rarity.RARE, mage.cards.o.OblivionStone.class));
        cards.add(new SetCardInfo("Open the Vaults", 24, Rarity.RARE, mage.cards.o.OpenTheVaults.class));
        cards.add(new SetCardInfo("Orcish Vandal", 137, Rarity.COMMON, mage.cards.o.OrcishVandal.class));
        cards.add(new SetCardInfo("Oubliette", 100, Rarity.UNCOMMON, mage.cards.o.Oubliette.class));
        cards.add(new SetCardInfo("Ovalchase Daredevil", 101, Rarity.UNCOMMON, mage.cards.o.OvalchaseDaredevil.class));
        cards.add(new SetCardInfo("Painsmith", 102, Rarity.UNCOMMON, mage.cards.p.Painsmith.class));
        cards.add(new SetCardInfo("Parasitic Strix", 60, Rarity.COMMON, mage.cards.p.ParasiticStrix.class));
        cards.add(new SetCardInfo("Path to Exile", 25, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Peace Strider", 280, Rarity.COMMON, mage.cards.p.PeaceStrider.class));
        cards.add(new SetCardInfo("Pentad Prism", 281, Rarity.UNCOMMON, mage.cards.p.PentadPrism.class));
        cards.add(new SetCardInfo("Phyrexian Metamorph", 341, Rarity.RARE, mage.cards.p.PhyrexianMetamorph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Metamorph", 61, Rarity.RARE, mage.cards.p.PhyrexianMetamorph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Revoker", 282, Rarity.RARE, mage.cards.p.PhyrexianRevoker.class));
        cards.add(new SetCardInfo("Plains", 373, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 374, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Pongify", 62, Rarity.UNCOMMON, mage.cards.p.Pongify.class));
        cards.add(new SetCardInfo("Progenitor Mimic", 212, Rarity.RARE, mage.cards.p.ProgenitorMimic.class));
        cards.add(new SetCardInfo("Puresteel Paladin", 26, Rarity.RARE, mage.cards.p.PuresteelPaladin.class));
        cards.add(new SetCardInfo("Pyrewild Shaman", 138, Rarity.UNCOMMON, mage.cards.p.PyrewildShaman.class));
        cards.add(new SetCardInfo("Pyrite Spellbomb", 283, Rarity.COMMON, mage.cards.p.PyriteSpellbomb.class));
        cards.add(new SetCardInfo("Rage Reflection", 139, Rarity.RARE, mage.cards.r.RageReflection.class));
        cards.add(new SetCardInfo("Rapacious Dragon", 140, Rarity.COMMON, mage.cards.r.RapaciousDragon.class));
        cards.add(new SetCardInfo("Ratchet Bomb", 284, Rarity.RARE, mage.cards.r.RatchetBomb.class));
        cards.add(new SetCardInfo("Ravenous Intruder", 141, Rarity.UNCOMMON, mage.cards.r.RavenousIntruder.class));
        cards.add(new SetCardInfo("Ravenous Trap", 103, Rarity.RARE, mage.cards.r.RavenousTrap.class));
        cards.add(new SetCardInfo("Reclamation Sage", 178, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class));
        cards.add(new SetCardInfo("Relic Runner", 63, Rarity.COMMON, mage.cards.r.RelicRunner.class));
        cards.add(new SetCardInfo("Remember the Fallen", 27, Rarity.COMMON, mage.cards.r.RememberTheFallen.class));
        cards.add(new SetCardInfo("Reshape", 64, Rarity.RARE, mage.cards.r.Reshape.class));
        cards.add(new SetCardInfo("Revoke Existence", 28, Rarity.COMMON, mage.cards.r.RevokeExistence.class));
        cards.add(new SetCardInfo("Rhys the Redeemed", 213, Rarity.RARE, mage.cards.r.RhysTheRedeemed.class));
        cards.add(new SetCardInfo("Riddlesmith", 65, Rarity.UNCOMMON, mage.cards.r.Riddlesmith.class));
        cards.add(new SetCardInfo("Riku of Two Reflections", 214, Rarity.MYTHIC, mage.cards.r.RikuOfTwoReflections.class));
        cards.add(new SetCardInfo("Rolling Earthquake", 142, Rarity.RARE, mage.cards.r.RollingEarthquake.class));
        cards.add(new SetCardInfo("Rugged Prairie", 325, Rarity.RARE, mage.cards.r.RuggedPrairie.class));
        cards.add(new SetCardInfo("Rush of Knowledge", 66, Rarity.UNCOMMON, mage.cards.r.RushOfKnowledge.class));
        cards.add(new SetCardInfo("Salivating Gremlins", 143, Rarity.COMMON, mage.cards.s.SalivatingGremlins.class));
        cards.add(new SetCardInfo("Salvage Titan", 104, Rarity.RARE, mage.cards.s.SalvageTitan.class));
        cards.add(new SetCardInfo("Sanctum Gargoyle", 29, Rarity.COMMON, mage.cards.s.SanctumGargoyle.class));
        cards.add(new SetCardInfo("Sanctum Spirit", 30, Rarity.COMMON, mage.cards.s.SanctumSpirit.class));
        cards.add(new SetCardInfo("Sandstone Oracle", 285, Rarity.UNCOMMON, mage.cards.s.SandstoneOracle.class));
        cards.add(new SetCardInfo("Savageborn Hydra", 215, Rarity.RARE, mage.cards.s.SavagebornHydra.class));
        cards.add(new SetCardInfo("Sculpting Steel", 286, Rarity.RARE, mage.cards.s.SculptingSteel.class));
        cards.add(new SetCardInfo("Selesnya Guildmage", 217, Rarity.UNCOMMON, mage.cards.s.SelesnyaGuildmage.class));
        cards.add(new SetCardInfo("Sen Triplets", 218, Rarity.MYTHIC, mage.cards.s.SenTriplets.class));
        cards.add(new SetCardInfo("Sentinel of the Pearl Trident", 67, Rarity.UNCOMMON, mage.cards.s.SentinelOfThePearlTrident.class));
        cards.add(new SetCardInfo("Serra Sphinx", 68, Rarity.UNCOMMON, mage.cards.s.SerraSphinx.class));
        cards.add(new SetCardInfo("Shamanic Revelation", 179, Rarity.RARE, mage.cards.s.ShamanicRevelation.class));
        cards.add(new SetCardInfo("Sharuum the Hegemon", 219, Rarity.RARE, mage.cards.s.SharuumTheHegemon.class));
        cards.add(new SetCardInfo("Sickleslicer", 287, Rarity.COMMON, mage.cards.s.Sickleslicer.class));
        cards.add(new SetCardInfo("Sift", 69, Rarity.COMMON, mage.cards.s.Sift.class));
        cards.add(new SetCardInfo("Silumgar Scavenger", 105, Rarity.COMMON, mage.cards.s.SilumgarScavenger.class));
        cards.add(new SetCardInfo("Skinbrand Goblin", 144, Rarity.COMMON, mage.cards.s.SkinbrandGoblin.class));
        cards.add(new SetCardInfo("Skinwing", 288, Rarity.COMMON, mage.cards.s.Skinwing.class));
        cards.add(new SetCardInfo("Skirsdag High Priest", 106, Rarity.RARE, mage.cards.s.SkirsdagHighPriest.class));
        cards.add(new SetCardInfo("Skithiryx, the Blight Dragon", 107, Rarity.MYTHIC, mage.cards.s.SkithiryxTheBlightDragon.class));
        cards.add(new SetCardInfo("Skullmulcher", 180, Rarity.UNCOMMON, mage.cards.s.Skullmulcher.class));
        cards.add(new SetCardInfo("Sneak Attack", 145, Rarity.MYTHIC, mage.cards.s.SneakAttack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sneak Attack", 348, Rarity.MYTHIC, mage.cards.s.SneakAttack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spellskite", 289, Rarity.RARE, mage.cards.s.Spellskite.class));
        cards.add(new SetCardInfo("Sphinx Summoner", 220, Rarity.UNCOMMON, mage.cards.s.SphinxSummoner.class));
        cards.add(new SetCardInfo("Sphinx of the Guildpact", 290, Rarity.UNCOMMON, mage.cards.s.SphinxOfTheGuildpact.class));
        cards.add(new SetCardInfo("Springleaf Drum", 291, Rarity.UNCOMMON, mage.cards.s.SpringleafDrum.class));
        cards.add(new SetCardInfo("Steel Sabotage", 70, Rarity.COMMON, mage.cards.s.SteelSabotage.class));
        cards.add(new SetCardInfo("Stoneforge Mystic", 31, Rarity.RARE, mage.cards.s.StoneforgeMystic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stoneforge Mystic", 337, Rarity.RARE, mage.cards.s.StoneforgeMystic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stonehewer Giant", 32, Rarity.RARE, mage.cards.s.StonehewerGiant.class));
        cards.add(new SetCardInfo("Strength of Arms", 33, Rarity.COMMON, mage.cards.s.StrengthOfArms.class));
        cards.add(new SetCardInfo("Sundering Titan", 292, Rarity.RARE, mage.cards.s.SunderingTitan.class));
        cards.add(new SetCardInfo("Sunforger", 293, Rarity.RARE, mage.cards.s.Sunforger.class));
        cards.add(new SetCardInfo("Sunken Ruins", 326, Rarity.RARE, mage.cards.s.SunkenRuins.class));
        cards.add(new SetCardInfo("Supernatural Stamina", 108, Rarity.COMMON, mage.cards.s.SupernaturalStamina.class));
        cards.add(new SetCardInfo("Surge Node", 294, Rarity.COMMON, mage.cards.s.SurgeNode.class));
        cards.add(new SetCardInfo("Swamp", 377, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 378, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swiftblade Vindicator", 221, Rarity.RARE, mage.cards.s.SwiftbladeVindicator.class));
        cards.add(new SetCardInfo("Sword of Body and Mind", 295, Rarity.MYTHIC, mage.cards.s.SwordOfBodyAndMind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Body and Mind", 363, Rarity.MYTHIC, mage.cards.s.SwordOfBodyAndMind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 296, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 364, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Fire and Ice", 297, Rarity.MYTHIC, mage.cards.s.SwordOfFireAndIce.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Fire and Ice", 365, Rarity.MYTHIC, mage.cards.s.SwordOfFireAndIce.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Light and Shadow", 298, Rarity.MYTHIC, mage.cards.s.SwordOfLightAndShadow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Light and Shadow", 366, Rarity.MYTHIC, mage.cards.s.SwordOfLightAndShadow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of War and Peace", 300, Rarity.MYTHIC, mage.cards.s.SwordOfWarAndPeace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of War and Peace", 367, Rarity.MYTHIC, mage.cards.s.SwordOfWarAndPeace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of the Meek", 299, Rarity.RARE, mage.cards.s.SwordOfTheMeek.class));
        cards.add(new SetCardInfo("Sylvan Might", 181, Rarity.COMMON, mage.cards.s.SylvanMight.class));
        cards.add(new SetCardInfo("Tempered Steel", 34, Rarity.RARE, mage.cards.t.TemperedSteel.class));
        cards.add(new SetCardInfo("Temur Battle Rage", 146, Rarity.COMMON, mage.cards.t.TemurBattleRage.class));
        cards.add(new SetCardInfo("Terastodon", 182, Rarity.RARE, mage.cards.t.Terastodon.class));
        cards.add(new SetCardInfo("The Scarab God", 216, Rarity.MYTHIC, mage.cards.t.TheScarabGod.class));
        cards.add(new SetCardInfo("Thespian's Stage", 327, Rarity.RARE, mage.cards.t.ThespiansStage.class));
        cards.add(new SetCardInfo("Thirst for Knowledge", 71, Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Thopter Engineer", 147, Rarity.UNCOMMON, mage.cards.t.ThopterEngineer.class));
        cards.add(new SetCardInfo("Thopter Foundry", 222, Rarity.UNCOMMON, mage.cards.t.ThopterFoundry.class));
        cards.add(new SetCardInfo("Thought Reflection", 72, Rarity.RARE, mage.cards.t.ThoughtReflection.class));
        cards.add(new SetCardInfo("Thoughtseize", 109, Rarity.RARE, mage.cards.t.Thoughtseize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thoughtseize", 344, Rarity.RARE, mage.cards.t.Thoughtseize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thraben Inspector", 35, Rarity.COMMON, mage.cards.t.ThrabenInspector.class));
        cards.add(new SetCardInfo("Thragtusk", 183, Rarity.RARE, mage.cards.t.Thragtusk.class));
        cards.add(new SetCardInfo("Throne of Geth", 301, Rarity.UNCOMMON, mage.cards.t.ThroneOfGeth.class));
        cards.add(new SetCardInfo("Time Sieve", 223, Rarity.RARE, mage.cards.t.TimeSieve.class));
        cards.add(new SetCardInfo("Topple the Statue", 36, Rarity.UNCOMMON, mage.cards.t.ToppleTheStatue.class));
        cards.add(new SetCardInfo("Toxic Deluge", 110, Rarity.RARE, mage.cards.t.ToxicDeluge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Toxic Deluge", 345, Rarity.RARE, mage.cards.t.ToxicDeluge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trash for Treasure", 148, Rarity.UNCOMMON, mage.cards.t.TrashForTreasure.class));
        cards.add(new SetCardInfo("Treasure Keeper", 302, Rarity.UNCOMMON, mage.cards.t.TreasureKeeper.class));
        cards.add(new SetCardInfo("Treasure Mage", 73, Rarity.UNCOMMON, mage.cards.t.TreasureMage.class));
        cards.add(new SetCardInfo("Trinisphere", 303, Rarity.MYTHIC, mage.cards.t.Trinisphere.class));
        cards.add(new SetCardInfo("Tuktuk the Explorer", 149, Rarity.RARE, mage.cards.t.TuktukTheExplorer.class));
        cards.add(new SetCardInfo("Tumble Magnet", 304, Rarity.COMMON, mage.cards.t.TumbleMagnet.class));
        cards.add(new SetCardInfo("Twilight Mire", 328, Rarity.RARE, mage.cards.t.TwilightMire.class));
        cards.add(new SetCardInfo("Twisted Abomination", 111, Rarity.COMMON, mage.cards.t.TwistedAbomination.class));
        cards.add(new SetCardInfo("Ulvenwald Mysteries", 184, Rarity.UNCOMMON, mage.cards.u.UlvenwaldMysteries.class));
        cards.add(new SetCardInfo("Unlicensed Disintegration", 224, Rarity.UNCOMMON, mage.cards.u.UnlicensedDisintegration.class));
        cards.add(new SetCardInfo("Urza's Mine", 329, Rarity.COMMON, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", 370, Rarity.RARE, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 330, Rarity.COMMON, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 371, Rarity.RARE, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 331, Rarity.COMMON, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 372, Rarity.RARE, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valor in Akros", 37, Rarity.UNCOMMON, mage.cards.v.ValorInAkros.class));
        cards.add(new SetCardInfo("Valorous Stance", 38, Rarity.UNCOMMON, mage.cards.v.ValorousStance.class));
        cards.add(new SetCardInfo("Vampire Hexmage", 112, Rarity.UNCOMMON, mage.cards.v.VampireHexmage.class));
        cards.add(new SetCardInfo("Vedalken Infuser", 74, Rarity.COMMON, mage.cards.v.VedalkenInfuser.class));
        cards.add(new SetCardInfo("Vengevine", 185, Rarity.MYTHIC, mage.cards.v.Vengevine.class));
        cards.add(new SetCardInfo("Veteran Explorer", 186, Rarity.UNCOMMON, mage.cards.v.VeteranExplorer.class));
        cards.add(new SetCardInfo("Vexing Shusher", 225, Rarity.RARE, mage.cards.v.VexingShusher.class));
        cards.add(new SetCardInfo("Vish Kal, Blood Arbiter", 226, Rarity.RARE, mage.cards.v.VishKalBloodArbiter.class));
        cards.add(new SetCardInfo("Voice of Resurgence", 227, Rarity.RARE, mage.cards.v.VoiceOfResurgence.class));
        cards.add(new SetCardInfo("Vulshok Gauntlets", 305, Rarity.COMMON, mage.cards.v.VulshokGauntlets.class));
        cards.add(new SetCardInfo("Walking Ballista", 306, Rarity.RARE, mage.cards.w.WalkingBallista.class));
        cards.add(new SetCardInfo("Weapon Surge", 150, Rarity.COMMON, mage.cards.w.WeaponSurge.class));
        cards.add(new SetCardInfo("Weapons Trainer", 228, Rarity.UNCOMMON, mage.cards.w.WeaponsTrainer.class));
        cards.add(new SetCardInfo("Welding Jar", 307, Rarity.UNCOMMON, mage.cards.w.WeldingJar.class));
        cards.add(new SetCardInfo("Well of Ideas", 75, Rarity.RARE, mage.cards.w.WellOfIdeas.class));
        cards.add(new SetCardInfo("Whisperer of the Wilds", 187, Rarity.COMMON, mage.cards.w.WhispererOfTheWilds.class));
        cards.add(new SetCardInfo("Wooded Bastion", 332, Rarity.RARE, mage.cards.w.WoodedBastion.class));
        cards.add(new SetCardInfo("Woodland Champion", 188, Rarity.UNCOMMON, mage.cards.w.WoodlandChampion.class));
        cards.add(new SetCardInfo("Wound Reflection", 113, Rarity.RARE, mage.cards.w.WoundReflection.class));
        cards.add(new SetCardInfo("Wrath of God", 383, Rarity.RARE, mage.cards.w.WrathOfGod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrath of God", 39, Rarity.RARE, mage.cards.w.WrathOfGod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wurmcoil Engine", 308, Rarity.MYTHIC, mage.cards.w.WurmcoilEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wurmcoil Engine", 368, Rarity.MYTHIC, mage.cards.w.WurmcoilEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yavimaya's Embrace", 229, Rarity.UNCOMMON, mage.cards.y.YavimayasEmbrace.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new DoubleMastersCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/2xm.html
// Using USA collation for all rarities
// Foil slot partially inferred to match standard booster rarity as best as possible
// Foil odds (approximate): 10/14 to be common, 3/14 to be uncommon, 0.875/14 to be rare, 0.125/14 to be mythic
// Regular common sheets used for foil commons as foil common sheet is currently incomplete
// TODO: write a test, not sure how right now
class DoubleMastersCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(true, "160", "108", "146", "79", "247", "165", "114", "111", "163", "29", "143", "105", "162", "135", "154", "78", "144", "151", "140", "84", "187", "304", "87", "133", "173", "95", "126", "28", "176", "90", "137", "165", "83", "159", "116", "168", "92", "121", "154", "79", "150", "181", "247", "146", "111", "160", "143", "96", "114", "108", "151", "78", "135", "95", "162", "144", "87", "140", "105", "163", "304", "84", "126", "173", "111", "133", "29", "187", "83", "137", "176", "90", "159", "150", "96", "247", "146", "165", "92", "116", "28", "160", "108", "121", "79", "168", "144", "162", "87", "163", "114", "84", "154", "304", "105", "135", "173", "95", "126", "29", "151", "83", "140", "181", "133", "78", "143", "187", "28", "150", "96", "159", "176", "90", "137", "168", "116", "92", "181", "121");
    private final CardRun commonB = new CardRun(true, "250", "70", "259", "305", "45", "80", "261", "60", "288", "331", "294", "63", "255", "263", "46", "230", "262", "50", "257", "256", "44", "283", "237", "74", "157", "277", "59", "330", "280", "52", "254", "329", "250", "69", "239", "331", "45", "287", "288", "42", "115", "273", "40", "305", "269", "63", "294", "257", "50", "80", "230", "60", "256", "259", "46", "261", "283", "44", "237", "330", "70", "263", "255", "52", "262", "254", "40", "157", "287", "115", "69", "277", "273", "59", "329", "280", "74", "250", "257", "60", "331", "261", "45", "239", "269", "42", "288", "305", "63", "80", "283", "50", "294", "259", "70", "263", "237", "44", "255", "262", "46", "230", "157", "59", "256", "330", "52", "254", "277", "74", "280", "329", "69", "287", "115", "42", "239", "269", "40", "273");
    private final CardRun commonC = new CardRun(true, "18", "35", "4", "17", "2", "30", "12", "27", "3", "13", "33", "30", "18", "12", "35", "27", "4", "3", "17", "13", "2", "33", "12", "4", "13", "18", "27", "17", "33", "30", "35", "3", "2", "18", "35", "4", "17", "2", "30", "12", "27", "3", "13", "33", "30", "18", "12", "35", "27", "4", "3", "17", "13", "2", "33", "12", "4", "13", "18", "27", "17", "33", "30", "35", "3", "2", "18", "35", "4", "17", "2", "30", "12", "27", "3", "13", "33", "30", "18", "12", "35", "27", "4", "3", "17", "13", "2", "33", "12", "4", "13", "18", "27", "17", "33", "30", "35", "3", "2", "18", "35", "4", "17", "2", "30", "12", "27", "3", "13", "33", "30", "18", "12", "35", "27", "4", "3", "17", "13", "2", "33");
    private final CardRun uncommonA = new CardRun(true, "315", "244", "73", "274", "208", "147", "202", "169", "290", "102", "65", "285", "220", "67", "186", "246", "112", "222", "22", "301", "86", "62", "228", "161", "101", "302", "54", "184", "220", "307", "73", "93", "15", "119", "202", "291", "169", "323", "102", "244", "201", "172", "312", "290", "37", "208", "246", "6", "65", "307", "86", "186", "67", "222", "141", "15", "315", "22", "274", "161", "37", "101", "285", "228", "184", "112", "147", "302", "172", "54", "220", "141", "301", "6", "93", "119", "169", "323", "291", "312", "201", "62", "244", "184", "102", "15", "222", "37", "290", "112", "147", "65", "285", "101", "315", "67", "202", "186", "274", "208", "323", "73", "307", "228", "86", "161", "119", "246", "312", "6", "22", "172", "301", "62", "302", "141", "93", "291", "54", "201");
    private final CardRun uncommonB = new CardRun(true, "217", "23", "49", "245", "91", "194", "148", "71", "16", "125", "238", "198", "180", "36", "278", "99", "224", "38", "232", "123", "68", "258", "229", "310", "120", "242", "188", "25", "66", "267", "138", "178", "281", "199", "89", "194", "241", "23", "49", "91", "245", "166", "134", "238", "217", "148", "36", "265", "16", "125", "198", "232", "71", "100", "267", "229", "180", "68", "278", "123", "25", "99", "241", "38", "120", "258", "199", "188", "224", "281", "310", "49", "23", "66", "138", "178", "245", "217", "166", "134", "242", "89", "36", "265", "148", "100", "242", "198", "180", "25", "238", "16", "194", "38", "91", "71", "125", "278", "229", "310", "68", "232", "123", "178", "99", "258", "188", "120", "267", "199", "89", "224", "241", "66", "134", "166", "281", "138", "100", "265");
    private final CardRun rareA = new CardRun(false, "76", "231", "153", "77", "117", "118", "10", "43", "313", "158", "48", "85", "124", "252", "14", "167", "316", "318", "196", "127", "320", "130", "321", "97", "175", "271", "210", "272", "282", "26", "139", "103", "64", "325", "104", "179", "289", "32", "293", "326", "299", "327", "72", "109", "223", "225", "226", "75", "332", "113", "76", "231", "153", "77", "117", "118", "10", "43", "313", "158", "48", "85", "124", "252", "14", "167", "316", "318", "196", "127", "320", "130", "321", "97", "175", "271", "210", "272", "282", "26", "139", "103", "64", "325", "104", "179", "289", "32", "293", "326", "299", "327", "72", "109", "223", "225", "226", "75", "332", "113", "190", "8", "192", "240", "81", "314", "248", "164", "253", "51", "131", "204", "205", "20", "206", "136", "275", "214", "218", "303");
    private final CardRun rareB = new CardRun(false, "122", "82", "317", "55", "264", "174", "324", "24", "284", "215", "328");
    private final CardRun rareC = new CardRun(false, "189", "7", "311", "155", "236", "193", "11", "47", "249", "195", "128", "170", "260", "132", "203", "21", "268", "98", "57", "322", "209", "177", "279", "61", "212", "219", "292", "34", "183", "110", "149", "227", "306");
    private final CardRun rareD = new CardRun(false, "309", "191", "233", "9", "156", "243", "88", "251", "319", "53", "129", "200", "171", "19", "266", "207", "58", "211", "276", "213", "142", "286", "106", "31", "221", "182", "39");
    private final CardRun rareE = new CardRun(false, "5", "41", "152", "234", "235", "197", "94", "56", "1", "270", "107", "145", "295", "296", "297", "298", "300", "216", "185", "308");
    private final CardRun foilUncommonA = new CardRun(false, "6", "119", "312", "244", "161", "246", "315", "86", "93", "15", "169", "201", "54", "172", "202", "208", "22", "274", "323", "101", "102", "62", "141", "65", "285", "67", "220", "290", "291", "147", "222", "301", "302", "73", "184", "37", "112", "186", "228", "307");
    private final CardRun foilUncommonB = new CardRun(false, "310", "232", "120", "238", "241", "242", "245", "194", "123", "89", "91", "166", "49", "16", "125", "198", "199", "258", "265", "134", "267", "99", "23", "278", "100", "25", "281", "138", "178", "66", "217", "68", "180", "71", "36", "148", "224", "38", "188", "229");
    private final CardRun foilRareA = new CardRun(false, "309", "231", "76", "189", "7", "153", "191", "233", "77", "9", "117", "311", "118", "155", "10", "236", "43", "193", "313", "156", "158", "243", "11", "122", "47", "82", "48", "85", "88", "124", "249", "251", "252", "14", "167", "195", "316", "317", "318", "196", "319", "127", "128", "53", "320", "170", "129", "260", "200", "171", "130", "321", "55", "132", "264", "203", "19", "266", "21", "174", "268", "207", "97", "98", "175", "57", "58", "271", "322", "209", "210", "211", "272", "276", "324", "177", "279", "24", "61", "282", "212", "26", "139", "284", "103", "64", "213", "142", "325", "104", "215", "286", "179", "219", "106", "289", "31", "32", "292", "293", "326", "221", "299", "34", "182", "327", "72", "109", "183", "223", "110", "149", "328", "225", "226", "227", "306", "75", "332", "113", "39");
    private final CardRun foilRareB = new CardRun(false, "5", "41", "190", "8", "152", "234", "235", "192", "240", "81", "314", "248", "164", "253", "51", "197", "94", "131", "56", "204", "1", "205", "20", "206", "270", "136", "275", "214", "218", "107", "145", "295", "296", "297", "298", "300", "216", "303", "185", "308");

    private final BoosterStructure C1 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonC
    );
    private final BoosterStructure C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC
    );
    private final BoosterStructure C3 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB
    );
    private final BoosterStructure U1 = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure U2 = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rareA, rareC);
    private final BoosterStructure R2 = new BoosterStructure(rareA, rareD);
    private final BoosterStructure R3 = new BoosterStructure(rareA, rareE);
    private final BoosterStructure R4 = new BoosterStructure(rareB, rareC);
    private final BoosterStructure R5 = new BoosterStructure(rareB, rareD);
    private final BoosterStructure R6 = new BoosterStructure(rareB, rareE);

    private final BoosterStructure F01 = new BoosterStructure(commonA, commonB);
    private final BoosterStructure F02 = new BoosterStructure(commonA, commonC);
    private final BoosterStructure F03 = new BoosterStructure(commonA, foilUncommonA);
    private final BoosterStructure F04 = new BoosterStructure(commonA, foilUncommonB);
    private final BoosterStructure F05 = new BoosterStructure(commonA, foilRareA);
    private final BoosterStructure F06 = new BoosterStructure(commonA, foilRareB);
    private final BoosterStructure F07 = new BoosterStructure(commonB, commonC);
    private final BoosterStructure F08 = new BoosterStructure(commonB, foilUncommonA);
    private final BoosterStructure F09 = new BoosterStructure(commonB, foilUncommonB);
    private final BoosterStructure F10 = new BoosterStructure(commonB, foilRareA);
    private final BoosterStructure F11 = new BoosterStructure(commonB, foilRareB);
    private final BoosterStructure F12 = new BoosterStructure(commonC, foilUncommonA);
    private final BoosterStructure F13 = new BoosterStructure(commonC, foilUncommonB);
    private final BoosterStructure F14 = new BoosterStructure(commonC, foilRareA);
    private final BoosterStructure F15 = new BoosterStructure(commonC, foilRareB);
    private final BoosterStructure F16 = new BoosterStructure(foilUncommonA, foilUncommonB);
    private final BoosterStructure F17 = new BoosterStructure(foilUncommonA, foilRareA);
    private final BoosterStructure F18 = new BoosterStructure(foilUncommonA, foilRareB);
    private final BoosterStructure F19 = new BoosterStructure(foilUncommonB, foilRareA);
    private final BoosterStructure F20 = new BoosterStructure(foilUncommonB, foilRareB);
    private final BoosterStructure F21 = new BoosterStructure(foilRareA, foilRareB);

    private final RarityConfiguration commonRuns = new RarityConfiguration(
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C1, C2, C1, C2,
            C3, C3, C3
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            U1, U2
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(
            R1, R2, R3,
            R4, R5, R6
    );
    private final RarityConfiguration foilRuns = new RarityConfiguration(
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,
            F01, F01, F01,

            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,
            F02, F02, F02,

            F03, F03, F03,
            F03, F03, F03,
            F03, F03, F03,
            F03, F03, F03,
            F03, F03, F03,
            F03,

            F04, F04, F04,
            F04, F04, F04,
            F04, F04, F04,
            F04, F04, F04,
            F04, F04, F04,
            F04,

            F05, F05, F05,
            F05, F05, F05,
            F05, F05,

            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,
            F07, F07, F07,

            F08, F08, F08,
            F08, F08, F08,
            F08, F08, F08,
            F08, F08, F08,
            F08, F08, F08,
            F08,

            F09, F09, F09,
            F09, F09, F09,
            F09, F09, F09,
            F09, F09, F09,
            F09, F09, F09,
            F09,

            F10, F10, F10,
            F10, F10, F10,
            F10, F10,

            F12, F12, F12,
            F12, F12, F12,
            F12, F12, F12,
            F12, F12, F12,
            F12, F12, F12,
            F12,

            F13, F13, F13,
            F13, F13, F13,
            F13, F13, F13,
            F13, F13, F13,
            F13, F13, F13,
            F13,

            F14, F14, F14,
            F14, F14, F14,
            F14, F14,

            F16, F16, F16,
            F16, F16, F16,
            F16, F16, F16,
            F16, F16, F16,
            F16, F16, F16,
            F16,

            F17, F17, F17,
            F17, F17, F17,
            F17, F17,

            F19, F19, F19,
            F19, F19, F19,
            F19, F19,

            F06, F11, F15,
            F18, F20, F21
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
