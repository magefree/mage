
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class MassiveRaid extends CardImpl {

    public MassiveRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}");


        // Massive Raid deals damage to any target equal to the number of creatures you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent())));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public MassiveRaid(final MassiveRaid card) {
        super(card);
    }

    @Override
    public MassiveRaid copy() {
        return new MassiveRaid(this);
    }
}
