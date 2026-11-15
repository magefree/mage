package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 601.2i
 * Once the steps described in 601.2a–h are completed, effects that modify the characteristics of the spell as
 * it’s cast are applied, then the spell becomes cast. Any abilities that trigger when a spell is cast or put onto
 * the stack trigger at this time. If the spell’s controller had priority before casting it, they get priority.
 * <p>
 * 602.2b
 * The remainder of the process for activating an ability is identical to the process for casting a spell listed in
 * rules 601.2b–i. Those rules apply to activating an ability just as they apply to casting a spell. An activated
 * ability’s analog to a spell’s mana cost (as referenced in rule 601.2f) is its activation cost.
 *
 * @author JayDi85
 */
public class TriggerSourceSacrificedAsCostTest extends CardTestPlayerBase {

    @Test
    public void test_CastTrigger_Normal() {
        // Whenever you cast an instant or sorcery spell, this creature deals 2 damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Guttersnipe");
        //
        addCard(Zone.HAND, playerA, "Heartfire"); // {1}{R}, sacrifice a creature or planeswalker, 4 damage
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2 for sacrifice

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heartfire", playerB);
        setChoice(playerA, "Grizzly Bears"); // sacrifice cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20 - 2 - 4);
    }

    @Test
    public void test_CastTrigger_SourceSacrificedAsCost() {
        // Chalice of the Void ruling for the same situation:
        // Chalice of the Void has to be on the battlefield at the end of casting a spell for the ability to
        // trigger. If you sacrifice Chalice of the Void as a cost to cast a spell, its ability can't trigger.
        // (2021-03-19)

        // Whenever you cast an instant or sorcery spell, this creature deals 2 damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Guttersnipe");
        //
        addCard(Zone.HAND, playerA, "Heartfire"); // {1}{R}, sacrifice a creature or planeswalker, 4 damage
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heartfire", playerB);
        setChoice(playerA, "Guttersnipe"); // sacrifice cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Guttersnipe", 1);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_ActivateTrigger_Normal() {
        // Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that
        // ability. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Rings of Brighthearth");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Atog"); // 1/2, Sacrifice an artifact: +2/+2 until end of turn
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter"); // artifact for sacrifice

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice an artifact");
        setChoice(playerA, "Ornithopter"); // sacrifice cost
        setChoice(playerA, true); // pay {2} to copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Ornithopter", 1);
        assertPowerToughness(playerA, "Atog", 1 + 2 + 2, 2 + 2 + 2);
    }

    @Test
    public void test_ActivateTrigger_SourceSacrificedAsCost() {
        // Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that
        // ability. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Rings of Brighthearth");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Atog"); // 1/2, Sacrifice an artifact: +2/+2 until end of turn

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice an artifact");
        setChoice(playerA, "Rings of Brighthearth"); // sacrifice cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Rings of Brighthearth", 1);
        assertPowerToughness(playerA, "Atog", 1 + 2, 2 + 2);
    }
}
