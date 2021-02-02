
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class SarkhansRage extends CardImpl {

    public SarkhansRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");

        // Sarkhan's Rage deals 5 damage to any target. If you control no Dragons, Sarkhan's Rage deals 2 damage to you.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DamageControllerEffect(2),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledCreaturePermanent(SubType.DRAGON,"you control no Dragons"), ComparisonType.EQUAL_TO, 0)
        ));

    }

    private SarkhansRage(final SarkhansRage card) {
        super(card);
    }

    @Override
    public SarkhansRage copy() {
        return new SarkhansRage(this);
    }
}
