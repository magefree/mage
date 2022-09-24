
package mage.cards.e;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class ExplosiveGrowth extends CardImpl {

    public ExplosiveGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Kicker {5}
        this.addAbility(new KickerAbility("{5}"));

        // Target creature gets +2/+2 until end of turn. If Explosive Growth was kicked, that creature gets +5/+5 until end of turn instead.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new BoostTargetEffect(5, 5, Duration.EndOfTurn),
                new BoostTargetEffect(2, 2, Duration.EndOfTurn), new LockedInCondition(KickedCondition.ONCE),
                "Target creature gets +2/+2 until end of turn. If this spell was kicked, that creature gets +5/+5 until end of turn instead."));
    }

    private ExplosiveGrowth(final ExplosiveGrowth card) {
        super(card);
    }

    @Override
    public ExplosiveGrowth copy() {
        return new ExplosiveGrowth(this);
    }
}
