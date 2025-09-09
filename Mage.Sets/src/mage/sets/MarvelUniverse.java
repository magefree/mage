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
        cards.add(new SetCardInfo("Arachnogenesis", 31, Rarity.MYTHIC, mage.cards.a.Arachnogenesis.class));
        cards.add(new SetCardInfo("Arasta of the Endless Web", 32, Rarity.MYTHIC, mage.cards.a.ArastaOfTheEndlessWeb.class));
        cards.add(new SetCardInfo("Beast Within", 33, Rarity.MYTHIC, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Clever Impersonator", 8, Rarity.MYTHIC, mage.cards.c.CleverImpersonator.class));
        cards.add(new SetCardInfo("Comeuppance", 1, Rarity.MYTHIC, mage.cards.c.Comeuppance.class));
        cards.add(new SetCardInfo("Counterspell", 9, Rarity.MYTHIC, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Feed the Swarm", 16, Rarity.MYTHIC, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Goblin Bombardment", 23, Rarity.MYTHIC, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Heroic Intervention", 34, Rarity.MYTHIC, mage.cards.h.HeroicIntervention.class));
        cards.add(new SetCardInfo("Hex", 17, Rarity.MYTHIC, mage.cards.h.Hex.class));
        cards.add(new SetCardInfo("Hunter's Insight", 35, Rarity.MYTHIC, mage.cards.h.HuntersInsight.class));
        cards.add(new SetCardInfo("Infernal Grasp", 18, Rarity.MYTHIC, mage.cards.i.InfernalGrasp.class));
        cards.add(new SetCardInfo("Leyline Binding", 2, Rarity.MYTHIC, mage.cards.l.LeylineBinding.class));
        cards.add(new SetCardInfo("Lorthos, the Tidemaker", 10, Rarity.MYTHIC, mage.cards.l.LorthosTheTidemaker.class));
        cards.add(new SetCardInfo("Mindbreak Trap", 11, Rarity.MYTHIC, mage.cards.m.MindbreakTrap.class));
        cards.add(new SetCardInfo("Mystic Confluence", 12, Rarity.MYTHIC, mage.cards.m.MysticConfluence.class));
        cards.add(new SetCardInfo("Najeela, the Blade-Blossom", 24, Rarity.MYTHIC, mage.cards.n.NajeelaTheBladeBlossom.class));
        cards.add(new SetCardInfo("Nine Lives", 3, Rarity.MYTHIC, mage.cards.n.NineLives.class));
        cards.add(new SetCardInfo("Opposition Agent", 19, Rarity.MYTHIC, mage.cards.o.OppositionAgent.class));
        cards.add(new SetCardInfo("Parallel Lives", 36, Rarity.MYTHIC, mage.cards.p.ParallelLives.class));
        cards.add(new SetCardInfo("Path to Exile", 4, Rarity.MYTHIC, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Ponder", 13, Rarity.MYTHIC, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Reanimate", 20, Rarity.MYTHIC, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Relentless Assault", 25, Rarity.MYTHIC, mage.cards.r.RelentlessAssault.class));
        cards.add(new SetCardInfo("Reprieve", 5, Rarity.MYTHIC, mage.cards.r.Reprieve.class));
        cards.add(new SetCardInfo("Rest in Peace", 6, Rarity.MYTHIC, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Rite of Replication", 14, Rarity.MYTHIC, mage.cards.r.RiteOfReplication.class));
        cards.add(new SetCardInfo("Savage Beating", 26, Rarity.MYTHIC, mage.cards.s.SavageBeating.class));
        cards.add(new SetCardInfo("Saw in Half", 21, Rarity.MYTHIC, mage.cards.s.SawInHalf.class));
        cards.add(new SetCardInfo("Shock", 27, Rarity.MYTHIC, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Silkguard", 37, Rarity.MYTHIC, mage.cards.s.Silkguard.class));
        cards.add(new SetCardInfo("Skithiryx, the Blight Dragon", 22, Rarity.MYTHIC, mage.cards.s.SkithiryxTheBlightDragon.class));
        cards.add(new SetCardInfo("Tangle", 38, Rarity.MYTHIC, mage.cards.t.Tangle.class));
        cards.add(new SetCardInfo("Terminate", 40, Rarity.MYTHIC, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 28, Rarity.MYTHIC, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Traumatize", 15, Rarity.MYTHIC, mage.cards.t.Traumatize.class));
        cards.add(new SetCardInfo("Unexpected Windfall", 29, Rarity.MYTHIC, mage.cards.u.UnexpectedWindfall.class));
        cards.add(new SetCardInfo("Wedding Ring", 7, Rarity.MYTHIC, mage.cards.w.WeddingRing.class));
        cards.add(new SetCardInfo("Winds of Change", 30, Rarity.MYTHIC, mage.cards.w.WindsOfChange.class));
    }
}
