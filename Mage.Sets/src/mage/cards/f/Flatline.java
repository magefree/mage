package mage.cards.f;

import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Flatline extends CardImpl {

    public Flatline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Creatures your opponents control have base power and toughness 0/1 until end of turn.
        this.getSpellAbility().addEffect(new SetBasePowerToughnessAllEffect(
                0, 1, Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES
        ));
    }

    private Flatline(final Flatline card) {
        super(card);
    }

    @Override
    public Flatline copy() {
        return new Flatline(this);
    }
}
