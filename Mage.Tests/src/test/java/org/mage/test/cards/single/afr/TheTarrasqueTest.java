package org.mage.test.cards.single.afr;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class TheTarrasqueTest extends CardTestPlayerBase {

    private static final String tarrasque = "The Tarrasque";
    private static final String sakashima = "Sakashima the Impostor";

    @Test
    public void testCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);
        addCard(Zone.HAND, playerA, tarrasque);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tarrasque);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), true);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), true);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), true);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), true);
    }

    @Test
    public void testNotCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Piper");
        addCard(Zone.HAND, playerA, tarrasque);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G},");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), false);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), false);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), false);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), false);
    }

    @Test
    public void testSakashima() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Piper");
        addCard(Zone.HAND, playerA, tarrasque);
        addCard(Zone.HAND, playerA, sakashima);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{G},");
        setChoice(playerA, true);
        setChoice(playerA, tarrasque);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, sakashima);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, sakashima, 10, 10);
        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), false);
        assertAbility(playerA, sakashima, HasteAbility.getInstance(), true);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), false);
        assertAbility(playerA, sakashima, HasteAbility.getInstance(), true);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), false);
        assertAbility(playerA, sakashima, HasteAbility.getInstance(), true);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, tarrasque, HasteAbility.getInstance(), false);
        assertAbility(playerA, sakashima, HasteAbility.getInstance(), true);
    }
}
