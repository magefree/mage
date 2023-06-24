package mage.cards.b;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BorneUponAWind extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public BorneUponAWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // You may cast spells this turn as though they had flash.
        this.getSpellAbility().addEffect(new CastAsThoughItHadFlashAllEffect(Duration.EndOfTurn, filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private BorneUponAWind(final BorneUponAWind card) {
        super(card);
    }

    @Override
    public BorneUponAWind copy() {
        return new BorneUponAWind(this);
    }
}
