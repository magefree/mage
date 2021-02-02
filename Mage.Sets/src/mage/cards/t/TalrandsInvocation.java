
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DrakeToken;

/**
 *
 * @author North
 */
public final class TalrandsInvocation extends CardImpl {

    public TalrandsInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");


        // Create two 2/2 blue Drake creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DrakeToken(), 2));
    }

    private TalrandsInvocation(final TalrandsInvocation card) {
        super(card);
    }

    @Override
    public TalrandsInvocation copy() {
        return new TalrandsInvocation(this);
    }
}
