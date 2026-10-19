package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class AlchemyEdgeOfEternities extends ExpansionSet {

    private static final AlchemyEdgeOfEternities instance = new AlchemyEdgeOfEternities();

    public static AlchemyEdgeOfEternities getInstance() {
        return instance;
    }

    private AlchemyEdgeOfEternities() {
        super("Alchemy: Edge of Eternities", "YEOE", ExpansionSet.buildDate(2025, 8, 19), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Cosmic Sovereign", 14, Rarity.MYTHIC, mage.cards.c.CosmicSovereign.class));

        // Hydroponics Architect (6)

        // Val, Marooned Surveyor (26)

    }
}
