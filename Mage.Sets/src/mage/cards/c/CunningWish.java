
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.WishEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Plopman
 */
public final class CunningWish extends CardImpl {

    private static final FilterCard filter = new FilterCard("an instant card");

    static {
        filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public CunningWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // You may choose an instant card you own from outside the game, reveal that card, and put it into your hand.
        this.getSpellAbility().addEffect(new WishEffect(filter));

        // Exile Cunning Wish.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public CunningWish(final CunningWish card) {
        super(card);
    }

    @Override
    public CunningWish copy() {
        return new CunningWish(this);
    }
}
