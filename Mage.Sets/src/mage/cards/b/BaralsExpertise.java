
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.CastWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class BaralsExpertise extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and/or creatures");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    public BaralsExpertise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Return up to three target artifacts and/or creatures to their owners' hands.
        getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getSpellAbility().addTarget(new TargetPermanent(0, 3, filter, false));

        // You may cast a card with converted mana cost 4 or less from your hand without paying its mana cost.
        getSpellAbility().addEffect(new CastWithoutPayingManaCostEffect(4).concatBy("<br>"));
    }

    private BaralsExpertise(final BaralsExpertise card) {
        super(card);
    }

    @Override
    public BaralsExpertise copy() {
        return new BaralsExpertise(this);
    }
}
