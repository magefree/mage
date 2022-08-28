package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author PurpleCrowbar
 */
public final class CommanderCollectionBlack extends ExpansionSet {

    private static final CommanderCollectionBlack instance = new CommanderCollectionBlack();

    public static CommanderCollectionBlack getInstance() {
        return instance;
    }

    private CommanderCollectionBlack() {
        super("Commander Collection: Black", "CC2", ExpansionSet.buildDate(2022, 1, 28), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Command Tower", 8, Rarity.RARE, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Ghoulcaller Gisa", 2, Rarity.MYTHIC, mage.cards.g.GhoulcallerGisa.class));
        cards.add(new SetCardInfo("Liliana, Defiant Necromancer", 1, Rarity.MYTHIC, mage.cards.l.LilianaDefiantNecromancer.class));
        cards.add(new SetCardInfo("Liliana, Heretical Healer", 1, Rarity.MYTHIC, mage.cards.l.LilianaHereticalHealer.class));
        cards.add(new SetCardInfo("Ophiomancer", 3, Rarity.RARE, mage.cards.o.Ophiomancer.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 4, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Reanimate", 5, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Sol Ring", 7, Rarity.RARE, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Toxic Deluge", 6, Rarity.RARE, mage.cards.t.ToxicDeluge.class));
    }
}
