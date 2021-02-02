
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CentaurToken;

/**
 *
 * @author LevelX2
 */
public final class CallOfTheConclave extends CardImpl {

    public CallOfTheConclave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}{W}");


        // Create a 3/3 green Centaur creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CentaurToken()));
    }

    private CallOfTheConclave(final CallOfTheConclave card) {
        super(card);
    }

    @Override
    public CallOfTheConclave copy() {
        return new CallOfTheConclave(this);
    }
}