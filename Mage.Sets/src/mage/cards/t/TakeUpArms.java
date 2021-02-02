
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.WarriorToken;

/**
 *
 * @author emerald000
 */
public final class TakeUpArms extends CardImpl {

    public TakeUpArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{W}");


        // Create three 1/1 white Warrior creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WarriorToken(), 3));
    }

    private TakeUpArms(final TakeUpArms card) {
        super(card);
    }

    @Override
    public TakeUpArms copy() {
        return new TakeUpArms(this);
    }
}
