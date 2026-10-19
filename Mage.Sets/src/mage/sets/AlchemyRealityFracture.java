package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class AlchemyRealityFracture extends ExpansionSet {

    private static final AlchemyRealityFracture instance = new AlchemyRealityFracture();

    public static AlchemyRealityFracture getInstance() {
        return instance;
    }

    private AlchemyRealityFracture() {
        super("Alchemy: Reality Fracture", "YFRA", ExpansionSet.buildDate(2026, 10, 13), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Asymmetry Sage (4)

        cards.add(new SetCardInfo("Avoided Stairwurm", 10, Rarity.RARE, mage.cards.a.AvoidedStairwurm.class));

        // Diabolic Formula (21)

        // Selfless Germinator (17)
    }
}
