
package mage.cards.b;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.TargetHasCardTypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author spjspj
 */
public final class BuiltToSmash extends CardImpl {

    public BuiltToSmash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Target attacking creature gets +3/+3 until end of turn. If it's an artifact creature, it gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), new LockedInCondition(new TargetHasCardTypeCondition(CardType.ARTIFACT)),
                "If it's an artifact creature, it gains trample until end of turn"));
    }

    private BuiltToSmash(final BuiltToSmash card) {
        super(card);
    }

    @Override
    public BuiltToSmash copy() {
        return new BuiltToSmash(this);
    }
}
