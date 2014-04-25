package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.GregorianCalendar;

/**
 *
 * @author magenoxx
 */
public class Unhinged extends ExpansionSet {
   private static final Unhinged fINSTANCE =  new Unhinged();

    public static Unhinged getInstance() {
        return fINSTANCE;
    }

    private Unhinged() {
        super("Unhinged", "UNH", "mage.sets.unhinged", new GregorianCalendar(2004, 11, 20).getTime(), SetType.JOKESET);
    }
}
