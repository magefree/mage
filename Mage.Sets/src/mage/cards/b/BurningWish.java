
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;

/**
 *
 * @author Plopman
 */
public final class BurningWish extends CardImpl {

    private static final FilterCard filter = new FilterCard("sorcery card");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public BurningWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // You may reveal a sorcery card you own from outside the game and put it into your hand.
        this.getSpellAbility().addEffect(new WishEffect(filter));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);

        // Exile Burning Wish.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private BurningWish(final BurningWish card) {
        super(card);
    }

    @Override
    public BurningWish copy() {
        return new BurningWish(this);
    }
}
