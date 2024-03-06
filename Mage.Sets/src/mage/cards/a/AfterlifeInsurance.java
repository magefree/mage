package mage.cards.a;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.AfterlifeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AfterlifeInsurance extends CardImpl {

    public AfterlifeInsurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W/B}");

        // Creatures you control gain afterlife 1 until end of turn. Draw a card.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new AfterlifeAbility(1), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private AfterlifeInsurance(final AfterlifeInsurance card) {
        super(card);
    }

    @Override
    public AfterlifeInsurance copy() {
        return new AfterlifeInsurance(this);
    }
}
