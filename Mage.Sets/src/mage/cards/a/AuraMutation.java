
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author North
 */
public final class AuraMutation extends CardImpl {

    public AuraMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{W}");


        // Destroy target enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        // create X 1/1 green Saproling creature tokens, where X is that enchantment's converted mana cost.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), TargetManaValue.instance));
    }

    private AuraMutation(final AuraMutation card) {
        super(card);
    }

    @Override
    public AuraMutation copy() {
        return new AuraMutation(this);
    }
}
