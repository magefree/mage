
package mage.cards.h;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class HarshSustenance extends CardImpl {

    public HarshSustenance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{B}");

        // Harsh Sustenance deals X damage to any target and you gain X life, where X is the number of creatures you control.
        DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent());
        Effect effect = new DamageTargetEffect(xValue);
        effect.setText("{this} deals X damage to any target");
        getSpellAbility().addEffect(effect);
        getSpellAbility().addTarget(new TargetAnyTarget());
        effect = new GainLifeEffect(xValue);
        effect.setText("and you gain X life, where X is the number of creatures you control");
        getSpellAbility().addEffect(effect);
    }

    public HarshSustenance(final HarshSustenance card) {
        super(card);
    }

    @Override
    public HarshSustenance copy() {
        return new HarshSustenance(this);
    }
}
