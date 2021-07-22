package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pres
 * Stance on non-english cards:
 * https://github.com/magefree/mage/pull/6190#issuecomment-582353697
 * https://github.com/magefree/mage/pull/6190#issuecomment-582354790
 */
public class ResalePromos extends ExpansionSet {

    private static final ResalePromos instance = new ResalePromos();

    public static ResalePromos getInstance() {
        return instance;
    }

    private ResalePromos() {
        super("Resale Promos", "PRES", ExpansionSet.buildDate(2007, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Angel of Glory's Rise", "A9", Rarity.RARE, mage.cards.a.AngelOfGlorysRise.class));
        cards.add(new SetCardInfo("Angelic Skirmisher", "A11", Rarity.RARE, mage.cards.a.AngelicSkirmisher.class));
        cards.add(new SetCardInfo("Atarka, World Render", 149, Rarity.RARE, mage.cards.a.AtarkaWorldRender.class));
        cards.add(new SetCardInfo("Beast Whisperer", 123, Rarity.RARE, mage.cards.b.BeastWhisperer.class));
        cards.add(new SetCardInfo("Brion Stoutarm", "A2", Rarity.RARE, mage.cards.b.BrionStoutarm.class));
        cards.add(new SetCardInfo("Bristling Hydra", 147, Rarity.RARE, mage.cards.b.BristlingHydra.class));
        cards.add(new SetCardInfo("Broodmate Dragon", "A3", Rarity.RARE, mage.cards.b.BroodmateDragon.class));
        cards.add(new SetCardInfo("Curator of Mysteries", 49, Rarity.RARE, mage.cards.c.CuratorOfMysteries.class));
        cards.add(new SetCardInfo("Etali, Primal Storm", 100, Rarity.RARE, mage.cards.e.EtaliPrimalStorm.class));
        cards.add(new SetCardInfo("Felidar Sovereign", 26, Rarity.RARE, mage.cards.f.FelidarSovereign.class));
        cards.add(new SetCardInfo("Gargos, Vicious Watcher", 172, Rarity.RARE, mage.cards.g.GargosViciousWatcher.class));
        cards.add(new SetCardInfo("Genesis Hydra", 176, Rarity.RARE, mage.cards.g.GenesisHydra.class));
        cards.add(new SetCardInfo("Goblin Chieftain", "141*", Rarity.RARE, mage.cards.g.GoblinChieftain.class));
        cards.add(new SetCardInfo("Hamletback Goliath", "A10", Rarity.RARE, mage.cards.h.HamletbackGoliath.class));
        cards.add(new SetCardInfo("Jaya Ballard, Task Mage", "A1", Rarity.RARE, mage.cards.j.JayaBallardTaskMage.class));
        cards.add(new SetCardInfo("Knight Exemplar", "A7", Rarity.RARE, mage.cards.k.KnightExemplar.class));
        cards.add(new SetCardInfo("Lathliss, Dragon Queen", "149*", Rarity.RARE, mage.cards.l.LathlissDragonQueen.class));
        cards.add(new SetCardInfo("Loam Lion", "13*", Rarity.UNCOMMON, mage.cards.l.LoamLion.class));
        cards.add(new SetCardInfo("Neheb, Dreadhorde Champion", 140, Rarity.RARE, mage.cards.n.NehebDreadhordeChampion.class));
        cards.add(new SetCardInfo("Opportunistic Dragon", 133, Rarity.RARE, mage.cards.o.OpportunisticDragon.class));
        cards.add(new SetCardInfo("Oran-Rief, the Vastwood", "221*", Rarity.RARE, mage.cards.o.OranRiefTheVastwood.class));
        cards.add(new SetCardInfo("Outland Colossus", 193, Rarity.RARE, mage.cards.o.OutlandColossus.class));
        cards.add(new SetCardInfo("Retaliator Griffin", "A4", Rarity.RARE, mage.cards.r.RetaliatorGriffin.class));
        cards.add(new SetCardInfo("Stonecoil Serpent", 235, Rarity.RARE, mage.cards.s.StonecoilSerpent.class));
        cards.add(new SetCardInfo("Sunblast Angel", "A8", Rarity.RARE, mage.cards.s.SunblastAngel.class));
        cards.add(new SetCardInfo("Terastodon", "A6", Rarity.RARE, mage.cards.t.Terastodon.class));
        cards.add(new SetCardInfo("Terra Stomper", "A5", Rarity.RARE, mage.cards.t.TerraStomper.class));
        cards.add(new SetCardInfo("Thalia's Lancers", 47, Rarity.RARE, mage.cards.t.ThaliasLancers.class));
        cards.add(new SetCardInfo("Xathrid Necromancer", "A12", Rarity.RARE, mage.cards.x.XathridNecromancer.class));
    }
}
