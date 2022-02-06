package mage.sets;

import mage.cards.Card;
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
public final class InnistradCrimsonVow extends ExpansionSet {

    private static final InnistradCrimsonVow instance = new InnistradCrimsonVow();

    public static InnistradCrimsonVow getInstance() {
        return instance;
    }

    private InnistradCrimsonVow() {
        super("Innistrad: Crimson Vow", "VOW", ExpansionSet.buildDate(2021, 11, 19), SetType.EXPANSION);
        this.blockName = "Innistrad: Midnight Hunt";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialRare = 5.5;
        this.ratioBoosterSpecialMythic = 5.4;   // 5 mythic DFCs, 11 rare DFCs
        this.numBoosterDoubleFaced = 1;
        this.maxCardNumberInBooster = 277;

        cards.add(new SetCardInfo("Abrade", 139, Rarity.COMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Adamant Will", 1, Rarity.COMMON, mage.cards.a.AdamantWill.class));
        cards.add(new SetCardInfo("Aim for the Head", 92, Rarity.COMMON, mage.cards.a.AimForTheHead.class));
        cards.add(new SetCardInfo("Alchemist's Gambit", 140, Rarity.RARE, mage.cards.a.AlchemistsGambit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alchemist's Gambit", 374, Rarity.RARE, mage.cards.a.AlchemistsGambit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alchemist's Retrieval", 47, Rarity.COMMON, mage.cards.a.AlchemistsRetrieval.class));
        cards.add(new SetCardInfo("Alluring Suitor", 141, Rarity.UNCOMMON, mage.cards.a.AlluringSuitor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alluring Suitor", 300, Rarity.UNCOMMON, mage.cards.a.AlluringSuitor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancestor's Embrace", 22, Rarity.COMMON, mage.cards.a.AncestorsEmbrace.class));
        cards.add(new SetCardInfo("Ancestral Anger", 142, Rarity.COMMON, mage.cards.a.AncestralAnger.class));
        cards.add(new SetCardInfo("Ancient Lumberknot", 230, Rarity.UNCOMMON, mage.cards.a.AncientLumberknot.class));
        cards.add(new SetCardInfo("Angelic Quartermaster", 2, Rarity.UNCOMMON, mage.cards.a.AngelicQuartermaster.class));
        cards.add(new SetCardInfo("Anje, Maid of Dishonor", 231, Rarity.RARE, mage.cards.a.AnjeMaidOfDishonor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anje, Maid of Dishonor", 309, Rarity.RARE, mage.cards.a.AnjeMaidOfDishonor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Apprentice Sharpshooter", 185, Rarity.COMMON, mage.cards.a.ApprenticeSharpshooter.class));
        cards.add(new SetCardInfo("Archghoul of Thraben", 93, Rarity.UNCOMMON, mage.cards.a.ArchghoulOfThraben.class));
        cards.add(new SetCardInfo("Arm the Cathars", 3, Rarity.UNCOMMON, mage.cards.a.ArmTheCathars.class));
        cards.add(new SetCardInfo("Ascendant Packleader", 186, Rarity.RARE, mage.cards.a.AscendantPackleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ascendant Packleader", 383, Rarity.RARE, mage.cards.a.AscendantPackleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avabruck Caretaker", 187, Rarity.MYTHIC, mage.cards.a.AvabruckCaretaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avabruck Caretaker", 384, Rarity.MYTHIC, mage.cards.a.AvabruckCaretaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ballista Watcher", 143, Rarity.UNCOMMON, mage.cards.b.BallistaWatcher.class));
        cards.add(new SetCardInfo("Ballista Wielder", 143, Rarity.UNCOMMON, mage.cards.b.BallistaWielder.class));
        cards.add(new SetCardInfo("Belligerent Guest", 144, Rarity.COMMON, mage.cards.b.BelligerentGuest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Belligerent Guest", 301, Rarity.COMMON, mage.cards.b.BelligerentGuest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Binding Geist", 48, Rarity.COMMON, mage.cards.b.BindingGeist.class));
        cards.add(new SetCardInfo("Biolume Egg", 49, Rarity.UNCOMMON, mage.cards.b.BiolumeEgg.class));
        cards.add(new SetCardInfo("Biolume Serpent", 49, Rarity.UNCOMMON, mage.cards.b.BiolumeSerpent.class));
        cards.add(new SetCardInfo("Bleed Dry", 94, Rarity.COMMON, mage.cards.b.BleedDry.class));
        cards.add(new SetCardInfo("Blood Fountain", 95, Rarity.COMMON, mage.cards.b.BloodFountain.class));
        cards.add(new SetCardInfo("Blood Hypnotist", 145, Rarity.UNCOMMON, mage.cards.b.BloodHypnotist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Hypnotist", 302, Rarity.UNCOMMON, mage.cards.b.BloodHypnotist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Petal Celebrant", 146, Rarity.COMMON, mage.cards.b.BloodPetalCelebrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Petal Celebrant", 303, Rarity.COMMON, mage.cards.b.BloodPetalCelebrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Servitor", 252, Rarity.COMMON, mage.cards.b.BloodServitor.class));
        cards.add(new SetCardInfo("Bloodbat Summoner", 137, Rarity.RARE, mage.cards.b.BloodbatSummoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodbat Summoner", 298, Rarity.RARE, mage.cards.b.BloodbatSummoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodbat Summoner", 338, Rarity.RARE, mage.cards.b.BloodbatSummoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodcrazed Socialite", 288, Rarity.COMMON, mage.cards.b.BloodcrazedSocialite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodcrazed Socialite", 96, Rarity.COMMON, mage.cards.b.BloodcrazedSocialite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodsoaked Reveler", 128, Rarity.UNCOMMON, mage.cards.b.BloodsoakedReveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodsoaked Reveler", 295, Rarity.UNCOMMON, mage.cards.b.BloodsoakedReveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodsworn Knight", 289, Rarity.UNCOMMON, mage.cards.b.BloodswornKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodsworn Knight", 97, Rarity.UNCOMMON, mage.cards.b.BloodswornKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodsworn Squire", 289, Rarity.UNCOMMON, mage.cards.b.BloodswornSquire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodsworn Squire", 97, Rarity.UNCOMMON, mage.cards.b.BloodswornSquire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodtithe Harvester", 232, Rarity.UNCOMMON, mage.cards.b.BloodtitheHarvester.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodtithe Harvester", 310, Rarity.UNCOMMON, mage.cards.b.BloodtitheHarvester.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodvial Purveyor", 290, Rarity.RARE, mage.cards.b.BloodvialPurveyor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodvial Purveyor", 98, Rarity.RARE, mage.cards.b.BloodvialPurveyor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloody Betrayal", 147, Rarity.COMMON, mage.cards.b.BloodyBetrayal.class));
        cards.add(new SetCardInfo("Blossom-Clad Werewolf", 226, Rarity.COMMON, mage.cards.b.BlossomCladWerewolf.class));
        cards.add(new SetCardInfo("Boarded Window", 253, Rarity.UNCOMMON, mage.cards.b.BoardedWindow.class));
        cards.add(new SetCardInfo("Bramble Armor", 188, Rarity.COMMON, mage.cards.b.BrambleArmor.class));
        cards.add(new SetCardInfo("Bramble Wurm", 189, Rarity.UNCOMMON, mage.cards.b.BrambleWurm.class));
        cards.add(new SetCardInfo("Bride's Gown", 4, Rarity.UNCOMMON, mage.cards.b.BridesGown.class));
        cards.add(new SetCardInfo("Brine Comber", 233, Rarity.UNCOMMON, mage.cards.b.BrineComber.class));
        cards.add(new SetCardInfo("Brinebound Gift", 233, Rarity.UNCOMMON, mage.cards.b.BrineboundGift.class));
        cards.add(new SetCardInfo("By Invitation Only", 346, Rarity.RARE, mage.cards.b.ByInvitationOnly.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("By Invitation Only", 5, Rarity.RARE, mage.cards.b.ByInvitationOnly.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cackling Culprit", 28, Rarity.UNCOMMON, mage.cards.c.CacklingCulprit.class));
        cards.add(new SetCardInfo("Cartographer's Survey", 190, Rarity.UNCOMMON, mage.cards.c.CartographersSurvey.class));
        cards.add(new SetCardInfo("Catapult Captain", 99, Rarity.UNCOMMON, mage.cards.c.CatapultCaptain.class));
        cards.add(new SetCardInfo("Catapult Fodder", 99, Rarity.UNCOMMON, mage.cards.c.CatapultFodder.class));
        cards.add(new SetCardInfo("Catlike Curiosity", 69, Rarity.UNCOMMON, mage.cards.c.CatlikeCuriosity.class));
        cards.add(new SetCardInfo("Cemetery Desecrator", 100, Rarity.MYTHIC, mage.cards.c.CemeteryDesecrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Desecrator", 366, Rarity.MYTHIC, mage.cards.c.CemeteryDesecrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Gatekeeper", 148, Rarity.MYTHIC, mage.cards.c.CemeteryGatekeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Gatekeeper", 304, Rarity.MYTHIC, mage.cards.c.CemeteryGatekeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Illuminator", 356, Rarity.MYTHIC, mage.cards.c.CemeteryIlluminator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Illuminator", 50, Rarity.MYTHIC, mage.cards.c.CemeteryIlluminator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Protector", 347, Rarity.MYTHIC, mage.cards.c.CemeteryProtector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Protector", 6, Rarity.MYTHIC, mage.cards.c.CemeteryProtector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Prowler", 191, Rarity.MYTHIC, mage.cards.c.CemeteryProwler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Prowler", 385, Rarity.MYTHIC, mage.cards.c.CemeteryProwler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ceremonial Knife", 254, Rarity.COMMON, mage.cards.c.CeremonialKnife.class));
        cards.add(new SetCardInfo("Chandra, Dressed to Kill", 149, Rarity.MYTHIC, mage.cards.c.ChandraDressedToKill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Dressed to Kill", 279, Rarity.MYTHIC, mage.cards.c.ChandraDressedToKill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Change of Fortune", 150, Rarity.RARE, mage.cards.c.ChangeOfFortune.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Change of Fortune", 375, Rarity.RARE, mage.cards.c.ChangeOfFortune.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Child of the Pack", 234, Rarity.UNCOMMON, mage.cards.c.ChildOfThePack.class));
        cards.add(new SetCardInfo("Chill of the Grave", 51, Rarity.COMMON, mage.cards.c.ChillOfTheGrave.class));
        cards.add(new SetCardInfo("Cipherbound Spirit", 79, Rarity.UNCOMMON, mage.cards.c.CipherboundSpirit.class));
        cards.add(new SetCardInfo("Circle of Confinement", 329, Rarity.UNCOMMON, mage.cards.c.CircleOfConfinement.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circle of Confinement", 7, Rarity.UNCOMMON, mage.cards.c.CircleOfConfinement.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clever Distraction", 9, Rarity.UNCOMMON, mage.cards.c.CleverDistraction.class));
        cards.add(new SetCardInfo("Cloaked Cadet", 192, Rarity.UNCOMMON, mage.cards.c.CloakedCadet.class));
        cards.add(new SetCardInfo("Cobbled Lancer", 52, Rarity.UNCOMMON, mage.cards.c.CobbledLancer.class));
        cards.add(new SetCardInfo("Concealing Curtains", 101, Rarity.RARE, mage.cards.c.ConcealingCurtains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Concealing Curtains", 367, Rarity.RARE, mage.cards.c.ConcealingCurtains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Consuming Tide", 357, Rarity.RARE, mage.cards.c.ConsumingTide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Consuming Tide", 53, Rarity.RARE, mage.cards.c.ConsumingTide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Courier Bat", 102, Rarity.COMMON, mage.cards.c.CourierBat.class));
        cards.add(new SetCardInfo("Cradle of Safety", 54, Rarity.COMMON, mage.cards.c.CradleOfSafety.class));
        cards.add(new SetCardInfo("Crawling Infestation", 193, Rarity.UNCOMMON, mage.cards.c.CrawlingInfestation.class));
        cards.add(new SetCardInfo("Creepy Puppeteer", 151, Rarity.RARE, mage.cards.c.CreepyPuppeteer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Creepy Puppeteer", 376, Rarity.RARE, mage.cards.c.CreepyPuppeteer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cruel Witness", 55, Rarity.COMMON, mage.cards.c.CruelWitness.class));
        cards.add(new SetCardInfo("Crushing Canopy", 194, Rarity.COMMON, mage.cards.c.CrushingCanopy.class));
        cards.add(new SetCardInfo("Cultivator Colossus", 195, Rarity.MYTHIC, mage.cards.c.CultivatorColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cultivator Colossus", 386, Rarity.MYTHIC, mage.cards.c.CultivatorColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curse of Hospitality", 152, Rarity.RARE, mage.cards.c.CurseOfHospitality.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curse of Hospitality", 377, Rarity.RARE, mage.cards.c.CurseOfHospitality.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dawnhart Disciple", 196, Rarity.COMMON, mage.cards.d.DawnhartDisciple.class));
        cards.add(new SetCardInfo("Dawnhart Geist", 8, Rarity.UNCOMMON, mage.cards.d.DawnhartGeist.class));
        cards.add(new SetCardInfo("Daybreak Combatants", 153, Rarity.COMMON, mage.cards.d.DaybreakCombatants.class));
        cards.add(new SetCardInfo("Deadly Dancer", 141, Rarity.UNCOMMON, mage.cards.d.DeadlyDancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deadly Dancer", 300, Rarity.UNCOMMON, mage.cards.d.DeadlyDancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathcap Glade", 261, Rarity.RARE, mage.cards.d.DeathcapGlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathcap Glade", 281, Rarity.RARE, mage.cards.d.DeathcapGlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demonic Bargain", 103, Rarity.RARE, mage.cards.d.DemonicBargain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demonic Bargain", 368, Rarity.RARE, mage.cards.d.DemonicBargain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Depraved Harvester", 104, Rarity.COMMON, mage.cards.d.DepravedHarvester.class));
        cards.add(new SetCardInfo("Desperate Farmer", 104, Rarity.COMMON, mage.cards.d.DesperateFarmer.class));
        cards.add(new SetCardInfo("Dig Up", 197, Rarity.RARE, mage.cards.d.DigUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dig Up", 387, Rarity.RARE, mage.cards.d.DigUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire-Strain Anarchist", 181, Rarity.MYTHIC, mage.cards.d.DireStrainAnarchist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire-Strain Anarchist", 382, Rarity.MYTHIC, mage.cards.d.DireStrainAnarchist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diregraf Scavenger", 105, Rarity.COMMON, mage.cards.d.DiregrafScavenger.class));
        cards.add(new SetCardInfo("Distracting Geist", 9, Rarity.UNCOMMON, mage.cards.d.DistractingGeist.class));
        cards.add(new SetCardInfo("Diver Skaab", 56, Rarity.UNCOMMON, mage.cards.d.DiverSkaab.class));
        cards.add(new SetCardInfo("Dollhouse of Horrors", 255, Rarity.RARE, mage.cards.d.DollhouseOfHorrors.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dollhouse of Horrors", 395, Rarity.RARE, mage.cards.d.DollhouseOfHorrors.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dominating Vampire", 154, Rarity.RARE, mage.cards.d.DominatingVampire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dominating Vampire", 305, Rarity.RARE, mage.cards.d.DominatingVampire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dominating Vampire", 407, Rarity.RARE, mage.cards.d.DominatingVampire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doomed Dissenter", 106, Rarity.COMMON, mage.cards.d.DoomedDissenter.class));
        cards.add(new SetCardInfo("Dormant Grove", 198, Rarity.UNCOMMON, mage.cards.d.DormantGrove.class));
        cards.add(new SetCardInfo("Dorothea's Retribution", 235, Rarity.RARE, mage.cards.d.DorotheasRetribution.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dorothea's Retribution", 322, Rarity.RARE, mage.cards.d.DorotheasRetribution.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dorothea, Vengeful Victim", 235, Rarity.RARE, mage.cards.d.DorotheaVengefulVictim.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dorothea, Vengeful Victim", 322, Rarity.RARE, mage.cards.d.DorotheaVengefulVictim.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dread Fugue", 107, Rarity.UNCOMMON, mage.cards.d.DreadFugue.class));
        cards.add(new SetCardInfo("Dreadfeast Demon", 108, Rarity.RARE, mage.cards.d.DreadfeastDemon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreadfeast Demon", 369, Rarity.RARE, mage.cards.d.DreadfeastDemon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreadlight Monstrosity", 57, Rarity.COMMON, mage.cards.d.DreadlightMonstrosity.class));
        cards.add(new SetCardInfo("Dreamroot Cascade", 262, Rarity.RARE, mage.cards.d.DreamrootCascade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreamroot Cascade", 282, Rarity.RARE, mage.cards.d.DreamrootCascade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreamshackle Geist", 358, Rarity.RARE, mage.cards.d.DreamshackleGeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreamshackle Geist", 58, Rarity.RARE, mage.cards.d.DreamshackleGeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drogskol Armaments", 10, Rarity.COMMON, mage.cards.d.DrogskolArmaments.class));
        cards.add(new SetCardInfo("Drogskol Infantry", 10, Rarity.COMMON, mage.cards.d.DrogskolInfantry.class));
        cards.add(new SetCardInfo("Dying to Serve", 109, Rarity.RARE, mage.cards.d.DyingToServe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dying to Serve", 370, Rarity.RARE, mage.cards.d.DyingToServe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Edgar Markov's Coffin", 236, Rarity.RARE, mage.cards.e.EdgarMarkovsCoffin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Edgar Markov's Coffin", 311, Rarity.RARE, mage.cards.e.EdgarMarkovsCoffin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Edgar Markov's Coffin", 341, Rarity.RARE, mage.cards.e.EdgarMarkovsCoffin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Edgar's Awakening", 110, Rarity.UNCOMMON, mage.cards.e.EdgarsAwakening.class));
        cards.add(new SetCardInfo("Edgar, Charmed Groom", 236, Rarity.RARE, mage.cards.e.EdgarCharmedGroom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Edgar, Charmed Groom", 311, Rarity.RARE, mage.cards.e.EdgarCharmedGroom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Edgar, Charmed Groom", 341, Rarity.RARE, mage.cards.e.EdgarCharmedGroom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("End the Festivities", 155, Rarity.COMMON, mage.cards.e.EndTheFestivities.class));
        cards.add(new SetCardInfo("Eruth, Tormented Prophet", 237, Rarity.RARE, mage.cards.e.EruthTormentedProphet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eruth, Tormented Prophet", 323, Rarity.RARE, mage.cards.e.EruthTormentedProphet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eruth, Tormented Prophet", 342, Rarity.RARE, mage.cards.e.EruthTormentedProphet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Estwald Shieldbasher", 11, Rarity.COMMON, mage.cards.e.EstwaldShieldbasher.class));
        cards.add(new SetCardInfo("Evolving Wilds", 263, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Faithbound Judge", 12, Rarity.MYTHIC, mage.cards.f.FaithboundJudge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faithbound Judge", 348, Rarity.MYTHIC, mage.cards.f.FaithboundJudge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Falkenrath Celebrants", 156, Rarity.COMMON, mage.cards.f.FalkenrathCelebrants.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Falkenrath Celebrants", 306, Rarity.COMMON, mage.cards.f.FalkenrathCelebrants.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Falkenrath Forebear", 111, Rarity.RARE, mage.cards.f.FalkenrathForebear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Falkenrath Forebear", 291, Rarity.RARE, mage.cards.f.FalkenrathForebear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Falkenrath Forebear", 334, Rarity.RARE, mage.cards.f.FalkenrathForebear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fear of Death", 59, Rarity.COMMON, mage.cards.f.FearOfDeath.class));
        cards.add(new SetCardInfo("Fearful Villager", 157, Rarity.COMMON, mage.cards.f.FearfulVillager.class));
        cards.add(new SetCardInfo("Fearsome Werewolf", 157, Rarity.COMMON, mage.cards.f.FearsomeWerewolf.class));
        cards.add(new SetCardInfo("Fell Stinger", 112, Rarity.UNCOMMON, mage.cards.f.FellStinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fell Stinger", 406, Rarity.UNCOMMON, mage.cards.f.FellStinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fierce Retribution", 13, Rarity.COMMON, mage.cards.f.FierceRetribution.class));
        cards.add(new SetCardInfo("Flame-Blessed Bolt", 158, Rarity.COMMON, mage.cards.f.FlameBlessedBolt.class));
        cards.add(new SetCardInfo("Fleeting Spirit", 14, Rarity.UNCOMMON, mage.cards.f.FleetingSpirit.class));
        cards.add(new SetCardInfo("Flourishing Hunter", 199, Rarity.COMMON, mage.cards.f.FlourishingHunter.class));
        cards.add(new SetCardInfo("Foreboding Statue", 256, Rarity.UNCOMMON, mage.cards.f.ForebodingStatue.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 277, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 402, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 412, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forsaken Thresher", 256, Rarity.UNCOMMON, mage.cards.f.ForsakenThresher.class));
        cards.add(new SetCardInfo("Frenzied Devils", 159, Rarity.UNCOMMON, mage.cards.f.FrenziedDevils.class));
        cards.add(new SetCardInfo("Geistlight Snare", 405, Rarity.UNCOMMON, mage.cards.g.GeistlightSnare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geistlight Snare", 60, Rarity.UNCOMMON, mage.cards.g.GeistlightSnare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geralf, Visionary Stitcher", 319, Rarity.RARE, mage.cards.g.GeralfVisionaryStitcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geralf, Visionary Stitcher", 61, Rarity.RARE, mage.cards.g.GeralfVisionaryStitcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghastly Mimicry", 361, Rarity.RARE, mage.cards.g.GhastlyMimicry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghastly Mimicry", 68, Rarity.RARE, mage.cards.g.GhastlyMimicry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gift of Fangs", 113, Rarity.COMMON, mage.cards.g.GiftOfFangs.class));
        cards.add(new SetCardInfo("Glorious Sunrise", 200, Rarity.RARE, mage.cards.g.GloriousSunrise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glorious Sunrise", 388, Rarity.RARE, mage.cards.g.GloriousSunrise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gluttonous Guest", 114, Rarity.COMMON, mage.cards.g.GluttonousGuest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gluttonous Guest", 292, Rarity.COMMON, mage.cards.g.GluttonousGuest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gnarled Grovestrider", 198, Rarity.UNCOMMON, mage.cards.g.GnarledGrovestrider.class));
        cards.add(new SetCardInfo("Graf Reaver", 115, Rarity.RARE, mage.cards.g.GrafReaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Graf Reaver", 371, Rarity.RARE, mage.cards.g.GrafReaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grisly Ritual", 116, Rarity.COMMON, mage.cards.g.GrislyRitual.class));
        cards.add(new SetCardInfo("Grolnok, the Omnivore", 238, Rarity.RARE, mage.cards.g.GrolnokTheOmnivore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grolnok, the Omnivore", 324, Rarity.RARE, mage.cards.g.GrolnokTheOmnivore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Groom's Finery", 117, Rarity.UNCOMMON, mage.cards.g.GroomsFinery.class));
        cards.add(new SetCardInfo("Gryff Rider", 15, Rarity.COMMON, mage.cards.g.GryffRider.class));
        cards.add(new SetCardInfo("Gryffwing Cavalry", 16, Rarity.UNCOMMON, mage.cards.g.GryffwingCavalry.class));
        cards.add(new SetCardInfo("Gutter Shortcut", 62, Rarity.UNCOMMON, mage.cards.g.GutterShortcut.class));
        cards.add(new SetCardInfo("Gutter Skulker", 62, Rarity.UNCOMMON, mage.cards.g.GutterSkulker.class));
        cards.add(new SetCardInfo("Halana and Alena, Partners", 239, Rarity.RARE, mage.cards.h.HalanaAndAlenaPartners.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Halana and Alena, Partners", 325, Rarity.RARE, mage.cards.h.HalanaAndAlenaPartners.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Haunting", 17, Rarity.MYTHIC, mage.cards.h.HallowedHaunting.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Haunting", 349, Rarity.MYTHIC, mage.cards.h.HallowedHaunting.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hamlet Vanguard", 201, Rarity.RARE, mage.cards.h.HamletVanguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hamlet Vanguard", 389, Rarity.RARE, mage.cards.h.HamletVanguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hauken's Insight", 320, Rarity.MYTHIC, mage.cards.h.HaukensInsight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hauken's Insight", 332, Rarity.MYTHIC, mage.cards.h.HaukensInsight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hauken's Insight", 65, Rarity.MYTHIC, mage.cards.h.HaukensInsight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Headless Rider", 118, Rarity.RARE, mage.cards.h.HeadlessRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Headless Rider", 372, Rarity.RARE, mage.cards.h.HeadlessRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Henrika Domnathi", 119, Rarity.MYTHIC, mage.cards.h.HenrikaDomnathi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Henrika Domnathi", 293, Rarity.MYTHIC, mage.cards.h.HenrikaDomnathi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Henrika Domnathi", 335, Rarity.MYTHIC, mage.cards.h.HenrikaDomnathi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Henrika, Infernal Seer", 119, Rarity.MYTHIC, mage.cards.h.HenrikaInfernalSeer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Henrika, Infernal Seer", 293, Rarity.MYTHIC, mage.cards.h.HenrikaInfernalSeer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Henrika, Infernal Seer", 335, Rarity.MYTHIC, mage.cards.h.HenrikaInfernalSeer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hero's Downfall", 120, Rarity.UNCOMMON, mage.cards.h.HerosDownfall.class));
        cards.add(new SetCardInfo("Heron of Hope", 18, Rarity.COMMON, mage.cards.h.HeronOfHope.class));
        cards.add(new SetCardInfo("Heron-Blessed Geist", 19, Rarity.COMMON, mage.cards.h.HeronBlessedGeist.class));
        cards.add(new SetCardInfo("Hiveheart Shaman", 202, Rarity.RARE, mage.cards.h.HiveheartShaman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hiveheart Shaman", 390, Rarity.RARE, mage.cards.h.HiveheartShaman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hollowhenge Huntmaster", 187, Rarity.MYTHIC, mage.cards.h.HollowhengeHuntmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hollowhenge Huntmaster", 384, Rarity.MYTHIC, mage.cards.h.HollowhengeHuntmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Honeymoon Hearse", 160, Rarity.UNCOMMON, mage.cards.h.HoneymoonHearse.class));
        cards.add(new SetCardInfo("Honored Heirloom", 257, Rarity.COMMON, mage.cards.h.HonoredHeirloom.class));
        cards.add(new SetCardInfo("Hookhand Mariner", 203, Rarity.COMMON, mage.cards.h.HookhandMariner.class));
        cards.add(new SetCardInfo("Hopeful Initiate", 20, Rarity.RARE, mage.cards.h.HopefulInitiate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hopeful Initiate", 350, Rarity.RARE, mage.cards.h.HopefulInitiate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Howling Moon", 204, Rarity.RARE, mage.cards.h.HowlingMoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Howling Moon", 391, Rarity.RARE, mage.cards.h.HowlingMoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Howlpack Avenger", 162, Rarity.RARE, mage.cards.h.HowlpackAvenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Howlpack Avenger", 378, Rarity.RARE, mage.cards.h.HowlpackAvenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Howlpack Piper", 205, Rarity.RARE, mage.cards.h.HowlpackPiper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Howlpack Piper", 392, Rarity.RARE, mage.cards.h.HowlpackPiper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hullbreaker Horror", 359, Rarity.RARE, mage.cards.h.HullbreakerHorror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hullbreaker Horror", 63, Rarity.RARE, mage.cards.h.HullbreakerHorror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hungry Ridgewolf", 161, Rarity.COMMON, mage.cards.h.HungryRidgewolf.class));
        cards.add(new SetCardInfo("Ill-Tempered Loner", 162, Rarity.RARE, mage.cards.i.IllTemperedLoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ill-Tempered Loner", 378, Rarity.RARE, mage.cards.i.IllTemperedLoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Infestation Expert", 206, Rarity.UNCOMMON, mage.cards.i.InfestationExpert.class));
        cards.add(new SetCardInfo("Infested Werewolf", 206, Rarity.UNCOMMON, mage.cards.i.InfestedWerewolf.class));
        cards.add(new SetCardInfo("Innocent Traveler", 121, Rarity.UNCOMMON, mage.cards.i.InnocentTraveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Innocent Traveler", 294, Rarity.UNCOMMON, mage.cards.i.InnocentTraveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Innocent Traveler", 336, Rarity.UNCOMMON, mage.cards.i.InnocentTraveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inspired Idea", 360, Rarity.RARE, mage.cards.i.InspiredIdea.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inspired Idea", 64, Rarity.RARE, mage.cards.i.InspiredIdea.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Into the Night", 163, Rarity.UNCOMMON, mage.cards.i.IntoTheNight.class));
        cards.add(new SetCardInfo("Investigator's Journal", 258, Rarity.RARE, mage.cards.i.InvestigatorsJournal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Investigator's Journal", 345, Rarity.RARE, mage.cards.i.InvestigatorsJournal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Investigator's Journal", 396, Rarity.RARE, mage.cards.i.InvestigatorsJournal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 271, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 399, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 409, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jacob Hauken, Inspector", 320, Rarity.MYTHIC, mage.cards.j.JacobHaukenInspector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jacob Hauken, Inspector", 332, Rarity.MYTHIC, mage.cards.j.JacobHaukenInspector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jacob Hauken, Inspector", 65, Rarity.MYTHIC, mage.cards.j.JacobHaukenInspector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katilda's Rising Dawn", 21, Rarity.RARE, mage.cards.k.KatildasRisingDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katilda's Rising Dawn", 317, Rarity.RARE, mage.cards.k.KatildasRisingDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katilda, Dawnhart Martyr", 21, Rarity.RARE, mage.cards.k.KatildaDawnhartMartyr.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katilda, Dawnhart Martyr", 317, Rarity.RARE, mage.cards.k.KatildaDawnhartMartyr.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaya, Geist Hunter", 240, Rarity.MYTHIC, mage.cards.k.KayaGeistHunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaya, Geist Hunter", 280, Rarity.MYTHIC, mage.cards.k.KayaGeistHunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kessig Flamebreather", 164, Rarity.COMMON, mage.cards.k.KessigFlamebreather.class));
        cards.add(new SetCardInfo("Kessig Wolfrider", 165, Rarity.RARE, mage.cards.k.KessigWolfrider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kessig Wolfrider", 379, Rarity.RARE, mage.cards.k.KessigWolfrider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kindly Ancestor", 22, Rarity.COMMON, mage.cards.k.KindlyAncestor.class));
        cards.add(new SetCardInfo("Krothuss, Lord of the Deep", 246, Rarity.RARE, mage.cards.k.KrothussLordOfTheDeep.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krothuss, Lord of the Deep", 316, Rarity.RARE, mage.cards.k.KrothussLordOfTheDeep.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krothuss, Lord of the Deep", 327, Rarity.RARE, mage.cards.k.KrothussLordOfTheDeep.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lacerate Flesh", 166, Rarity.COMMON, mage.cards.l.LacerateFlesh.class));
        cards.add(new SetCardInfo("Laid to Rest", 207, Rarity.UNCOMMON, mage.cards.l.LaidToRest.class));
        cards.add(new SetCardInfo("Lambholt Raconteur", 167, Rarity.UNCOMMON, mage.cards.l.LambholtRaconteur.class));
        cards.add(new SetCardInfo("Lambholt Ravager", 167, Rarity.UNCOMMON, mage.cards.l.LambholtRavager.class));
        cards.add(new SetCardInfo("Lantern Bearer", 66, Rarity.COMMON, mage.cards.l.LanternBearer.class));
        cards.add(new SetCardInfo("Lantern Flare", 23, Rarity.RARE, mage.cards.l.LanternFlare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lantern Flare", 351, Rarity.RARE, mage.cards.l.LanternFlare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lantern of the Lost", 259, Rarity.UNCOMMON, mage.cards.l.LanternOfTheLost.class));
        cards.add(new SetCardInfo("Lanterns' Lift", 66, Rarity.COMMON, mage.cards.l.LanternsLift.class));
        cards.add(new SetCardInfo("Lightning Wolf", 168, Rarity.COMMON, mage.cards.l.LightningWolf.class));
        cards.add(new SetCardInfo("Lunar Rejection", 67, Rarity.UNCOMMON, mage.cards.l.LunarRejection.class));
        cards.add(new SetCardInfo("Magma Pummeler", 169, Rarity.UNCOMMON, mage.cards.m.MagmaPummeler.class));
        cards.add(new SetCardInfo("Malicious Invader", 121, Rarity.UNCOMMON, mage.cards.m.MaliciousInvader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Malicious Invader", 294, Rarity.UNCOMMON, mage.cards.m.MaliciousInvader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Malicious Invader", 336, Rarity.UNCOMMON, mage.cards.m.MaliciousInvader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Manaform Hellkite", 170, Rarity.MYTHIC, mage.cards.m.ManaformHellkite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Manaform Hellkite", 380, Rarity.MYTHIC, mage.cards.m.ManaformHellkite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Markov Purifier", 241, Rarity.UNCOMMON, mage.cards.m.MarkovPurifier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Markov Purifier", 312, Rarity.UNCOMMON, mage.cards.m.MarkovPurifier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Markov Retribution", 171, Rarity.UNCOMMON, mage.cards.m.MarkovRetribution.class));
        cards.add(new SetCardInfo("Markov Waltzer", 242, Rarity.UNCOMMON, mage.cards.m.MarkovWaltzer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Markov Waltzer", 313, Rarity.UNCOMMON, mage.cards.m.MarkovWaltzer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Massive Might", 208, Rarity.COMMON, mage.cards.m.MassiveMight.class));
        cards.add(new SetCardInfo("Militia Rallier", 24, Rarity.COMMON, mage.cards.m.MilitiaRallier.class));
        cards.add(new SetCardInfo("Mindleech Ghoul", 122, Rarity.COMMON, mage.cards.m.MindleechGhoul.class));
        cards.add(new SetCardInfo("Mirrorhall Mimic", 361, Rarity.RARE, mage.cards.m.MirrorhallMimic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirrorhall Mimic", 68, Rarity.RARE, mage.cards.m.MirrorhallMimic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mischievous Catgeist", 69, Rarity.UNCOMMON, mage.cards.m.MischievousCatgeist.class));
        cards.add(new SetCardInfo("Moldgraf Millipede", 209, Rarity.COMMON, mage.cards.m.MoldgrafMillipede.class));
        cards.add(new SetCardInfo("Moonlit Ambusher", 212, Rarity.UNCOMMON, mage.cards.m.MoonlitAmbusher.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 401, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 411, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mulch", 210, Rarity.COMMON, mage.cards.m.Mulch.class));
        cards.add(new SetCardInfo("Nature's Embrace", 211, Rarity.COMMON, mage.cards.n.NaturesEmbrace.class));
        cards.add(new SetCardInfo("Nebelgast Beguiler", 25, Rarity.COMMON, mage.cards.n.NebelgastBeguiler.class));
        cards.add(new SetCardInfo("Necroduality", 362, Rarity.MYTHIC, mage.cards.n.Necroduality.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necroduality", 70, Rarity.MYTHIC, mage.cards.n.Necroduality.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nurturing Presence", 26, Rarity.COMMON, mage.cards.n.NurturingPresence.class));
        cards.add(new SetCardInfo("Oakshade Stalker", 212, Rarity.UNCOMMON, mage.cards.o.OakshadeStalker.class));
        cards.add(new SetCardInfo("Odious Witch", 127, Rarity.COMMON, mage.cards.o.OdiousWitch.class));
        cards.add(new SetCardInfo("Odric, Blood-Cursed", 243, Rarity.RARE, mage.cards.o.OdricBloodCursed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Odric, Blood-Cursed", 314, Rarity.RARE, mage.cards.o.OdricBloodCursed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Old Rutstein", 244, Rarity.RARE, mage.cards.o.OldRutstein.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Old Rutstein", 326, Rarity.RARE, mage.cards.o.OldRutstein.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Olivia's Attendants", 172, Rarity.RARE, mage.cards.o.OliviasAttendants.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Olivia's Attendants", 307, Rarity.RARE, mage.cards.o.OliviasAttendants.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Olivia, Crimson Bride", 245, Rarity.MYTHIC, mage.cards.o.OliviaCrimsonBride.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Olivia, Crimson Bride", 315, Rarity.MYTHIC, mage.cards.o.OliviaCrimsonBride.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Olivia, Crimson Bride", 343, Rarity.MYTHIC, mage.cards.o.OliviaCrimsonBride.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ollenbock Escort", 27, Rarity.UNCOMMON, mage.cards.o.OllenbockEscort.class));
        cards.add(new SetCardInfo("Overcharged Amalgam", 363, Rarity.RARE, mage.cards.o.OverchargedAmalgam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overcharged Amalgam", 71, Rarity.RARE, mage.cards.o.OverchargedAmalgam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Packsong Pup", 213, Rarity.UNCOMMON, mage.cards.p.PacksongPup.class));
        cards.add(new SetCardInfo("Panicked Bystander", 28, Rarity.UNCOMMON, mage.cards.p.PanickedBystander.class));
        cards.add(new SetCardInfo("Parasitic Grasp", 123, Rarity.UNCOMMON, mage.cards.p.ParasiticGrasp.class));
        cards.add(new SetCardInfo("Parish-Blade Trainee", 29, Rarity.COMMON, mage.cards.p.ParishBladeTrainee.class));
        cards.add(new SetCardInfo("Patchwork Crawler", 364, Rarity.RARE, mage.cards.p.PatchworkCrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Patchwork Crawler", 72, Rarity.RARE, mage.cards.p.PatchworkCrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Path of Peril", 124, Rarity.RARE, mage.cards.p.PathOfPeril.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Path of Peril", 373, Rarity.RARE, mage.cards.p.PathOfPeril.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Persistent Specimen", 125, Rarity.COMMON, mage.cards.p.PersistentSpecimen.class));
        cards.add(new SetCardInfo("Piercing Light", 30, Rarity.COMMON, mage.cards.p.PiercingLight.class));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 269, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 398, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 408, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Pointed Discussion", 126, Rarity.COMMON, mage.cards.p.PointedDiscussion.class));
        cards.add(new SetCardInfo("Pyre Spawn", 173, Rarity.COMMON, mage.cards.p.PyreSpawn.class));
        cards.add(new SetCardInfo("Radiant Grace", 31, Rarity.UNCOMMON, mage.cards.r.RadiantGrace.class));
        cards.add(new SetCardInfo("Radiant Restraints", 31, Rarity.UNCOMMON, mage.cards.r.RadiantRestraints.class));
        cards.add(new SetCardInfo("Ragged Recluse", 127, Rarity.COMMON, mage.cards.r.RaggedRecluse.class));
        cards.add(new SetCardInfo("Reckless Impulse", 174, Rarity.COMMON, mage.cards.r.RecklessImpulse.class));
        cards.add(new SetCardInfo("Reclusive Taxidermist", 214, Rarity.UNCOMMON, mage.cards.r.ReclusiveTaxidermist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reclusive Taxidermist", 340, Rarity.UNCOMMON, mage.cards.r.ReclusiveTaxidermist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rending Flame", 175, Rarity.UNCOMMON, mage.cards.r.RendingFlame.class));
        cards.add(new SetCardInfo("Repository Skaab", 73, Rarity.COMMON, mage.cards.r.RepositorySkaab.class));
        cards.add(new SetCardInfo("Resistance Squad", 32, Rarity.UNCOMMON, mage.cards.r.ResistanceSquad.class));
        cards.add(new SetCardInfo("Restless Bloodseeker", 128, Rarity.UNCOMMON, mage.cards.r.RestlessBloodseeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Restless Bloodseeker", 295, Rarity.UNCOMMON, mage.cards.r.RestlessBloodseeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Retrieve", 215, Rarity.UNCOMMON, mage.cards.r.Retrieve.class));
        cards.add(new SetCardInfo("Revealing Eye", 101, Rarity.RARE, mage.cards.r.RevealingEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Revealing Eye", 367, Rarity.RARE, mage.cards.r.RevealingEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Riphook Raider", 203, Rarity.COMMON, mage.cards.r.RiphookRaider.class));
        cards.add(new SetCardInfo("Rot-Tide Gargantua", 129, Rarity.COMMON, mage.cards.r.RotTideGargantua.class));
        cards.add(new SetCardInfo("Runebound Wolf", 176, Rarity.UNCOMMON, mage.cards.r.RuneboundWolf.class));
        cards.add(new SetCardInfo("Runo Stromkirk", 246, Rarity.RARE, mage.cards.r.RunoStromkirk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Runo Stromkirk", 316, Rarity.RARE, mage.cards.r.RunoStromkirk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Runo Stromkirk", 327, Rarity.RARE, mage.cards.r.RunoStromkirk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rural Recruit", 216, Rarity.COMMON, mage.cards.r.RuralRecruit.class));
        cards.add(new SetCardInfo("Sanctify", 33, Rarity.COMMON, mage.cards.s.Sanctify.class));
        cards.add(new SetCardInfo("Sanguine Statuette", 177, Rarity.UNCOMMON, mage.cards.s.SanguineStatuette.class));
        cards.add(new SetCardInfo("Savage Packmate", 234, Rarity.UNCOMMON, mage.cards.s.SavagePackmate.class));
        cards.add(new SetCardInfo("Savior of Ollenbock", 330, Rarity.MYTHIC, mage.cards.s.SaviorOfOllenbock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Savior of Ollenbock", 34, Rarity.MYTHIC, mage.cards.s.SaviorOfOllenbock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Savior of Ollenbock", 352, Rarity.MYTHIC, mage.cards.s.SaviorOfOllenbock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sawblade Slinger", 217, Rarity.UNCOMMON, mage.cards.s.SawbladeSlinger.class));
        cards.add(new SetCardInfo("Scattered Thoughts", 74, Rarity.COMMON, mage.cards.s.ScatteredThoughts.class));
        cards.add(new SetCardInfo("Screaming Swarm", 75, Rarity.UNCOMMON, mage.cards.s.ScreamingSwarm.class));
        cards.add(new SetCardInfo("Selhoff Entomber", 76, Rarity.COMMON, mage.cards.s.SelhoffEntomber.class));
        cards.add(new SetCardInfo("Serpentine Ambush", 77, Rarity.COMMON, mage.cards.s.SerpentineAmbush.class));
        cards.add(new SetCardInfo("Shattered Sanctum", 264, Rarity.RARE, mage.cards.s.ShatteredSanctum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shattered Sanctum", 283, Rarity.RARE, mage.cards.s.ShatteredSanctum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sheltering Boughs", 218, Rarity.COMMON, mage.cards.s.ShelteringBoughs.class));
        cards.add(new SetCardInfo("Sigarda's Imprisonment", 35, Rarity.COMMON, mage.cards.s.SigardasImprisonment.class));
        cards.add(new SetCardInfo("Sigarda's Summons", 353, Rarity.RARE, mage.cards.s.SigardasSummons.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigarda's Summons", 36, Rarity.RARE, mage.cards.s.SigardasSummons.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigarda's Summons", 404, Rarity.RARE, mage.cards.s.SigardasSummons.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigardian Paladin", 247, Rarity.UNCOMMON, mage.cards.s.SigardianPaladin.class));
        cards.add(new SetCardInfo("Sinner's Judgment", 12, Rarity.MYTHIC, mage.cards.s.SinnersJudgment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sinner's Judgment", 348, Rarity.MYTHIC, mage.cards.s.SinnersJudgment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skulking Killer", 130, Rarity.UNCOMMON, mage.cards.s.SkulkingKiller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skulking Killer", 296, Rarity.UNCOMMON, mage.cards.s.SkulkingKiller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skull Skaab", 248, Rarity.UNCOMMON, mage.cards.s.SkullSkaab.class));
        cards.add(new SetCardInfo("Skywarp Skaab", 78, Rarity.COMMON, mage.cards.s.SkywarpSkaab.class));
        cards.add(new SetCardInfo("Snarling Wolf", 219, Rarity.COMMON, mage.cards.s.SnarlingWolf.class));
        cards.add(new SetCardInfo("Sorin the Mirthless", 131, Rarity.MYTHIC, mage.cards.s.SorinTheMirthless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sorin the Mirthless", 278, Rarity.MYTHIC, mage.cards.s.SorinTheMirthless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sorin the Mirthless", 297, Rarity.MYTHIC, mage.cards.s.SorinTheMirthless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sorin the Mirthless", 337, Rarity.MYTHIC, mage.cards.s.SorinTheMirthless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soulcipher Board", 79, Rarity.UNCOMMON, mage.cards.s.SoulcipherBoard.class));
        cards.add(new SetCardInfo("Spectral Binding", 48, Rarity.COMMON, mage.cards.s.SpectralBinding.class));
        cards.add(new SetCardInfo("Spiked Ripsaw", 220, Rarity.UNCOMMON, mage.cards.s.SpikedRipsaw.class));
        cards.add(new SetCardInfo("Splendid Reclamation", 221, Rarity.RARE, mage.cards.s.SplendidReclamation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Splendid Reclamation", 393, Rarity.RARE, mage.cards.s.SplendidReclamation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spore Crawler", 222, Rarity.COMMON, mage.cards.s.SporeCrawler.class));
        cards.add(new SetCardInfo("Sporeback Wolf", 223, Rarity.COMMON, mage.cards.s.SporebackWolf.class));
        cards.add(new SetCardInfo("Steelclad Spirit", 80, Rarity.COMMON, mage.cards.s.SteelcladSpirit.class));
        cards.add(new SetCardInfo("Stensia Uprising", 178, Rarity.RARE, mage.cards.s.StensiaUprising.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stensia Uprising", 381, Rarity.RARE, mage.cards.s.StensiaUprising.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stitched Assistant", 81, Rarity.COMMON, mage.cards.s.StitchedAssistant.class));
        cards.add(new SetCardInfo("Stormcarved Coast", 265, Rarity.RARE, mage.cards.s.StormcarvedCoast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stormcarved Coast", 284, Rarity.RARE, mage.cards.s.StormcarvedCoast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stormchaser Drake", 82, Rarity.UNCOMMON, mage.cards.s.StormchaserDrake.class));
        cards.add(new SetCardInfo("Sundown Pass", 266, Rarity.RARE, mage.cards.s.SundownPass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sundown Pass", 285, Rarity.RARE, mage.cards.s.SundownPass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Supernatural Rescue", 37, Rarity.COMMON, mage.cards.s.SupernaturalRescue.class));
        cards.add(new SetCardInfo("Sure Strike", 179, Rarity.COMMON, mage.cards.s.SureStrike.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 273, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 400, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 410, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Syncopate", 83, Rarity.COMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Syphon Essence", 84, Rarity.COMMON, mage.cards.s.SyphonEssence.class));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 318, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 331, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 38, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thirst for Discovery", 333, Rarity.UNCOMMON, mage.cards.t.ThirstForDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thirst for Discovery", 85, Rarity.UNCOMMON, mage.cards.t.ThirstForDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Torens, Fist of the Angels", 249, Rarity.RARE, mage.cards.t.TorensFistOfTheAngels.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Torens, Fist of the Angels", 328, Rarity.RARE, mage.cards.t.TorensFistOfTheAngels.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Torens, Fist of the Angels", 344, Rarity.RARE, mage.cards.t.TorensFistOfTheAngels.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Toxic Scorpion", 224, Rarity.COMMON, mage.cards.t.ToxicScorpion.class));
        cards.add(new SetCardInfo("Toxrill, the Corrosive", 132, Rarity.MYTHIC, mage.cards.t.ToxrillTheCorrosive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Toxrill, the Corrosive", 321, Rarity.MYTHIC, mage.cards.t.ToxrillTheCorrosive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Traveling Minister", 39, Rarity.COMMON, mage.cards.t.TravelingMinister.class));
        cards.add(new SetCardInfo("Twinblade Geist", 40, Rarity.UNCOMMON, mage.cards.t.TwinbladeGeist.class));
        cards.add(new SetCardInfo("Twinblade Invocation", 40, Rarity.UNCOMMON, mage.cards.t.TwinbladeInvocation.class));
        cards.add(new SetCardInfo("Ulvenwald Behemoth", 225, Rarity.RARE, mage.cards.u.UlvenwaldBehemoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ulvenwald Behemoth", 394, Rarity.RARE, mage.cards.u.UlvenwaldBehemoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ulvenwald Oddity", 225, Rarity.RARE, mage.cards.u.UlvenwaldOddity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ulvenwald Oddity", 394, Rarity.RARE, mage.cards.u.UlvenwaldOddity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undead Butler", 133, Rarity.UNCOMMON, mage.cards.u.UndeadButler.class));
        cards.add(new SetCardInfo("Undying Malice", 134, Rarity.COMMON, mage.cards.u.UndyingMalice.class));
        cards.add(new SetCardInfo("Unhallowed Phalanx", 135, Rarity.COMMON, mage.cards.u.UnhallowedPhalanx.class));
        cards.add(new SetCardInfo("Unholy Officiant", 286, Rarity.COMMON, mage.cards.u.UnholyOfficiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unholy Officiant", 41, Rarity.COMMON, mage.cards.u.UnholyOfficiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valorous Stance", 42, Rarity.UNCOMMON, mage.cards.v.ValorousStance.class));
        cards.add(new SetCardInfo("Vampire Slayer", 43, Rarity.COMMON, mage.cards.v.VampireSlayer.class));
        cards.add(new SetCardInfo("Vampire's Kiss", 136, Rarity.COMMON, mage.cards.v.VampiresKiss.class));
        cards.add(new SetCardInfo("Vampires' Vengeance", 180, Rarity.UNCOMMON, mage.cards.v.VampiresVengeance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vampires' Vengeance", 339, Rarity.UNCOMMON, mage.cards.v.VampiresVengeance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vilespawn Spider", 250, Rarity.UNCOMMON, mage.cards.v.VilespawnSpider.class));
        cards.add(new SetCardInfo("Voice of the Blessed", 354, Rarity.RARE, mage.cards.v.VoiceOfTheBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voice of the Blessed", 44, Rarity.RARE, mage.cards.v.VoiceOfTheBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Volatile Arsonist", 181, Rarity.MYTHIC, mage.cards.v.VolatileArsonist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Volatile Arsonist", 382, Rarity.MYTHIC, mage.cards.v.VolatileArsonist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voldaren Bloodcaster", 137, Rarity.RARE, mage.cards.v.VoldarenBloodcaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voldaren Bloodcaster", 298, Rarity.RARE, mage.cards.v.VoldarenBloodcaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voldaren Bloodcaster", 338, Rarity.RARE, mage.cards.v.VoldarenBloodcaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voldaren Epicure", 182, Rarity.COMMON, mage.cards.v.VoldarenEpicure.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voldaren Epicure", 308, Rarity.COMMON, mage.cards.v.VoldarenEpicure.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voldaren Estate", 267, Rarity.RARE, mage.cards.v.VoldarenEstate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voldaren Estate", 397, Rarity.RARE, mage.cards.v.VoldarenEstate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voldaren Estate", 403, Rarity.RARE, mage.cards.v.VoldarenEstate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Volt-Charged Berserker", 183, Rarity.UNCOMMON, mage.cards.v.VoltChargedBerserker.class));
        cards.add(new SetCardInfo("Voltaic Visionary", 183, Rarity.UNCOMMON, mage.cards.v.VoltaicVisionary.class));
        cards.add(new SetCardInfo("Wandering Mind", 251, Rarity.UNCOMMON, mage.cards.w.WanderingMind.class));
        cards.add(new SetCardInfo("Wanderlight Spirit", 86, Rarity.COMMON, mage.cards.w.WanderlightSpirit.class));
        cards.add(new SetCardInfo("Wash Away", 87, Rarity.UNCOMMON, mage.cards.w.WashAway.class));
        cards.add(new SetCardInfo("Weary Prisoner", 184, Rarity.COMMON, mage.cards.w.WearyPrisoner.class));
        cards.add(new SetCardInfo("Weaver of Blossoms", 226, Rarity.COMMON, mage.cards.w.WeaverOfBlossoms.class));
        cards.add(new SetCardInfo("Wedding Announcement", 355, Rarity.RARE, mage.cards.w.WeddingAnnouncement.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wedding Announcement", 45, Rarity.RARE, mage.cards.w.WeddingAnnouncement.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wedding Crasher", 229, Rarity.UNCOMMON, mage.cards.w.WeddingCrasher.class));
        cards.add(new SetCardInfo("Wedding Festivity", 355, Rarity.RARE, mage.cards.w.WeddingFestivity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wedding Festivity", 45, Rarity.RARE, mage.cards.w.WeddingFestivity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wedding Invitation", 260, Rarity.COMMON, mage.cards.w.WeddingInvitation.class));
        cards.add(new SetCardInfo("Wedding Security", 138, Rarity.UNCOMMON, mage.cards.w.WeddingSecurity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wedding Security", 299, Rarity.UNCOMMON, mage.cards.w.WeddingSecurity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Welcoming Vampire", 287, Rarity.RARE, mage.cards.w.WelcomingVampire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Welcoming Vampire", 46, Rarity.RARE, mage.cards.w.WelcomingVampire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whispering Wizard", 88, Rarity.UNCOMMON, mage.cards.w.WhisperingWizard.class));
        cards.add(new SetCardInfo("Wildsong Howler", 205, Rarity.RARE, mage.cards.w.WildsongHowler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wildsong Howler", 392, Rarity.RARE, mage.cards.w.WildsongHowler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Winged Portent", 365, Rarity.RARE, mage.cards.w.WingedPortent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Winged Portent", 89, Rarity.RARE, mage.cards.w.WingedPortent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Witch's Web", 227, Rarity.COMMON, mage.cards.w.WitchsWeb.class));
        cards.add(new SetCardInfo("Witness the Future", 90, Rarity.UNCOMMON, mage.cards.w.WitnessTheFuture.class));
        cards.add(new SetCardInfo("Wolf Strike", 228, Rarity.COMMON, mage.cards.w.WolfStrike.class));
        cards.add(new SetCardInfo("Wolfkin Outcast", 229, Rarity.UNCOMMON, mage.cards.w.WolfkinOutcast.class));
        cards.add(new SetCardInfo("Wrathful Jailbreaker", 184, Rarity.COMMON, mage.cards.w.WrathfulJailbreaker.class));
        cards.add(new SetCardInfo("Wretched Throng", 91, Rarity.COMMON, mage.cards.w.WretchedThrong.class));
    }

    // add common double faced card to booster
    @Override
    protected void addDoubleFace(List<Card> booster) {
        addToBooster(booster, getSpecialCardsByRarity(Rarity.COMMON));
    }

    @Override
    public BoosterCollator createCollator() {
        return new InnistradCrimsonVowCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/vow.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class InnistradCrimsonVowCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "158", "59", "11", "174", "74", "1", "303", "25", "73", "26", "156", "91", "19", "164", "80", "43", "139", "54", "41", "166", "55", "30", "144", "29", "78", "18", "161", "57", "35", "179", "86", "11", "168", "81", "37", "142", "74", "13", "174", "59", "15", "147", "77", "25", "158", "47", "1", "146", "73", "26", "156", "91", "19", "164", "54", "43", "139", "80", "30", "166", "55", "41", "301", "18", "78", "29", "179", "57", "35", "161", "81", "37", "168", "86", "13", "147", "74", "11", "142", "77", "15", "174", "59", "1", "158", "47", "25", "306", "73", "43", "146", "91", "19", "164", "80", "26", "139", "54", "286", "166", "55", "30", "144", "81", "35", "179", "57", "29", "168", "86", "18", "161", "78", "13", "142", "77", "15", "147", "47", "37");
    private final CardRun commonB = new CardRun(true, "95", "208", "125", "185", "94", "218", "116", "194", "129", "219", "106", "227", "135", "228", "136", "223", "122", "224", "102", "210", "134", "209", "113", "211", "92", "188", "105", "199", "125", "216", "126", "218", "95", "208", "94", "194", "116", "185", "129", "219", "106", "227", "102", "223", "135", "210", "136", "228", "92", "224", "122", "211", "113", "209", "134", "199", "126", "216", "105", "188", "125", "208", "94", "185", "95", "218", "116", "194", "129", "219", "106", "227", "102", "223", "135", "224", "136", "228", "122", "210", "92", "209", "134", "199", "113", "211", "126", "188", "105", "218", "95", "216", "125", "208", "116", "185", "94", "194", "106", "227", "129", "219", "135", "210", "136", "228", "122", "223", "92", "224", "102", "211", "134", "209", "113", "199", "105", "188", "126", "216");
    private final CardRun commonC = new CardRun(true, "263", "173", "83", "96", "196", "33", "153", "260", "51", "24", "182", "292", "252", "84", "222", "39", "257", "76", "263", "155", "254", "24", "83", "96", "260", "173", "51", "33", "153", "196", "84", "252", "308", "39", "254", "155", "76", "222", "257", "114", "173", "83", "288", "263", "33", "153", "260", "196", "51", "182", "24", "252", "84", "114", "39", "257", "222", "76", "155", "254", "83", "173", "24", "260", "263", "153", "33", "96", "51", "182", "196", "252", "84", "39", "254", "155", "292", "76", "257", "222", "173", "83", "263", "96", "33", "153", "196", "260", "51", "24", "308", "252", "114", "84", "39", "222", "257", "76", "155", "254", "263", "83", "173", "24", "260", "288", "153", "33", "196", "51", "182", "39", "252", "84", "114", "254", "155", "76", "222", "257");
    private final CardRun commonDFC = new CardRun(true, "48", "10", "127", "22", "184", "203", "104", "226", "10", "66", "157", "48", "203", "22", "184", "127", "226", "66", "104", "157", "10", "48", "127", "203", "22", "184", "66", "104", "226", "10", "48", "127", "157", "22", "203", "66", "184", "226", "10", "104", "48", "157", "22", "184", "127", "203", "104", "226", "66", "10", "48", "157", "22", "127", "203", "184", "226", "104", "66", "48", "10", "203", "22", "157", "127", "184", "226", "66", "104", "157", "48", "10", "127", "22", "184", "203", "104", "66", "226", "127", "48", "10", "157", "22", "184", "203", "66", "104", "226", "10", "48", "22", "157", "203", "127", "184", "66", "226", "104", "10", "48", "22", "157", "203", "127", "184", "66", "226", "157", "104", "48", "10", "203", "22", "127", "226", "184", "66", "104", "157");
    private final CardRun uncommonA = new CardRun(true, "171", "133", "217", "67", "176", "7", "138", "189", "88", "313", "145", "4", "214", "93", "159", "56", "248", "123", "213", "180", "42", "192", "117", "52", "32", "220", "60", "230", "160", "110", "193", "85", "14", "207", "310", "169", "75", "215", "130", "241", "217", "171", "67", "133", "189", "176", "7", "299", "88", "242", "214", "145", "93", "4", "159", "213", "56", "248", "42", "192", "123", "52", "180", "32", "220", "117", "60", "160", "230", "193", "110", "14", "232", "85", "207", "169", "296", "241", "215", "75", "171", "217", "67", "133", "176", "7", "189", "88", "138", "302", "242", "214", "4", "93", "56", "159", "248", "213", "42", "123", "180", "192", "52", "32", "117", "220", "160", "230", "60", "14", "110", "193", "85", "232", "169", "207", "130", "75", "312", "215");
    private final CardRun uncommonB = new CardRun(true, "90", "2", "190", "16", "253", "82", "8", "247", "163", "3", "107", "251", "87", "250", "259", "120", "27", "112", "90", "16", "177", "247", "253", "175", "107", "2", "190", "3", "163", "82", "8", "251", "27", "120", "87", "250", "259", "175", "90", "16", "177", "112", "253", "2", "190", "247", "8", "82", "107", "3", "163", "251", "87", "120", "27", "250", "177", "112", "259", "175");
    private final CardRun uncommonDFC = new CardRun(true, "233", "256", "99", "206", "31", "289", "141", "62", "183", "128", "229", "49", "40", "167", "28", "198", "79", "9", "143", "69", "294", "234", "212", "256", "99", "233", "31", "97", "206", "300", "62", "229", "183", "49", "40", "167", "295", "28", "198", "9", "143", "79", "234", "69", "212", "121", "233", "256", "206", "99", "31", "141", "97", "62", "183", "229", "40", "49", "128", "28", "167", "198", "143", "9", "79", "69", "234", "121", "212");
    private final CardRun rare = new CardRun(false, "5", "20", "23", "36", "38", "44", "46", "53", "58", "61", "63", "64", "71", "72", "89", "98", "103", "108", "109", "111", "115", "118", "124", "140", "150", "151", "152", "154", "165", "172", "178", "186", "197", "200", "201", "202", "204", "221", "231", "237", "238", "239", "243", "244", "249", "255", "258", "261", "262", "264", "265", "266", "267", "5", "20", "23", "36", "38", "44", "46", "53", "58", "61", "63", "64", "71", "72", "89", "98", "103", "108", "109", "111", "115", "118", "124", "140", "150", "151", "152", "154", "165", "172", "178", "186", "197", "200", "201", "202", "204", "221", "231", "237", "238", "239", "243", "244", "249", "255", "258", "261", "262", "264", "265", "266", "267", "6", "17", "34", "50", "70", "100", "131", "132", "148", "149", "170", "191", "195", "240", "245");
    private final CardRun rareDFC = new CardRun(false, "21", "45", "68", "101", "137", "162", "205", "225", "235", "236", "246", "21", "45", "68", "101", "137", "162", "205", "225", "235", "236", "246", "12", "65", "119", "181", "187");
    private final CardRun land = new CardRun(false, "268", "269", "270", "271", "272", "273", "274", "275", "276", "277");

    private final BoosterStructure AAAABBBCCD = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC,
            commonDFC
    );

    private final BoosterStructure AAD = new BoosterStructure(uncommonA, uncommonA, uncommonDFC, rare);
    private final BoosterStructure ABD = new BoosterStructure(uncommonA, uncommonB, uncommonDFC, rare);
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB, rareDFC);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB, rareDFC);
    private final BoosterStructure L1 = new BoosterStructure(land);

    private final RarityConfiguration commonRuns = new RarityConfiguration(AAAABBBCCD);
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 1.45 A uncommons  (120 / 83)
    // 0.72 B uncommons  (60 / 83)
    // 0.83 DFC uncommon (69 / 83)
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAD, AAD, AAD, AAD, AAD, AAD,
            AAD, AAD, AAD, AAD, AAD, AAD,
            AAD, AAD, AAD, AAD, AAD, AAD,
            AAD, AAD, AAD, AAD, AAD, AAD,
            AAD, AAD, AAD, AAD, AAD, AAD,

            ABD, ABD, ABD, ABD, ABD, ABD,
            ABD, ABD, ABD, ABD, ABD, ABD,
            ABD, ABD, ABD, ABD, ABD, ABD,
            ABD, ABD, ABD, ABD, ABD, ABD,
            ABD, ABD, ABD, ABD, ABD, ABD,
            ABD, ABD, ABD, ABD, ABD, ABD,
            ABD, ABD, ABD,

            AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            ABB, ABB, ABB, ABB, ABB, ABB, ABB
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
