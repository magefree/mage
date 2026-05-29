package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class AlchemySecretsOfStrixhaven extends ExpansionSet {

    private static final AlchemySecretsOfStrixhaven instance = new AlchemySecretsOfStrixhaven();

    public static AlchemySecretsOfStrixhaven getInstance() {
        return instance;
    }

    private AlchemySecretsOfStrixhaven() {
        super("Alchemy: Secrets of Strixhaven", "YSOS", ExpansionSet.buildDate(2026, 5, 19), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Corpseweaver Prodigy", 5, Rarity.MYTHIC, mage.cards.c.CorpseweaverProdigy.class));
        cards.add(new SetCardInfo("Glorifying Verse", 19, Rarity.RARE, mage.cards.g.GlorifyingVerse.class));
        cards.add(new SetCardInfo("Grave Studies", 20, Rarity.UNCOMMON, mage.cards.g.GraveStudies.class));
        cards.add(new SetCardInfo("Interdisciplinary Studies", 4, Rarity.UNCOMMON, mage.cards.i.InterdisciplinaryStudies.class));
    }
}
