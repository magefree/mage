package mage.cards.h;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeroicCharge extends CardImpl {

    public HeroicCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Kicker {1}{R}
        this.addAbility(new KickerAbility("{1}{R}"));

        // Creatures you control get +2/+1 until end of turn. If this spell was kicked, those creatures also gain trample until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE
                )), KickedCondition.ONCE, "If this spell was kicked, " +
                "those creatures also gain trample until end of turn."
        ));
    }

    private HeroicCharge(final HeroicCharge card) {
        super(card);
    }

    @Override
    public HeroicCharge copy() {
        return new HeroicCharge(this);
    }
}
