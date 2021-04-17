
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class AetherMutation extends CardImpl {

    public AetherMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{U}");


        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // create X 1/1 green Saproling creature tokens, where X is that creature's converted mana cost.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), TargetManaValue.instance));
    }

    private AetherMutation(final AetherMutation card) {
        super(card);
    }

    @Override
    public AetherMutation copy() {
        return new AetherMutation(this);
    }
}
