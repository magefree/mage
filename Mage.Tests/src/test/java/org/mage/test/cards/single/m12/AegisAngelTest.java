package org.mage.test.cards.single.m12;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class AegisAngelTest extends CardTestPlayerBase {
    private static final String angel = "Aegis Angel";
    private static final String lion = "Silvercoat Lion";
    private static final String murder = "Murder";
    private static final String act = "Act of Treason";

    @Test
    public void testGainsAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, angel);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, angel);
        addTarget(playerA, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertAbility(playerA, lion, IndestructibleAbility.getInstance(), true);
    }

    @Test
    public void testKeepsAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, angel);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, angel);
        addTarget(playerA, lion);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertAbility(playerA, lion, IndestructibleAbility.getInstance(), true);
    }

    @Test
    public void testAngelDiesBeforeEntering() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 9);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, angel);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, angel);
        addTarget(playerA, lion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, angel);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, angel, 1);
        assertGraveyardCount(playerA, murder, 1);
        assertAbility(playerA, lion, IndestructibleAbility.getInstance(), false);
    }

    @Test
    public void testAngelDiesAfterEntering() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 9);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, angel);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, angel);
        addTarget(playerA, lion);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, angel);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, angel, 1);
        assertGraveyardCount(playerA, murder, 1);
        assertAbility(playerA, lion, IndestructibleAbility.getInstance(), false);
    }

    @Test
    public void testAngelLoseControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, angel);
        addCard(Zone.HAND, playerB, act);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, angel);
        addTarget(playerA, lion);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, act, angel);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, act, 1);
        assertPermanentCount(playerB, angel, 1);
        assertAbility(playerA, lion, IndestructibleAbility.getInstance(), false);
    }
}
