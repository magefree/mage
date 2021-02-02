
package mage.cards.m;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MightOfOldKrosa extends CardImpl {

    public MightOfOldKrosa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target creature gets +2/+2 until end of turn. If you cast this spell during your main phase, that creature gets +4/+4 until end of turn instead.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostTargetEffect(4,4, Duration.EndOfTurn),
                new BoostTargetEffect(2, 2, Duration.EndOfTurn),
                new LockedInCondition(AddendumCondition.instance),
                "Target creature gets +2/+2 until end of turn. If you cast this spell during your main phase, that creature gets +4/+4 until end of turn instead"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MightOfOldKrosa(final MightOfOldKrosa card) {
        super(card);
    }

    @Override
    public MightOfOldKrosa copy() {
        return new MightOfOldKrosa(this);
    }
}
