package org.mage.test.cards.replacement.redirect;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class KorDirgeTest extends CardTestPlayerBase {

    /**
     * The same card resolving a second time must ask for a fresh source, and both resolutions must
     * keep redirecting from the source each one chose.
     */
    @Test
    public void testCastTwiceChoosesNewSource() {
        // All damage that would be dealt this turn to target creature you control by a source of your choice
        // is dealt to another target creature instead.
        addCard(Zone.HAND, playerA, "Kor Dirge");
        addCard(Zone.HAND, playerA, "Regrowth");
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        addCard(Zone.BATTLEFIELD, playerB, "Prodigal Pyromancer");
        addCard(Zone.BATTLEFIELD, playerB, "Rod of Ruin");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kor Dirge");
        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, "Hill Giant");
        setChoice(playerA, "Prodigal Pyromancer");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Regrowth", "Kor Dirge", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kor Dirge");
        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, "Hill Giant");
        setChoice(playerA, "Rod of Ruin");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: {this} deals 1 damage", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerB);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{3}, {T}: {this} deals 1 damage", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerA, "Grizzly Bears", 0);
        assertDamageReceived(playerB, "Hill Giant", 2);
    }

    /**
     * 614.9: "If one of those permanents is no longer on the battlefield when the damage would be
     * redirected, or is no longer a battle, creature, or planeswalker when the damage would be
     * redirected, the effect does nothing." The flickered creature is a new object.
     */
    @Test
    public void testRedirectToFlickeredCreatureDoesNothing() {
        addCard(Zone.HAND, playerA, "Kor Dirge");
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3

        addCard(Zone.BATTLEFIELD, playerB, "Prodigal Pyromancer");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kor Dirge");
        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, "Hill Giant");
        setChoice(playerA, "Prodigal Pyromancer");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Hill Giant", true);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: {this} deals 1 damage", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerA, "Grizzly Bears", 1);
        assertDamageReceived(playerA, "Hill Giant", 0);
    }
}
