package org.mage.test.cards.single.ons;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TephradermTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.Tephraderm Tephraderm} {4}{R}
     * Creature — Beast
     * Whenever a creature deals damage to Tephraderm, Tephraderm deals that much damage to that creature.
     * Whenever a spell deals damage to Tephraderm, Tephraderm deals that much damage to that spell’s controller.
     * 4/5
     */
    private static final String tephraderm = "Tephraderm";

    @Test
    public void test_Bolt() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tephraderm);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", tephraderm);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, tephraderm, 3);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Combat_Damage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tephraderm);
        addCard(Zone.BATTLEFIELD, playerB, "Indomitable Ancients"); // 2/10

        attack(1, playerA, tephraderm, playerB);
        block(1, playerB, "Indomitable Ancients", tephraderm);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, tephraderm, 2);
        assertDamageReceived(playerB, "Indomitable Ancients", 4 + 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_NonCombat_Damage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tephraderm);
        addCard(Zone.BATTLEFIELD, playerB, "Indomitable Ancients"); // 2/10
        addCard(Zone.HAND, playerB, "Bite Down");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Bite Down", "Indomitable Ancients^" + tephraderm);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, tephraderm, 2);
        assertDamageReceived(playerB, "Indomitable Ancients", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
