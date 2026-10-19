package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class AlchemyHorizonsBaldursGate extends ExpansionSet {

    private static final AlchemyHorizonsBaldursGate instance = new AlchemyHorizonsBaldursGate();

    public static AlchemyHorizonsBaldursGate getInstance() {
        return instance;
    }

    private AlchemyHorizonsBaldursGate() {
        super("Alchemy Horizons: Baldur's Gate", "HBG", ExpansionSet.buildDate(2022, 7, 7), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Calim, Djinn Emperor (33)

        // Goblin Trapfinder (56)

        cards.add(new SetCardInfo("Lae'zel, Blessed Warrior", "2w", Rarity.RARE, mage.cards.l.LaezelBlessedWarrior.class));
        cards.add(new SetCardInfo("Lae'zel, Callous Warrior", "2b", Rarity.RARE, mage.cards.l.LaezelCallousWarrior.class));
        cards.add(new SetCardInfo("Lae'zel, Githyanki Warrior", 2, Rarity.RARE, mage.cards.l.LaezelGithyankiWarrior.class));
        cards.add(new SetCardInfo("Lae'zel, Illithid Thrall", "2u", Rarity.RARE, mage.cards.l.LaezelIllithidThrall.class));
        cards.add(new SetCardInfo("Lae'zel, Primal Warrior", "2g", Rarity.RARE, mage.cards.l.LaezelPrimalWarrior.class));
        cards.add(new SetCardInfo("Lae'zel, Wrathful Warrior", "2r", Rarity.RARE, mage.cards.l.LaezelWrathfulWarrior.class));
    }
}
