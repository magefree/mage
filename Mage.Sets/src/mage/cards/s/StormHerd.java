
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PegasusToken;

/**
 *
 * @author fireshoes
 */
public final class StormHerd extends CardImpl {

    public StormHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{8}{W}{W}");

        // create X 1/1 white Pegasus creature tokens with flying, where X is your life total.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PegasusToken(), ControllerLifeCount.instance));
    }

    private StormHerd(final StormHerd card) {
        super(card);
    }

    @Override
    public StormHerd copy() {
        return new StormHerd(this);
    }
}
