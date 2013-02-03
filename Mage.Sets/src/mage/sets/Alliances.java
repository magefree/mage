package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class Alliances extends ExpansionSet {
    private static final Alliances fINSTANCE =  new Alliances();

    public static Alliances getInstance() {
        return fINSTANCE;
    }

    private Alliances() {
        super("Alliances", "ALL", "", "mage.sets.alliances", new GregorianCalendar(1996, 6, 10).getTime(), Constants.SetType.EXPANSION);
        this.blockName = "Ice Age";
        this.parentSet = IceAge.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 8;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}
