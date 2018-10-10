package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class NinthEditionBox extends ExpansionSet {

    private static final NinthEditionBox instance = new NinthEditionBox();

    public static NinthEditionBox getInstance() {
        return instance;
    }

    private NinthEditionBox() {
        super("Ninth Edition Box", "9EB", ExpansionSet.buildDate(2005, 7, 29), SetType.CORE);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Coral Eel", 3, Rarity.COMMON, mage.cards.c.CoralEel.class));
        cards.add(new SetCardInfo("Eager Cadet", 1, Rarity.COMMON, mage.cards.e.EagerCadet.class));
        cards.add(new SetCardInfo("Enormous Baloth", 9, Rarity.UNCOMMON, mage.cards.e.EnormousBaloth.class));
        cards.add(new SetCardInfo("Giant Octopus", 4, Rarity.COMMON, mage.cards.g.GiantOctopus.class));
        cards.add(new SetCardInfo("Goblin Raider", 8, Rarity.COMMON, mage.cards.g.GoblinRaider.class));
        cards.add(new SetCardInfo("Index", 5, Rarity.COMMON, mage.cards.i.Index.class));
        cards.add(new SetCardInfo("Spined Wurm", 10, Rarity.COMMON, mage.cards.s.SpinedWurm.class));
        cards.add(new SetCardInfo("Vengeance", 2, Rarity.UNCOMMON, mage.cards.v.Vengeance.class));
        cards.add(new SetCardInfo("Vizzerdrix", 7, Rarity.RARE, mage.cards.v.Vizzerdrix.class));
    }
}
