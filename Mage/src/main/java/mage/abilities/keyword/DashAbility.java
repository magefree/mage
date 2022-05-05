package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.DashedCondition;
import mage.abilities.costs.AlternativeSourceCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class DashAbility extends AlternativeSourceCostsImpl {

    protected static final String KEYWORD = "Dash";
    protected static final String REMINDER_TEXT = "You may cast this spell for its dash cost. "
            + "If you do, it gains haste, and it's returned from the battlefield to its owner's "
            + "hand at the beginning of the next end step.";

    public DashAbility(String manaString) {
        super(KEYWORD, REMINDER_TEXT, manaString);
        Ability ability = new EntersBattlefieldAbility(
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.Custom, false),
                DashedCondition.instance, "", "");
        ability.addEffect(new DashAddDelayedTriggeredAbilityEffect());
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    private DashAbility(final DashAbility ability) {
        super(ability);
    }

    @Override
    public DashAbility copy() {
        return new DashAbility(this);
    }
}

class DashAddDelayedTriggeredAbilityEffect extends OneShotEffect {

    DashAddDelayedTriggeredAbilityEffect() {
        super(Outcome.Benefit);
    }

    private DashAddDelayedTriggeredAbilityEffect(final DashAddDelayedTriggeredAbilityEffect effect) {
        super(effect);
    }

    @Override
    public DashAddDelayedTriggeredAbilityEffect copy() {
        return new DashAddDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPermanentEntering(source.getSourceId()) == null) {
            return false;
        }
        // init target pointer now because the dashed creature will only be returned from battlefield zone (now in entering state so zone change counter is not raised yet)
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToHandTargetEffect()
                        .setText("return the dashed creature from the battlefield to its owner's hand")
                        .setTargetPointer(new FixedTarget(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()) + 1))
        ), source);
        return true;
    }
}
