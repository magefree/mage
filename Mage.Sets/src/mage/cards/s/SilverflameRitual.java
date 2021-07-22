package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
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
public final class SilverflameRitual extends CardImpl {

    public SilverflameRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        // Adamant — If at least three white mana was spent to cast this spell, creatures you control gain vigilance until end of turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SilverflameRitualEffect(), AdamantCondition.WHITE,
                "<br><i>Adamant</i> &mdash; If at least three white mana was spent to cast this spell, " +
                        "creatures you control gain vigilance until end of turn."
        ));
    }

    private SilverflameRitual(final SilverflameRitual card) {
        super(card);
    }

    @Override
    public SilverflameRitual copy() {
        return new SilverflameRitual(this);
    }
}

class SilverflameRitualEffect extends OneShotEffect {

    SilverflameRitualEffect() {
        super(Outcome.Benefit);
    }

    private SilverflameRitualEffect(final SilverflameRitualEffect effect) {
        super(effect);
    }

    @Override
    public SilverflameRitualEffect copy() {
        return new SilverflameRitualEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ), source);
        return true;
    }
}
