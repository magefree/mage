package mage.cards.t;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.BeamMeUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import java.util.UUID;

/**
 * @author muz
 */
public final class TheresCoffeeInThatNebula extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("a Plains card or nonland permanent card with mana value 3 or less");

    static {
        filter.add(
            Predicates.or(
                SubType.PLAINS.getPredicate(),
                Predicates.and(
                    Predicates.not(CardType.LAND.getPredicate()),
                    new ManaValuePredicate(ComparisonType.OR_LESS, 3)
                )
            )
        );
    }

    public TheresCoffeeInThatNebula(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Look at the top five cards of your library. You may reveal a Plains card or nonland permanent card with mana value 3 or less from among them and put it into your hand. Put the rest on the bottom in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM));

        // Beam me up {2}{W}
        this.addAbility(new BeamMeUpAbility(this, "{2}{W}"));
    }

    private TheresCoffeeInThatNebula(final TheresCoffeeInThatNebula card) {
        super(card);
    }

    @Override
    public TheresCoffeeInThatNebula copy() {
        return new TheresCoffeeInThatNebula(this);
    }
}
