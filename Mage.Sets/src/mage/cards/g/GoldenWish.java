
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Plopman
 */
public final class GoldenWish extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or enchantment card");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public GoldenWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // You may choose an artifact or enchantment card you own from outside the game, reveal that card, and put it into your hand. 
        this.getSpellAbility().addEffect(new WishEffect(filter));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);

        // Exile Golden Wish.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private GoldenWish(final GoldenWish card) {
        super(card);
    }

    @Override
    public GoldenWish copy() {
        return new GoldenWish(this);
    }
}
