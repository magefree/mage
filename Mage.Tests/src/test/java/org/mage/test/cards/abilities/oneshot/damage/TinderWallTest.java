package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TinderWallTest extends CardTestPlayerBase {

    /**
     * This test fails because the target is no longer seen as valid during
     * resolution, because the source is no longer on the battlefield
     * (sacrificed as cost) and therefore it's no longer blocking the target.
     */
    @Test
    public void testDamageFromInstantToPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Defender
        // Sacrifice Tinder Wall: Add {R}{R}.
        // {R}, Sacrifice Tinder Wall: Tinder Wall deals 2 damage to target creature it's blocking.
        addCard(Zone.BATTLEFIELD, playerA, "Tinder Wall"); // Creature 0/3

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Tinder Wall", "Silvercoat Lion");
        activateAbility(2, PhaseStep.DECLARE_BLOCKERS, playerA, "{R}, Sacrifice", "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Tinder Wall", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

}
