package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * Those cards are not yet confirmed to be true.
 * They seem legit enough, that implementing seemed worthwhile.
 * Set to Custom for only opt-in use them (like in cube list).
 * <p>
 * TODO: Move cards to MH3 and remove this Set once confirmed real cards, or remove if confirmed fake.
 *
 * @author Susucr
 */
public final class ModernHorizons3Leaks extends ExpansionSet {

    private static final ModernHorizons3Leaks instance = new ModernHorizons3Leaks();

    public static ModernHorizons3Leaks getInstance() {
        return instance;
    }

    private ModernHorizons3Leaks() {
        super("Modern Horizons 3 Leaks", "MH3L", ExpansionSet.buildDate(2024, 6, 7), SetType.CUSTOM_SET);
        this.blockName = "Modern Horizons 3";
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Ral, Monsoon Mage", 247, Rarity.MYTHIC, mage.cards.r.RalMonsoonMage.class));
        cards.add(new SetCardInfo("Ral, Leyline Prodigy", 247, Rarity.MYTHIC, mage.cards.r.RalLeylineProdigy.class));
        cards.add(new SetCardInfo("Sorin of House Markov", 245, Rarity.MYTHIC, mage.cards.s.SorinOfHouseMarkov.class));
        cards.add(new SetCardInfo("Sorin, Ravenous Neonate", 245, Rarity.MYTHIC, mage.cards.s.SorinRavenousNeonate.class));
        cards.add(new SetCardInfo("Tamiyo, Inquisitive Student", 242, Rarity.MYTHIC, mage.cards.t.TamiyoInquisitiveStudent.class));
        cards.add(new SetCardInfo("Tamiyo, Seasoned Scholar", 242, Rarity.MYTHIC, mage.cards.t.TamiyoSeasonedScholar.class));
    }
}
