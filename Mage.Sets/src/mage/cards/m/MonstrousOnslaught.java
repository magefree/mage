
package mage.cards.m;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class MonstrousOnslaught extends CardImpl {

    public MonstrousOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Monstrous Onslaught deals X damage divided as you choose among any number of target creatures, where X is the greatest power among creatures you control as you cast Monstrous Onslaught.
        Effect effect = new DamageMultiEffect();
        effect.setText("{this} deals X damage divided as you choose among any number of target creatures, where X is the greatest power among creatures you control as you cast this spell");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES));
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint());
    }

    private MonstrousOnslaught(final MonstrousOnslaught card) {
        super(card);
    }

    @Override
    public MonstrousOnslaught copy() {
        return new MonstrousOnslaught(this);
    }
}
