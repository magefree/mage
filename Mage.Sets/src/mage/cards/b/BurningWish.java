
package mage.cards.b;

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
public final class BurningWish extends CardImpl {

    private static final FilterCard filter = new FilterCard("a sorcery card");

    static {
        filter.add(new CardTypePredicate(CardType.SORCERY));
    }

    public BurningWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // You may choose a sorcery card you own from outside the game, reveal that card, and put it into your hand.
        this.getSpellAbility().addEffect(new WishEffect(filter));

        // Exile Burning Wish.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public BurningWish(final BurningWish card) {
        super(card);
    }

    @Override
    public BurningWish copy() {
        return new BurningWish(this);
    }
}
