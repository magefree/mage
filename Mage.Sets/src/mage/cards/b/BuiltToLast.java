
package mage.cards.b;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.TargetHasCardTypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BuiltToLast extends CardImpl {

    public BuiltToLast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target creature gets +2/+2 until end of turn.  If its an artifact creature, it gains indestructible until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new LockedInCondition(new TargetHasCardTypeCondition(CardType.ARTIFACT)),
                "If it's an artifact creature, it gains indestructible until end of turn"));

    }

    private BuiltToLast(final BuiltToLast card) {
        super(card);
    }

    @Override
    public BuiltToLast copy() {
        return new BuiltToLast(this);
    }
}
