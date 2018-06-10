
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.LoseHalfLifeEffect;
import mage.abilities.effects.common.WishEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;

/**
 *
 * @author Plopman
 */
public final class DeathWish extends CardImpl {

    public DeathWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // You may choose a card you own from outside the game and put it into your hand. 
        this.getSpellAbility().addEffect(new WishEffect(new FilterCard(), false));

        // You lose half your life, rounded up. 
        this.getSpellAbility().addEffect(new LoseHalfLifeEffect());

        // Exile Death Wish.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public DeathWish(final DeathWish card) {
        super(card);
    }

    @Override
    public DeathWish copy() {
        return new DeathWish(this);
    }
}
