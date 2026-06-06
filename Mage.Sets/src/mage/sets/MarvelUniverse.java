package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/mar
 *
 * @author ReSech
 */
public class MarvelUniverse extends ExpansionSet {

    private static final MarvelUniverse instance = new MarvelUniverse();

    public static MarvelUniverse getInstance() {
        return instance;
    }

    private MarvelUniverse() {
        super("Marvel Universe", "MAR", ExpansionSet.buildDate(2025, 9, 26), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Alibou, Ancient Witness", 39, Rarity.MYTHIC, mage.cards.a.AlibouAncientWitness.class));
        cards.add(new SetCardInfo("Anthem of Champions", 85, Rarity.MYTHIC, mage.cards.a.AnthemOfChampions.class));
        cards.add(new SetCardInfo("Arachnogenesis", 31, Rarity.MYTHIC, mage.cards.a.Arachnogenesis.class));
        cards.add(new SetCardInfo("Arasta of the Endless Web", 32, Rarity.MYTHIC, mage.cards.a.ArastaOfTheEndlessWeb.class));
        cards.add(new SetCardInfo("Beast Within", 33, Rarity.MYTHIC, mage.cards.b.BeastWithin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Beast Within", 75, Rarity.MYTHIC, mage.cards.b.BeastWithin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bedlam", 74, Rarity.MYTHIC, mage.cards.b.Bedlam.class));
        cards.add(new SetCardInfo("Black Panther, Wakandan King", 87, Rarity.MYTHIC, mage.cards.b.BlackPantherWakandanKing.class));
        cards.add(new SetCardInfo("Captain America, First Avenger", 88, Rarity.MYTHIC, mage.cards.c.CaptainAmericaFirstAvenger.class));
        cards.add(new SetCardInfo("Chaos Warp", 69, Rarity.MYTHIC, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Clever Impersonator", 8, Rarity.MYTHIC, mage.cards.c.CleverImpersonator.class));
        cards.add(new SetCardInfo("Comeuppance", 1, Rarity.MYTHIC, mage.cards.c.Comeuppance.class));
        cards.add(new SetCardInfo("Concerted Effort", 42, Rarity.MYTHIC, mage.cards.c.ConcertedEffort.class));
        cards.add(new SetCardInfo("Counterspell", 9, Rarity.MYTHIC, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", 52, Rarity.MYTHIC, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dauthi Voidwalker", 63, Rarity.MYTHIC, mage.cards.d.DauthiVoidwalker.class));
        cards.add(new SetCardInfo("Deadly Dispute", 64, Rarity.MYTHIC, mage.cards.d.DeadlyDispute.class));
        cards.add(new SetCardInfo("Defense of the Heart", 76, Rarity.MYTHIC, mage.cards.d.DefenseOfTheHeart.class));
        cards.add(new SetCardInfo("Dig Through Time", 54, Rarity.MYTHIC, mage.cards.d.DigThroughTime.class));
        cards.add(new SetCardInfo("Ephemerate", 44, Rarity.MYTHIC, mage.cards.e.Ephemerate.class));
        cards.add(new SetCardInfo("Escape to the Wilds", 89, Rarity.MYTHIC, mage.cards.e.EscapeToTheWilds.class));
        cards.add(new SetCardInfo("Extinction Event", 65, Rarity.MYTHIC, mage.cards.e.ExtinctionEvent.class));
        cards.add(new SetCardInfo("Feed the Swarm", 16, Rarity.MYTHIC, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Fiery Emancipation", 70, Rarity.MYTHIC, mage.cards.f.FieryEmancipation.class));
        cards.add(new SetCardInfo("Fight to the Death", 90, Rarity.MYTHIC, mage.cards.f.FightToTheDeath.class));
        cards.add(new SetCardInfo("Final Act", 66, Rarity.MYTHIC, mage.cards.f.FinalAct.class));
        cards.add(new SetCardInfo("Final Showdown", 45, Rarity.MYTHIC, mage.cards.f.FinalShowdown.class));
        cards.add(new SetCardInfo("Force of Vigor", 77, Rarity.MYTHIC, mage.cards.f.ForceOfVigor.class));
        cards.add(new SetCardInfo("Goblin Bombardment", 23, Rarity.MYTHIC, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Harbinger of the Seas", 55, Rarity.MYTHIC, mage.cards.h.HarbingerOfTheSeas.class));
        cards.add(new SetCardInfo("Heroic Intervention", 34, Rarity.MYTHIC, mage.cards.h.HeroicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heroic Intervention", 78, Rarity.MYTHIC, mage.cards.h.HeroicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heroic Intervention", 79, Rarity.MYTHIC, mage.cards.h.HeroicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heroic Intervention", 80, Rarity.MYTHIC, mage.cards.h.HeroicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hex", 17, Rarity.MYTHIC, mage.cards.h.Hex.class));
        cards.add(new SetCardInfo("Horn of Greed", 98, Rarity.MYTHIC, mage.cards.h.HornOfGreed.class));
        cards.add(new SetCardInfo("Hunter's Insight", 35, Rarity.MYTHIC, mage.cards.h.HuntersInsight.class));
        cards.add(new SetCardInfo("Infernal Grasp", 18, Rarity.MYTHIC, mage.cards.i.InfernalGrasp.class));
        cards.add(new SetCardInfo("Iron Man, Titan of Innovation", 91, Rarity.MYTHIC, mage.cards.i.IronManTitanOfInnovation.class));
        cards.add(new SetCardInfo("Leyline Binding", 2, Rarity.MYTHIC, mage.cards.l.LeylineBinding.class));
        cards.add(new SetCardInfo("Light of Promise", 46, Rarity.MYTHIC, mage.cards.l.LightOfPromise.class));
        cards.add(new SetCardInfo("Lord of Atlantis", 56, Rarity.MYTHIC, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Lorthos, the Tidemaker", 10, Rarity.MYTHIC, mage.cards.l.LorthosTheTidemaker.class));
        cards.add(new SetCardInfo("Massacre Girl", 67, Rarity.MYTHIC, mage.cards.m.MassacreGirl.class));
        cards.add(new SetCardInfo("Mechanized Production", 57, Rarity.MYTHIC, mage.cards.m.MechanizedProduction.class));
        cards.add(new SetCardInfo("Mindbreak Trap", 11, Rarity.MYTHIC, mage.cards.m.MindbreakTrap.class));
        cards.add(new SetCardInfo("Monstrous Rage", 72, Rarity.MYTHIC, mage.cards.m.MonstrousRage.class));
        cards.add(new SetCardInfo("Mystic Confluence", 12, Rarity.MYTHIC, mage.cards.m.MysticConfluence.class));
        cards.add(new SetCardInfo("Najeela, the Blade-Blossom", 24, Rarity.MYTHIC, mage.cards.n.NajeelaTheBladeBlossom.class));
        cards.add(new SetCardInfo("Narset's Reversal", 58, Rarity.MYTHIC, mage.cards.n.NarsetsReversal.class));
        cards.add(new SetCardInfo("Nine Lives", 3, Rarity.MYTHIC, mage.cards.n.NineLives.class));
        cards.add(new SetCardInfo("No Mercy", 68, Rarity.MYTHIC, mage.cards.n.NoMercy.class));
        cards.add(new SetCardInfo("Opposition Agent", 19, Rarity.MYTHIC, mage.cards.o.OppositionAgent.class));
        cards.add(new SetCardInfo("Parallel Lives", 36, Rarity.MYTHIC, mage.cards.p.ParallelLives.class));
        cards.add(new SetCardInfo("Path to Exile", 4, Rarity.MYTHIC, mage.cards.p.PathToExile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Path to Exile", 47, Rarity.MYTHIC, mage.cards.p.PathToExile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ponder", 13, Rarity.MYTHIC, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Primal Vigor", 81, Rarity.MYTHIC, mage.cards.p.PrimalVigor.class));
        cards.add(new SetCardInfo("Privileged Position", 92, Rarity.MYTHIC, mage.cards.p.PrivilegedPosition.class));
        cards.add(new SetCardInfo("Ravenous Tyrannosaurus", 93, Rarity.MYTHIC, mage.cards.r.RavenousTyrannosaurus.class));
        cards.add(new SetCardInfo("Reanimate", 20, Rarity.MYTHIC, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Reconnaissance Mission", 59, Rarity.MYTHIC, mage.cards.r.ReconnaissanceMission.class));
        cards.add(new SetCardInfo("Relentless Assault", 25, Rarity.MYTHIC, mage.cards.r.RelentlessAssault.class));
        cards.add(new SetCardInfo("Reprieve", 5, Rarity.MYTHIC, mage.cards.r.Reprieve.class));
        cards.add(new SetCardInfo("Rest in Peace", 6, Rarity.MYTHIC, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Return to the Ranks", 48, Rarity.MYTHIC, mage.cards.r.ReturnToTheRanks.class));
        cards.add(new SetCardInfo("Righteous Fury", 49, Rarity.MYTHIC, mage.cards.r.RighteousFury.class));
        cards.add(new SetCardInfo("Rite of Replication", 14, Rarity.MYTHIC, mage.cards.r.RiteOfReplication.class));
        cards.add(new SetCardInfo("Savage Beating", 26, Rarity.MYTHIC, mage.cards.s.SavageBeating.class));
        cards.add(new SetCardInfo("Saw in Half", 21, Rarity.MYTHIC, mage.cards.s.SawInHalf.class));
        cards.add(new SetCardInfo("Seize the Day", 73, Rarity.MYTHIC, mage.cards.s.SeizeTheDay.class));
        cards.add(new SetCardInfo("Shock", 27, Rarity.MYTHIC, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Show and Tell", 60, Rarity.MYTHIC, mage.cards.s.ShowAndTell.class));
        cards.add(new SetCardInfo("Silkguard", 37, Rarity.MYTHIC, mage.cards.s.Silkguard.class));
        cards.add(new SetCardInfo("Simulacrum Synthesizer", 61, Rarity.MYTHIC, mage.cards.s.SimulacrumSynthesizer.class));
        cards.add(new SetCardInfo("Skithiryx, the Blight Dragon", 22, Rarity.MYTHIC, mage.cards.s.SkithiryxTheBlightDragon.class));
        cards.add(new SetCardInfo("Steelshaper's Gift", 50, Rarity.MYTHIC, mage.cards.s.SteelshapersGift.class));
        cards.add(new SetCardInfo("Steely Resolve", 83, Rarity.MYTHIC, mage.cards.s.SteelyResolve.class));
        cards.add(new SetCardInfo("Storm, Force of Nature", 94, Rarity.MYTHIC, mage.cards.s.StormForceOfNature.class));
        cards.add(new SetCardInfo("Sundering Growth", 95, Rarity.MYTHIC, mage.cards.s.SunderingGrowth.class));
        cards.add(new SetCardInfo("Sword of Fire and Ice", 100, Rarity.MYTHIC, mage.cards.s.SwordOfFireAndIce.class));
        cards.add(new SetCardInfo("Tangle", 38, Rarity.MYTHIC, mage.cards.t.Tangle.class));
        cards.add(new SetCardInfo("Teferi's Protection", 51, Rarity.MYTHIC, mage.cards.t.TeferisProtection.class));
        cards.add(new SetCardInfo("Terminate", 40, Rarity.MYTHIC, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 28, Rarity.MYTHIC, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Traumatize", 15, Rarity.MYTHIC, mage.cards.t.Traumatize.class));
        cards.add(new SetCardInfo("Unexpected Windfall", 29, Rarity.MYTHIC, mage.cards.u.UnexpectedWindfall.class));
        cards.add(new SetCardInfo("Unnatural Growth", 84, Rarity.MYTHIC, mage.cards.u.UnnaturalGrowth.class));
        cards.add(new SetCardInfo("Warleader's Call", 96, Rarity.MYTHIC, mage.cards.w.WarleadersCall.class));
        cards.add(new SetCardInfo("Wedding Ring", 7, Rarity.MYTHIC, mage.cards.w.WeddingRing.class));
        cards.add(new SetCardInfo("Winds of Change", 30, Rarity.MYTHIC, mage.cards.w.WindsOfChange.class));
        cards.add(new SetCardInfo("Wolverine, Best There Is", 97, Rarity.MYTHIC, mage.cards.w.WolverineBestThereIs.class));
    }
}
