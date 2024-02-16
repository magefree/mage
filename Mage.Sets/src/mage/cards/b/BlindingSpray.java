package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class BlindingSpray extends CardImpl {

    public BlindingSpray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");

        // Creatures your opponents control get -4/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-4, 0, Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private BlindingSpray(final BlindingSpray card) {
        super(card);
    }

    @Override
    public BlindingSpray copy() {
        return new BlindingSpray(this);
    }
}
