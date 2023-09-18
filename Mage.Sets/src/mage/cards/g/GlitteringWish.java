
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author Plopman
 */
public final class GlitteringWish extends CardImpl {

    private static final FilterCard filter = new FilterCard("multicolored card");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public GlitteringWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{W}");

        // You may reveal a multicolored card you own from outside the game and put it into your hand.
        this.getSpellAbility().addEffect(new WishEffect(filter));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);

        // Exile Glittering Wish.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private GlitteringWish(final GlitteringWish card) {
        super(card);
    }

    @Override
    public GlitteringWish copy() {
        return new GlitteringWish(this);
    }
}
