package mage.cards.z;

import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZealousDisplay extends CardImpl {

    public ZealousDisplay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Creatures you control get +2/+0 until end of turn. If it's not your turn, untap those creatures.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE),
                NotMyTurnCondition.instance, "If it's not your turn, untap those creatures"
        ));
    }

    private ZealousDisplay(final ZealousDisplay card) {
        super(card);
    }

    @Override
    public ZealousDisplay copy() {
        return new ZealousDisplay(this);
    }
}
