
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.RatToken;

/**
 *
 * @author North
 */
public final class LabRats extends CardImpl {

    public LabRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Buyback {4}
        this.addAbility(new BuybackAbility("{4}"));
        // Create a 1/1 black Rat creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RatToken()));
    }

    private LabRats(final LabRats card) {
        super(card);
    }

    @Override
    public LabRats copy() {
        return new LabRats(this);
    }
}
