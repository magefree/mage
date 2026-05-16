package mage.player.ai;

import mage.cards.decks.analysis.DeckRole;
import mage.constants.PhaseStep;
import org.junit.Assert;
import org.junit.Test;

import java.util.EnumSet;

public class DeckStrategyAdvisorContextTest {

    @Test
    public void keepsGenericDeckPlanBonusesInMainPhasesOnly() {
        Assert.assertTrue(DeckStrategyAdvisor.shouldKeepRoleForPhase(
                PhaseStep.PRECOMBAT_MAIN,
                DeckRole.RAMP,
                EnumSet.of(DeckRole.RAMP)
        ));

        Assert.assertFalse(DeckStrategyAdvisor.shouldKeepRoleForPhase(
                PhaseStep.DECLARE_ATTACKERS,
                DeckRole.RAMP,
                EnumSet.of(DeckRole.RAMP)
        ));
        Assert.assertFalse(DeckStrategyAdvisor.shouldKeepRoleForPhase(
                PhaseStep.DECLARE_ATTACKERS,
                DeckRole.MANA_FIXING,
                EnumSet.of(DeckRole.MANA_FIXING)
        ));
        Assert.assertFalse(DeckStrategyAdvisor.shouldKeepRoleForPhase(
                PhaseStep.DECLARE_ATTACKERS,
                DeckRole.CARD_DRAW,
                EnumSet.of(DeckRole.CARD_DRAW)
        ));
        Assert.assertFalse(DeckStrategyAdvisor.shouldKeepRoleForPhase(
                PhaseStep.DECLARE_ATTACKERS,
                DeckRole.X_SPELL,
                EnumSet.of(DeckRole.X_SPELL)
        ));
    }

    @Test
    public void keepsTacticalBonusesOutsideMainPhases() {
        Assert.assertTrue(DeckStrategyAdvisor.shouldKeepRoleForPhase(
                PhaseStep.DECLARE_BLOCKERS,
                DeckRole.REMOVAL,
                EnumSet.of(DeckRole.REMOVAL)
        ));
        Assert.assertTrue(DeckStrategyAdvisor.shouldKeepRoleForPhase(
                PhaseStep.DECLARE_BLOCKERS,
                DeckRole.COMBAT_TRICK,
                EnumSet.of(DeckRole.COMBAT_TRICK)
        ));
        Assert.assertTrue(DeckStrategyAdvisor.shouldKeepRoleForPhase(
                PhaseStep.DECLARE_BLOCKERS,
                DeckRole.X_SPELL,
                EnumSet.of(DeckRole.X_SPELL, DeckRole.COMBAT_TRICK)
        ));

        Assert.assertFalse(DeckStrategyAdvisor.shouldKeepRoleForPhase(
                PhaseStep.END_TURN,
                DeckRole.COMBAT_TRICK,
                EnumSet.of(DeckRole.COMBAT_TRICK)
        ));
    }

    @Test
    public void usesTargetPolarityForOffensiveAndProtectiveRoles() {
        Assert.assertFalse(DeckStrategyAdvisor.shouldKeepRoleForTargetPolarity(
                DeckRole.REMOVAL,
                true,
                true,
                false,
                false
        ));
        Assert.assertTrue(DeckStrategyAdvisor.shouldKeepRoleForTargetPolarity(
                DeckRole.REMOVAL,
                true,
                false,
                true,
                false
        ));
        Assert.assertFalse(DeckStrategyAdvisor.shouldKeepRoleForTargetPolarity(
                DeckRole.COUNTER_OR_PROTECTION,
                true,
                false,
                true,
                false
        ));
        Assert.assertTrue(DeckStrategyAdvisor.shouldKeepRoleForTargetPolarity(
                DeckRole.COUNTER_OR_PROTECTION,
                true,
                true,
                false,
                false
        ));
        Assert.assertTrue(DeckStrategyAdvisor.shouldKeepRoleForTargetPolarity(
                DeckRole.REMOVAL,
                true,
                false,
                false,
                true
        ));
    }

    @Test
    public void commanderTaxPenaltyScalesWithTaxAndHandOptions() {
        Assert.assertEquals(0, DeckStrategyAdvisor.estimateCommanderTaxPenalty(0, 4, 120));

        int lowTaxFewOptions = DeckStrategyAdvisor.estimateCommanderTaxPenalty(2, 1, 120);
        int highTaxManyOptions = DeckStrategyAdvisor.estimateCommanderTaxPenalty(10, 5, 120);

        Assert.assertTrue("Commander tax should reduce the strategy score", lowTaxFewOptions < 0);
        Assert.assertTrue("High tax with cards in hand should be penalized more heavily",
                highTaxManyOptions < lowTaxFewOptions);
        Assert.assertTrue("Penalty should remain bounded by the configured modifier scale",
                highTaxManyOptions >= -240);
    }
}
