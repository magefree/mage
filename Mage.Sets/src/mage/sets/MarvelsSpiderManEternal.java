package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelsSpiderManEternal extends ExpansionSet {

    private static final MarvelsSpiderManEternal instance = new MarvelsSpiderManEternal();

    public static MarvelsSpiderManEternal getInstance() {
        return instance;
    }

    private MarvelsSpiderManEternal() {
        super("Marvel's Spider-Man Eternal", "SPE", ExpansionSet.buildDate(2025, 9, 26), SetType.EXPANSION);
        this.blockName = "Marvel's Spider-Man"; // for sorting in GUI
        this.hasBasicLands = false; // temporary
    }
}
