
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class DamageEffectsTest extends CardTestPlayerBase {

    /**
     * Just encountered another bug. With Wurmcoil Engine out and with a
     * Gratuitous Violence on the field, I only gained 6 life on blocking rather
     * than 12 life.
     */
    @Test
    public void testDamageIsDoubledWithLifelink() {
        // Landfall - Whenever a land enters the battlefield under your control, you may have target player lose 3 life.
        // If you do, put three +1/+1 counters on Ob Nixilis, the Fallen.
        addCard(Zone.BATTLEFIELD, playerB, "Ob Nixilis, the Fallen");
        addCard(Zone.HAND, playerB, "Mountain");

        // Deathtouch, lifelink
        // When Wurmcoil Engine dies, create a 3/3 colorless Wurm artifact creature token with deathtouch and a 3/3 colorless Wurm artifact creature token with lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Wurmcoil Engine");
        // If a creature you control would deal damage to a creature or player, it deals double that damage to that creature or player instead.
        addCard(Zone.BATTLEFIELD, playerA, "Gratuitous Violence");

        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Mountain");
        setChoice(playerB, true);
        addTarget(playerB, playerA);

        attack(2, playerB, "Ob Nixilis, the Fallen");
        block(2, playerA, "Wurmcoil Engine", "Ob Nixilis, the Fallen");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Ob Nixilis, the Fallen", 1);

        assertGraveyardCount(playerA, "Wurmcoil Engine", 1);
        assertPermanentCount(playerA, "Phyrexian Wurm Token", 2);

        assertLife(playerB, 20);
        assertLife(playerA, 29); // -2 from Ob Nixilis + 12 from double damage with lifelink from Wurmcoil Engine

    }

    @Test
    public void testDamageToPlayer() {
        // Deathtouch, lifelink
        // When Wurmcoil Engine dies, create a 3/3 colorless Wurm artifact creature token with deathtouch and a 3/3 colorless Wurm artifact creature token with lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Wurmcoil Engine");
        // If a creature you control would deal damage to a creature or player, it deals double that damage to that creature or player instead.
        addCard(Zone.BATTLEFIELD, playerA, "Gratuitous Violence");

        attack(1, playerA, "Wurmcoil Engine");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Wurmcoil Engine", 1);

        assertLife(playerB, 8);
        assertLife(playerA, 32);

    }
}
