package mage.sets;

import java.util.GregorianCalendar;
import mage.Constants;
import mage.cards.ExpansionSet;


public class NewPhyrexia extends ExpansionSet {
    private static final NewPhyrexia fINSTANCE =  new NewPhyrexia();

    public static NewPhyrexia getInstance() {
        return fINSTANCE;
    }

    private NewPhyrexia() {
        super("New Phyrexia", "NPH", "", "mage.sets.newphyrexia", new GregorianCalendar(2011, 4, 4).getTime(), Constants.SetType.EXPANSION);
        this.blockName = "Scars of Mirrodin";
        this.parentSet = ScarsOfMirrodin.getInstance();
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
    }
}
