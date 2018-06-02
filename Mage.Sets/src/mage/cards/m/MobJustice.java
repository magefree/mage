
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LoneFox
 */
public final class MobJustice extends CardImpl {

    public MobJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Mob Justice deals damage to target player equal to the number of creatures you control.
        Effect effect = new DamageTargetEffect(new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent()));
        effect.setText("{this} deals damage to target player or planeswalker equal to the number of creatures you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    public MobJustice(final MobJustice card) {
        super(card);
    }

    @Override
    public MobJustice copy() {
        return new MobJustice(this);
    }
}
