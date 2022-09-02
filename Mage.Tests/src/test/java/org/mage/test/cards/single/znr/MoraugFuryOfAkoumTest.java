package org.mage.test.cards.single.znr;

import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class MoraugFuryOfAkoumTest extends CardTestPlayerBase {

    private static final String moraug = "Moraug, Fury of Akoum";
    private static final String mountain = "Mountain";
    private static final String lion = "Silvercoat Lion";
    private static final String bear = "Grizzly Bears";
    private static final String corpse = "Walking Corpse";
    private static final String azusa = "Azusa, Lost but Seeking";

    private void makeCombatCounter() {
        addCustomCardWithAbility(
                "Combat Counter", playerA,
                new BeginningOfCombatTriggeredAbility(
                        new GainLifeEffect(1), TargetController.YOU, false
                )
        );
    }

    private void makeAttackers() {
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, corpse);
    }

    private void attackWithAttackers() {
        attack(1, playerA, lion);
        attack(1, playerA, bear);
        attack(1, playerA, corpse);
    }

    /**
     * If the landfall ability resolves during your precombat main phase,
     * the additional combat phase will happen before your regular combat phase.
     * You’ll untap creatures you control at the beginning of the additional combat
     * but not at the beginning of your regular combat.
     *      (2020-09-25)
     */
    @Test
    public void testPrecombatLandfall() {
        makeCombatCounter();
        makeAttackers();
        addCard(Zone.BATTLEFIELD, playerA, moraug);
        addCard(Zone.HAND, playerA, mountain);

        setLife(playerB, 100); // 9 damage

        // playing land pre-combat adds extra combat with untap before normal combat
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, mountain);

        attackWithAttackers();

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        // untap trigger already happened, creatures are still tapped and can't attack again
        assertTapped(lion, true);
        assertTapped(bear, true);
        assertTapped(corpse, true);

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 100 - 9 - 0);
    }

    @Test
    public void testPostcombatLandfall() {
        makeCombatCounter();
        makeAttackers();
        addCard(Zone.BATTLEFIELD, playerA, moraug);
        addCard(Zone.HAND, playerA, mountain);

        setLife(playerB, 100);

        attackWithAttackers(); // 9 damage

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, mountain);

        attackWithAttackers(); // 12 damage

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 100 - 9 - 12);
    }

    @Test
    public void testDoubleLandfall() {
        makeCombatCounter();
        makeAttackers();
        addCard(Zone.BATTLEFIELD, playerA, moraug);
        addCard(Zone.BATTLEFIELD, playerA, azusa);
        addCard(Zone.HAND, playerA, mountain, 2);

        setLife(playerB, 100);

        attackWithAttackers(); // 9 damage

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, mountain);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, mountain);

        attackWithAttackers(); // 12 damage
        attackWithAttackers(); // 15 damage

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20 + 3);
        assertLife(playerB, 100 - 9 - 12 - 15);
    }

    @Test
    public void testDoubleMoraug() {
        makeCombatCounter();
        makeAttackers();
        addCard(Zone.BATTLEFIELD, playerA, "Mirror Gallery");
        addCard(Zone.BATTLEFIELD, playerA, moraug, 2);
        addCard(Zone.HAND, playerA, mountain);

        setLife(playerB, 100);

        attackWithAttackers(); // 12 damage

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, mountain);

        attackWithAttackers(); // 18 damage
        attackWithAttackers(); // 24 damage

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20 + 3);
        assertLife(playerB, 100 - 12 - 18 - 24);
    }
}
