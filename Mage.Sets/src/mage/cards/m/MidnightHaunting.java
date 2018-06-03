
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author nantuko
 */
public final class MidnightHaunting extends CardImpl {

    public MidnightHaunting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Create two 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken("ISD"), 2));
    }

    public MidnightHaunting(final MidnightHaunting card) {
        super(card);
    }

    @Override
    public MidnightHaunting copy() {
        return new MidnightHaunting(this);
    }
}
