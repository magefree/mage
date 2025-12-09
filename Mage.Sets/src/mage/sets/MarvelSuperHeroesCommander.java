package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelSuperHeroesCommander extends ExpansionSet {

    private static final MarvelSuperHeroesCommander instance = new MarvelSuperHeroesCommander();

    public static MarvelSuperHeroesCommander getInstance() {
        return instance;
    }

    private MarvelSuperHeroesCommander() {
        super("Marvel Super Heroes Commander", "MSC", ExpansionSet.buildDate(2026, 6, 26), SetType.SUPPLEMENTAL);
        this.blockName = "Marvel Super Heroes"; // for sorting in GUI
        this.hasBasicLands = false; // temporary
    }
}
