package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.BlitzedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public class BlitzAbility extends SpellAbility {

    public static final String BLITZ_ACTIVATION_VALUE_KEY = "blitzActivation";
    protected static final String KEYWORD = "Blitz";
    protected static final String REMINDER_TEXT = "If you cast this spell for its blitz cost, it gains haste " +
            "and \"When this creature dies, draw a card.\" Sacrifice it at the beginning of the next end step.";

    public BlitzAbility(Card card, String manaString) {
        super(new ManaCostsImpl<>(manaString), card.getName() + " with Blitz");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
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

    @Override
    public String getRule() {
        return "Blitz";
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (!super.activate(game, noMana)) {
            return false;
        }
        Object obj = game.getState().getValue(BLITZ_ACTIVATION_VALUE_KEY + getSourceId());
        List<Integer> blitzActivations;
        if (obj != null) {
            blitzActivations = (List<Integer>) obj;
        } else {
            blitzActivations = new ArrayList<>();
            game.getState().setValue(BLITZ_ACTIVATION_VALUE_KEY + getSourceId(), blitzActivations);
        }
        blitzActivations.add(game.getState().getZoneChangeCounter(getSourceId()));
        return true;
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
