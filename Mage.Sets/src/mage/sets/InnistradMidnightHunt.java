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
public final class InnistradMidnightHunt extends ExpansionSet {

    private static final InnistradMidnightHunt instance = new InnistradMidnightHunt();

    public static InnistradMidnightHunt getInstance() {
        return instance;
    }

    private InnistradMidnightHunt() {
        super("Innistrad: Midnight Hunt", "MID", ExpansionSet.buildDate(2021, 9, 24), SetType.EXPANSION);
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

        cards.add(new SetCardInfo("Abandon the Post", 127, Rarity.COMMON, mage.cards.a.AbandonThePost.class));
        cards.add(new SetCardInfo("Adeline, Resplendent Cathar", 1, Rarity.RARE, mage.cards.a.AdelineResplendentCathar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Adeline, Resplendent Cathar", 312, Rarity.RARE, mage.cards.a.AdelineResplendentCathar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ambitious Farmhand", 2, Rarity.UNCOMMON, mage.cards.a.AmbitiousFarmhand.class));
        cards.add(new SetCardInfo("Angelfire Ignition", 209, Rarity.RARE, mage.cards.a.AngelfireIgnition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angelfire Ignition", 367, Rarity.RARE, mage.cards.a.AngelfireIgnition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angelic Enforcer", 17, Rarity.MYTHIC, mage.cards.a.AngelicEnforcer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angelic Enforcer", 327, Rarity.MYTHIC, mage.cards.a.AngelicEnforcer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcane Infusion", 210, Rarity.UNCOMMON, mage.cards.a.ArcaneInfusion.class));
        cards.add(new SetCardInfo("Archive Haunt", 68, Rarity.UNCOMMON, mage.cards.a.ArchiveHaunt.class));
        cards.add(new SetCardInfo("Ardent Elementalist", 128, Rarity.COMMON, mage.cards.a.ArdentElementalist.class));
        cards.add(new SetCardInfo("Arlinn, the Moon's Fury", 211, Rarity.MYTHIC, mage.cards.a.ArlinnTheMoonsFury.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arlinn, the Moon's Fury", 279, Rarity.MYTHIC, mage.cards.a.ArlinnTheMoonsFury.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arlinn, the Moon's Fury", 307, Rarity.MYTHIC, mage.cards.a.ArlinnTheMoonsFury.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arlinn, the Pack's Hope", 211, Rarity.MYTHIC, mage.cards.a.ArlinnThePacksHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arlinn, the Pack's Hope", 279, Rarity.MYTHIC, mage.cards.a.ArlinnThePacksHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arlinn, the Pack's Hope", 307, Rarity.MYTHIC, mage.cards.a.ArlinnThePacksHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arrogant Outlaw", 84, Rarity.COMMON, mage.cards.a.ArrogantOutlaw.class));
        cards.add(new SetCardInfo("Ashmouth Dragon", 159, Rarity.RARE, mage.cards.a.AshmouthDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashmouth Dragon", 358, Rarity.RARE, mage.cards.a.AshmouthDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Augur of Autumn", 168, Rarity.RARE, mage.cards.a.AugurOfAutumn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Augur of Autumn", 360, Rarity.RARE, mage.cards.a.AugurOfAutumn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Awoken Demon", 100, Rarity.COMMON, mage.cards.a.AwokenDemon.class));
        cards.add(new SetCardInfo("Baithook Angler", 42, Rarity.COMMON, mage.cards.b.BaithookAngler.class));
        cards.add(new SetCardInfo("Baneblade Scoundrel", 289, Rarity.UNCOMMON, mage.cards.b.BanebladeScoundrel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baneblade Scoundrel", 85, Rarity.UNCOMMON, mage.cards.b.BanebladeScoundrel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baneclaw Marauder", 289, Rarity.UNCOMMON, mage.cards.b.BaneclawMarauder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baneclaw Marauder", 85, Rarity.UNCOMMON, mage.cards.b.BaneclawMarauder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bat Whisperer", 86, Rarity.COMMON, mage.cards.b.BatWhisperer.class));
        cards.add(new SetCardInfo("Beloved Beggar", 3, Rarity.UNCOMMON, mage.cards.b.BelovedBeggar.class));
        cards.add(new SetCardInfo("Benevolent Geist", 336, Rarity.RARE, mage.cards.b.BenevolentGeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Benevolent Geist", 61, Rarity.RARE, mage.cards.b.BenevolentGeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bereaved Survivor", 4, Rarity.UNCOMMON, mage.cards.b.BereavedSurvivor.class));
        cards.add(new SetCardInfo("Bird Admirer", 169, Rarity.COMMON, mage.cards.b.BirdAdmirer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bird Admirer", 298, Rarity.COMMON, mage.cards.b.BirdAdmirer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bladebrand", 87, Rarity.COMMON, mage.cards.b.Bladebrand.class));
        cards.add(new SetCardInfo("Bladestitched Skaab", 212, Rarity.UNCOMMON, mage.cards.b.BladestitchedSkaab.class));
        cards.add(new SetCardInfo("Blessed Defiance", 5, Rarity.COMMON, mage.cards.b.BlessedDefiance.class));
        cards.add(new SetCardInfo("Blood Pact", 88, Rarity.COMMON, mage.cards.b.BloodPact.class));
        cards.add(new SetCardInfo("Bloodline Culling", 343, Rarity.RARE, mage.cards.b.BloodlineCulling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodline Culling", 89, Rarity.RARE, mage.cards.b.BloodlineCulling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodthirsty Adversary", 129, Rarity.MYTHIC, mage.cards.b.BloodthirstyAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodthirsty Adversary", 351, Rarity.MYTHIC, mage.cards.b.BloodthirstyAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodtithe Collector", 90, Rarity.UNCOMMON, mage.cards.b.BloodtitheCollector.class));
        cards.add(new SetCardInfo("Borrowed Time", 6, Rarity.UNCOMMON, mage.cards.b.BorrowedTime.class));
        cards.add(new SetCardInfo("Bounding Wolf", 170, Rarity.COMMON, mage.cards.b.BoundingWolf.class));
        cards.add(new SetCardInfo("Bramble Armor", 171, Rarity.COMMON, mage.cards.b.BrambleArmor.class));
        cards.add(new SetCardInfo("Briarbridge Tracker", 172, Rarity.RARE, mage.cards.b.BriarbridgeTracker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Briarbridge Tracker", 361, Rarity.RARE, mage.cards.b.BriarbridgeTracker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brimstone Vandal", 130, Rarity.COMMON, mage.cards.b.BrimstoneVandal.class));
        cards.add(new SetCardInfo("Brood Weaver", 173, Rarity.UNCOMMON, mage.cards.b.BroodWeaver.class));
        cards.add(new SetCardInfo("Brutal Cathar", 286, Rarity.RARE, mage.cards.b.BrutalCathar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brutal Cathar", 7, Rarity.RARE, mage.cards.b.BrutalCathar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burly Breaker", 174, Rarity.UNCOMMON, mage.cards.b.BurlyBreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burly Breaker", 299, Rarity.UNCOMMON, mage.cards.b.BurlyBreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burn Down the House", 131, Rarity.RARE, mage.cards.b.BurnDownTheHouse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burn Down the House", 352, Rarity.RARE, mage.cards.b.BurnDownTheHouse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burn the Accursed", 132, Rarity.COMMON, mage.cards.b.BurnTheAccursed.class));
        cards.add(new SetCardInfo("Can't Stay Away", 213, Rarity.RARE, mage.cards.c.CantStayAway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Can't Stay Away", 368, Rarity.RARE, mage.cards.c.CantStayAway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Candlegrove Witch", 287, Rarity.COMMON, mage.cards.c.CandlegroveWitch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Candlegrove Witch", 8, Rarity.COMMON, mage.cards.c.CandlegroveWitch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Candlelit Cavalry", 175, Rarity.COMMON, mage.cards.c.CandlelitCavalry.class));
        cards.add(new SetCardInfo("Candletrap", 9, Rarity.COMMON, mage.cards.c.Candletrap.class));
        cards.add(new SetCardInfo("Cathar Commando", 10, Rarity.COMMON, mage.cards.c.CatharCommando.class));
        cards.add(new SetCardInfo("Cathar's Call", 11, Rarity.UNCOMMON, mage.cards.c.CatharsCall.class));
        cards.add(new SetCardInfo("Cathartic Pyre", 133, Rarity.UNCOMMON, mage.cards.c.CatharticPyre.class));
        cards.add(new SetCardInfo("Celestus Sanctifier", 12, Rarity.COMMON, mage.cards.c.CelestusSanctifier.class));
        cards.add(new SetCardInfo("Champion of the Perished", 344, Rarity.RARE, mage.cards.c.ChampionOfThePerished.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Champion of the Perished", 385, Rarity.RARE, mage.cards.c.ChampionOfThePerished.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Champion of the Perished", 91, Rarity.RARE, mage.cards.c.ChampionOfThePerished.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chapel Shieldgeist", 13, Rarity.UNCOMMON, mage.cards.c.ChapelShieldgeist.class));
        cards.add(new SetCardInfo("Chaplain of Alms", 13, Rarity.UNCOMMON, mage.cards.c.ChaplainOfAlms.class));
        cards.add(new SetCardInfo("Chilling Chronicle", 63, Rarity.UNCOMMON, mage.cards.c.ChillingChronicle.class));
        cards.add(new SetCardInfo("Clarion Cathars", 14, Rarity.COMMON, mage.cards.c.ClarionCathars.class));
        cards.add(new SetCardInfo("Clear Shot", 176, Rarity.UNCOMMON, mage.cards.c.ClearShot.class));
        cards.add(new SetCardInfo("Component Collector", 43, Rarity.COMMON, mage.cards.c.ComponentCollector.class));
        cards.add(new SetCardInfo("Consider", 388, Rarity.COMMON, mage.cards.c.Consider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Consider", 44, Rarity.COMMON, mage.cards.c.Consider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Consuming Blob", 177, Rarity.MYTHIC, mage.cards.c.ConsumingBlob.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Consuming Blob", 362, Rarity.MYTHIC, mage.cards.c.ConsumingBlob.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Contortionist Troupe", 178, Rarity.UNCOMMON, mage.cards.c.ContortionistTroupe.class));
        cards.add(new SetCardInfo("Corpse Cobble", 214, Rarity.UNCOMMON, mage.cards.c.CorpseCobble.class));
        cards.add(new SetCardInfo("Covert Cutpurse", 92, Rarity.UNCOMMON, mage.cards.c.CovertCutpurse.class));
        cards.add(new SetCardInfo("Covetous Castaway", 45, Rarity.UNCOMMON, mage.cards.c.CovetousCastaway.class));
        cards.add(new SetCardInfo("Covetous Geist", 92, Rarity.UNCOMMON, mage.cards.c.CovetousGeist.class));
        cards.add(new SetCardInfo("Crawl from the Cellar", 93, Rarity.COMMON, mage.cards.c.CrawlFromTheCellar.class));
        cards.add(new SetCardInfo("Creeping Inn", 264, Rarity.MYTHIC, mage.cards.c.CreepingInn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Creeping Inn", 379, Rarity.MYTHIC, mage.cards.c.CreepingInn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Croaking Counterpart", 215, Rarity.RARE, mage.cards.c.CroakingCounterpart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Croaking Counterpart", 369, Rarity.RARE, mage.cards.c.CroakingCounterpart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crossroads Candleguide", 253, Rarity.COMMON, mage.cards.c.CrossroadsCandleguide.class));
        cards.add(new SetCardInfo("Curse of Leeches", 345, Rarity.RARE, mage.cards.c.CurseOfLeeches.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curse of Leeches", 94, Rarity.RARE, mage.cards.c.CurseOfLeeches.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curse of Shaken Faith", 134, Rarity.RARE, mage.cards.c.CurseOfShakenFaith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curse of Shaken Faith", 353, Rarity.RARE, mage.cards.c.CurseOfShakenFaith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curse of Silence", 15, Rarity.RARE, mage.cards.c.CurseOfSilence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curse of Silence", 326, Rarity.RARE, mage.cards.c.CurseOfSilence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curse of Surveillance", 334, Rarity.RARE, mage.cards.c.CurseOfSurveillance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curse of Surveillance", 46, Rarity.RARE, mage.cards.c.CurseOfSurveillance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dauntless Avenger", 4, Rarity.UNCOMMON, mage.cards.d.DauntlessAvenger.class));
        cards.add(new SetCardInfo("Dawnhart Mentor", 179, Rarity.UNCOMMON, mage.cards.d.DawnhartMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dawnhart Mentor", 300, Rarity.UNCOMMON, mage.cards.d.DawnhartMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dawnhart Rejuvenator", 180, Rarity.COMMON, mage.cards.d.DawnhartRejuvenator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dawnhart Rejuvenator", 301, Rarity.COMMON, mage.cards.d.DawnhartRejuvenator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dawnhart Wardens", 216, Rarity.UNCOMMON, mage.cards.d.DawnhartWardens.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dawnhart Wardens", 308, Rarity.UNCOMMON, mage.cards.d.DawnhartWardens.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathbonnet Hulk", 181, Rarity.UNCOMMON, mage.cards.d.DeathbonnetHulk.class));
        cards.add(new SetCardInfo("Deathbonnet Sprout", 181, Rarity.UNCOMMON, mage.cards.d.DeathbonnetSprout.class));
        cards.add(new SetCardInfo("Defend the Celestus", 182, Rarity.UNCOMMON, mage.cards.d.DefendTheCelestus.class));
        cards.add(new SetCardInfo("Defenestrate", 95, Rarity.COMMON, mage.cards.d.Defenestrate.class));
        cards.add(new SetCardInfo("Delver of Secrets", 47, Rarity.UNCOMMON, mage.cards.d.DelverOfSecrets.class));
        cards.add(new SetCardInfo("Dennick, Pious Apparition", 217, Rarity.RARE, mage.cards.d.DennickPiousApparition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dennick, Pious Apparition", 317, Rarity.RARE, mage.cards.d.DennickPiousApparition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dennick, Pious Apprentice", 217, Rarity.RARE, mage.cards.d.DennickPiousApprentice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dennick, Pious Apprentice", 317, Rarity.RARE, mage.cards.d.DennickPiousApprentice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Departed Soulkeeper", 218, Rarity.UNCOMMON, mage.cards.d.DepartedSoulkeeper.class));
        cards.add(new SetCardInfo("Deserted Beach", 260, Rarity.RARE, mage.cards.d.DesertedBeach.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deserted Beach", 281, Rarity.RARE, mage.cards.d.DesertedBeach.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Devious Cover-Up", 48, Rarity.COMMON, mage.cards.d.DeviousCoverUp.class));
        cards.add(new SetCardInfo("Devoted Grafkeeper", 218, Rarity.UNCOMMON, mage.cards.d.DevotedGrafkeeper.class));
        cards.add(new SetCardInfo("Dire-Strain Brawler", 203, Rarity.COMMON, mage.cards.d.DireStrainBrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire-Strain Brawler", 305, Rarity.COMMON, mage.cards.d.DireStrainBrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire-Strain Demolisher", 174, Rarity.UNCOMMON, mage.cards.d.DireStrainDemolisher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire-Strain Demolisher", 299, Rarity.UNCOMMON, mage.cards.d.DireStrainDemolisher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire-Strain Rampage", 219, Rarity.RARE, mage.cards.d.DireStrainRampage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire-Strain Rampage", 370, Rarity.RARE, mage.cards.d.DireStrainRampage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diregraf Horde", 96, Rarity.COMMON, mage.cards.d.DiregrafHorde.class));
        cards.add(new SetCardInfo("Diregraf Rebirth", 220, Rarity.UNCOMMON, mage.cards.d.DiregrafRebirth.class));
        cards.add(new SetCardInfo("Dissipate", 49, Rarity.UNCOMMON, mage.cards.d.Dissipate.class));
        cards.add(new SetCardInfo("Dreadhound", 97, Rarity.UNCOMMON, mage.cards.d.Dreadhound.class));
        cards.add(new SetCardInfo("Drownyard Amalgam", 50, Rarity.COMMON, mage.cards.d.DrownyardAmalgam.class));
        cards.add(new SetCardInfo("Dryad's Revival", 183, Rarity.UNCOMMON, mage.cards.d.DryadsRevival.class));
        cards.add(new SetCardInfo("Duel for Dominance", 184, Rarity.COMMON, mage.cards.d.DuelForDominance.class));
        cards.add(new SetCardInfo("Duelcraft Trainer", 16, Rarity.UNCOMMON, mage.cards.d.DuelcraftTrainer.class));
        cards.add(new SetCardInfo("Duress", 98, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Eaten Alive", 99, Rarity.COMMON, mage.cards.e.EatenAlive.class));
        cards.add(new SetCardInfo("Eccentric Farmer", 185, Rarity.COMMON, mage.cards.e.EccentricFarmer.class));
        cards.add(new SetCardInfo("Ecstatic Awakener", 100, Rarity.COMMON, mage.cards.e.EcstaticAwakener.class));
        cards.add(new SetCardInfo("Electric Revelation", 135, Rarity.COMMON, mage.cards.e.ElectricRevelation.class));
        cards.add(new SetCardInfo("Embodiment of Flame", 141, Rarity.UNCOMMON, mage.cards.e.EmbodimentOfFlame.class));
        cards.add(new SetCardInfo("Enduring Angel", 17, Rarity.MYTHIC, mage.cards.e.EnduringAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Enduring Angel", 327, Rarity.MYTHIC, mage.cards.e.EnduringAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Evolving Wilds", 261, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Fading Hope", 51, Rarity.UNCOMMON, mage.cards.f.FadingHope.class));
        cards.add(new SetCardInfo("Faithful Mending", 221, Rarity.UNCOMMON, mage.cards.f.FaithfulMending.class));
        cards.add(new SetCardInfo("Falcon Abomination", 52, Rarity.COMMON, mage.cards.f.FalconAbomination.class));
        cards.add(new SetCardInfo("Falkenrath Perforator", 136, Rarity.COMMON, mage.cards.f.FalkenrathPerforator.class));
        cards.add(new SetCardInfo("Falkenrath Pit Fighter", 137, Rarity.RARE, mage.cards.f.FalkenrathPitFighter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Falkenrath Pit Fighter", 354, Rarity.RARE, mage.cards.f.FalkenrathPitFighter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Famished Foragers", 138, Rarity.COMMON, mage.cards.f.FamishedForagers.class));
        cards.add(new SetCardInfo("Fangblade Brigand", 139, Rarity.UNCOMMON, mage.cards.f.FangbladeBrigand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fangblade Brigand", 292, Rarity.UNCOMMON, mage.cards.f.FangbladeBrigand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fangblade Eviscerator", 139, Rarity.UNCOMMON, mage.cards.f.FangbladeEviscerator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fangblade Eviscerator", 292, Rarity.UNCOMMON, mage.cards.f.FangbladeEviscerator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fateful Absence", 18, Rarity.RARE, mage.cards.f.FatefulAbsence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fateful Absence", 328, Rarity.RARE, mage.cards.f.FatefulAbsence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Festival Crasher", 140, Rarity.COMMON, mage.cards.f.FestivalCrasher.class));
        cards.add(new SetCardInfo("Field of Ruin", 262, Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Firmament Sage", 53, Rarity.UNCOMMON, mage.cards.f.FirmamentSage.class));
        cards.add(new SetCardInfo("Flame Channeler", 141, Rarity.UNCOMMON, mage.cards.f.FlameChanneler.class));
        cards.add(new SetCardInfo("Flare of Faith", 19, Rarity.COMMON, mage.cards.f.FlareOfFaith.class));
        cards.add(new SetCardInfo("Fleshtaker", 222, Rarity.UNCOMMON, mage.cards.f.Fleshtaker.class));
        cards.add(new SetCardInfo("Flip the Switch", 54, Rarity.COMMON, mage.cards.f.FlipTheSwitch.class));
        cards.add(new SetCardInfo("Florian, Voldaren Scion", 223, Rarity.RARE, mage.cards.f.FlorianVoldarenScion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Florian, Voldaren Scion", 318, Rarity.RARE, mage.cards.f.FlorianVoldarenScion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 277, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 384, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foul Play", 101, Rarity.UNCOMMON, mage.cards.f.FoulPlay.class));
        cards.add(new SetCardInfo("Frenzied Trapbreaker", 190, Rarity.UNCOMMON, mage.cards.f.FrenziedTrapbreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Trapbreaker", 303, Rarity.UNCOMMON, mage.cards.f.FrenziedTrapbreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galedrifter", 55, Rarity.COMMON, mage.cards.g.Galedrifter.class));
        cards.add(new SetCardInfo("Galvanic Iteration", 224, Rarity.RARE, mage.cards.g.GalvanicIteration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galvanic Iteration", 371, Rarity.RARE, mage.cards.g.GalvanicIteration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gavony Dawnguard", 20, Rarity.UNCOMMON, mage.cards.g.GavonyDawnguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gavony Dawnguard", 387, Rarity.UNCOMMON, mage.cards.g.GavonyDawnguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gavony Silversmith", 21, Rarity.COMMON, mage.cards.g.GavonySilversmith.class));
        cards.add(new SetCardInfo("Gavony Trapper", 22, Rarity.COMMON, mage.cards.g.GavonyTrapper.class));
        cards.add(new SetCardInfo("Geistflame Reservoir", 142, Rarity.RARE, mage.cards.g.GeistflameReservoir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geistflame Reservoir", 355, Rarity.RARE, mage.cards.g.GeistflameReservoir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geistwave", 56, Rarity.COMMON, mage.cards.g.Geistwave.class));
        cards.add(new SetCardInfo("Generous Soul", 3, Rarity.UNCOMMON, mage.cards.g.GenerousSoul.class));
        cards.add(new SetCardInfo("Ghostly Castigator", 45, Rarity.UNCOMMON, mage.cards.g.GhostlyCastigator.class));
        cards.add(new SetCardInfo("Ghoulcaller's Harvest", 225, Rarity.RARE, mage.cards.g.GhoulcallersHarvest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghoulcaller's Harvest", 372, Rarity.RARE, mage.cards.g.GhoulcallersHarvest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghoulish Procession", 102, Rarity.UNCOMMON, mage.cards.g.GhoulishProcession.class));
        cards.add(new SetCardInfo("Gisa, Glorious Resurrector", 103, Rarity.RARE, mage.cards.g.GisaGloriousResurrector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gisa, Glorious Resurrector", 314, Rarity.RARE, mage.cards.g.GisaGloriousResurrector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grafted Identity", 335, Rarity.RARE, mage.cards.g.GraftedIdentity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grafted Identity", 57, Rarity.RARE, mage.cards.g.GraftedIdentity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Graveyard Glutton", 104, Rarity.RARE, mage.cards.g.GraveyardGlutton.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Graveyard Glutton", 290, Rarity.RARE, mage.cards.g.GraveyardGlutton.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Graveyard Trespasser", 104, Rarity.RARE, mage.cards.g.GraveyardTrespasser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Graveyard Trespasser", 290, Rarity.RARE, mage.cards.g.GraveyardTrespasser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grizzly Ghoul", 226, Rarity.UNCOMMON, mage.cards.g.GrizzlyGhoul.class));
        cards.add(new SetCardInfo("Hallowed Respite", 227, Rarity.RARE, mage.cards.h.HallowedRespite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Respite", 373, Rarity.RARE, mage.cards.h.HallowedRespite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harvesttide Assailant", 143, Rarity.COMMON, mage.cards.h.HarvesttideAssailant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harvesttide Assailant", 293, Rarity.COMMON, mage.cards.h.HarvesttideAssailant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harvesttide Infiltrator", 143, Rarity.COMMON, mage.cards.h.HarvesttideInfiltrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harvesttide Infiltrator", 293, Rarity.COMMON, mage.cards.h.HarvesttideInfiltrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harvesttide Sentry", 186, Rarity.COMMON, mage.cards.h.HarvesttideSentry.class));
        cards.add(new SetCardInfo("Haunted Ridge", 263, Rarity.RARE, mage.cards.h.HauntedRidge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Haunted Ridge", 282, Rarity.RARE, mage.cards.h.HauntedRidge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hedgewitch's Mask", 23, Rarity.COMMON, mage.cards.h.HedgewitchsMask.class));
        cards.add(new SetCardInfo("Heirloom Mirror", 105, Rarity.UNCOMMON, mage.cards.h.HeirloomMirror.class));
        cards.add(new SetCardInfo("Hobbling Zombie", 106, Rarity.COMMON, mage.cards.h.HobblingZombie.class));
        cards.add(new SetCardInfo("Homestead Courage", 24, Rarity.COMMON, mage.cards.h.HomesteadCourage.class));
        cards.add(new SetCardInfo("Hook-Haunt Drifter", 42, Rarity.COMMON, mage.cards.h.HookHauntDrifter.class));
        cards.add(new SetCardInfo("Hostile Hostel", 264, Rarity.MYTHIC, mage.cards.h.HostileHostel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hostile Hostel", 379, Rarity.MYTHIC, mage.cards.h.HostileHostel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hound Tamer", 187, Rarity.UNCOMMON, mage.cards.h.HoundTamer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hound Tamer", 302, Rarity.UNCOMMON, mage.cards.h.HoundTamer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Howl of the Hunt", 188, Rarity.COMMON, mage.cards.h.HowlOfTheHunt.class));
        cards.add(new SetCardInfo("Hungry for More", 228, Rarity.UNCOMMON, mage.cards.h.HungryForMore.class));
        cards.add(new SetCardInfo("Immolation", 144, Rarity.COMMON, mage.cards.i.Immolation.class));
        cards.add(new SetCardInfo("Infernal Grasp", 107, Rarity.UNCOMMON, mage.cards.i.InfernalGrasp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Infernal Grasp", 389, Rarity.UNCOMMON, mage.cards.i.InfernalGrasp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inherited Fiend", 105, Rarity.UNCOMMON, mage.cards.i.InheritedFiend.class));
        cards.add(new SetCardInfo("Insectile Aberration", 47, Rarity.UNCOMMON, mage.cards.i.InsectileAberration.class));
        cards.add(new SetCardInfo("Intrepid Adversary", 25, Rarity.MYTHIC, mage.cards.i.IntrepidAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Intrepid Adversary", 329, Rarity.MYTHIC, mage.cards.i.IntrepidAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 271, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 381, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jack-o'-Lantern", 254, Rarity.COMMON, mage.cards.j.JackOLantern.class));
        cards.add(new SetCardInfo("Jadar, Ghoulcaller of Nephalia", 108, Rarity.RARE, mage.cards.j.JadarGhoulcallerOfNephalia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jadar, Ghoulcaller of Nephalia", 315, Rarity.RARE, mage.cards.j.JadarGhoulcallerOfNephalia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jerren, Corrupted Bishop", 109, Rarity.MYTHIC, mage.cards.j.JerrenCorruptedBishop.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jerren, Corrupted Bishop", 316, Rarity.MYTHIC, mage.cards.j.JerrenCorruptedBishop.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Join the Dance", 229, Rarity.UNCOMMON, mage.cards.j.JoinTheDance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Join the Dance", 391, Rarity.UNCOMMON, mage.cards.j.JoinTheDance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katilda, Dawnhart Prime", 230, Rarity.RARE, mage.cards.k.KatildaDawnhartPrime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katilda, Dawnhart Prime", 309, Rarity.RARE, mage.cards.k.KatildaDawnhartPrime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kessig Naturalist", 231, Rarity.UNCOMMON, mage.cards.k.KessigNaturalist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kessig Naturalist", 310, Rarity.UNCOMMON, mage.cards.k.KessigNaturalist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lambholt Harrier", 145, Rarity.COMMON, mage.cards.l.LambholtHarrier.class));
        cards.add(new SetCardInfo("Larder Zombie", 58, Rarity.COMMON, mage.cards.l.LarderZombie.class));
        cards.add(new SetCardInfo("Leeching Lurker", 345, Rarity.RARE, mage.cards.l.LeechingLurker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leeching Lurker", 94, Rarity.RARE, mage.cards.l.LeechingLurker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lier, Disciple of the Drowned", 313, Rarity.MYTHIC, mage.cards.l.LierDiscipleOfTheDrowned.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lier, Disciple of the Drowned", 59, Rarity.MYTHIC, mage.cards.l.LierDiscipleOfTheDrowned.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liesa, Forgotten Archangel", 232, Rarity.RARE, mage.cards.l.LiesaForgottenArchangel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liesa, Forgotten Archangel", 319, Rarity.RARE, mage.cards.l.LiesaForgottenArchangel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Light Up the Night", 146, Rarity.RARE, mage.cards.l.LightUpTheNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Light Up the Night", 356, Rarity.RARE, mage.cards.l.LightUpTheNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Locked in the Cemetery", 60, Rarity.COMMON, mage.cards.l.LockedInTheCemetery.class));
        cards.add(new SetCardInfo("Lord of the Forsaken", 110, Rarity.MYTHIC, mage.cards.l.LordOfTheForsaken.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lord of the Forsaken", 346, Rarity.MYTHIC, mage.cards.l.LordOfTheForsaken.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lord of the Ulvenwald", 231, Rarity.UNCOMMON, mage.cards.l.LordOfTheUlvenwald.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lord of the Ulvenwald", 310, Rarity.UNCOMMON, mage.cards.l.LordOfTheUlvenwald.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loyal Gryff", 26, Rarity.UNCOMMON, mage.cards.l.LoyalGryff.class));
        cards.add(new SetCardInfo("Ludevic, Necrogenius", 233, Rarity.RARE, mage.cards.l.LudevicNecrogenius.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ludevic, Necrogenius", 320, Rarity.RARE, mage.cards.l.LudevicNecrogenius.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Luminous Phantom", 27, Rarity.COMMON, mage.cards.l.LuminousPhantom.class));
        cards.add(new SetCardInfo("Lunar Frenzy", 147, Rarity.UNCOMMON, mage.cards.l.LunarFrenzy.class));
        cards.add(new SetCardInfo("Lunarch Veteran", 27, Rarity.COMMON, mage.cards.l.LunarchVeteran.class));
        cards.add(new SetCardInfo("Malevolent Hermit", 336, Rarity.RARE, mage.cards.m.MalevolentHermit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Malevolent Hermit", 61, Rarity.RARE, mage.cards.m.MalevolentHermit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mask of Griselbrand", 111, Rarity.RARE, mage.cards.m.MaskOfGriselbrand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mask of Griselbrand", 347, Rarity.RARE, mage.cards.m.MaskOfGriselbrand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Memory Deluge", 337, Rarity.RARE, mage.cards.m.MemoryDeluge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Memory Deluge", 62, Rarity.RARE, mage.cards.m.MemoryDeluge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Might of the Old Ways", 189, Rarity.COMMON, mage.cards.m.MightOfTheOldWays.class));
        cards.add(new SetCardInfo("Moonrage Brute", 286, Rarity.RARE, mage.cards.m.MoonrageBrute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moonrage Brute", 7, Rarity.RARE, mage.cards.m.MoonrageBrute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moonrager's Slash", 148, Rarity.COMMON, mage.cards.m.MoonragersSlash.class));
        cards.add(new SetCardInfo("Moonsilver Key", 255, Rarity.UNCOMMON, mage.cards.m.MoonsilverKey.class));
        cards.add(new SetCardInfo("Moonveil Regent", 149, Rarity.MYTHIC, mage.cards.m.MoonveilRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moonveil Regent", 357, Rarity.MYTHIC, mage.cards.m.MoonveilRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Morbid Opportunist", 113, Rarity.UNCOMMON, mage.cards.m.MorbidOpportunist.class));
        cards.add(new SetCardInfo("Morkrut Behemoth", 114, Rarity.COMMON, mage.cards.m.MorkrutBehemoth.class));
        cards.add(new SetCardInfo("Morning Apparition", 28, Rarity.COMMON, mage.cards.m.MorningApparition.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 383, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mounted Dreadknight", 150, Rarity.COMMON, mage.cards.m.MountedDreadknight.class));
        cards.add(new SetCardInfo("Mourning Patrol", 28, Rarity.COMMON, mage.cards.m.MourningPatrol.class));
        cards.add(new SetCardInfo("Mysterious Tome", 63, Rarity.UNCOMMON, mage.cards.m.MysteriousTome.class));
        cards.add(new SetCardInfo("Mystic Monstrosity", 256, Rarity.UNCOMMON, mage.cards.m.MysticMonstrosity.class));
        cards.add(new SetCardInfo("Mystic Skull", 256, Rarity.UNCOMMON, mage.cards.m.MysticSkull.class));
        cards.add(new SetCardInfo("Nebelgast Intruder", 64, Rarity.UNCOMMON, mage.cards.n.NebelgastIntruder.class));
        cards.add(new SetCardInfo("Necrosynthesis", 115, Rarity.UNCOMMON, mage.cards.n.Necrosynthesis.class));
        cards.add(new SetCardInfo("Neonate's Rush", 151, Rarity.COMMON, mage.cards.n.NeonatesRush.class));
        cards.add(new SetCardInfo("No Way Out", 116, Rarity.COMMON, mage.cards.n.NoWayOut.class));
        cards.add(new SetCardInfo("Novice Occultist", 117, Rarity.COMMON, mage.cards.n.NoviceOccultist.class));
        cards.add(new SetCardInfo("Obsessive Astronomer", 152, Rarity.UNCOMMON, mage.cards.o.ObsessiveAstronomer.class));
        cards.add(new SetCardInfo("Odric's Outrider", 29, Rarity.UNCOMMON, mage.cards.o.OdricsOutrider.class));
        cards.add(new SetCardInfo("Olag, Ludevic's Hubris", 233, Rarity.RARE, mage.cards.o.OlagLudevicsHubris.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Olag, Ludevic's Hubris", 320, Rarity.RARE, mage.cards.o.OlagLudevicsHubris.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Old Stickfingers", 234, Rarity.RARE, mage.cards.o.OldStickfingers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Old Stickfingers", 321, Rarity.RARE, mage.cards.o.OldStickfingers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Olivia's Midnight Ambush", 118, Rarity.COMMON, mage.cards.o.OliviasMidnightAmbush.class));
        cards.add(new SetCardInfo("Ominous Roost", 65, Rarity.UNCOMMON, mage.cards.o.OminousRoost.class));
        cards.add(new SetCardInfo("Organ Hoarder", 66, Rarity.COMMON, mage.cards.o.OrganHoarder.class));
        cards.add(new SetCardInfo("Ormendahl, the Corrupter", 109, Rarity.MYTHIC, mage.cards.o.OrmendahlTheCorrupter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ormendahl, the Corrupter", 316, Rarity.MYTHIC, mage.cards.o.OrmendahlTheCorrupter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Otherworldly Gaze", 67, Rarity.COMMON, mage.cards.o.OtherworldlyGaze.class));
        cards.add(new SetCardInfo("Outland Liberator", 190, Rarity.UNCOMMON, mage.cards.o.OutlandLiberator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Outland Liberator", 303, Rarity.UNCOMMON, mage.cards.o.OutlandLiberator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Farmland", 265, Rarity.RARE, mage.cards.o.OvergrownFarmland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Farmland", 283, Rarity.RARE, mage.cards.o.OvergrownFarmland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overwhelmed Archivist", 68, Rarity.UNCOMMON, mage.cards.o.OverwhelmedArchivist.class));
        cards.add(new SetCardInfo("Pack's Betrayal", 153, Rarity.COMMON, mage.cards.p.PacksBetrayal.class));
        cards.add(new SetCardInfo("Path to the Festival", 191, Rarity.COMMON, mage.cards.p.PathToTheFestival.class));
        cards.add(new SetCardInfo("Patrician Geist", 338, Rarity.RARE, mage.cards.p.PatricianGeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Patrician Geist", 69, Rarity.RARE, mage.cards.p.PatricianGeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pestilent Wolf", 192, Rarity.COMMON, mage.cards.p.PestilentWolf.class));
        cards.add(new SetCardInfo("Phantom Carriage", 70, Rarity.UNCOMMON, mage.cards.p.PhantomCarriage.class));
        cards.add(new SetCardInfo("Pithing Needle", 257, Rarity.RARE, mage.cards.p.PithingNeedle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pithing Needle", 378, Rarity.RARE, mage.cards.p.PithingNeedle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 269, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 380, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Play with Fire", 154, Rarity.UNCOMMON, mage.cards.p.PlayWithFire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Play with Fire", 390, Rarity.UNCOMMON, mage.cards.p.PlayWithFire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plummet", 193, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Poppet Factory", 339, Rarity.MYTHIC, mage.cards.p.PoppetFactory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Poppet Factory", 71, Rarity.MYTHIC, mage.cards.p.PoppetFactory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Poppet Stitcher", 339, Rarity.MYTHIC, mage.cards.p.PoppetStitcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Poppet Stitcher", 71, Rarity.MYTHIC, mage.cards.p.PoppetStitcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Primal Adversary", 194, Rarity.MYTHIC, mage.cards.p.PrimalAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Primal Adversary", 363, Rarity.MYTHIC, mage.cards.p.PrimalAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purifying Dragon", 155, Rarity.UNCOMMON, mage.cards.p.PurifyingDragon.class));
        cards.add(new SetCardInfo("Raze the Effigy", 156, Rarity.COMMON, mage.cards.r.RazeTheEffigy.class));
        cards.add(new SetCardInfo("Reckless Stormseeker", 157, Rarity.RARE, mage.cards.r.RecklessStormseeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reckless Stormseeker", 294, Rarity.RARE, mage.cards.r.RecklessStormseeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rem Karolus, Stalwart Slayer", 235, Rarity.RARE, mage.cards.r.RemKarolusStalwartSlayer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rem Karolus, Stalwart Slayer", 322, Rarity.RARE, mage.cards.r.RemKarolusStalwartSlayer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Return to Nature", 195, Rarity.COMMON, mage.cards.r.ReturnToNature.class));
        cards.add(new SetCardInfo("Revenge of the Drowned", 72, Rarity.COMMON, mage.cards.r.RevengeOfTheDrowned.class));
        cards.add(new SetCardInfo("Rise of the Ants", 196, Rarity.UNCOMMON, mage.cards.r.RiseOfTheAnts.class));
        cards.add(new SetCardInfo("Rite of Harmony", 236, Rarity.RARE, mage.cards.r.RiteOfHarmony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rite of Harmony", 374, Rarity.RARE, mage.cards.r.RiteOfHarmony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rite of Oblivion", 237, Rarity.UNCOMMON, mage.cards.r.RiteOfOblivion.class));
        cards.add(new SetCardInfo("Ritual Guardian", 30, Rarity.COMMON, mage.cards.r.RitualGuardian.class));
        cards.add(new SetCardInfo("Ritual of Hope", 31, Rarity.UNCOMMON, mage.cards.r.RitualOfHope.class));
        cards.add(new SetCardInfo("Rockfall Vale", 266, Rarity.RARE, mage.cards.r.RockfallVale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rockfall Vale", 284, Rarity.RARE, mage.cards.r.RockfallVale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rootcoil Creeper", 238, Rarity.UNCOMMON, mage.cards.r.RootcoilCreeper.class));
        cards.add(new SetCardInfo("Rotten Reunion", 119, Rarity.COMMON, mage.cards.r.RottenReunion.class));
        cards.add(new SetCardInfo("Sacred Fire", 239, Rarity.UNCOMMON, mage.cards.s.SacredFire.class));
        cards.add(new SetCardInfo("Saryth, the Viper's Fang", 197, Rarity.RARE, mage.cards.s.SarythTheVipersFang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saryth, the Viper's Fang", 304, Rarity.RARE, mage.cards.s.SarythTheVipersFang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seafaring Werewolf", 288, Rarity.RARE, mage.cards.s.SeafaringWerewolf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seafaring Werewolf", 80, Rarity.RARE, mage.cards.s.SeafaringWerewolf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Search Party Captain", 32, Rarity.COMMON, mage.cards.s.SearchPartyCaptain.class));
        cards.add(new SetCardInfo("Seasoned Cathar", 2, Rarity.UNCOMMON, mage.cards.s.SeasonedCathar.class));
        cards.add(new SetCardInfo("Secrets of the Key", 73, Rarity.COMMON, mage.cards.s.SecretsOfTheKey.class));
        cards.add(new SetCardInfo("Seize the Storm", 158, Rarity.UNCOMMON, mage.cards.s.SeizeTheStorm.class));
        cards.add(new SetCardInfo("Shadowbeast Sighting", 198, Rarity.COMMON, mage.cards.s.ShadowbeastSighting.class));
        cards.add(new SetCardInfo("Shady Traveler", 120, Rarity.COMMON, mage.cards.s.ShadyTraveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shady Traveler", 291, Rarity.COMMON, mage.cards.s.ShadyTraveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shipwreck Marsh", 267, Rarity.RARE, mage.cards.s.ShipwreckMarsh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shipwreck Marsh", 285, Rarity.RARE, mage.cards.s.ShipwreckMarsh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shipwreck Sifters", 74, Rarity.COMMON, mage.cards.s.ShipwreckSifters.class));
        cards.add(new SetCardInfo("Siege Zombie", 121, Rarity.COMMON, mage.cards.s.SiegeZombie.class));
        cards.add(new SetCardInfo("Sigarda's Splendor", 33, Rarity.RARE, mage.cards.s.SigardasSplendor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigarda's Splendor", 330, Rarity.RARE, mage.cards.s.SigardasSplendor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigarda, Champion of Light", 240, Rarity.MYTHIC, mage.cards.s.SigardaChampionOfLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigarda, Champion of Light", 323, Rarity.MYTHIC, mage.cards.s.SigardaChampionOfLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigardian Savior", 331, Rarity.MYTHIC, mage.cards.s.SigardianSavior.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigardian Savior", 34, Rarity.MYTHIC, mage.cards.s.SigardianSavior.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silver Bolt", 258, Rarity.COMMON, mage.cards.s.SilverBolt.class));
        cards.add(new SetCardInfo("Siphon Insight", 241, Rarity.RARE, mage.cards.s.SiphonInsight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Siphon Insight", 375, Rarity.RARE, mage.cards.s.SiphonInsight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skaab Wrangler", 75, Rarity.UNCOMMON, mage.cards.s.SkaabWrangler.class));
        cards.add(new SetCardInfo("Slaughter Specialist", 122, Rarity.RARE, mage.cards.s.SlaughterSpecialist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slaughter Specialist", 349, Rarity.RARE, mage.cards.s.SlaughterSpecialist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slogurk, the Overslime", 242, Rarity.RARE, mage.cards.s.SlogurkTheOverslime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slogurk, the Overslime", 324, Rarity.RARE, mage.cards.s.SlogurkTheOverslime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sludge Monster", 340, Rarity.RARE, mage.cards.s.SludgeMonster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sludge Monster", 76, Rarity.RARE, mage.cards.s.SludgeMonster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smoldering Egg", 159, Rarity.RARE, mage.cards.s.SmolderingEgg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smoldering Egg", 358, Rarity.RARE, mage.cards.s.SmolderingEgg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Snarling Wolf", 199, Rarity.COMMON, mage.cards.s.SnarlingWolf.class));
        cards.add(new SetCardInfo("Soul-Guide Gryff", 35, Rarity.COMMON, mage.cards.s.SoulGuideGryff.class));
        cards.add(new SetCardInfo("Spectral Adversary", 341, Rarity.MYTHIC, mage.cards.s.SpectralAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectral Adversary", 77, Rarity.MYTHIC, mage.cards.s.SpectralAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spellrune Howler", 160, Rarity.UNCOMMON, mage.cards.s.SpellruneHowler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spellrune Howler", 295, Rarity.UNCOMMON, mage.cards.s.SpellruneHowler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spellrune Painter", 160, Rarity.UNCOMMON, mage.cards.s.SpellrunePainter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spellrune Painter", 295, Rarity.UNCOMMON, mage.cards.s.SpellrunePainter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stalking Predator", 120, Rarity.COMMON, mage.cards.s.StalkingPredator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stalking Predator", 291, Rarity.COMMON, mage.cards.s.StalkingPredator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Startle", 78, Rarity.COMMON, mage.cards.s.Startle.class));
        cards.add(new SetCardInfo("Stolen Vitality", 161, Rarity.COMMON, mage.cards.s.StolenVitality.class));
        cards.add(new SetCardInfo("Storm Skreelix", 243, Rarity.UNCOMMON, mage.cards.s.StormSkreelix.class));
        cards.add(new SetCardInfo("Storm the Festival", 200, Rarity.RARE, mage.cards.s.StormTheFestival.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Storm the Festival", 364, Rarity.RARE, mage.cards.s.StormTheFestival.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Storm-Charged Slasher", 157, Rarity.RARE, mage.cards.s.StormChargedSlasher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Storm-Charged Slasher", 294, Rarity.RARE, mage.cards.s.StormChargedSlasher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stormrider Spirit", 79, Rarity.COMMON, mage.cards.s.StormriderSpirit.class));
        cards.add(new SetCardInfo("Strangling Grasp", 126, Rarity.UNCOMMON, mage.cards.s.StranglingGrasp.class));
        cards.add(new SetCardInfo("Stromkirk Bloodthief", 123, Rarity.UNCOMMON, mage.cards.s.StromkirkBloodthief.class));
        cards.add(new SetCardInfo("Stuffed Bear", 259, Rarity.COMMON, mage.cards.s.StuffedBear.class));
        cards.add(new SetCardInfo("Sungold Barrage", 36, Rarity.COMMON, mage.cards.s.SungoldBarrage.class));
        cards.add(new SetCardInfo("Sungold Sentinel", 332, Rarity.RARE, mage.cards.s.SungoldSentinel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sungold Sentinel", 37, Rarity.RARE, mage.cards.s.SungoldSentinel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunrise Cavalier", 244, Rarity.UNCOMMON, mage.cards.s.SunriseCavalier.class));
        cards.add(new SetCardInfo("Sunset Revelry", 38, Rarity.UNCOMMON, mage.cards.s.SunsetRevelry.class));
        cards.add(new SetCardInfo("Sunstreak Phoenix", 162, Rarity.MYTHIC, mage.cards.s.SunstreakPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunstreak Phoenix", 359, Rarity.MYTHIC, mage.cards.s.SunstreakPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Suspicious Stowaway", 288, Rarity.RARE, mage.cards.s.SuspiciousStowaway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Suspicious Stowaway", 80, Rarity.RARE, mage.cards.s.SuspiciousStowaway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 273, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 382, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tainted Adversary", 124, Rarity.MYTHIC, mage.cards.t.TaintedAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tainted Adversary", 350, Rarity.MYTHIC, mage.cards.t.TaintedAdversary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tapping at the Window", 201, Rarity.COMMON, mage.cards.t.TappingAtTheWindow.class));
        cards.add(new SetCardInfo("Tavern Ruffian", 163, Rarity.COMMON, mage.cards.t.TavernRuffian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tavern Ruffian", 296, Rarity.COMMON, mage.cards.t.TavernRuffian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tavern Smasher", 163, Rarity.COMMON, mage.cards.t.TavernSmasher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tavern Smasher", 296, Rarity.COMMON, mage.cards.t.TavernSmasher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Who Slows the Sunset", 245, Rarity.MYTHIC, mage.cards.t.TeferiWhoSlowsTheSunset.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Who Slows the Sunset", 280, Rarity.MYTHIC, mage.cards.t.TeferiWhoSlowsTheSunset.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Celestus", 252, Rarity.RARE, mage.cards.t.TheCelestus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Celestus", 377, Rarity.RARE, mage.cards.t.TheCelestus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Meathook Massacre", 112, Rarity.MYTHIC, mage.cards.t.TheMeathookMassacre.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Meathook Massacre", 348, Rarity.MYTHIC, mage.cards.t.TheMeathookMassacre.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thermo-Alchemist", 164, Rarity.UNCOMMON, mage.cards.t.ThermoAlchemist.class));
        cards.add(new SetCardInfo("Thraben Exorcism", 39, Rarity.COMMON, mage.cards.t.ThrabenExorcism.class));
        cards.add(new SetCardInfo("Timberland Guide", 202, Rarity.COMMON, mage.cards.t.TimberlandGuide.class));
        cards.add(new SetCardInfo("Tireless Hauler", 203, Rarity.COMMON, mage.cards.t.TirelessHauler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tireless Hauler", 305, Rarity.COMMON, mage.cards.t.TirelessHauler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tovolar's Huntmaster", 204, Rarity.RARE, mage.cards.t.TovolarsHuntmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tovolar's Huntmaster", 306, Rarity.RARE, mage.cards.t.TovolarsHuntmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tovolar's Packleader", 204, Rarity.RARE, mage.cards.t.TovolarsPackleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tovolar's Packleader", 306, Rarity.RARE, mage.cards.t.TovolarsPackleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tovolar, Dire Overlord", 246, Rarity.RARE, mage.cards.t.TovolarDireOverlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tovolar, Dire Overlord", 311, Rarity.RARE, mage.cards.t.TovolarDireOverlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tovolar, the Midnight Scourge", 246, Rarity.RARE, mage.cards.t.TovolarTheMidnightScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tovolar, the Midnight Scourge", 311, Rarity.RARE, mage.cards.t.TovolarTheMidnightScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Triskaidekaphile", 342, Rarity.RARE, mage.cards.t.Triskaidekaphile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Triskaidekaphile", 386, Rarity.RARE, mage.cards.t.Triskaidekaphile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Triskaidekaphile", 81, Rarity.RARE, mage.cards.t.Triskaidekaphile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turn the Earth", 205, Rarity.UNCOMMON, mage.cards.t.TurnTheEarth.class));
        cards.add(new SetCardInfo("Unblinking Observer", 82, Rarity.COMMON, mage.cards.u.UnblinkingObserver.class));
        cards.add(new SetCardInfo("Unnatural Growth", 206, Rarity.RARE, mage.cards.u.UnnaturalGrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unnatural Growth", 365, Rarity.RARE, mage.cards.u.UnnaturalGrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unnatural Moonrise", 247, Rarity.UNCOMMON, mage.cards.u.UnnaturalMoonrise.class));
        cards.add(new SetCardInfo("Unruly Mob", 40, Rarity.COMMON, mage.cards.u.UnrulyMob.class));
        cards.add(new SetCardInfo("Untamed Pup", 187, Rarity.UNCOMMON, mage.cards.u.UntamedPup.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Untamed Pup", 302, Rarity.UNCOMMON, mage.cards.u.UntamedPup.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vadrik, Astral Archmage", 248, Rarity.RARE, mage.cards.v.VadrikAstralArchmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vadrik, Astral Archmage", 325, Rarity.RARE, mage.cards.v.VadrikAstralArchmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vampire Interloper", 125, Rarity.COMMON, mage.cards.v.VampireInterloper.class));
        cards.add(new SetCardInfo("Vampire Socialite", 249, Rarity.UNCOMMON, mage.cards.v.VampireSocialite.class));
        cards.add(new SetCardInfo("Vanquish the Horde", 333, Rarity.RARE, mage.cards.v.VanquishTheHorde.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanquish the Horde", 41, Rarity.RARE, mage.cards.v.VanquishTheHorde.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vengeful Strangler", 126, Rarity.UNCOMMON, mage.cards.v.VengefulStrangler.class));
        cards.add(new SetCardInfo("Village Reavers", 165, Rarity.UNCOMMON, mage.cards.v.VillageReavers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Village Reavers", 297, Rarity.UNCOMMON, mage.cards.v.VillageReavers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Village Watch", 165, Rarity.UNCOMMON, mage.cards.v.VillageWatch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Village Watch", 297, Rarity.UNCOMMON, mage.cards.v.VillageWatch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vivisection", 83, Rarity.UNCOMMON, mage.cards.v.Vivisection.class));
        cards.add(new SetCardInfo("Voldaren Ambusher", 166, Rarity.UNCOMMON, mage.cards.v.VoldarenAmbusher.class));
        cards.add(new SetCardInfo("Voldaren Stinger", 167, Rarity.COMMON, mage.cards.v.VoldarenStinger.class));
        cards.add(new SetCardInfo("Waildrifter", 55, Rarity.COMMON, mage.cards.w.Waildrifter.class));
        cards.add(new SetCardInfo("Wake to Slaughter", 250, Rarity.RARE, mage.cards.w.WakeToSlaughter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wake to Slaughter", 376, Rarity.RARE, mage.cards.w.WakeToSlaughter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Willow Geist", 207, Rarity.RARE, mage.cards.w.WillowGeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Willow Geist", 366, Rarity.RARE, mage.cards.w.WillowGeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wing Shredder", 169, Rarity.COMMON, mage.cards.w.WingShredder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wing Shredder", 298, Rarity.COMMON, mage.cards.w.WingShredder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Winterthorn Blessing", 251, Rarity.UNCOMMON, mage.cards.w.WinterthornBlessing.class));
        cards.add(new SetCardInfo("Wrenn and Seven", 208, Rarity.MYTHIC, mage.cards.w.WrennAndSeven.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrenn and Seven", 278, Rarity.MYTHIC, mage.cards.w.WrennAndSeven.class, NON_FULL_USE_VARIOUS));
    }

    // add common double faced card to booster
    @Override
    protected void addDoubleFace(List<Card> booster) {
        addToBooster(booster, getSpecialCardsByRarity(Rarity.COMMON));
    }

    @Override
    public BoosterCollator createCollator() {
        return new InnistradMidnightHuntCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/mid.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class InnistradMidnightHuntCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "202", "23", "48", "125", "184", "153", "74", "116", "171", "259", "52", "87", "201", "135", "79", "98", "189", "253", "54", "114", "175", "35", "44", "121", "193", "136", "73", "86", "199", "19", "253", "67", "117", "186", "151", "82", "88", "170", "5", "50", "119", "192", "261", "58", "96", "191", "39", "56", "84", "195", "127", "78", "118", "188", "12", "43", "93", "184", "259", "79", "116", "171", "153", "74", "98", "202", "258", "52", "125", "189", "23", "135", "48", "87", "201", "35", "54", "121", "199", "254", "44", "114", "191", "19", "136", "73", "86", "193", "261", "82", "96", "175", "5", "254", "58", "117", "186", "151", "78", "119", "170", "39", "50", "88", "192", "258", "67", "118", "188", "127", "56", "84", "195", "12", "43", "93");
    private final CardRun commonB = new CardRun(true, "140", "198", "32", "145", "60", "22", "167", "106", "9", "128", "180", "287", "130", "66", "24", "161", "95", "40", "144", "185", "30", "148", "150", "21", "138", "72", "14", "156", "99", "36", "140", "60", "10", "132", "301", "8", "145", "106", "22", "167", "198", "32", "130", "66", "40", "128", "150", "9", "148", "95", "24", "161", "185", "14", "156", "72", "30", "132", "144", "36", "138", "99", "10", "140", "198", "32", "145", "60", "22", "128", "180", "21", "167", "106", "40", "130", "66", "24", "144", "95", "9", "148", "150", "8", "161", "185", "30", "138", "72", "14", "156", "99", "36", "132", "10", "21");
    private final CardRun commonDFC = new CardRun(true, "100", "55", "169", "293", "28", "120", "42", "305", "143", "27", "100", "55", "298", "163", "28", "120", "42", "203", "296", "27", "100", "55", "169", "143", "28", "291", "42", "203", "163", "27");
    private final CardRun uncommonA = new CardRun(true, "38", "53", "210", "97", "49", "26", "51", "115", "20", "70", "228", "113", "16", "75", "205", "90", "11", "83", "255", "123", "29", "65", "102", "31", "53", "262", "101", "38", "64", "210", "49", "20", "97", "51", "228", "90", "26", "75", "115", "16", "83", "205", "113", "11", "65", "102", "29", "70", "255", "31", "123", "64", "262", "97", "38", "53", "101", "20", "49", "210", "51", "115", "26", "75", "228", "113", "16", "83", "205", "90", "11", "65", "102", "31", "70", "255", "123", "29", "53", "262", "97", "38", "64", "210", "101", "49", "20", "51", "115", "26", "75", "228", "90", "16", "65", "205", "113", "11", "83", "102", "31", "70", "255", "123", "29", "64", "262", "101");
    private final CardRun uncommonB = new CardRun(true, "107", "229", "183", "237", "152", "238", "176", "220", "158", "216", "300", "222", "133", "244", "182", "226", "164", "251", "6", "212", "147", "247", "196", "239", "166", "249", "176", "243", "152", "214", "178", "154", "107", "221", "179", "238", "155", "229", "173", "237", "164", "216", "183", "220", "158", "239", "182", "222", "147", "226", "6", "244", "166", "212", "196", "251", "133", "247", "152", "237", "178", "243", "155", "249", "173", "214", "107", "229", "179", "238", "154", "221", "176", "220", "164", "308", "183", "226", "133", "244", "182", "222", "147", "212", "6", "247", "158", "251", "196", "239", "166", "243", "178", "249", "155", "221", "173", "154", "214");
    private final CardRun uncommonDFC1 = new CardRun(true, "190", "231", "139", "92", "302", "68", "160", "218", "299", "2", "165", "310", "190", "92", "292", "68", "187", "85", "160", "231", "174", "85", "165", "2", "303", "218", "139", "289", "187", "92", "295", "68", "174", "218", "297", "2");
    private final CardRun uncommonDFC2 = new CardRun(false, "3", "4", "13", "45", "47", "63", "105", "126", "141", "181", "256");
    private final CardRun rare = new CardRun(false, "1", "15", "18", "33", "37", "41", "46", "57", "62", "69", "76", "81", "89", "91", "103", "108", "111", "122", "131", "134", "137", "142", "146", "168", "172", "197", "200", "206", "207", "209", "213", "215", "219", "223", "224", "225", "227", "230", "232", "234", "235", "236", "241", "242", "248", "250", "252", "257", "260", "263", "265", "266", "267", "1", "15", "18", "33", "37", "41", "46", "57", "62", "69", "76", "81", "89", "91", "103", "108", "111", "122", "131", "134", "137", "142", "146", "168", "172", "197", "200", "206", "207", "209", "213", "215", "219", "223", "224", "225", "227", "230", "232", "234", "235", "236", "241", "242", "248", "250", "252", "257", "260", "263", "265", "266", "267", "25", "34", "59", "77", "110", "112", "124", "129", "149", "162", "177", "194", "208", "240", "245");
    private final CardRun rareDFC = new CardRun(false, "7", "61", "80", "94", "104", "157", "159", "204", "217", "233", "246", "7", "61", "80", "94", "104", "157", "159", "204", "217", "233", "246", "17", "71", "109", "211", "264");
    private final CardRun land = new CardRun(false, "268", "269", "270", "271", "272", "273", "274", "275", "276", "277");

    private final BoosterStructure AAAAABBBBD = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonDFC
    );
    private final BoosterStructure AAAAAABBBD = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonDFC
    );
    private final BoosterStructure ABD1 = new BoosterStructure(uncommonA, uncommonB, uncommonDFC1, rare);
    private final BoosterStructure BBD1 = new BoosterStructure(uncommonB, uncommonB, uncommonDFC1, rare);
    private final BoosterStructure ABD2 = new BoosterStructure(uncommonA, uncommonB, uncommonDFC2, rare);
    private final BoosterStructure BBD2 = new BoosterStructure(uncommonB, uncommonB, uncommonDFC2, rare);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB, rareDFC);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 5.8 A commons (58 / 10)
    // 3.2 B commons (32 / 10)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAAAABBBBD, AAAAABBBBD,
            AAAAAABBBD, AAAAAABBBD, AAAAAABBBD, AAAAAABBBD,
            AAAAAABBBD, AAAAAABBBD, AAAAAABBBD, AAAAAABBBD
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.98 A uncommons   (81 / 83)
    // 1.19 B uncommons   (99 / 83)
    // 0.43 DFC1 uncommon (36 / 83)
    // 0.40 DFC2 uncommon (33 / 83)
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABD1, ABD1, ABD1, ABD1, ABD1, ABD1,
            ABD1, ABD1, ABD1, ABD1, ABD1, ABD1,
            ABD1, ABD1, ABD1, ABD1, ABD1, ABD1,
            ABD1, ABD1, ABD1, ABD1, ABD1, ABD1,
            ABD1, ABD1, ABD1, ABD1, ABD1, ABD1,
            ABD1, ABD1, ABD1, ABD1, ABD1, BBD1,

            ABD2, ABD2, ABD2, ABD2, ABD2, ABD2,
            ABD2, ABD2, ABD2, ABD2, ABD2, ABD2,
            ABD2, ABD2, ABD2, ABD2, ABD2, ABD2,
            ABD2, ABD2, ABD2, ABD2, ABD2, ABD2,
            ABD2, ABD2, ABD2, ABD2, ABD2, ABD2,
            ABD2, ABD2, BBD2,

            ABB, ABB, ABB, ABB, ABB, ABB, ABB,
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
