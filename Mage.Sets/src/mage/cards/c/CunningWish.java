
package mage.cards.c;

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
public final class CunningWish extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant card");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public CunningWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // You may reveal an instant card you own from outside the game and put it into your hand.
        this.getSpellAbility().addEffect(new WishEffect(filter));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);

        // Exile Cunning Wish.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private CunningWish(final CunningWish card) {
        super(card);
    }

    @Override
    public CunningWish copy() {
        return new CunningWish(this);
    }
}
