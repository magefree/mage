package org.mage.test.cards.protection;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


/**
 *
 * @author LevelX2
 */
public class EightAndAHalfTailsTest extends CardTestPlayerBase {

    @Test
    public void testProtectingPlaneswalker() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Activated abilities of artifacts your opponents control can't be activated.
        // +1: Until your next turn, up to one target noncreature artifact becomes an artifact creature with power and toughness equal to its converted mana cost.
        // -2: You may choose an artifact card you own from outside the game or in exile, reveal that card, and put it into your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Karn, the Great Creator"); // Planeswalker (5)
        
        // {1}{W}: Target permanent you control gains protection from white until end of turn.
        // {1}: Target spell or permanent becomes white until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Eight-and-a-Half-Tails"); // Creature
        
        // Flying, double strike
        // Whenever a creature you control deals combat damage to a player, you and that player each gain that much life.
        // At the beginning of your end step, if you have at least 15 life more than your starting life total, each player Angel of Destiny attacked this turn loses the game.
        addCard(Zone.BATTLEFIELD, playerB, "Angel of Destiny"); // Creature

        attack(2, playerB, "Angel of Destiny", "Karn, the Great Creator");
        activateAbility(2, PhaseStep.DECLARE_ATTACKERS, playerA, "{1}{W}: Target permanent you control gains protection from white until end of turn.");
        addTarget(playerA, "Karn, the Great Creator");
        
        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Karn, the Great Creator", 1);
        assertCounterCount("Karn, the Great Creator", CounterType.LOYALTY, 5);
        
    }

}