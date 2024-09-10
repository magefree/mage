package mage.cards.f;

import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FilterOut extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("noncreature, nonland permanents");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public FilterOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Return all noncreature, nonland permanents to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter));
    }

    private FilterOut(final FilterOut card) {
        super(card);
    }

    @Override
    public FilterOut copy() {
        return new FilterOut(this);
    }
}
