package org.mage.test.cards.single.mm2;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Death Denied {X}{B}{B}
 * Return X target creature cards from your graveyard to your hand.
 * <p>
 * X is announced before targets are chosen (CR 601.2b, 601.2c), so with X = 0 the spell has
 * no targets and is castable with no creature card in the graveyard.
 */
public class DeathDeniedTest extends CardTestPlayerBase {

    @Test
    public void testNoCreatureCardCastsForZero() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Death Denied");
        addCard(Zone.GRAVEYARD, playerA, "Swamp", 3); // no creature card
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // not "your graveyard"

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Death Denied");
        setChoice(playerA, "X=0");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Death Denied", 1);
        assertGraveyardCount(playerA, "Swamp", 3);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);
    }

    @Test
    public void testOneCreatureCardStillTargetsForOne() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Death Denied");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Death Denied");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Death Denied", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 0);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }
}
