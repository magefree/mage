package org.mage.test.cards.single.c18;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class VarchildBetrayerOfKjeldorTest extends CardTestPlayerBase {

    // Varchild - varchild died, then next turn i played an irregular cohort and varchild's controller
    // instantly gained control of it. the triggered token entered on varchild's side too.
    // Tried rollbacking, same thing happened
    @Test
    public void testOpponentGetsSurvivorTokens() {
        // Whenever Varchild, Betrayer of Kjeldor deals combat damage to a player, that player creates that many 1/1 red Survivor creature tokens.
        // Survivors your opponents control can't block, and they can't attack you or a planeswalker you control.
        // When Varchild leaves the battlefield, gain control of all Survivors.
        addCard(Zone.HAND, playerA, "Varchild, Betrayer of Kjeldor"); // Creature 3/3 - {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Varchild, Betrayer of Kjeldor");

        attack(3, playerA, "Varchild, Betrayer of Kjeldor");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Varchild, Betrayer of Kjeldor", 1);

        assertPermanentCount(playerB, "Survivor Token", 3);
    }

    @Test
    public void testGetControlEffect() {
        // Whenever Varchild, Betrayer of Kjeldor deals combat damage to a player, that player creates that many 1/1 red Survivor creature tokens.
        // Survivors your opponents control can't block, and they can't attack you or a planeswalker you control.
        // When Varchild leaves the battlefield, gain control of all Survivors.
        addCard(Zone.HAND, playerA, "Varchild, Betrayer of Kjeldor"); // Creature 3/3 - {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // Changeling
        // When Irregular Cohort enters the battlefield, create a 2/2 colorless Shapeshifter creature token with changeling.
        addCard(Zone.HAND, playerB, "Irregular Cohort"); // Creature 2/2 - {2}{W}{W}
        // Exile target creature. Its controller gains life equal to its power.
        addCard(Zone.HAND, playerB, "Swords to Plowshares"); // Instant {W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Varchild, Betrayer of Kjeldor");

        attack(3, playerA, "Varchild, Betrayer of Kjeldor");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Swords to Plowshares", "Varchild, Betrayer of Kjeldor");

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Irregular Cohort");

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Swords to Plowshares", 1);
        assertExileCount(playerA, "Varchild, Betrayer of Kjeldor", 1);
        assertLife(playerA, 23);

        assertPermanentCount(playerA, "Survivor Token", 3);

        assertPermanentCount(playerB, "Irregular Cohort", 1);
        assertPermanentCount(playerB, "Shapeshifter Token", 1);

    }
}
