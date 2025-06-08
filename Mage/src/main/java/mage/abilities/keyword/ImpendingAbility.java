package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.AlternativeSourceCostsImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.stream.Collectors;

/**
 * "Impending N–[cost]" is a keyword that represents multiple abilities.
 * The official rules are as follows:
 * (a) You may choose to pay [cost] rather than pay this spell's mana cost.
 * (b) If you chose to pay this spell's impending cost, it enters the battlefield with N time counters on it.
 * (c) As long as this permanent has a time counter on it, if it was cast for its impending cost, it's not a creature.
 * (d) At the beginning of your end step, if this permanent was cast for its impending cost, remove a time counter from it. Then if it has no time counters on it, it loses impending.
 *
 * @author TheElk801
 */
public class ImpendingAbility extends AlternativeSourceCostsImpl {

    private static final String IMPENDING_KEYWORD = "Impending";
    private static final String IMPENDING_REMINDER = "If you cast this spell for its impending cost, " +
            "it enters with %s time counters and isn't a creature until the last is removed. " +
            "At the beginning of your end step, remove a time counter from it.";
    private static final Condition counterCondition = new SourceHasCounterCondition(CounterType.TIME, ComparisonType.EQUAL_TO, 0);

    public ImpendingAbility(int amount, String manaString) {
        super(IMPENDING_KEYWORD + ' ' + amount, String.format(IMPENDING_REMINDER, CardUtil.numberToText(amount)), new ManaCostsImpl<>(manaString), IMPENDING_KEYWORD);
        this.setRuleAtTheTop(true);
        this.addSubAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new AddCountersSourceEffect(CounterType.TIME.createInstance(amount)), ImpendingCondition.instance, ""
        ), "").setRuleVisible(false));
        this.addSubAbility(new SimpleStaticAbility(new ImpendingAbilityTypeEffect()).setRuleVisible(false));
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new RemoveCounterSourceEffect(CounterType.TIME.createInstance()),
                false, ImpendingCondition.instance
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new ImpendingAbilityRemoveEffect()),
                counterCondition, "Then if it has no time counters on it, it loses impending"
        ));
        this.addSubAbility(ability.setRuleVisible(false));
    }

    private ImpendingAbility(final ImpendingAbility ability) {
        super(ability);
    }

    @Override
    public ImpendingAbility copy() {
        return new ImpendingAbility(this);
    }

    public static String getActivationKey() {
        return getActivationKey(IMPENDING_KEYWORD);
    }
}

enum ImpendingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, ImpendingAbility.getActivationKey());
    }
}

class ImpendingAbilityTypeEffect extends ContinuousEffectImpl {

    ImpendingAbilityTypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        staticText = "As long as this permanent has a time counter on it, if it was cast for its impending cost, it's not a creature.";
    }

    private ImpendingAbilityTypeEffect(final ImpendingAbilityTypeEffect effect) {
        super(effect);
    }

    @Override
    public ImpendingAbilityTypeEffect copy() {
        return new ImpendingAbilityTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!ImpendingCondition.instance.apply(game, source)) {
            return false;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent.getCounters(game).getCount(CounterType.TIME) < 1) {
            return false;
        }
        permanent.removeCardType(game, CardType.CREATURE);
        permanent.removeAllCreatureTypes(game);
        return true;
    }
}

class ImpendingAbilityRemoveEffect extends ContinuousEffectImpl {

    ImpendingAbilityRemoveEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
    }

    private ImpendingAbilityRemoveEffect(final ImpendingAbilityRemoveEffect effect) {
        super(effect);
    }

    @Override
    public ImpendingAbilityRemoveEffect copy() {
        return new ImpendingAbilityRemoveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            discard();
            return false;
        }
        permanent.removeAbilities(
                permanent
                        .getAbilities(game)
                        .stream()
                        .filter(ImpendingAbility.class::isInstance)
                        .collect(Collectors.toList()),
                source.getSourceId(), game
        );
        return true;
    }
}
