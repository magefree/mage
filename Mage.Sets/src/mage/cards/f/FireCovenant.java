
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author magenoxx
 */
public final class FireCovenant extends CardImpl {

    public FireCovenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{R}");


        // As an additional cost to cast Fire Covenant, pay X life.
        this.getSpellAbility().addCost(new PayVariableLifeCost(true));

        // Fire Covenant deals X damage divided as you choose among any number of target creatures.
        DynamicValue xValue = GetXValue.instance;
        this.getSpellAbility().addEffect(new DamageMultiEffect(xValue));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(xValue));
    }

    private FireCovenant(final FireCovenant card) {
        super(card);
    }

    @Override
    public FireCovenant copy() {
        return new FireCovenant(this);
    }
}
