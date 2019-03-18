
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
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
        setChoice(playerB, "Yes");
        addTarget(playerB, playerA);

        attack(2, playerB, "Ob Nixilis, the Fallen");
        block(2, playerA, "Wurmcoil Engine", "Ob Nixilis, the Fallen");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Ob Nixilis, the Fallen", 1);

        assertGraveyardCount(playerA, "Wurmcoil Engine", 1);
        assertPermanentCount(playerA, "Wurm", 2);

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

    @Ignore
    @Test
    public void vexingDevilFurnaceRathRedirectToPlaneswalker() {

        /*
    Vexing Devil {R}
    Creature — Devil
    When Vexing Devil enters the battlefield, any opponent may have it deal 4 damage to him or her. If a player does, sacrifice Vexing Devil.
         */
        String vDevil = "Vexing Devil";

        /*
    Nissa, Worldwaker {3}{G}{G}
    Planeswalker — Nissa
    +1: Target land you control becomes a 4/4 Elemental creature with trample. It's still a land.
    +1: Untap up to four target Forests.
    −7: Search your library for any number of basic land cards, put them onto the battlefield, then shuffle your library. Those lands become 4/4 Elemental creatures with trample. They're still lands.
         */
        String nissa = "Nissa, Worldwaker";

        /*
    Furnace of Rath {1}{R}{R}{R}
    Enchantment
    If a source would deal damage to a creature or player, it deals double that damage to that creature or player instead.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Furnace of Rath");
        addCard(Zone.HAND, playerB, vDevil);
        addCard(Zone.HAND, playerA, nissa);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nissa);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, vDevil);
        setChoice(playerA, "Yes"); // deal 8 damage to playerA and sac vexing devil (8 due to furnace)
        setChoice(playerB, "Yes"); // redirect to planeswalker
        addTarget(playerB, nissa);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, vDevil, 1);
        assertLife(playerA, 20);
        assertGraveyardCount(playerA, nissa, 1);
    }
}
