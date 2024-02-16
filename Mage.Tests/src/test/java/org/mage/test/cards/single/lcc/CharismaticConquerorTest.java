package org.mage.test.cards.single.lcc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Charismatic Conqueror {1}{W}
 * Creature - Vampire Soldier 2/2
 * Vigilance
 * Whenever an artifact or creature enters the battlefield untapped and under an opponent’s control, they may tap that permanent. If they don’t, you create a 1/1 white Vampire creature token with lifelink.
 *
 * @author DominionSpy
 */
public class CharismaticConquerorTest extends CardTestPlayerBase {

    private static final String charismaticConqueror = "Charismatic Conqueror";
    private static final String chaliceOfLife = "Chalice of Life";
    private static final String vampireToken = "Vampire Token";

    @Test
    public void test_ChooseToTap() {
        addCard(Zone.BATTLEFIELD, playerB, charismaticConqueror);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, chaliceOfLife);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, chaliceOfLife);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, vampireToken, 0);
    }

    @Test
    public void test_ChooseNotToTap() {
        addCard(Zone.BATTLEFIELD, playerB, charismaticConqueror);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, chaliceOfLife);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, chaliceOfLife);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, vampireToken, 1);
    }

    @Test
    public void test_TapBeforeResolution() {
        addCard(Zone.BATTLEFIELD, playerB, charismaticConqueror);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, chaliceOfLife);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, chaliceOfLife);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        // Activate Chalice of Life's ability while Charismatic Conqueror's trigger is on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,  "{T}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, vampireToken, 1);
    }

    @Test
    public void test_DestroyBeforeResolution() {
        addCard(Zone.BATTLEFIELD, playerB, charismaticConqueror);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3 + 2);
        addCard(Zone.HAND, playerA, chaliceOfLife);
        addCard(Zone.HAND, playerA, "Divine Offering");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, chaliceOfLife);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        // Destroy Chalice of Life while Charismatic Conqueror's trigger is on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divine Offering", chaliceOfLife);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, vampireToken, 1);
    }
}
