package org.mage.test.cards.single.iko;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author htrajan
 */
public class ZilorthaStrengthIncarnateTest extends CardTestPlayerBase {

    @Test
    public void testNotPresent_combatDamageResolvesLethalityAsNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Savai Sabertooth");
        addCard(Zone.BATTLEFIELD, playerB, "Drannith Healer");

        attack(1, playerA, "Savai Sabertooth");
        block(1, playerB, "Drannith Healer", "Savai Sabertooth");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Savai Sabertooth", 1);
        assertGraveyardCount(playerB, "Drannith Healer", 1);
    }

    @Test
    public void testPresent_combatDamageResolvesLethalityUsingPower() {
        addCard(Zone.BATTLEFIELD, playerA, "Zilortha, Strength Incarnate");
        addCard(Zone.BATTLEFIELD, playerA, "Savai Sabertooth");
        addCard(Zone.BATTLEFIELD, playerB, "Drannith Healer");

        attack(1, playerA, "Savai Sabertooth");
        block(1, playerB, "Drannith Healer", "Savai Sabertooth");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Savai Sabertooth", 0);
        assertGraveyardCount(playerB, "Drannith Healer", 1);
    }

    /*
    * 2020-04-17
    * A creature with 0 power isn’t destroyed unless it has at least 1 damage marked on it.
    */
    @Test
    public void testPresent_oneDamageRequiredToDestroyZeroPowerCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Zilortha, Strength Incarnate");
        addCard(Zone.BATTLEFIELD, playerA, "Aegis Turtle");
        addCard(Zone.BATTLEFIELD, playerB, "Aegis Turtle");

        attack(1, playerA, "Aegis Turtle");
        block(1, playerB, "Aegis Turtle", "Aegis Turtle");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Aegis Turtle", 0);
        assertGraveyardCount(playerB, "Aegis Turtle", 0);
    }

    @Test
    public void testNotPresent_flameSpillResolvesAsNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Savai Sabertooth");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Flame Spill");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Flame Spill", "Savai Sabertooth");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Savai Sabertooth", 1);
        assertGraveyardCount(playerB, "Flame Spill", 1);

        assertLife(playerA, 17);
    }

    @Test
    public void testPresent_flameSpillResolvesUsingPower() {
        addCard(Zone.BATTLEFIELD, playerA, "Zilortha, Strength Incarnate");
        addCard(Zone.BATTLEFIELD, playerA, "Savai Sabertooth");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Flame Spill");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Flame Spill", "Savai Sabertooth");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Savai Sabertooth", 1);
        assertGraveyardCount(playerB, "Flame Spill", 1);

        assertLife(playerA, 19);
    }

    /*
    * 2020-04-17
    * Because damage remains marked on a creature until the damage is removed as the turn ends, nonlethal damage dealt to a creature you control may become lethal if Zilortha enters or leaves the battlefield during that turn.
     */
    @Test
    public void testPresent_leavesBattlefield_damageResolvesLethalityUsingPower_thenCheckedAgainstToughness() {
        addCard(Zone.BATTLEFIELD, playerA, "Zilortha, Strength Incarnate");
        addCard(Zone.BATTLEFIELD, playerA, "Savai Sabertooth");
        addCard(Zone.BATTLEFIELD, playerB, "Drannith Healer");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Murder");

        attack(1, playerA, "Savai Sabertooth");
        block(1, playerB, "Drannith Healer", "Savai Sabertooth");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Murder", "Zilortha, Strength Incarnate");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Zilortha, Strength Incarnate", 1);
        assertGraveyardCount(playerA, "Savai Sabertooth", 1);
        assertGraveyardCount(playerB, "Drannith Healer", 1);
        assertGraveyardCount(playerB, "Murder", 1);
    }

    @Test
    public void testPresent_ownedByBothPlayers() {
        addCard(Zone.BATTLEFIELD, playerA, "Zilortha, Strength Incarnate");
        addCard(Zone.BATTLEFIELD, playerA, "Maned Serval");
        addCard(Zone.BATTLEFIELD, playerB, "Zilortha, Strength Incarnate");
        addCard(Zone.BATTLEFIELD, playerB, "Maned Serval");

        attack(1, playerA, "Maned Serval");
        block(1, playerB, "Maned Serval", "Maned Serval");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Maned Serval", 1);
        assertGraveyardCount(playerB, "Maned Serval", 1);
    }

    @Test
    public void testAbsent_entersBattlefield_damageResolvesLethalityUsingToughness_thenCheckedAgainstPower() {
        addCard(Zone.HAND, playerA, "Zilortha, Strength Incarnate");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Maned Serval");
        addCard(Zone.BATTLEFIELD, playerB, "Maned Serval");

        attack(1, playerA, "Maned Serval");
        block(1, playerB, "Maned Serval", "Maned Serval");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Zilortha, Strength Incarnate");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Maned Serval", 1);
        assertGraveyardCount(playerB, "Maned Serval", 0);
    }
}
