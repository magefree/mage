
package mage.cards.h;

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
public final class Hydrolash extends CardImpl {

    public Hydrolash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Attacking creatures get -2/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Hydrolash(final Hydrolash card) {
        super(card);
    }

    @Override
    public Hydrolash copy() {
        return new Hydrolash(this);
    }
}
