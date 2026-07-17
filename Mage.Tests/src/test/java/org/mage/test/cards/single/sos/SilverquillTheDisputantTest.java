package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SilverquillTheDisputantTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SilverquillTheDisputant Silverquill, the Disputant} {2}{W}{B}
     * Legendary Creature — Elder Dragon
     * Flying, vigilance
     * Each instant and sorcery spell you cast has casualty 1. (As you cast that spell, you may sacrifice a creature with power 1 or greater. When you do, copy the spell and you may choose new targets for the copy.)
     * 4/4
     */
    private static final String silverquill = "Silverquill, the Disputant";

    @Test
    public void test_Simple() {
        addCard(Zone.BATTLEFIELD, playerA, silverquill, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Squire", 1);
        addCard(Zone.HAND, playerA, "Chaplain's Blessing");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chaplain's Blessing");
        setChoice(playerA, true); // yes to casualty
        setChoice(playerA, "Squire"); // sac Squire to Casualty 1

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 + 2 * 5);
        assertGraveyardCount(playerA, "Squire", 1);
    }

    @Ignore // TODO: Casualty is currently incorrectly implemented. It should be 2 separate linked abilities.
    @Test
    public void test_SacSilverquill() {
        // Since the trigger and the payment are separate, sacrificing Silverquill
        // for the casualty ends up with no copying.
        /** 702.153a. Casualty is a keyword that represents two abilities.
         * The first is a static ability that functions while the spell with casualty is on the stack.
         * The second is a triggered ability that functions while the spell with casualty is on the stack.
         * Casualty N means "As an additional cost to cast this spell, you may sacrifice a creature with
         * power N or greater," and "When you cast this spell, if a casualty cost was paid for it, copy it.
         * If the spell has any targets, you may choose new targets for the copy." Paying a spell's casualty
         * cost follows the rules for paying additional costs in rules 601.2b and 601.2f-h. */
        addCard(Zone.BATTLEFIELD, playerA, silverquill, 1);
        addCard(Zone.HAND, playerA, "Chaplain's Blessing");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chaplain's Blessing");
        setChoice(playerA, true); // yes to casualty
        setChoice(playerA, silverquill); // sac Silverquill to Casualty 1

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, silverquill, 1);
        assertLife(playerA, 20 + 5);
    }
}
