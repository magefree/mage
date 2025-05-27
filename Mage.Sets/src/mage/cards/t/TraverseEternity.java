package mage.cards.t;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TraverseEternity extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("historic permanents you control");

    static {
        filter.add(HistoricPredicate.instance);
    }

    private static final GreatestAmongPermanentsValue xValue = new GreatestAmongPermanentsValue(GreatestAmongPermanentsValue.Quality.ManaValue, filter);
    private static final Hint hint = xValue.getHint();

    public TraverseEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Draw cards equal to the greatest mana value among historic permanents you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(xValue)
                .setText("draw cards equal to the greatest mana value among historic permanents you control"));
        this.getSpellAbility().addHint(hint);
    }

    private TraverseEternity(final TraverseEternity card) {
        super(card);
    }

    @Override
    public TraverseEternity copy() {
        return new TraverseEternity(this);
    }
}