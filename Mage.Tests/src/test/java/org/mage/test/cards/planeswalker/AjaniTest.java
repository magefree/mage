
package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AjaniTest extends CardTestPlayerBase {

    @Test
    public void CastAjani() {
        // +1: You gain 2 life.
        // -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        // -6: Put a white Avatar creature token onto the battlefield. It has "This creature's power and toughness are each equal to your life total."
        addCard(Zone.HAND, playerA, "Ajani Goldmane"); // {2}{W}{W} starts with 4 Loyality counters
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ajani Goldmane");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: You gain 2 life");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ajani Goldmane", 1);
        assertCounterCount("Ajani Goldmane", CounterType.LOYALTY, 5);  // 4 + 1 = 5

        assertLife(playerA, 22);
        assertLife(playerB, 20);
    }

    @Test
    public void CastAjaniWithOathOfGideon() {
        // +1: You gain 2 life.
        // -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        // -6: Put a white Avatar creature token onto the battlefield. It has "This creature's power and toughness are each equal to your life total."
        addCard(Zone.HAND, playerA, "Ajani Goldmane"); // {2}{W}{W} starts with 4 Loyality counters
        // When Oath of Gideon enters the battlefield, put two 1/1 Kor Ally creature tokens onto the battlefield.
        // Each planeswalker you control enters the battlefield with an additional loyalty counter on it.
        addCard(Zone.HAND, playerA, "Oath of Gideon"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oath of Gideon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ajani Goldmane");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: You gain 2 life");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kor Ally Token", 2);
        assertPermanentCount(playerA, "Oath of Gideon", 1);
        assertPermanentCount(playerA, "Ajani Goldmane", 1);
        assertCounterCount("Ajani Goldmane", CounterType.LOYALTY, 6);  // 4 + 1 + 1 = 6

        assertLife(playerA, 22);
        assertLife(playerB, 20);
    }

}
