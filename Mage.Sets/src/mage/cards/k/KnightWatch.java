
package mage.cards.k;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KnightToken;

/**
 *
 * @author LevelX2
 */
public final class KnightWatch extends CardImpl {

    public KnightWatch (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}");


        //Create two 2/2 white Knight creature tokens with vigilance.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KnightToken(),2));
    }

    private KnightWatch(final KnightWatch card) {
        super(card);
    }

    @Override
    public KnightWatch  copy() {
        return new KnightWatch(this);
    }
}
