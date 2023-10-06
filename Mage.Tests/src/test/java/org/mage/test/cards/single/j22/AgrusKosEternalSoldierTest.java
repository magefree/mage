package org.mage.test.cards.single.j22;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class AgrusKosEternalSoldierTest extends CardTestPlayerBase {

    private static final String agrus = "Agrus Kos, Eternal Soldier"; // 3/4 Vigilance
    // Whenever Agrus Kos, Eternal Soldier becomes the target of an ability that targets only it, you may pay {1}{R/W}.
    // If you do, copy that ability for each other creature you control that ability could target. Each copy targets a different one of those creatures.
    private static final String sparkmage = "Cunning Sparkmage"; // 0/1 Haste pinger
    private static final String turtle = "Horned Turtle"; // 1/4
    private static final String firewalker = "Kor Firewalker"; // 2/2 protection from red
    // Whenever a player casts a red spell, you may gain 1 life.

    @Test
    public void testTriggerCopiesAbility() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, agrus);
        addCard(Zone.BATTLEFIELD, playerA, turtle);
        addCard(Zone.BATTLEFIELD, playerA, firewalker);
        addCard(Zone.BATTLEFIELD, playerB, sparkmage);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: {this} deals", agrus);
        setChoice(playerA, true); // pay to copy

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(sparkmage, true);
        assertTappedCount("Plateau", true, 2);
        assertDamageReceived(playerA, agrus, 1);
        assertDamageReceived(playerA, turtle, 1);
        assertDamageReceived(playerA, firewalker, 0);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testTriggerDoesntCopySpell() {
        setStrictChooseMode(true);

        String shock = "Shock";
        addCard(Zone.BATTLEFIELD, playerA, agrus);
        addCard(Zone.BATTLEFIELD, playerA, turtle);
        addCard(Zone.BATTLEFIELD, playerA, firewalker);
        addCard(Zone.HAND, playerB, shock);
        addCard(Zone.BATTLEFIELD, playerB, "Plateau");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, shock, agrus);
        setChoice(playerA, true); // gain 1 life

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, agrus, 2);
        assertDamageReceived(playerA, turtle, 0);
        assertDamageReceived(playerA, firewalker, 0);
        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }

}
