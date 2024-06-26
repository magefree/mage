package mage.cards.d;

import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.HistoricPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Desynchronization extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(Predicates.not(HistoricPredicate.instance));
    }

    public Desynchronization(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Return each nonland permanent that's not historic to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter)
                .setText("return each nonland permanent that's not historic to its owner's hand"));
    }

    private Desynchronization(final Desynchronization card) {
        super(card);
    }

    @Override
    public Desynchronization copy() {
        return new Desynchronization(this);
    }
}
