
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LoneFox
 */
public final class Sprout extends CardImpl {

    public Sprout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Create a 1/1 green Saproling creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken()));
    }

    private Sprout(final Sprout card) {
        super(card);
    }

    @Override
    public Sprout copy() {
        return new Sprout(this);
    }
}
