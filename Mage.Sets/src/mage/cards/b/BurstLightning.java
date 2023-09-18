
package mage.cards.b;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BurstLightning extends CardImpl {

    public BurstLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Kicker {4} (You may pay an additional {4} as you cast this spell.)
        this.addAbility(new KickerAbility("{4}"));

        // Burst Lightning deals 2 damage to any target. If Burst Lightning was kicked, it deals 4 damage to that creature or player instead.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DamageTargetEffect(4),
                new DamageTargetEffect(2), KickedCondition.ONCE, "{this} deals 2 damage to any target. If this spell was kicked, it deals 4 damage instead"));
    }

    private BurstLightning(final BurstLightning card) {
        super(card);
    }

    @Override
    public BurstLightning copy() {
        return new BurstLightning(this);
    }

}
