package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class IcewroughtSentryTest extends CardTestPlayerBase {

    /**
     * Icewrought Sentry
     * {2}{U}
     * Creature — Elemental Soldier
     * <p>
     * Vigilance
     * Whenever Icewrought Sentry attacks, you may pay {1}{U}. When you do, tap target creature an opponent controls.
     * Whenever you tap an untapped creature an opponent controls, Icewrought Sentry gets +2/+1 until end of turn.
     * <p>
     * 2/3
     */
    private static final String sentry = "Icewrought Sentry";

    /**
     * Ice
     * {1}{U}
     * Instant
     * <p>
     * Tap target permanent.
     * <p>
     * Draw a card.
     */
    private static final String fireIce = "Fire // Ice";

    // 2/1, will be tapped.
    private static final String myr = "Alpha Myr";

    @Test
    public void triggerOnTapping() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, fireIce);
        addCard(Zone.BATTLEFIELD, playerA, sentry, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, myr, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice", myr);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, sentry, 2 + 2, 3 + 1);
    }


    @Test
    public void doesNotTriggerOnAlreadyTapped() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, fireIce, 2);
        addCard(Zone.BATTLEFIELD, playerA, sentry, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        addCard(Zone.BATTLEFIELD, playerB, myr, 1);

        // double ice, only 1 trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice", myr);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice", myr);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, sentry, 2 + 2, 3 + 1);
    }

    @Test
    public void doesNotTriggerOnYourCreature() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, fireIce, 1);
        addCard(Zone.BATTLEFIELD, playerA, sentry, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // double ice, only 1 trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice", sentry);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, sentry, 2, 3);
    }

    @Test
    public void doesTriggerOncePerTappedOpponentCreature() {
        setStrictChooseMode(true);

        // Tap all creatures target player controls. Those creatures don’t untap during that player’s next untap step.
        addCard(Zone.HAND, playerA, "Sleep", 1);
        addCard(Zone.BATTLEFIELD, playerA, sentry, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        addCard(Zone.BATTLEFIELD, playerB, myr, 3);

        // double ice, only 1 trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sleep", playerB);
        setChoice(playerA, "Whenever you tap an untapped creature an opponent controls, {this} gets +2/+1 until end of turn.", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, sentry, 2 + 2 * 3, 3 + 1 * 3);
    }

    @Test
    public void attackingDoesNotTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, myr, 1);
        addCard(Zone.BATTLEFIELD, playerB, sentry, 1);

        attack(1, playerA, myr);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerB, sentry, 2, 3);
        assertLife(playerB, 20 - 2);
        assertTapped(myr, true);
    }
}
