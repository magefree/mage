package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.constants.SuperType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class DominariaUnited extends ExpansionSet {

    private static final DominariaUnited instance = new DominariaUnited();

    public static DominariaUnited getInstance() {
        return instance;
    }

    private DominariaUnited() {
        super("Dominaria United", "DMU", ExpansionSet.buildDate(2022, 9, 9), SetType.EXPANSION);
        this.blockName = "Dominaria United";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;        // non-legendary creature: 46 rare, 13 mythic
        this.ratioBoosterSpecialRare = 4;
        this.ratioBoosterSpecialMythic = 5; // legendary creature: 14 rare, 7 mythic
        this.maxCardNumberInBooster = 281;

        cards.add(new SetCardInfo("Academy Loremaster", 391, Rarity.RARE, mage.cards.a.AcademyLoremaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Academy Loremaster", 40, Rarity.RARE, mage.cards.a.AcademyLoremaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Academy Wall", 41, Rarity.COMMON, mage.cards.a.AcademyWall.class));
        cards.add(new SetCardInfo("Adarkar Wastes", 243, Rarity.RARE, mage.cards.a.AdarkarWastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Adarkar Wastes", 377, Rarity.RARE, mage.cards.a.AdarkarWastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aether Channeler", 392, Rarity.RARE, mage.cards.a.AetherChanneler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aether Channeler", 42, Rarity.RARE, mage.cards.a.AetherChanneler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aggressive Sabotage", 78, Rarity.COMMON, mage.cards.a.AggressiveSabotage.class));
        cards.add(new SetCardInfo("Ajani, Sleeper Agent", 192, Rarity.MYTHIC, mage.cards.a.AjaniSleeperAgent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ajani, Sleeper Agent", 370, Rarity.MYTHIC, mage.cards.a.AjaniSleeperAgent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ajani, Sleeper Agent", 371, Rarity.MYTHIC, mage.cards.a.AjaniSleeperAgent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ajani, Sleeper Agent", 375, Rarity.MYTHIC, mage.cards.a.AjaniSleeperAgent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ajani, Sleeper Agent", 376, Rarity.MYTHIC, mage.cards.a.AjaniSleeperAgent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anointed Peacekeeper", 2, Rarity.RARE, mage.cards.a.AnointedPeacekeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anointed Peacekeeper", 383, Rarity.RARE, mage.cards.a.AnointedPeacekeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archangel of Wrath", 3, Rarity.RARE, mage.cards.a.ArchangelOfWrath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archangel of Wrath", 384, Rarity.RARE, mage.cards.a.ArchangelOfWrath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Argivian Cavalier", 4, Rarity.COMMON, mage.cards.a.ArgivianCavalier.class));
        cards.add(new SetCardInfo("Argivian Phalanx", 5, Rarity.COMMON, mage.cards.a.ArgivianPhalanx.class));
        cards.add(new SetCardInfo("Aron, Benalia's Ruin", 193, Rarity.UNCOMMON, mage.cards.a.AronBenaliasRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aron, Benalia's Ruin", 292, Rarity.UNCOMMON, mage.cards.a.AronBenaliasRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aron, Benalia's Ruin", 333, Rarity.UNCOMMON, mage.cards.a.AronBenaliasRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Artillery Blast", 6, Rarity.COMMON, mage.cards.a.ArtilleryBlast.class));
        cards.add(new SetCardInfo("Astor, Bearer of Blades", 194, Rarity.RARE, mage.cards.a.AstorBearerOfBlades.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Astor, Bearer of Blades", 293, Rarity.RARE, mage.cards.a.AstorBearerOfBlades.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Astor, Bearer of Blades", 334, Rarity.RARE, mage.cards.a.AstorBearerOfBlades.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Automatic Librarian", 229, Rarity.COMMON, mage.cards.a.AutomaticLibrarian.class));
        cards.add(new SetCardInfo("Baird, Argivian Recruiter", 195, Rarity.UNCOMMON, mage.cards.b.BairdArgivianRecruiter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baird, Argivian Recruiter", 294, Rarity.UNCOMMON, mage.cards.b.BairdArgivianRecruiter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baird, Argivian Recruiter", 335, Rarity.UNCOMMON, mage.cards.b.BairdArgivianRecruiter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Balduvian Atrocity", 79, Rarity.UNCOMMON, mage.cards.b.BalduvianAtrocity.class));
        cards.add(new SetCardInfo("Balduvian Berserker", 116, Rarity.UNCOMMON, mage.cards.b.BalduvianBerserker.class));
        cards.add(new SetCardInfo("Balmor, Battlemage Captain", 196, Rarity.UNCOMMON, mage.cards.b.BalmorBattlemageCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Balmor, Battlemage Captain", 295, Rarity.UNCOMMON, mage.cards.b.BalmorBattlemageCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Balmor, Battlemage Captain", 336, Rarity.UNCOMMON, mage.cards.b.BalmorBattlemageCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Barkweave Crusher", 154, Rarity.COMMON, mage.cards.b.BarkweaveCrusher.class));
        cards.add(new SetCardInfo("Battle-Rage Blessing", 80, Rarity.COMMON, mage.cards.b.BattleRageBlessing.class));
        cards.add(new SetCardInfo("Battlefly Swarm", 81, Rarity.COMMON, mage.cards.b.BattleflySwarm.class));
        cards.add(new SetCardInfo("Battlewing Mystic", 43, Rarity.UNCOMMON, mage.cards.b.BattlewingMystic.class));
        cards.add(new SetCardInfo("Benalish Faithbonder", 7, Rarity.COMMON, mage.cards.b.BenalishFaithbonder.class));
        cards.add(new SetCardInfo("Benalish Sleeper", 8, Rarity.COMMON, mage.cards.b.BenalishSleeper.class));
        cards.add(new SetCardInfo("Bite Down", 155, Rarity.COMMON, mage.cards.b.BiteDown.class));
        cards.add(new SetCardInfo("Blight Pile", 82, Rarity.UNCOMMON, mage.cards.b.BlightPile.class));
        cards.add(new SetCardInfo("Bog Badger", 156, Rarity.COMMON, mage.cards.b.BogBadger.class));
        cards.add(new SetCardInfo("Bone Splinters", 83, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Bortuk Bonerattle", 197, Rarity.UNCOMMON, mage.cards.b.BortukBonerattle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bortuk Bonerattle", 296, Rarity.UNCOMMON, mage.cards.b.BortukBonerattle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bortuk Bonerattle", 337, Rarity.UNCOMMON, mage.cards.b.BortukBonerattle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Braids's Frightful Return", 85, Rarity.UNCOMMON, mage.cards.b.BraidssFrightfulReturn.class));
        cards.add(new SetCardInfo("Braids, Arisen Nightmare", 288, Rarity.RARE, mage.cards.b.BraidsArisenNightmare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Braids, Arisen Nightmare", 329, Rarity.RARE, mage.cards.b.BraidsArisenNightmare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Braids, Arisen Nightmare", 84, Rarity.RARE, mage.cards.b.BraidsArisenNightmare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Briar Hydra", 286, Rarity.RARE, mage.cards.b.BriarHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Briar Hydra", 427, Rarity.RARE, mage.cards.b.BriarHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Broken Wings", 157, Rarity.COMMON, mage.cards.b.BrokenWings.class));
        cards.add(new SetCardInfo("Captain's Call", 9, Rarity.COMMON, mage.cards.c.CaptainsCall.class));
        cards.add(new SetCardInfo("Caves of Koilos", 244, Rarity.RARE, mage.cards.c.CavesOfKoilos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Caves of Koilos", 378, Rarity.RARE, mage.cards.c.CavesOfKoilos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chaotic Transformation", 117, Rarity.RARE, mage.cards.c.ChaoticTransformation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chaotic Transformation", 405, Rarity.RARE, mage.cards.c.ChaoticTransformation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Charismatic Vanguard", 10, Rarity.COMMON, mage.cards.c.CharismaticVanguard.class));
        cards.add(new SetCardInfo("Choking Miasma", 86, Rarity.UNCOMMON, mage.cards.c.ChokingMiasma.class));
        cards.add(new SetCardInfo("Citizen's Arrest", 11, Rarity.COMMON, mage.cards.c.CitizensArrest.class));
        cards.add(new SetCardInfo("Cleaving Skyrider", 12, Rarity.UNCOMMON, mage.cards.c.CleavingSkyrider.class));
        cards.add(new SetCardInfo("Clockwork Drawbridge", 13, Rarity.COMMON, mage.cards.c.ClockworkDrawbridge.class));
        cards.add(new SetCardInfo("Coalition Skyknight", 14, Rarity.UNCOMMON, mage.cards.c.CoalitionSkyknight.class));
        cards.add(new SetCardInfo("Coalition Warbrute", 118, Rarity.COMMON, mage.cards.c.CoalitionWarbrute.class));
        cards.add(new SetCardInfo("Colossal Growth", 158, Rarity.COMMON, mage.cards.c.ColossalGrowth.class));
        cards.add(new SetCardInfo("Combat Research", 44, Rarity.UNCOMMON, mage.cards.c.CombatResearch.class));
        cards.add(new SetCardInfo("Contaminated Aquifer", 245, Rarity.COMMON, mage.cards.c.ContaminatedAquifer.class));
        cards.add(new SetCardInfo("Coral Colony", 45, Rarity.UNCOMMON, mage.cards.c.CoralColony.class));
        cards.add(new SetCardInfo("Cosmic Epiphany", 283, Rarity.RARE, mage.cards.c.CosmicEpiphany.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cosmic Epiphany", 424, Rarity.RARE, mage.cards.c.CosmicEpiphany.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crystal Grotto", 246, Rarity.COMMON, mage.cards.c.CrystalGrotto.class));
        cards.add(new SetCardInfo("Cult Conscript", 88, Rarity.UNCOMMON, mage.cards.c.CultConscript.class));
        cards.add(new SetCardInfo("Cut Down", 432, Rarity.UNCOMMON, mage.cards.c.CutDown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cut Down", 89, Rarity.UNCOMMON, mage.cards.c.CutDown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Danitha, Benalia's Hope", 15, Rarity.RARE, mage.cards.d.DanithaBenaliasHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Danitha, Benalia's Hope", 287, Rarity.RARE, mage.cards.d.DanithaBenaliasHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Danitha, Benalia's Hope", 328, Rarity.RARE, mage.cards.d.DanithaBenaliasHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathbloom Gardener", 159, Rarity.COMMON, mage.cards.d.DeathbloomGardener.class));
        cards.add(new SetCardInfo("Defiler of Dreams", 393, Rarity.RARE, mage.cards.d.DefilerOfDreams.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiler of Dreams", 46, Rarity.RARE, mage.cards.d.DefilerOfDreams.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiler of Faith", 16, Rarity.RARE, mage.cards.d.DefilerOfFaith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiler of Faith", 385, Rarity.RARE, mage.cards.d.DefilerOfFaith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiler of Flesh", 400, Rarity.RARE, mage.cards.d.DefilerOfFlesh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiler of Flesh", 90, Rarity.RARE, mage.cards.d.DefilerOfFlesh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiler of Instinct", 119, Rarity.RARE, mage.cards.d.DefilerOfInstinct.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiler of Instinct", 406, Rarity.RARE, mage.cards.d.DefilerOfInstinct.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiler of Vigor", 160, Rarity.RARE, mage.cards.d.DefilerOfVigor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defiler of Vigor", 412, Rarity.RARE, mage.cards.d.DefilerOfVigor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Destroy Evil", 17, Rarity.COMMON, mage.cards.d.DestroyEvil.class));
        cards.add(new SetCardInfo("Djinn of the Fountain", 47, Rarity.UNCOMMON, mage.cards.d.DjinnOfTheFountain.class));
        cards.add(new SetCardInfo("Drag to the Bottom", 401, Rarity.RARE, mage.cards.d.DragToTheBottom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drag to the Bottom", 91, Rarity.RARE, mage.cards.d.DragToTheBottom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon Whelp", 120, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Eerie Soultender", 92, Rarity.COMMON, mage.cards.e.EerieSoultender.class));
        cards.add(new SetCardInfo("Elas il-Kor, Sadistic Pilgrim", 198, Rarity.UNCOMMON, mage.cards.e.ElasIlKorSadisticPilgrim.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elas il-Kor, Sadistic Pilgrim", 297, Rarity.UNCOMMON, mage.cards.e.ElasIlKorSadisticPilgrim.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elas il-Kor, Sadistic Pilgrim", 338, Rarity.UNCOMMON, mage.cards.e.ElasIlKorSadisticPilgrim.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Electrostatic Infantry", 122, Rarity.UNCOMMON, mage.cards.e.ElectrostaticInfantry.class));
        cards.add(new SetCardInfo("Elfhame Wurm", 161, Rarity.COMMON, mage.cards.e.ElfhameWurm.class));
        cards.add(new SetCardInfo("Elvish Hydromancer", 162, Rarity.UNCOMMON, mage.cards.e.ElvishHydromancer.class));
        cards.add(new SetCardInfo("Ertai Resurrected", 199, Rarity.RARE, mage.cards.e.ErtaiResurrected.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ertai Resurrected", 298, Rarity.RARE, mage.cards.e.ErtaiResurrected.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ertai Resurrected", 339, Rarity.RARE, mage.cards.e.ErtaiResurrected.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ertai's Scorn", 48, Rarity.UNCOMMON, mage.cards.e.ErtaisScorn.class));
        cards.add(new SetCardInfo("Essence Scatter", 49, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Evolved Sleeper", 402, Rarity.RARE, mage.cards.e.EvolvedSleeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Evolved Sleeper", 93, Rarity.RARE, mage.cards.e.EvolvedSleeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Extinguish the Light", 94, Rarity.COMMON, mage.cards.e.ExtinguishTheLight.class));
        cards.add(new SetCardInfo("Fires of Victory", 123, Rarity.UNCOMMON, mage.cards.f.FiresOfVictory.class));
        cards.add(new SetCardInfo("Floriferous Vinewall", 163, Rarity.COMMON, mage.cards.f.FloriferousVinewall.class));
        cards.add(new SetCardInfo("Flowstone Infusion", 124, Rarity.COMMON, mage.cards.f.FlowstoneInfusion.class));
        cards.add(new SetCardInfo("Flowstone Kavu", 125, Rarity.COMMON, mage.cards.f.FlowstoneKavu.class));
        cards.add(new SetCardInfo("Forest", 274, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 275, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 281, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Founding the Third Path", 50, Rarity.UNCOMMON, mage.cards.f.FoundingTheThirdPath.class));
        cards.add(new SetCardInfo("Frostfist Strider", 51, Rarity.UNCOMMON, mage.cards.f.FrostfistStrider.class));
        cards.add(new SetCardInfo("Furious Bellow", 126, Rarity.COMMON, mage.cards.f.FuriousBellow.class));
        cards.add(new SetCardInfo("Gaea's Might", 164, Rarity.COMMON, mage.cards.g.GaeasMight.class));
        cards.add(new SetCardInfo("Garna, Bloodfist of Keld", 200, Rarity.UNCOMMON, mage.cards.g.GarnaBloodfistOfKeld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garna, Bloodfist of Keld", 299, Rarity.UNCOMMON, mage.cards.g.GarnaBloodfistOfKeld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garna, Bloodfist of Keld", 340, Rarity.UNCOMMON, mage.cards.g.GarnaBloodfistOfKeld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geothermal Bog", 247, Rarity.COMMON, mage.cards.g.GeothermalBog.class));
        cards.add(new SetCardInfo("Ghitu Amplifier", 127, Rarity.COMMON, mage.cards.g.GhituAmplifier.class));
        cards.add(new SetCardInfo("Gibbering Barricade", 95, Rarity.COMMON, mage.cards.g.GibberingBarricade.class));
        cards.add(new SetCardInfo("Goblin Picker", 128, Rarity.COMMON, mage.cards.g.GoblinPicker.class));
        cards.add(new SetCardInfo("Golden Argosy", 230, Rarity.RARE, mage.cards.g.GoldenArgosy.class));
        cards.add(new SetCardInfo("Griffin Protector", 18, Rarity.COMMON, mage.cards.g.GriffinProtector.class));
        cards.add(new SetCardInfo("Guardian of New Benalia", 19, Rarity.RARE, mage.cards.g.GuardianOfNewBenalia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guardian of New Benalia", 386, Rarity.RARE, mage.cards.g.GuardianOfNewBenalia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hammerhand", 129, Rarity.COMMON, mage.cards.h.Hammerhand.class));
        cards.add(new SetCardInfo("Haughty Djinn", 394, Rarity.RARE, mage.cards.h.HaughtyDjinn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Haughty Djinn", 52, Rarity.RARE, mage.cards.h.HaughtyDjinn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Haunted Mire", 248, Rarity.COMMON, mage.cards.h.HauntedMire.class));
        cards.add(new SetCardInfo("Haunting Figment", 53, Rarity.COMMON, mage.cards.h.HauntingFigment.class));
        cards.add(new SetCardInfo("Herd Migration", 165, Rarity.RARE, mage.cards.h.HerdMigration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Herd Migration", 413, Rarity.RARE, mage.cards.h.HerdMigration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Herd Migration", 429, Rarity.RARE, mage.cards.h.HerdMigration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hero's Heirloom", 231, Rarity.UNCOMMON, mage.cards.h.HerosHeirloom.class));
        cards.add(new SetCardInfo("Heroic Charge", 20, Rarity.COMMON, mage.cards.h.HeroicCharge.class));
        cards.add(new SetCardInfo("Hexbane Tortoise", 166, Rarity.COMMON, mage.cards.h.HexbaneTortoise.class));
        cards.add(new SetCardInfo("Hurler Cyclops", 130, Rarity.UNCOMMON, mage.cards.h.HurlerCyclops.class));
        cards.add(new SetCardInfo("Hurloon Battle Hymn", 131, Rarity.UNCOMMON, mage.cards.h.HurloonBattleHymn.class));
        cards.add(new SetCardInfo("Idyllic Beachfront", 249, Rarity.COMMON, mage.cards.i.IdyllicBeachfront.class));
        cards.add(new SetCardInfo("Impede Momentum", 54, Rarity.COMMON, mage.cards.i.ImpedeMomentum.class));
        cards.add(new SetCardInfo("Impulse", 55, Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("In Thrall to the Pit", 132, Rarity.COMMON, mage.cards.i.InThrallToThePit.class));
        cards.add(new SetCardInfo("Inscribed Tablet", 232, Rarity.UNCOMMON, mage.cards.i.InscribedTablet.class));
        cards.add(new SetCardInfo("Island", 265, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 267, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 278, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Ivy, Gleeful Spellthief", 201, Rarity.RARE, mage.cards.i.IvyGleefulSpellthief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivy, Gleeful Spellthief", 300, Rarity.RARE, mage.cards.i.IvyGleefulSpellthief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivy, Gleeful Spellthief", 341, Rarity.RARE, mage.cards.i.IvyGleefulSpellthief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaya's Firenado", 134, Rarity.COMMON, mage.cards.j.JayasFirenado.class));
        cards.add(new SetCardInfo("Jaya, Fiery Negotiator", 133, Rarity.MYTHIC, mage.cards.j.JayaFieryNegotiator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaya, Fiery Negotiator", 374, Rarity.MYTHIC, mage.cards.j.JayaFieryNegotiator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jhoira, Ageless Innovator", 202, Rarity.RARE, mage.cards.j.JhoiraAgelessInnovator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jhoira, Ageless Innovator", 301, Rarity.RARE, mage.cards.j.JhoiraAgelessInnovator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jhoira, Ageless Innovator", 342, Rarity.RARE, mage.cards.j.JhoiraAgelessInnovator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jodah's Codex", 233, Rarity.UNCOMMON, mage.cards.j.JodahsCodex.class));
        cards.add(new SetCardInfo("Jodah, the Unifier", 203, Rarity.MYTHIC, mage.cards.j.JodahTheUnifier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jodah, the Unifier", 302, Rarity.MYTHIC, mage.cards.j.JodahTheUnifier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jodah, the Unifier", 343, Rarity.MYTHIC, mage.cards.j.JodahTheUnifier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Join Forces", 21, Rarity.UNCOMMON, mage.cards.j.JoinForces.class));
        cards.add(new SetCardInfo("Joint Exploration", 56, Rarity.UNCOMMON, mage.cards.j.JointExploration.class));
        cards.add(new SetCardInfo("Juniper Order Rootweaver", 22, Rarity.COMMON, mage.cards.j.JuniperOrderRootweaver.class));
        cards.add(new SetCardInfo("Karn's Sylex", 234, Rarity.MYTHIC, mage.cards.k.KarnsSylex.class));
        cards.add(new SetCardInfo("Karn, Living Legacy", 1, Rarity.MYTHIC, mage.cards.k.KarnLivingLegacy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karn, Living Legacy", 372, Rarity.MYTHIC, mage.cards.k.KarnLivingLegacy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karplusan Forest", 250, Rarity.RARE, mage.cards.k.KarplusanForest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karplusan Forest", 379, Rarity.RARE, mage.cards.k.KarplusanForest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keldon Flamesage", 135, Rarity.RARE, mage.cards.k.KeldonFlamesage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keldon Flamesage", 407, Rarity.RARE, mage.cards.k.KeldonFlamesage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keldon Strike Team", 136, Rarity.COMMON, mage.cards.k.KeldonStrikeTeam.class));
        cards.add(new SetCardInfo("King Darien XLVIII", 204, Rarity.RARE, mage.cards.k.KingDarienXLVIII.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("King Darien XLVIII", 303, Rarity.RARE, mage.cards.k.KingDarienXLVIII.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("King Darien XLVIII", 344, Rarity.RARE, mage.cards.k.KingDarienXLVIII.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Knight of Dawn's Light", 23, Rarity.UNCOMMON, mage.cards.k.KnightOfDawnsLight.class));
        cards.add(new SetCardInfo("Knight of Dusk's Shadow", 96, Rarity.UNCOMMON, mage.cards.k.KnightOfDusksShadow.class));
        cards.add(new SetCardInfo("Lagomos, Hand of Hatred", 205, Rarity.UNCOMMON, mage.cards.l.LagomosHandOfHatred.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lagomos, Hand of Hatred", 304, Rarity.UNCOMMON, mage.cards.l.LagomosHandOfHatred.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lagomos, Hand of Hatred", 345, Rarity.UNCOMMON, mage.cards.l.LagomosHandOfHatred.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leaf-Crowned Visionary", 167, Rarity.RARE, mage.cards.l.LeafCrownedVisionary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leaf-Crowned Visionary", 414, Rarity.RARE, mage.cards.l.LeafCrownedVisionary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leyline Binding", 24, Rarity.RARE, mage.cards.l.LeylineBinding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leyline Binding", 387, Rarity.RARE, mage.cards.l.LeylineBinding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Strike", 137, Rarity.COMMON, mage.cards.l.LightningStrike.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Strike", 433, Rarity.COMMON, mage.cards.l.LightningStrike.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana of the Veil", 373, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana of the Veil", 97, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Linebreaker Baloth", 168, Rarity.UNCOMMON, mage.cards.l.LinebreakerBaloth.class));
        cards.add(new SetCardInfo("Llanowar Greenwidow", 169, Rarity.RARE, mage.cards.l.LlanowarGreenwidow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Greenwidow", 415, Rarity.RARE, mage.cards.l.LlanowarGreenwidow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Loamspeaker", 170, Rarity.RARE, mage.cards.l.LlanowarLoamspeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Loamspeaker", 416, Rarity.RARE, mage.cards.l.LlanowarLoamspeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Loamspeaker", 428, Rarity.RARE, mage.cards.l.LlanowarLoamspeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Stalker", 171, Rarity.COMMON, mage.cards.l.LlanowarStalker.class));
        cards.add(new SetCardInfo("Love Song of Night and Day", 25, Rarity.UNCOMMON, mage.cards.l.LoveSongOfNightAndDay.class));
        cards.add(new SetCardInfo("Magnigoth Sentry", 172, Rarity.COMMON, mage.cards.m.MagnigothSentry.class));
        cards.add(new SetCardInfo("Meria's Outrider", 138, Rarity.COMMON, mage.cards.m.MeriasOutrider.class));
        cards.add(new SetCardInfo("Meria, Scholar of Antiquity", 206, Rarity.RARE, mage.cards.m.MeriaScholarOfAntiquity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meria, Scholar of Antiquity", 305, Rarity.RARE, mage.cards.m.MeriaScholarOfAntiquity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meria, Scholar of Antiquity", 346, Rarity.RARE, mage.cards.m.MeriaScholarOfAntiquity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mesa Cavalier", 26, Rarity.COMMON, mage.cards.m.MesaCavalier.class));
        cards.add(new SetCardInfo("Meteorite", 235, Rarity.COMMON, mage.cards.m.Meteorite.class));
        cards.add(new SetCardInfo("Micromancer", 431, Rarity.UNCOMMON, mage.cards.m.Micromancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Micromancer", 57, Rarity.UNCOMMON, mage.cards.m.Micromancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Molten Monstrosity", 139, Rarity.COMMON, mage.cards.m.MoltenMonstrosity.class));
        cards.add(new SetCardInfo("Molten Tributary", 251, Rarity.COMMON, mage.cards.m.MoltenTributary.class));
        cards.add(new SetCardInfo("Monstrous War-Leech", 98, Rarity.UNCOMMON, mage.cards.m.MonstrousWarLeech.class));
        cards.add(new SetCardInfo("Mossbeard Ancient", 173, Rarity.UNCOMMON, mage.cards.m.MossbeardAncient.class));
        cards.add(new SetCardInfo("Mountain", 271, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 272, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 273, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 280, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Nael, Avizoa Aeronaut", 207, Rarity.UNCOMMON, mage.cards.n.NaelAvizoaAeronaut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nael, Avizoa Aeronaut", 306, Rarity.UNCOMMON, mage.cards.n.NaelAvizoaAeronaut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nael, Avizoa Aeronaut", 347, Rarity.UNCOMMON, mage.cards.n.NaelAvizoaAeronaut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Najal, the Storm Runner", 208, Rarity.UNCOMMON, mage.cards.n.NajalTheStormRunner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Najal, the Storm Runner", 307, Rarity.UNCOMMON, mage.cards.n.NajalTheStormRunner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Najal, the Storm Runner", 348, Rarity.UNCOMMON, mage.cards.n.NajalTheStormRunner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Negate", 58, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nemata, Primeval Warden", 209, Rarity.RARE, mage.cards.n.NemataPrimevalWarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nemata, Primeval Warden", 308, Rarity.RARE, mage.cards.n.NemataPrimevalWarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nemata, Primeval Warden", 349, Rarity.RARE, mage.cards.n.NemataPrimevalWarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nishoba Brawler", 174, Rarity.UNCOMMON, mage.cards.n.NishobaBrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nishoba Brawler", 434, Rarity.UNCOMMON, mage.cards.n.NishobaBrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phoenix Chick", 140, Rarity.UNCOMMON, mage.cards.p.PhoenixChick.class));
        cards.add(new SetCardInfo("Phyrexian Espionage", 60, Rarity.COMMON, mage.cards.p.PhyrexianEspionage.class));
        cards.add(new SetCardInfo("Phyrexian Missionary", 27, Rarity.UNCOMMON, mage.cards.p.PhyrexianMissionary.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 99, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Phyrexian Vivisector", 100, Rarity.COMMON, mage.cards.p.PhyrexianVivisector.class));
        cards.add(new SetCardInfo("Phyrexian Warhorse", 101, Rarity.COMMON, mage.cards.p.PhyrexianWarhorse.class));
        cards.add(new SetCardInfo("Pilfer", 102, Rarity.UNCOMMON, mage.cards.p.Pilfer.class));
        cards.add(new SetCardInfo("Pixie Illusionist", 61, Rarity.COMMON, mage.cards.p.PixieIllusionist.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 263, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 264, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 277, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plaza of Heroes", 252, Rarity.RARE, mage.cards.p.PlazaOfHeroes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plaza of Heroes", 421, Rarity.RARE, mage.cards.p.PlazaOfHeroes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prayer of Binding", 28, Rarity.UNCOMMON, mage.cards.p.PrayerOfBinding.class));
        cards.add(new SetCardInfo("Protect the Negotiators", 62, Rarity.UNCOMMON, mage.cards.p.ProtectTheNegotiators.class));
        cards.add(new SetCardInfo("Queen Allenal of Ruadach", 210, Rarity.UNCOMMON, mage.cards.q.QueenAllenalOfRuadach.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Queen Allenal of Ruadach", 309, Rarity.UNCOMMON, mage.cards.q.QueenAllenalOfRuadach.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Queen Allenal of Ruadach", 350, Rarity.UNCOMMON, mage.cards.q.QueenAllenalOfRuadach.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quirion Beastcaller", 175, Rarity.RARE, mage.cards.q.QuirionBeastcaller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quirion Beastcaller", 417, Rarity.RARE, mage.cards.q.QuirionBeastcaller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radha's Firebrand", 141, Rarity.RARE, mage.cards.r.RadhasFirebrand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radha's Firebrand", 408, Rarity.RARE, mage.cards.r.RadhasFirebrand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radha, Coalition Warlord", 211, Rarity.UNCOMMON, mage.cards.r.RadhaCoalitionWarlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radha, Coalition Warlord", 310, Rarity.UNCOMMON, mage.cards.r.RadhaCoalitionWarlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radha, Coalition Warlord", 351, Rarity.UNCOMMON, mage.cards.r.RadhaCoalitionWarlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radiant Grove", 253, Rarity.COMMON, mage.cards.r.RadiantGrove.class));
        cards.add(new SetCardInfo("Raff, Weatherlight Stalwart", 212, Rarity.UNCOMMON, mage.cards.r.RaffWeatherlightStalwart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raff, Weatherlight Stalwart", 311, Rarity.UNCOMMON, mage.cards.r.RaffWeatherlightStalwart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raff, Weatherlight Stalwart", 352, Rarity.UNCOMMON, mage.cards.r.RaffWeatherlightStalwart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ragefire Hellkite", 285, Rarity.RARE, mage.cards.r.RagefireHellkite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ragefire Hellkite", 426, Rarity.RARE, mage.cards.r.RagefireHellkite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ratadrabik of Urborg", 213, Rarity.RARE, mage.cards.r.RatadrabikOfUrborg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ratadrabik of Urborg", 312, Rarity.RARE, mage.cards.r.RatadrabikOfUrborg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ratadrabik of Urborg", 353, Rarity.RARE, mage.cards.r.RatadrabikOfUrborg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relic of Legends", 236, Rarity.UNCOMMON, mage.cards.r.RelicOfLegends.class));
        cards.add(new SetCardInfo("Resolute Reinforcements", 29, Rarity.UNCOMMON, mage.cards.r.ResoluteReinforcements.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Resolute Reinforcements", 430, Rarity.UNCOMMON, mage.cards.r.ResoluteReinforcements.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rith, Liberated Primeval", 214, Rarity.MYTHIC, mage.cards.r.RithLiberatedPrimeval.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rith, Liberated Primeval", 313, Rarity.MYTHIC, mage.cards.r.RithLiberatedPrimeval.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rith, Liberated Primeval", 354, Rarity.MYTHIC, mage.cards.r.RithLiberatedPrimeval.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rivaz of the Claw", 215, Rarity.RARE, mage.cards.r.RivazOfTheClaw.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rivaz of the Claw", 314, Rarity.RARE, mage.cards.r.RivazOfTheClaw.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rivaz of the Claw", 355, Rarity.RARE, mage.cards.r.RivazOfTheClaw.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rona's Vortex", 63, Rarity.UNCOMMON, mage.cards.r.RonasVortex.class));
        cards.add(new SetCardInfo("Rona, Sheoldred's Faithful", 216, Rarity.UNCOMMON, mage.cards.r.RonaSheoldredsFaithful.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rona, Sheoldred's Faithful", 315, Rarity.UNCOMMON, mage.cards.r.RonaSheoldredsFaithful.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rona, Sheoldred's Faithful", 356, Rarity.UNCOMMON, mage.cards.r.RonaSheoldredsFaithful.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rulik Mons, Warren Chief", 217, Rarity.UNCOMMON, mage.cards.r.RulikMonsWarrenChief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rulik Mons, Warren Chief", 316, Rarity.UNCOMMON, mage.cards.r.RulikMonsWarrenChief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rulik Mons, Warren Chief", 357, Rarity.UNCOMMON, mage.cards.r.RulikMonsWarrenChief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rundvelt Hordemaster", 142, Rarity.RARE, mage.cards.r.RundveltHordemaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rundvelt Hordemaster", 409, Rarity.RARE, mage.cards.r.RundveltHordemaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Runic Shot", 30, Rarity.UNCOMMON, mage.cards.r.RunicShot.class));
        cards.add(new SetCardInfo("Sacred Peaks", 254, Rarity.COMMON, mage.cards.s.SacredPeaks.class));
        cards.add(new SetCardInfo("Salvaged Manaworker", 237, Rarity.COMMON, mage.cards.s.SalvagedManaworker.class));
        cards.add(new SetCardInfo("Samite Herbalist", 31, Rarity.COMMON, mage.cards.s.SamiteHerbalist.class));
        cards.add(new SetCardInfo("Scout the Wilderness", 176, Rarity.COMMON, mage.cards.s.ScoutTheWilderness.class));
        cards.add(new SetCardInfo("Sengir Connoisseur", 104, Rarity.UNCOMMON, mage.cards.s.SengirConnoisseur.class));
        cards.add(new SetCardInfo("Serra Paragon", 32, Rarity.MYTHIC, mage.cards.s.SerraParagon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Paragon", 388, Rarity.MYTHIC, mage.cards.s.SerraParagon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Redeemer", 282, Rarity.RARE, mage.cards.s.SerraRedeemer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Redeemer", 423, Rarity.RARE, mage.cards.s.SerraRedeemer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shadow Prophecy", 105, Rarity.COMMON, mage.cards.s.ShadowProphecy.class));
        cards.add(new SetCardInfo("Shadow-Rite Priest", 106, Rarity.RARE, mage.cards.s.ShadowRitePriest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shadow-Rite Priest", 403, Rarity.RARE, mage.cards.s.ShadowRitePriest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shalai's Acolyte", 33, Rarity.UNCOMMON, mage.cards.s.ShalaisAcolyte.class));
        cards.add(new SetCardInfo("Shanna, Purifying Blade", 218, Rarity.MYTHIC, mage.cards.s.ShannaPurifyingBlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shanna, Purifying Blade", 317, Rarity.MYTHIC, mage.cards.s.ShannaPurifyingBlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shanna, Purifying Blade", 358, Rarity.MYTHIC, mage.cards.s.ShannaPurifyingBlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sheoldred's Restoration", 108, Rarity.UNCOMMON, mage.cards.s.SheoldredsRestoration.class));
        cards.add(new SetCardInfo("Sheoldred, the Apocalypse", 107, Rarity.MYTHIC, mage.cards.s.SheoldredTheApocalypse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sheoldred, the Apocalypse", 290, Rarity.MYTHIC, mage.cards.s.SheoldredTheApocalypse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sheoldred, the Apocalypse", 331, Rarity.MYTHIC, mage.cards.s.SheoldredTheApocalypse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sheoldred, the Apocalypse", 369, Rarity.MYTHIC, mage.cards.s.SheoldredTheApocalypse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shield-Wall Sentinel", 238, Rarity.COMMON, mage.cards.s.ShieldWallSentinel.class));
        cards.add(new SetCardInfo("Shivan Devastator", 143, Rarity.MYTHIC, mage.cards.s.ShivanDevastator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shivan Devastator", 410, Rarity.MYTHIC, mage.cards.s.ShivanDevastator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shivan Reef", 255, Rarity.RARE, mage.cards.s.ShivanReef.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shivan Reef", 380, Rarity.RARE, mage.cards.s.ShivanReef.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shore Up", 64, Rarity.COMMON, mage.cards.s.ShoreUp.class));
        cards.add(new SetCardInfo("Silver Scrutiny", 395, Rarity.RARE, mage.cards.s.SilverScrutiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silver Scrutiny", 65, Rarity.RARE, mage.cards.s.SilverScrutiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverback Elder", 177, Rarity.MYTHIC, mage.cards.s.SilverbackElder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverback Elder", 418, Rarity.MYTHIC, mage.cards.s.SilverbackElder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slimefoot's Survey", 178, Rarity.UNCOMMON, mage.cards.s.SlimefootsSurvey.class));
        cards.add(new SetCardInfo("Smash to Dust", 144, Rarity.COMMON, mage.cards.s.SmashToDust.class));
        cards.add(new SetCardInfo("Snarespinner", 179, Rarity.COMMON, mage.cards.s.Snarespinner.class));
        cards.add(new SetCardInfo("Soaring Drake", 66, Rarity.COMMON, mage.cards.s.SoaringDrake.class));
        cards.add(new SetCardInfo("Sol'Kanar the Tainted", 219, Rarity.MYTHIC, mage.cards.s.SolKanarTheTainted.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sol'Kanar the Tainted", 318, Rarity.MYTHIC, mage.cards.s.SolKanarTheTainted.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sol'Kanar the Tainted", 359, Rarity.MYTHIC, mage.cards.s.SolKanarTheTainted.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul of Windgrace", 220, Rarity.MYTHIC, mage.cards.s.SoulOfWindgrace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul of Windgrace", 319, Rarity.MYTHIC, mage.cards.s.SoulOfWindgrace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul of Windgrace", 360, Rarity.MYTHIC, mage.cards.s.SoulOfWindgrace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sphinx of Clear Skies", 396, Rarity.MYTHIC, mage.cards.s.SphinxOfClearSkies.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sphinx of Clear Skies", 67, Rarity.MYTHIC, mage.cards.s.SphinxOfClearSkies.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Splatter Goblin", 109, Rarity.COMMON, mage.cards.s.SplatterGoblin.class));
        cards.add(new SetCardInfo("Sprouting Goblin", 145, Rarity.UNCOMMON, mage.cards.s.SproutingGoblin.class));
        cards.add(new SetCardInfo("Squee, Dubious Monarch", 146, Rarity.RARE, mage.cards.s.SqueeDubiousMonarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Squee, Dubious Monarch", 291, Rarity.RARE, mage.cards.s.SqueeDubiousMonarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Squee, Dubious Monarch", 332, Rarity.RARE, mage.cards.s.SqueeDubiousMonarch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stall for Time", 34, Rarity.COMMON, mage.cards.s.StallForTime.class));
        cards.add(new SetCardInfo("Stenn, Paranoid Partisan", 221, Rarity.RARE, mage.cards.s.StennParanoidPartisan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stenn, Paranoid Partisan", 320, Rarity.RARE, mage.cards.s.StennParanoidPartisan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stenn, Paranoid Partisan", 361, Rarity.RARE, mage.cards.s.StennParanoidPartisan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strength of the Coalition", 180, Rarity.UNCOMMON, mage.cards.s.StrengthOfTheCoalition.class));
        cards.add(new SetCardInfo("Stronghold Arena", 110, Rarity.RARE, mage.cards.s.StrongholdArena.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stronghold Arena", 404, Rarity.RARE, mage.cards.s.StrongholdArena.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfurous Springs", 256, Rarity.RARE, mage.cards.s.SulfurousSprings.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfurous Springs", 381, Rarity.RARE, mage.cards.s.SulfurousSprings.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunbathing Rootwalla", 181, Rarity.COMMON, mage.cards.s.SunbathingRootwalla.class));
        cards.add(new SetCardInfo("Sunlit Marsh", 257, Rarity.COMMON, mage.cards.s.SunlitMarsh.class));
        cards.add(new SetCardInfo("Swamp", 268, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 269, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 279, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Tail Swipe", 182, Rarity.UNCOMMON, mage.cards.t.TailSwipe.class));
        cards.add(new SetCardInfo("Take Up the Shield", 35, Rarity.COMMON, mage.cards.t.TakeUpTheShield.class));
        cards.add(new SetCardInfo("Talas Lookout", 68, Rarity.COMMON, mage.cards.t.TalasLookout.class));
        cards.add(new SetCardInfo("Tangled Islet", 258, Rarity.COMMON, mage.cards.t.TangledIslet.class));
        cards.add(new SetCardInfo("Tattered Apparition", 111, Rarity.COMMON, mage.cards.t.TatteredApparition.class));
        cards.add(new SetCardInfo("Tatyova, Steward of Tides", 222, Rarity.UNCOMMON, mage.cards.t.TatyovaStewardOfTides.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tatyova, Steward of Tides", 321, Rarity.UNCOMMON, mage.cards.t.TatyovaStewardOfTides.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tatyova, Steward of Tides", 362, Rarity.UNCOMMON, mage.cards.t.TatyovaStewardOfTides.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tear Asunder", 183, Rarity.UNCOMMON, mage.cards.t.TearAsunder.class));
        cards.add(new SetCardInfo("Temporal Firestorm", 147, Rarity.RARE, mage.cards.t.TemporalFirestorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temporal Firestorm", 411, Rarity.RARE, mage.cards.t.TemporalFirestorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temporary Lockdown", 36, Rarity.RARE, mage.cards.t.TemporaryLockdown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temporary Lockdown", 389, Rarity.RARE, mage.cards.t.TemporaryLockdown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Territorial Maro", 184, Rarity.UNCOMMON, mage.cards.t.TerritorialMaro.class));
        cards.add(new SetCardInfo("The Cruelty of Gix", 87, Rarity.RARE, mage.cards.t.TheCrueltyOfGix.class));
        cards.add(new SetCardInfo("The Elder Dragon War", 121, Rarity.RARE, mage.cards.t.TheElderDragonWar.class));
        cards.add(new SetCardInfo("The Phasing of Zhalfir", 59, Rarity.RARE, mage.cards.t.ThePhasingOfZhalfir.class));
        cards.add(new SetCardInfo("The Raven Man", 103, Rarity.RARE, mage.cards.t.TheRavenMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Raven Man", 289, Rarity.RARE, mage.cards.t.TheRavenMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Raven Man", 330, Rarity.RARE, mage.cards.t.TheRavenMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Weatherseed Treaty", 188, Rarity.UNCOMMON, mage.cards.t.TheWeatherseedTreaty.class));
        cards.add(new SetCardInfo("The World Spell", 189, Rarity.MYTHIC, mage.cards.t.TheWorldSpell.class));
        cards.add(new SetCardInfo("Thran Portal", 259, Rarity.RARE, mage.cards.t.ThranPortal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thran Portal", 422, Rarity.RARE, mage.cards.t.ThranPortal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Threats Undetected", 185, Rarity.RARE, mage.cards.t.ThreatsUndetected.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Threats Undetected", 419, Rarity.RARE, mage.cards.t.ThreatsUndetected.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrill of Possibility", 148, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Tidepool Turtle", 69, Rarity.COMMON, mage.cards.t.TidepoolTurtle.class));
        cards.add(new SetCardInfo("Timeless Lotus", 239, Rarity.MYTHIC, mage.cards.t.TimelessLotus.class));
        cards.add(new SetCardInfo("Timely Interference", 70, Rarity.COMMON, mage.cards.t.TimelyInterference.class));
        cards.add(new SetCardInfo("Tolarian Geyser", 71, Rarity.COMMON, mage.cards.t.TolarianGeyser.class));
        cards.add(new SetCardInfo("Tolarian Terror", 72, Rarity.COMMON, mage.cards.t.TolarianTerror.class));
        cards.add(new SetCardInfo("Tori D'Avenant, Fury Rider", 223, Rarity.UNCOMMON, mage.cards.t.ToriDAvenantFuryRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tori D'Avenant, Fury Rider", 322, Rarity.UNCOMMON, mage.cards.t.ToriDAvenantFuryRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tori D'Avenant, Fury Rider", 363, Rarity.UNCOMMON, mage.cards.t.ToriDAvenantFuryRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Toxic Abomination", 112, Rarity.COMMON, mage.cards.t.ToxicAbomination.class));
        cards.add(new SetCardInfo("Tribute to Urborg", 113, Rarity.COMMON, mage.cards.t.TributeToUrborg.class));
        cards.add(new SetCardInfo("Tura Kennerud, Skyknight", 224, Rarity.UNCOMMON, mage.cards.t.TuraKennerudSkyknight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tura Kennerud, Skyknight", 323, Rarity.UNCOMMON, mage.cards.t.TuraKennerudSkyknight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tura Kennerud, Skyknight", 364, Rarity.UNCOMMON, mage.cards.t.TuraKennerudSkyknight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Twinferno", 149, Rarity.UNCOMMON, mage.cards.t.Twinferno.class));
        cards.add(new SetCardInfo("Tyrannical Pitlord", 284, Rarity.RARE, mage.cards.t.TyrannicalPitlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tyrannical Pitlord", 425, Rarity.RARE, mage.cards.t.TyrannicalPitlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urborg Lhurgoyf", 186, Rarity.RARE, mage.cards.u.UrborgLhurgoyf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urborg Lhurgoyf", 420, Rarity.RARE, mage.cards.u.UrborgLhurgoyf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urborg Repossession", 114, Rarity.COMMON, mage.cards.u.UrborgRepossession.class));
        cards.add(new SetCardInfo("Urza Assembles the Titans", 37, Rarity.RARE, mage.cards.u.UrzaAssemblesTheTitans.class));
        cards.add(new SetCardInfo("Uurg, Spawn of Turg", 225, Rarity.UNCOMMON, mage.cards.u.UurgSpawnOfTurg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uurg, Spawn of Turg", 324, Rarity.UNCOMMON, mage.cards.u.UurgSpawnOfTurg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uurg, Spawn of Turg", 365, Rarity.UNCOMMON, mage.cards.u.UurgSpawnOfTurg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valiant Veteran", 38, Rarity.RARE, mage.cards.v.ValiantVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valiant Veteran", 390, Rarity.RARE, mage.cards.v.ValiantVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanquisher's Axe", 240, Rarity.COMMON, mage.cards.v.VanquishersAxe.class));
        cards.add(new SetCardInfo("Vesuvan Duplimancy", 397, Rarity.MYTHIC, mage.cards.v.VesuvanDuplimancy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vesuvan Duplimancy", 73, Rarity.MYTHIC, mage.cards.v.VesuvanDuplimancy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Viashino Branchrider", 150, Rarity.COMMON, mage.cards.v.ViashinoBranchrider.class));
        cards.add(new SetCardInfo("Vineshaper Prodigy", 187, Rarity.COMMON, mage.cards.v.VineshaperProdigy.class));
        cards.add(new SetCardInfo("Voda Sea Scavenger", 74, Rarity.COMMON, mage.cards.v.VodaSeaScavenger.class));
        cards.add(new SetCardInfo("Vodalian Hexcatcher", 398, Rarity.RARE, mage.cards.v.VodalianHexcatcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vodalian Hexcatcher", 75, Rarity.RARE, mage.cards.v.VodalianHexcatcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vodalian Mindsinger", 399, Rarity.RARE, mage.cards.v.VodalianMindsinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vodalian Mindsinger", 76, Rarity.RARE, mage.cards.v.VodalianMindsinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vohar, Vodalian Desecrator", 226, Rarity.UNCOMMON, mage.cards.v.VoharVodalianDesecrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vohar, Vodalian Desecrator", 325, Rarity.UNCOMMON, mage.cards.v.VoharVodalianDesecrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vohar, Vodalian Desecrator", 366, Rarity.UNCOMMON, mage.cards.v.VoharVodalianDesecrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Volshe Tideturner", 77, Rarity.COMMON, mage.cards.v.VolsheTideturner.class));
        cards.add(new SetCardInfo("Walking Bulwark", 241, Rarity.UNCOMMON, mage.cards.w.WalkingBulwark.class));
        cards.add(new SetCardInfo("Warhost's Frenzy", 151, Rarity.UNCOMMON, mage.cards.w.WarhostsFrenzy.class));
        cards.add(new SetCardInfo("Weatherlight Compleated", 242, Rarity.MYTHIC, mage.cards.w.WeatherlightCompleated.class));
        cards.add(new SetCardInfo("Wingmantle Chaplain", 39, Rarity.UNCOMMON, mage.cards.w.WingmantleChaplain.class));
        cards.add(new SetCardInfo("Wooded Ridgeline", 260, Rarity.COMMON, mage.cards.w.WoodedRidgeline.class));
        cards.add(new SetCardInfo("Writhing Necromass", 115, Rarity.COMMON, mage.cards.w.WrithingNecromass.class));
        cards.add(new SetCardInfo("Yavimaya Coast", 261, Rarity.RARE, mage.cards.y.YavimayaCoast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yavimaya Coast", 382, Rarity.RARE, mage.cards.y.YavimayaCoast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yavimaya Iconoclast", 190, Rarity.UNCOMMON, mage.cards.y.YavimayaIconoclast.class));
        cards.add(new SetCardInfo("Yavimaya Sojourner", 191, Rarity.COMMON, mage.cards.y.YavimayaSojourner.class));
        cards.add(new SetCardInfo("Yavimaya Steelcrusher", 152, Rarity.COMMON, mage.cards.y.YavimayaSteelcrusher.class));
        cards.add(new SetCardInfo("Yotia Declares War", 153, Rarity.UNCOMMON, mage.cards.y.YotiaDeclaresWar.class));
        cards.add(new SetCardInfo("Zar Ojanen, Scion of Efrava", 227, Rarity.UNCOMMON, mage.cards.z.ZarOjanenScionOfEfrava.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zar Ojanen, Scion of Efrava", 326, Rarity.UNCOMMON, mage.cards.z.ZarOjanenScionOfEfrava.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zar Ojanen, Scion of Efrava", 367, Rarity.UNCOMMON, mage.cards.z.ZarOjanenScionOfEfrava.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zur, Eternal Schemer", 228, Rarity.MYTHIC, mage.cards.z.ZurEternalSchemer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zur, Eternal Schemer", 327, Rarity.MYTHIC, mage.cards.z.ZurEternalSchemer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zur, Eternal Schemer", 368, Rarity.MYTHIC, mage.cards.z.ZurEternalSchemer.class, NON_FULL_USE_VARIOUS));
    }

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findSpecialCardsByRarity(rarity);
        cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria()
                .setCodes(this.code)
                .rarities(rarity)
                .supertypes(SuperType.LEGENDARY)
                .types(CardType.CREATURE)
                .maxCardNumber(maxCardNumberInBooster)));
        return cardInfos;
    }

    @Override
    public BoosterCollator createCollator() {
        return new DominariaUnitedCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/dmu.html
// Using Japanese collation for common/uncommon, rare collation inferred from other sets
class DominariaUnitedCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "6", "137", "5", "113", "22", "172", "10", "94", "31", "181", "35", "99", "34", "68", "20", "124", "8", "100", "9", "260", "26", "229", "4", "258", "18", "254", "11", "22", "94", "35", "172", "10", "99", "31", "68", "6", "113", "5", "137", "8", "100", "34", "124", "20", "181", "9", "260", "26", "258", "11", "254", "4", "229", "18", "10", "99", "31", "68", "20", "113", "8", "172", "22", "124", "6", "100", "35", "137", "34", "181", "5", "94", "11", "229", "9", "258", "4", "254", "26", "260", "18", "22", "113", "5", "172", "6", "137", "31", "99", "35", "181", "10", "94", "20", "68", "8", "100", "34", "124", "4", "260", "9", "254", "18", "229", "11", "258", "26");
    private final CardRun commonB = new CardRun(true, "64", "101", "54", "257", "114", "72", "58", "95", "49", "74", "109", "70", "92", "17", "61", "111", "53", "41", "105", "247", "77", "80", "64", "240", "83", "60", "249", "78", "54", "237", "101", "72", "115", "55", "245", "112", "71", "253", "114", "69", "81", "66", "257", "109", "58", "92", "70", "111", "74", "17", "105", "61", "247", "80", "77", "41", "95", "60", "55", "115", "237", "49", "83", "53", "240", "78", "71", "69", "112", "253", "54", "101", "249", "64", "81", "257", "66", "109", "72", "245", "114", "74", "61", "92", "53", "58", "111", "247", "49", "95", "41", "80", "70", "60", "105", "17", "77", "115", "249", "66", "83", "240", "69", "78", "237", "71", "112", "245", "55", "81", "253");
    private final CardRun commonC = new CardRun(true, "118", "191", "129", "156", "7", "152", "154", "132", "176", "127", "238", "157", "139", "13", "166", "138", "150", "158", "136", "235", "159", "128", "187", "248", "134", "171", "144", "179", "246", "126", "163", "148", "164", "125", "191", "238", "129", "176", "118", "156", "251", "152", "161", "132", "155", "154", "127", "7", "166", "150", "136", "158", "235", "139", "159", "248", "138", "187", "13", "134", "157", "251", "125", "179", "148", "171", "191", "128", "163", "246", "144", "155", "129", "161", "154", "118", "164", "7", "126", "156", "238", "152", "176", "139", "127", "166", "235", "150", "157", "132", "158", "138", "13", "187", "134", "179", "248", "136", "159", "144", "171", "128", "246", "163", "125", "164", "161", "148", "126", "155", "251");
    private final CardRun uncommonLegendA = new CardRun(true, "223", "299", "195", "227", "211", "316", "212", "304", "222", "296", "200", "225", "292", "208", "309", "198", "306", "217", "197", "224", "205", "216", "321", "193", "297", "210", "310", "196", "212", "227", "207", "223", "208", "222", "294", "211", "200", "226", "198", "322", "225", "196", "216", "205", "217", "311", "195", "307", "224", "193", "315", "197", "210", "207", "226", "295");
    private final CardRun uncommonLegendB = new CardRun(false, "323", "324", "325", "326");
    private final CardRun uncommonC = new CardRun(false, "43", "47", "236", "241");
    private final CardRun uncommonD = new CardRun(true, "50", "98", "149", "12", "56", "86", "29", "63", "102", "231", "44", "88", "45", "82", "140", "51", "153", "57", "108", "233", "48", "104", "232", "85", "50", "96", "62", "12", "56", "89", "149", "79", "45", "98", "29", "86", "231", "102", "63", "140", "88", "44", "82", "153", "104", "51", "96", "48", "233", "108", "12", "232", "85", "62", "98", "50", "89", "44", "79", "57", "29", "231", "108", "63", "149", "86", "45", "88", "56", "96", "140", "82", "48", "233", "102", "51", "153", "104", "62", "79", "50", "98", "57", "232", "89", "44", "12", "149", "85", "29", "86", "56", "231", "108", "63", "102", "233", "45", "88", "140", "82", "153", "51", "96", "48", "85", "57", "89", "62", "104", "232", "79");
    private final CardRun uncommonE = new CardRun(true, "178", "116", "188", "28", "183", "30", "162", "123", "39", "182", "14", "145", "23", "151", "180", "131", "168", "25", "173", "122", "174", "27", "184", "130", "178", "33", "190", "120", "183", "21", "188", "28", "116", "30", "145", "14", "182", "23", "123", "162", "39", "131", "25", "180", "151", "168", "122", "190", "130", "178", "120", "184", "27", "188", "33", "174", "30", "173", "21", "182", "39", "162", "25", "183", "28", "116", "14", "123", "23", "131", "180", "151", "168", "145", "190", "122", "178", "120", "184", "27", "130", "174", "33", "173", "28", "188", "21", "183", "30", "182", "116", "39", "162", "123", "14", "151", "180", "131", "23", "145", "25", "122", "168", "130", "173", "27", "184", "33", "174", "120", "190", "21");
    private final CardRun rare = new CardRun(false, "2", "3", "16", "19", "24", "36", "37", "38", "40", "42", "46", "52", "59", "65", "75", "76", "87", "90", "91", "93", "106", "110", "117", "119", "121", "135", "141", "142", "147", "160", "165", "167", "169", "170", "175", "185", "186", "230", "243", "244", "250", "252", "255", "256", "259", "261", "2", "3", "16", "19", "24", "36", "37", "38", "40", "42", "46", "52", "59", "65", "75", "76", "87", "90", "91", "93", "106", "110", "117", "119", "121", "135", "141", "142", "147", "160", "165", "167", "169", "170", "175", "185", "186", "230", "243", "244", "250", "252", "255", "256", "259", "261", "1", "32", "67", "73", "97", "133", "143", "177", "189", "192", "234", "239", "242");
    private final CardRun rareLegend = new CardRun(false, "15", "84", "103", "146", "194", "199", "201", "202", "204", "206", "209", "213", "215", "221", "15", "84", "103", "146", "194", "199", "201", "202", "204", "206", "209", "213", "215", "221", "107", "203", "214", "218", "219", "220", "228");
    private final CardRun land = new CardRun(false, "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279", "280", "281");

    private final BoosterStructure AABBBBCCCC = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure AAABBBCCCC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure AAABBBBCCC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );

    private final BoosterStructure ADD = new BoosterStructure(uncommonLegendA, uncommonD, uncommonD, rare);
    private final BoosterStructure ADE = new BoosterStructure(uncommonLegendA, uncommonD, uncommonE, rare);
    private final BoosterStructure BDD = new BoosterStructure(uncommonLegendB, uncommonD, uncommonD, rare);
    private final BoosterStructure CEE = new BoosterStructure(uncommonC, uncommonE, uncommonE, rareLegend);
    private final BoosterStructure DDE = new BoosterStructure(uncommonD, uncommonD, uncommonE, rareLegend);
    private final BoosterStructure DEE = new BoosterStructure(uncommonD, uncommonE, uncommonE, rareLegend);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.67 A commons (270 / 101)
    // 3.66 B commons (370 / 101)
    // 3.66 C commons (370 / 101)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC
    );

    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.70 A uncommons (14 / 20)
    // 0.05 B uncommons ( 1 / 20)
    // 0.15 C uncommons ( 3 / 20)
    // 1.05 D uncommons (21 / 20)
    // 1.05 E uncommons (21 / 20)
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ADD, ADD, ADE, ADE, ADE, ADE, ADE, ADE, ADE, ADE, ADE, ADE, ADE, ADE, BDD,
            CEE, CEE, CEE, DDE, DEE
    );
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
