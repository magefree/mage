package mage.cards.c;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConsultTheStarCharts extends CardImpl {

    public ConsultTheStarCharts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));

        // Look at the top X cards of your library, where X is the number of lands you control. Put one of those cards into your hand. If this spell was kicked, put two of those cards into your hand instead. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LookLibraryAndPickControllerEffect(LandsYouControlCount.instance, 2, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                new LookLibraryAndPickControllerEffect(LandsYouControlCount.instance, 1, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                KickedCondition.ONCE, "look at the top X cards of your library, where X is the number of lands you control. " +
                "Put one of those cards into your hand. If this spell was kicked, put two of those cards into your hand instead. " +
                "Put the rest on the bottom of your library in a random order"
        ));
    }

    private ConsultTheStarCharts(final ConsultTheStarCharts card) {
        super(card);
    }

    @Override
    public ConsultTheStarCharts copy() {
        return new ConsultTheStarCharts(this);
    }
}
