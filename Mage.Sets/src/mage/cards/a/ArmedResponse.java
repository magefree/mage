
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author Plopman
 */
public final class ArmedResponse extends CardImpl {
    
        private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("Equipment you control");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }


    public ArmedResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Armed Response deals damage to target attacking creature equal to the number of Equipment you control.
        Effect effect = new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("{this} deals damage to target attacking creature equal to the number of Equipment you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private ArmedResponse(final ArmedResponse card) {
        super(card);
    }

    @Override
    public ArmedResponse copy() {
        return new ArmedResponse(this);
    }
}
