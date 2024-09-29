package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ThiefOfExistenceTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.ThiefOfExistence Thief of Existence} {1}{C}{G}
     * Creature — Eldrazi
     * Devoid (This card has no color.)
     * When you cast this spell, exile up to one target noncreature, nonland permanent an opponent controls with mana value 4 or less. If you do, Thief of Existence gains “When this creature leaves the battlefield, target opponent draws a card.”
     * 3/4
     */
    private static final String thief = "Thief of Existence";

    @Test
    public void test_NoTarget_NoAddedTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, thief);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Bombardment", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Sylvan Library", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, thief, true);
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice", playerB);
        setChoice(playerA, thief); // sacrifice thief to Bombardment

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 1);
        assertHandCount(playerB, 0);
        assertGraveyardCount(playerA, thief, 1);
        assertPermanentCount(playerB, 1);
    }

    @Test
    public void test_Target_AddedTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, thief);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Bombardment", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Sylvan Library", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, thief, true);
        addTarget(playerA, "Sylvan Library");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice", playerB);
        setChoice(playerA, thief); // sacrifice Thief to Bombardment
        addTarget(playerA, playerB); // thief has the added trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 1);
        assertHandCount(playerB, 1);
        assertGraveyardCount(playerA, thief, 1);
        assertPermanentCount(playerB, 0);
        assertExileCount(playerB, 1);
    }
}
