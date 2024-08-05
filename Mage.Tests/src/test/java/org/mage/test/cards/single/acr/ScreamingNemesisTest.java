package org.mage.test.cards.single.acr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ScreamingNemesisTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.ScreamingNemesis Screaming Nemesis} {2}{R}
     * Creature — Spirit
     * Haste
     * Whenever Screaming Nemesis is dealt damage, it deals that much damage to any other target. If a player is dealt damage this way, they can’t gain life for the rest of the game.
     * 3/3
     */
    private static final String nemesis = "Screaming Nemesis";

    @Test
    public void test_Trigger_PreventGainLife() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, nemesis);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.HAND, playerB, "Angel's Mercy"); // You gain 7 life.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", nemesis);
        addTarget(playerA, playerB); // target for trigger

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Angel's Mercy");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerB, "Angel's Mercy", 1);
        assertDamageReceived(playerA, nemesis, 2);
    }

    @Test
    public void test_Trigger_Permanent_NoPreventGainLife() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, nemesis);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.HAND, playerB, "Angel's Mercy"); // You gain 7 life.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", nemesis);
        addTarget(playerA, "Centaur Courser"); // target for trigger

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Angel's Mercy");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 + 7);
        assertGraveyardCount(playerB, "Angel's Mercy", 1);
        assertDamageReceived(playerA, nemesis, 2);
        assertDamageReceived(playerB, "Centaur Courser", 2);
    }
}
