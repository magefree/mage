package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

public final class AlchemyTarkir extends ExpansionSet {

    private static final AlchemyTarkir instance = new AlchemyTarkir();

    public static AlchemyTarkir getInstance() {
        return instance;
    }

    private AlchemyTarkir() {
        super("Alchemy: Tarkir", "YTDM", ExpansionSet.buildDate(2025, 4, 29), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Lam, Storm Crane Elder (1)
    }
}
