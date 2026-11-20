package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

/**
 * @author DarioBarrabinoDev
 */
public class KingSolomonsFrogsTest extends CardTestMultiPlayerBaseWithRangeAll {

    // Flash
    // When King Solomon's Frogs enters, if you cast it, for each opponent, exile up to one target permanent
    // that player controls with mana value 3 or greater. For each permanent exiled this way, its controller draws a card.
    // {3}, {T}, Exile King Solomon's Frogs: You become the monarch.
    private static final String frogs = "King Solomon's Frogs";

    private static final String juggernaut = "Juggernaut"; // mana value 4
    private static final String ogre = "Gray Ogre"; // mana value 3
    private static final String bear = "Grizzly Bears"; // mana value 2

    // Player order: A -> D -> C -> B, so the targets are chosen for D, C and B in that order

    @Test
    public void test_Cast_ExileOnePermanentPerOpponent() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, frogs);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, juggernaut);
        addCard(Zone.BATTLEFIELD, playerC, ogre);
        addCard(Zone.BATTLEFIELD, playerD, bear);

        // flash, cast on D's turn after D's draw
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, frogs);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // bear has mana value 2
        addTarget(playerA, ogre);
        addTarget(playerA, juggernaut);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, frogs, 1);
        assertExileCount(playerB, juggernaut, 1);
        assertExileCount(playerC, ogre, 1);
        assertPermanentCount(playerD, bear, 1);
        assertHandCount(playerA, 1); // draw step only
        assertHandCount(playerB, 1);
        assertHandCount(playerC, 1);
        assertHandCount(playerD, 1); // draw step only
    }

    @Test
    public void test_Cast_TargetIllegalOnResolution_NoDraw() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, frogs);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerB, "Unsummon");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, juggernaut);
        addCard(Zone.BATTLEFIELD, playerC, ogre);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, frogs);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, ogre);
        addTarget(playerA, juggernaut);

        // return Juggernaut in response to the trigger, so it isn't exiled this way
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Unsummon", juggernaut, "When");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, juggernaut, 1);
        assertHandCount(playerB, 1);
        assertExileCount(playerC, ogre, 1);
        assertHandCount(playerC, 1);
    }

    @Test
    public void test_NotCast_NoTrigger() {
        setStrictChooseMode(true);

        // Exile two target artifacts, creatures, and/or lands you control, then return those cards to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Ghostly Flicker");
        addCard(Zone.BATTLEFIELD, playerA, frogs);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, juggernaut);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ghostly Flicker");
        addTarget(playerA, frogs + "^Island");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // no targets were asked for in strict mode, so the trigger didn't happen
        assertPermanentCount(playerA, frogs, 1);
        assertPermanentCount(playerB, juggernaut, 1);
        assertHandCount(playerB, 0);
    }

    @Test
    public void test_BecomeTheMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, frogs);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        checkPlayableAbility("can activate", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}, Exile", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}, Exile");
        checkMonarch("monarch", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, playerA);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, frogs, 1);
        assertHandCount(playerA, 1 + 1); // draw step and monarch draw at the end step
    }
}
