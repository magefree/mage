package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/psoi
 */
public class ShadowsOverInnistradPromos extends ExpansionSet {

    private static final ShadowsOverInnistradPromos instance = new ShadowsOverInnistradPromos();

    public static ShadowsOverInnistradPromos getInstance() {
        return instance;
    }

    private ShadowsOverInnistradPromos() {
        super("Shadows over Innistrad Promos", "PSOI", ExpansionSet.buildDate(2016, 4, 9), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Altered Ego", "241s", Rarity.RARE, mage.cards.a.AlteredEgo.class));
        cards.add(new SetCardInfo("Always Watching", "1s", Rarity.RARE, mage.cards.a.AlwaysWatching.class));
        cards.add(new SetCardInfo("Angel of Deliverance", "2s", Rarity.RARE, mage.cards.a.AngelOfDeliverance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angel of Deliverance", 2, Rarity.RARE, mage.cards.a.AngelOfDeliverance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anguished Unmaking", "242p", Rarity.RARE, mage.cards.a.AnguishedUnmaking.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anguished Unmaking", "242s", Rarity.RARE, mage.cards.a.AnguishedUnmaking.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anguished Unmaking", 242, Rarity.RARE, mage.cards.a.AnguishedUnmaking.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archangel Avacyn", "5s", Rarity.MYTHIC, mage.cards.a.ArchangelAvacyn.class));
        cards.add(new SetCardInfo("Arlinn Kord", "243s", Rarity.MYTHIC, mage.cards.a.ArlinnKord.class));
        cards.add(new SetCardInfo("Arlinn, Embraced by the Moon", "243s", Rarity.MYTHIC, mage.cards.a.ArlinnEmbracedByTheMoon.class));
        cards.add(new SetCardInfo("Asylum Visitor", "99s", Rarity.RARE, mage.cards.a.AsylumVisitor.class));
        cards.add(new SetCardInfo("Avacyn's Judgment", "145s", Rarity.RARE, mage.cards.a.AvacynsJudgment.class));
        cards.add(new SetCardInfo("Avacyn, the Purifier", "5s", Rarity.MYTHIC, mage.cards.a.AvacynThePurifier.class));
        cards.add(new SetCardInfo("Awoken Horror", "92s", Rarity.RARE, mage.cards.a.AwokenHorror.class));
        cards.add(new SetCardInfo("Behold the Beyond", "101s", Rarity.MYTHIC, mage.cards.b.BeholdTheBeyond.class));
        cards.add(new SetCardInfo("Brain in a Jar", "252s", Rarity.RARE, mage.cards.b.BrainInAJar.class));
        cards.add(new SetCardInfo("Burn from Within", "148s", Rarity.RARE, mage.cards.b.BurnFromWithin.class));
        cards.add(new SetCardInfo("Bygone Bishop", "8s", Rarity.RARE, mage.cards.b.BygoneBishop.class));
        cards.add(new SetCardInfo("Choked Estuary", "270s", Rarity.RARE, mage.cards.c.ChokedEstuary.class));
        cards.add(new SetCardInfo("Confirm Suspicions", "53s", Rarity.RARE, mage.cards.c.ConfirmSuspicions.class));
        cards.add(new SetCardInfo("Corrupted Grafstone", "253s", Rarity.RARE, mage.cards.c.CorruptedGrafstone.class));
        cards.add(new SetCardInfo("Cryptolith Rite", "200s", Rarity.RARE, mage.cards.c.CryptolithRite.class));
        cards.add(new SetCardInfo("Deathcap Cultivator", "202s", Rarity.RARE, mage.cards.d.DeathcapCultivator.class));
        cards.add(new SetCardInfo("Declaration in Stone", "12s", Rarity.RARE, mage.cards.d.DeclarationInStone.class));
        cards.add(new SetCardInfo("Descend upon the Sinful", "13s", Rarity.MYTHIC, mage.cards.d.DescendUponTheSinful.class));
        cards.add(new SetCardInfo("Devils' Playground", "151s", Rarity.RARE, mage.cards.d.DevilsPlayground.class));
        cards.add(new SetCardInfo("Diregraf Colossus", "107s", Rarity.RARE, mage.cards.d.DiregrafColossus.class));
        cards.add(new SetCardInfo("Drogskol Cavalry", "15s", Rarity.RARE, mage.cards.d.DrogskolCavalry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drogskol Cavalry", 15, Rarity.RARE, mage.cards.d.DrogskolCavalry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drownyard Temple", "271s", Rarity.RARE, mage.cards.d.DrownyardTemple.class));
        cards.add(new SetCardInfo("Eerie Interlude", "16p", Rarity.RARE, mage.cards.e.EerieInterlude.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eerie Interlude", "16s", Rarity.RARE, mage.cards.e.EerieInterlude.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elusive Tormentor", "108s", Rarity.RARE, mage.cards.e.ElusiveTormentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elusive Tormentor", 108, Rarity.RARE, mage.cards.e.ElusiveTormentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Engulf the Shore", "58s", Rarity.RARE, mage.cards.e.EngulfTheShore.class));
        cards.add(new SetCardInfo("Epiphany at the Drownyard", "59s", Rarity.RARE, mage.cards.e.EpiphanyAtTheDrownyard.class));
        cards.add(new SetCardInfo("Ever After", "109s", Rarity.RARE, mage.cards.e.EverAfter.class));
        cards.add(new SetCardInfo("Falkenrath Gorger", "155s", Rarity.RARE, mage.cards.f.FalkenrathGorger.class));
        cards.add(new SetCardInfo("Fevered Visions", "244s", Rarity.RARE, mage.cards.f.FeveredVisions.class));
        cards.add(new SetCardInfo("Flameblade Angel", "157s", Rarity.RARE, mage.cards.f.FlamebladeAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flameblade Angel", 157, Rarity.RARE, mage.cards.f.FlamebladeAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foreboding Ruins", "272s", Rarity.RARE, mage.cards.f.ForebodingRuins.class));
        cards.add(new SetCardInfo("Forgotten Creation", "63s", Rarity.RARE, mage.cards.f.ForgottenCreation.class));
        cards.add(new SetCardInfo("Fortified Village", "274s", Rarity.RARE, mage.cards.f.FortifiedVillage.class));
        cards.add(new SetCardInfo("From Under the Floorboards", "111s", Rarity.RARE, mage.cards.f.FromUnderTheFloorboards.class));
        cards.add(new SetCardInfo("Game Trail", "276s", Rarity.RARE, mage.cards.g.GameTrail.class));
        cards.add(new SetCardInfo("Geier Reach Bandit", "159s", Rarity.RARE, mage.cards.g.GeierReachBandit.class));
        cards.add(new SetCardInfo("Geralf's Masterpiece", "65s", Rarity.MYTHIC, mage.cards.g.GeralfsMasterpiece.class));
        cards.add(new SetCardInfo("Goldnight Castigator", "162s", Rarity.MYTHIC, mage.cards.g.GoldnightCastigator.class));
        cards.add(new SetCardInfo("Hanweir Militia Captain", "21s", Rarity.RARE, mage.cards.h.HanweirMilitiaCaptain.class));
        cards.add(new SetCardInfo("Harness the Storm", "163s", Rarity.RARE, mage.cards.h.HarnessTheStorm.class));
        cards.add(new SetCardInfo("Incorrigible Youths", 166, Rarity.UNCOMMON, mage.cards.i.IncorrigibleYouths.class));
        cards.add(new SetCardInfo("Inexorable Blob", "212s", Rarity.RARE, mage.cards.i.InexorableBlob.class));
        cards.add(new SetCardInfo("Insidious Mist", "108s", Rarity.RARE, mage.cards.i.InsidiousMist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Insidious Mist", 108, Rarity.RARE, mage.cards.i.InsidiousMist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invocation of Saint Traft", "246s", Rarity.RARE, mage.cards.i.InvocationOfSaintTraft.class));
        cards.add(new SetCardInfo("Jace, Unraveler of Secrets", "69s", Rarity.MYTHIC, mage.cards.j.JaceUnravelerOfSecrets.class));
        cards.add(new SetCardInfo("Markov Dreadknight", "122s", Rarity.RARE, mage.cards.m.MarkovDreadknight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Markov Dreadknight", 122, Rarity.RARE, mage.cards.m.MarkovDreadknight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mindwrack Demon", "124s", Rarity.MYTHIC, mage.cards.m.MindwrackDemon.class));
        cards.add(new SetCardInfo("Nahiri, the Harbinger", "247s", Rarity.MYTHIC, mage.cards.n.NahiriTheHarbinger.class));
        cards.add(new SetCardInfo("Nephalia Moondrakes", "75s", Rarity.RARE, mage.cards.n.NephaliaMoondrakes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nephalia Moondrakes", 75, Rarity.RARE, mage.cards.n.NephaliaMoondrakes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Odric, Lunarch Marshal", "31s", Rarity.RARE, mage.cards.o.OdricLunarchMarshal.class));
        cards.add(new SetCardInfo("Olivia, Mobilized for War", "248s", Rarity.MYTHIC, mage.cards.o.OliviaMobilizedForWar.class));
        cards.add(new SetCardInfo("Ormendahl, Profane Prince", "281s", Rarity.RARE, mage.cards.o.OrmendahlProfanePrince.class));
        cards.add(new SetCardInfo("Persistent Nightmare", "88s", Rarity.MYTHIC, mage.cards.p.PersistentNightmare.class));
        cards.add(new SetCardInfo("Port Town", "278s", Rarity.RARE, mage.cards.p.PortTown.class));
        cards.add(new SetCardInfo("Prized Amalgam", "249s", Rarity.RARE, mage.cards.p.PrizedAmalgam.class));
        cards.add(new SetCardInfo("Rattlechains", "81s", Rarity.RARE, mage.cards.r.Rattlechains.class));
        cards.add(new SetCardInfo("Ravenous Bloodseeker", 175, Rarity.UNCOMMON, mage.cards.r.RavenousBloodseeker.class));
        cards.add(new SetCardInfo("Relentless Dead", "131s", Rarity.MYTHIC, mage.cards.r.RelentlessDead.class));
        cards.add(new SetCardInfo("Sage of Ancient Lore", "225s", Rarity.RARE, mage.cards.s.SageOfAncientLore.class));
        cards.add(new SetCardInfo("Scourge Wolf", "179s", Rarity.RARE, mage.cards.s.ScourgeWolf.class));
        cards.add(new SetCardInfo("Seasons Past", "226s", Rarity.MYTHIC, mage.cards.s.SeasonsPast.class));
        cards.add(new SetCardInfo("Second Harvest", "227s", Rarity.RARE, mage.cards.s.SecondHarvest.class));
        cards.add(new SetCardInfo("Sigarda, Heron's Grace", "250s", Rarity.MYTHIC, mage.cards.s.SigardaHeronsGrace.class));
        cards.add(new SetCardInfo("Silverfur Partisan", "228s", Rarity.RARE, mage.cards.s.SilverfurPartisan.class));
        cards.add(new SetCardInfo("Sin Prodder", "181s", Rarity.RARE, mage.cards.s.SinProdder.class));
        cards.add(new SetCardInfo("Slayer's Plate", "264s", Rarity.RARE, mage.cards.s.SlayersPlate.class));
        cards.add(new SetCardInfo("Sorin, Grim Nemesis", "251s", Rarity.MYTHIC, mage.cards.s.SorinGrimNemesis.class));
        cards.add(new SetCardInfo("Soul Swallower", "230s", Rarity.RARE, mage.cards.s.SoulSwallower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Swallower", 230, Rarity.RARE, mage.cards.s.SoulSwallower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Startled Awake", "88s", Rarity.MYTHIC, mage.cards.s.StartledAwake.class));
        cards.add(new SetCardInfo("Tamiyo's Journal", "265s", Rarity.RARE, mage.cards.t.TamiyosJournal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tamiyo's Journal", "265s+", Rarity.RARE, mage.cards.t.TamiyosJournal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thalia's Lieutenant", "43s", Rarity.RARE, mage.cards.t.ThaliasLieutenant.class));
        cards.add(new SetCardInfo("The Gitrog Monster", "245s", Rarity.MYTHIC, mage.cards.t.TheGitrogMonster.class));
        cards.add(new SetCardInfo("Thing in the Ice", "92s", Rarity.RARE, mage.cards.t.ThingInTheIce.class));
        cards.add(new SetCardInfo("Tireless Tracker", "233s", Rarity.RARE, mage.cards.t.TirelessTracker.class));
        cards.add(new SetCardInfo("To the Slaughter", "139s", Rarity.RARE, mage.cards.t.ToTheSlaughter.class));
        cards.add(new SetCardInfo("Traverse the Ulvenwald", "234s", Rarity.RARE, mage.cards.t.TraverseTheUlvenwald.class));
        cards.add(new SetCardInfo("Triskaidekaphobia", "141s", Rarity.RARE, mage.cards.t.Triskaidekaphobia.class));
        cards.add(new SetCardInfo("Ulvenwald Hydra", "235s", Rarity.MYTHIC, mage.cards.u.UlvenwaldHydra.class));
        cards.add(new SetCardInfo("Vildin-Pack Alpha", "159s", Rarity.RARE, mage.cards.v.VildinPackAlpha.class));
        cards.add(new SetCardInfo("Welcome to the Fold", "96s", Rarity.RARE, mage.cards.w.WelcomeToTheFold.class));
        cards.add(new SetCardInfo("Werewolf of Ancient Hunger", "225s", Rarity.RARE, mage.cards.w.WerewolfOfAncientHunger.class));
        cards.add(new SetCardInfo("Westvale Abbey", "281s", Rarity.RARE, mage.cards.w.WestvaleAbbey.class));
        cards.add(new SetCardInfo("Westvale Cult Leader", "21s", Rarity.RARE, mage.cards.w.WestvaleCultLeader.class));
        cards.add(new SetCardInfo("Wolf of Devil's Breach", "192s", Rarity.MYTHIC, mage.cards.w.WolfOfDevilsBreach.class));
    }
}
