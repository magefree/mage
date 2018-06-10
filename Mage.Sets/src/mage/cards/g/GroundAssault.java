
package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GroundAssault extends CardImpl {

    public GroundAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}{G}");


        // Ground Assault deals damage to target creature equal to the number of lands you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(new FilterControlledLandPermanent())));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public GroundAssault(final GroundAssault card) {
        super(card);
    }

    @Override
    public GroundAssault copy() {
        return new GroundAssault(this);
    }
}
