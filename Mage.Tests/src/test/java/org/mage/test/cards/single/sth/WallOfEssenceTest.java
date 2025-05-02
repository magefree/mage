package org.mage.test.cards.single.sth;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class WallOfEssenceTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.w.WallOfEssence Wall of Essence} {1}{W}
     * Creature — Wall
     * Defender (This creature can’t attack.)
     * Whenever Wall of Essence is dealt combat damage, you gain that much life.
     * 0/4
     */
    private static final String wall = "Wall of Essence";

    @Test
    public void test_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, wall);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, wall, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerB, wall, 2);
        assertLife(playerB, 20 + 2);
    }

    @Test
    public void test_NotTrigger_OtherCombatDamage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, wall);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Memnite", "Grizzly Bears");
        block(1, playerB, wall, "Grizzly Bears");

        setChoiceAmount(playerA, 2, 0); // 2 damage on Memnite, no damage to Wall

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerB, wall, 0);
        assertGraveyardCount(playerB, "Memnite", 1);
        assertLife(playerB, 20);
    }

    @Test
    public void test_NonCombat_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, wall);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", wall);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, wall, 3);
        assertLife(playerB, 20);
    }
}
