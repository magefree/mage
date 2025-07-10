package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SystemsOverride extends CardImpl {

    public SystemsOverride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target artifact or creature until end of turn. Untap that permanent. It gains haste until end of turn. If it's a Spacecraft, put ten charge counters on it. If you do, remove ten charge counters from it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that permanent"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new SystemsOverrideEffect());
    }

    private SystemsOverride(final SystemsOverride card) {
        super(card);
    }

    @Override
    public SystemsOverride copy() {
        return new SystemsOverride(this);
    }
}

class SystemsOverrideEffect extends OneShotEffect {

    SystemsOverrideEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a Spacecraft, put ten charge counters on it. If you do, " +
                "remove ten charge counters from it at the beginning of the next end step.";
    }

    private SystemsOverrideEffect(final SystemsOverrideEffect effect) {
        super(effect);
    }

    @Override
    public SystemsOverrideEffect copy() {
        return new SystemsOverrideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.hasSubtype(SubType.SPACECRAFT, game)
                || !permanent.addCounters(CounterType.CHARGE.createInstance(10), source, game)) {
            return false;
        }
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new RemoveCounterTargetEffect(CounterType.CHARGE.createInstance(10))
                        .setTargetPointer(new FixedTarget(permanent, game))
        ), source);
        return true;
    }
}
