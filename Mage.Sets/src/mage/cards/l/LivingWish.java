
package mage.cards.l;

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
public final class LivingWish extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or land card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public LivingWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // You may reveal a creature or land card you own from outside the game and put it into your hand.
        this.getSpellAbility().addEffect(new WishEffect(filter));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);

        // Exile Living Wish.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private LivingWish(final LivingWish card) {
        super(card);
    }

    @Override
    public LivingWish copy() {
        return new LivingWish(this);
    }
}
