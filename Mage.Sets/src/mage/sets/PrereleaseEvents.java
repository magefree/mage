package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.GregorianCalendar;

public class PrereleaseEvents extends ExpansionSet {
    private static final PrereleaseEvents fINSTANCE = new PrereleaseEvents();

    public static PrereleaseEvents getInstance() {
        return fINSTANCE;
    }

    private PrereleaseEvents() {
        super("Prerelease Events", "PTC", "mage.sets.prereleaseevents", new GregorianCalendar(1990, 1, 1).getTime(), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
    }
}
