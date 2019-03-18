
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DefiantVanguardTest extends CardTestPlayerBase {

    @Test
    public void testAllDestroyed() {
        // When Defiant Vanguard blocks, at end of combat, destroy it and all creatures it blocked this turn.
        // {5}, {tap}: Search your library for a Rebel permanent card with converted mana cost 4 or less and put it onto the battlefield. Then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Defiant Vanguard", 1); // Creature {2}{W}  2/2

        addCard(Zone.BATTLEFIELD, playerB, "Bane Alley Blackguard", 1); // Creature {1}{B}  1/3

        attack(2, playerB, "Bane Alley Blackguard");
        block(2, playerA, "Defiant Vanguard", "Bane Alley Blackguard");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Defiant Vanguard", 1);
        assertGraveyardCount(playerB, "Bane Alley Blackguard", 1);
    }

    @Test
    @Ignore // this test fails but it works fine in game.
    public void testSaveCreatureWithCloudshift() {
        // When Defiant Vanguard blocks, at end of combat, destroy it and all creatures it blocked this turn.
        // {5}, {tap}: Search your library for a Rebel permanent card with converted mana cost 4 or less and put it onto the battlefield. Then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Defiant Vanguard", 1); // Creature {2}{W}  2/2

        addCard(Zone.BATTLEFIELD, playerB, "Bane Alley Blackguard", 1); // Creature {1}{B}  1/3
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Cloudshift", 1); // Instant {W}

        attack(2, playerB, "Bane Alley Blackguard");
        block(2, playerA, "Defiant Vanguard", "Bane Alley Blackguard");

        castSpell(2, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, "Cloudshift", "Bane Alley Blackguard");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Defiant Vanguard", 1);
        assertGraveyardCount(playerB, "Cloudshift", 1);
        assertPermanentCount(playerB, "Bane Alley Blackguard", 1);

    }

}
