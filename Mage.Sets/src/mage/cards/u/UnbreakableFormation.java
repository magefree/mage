package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnbreakableFormation extends CardImpl {

    public UnbreakableFormation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Creatures you control gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));

        // Addendum — If you cast this spell during your main phase, put a +1/+1 counter on each of those creatures, and they also gain vigilance until end of turn.
        this.getSpellAbility().addEffect(new UnbreakableFormationEffect());
    }

    private UnbreakableFormation(final UnbreakableFormation card) {
        super(card);
    }

    @Override
    public UnbreakableFormation copy() {
        return new UnbreakableFormation(this);
    }
}

class UnbreakableFormationEffect extends OneShotEffect {

    UnbreakableFormationEffect() {
        super(Outcome.Benefit);
        staticText = "<br><i>Addendum</i> &mdash; If you cast this spell during your main phase, " +
                "put a +1/+1 counter on each of those creatures and they gain vigilance until end of turn.";
    }

    private UnbreakableFormationEffect(final UnbreakableFormationEffect effect) {
        super(effect);
    }

    @Override
    public UnbreakableFormationEffect copy() {
        return new UnbreakableFormationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!AddendumCondition.instance.apply(game, source)) {
            return false;
        }
        game.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ), source);
        return new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).apply(game, source);
    }
}