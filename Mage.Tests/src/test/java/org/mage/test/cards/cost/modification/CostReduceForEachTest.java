package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class CostReduceForEachTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_AncientStoneIdol_Attacking() {
        // {10}
        // Flash
        // This spell costs {1} less to cast for each attacking creature.
        addCard(Zone.HAND, playerA, "Ancient Stone Idol", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10 - 2);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2); // give 2 cost reduction

        // before
        checkPlayableAbility("before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ancient Stone Idol", false);

        // prepare for attack
        attack(1, playerA, "Balduvian Bears");
        attack(1, playerA, "Balduvian Bears");

        // on attack
        checkPlayableAbility("on attack", 1, PhaseStep.DECLARE_BLOCKERS, playerA, "Cast Ancient Stone Idol", true);
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Ancient Stone Idol");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Ancient Stone Idol", 1);
    }

    @Test
    public void test_AncientStoneIdol_AttackingWithSacrifice() {
        // The total cost to cast a spell is locked in before you pay that cost. For example, if you control five attacking
        // creatures, including one you can sacrifice to add {C} to your mana pool, Ancient Stone Idol costs {5} to cast.
        // Then you can sacrifice the creature when you activate mana abilities just before paying the cost, and it still
        // costs only {5} to cast.
        // (2018-07-13)

        // {10}
        // Flash
        // This spell costs {1} less to cast for each attacking creature.
        addCard(Zone.HAND, playerA, "Ancient Stone Idol", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10 - 4);
        //
        // Sacrifice Blood Pet: Add {B}.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Pet", 2); // give 2 cost reduction + can be sacrificed as 2 mana

        // before
        checkPlayableAbility("before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ancient Stone Idol", false);

        // prepare for attack
        attack(1, playerA, "Blood Pet");
        attack(1, playerA, "Blood Pet");

        // on attack (must automaticly sacrifice creatures as mana pay)
        checkPlayableAbility("on attack", 1, PhaseStep.DECLARE_BLOCKERS, playerA, "Cast Ancient Stone Idol", true);
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Ancient Stone Idol");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Ancient Stone Idol", 1);
        assertGraveyardCount(playerA, "Blood Pet", 2);
    }

    @Test
    public void test_KhalniHydra_ColorReduce() {
        // {G}{G}{G}{G}{G}{G}{G}{G}
        // This spell costs {G} less to cast for each green creature you control.
        addCard(Zone.HAND, playerA, "Khalni Hydra", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8 - 2);
        addCard(Zone.HAND, playerA, "Balduvian Bears", 2); // give 2 cost reduction

        checkPlayableAbility("no cost reduction 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Khalni Hydra", false);

        // prepare creatures for reduce
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("no cost reduction 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Khalni Hydra", false);

        // can cast on next turn
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Khalni Hydra");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Khalni Hydra", 1);
    }

    @Test
    public void test_TorgaarFamineIncarnate_SacrificeXTargets() {
        // {6}{B}{B}
        // As an additional cost to cast this spell, you may sacrifice any number of creatures.
        // This spell costs {2} less to cast for each creature sacrificed this way.
        // When Torgaar, Famine Incarnate enters the battlefield, up to one target player's life total becomes half their starting life total, rounded down.
        addCard(Zone.HAND, playerA, "Torgaar, Famine Incarnate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8 - 4 - 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Balduvian Bears", 2); // give 4 cost reduction on sacrifice

        checkPlayableAbility("no cost reduction 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Torgaar, Famine Incarnate", false);

        // prepare creatures for reduce
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("no cost reduction 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Torgaar, Famine Incarnate", false);

        // can cast on next turn
        checkPlayableAbility("must reduce", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Torgaar, Famine Incarnate", true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Torgaar, Famine Incarnate");
        setChoice(playerA, "X=2"); // two creatures sacrifice
        setChoice(playerA, "Balduvian Bears");
        setChoice(playerA, "Balduvian Bears");
        addTarget(playerA, playerB); // target player for half life

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Torgaar, Famine Incarnate", 1);
        assertLife(playerB, 20 / 2);
    }
}
