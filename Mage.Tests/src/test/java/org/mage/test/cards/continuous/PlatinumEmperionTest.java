
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PlatinumEmperionTest extends CardTestPlayerBase {

    @Test
    public void testLifeGain() {
        // Your life total can't change.
        addCard(Zone.BATTLEFIELD, playerA, "Platinum Emperion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // You gain 3 life.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Reviving Dose");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reviving Dose");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Reviving Dose", 1);
        assertHandCount(playerA, 1);

        assertLife(playerA, 20);
    }

    @Test
    public void testLifeLoose() {
        // Your life total can't change.
        addCard(Zone.BATTLEFIELD, playerA, "Platinum Emperion", 1);
        // You draw two cards and you lose 2 life.
        addCard(Zone.HAND, playerA, "Night's Whisper");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Night's Whisper");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Night's Whisper", 1);
        assertHandCount(playerA, 2);

        assertLife(playerA, 20);
    }

    /**
     * Swords to Plowshares and Platinum Emperion doesn't work
     */
    @Test
    public void testSwordsToPlowshares() {
        // Your life total can't change.
        addCard(Zone.BATTLEFIELD, playerA, "Platinum Emperion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Exile target creature.
        // Its controller gains life equal to its power.
        addCard(Zone.HAND, playerB, "Swords to Plowshares");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Swords to Plowshares", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Swords to Plowshares", 1);
        assertExileCount("Silvercoat Lion", 1);

        assertLife(playerA, 20);
    }

}
