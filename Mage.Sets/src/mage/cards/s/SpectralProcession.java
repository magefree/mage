
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author North
 */
public final class SpectralProcession extends CardImpl {

    public SpectralProcession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2/W}{2/W}{2/W}");


        // Create three 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken(), 3));
    }

    private SpectralProcession(final SpectralProcession card) {
        super(card);
    }

    @Override
    public SpectralProcession copy() {
        return new SpectralProcession(this);
    }
}
