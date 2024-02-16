
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HeartbeatOfSpringTest extends CardTestPlayerBase {

    /**
     * Heartbeat of Spring does not function on urza's
     */
    @Test
    public void testWorksForUrzasLand() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // {T}: Add {C}. If you control an Urza's Power-Plant and an Urza's Tower, add {C}{C} instead.
        addCard(Zone.HAND, playerA, "Urza's Mine", 1);
        // Whenever a player taps a land for mana, that player adds one mana of any type that land produced.
        addCard(Zone.HAND, playerA, "Heartbeat of Spring"); // {2}{G}
        // Whenever a player casts a white spell, you may gain 1 life.
        addCard(Zone.HAND, playerA, "Angel's Feather"); // {2}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heartbeat of Spring", true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Mine");

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}:");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Angel's Feather");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Heartbeat of Spring", 1);
        assertPermanentCount(playerA, "Angel's Feather", 1);
    }
}
