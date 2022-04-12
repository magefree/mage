package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.BlitzedCondition;
import mage.abilities.costs.AlternativeSourceCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class BlitzAbility extends AlternativeSourceCostsImpl {

    protected static final String KEYWORD = "Blitz";
    protected static final String REMINDER_TEXT = "If you cast this spell for its blitz cost, it gains haste " +
            "and \"When this creature dies, draw a card.\" Sacrifice it at the beginning of the next end step.";

    public BlitzAbility(String manaString) {
        super(KEYWORD, REMINDER_TEXT, manaString);
        Ability ability = new EntersBattlefieldAbility(
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.Custom, false),
                BlitzedCondition.instance, "", ""
        );
        ability.addEffect(new GainAbilitySourceEffect(new DiesSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
        ).setTriggerPhrase("When this creature dies, ")));
        ability.addEffect(new BlitzAddDelayedTriggeredAbilityEffect());
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    private BlitzAbility(final BlitzAbility ability) {
        super(ability);
    }

    @Override
    public BlitzAbility copy() {
        return new BlitzAbility(this);
    }
}

class BlitzAddDelayedTriggeredAbilityEffect extends OneShotEffect {

    BlitzAddDelayedTriggeredAbilityEffect() {
        super(Outcome.Benefit);
    }

    private BlitzAddDelayedTriggeredAbilityEffect(final BlitzAddDelayedTriggeredAbilityEffect effect) {
        super(effect);
    }

    @Override
    public BlitzAddDelayedTriggeredAbilityEffect copy() {
        return new BlitzAddDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPermanentEntering(source.getSourceId()) == null) {
            return false;
        }
        // init target pointer now because the Blitzed creature will only be returned from battlefield zone (now in entering state so zone change counter is not raised yet)
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect()
                        .setText("sacrifice the blitzed creature")
                        .setTargetPointer(new FixedTarget(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()) + 1))
        ), source);
        return true;
    }
}
