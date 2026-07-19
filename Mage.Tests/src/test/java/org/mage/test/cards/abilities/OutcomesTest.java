package org.mage.test.cards.abilities;

import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class OutcomesTest extends CardTestPlayerBaseWithAIHelps {

    /**
     * Normal outcome from effects
     */

    @Test
    public void test_FromEffects_Single() {
        Ability abilityGood = new SimpleStaticAbility(new GainLifeEffect(10));
        Assertions.assertEquals(1, abilityGood.getEffects().getOutcomeScore(abilityGood));

        Ability abilityBad = new SimpleStaticAbility(new DamageTargetEffect(10));
        Assertions.assertEquals(-1, abilityBad.getEffects().getOutcomeScore(abilityBad));
    }

    @Test
    public void test_FromEffects_Multi() {
        Ability abilityGood = new SimpleStaticAbility(new GainLifeEffect(10));
        abilityGood.addEffect(new BoostSourceEffect(10, 10, Duration.EndOfTurn));
        Assertions.assertEquals(1 + 1, abilityGood.getEffects().getOutcomeScore(abilityGood));

        Ability abilityBad = new SimpleStaticAbility(new DamageTargetEffect(10));
        abilityBad.addEffect(new ExileTargetEffect());
        Assertions.assertEquals(-1 + -1, abilityBad.getEffects().getOutcomeScore(abilityBad));
    }

    @Test
    public void test_FromEffects_MultiCombine() {
        Ability ability = new SimpleStaticAbility(new GainLifeEffect(10));
        ability.addEffect(new BoostSourceEffect(10, 10, Duration.EndOfTurn));
        ability.addEffect(new ExileTargetEffect());
        Assertions.assertEquals(1 + 1 + -1, ability.getEffects().getOutcomeScore(ability));
    }

    @Test
    public void test_FromEffects_Default() {
        Ability ability = new LeavesBattlefieldTriggeredAbility(null, false);
        Assertions.assertEquals(0, ability.getEffects().getOutcomeScore(ability));
        Assertions.assertEquals(Outcome.Detriment, ability.getEffects().getOutcome(ability));
        Assertions.assertEquals(Outcome.BoostCreature, ability.getEffects().getOutcome(ability, Outcome.BoostCreature));
    }

    /**
     * Special outcome from ability (AI activates only good abilities)
     */

    @Test
    public void test_FromAbility_Single() {
        Ability abilityGood = new SimpleStaticAbility(new GainLifeEffect(10));
        abilityGood.addCustomOutcome(Outcome.Detriment);
        Assertions.assertEquals(-1, abilityGood.getEffects().getOutcomeScore(abilityGood));
        Assertions.assertEquals(Outcome.Detriment, abilityGood.getEffects().getOutcome(abilityGood));

        Ability abilityBad = new SimpleStaticAbility(new DamageTargetEffect(10));
        abilityBad.addCustomOutcome(Outcome.Neutral);
        Assertions.assertEquals(1, abilityBad.getEffects().getOutcomeScore(abilityBad));
        Assertions.assertEquals(Outcome.Neutral, abilityBad.getEffects().getOutcome(abilityBad));
    }

    @Test
    public void test_FromAbility_Multi() {
        Ability abilityGood = new SimpleStaticAbility(new GainLifeEffect(10));
        abilityGood.addEffect(new BoostSourceEffect(10, 10, Duration.EndOfTurn));
        abilityGood.addCustomOutcome(Outcome.Detriment);
        Assertions.assertEquals(-1 + -1, abilityGood.getEffects().getOutcomeScore(abilityGood));

        Ability abilityBad = new SimpleStaticAbility(new DamageTargetEffect(10));
        abilityBad.addEffect(new ExileTargetEffect());
        abilityBad.addCustomOutcome(Outcome.Neutral);
        Assertions.assertEquals(1 + 1, abilityBad.getEffects().getOutcomeScore(abilityBad));
    }

    @Test
    public void test_FromAbility_MultiCombine() {
        Ability ability = new SimpleStaticAbility(new GainLifeEffect(10));
        ability.addEffect(new BoostSourceEffect(10, 10, Duration.EndOfTurn));
        ability.addEffect(new ExileTargetEffect());
        ability.addCustomOutcome(Outcome.Neutral); // must "convert" all effects to good
        Assertions.assertEquals(1 + 1 + 1, ability.getEffects().getOutcomeScore(ability));
    }
}
