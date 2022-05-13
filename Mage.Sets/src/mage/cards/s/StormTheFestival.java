package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormTheFestival extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard(
            "permanent cards with mana value 5 or less"
    );

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 6));
    }

    public StormTheFestival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}{G}");

        // Look at the top five cards of your library.
        // You may put up to two permanent cards with mana value 5 or less from among them onto the battlefield.
        // Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 2, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM));

        // Flashback {7}{G}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{7}{G}{G}{G}")));
    }

    private StormTheFestival(final StormTheFestival card) {
        super(card);
    }

    @Override
    public StormTheFestival copy() {
        return new StormTheFestival(this);
    }
}
