
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author North
 */
public final class ChainReaction extends CardImpl {

    public ChainReaction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");


        // Chain Reaction deals X damage to each creature, where X is the number of creatures on the battlefield.
        Effect effect = new DamageAllEffect(new PermanentsOnBattlefieldCount(new FilterCreaturePermanent()), new FilterCreaturePermanent());
        effect.setText("{this} deals X damage to each creature, where X is the number of creatures on the battlefield");
        this.getSpellAbility().addEffect(effect);
    }

    private ChainReaction(final ChainReaction card) {
        super(card);
    }

    @Override
    public ChainReaction copy() {
        return new ChainReaction(this);
    }
}
