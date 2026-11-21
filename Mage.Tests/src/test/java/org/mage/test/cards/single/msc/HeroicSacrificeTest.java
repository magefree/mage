package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Alexsandr0x
 */
public class HeroicSacrificeTest extends CardTestPlayerBase {

    // "Choose target creature you control. Until end of turn, all damage that would be dealt
    // to you and creatures you control is dealt to the chosen creature instead (if it's still
    // on the battlefield). When that creature dies this turn, put its counters on up to one
    // target creature you control and draw a card."

    @Test
    public void testDamageToPlayerRedirected() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Giant Spider"); // 2/4, survives the bolt
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Heroic Sacrifice");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroic Sacrifice");
        addTarget(playerA, "Giant Spider");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20); // 3 damage to player redirected to the spider
        assertPermanentCount(playerA, "Giant Spider", 1);
        assertDamageReceived(playerA, "Giant Spider", 3);
    }

    @Test
    public void testDamageToCreatureRedirected() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Giant Spider"); // chosen creature, 2/4
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // would die to the bolt without redirect
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Heroic Sacrifice");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroic Sacrifice");
        addTarget(playerA, "Giant Spider");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1); // damage redirected away from it
        assertPermanentCount(playerA, "Giant Spider", 1);
        assertDamageReceived(playerA, "Giant Spider", 3);
        assertDamageReceived(playerA, "Grizzly Bears", 0);
        assertLife(playerA, 20);
    }

    @Test
    public void testCombatDamageRedirected() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Giant Spider"); // chosen creature, 2/4
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // blocker, would die without redirect
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Heroic Sacrifice");
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroic Sacrifice");
        addTarget(playerA, "Giant Spider");

        attack(2, playerB, "Hill Giant");
        block(2, playerA, "Grizzly Bears", "Hill Giant");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertDamageReceived(playerA, "Grizzly Bears", 0);
        assertDamageReceived(playerA, "Giant Spider", 3);
        assertDamageReceived(playerB, "Hill Giant", 2); // the blocker still deals its damage
        assertLife(playerA, 20);
    }

    @Test
    public void testDamageToPlaneswalkerNotRedirected() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Giant Spider");
        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane"); // loyalty 4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Heroic Sacrifice");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroic Sacrifice");
        addTarget(playerA, "Giant Spider");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Ajani Goldmane");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // only creatures are covered, unlike Gideon's Sacrifice ("permanents")
        assertCounterCount(playerA, "Ajani Goldmane", CounterType.LOYALTY, 1);
        assertDamageReceived(playerA, "Giant Spider", 0);
    }

    @Test
    public void testNoRedirectAfterChosenLeavesBattlefield() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Heroic Sacrifice");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.HAND, playerB, "Unsummon");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroic Sacrifice");
        addTarget(playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Unsummon", "Grizzly Bears");
        castSpell(1, PhaseStep.END_TURN, playerB, "Lightning Bolt", playerA);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 17); // 614.9: chosen creature is gone, damage not redirected
        assertHandCount(playerA, 1); // the returned Grizzly Bears
    }

    @Test
    public void testDiesMovesCountersAndDraws() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2, will die to the redirected bolt
        addCard(Zone.BATTLEFIELD, playerA, "Yoked Ox"); // recipient of the counters
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Heroic Sacrifice");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", CounterType.P1P1, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroic Sacrifice");
        addTarget(playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        addTarget(playerA, "Yoked Ox"); // up to one target creature for the counters

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0); // died
        assertCounterCount(playerA, "Yoked Ox", CounterType.P1P1, 1);
        assertHandCount(playerA, 1); // drew a card
    }

    @Test
    public void testDiesWithoutCountersStillDraws() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // no counters
        addCard(Zone.BATTLEFIELD, playerA, "Yoked Ox");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Heroic Sacrifice");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroic Sacrifice");
        addTarget(playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        addTarget(playerA, "Yoked Ox"); // nothing to move, but the choice still happens

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertCounterCount(playerA, "Yoked Ox", CounterType.P1P1, 0);
        assertHandCount(playerA, 1); // drew even with no counters to move
    }

    @Test
    public void testOpponentRemovesAnnouncedTargetFizzlesAbility() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // dies to the redirected bolt
        addCard(Zone.BATTLEFIELD, playerA, "Yoked Ox"); // announced recipient, then doom bladed
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Heroic Sacrifice");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Doom Blade");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", CounterType.P1P1, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroic Sacrifice");
        addTarget(playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        // when the delayed trigger goes on the stack, playerA announces Yoked Ox as the target
        addTarget(playerA, "Yoked Ox");
        // playerB responds by killing the announced target (only once the trigger is on the stack)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Doom Blade", "Yoked Ox",
                "put its counters on up to one");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0); // died
        assertGraveyardCount(playerA, "Yoked Ox", 1); // killed in response to the trigger
        assertHandCount(playerA, 0); // all targets illegal → the ability doesn't resolve → no draw
    }
}
