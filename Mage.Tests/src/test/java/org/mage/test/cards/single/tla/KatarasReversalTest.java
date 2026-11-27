package org.mage.test.cards.single.tla;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.stack.Spell;
import mage.target.TargetStackObject;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Katara's Reversal: "Counter up to four target spells and/or abilities.
 * Untap up to four target artifacts and/or creatures."
 * <p>
 * Rule 115.5: a spell or ability on the stack is an illegal target for itself,
 * so on an empty stack the first group has no legal target and is simply
 * skipped.
 */
public class KatarasReversalTest extends CardTestPlayerBase {

    private static final String REVERSAL = "Katara's Reversal"; // {2}{U}{U} instant
    private static final String MEMNITE = "Memnite";

    @Test
    public void testEmptyStackOffersNoSelfTargetAndStillUntaps() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, REVERSAL);
        addCard(Zone.BATTLEFIELD, playerB, MEMNITE, 1, true);

        // the counter group has no legal target (skipped); the untap group takes Memnite
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, REVERSAL);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, MEMNITE);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, REVERSAL, 1);
        assertTapped(MEMNITE, false);
    }

    @Test
    public void testCountersOtherSpellButNotItself() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, REVERSAL);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, MEMNITE, 1, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        // the Bolt is the only spell the Reversal can counter; the Reversal is not offered as its own target
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, REVERSAL, "Lightning Bolt", "Lightning Bolt");
        addTarget(playerA, MEMNITE);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, REVERSAL, 1);
        assertLife(playerB, 20);
        assertTapped(MEMNITE, false);
    }

    @Test
    public void testSpellIsNotAPossibleTargetOfItself() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, REVERSAL);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, REVERSAL);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        // runs on playerA's priority with the Reversal on the stack
        runCode("self is no candidate", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Spell spell = game.getStack().stream()
                    .filter(Spell.class::isInstance).map(Spell.class::cast)
                    .filter(s -> REVERSAL.equals(s.getName())).findFirst().orElse(null);
            Assert.assertNotNull("the Reversal is on the stack", spell);
            TargetStackObject target = new TargetStackObject(0, 4, StaticFilters.FILTER_SPELL_OR_ABILITY);
            Assert.assertFalse("rule 115.5: not a possible target", target.possibleTargets(
                    player.getId(), spell.getSpellAbility(), game).contains(spell.getId()));
            Assert.assertFalse("rule 115.5: not targetable", target.canTarget(spell.getId(), spell.getSpellAbility(), game));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, REVERSAL, 1);
    }
}
