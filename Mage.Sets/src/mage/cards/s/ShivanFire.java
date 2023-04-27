

package mage.cards.s;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author rscoates
 */
public final class ShivanFire extends CardImpl {

    public ShivanFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Kicker {4} (You may pay an additional {4} as you cast this spell.)
        this.addAbility(new KickerAbility("{4}"));

        // Shivan Fire deals 2 damage to any target. If Shivan Fire was kicked, it deals 4 damage to that creature or player instead.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DamageTargetEffect(4),
                new DamageTargetEffect(2), KickedCondition.ONCE, "{this} deals 2 damage to target creature. If this spell was kicked, it deals 4 damage to that creature instead"));
    }

    private ShivanFire(final ShivanFire card) {
        super(card);
    }

    @Override
    public ShivanFire copy() {
        return new ShivanFire(this);
    }

}
