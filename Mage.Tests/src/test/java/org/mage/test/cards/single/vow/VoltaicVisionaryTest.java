package org.mage.test.cards.single.vow;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class VoltaicVisionaryTest extends CardTestPlayerBase {

    /**
     * Voltaic Visionary {1}{R}
     * Creature — Human Wizard
     * {T}: This creature deals 2 damage to you. Exile the top card of your library. You may play that card this turn.
     * Activate only as a sorcery.
     * When you play a card exiled with this creature, transform this creature.
     */
    private static final String visionary = "Voltaic Visionary";

    @Test
    public void test_CastDoubleFacedCard_Transform() {
        addCard(Zone.BATTLEFIELD, playerA, visionary);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Kazandu Mammoth"); // {1}{G}{G}, modal double-faced

        // exile
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: ");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // play
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kazandu Mammoth");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2);
        assertPermanentCount(playerA, "Kazandu Mammoth", 1);
        assertPermanentCount(playerA, "Volt-Charged Berserker", 1);
    }
}
