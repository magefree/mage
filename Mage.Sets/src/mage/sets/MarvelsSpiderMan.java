package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelsSpiderMan extends ExpansionSet {

    private static final MarvelsSpiderMan instance = new MarvelsSpiderMan();

    public static MarvelsSpiderMan getInstance() {
        return instance;
    }

    private MarvelsSpiderMan() {
        super("Marvel's Spider-Man", "SPM", ExpansionSet.buildDate(2025, 9, 26), SetType.EXPANSION);
        this.blockName = "Marvel's Spider-Man"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Origin of Spider-Man", 9, Rarity.RARE, mage.cards.o.OriginOfSpiderMan.class));
    }
}
