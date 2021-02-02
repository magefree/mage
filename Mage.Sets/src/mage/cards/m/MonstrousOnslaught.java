
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author Styxo
 */
public final class MonstrousOnslaught extends CardImpl {

    public MonstrousOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Monstrous Onslaught deals X damage divided as you choose among any number of target creatures, where X is the greatest power among creatures you control as you cast Monstrous Onslaught.
        DynamicValue xValue = GreatestPowerAmongControlledCreaturesValue.instance;
        Effect effect = new DamageMultiEffect(xValue);
        effect.setText("{this} deals X damage divided as you choose among any number of target creatures, where X is the greatest power among creatures you control as you cast this spell");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(xValue));
    }

    private MonstrousOnslaught(final MonstrousOnslaught card) {
        super(card);
    }

    @Override
    public MonstrousOnslaught copy() {
        return new MonstrousOnslaught(this);
    }
}
