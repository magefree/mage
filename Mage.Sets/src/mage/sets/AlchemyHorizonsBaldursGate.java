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

        cards.add(new SetCardInfo("Goblin Trapfinder", 56, Rarity.UNCOMMON, mage.cards.g.GoblinTrapfinder.class));

        // Lae'zel, Githyanki Warrior (2)
    }
}
