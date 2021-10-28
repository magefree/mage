package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author LePwnerer
 */
public final class InnistradCrimsonVow extends ExpansionSet {

    private static final InnistradCrimsonVow instance = new InnistradCrimsonVow();

    public static InnistradCrimsonVow getInstance() {
        return instance;
    }

    private InnistradCrimsonVow() {
        super("Innistrad: Crimson Vow", "VOW", ExpansionSet.buildDate(2021, 11, 19), SetType.EXPANSION);
        this.blockName = "Innistrad: Midnight Hunt";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;
    }
}
