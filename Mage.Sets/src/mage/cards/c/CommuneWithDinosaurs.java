package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author TheElk801
 */
public final class CommuneWithDinosaurs extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dinosaur or land card");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                SubType.DINOSAUR.getPredicate()
        ));
    }

    public CommuneWithDinosaurs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        //Look at the top five cards of your library. You may reveal a Dinosaur or land card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 1, filter, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private CommuneWithDinosaurs(final CommuneWithDinosaurs card) {
        super(card);
    }

    @Override
    public CommuneWithDinosaurs copy() {
        return new CommuneWithDinosaurs(this);
    }
}
