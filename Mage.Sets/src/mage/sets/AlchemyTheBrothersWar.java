package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

public final class AlchemyTheBrothersWar extends ExpansionSet {

    private static final AlchemyTheBrothersWar instance = new AlchemyTheBrothersWar();

    public static AlchemyTheBrothersWar getInstance() {
        return instance;
    }

    private AlchemyTheBrothersWar() {
        super("Alchemy: The Brothers' War", "YBRO", ExpansionSet.buildDate(2022, 12, 13), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Crucias, Titan of the Waves (18)

        // Jarsyl, Dark Age Scion (20)
    }
}
