package mage.cards.s;

import mage.abilities.common.EntersBattlefieldOrAttacksAllTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SearchForDagger extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("a legendary creature card");
    private static final FilterPermanent filter2 = new FilterPermanent("you commander");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(TargetController.YOU.getOwnerPredicate());
        filter2.add(CommanderPredicate.instance);
    }

    public SearchForDagger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever your commander enters or attacks, look at the top six cards of your library. You may reveal a legendary creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldOrAttacksAllTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), filter2));
    }

    private SearchForDagger(final SearchForDagger card) {
        super(card);
    }

    @Override
    public SearchForDagger copy() {
        return new SearchForDagger(this);
    }
}
