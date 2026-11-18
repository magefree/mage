package org.mage.test.cards.single.sld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class PrincessIrulanCorrinoTest extends CardTestCommander4Players {

    @Test
    public void testEachDrawingOpponentIsRemembered() {
        // Whenever you draw your second card each turn, you gain 2 life.
        // Whenever an opponent draws their second card each turn, they lose 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Princess Irulan Corrino");
        addCard(Zone.HAND, playerA, "Prosperity");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prosperity");
        setChoice(playerA, "X=3");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 18);
        assertLife(playerC, 18);
        assertLife(playerD, 18);
    }

    @Test
    public void testTapAbilityOnlyAffectsChosenOpponent() {
        // Whenever you draw your second card each turn, you gain 2 life.
        // {T}: You and target opponent each draw two cards.
        addCard(Zone.BATTLEFIELD, playerA, "Princess Irulan Corrino");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: You and target opponent");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 18);
        assertLife(playerC, 20);
        assertLife(playerD, 20);
    }
}
