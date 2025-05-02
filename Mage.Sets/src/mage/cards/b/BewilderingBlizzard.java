package mage.cards.b;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BewilderingBlizzard extends CardImpl {

    public BewilderingBlizzard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Draw three cards. Creatures your opponents control get -3/-0 until end of turn.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -3, 0, Duration.EndOfTurn,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        ));
    }

    private BewilderingBlizzard(final BewilderingBlizzard card) {
        super(card);
    }

    @Override
    public BewilderingBlizzard copy() {
        return new BewilderingBlizzard(this);
    }
}
