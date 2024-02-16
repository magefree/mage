package org.mage.test.cards.single.lgn;

import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.w.WardSliver Ward Sliver}
 * {4}{W}
 *
 * As Ward Sliver enters the battlefield, choose a color.
 * All Slivers have protection from the chosen color.
 *
 * @author Alex-Vasile
 */
public class WardSliverTest extends CardTestPlayerBase {
    private static final String wardSliver = "Ward Sliver";
    // {W}
    // Exile target creature
    String pathToExile = "Path to Exile";

    /**
     * Test that it gives protection to itself.
     */
    @Test
    public void givesItselfProtection() {
        addCard(Zone.HAND, playerA, wardSliver);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        addCard(Zone.HAND, playerB, pathToExile);
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, wardSliver);
        setChoice(playerA, "White");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        FilterPermanent filter = new FilterPermanent(ObjectColor.WHITE.getDescription());
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        assertAbility(playerA, wardSliver, new ProtectionAbility(filter), true);

        checkPlayableAbility("Can't exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast " + pathToExile, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    /**
     * Test that it gives protection to another sliver.
     */
    @Test
    public void givesOtherSliversProtection() {
        // {1}
        String metalicSliver = "Metallic Sliver";

        addCard(Zone.HAND, playerA, wardSliver);
        addCard(Zone.BATTLEFIELD, playerA, metalicSliver);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        addCard(Zone.HAND, playerB, pathToExile);
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, wardSliver);
        setChoice(playerA, "White");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        FilterPermanent filter = new FilterPermanent(ObjectColor.WHITE.getDescription());
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        assertAbility(playerA, metalicSliver, new ProtectionAbility(filter), true);

        checkPlayableAbility("Can't exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast " + pathToExile, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9418
     *      Player casts Ward Sliver and chooses white.
     *      Same player has a Morophon on the board.
     *      Another player was able to target Morophon with Path to Exile, even though it should have gotten protection from white.
     */
    @Test
    public void givesProtectionToChangeling() {
        // {7}
        // As Morophon, the Boundless enters the battlefield, choose a creature type.
        String morophon = "Morophon, the Boundless";

        addCard(Zone.HAND, playerA, wardSliver);
        addCard(Zone.HAND, playerA, morophon);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 12);

        addCard(Zone.HAND, playerB, pathToExile);
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, morophon);
        setChoice(playerA, "Dragon");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, wardSliver);
        setChoice(playerA, "White");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        FilterPermanent filter = new FilterPermanent(ObjectColor.WHITE.getDescription());
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        assertAbility(playerA, morophon, new ProtectionAbility(filter), true);

        checkPlayableAbility("Can't exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast " + pathToExile, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }
}
