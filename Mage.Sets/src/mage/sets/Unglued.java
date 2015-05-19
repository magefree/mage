package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.GregorianCalendar;

/**
 *
 * @author lopho
 */
public class Unglued extends ExpansionSet {
   private static final Unglued fINSTANCE =  new Unglued();

    public static Unglued getInstance() {
        return fINSTANCE;
    }

    private Unglued() {
        super("Unglued", "UGL", "mage.sets.unglued", new GregorianCalendar(1998, 8, 11).getTime(), SetType.JOKESET);
    }
}
