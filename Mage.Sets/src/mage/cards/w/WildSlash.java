
package mage.cards.w;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class WildSlash extends CardImpl {

    public WildSlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // <i>Ferocious</i> If you control a creature with power 4 or greater, damage can't be prevented this turn.
        ContinuousRuleModifyingEffect effect = new DamageCantBePreventedEffect(Duration.EndOfTurn, "damage can't be prevented this turn", false, false);
        effect.setText("<i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, damage can't be prevented this turn.<br>");
        this.getSpellAbility().addEffect(new ConditionalContinuousRuleModifyingEffect(effect,
                new LockedInCondition(FerociousCondition.instance)));

        // Wild Slash deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

    }

    public WildSlash(final WildSlash card) {
        super(card);
    }

    @Override
    public WildSlash copy() {
        return new WildSlash(this);
    }
}
