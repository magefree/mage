package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AshPartyCrasherTest extends CardTestPlayerBase {

    /**
     * Ash, Party Crasher
     * {R}{W}
     * Legendary Creature — Human Peasant
     * <p>
     * Haste
     * Celebration — Whenever Ash, Party Crasher attacks, if two or more nonland permanents entered the battlefield under your co
     */
    private static final String ash = "Ash, Party Crasher";

    // {1}{G} 2/2
    private static final String bears = "Grizzly Bears";

    /**
     * Instant {1}{W}
     * Create two 1/1 white Soldier creature tokens.
     */
    private static final String raiseTheAlarm = "Raise the Alarm";

    @Test
    public void noCelebration() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ash);
        attack(1, playerA, ash);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void celebrationItselfAndBear() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, ash);
        addCard(Zone.HAND, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ash, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);

        attack(1, playerA, ash);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void noCelebrationOnLand() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, ash);
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ash, true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");

        attack(1, playerA, ash);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void celebrationBearThenItself() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, ash);
        addCard(Zone.HAND, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ash);

        attack(1, playerA, ash);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void celebrationDoubleBear() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ash);
        addCard(Zone.HAND, playerA, bears, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);

        attack(1, playerA, ash);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void celebrationRaiseTheAlarm() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ash);
        addCard(Zone.HAND, playerA, raiseTheAlarm);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, raiseTheAlarm);

        attack(1, playerA, ash);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void noCelebrationOpponentRaiseTheAlarm() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ash);
        addCard(Zone.HAND, playerB, raiseTheAlarm);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, raiseTheAlarm);

        attack(1, playerA, ash);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
    }
}