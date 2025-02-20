package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.player.TestPlayer.CHOICE_SKIP;

/**
 * @author Susucr
 */
public class BindingAgonyTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BindingAgony Binding Agony} {1}{B}
     * Enchantment — Aura
     * Enchant creature
     * Whenever enchanted creature is dealt damage, Binding Agony deals that much damage to that creature’s controller.
     */
    private static final String agony = "Binding Agony";

    @Test
    public void test_Trigger_Once_DoubleBlocked() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, agony);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, agony, "Grizzly Bears");

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Centaur Courser", "Grizzly Bears");
        block(1, playerB, "Memnite", "Grizzly Bears");
        setChoice(playerA, CHOICE_SKIP); // Assign default damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 20 - 1 - 3);
    }

    @Test
    public void test_NonCombat_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerA, agony);
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, agony, "Grizzly Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertLife(playerB, 20 - 3);
    }
}
