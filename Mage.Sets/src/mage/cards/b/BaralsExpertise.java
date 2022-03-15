
package mage.cards.b;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BaralsExpertise extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and/or creatures");
    private static final FilterCard filter2 = new FilterCard("a spell with mana value 4 or less");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    public BaralsExpertise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Return up to three target artifacts and/or creatures to their owners' hands.
        getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getSpellAbility().addTarget(new TargetPermanent(0, 3, filter, false));

        // You may cast a card with converted mana cost 4 or less from your hand without paying its mana cost.
        getSpellAbility().addEffect(new CastFromHandForFreeEffect(filter2).concatBy("<br>"));
    }

    private BaralsExpertise(final BaralsExpertise card) {
        super(card);
    }

    @Override
    public BaralsExpertise copy() {
        return new BaralsExpertise(this);
    }
}
