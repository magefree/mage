package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelSuperHeroes extends ExpansionSet {

    private static final MarvelSuperHeroes instance = new MarvelSuperHeroes();

    public static MarvelSuperHeroes getInstance() {
        return instance;
    }

    private MarvelSuperHeroes() {
        super("Marvel Super Heroes", "MSH", ExpansionSet.buildDate(2026, 6, 26), SetType.EXPANSION);
        this.blockName = "Marvel Super Heroes"; // for sorting in GUI
        this.hasBasicLands = false; // temporary
    }
}
