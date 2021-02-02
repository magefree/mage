
package mage.cards.t;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.LandfallCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.LandfallWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class TombHex extends CardImpl {

    public TombHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Target creature gets -2/-2 until end of turn.
        // Landfall - If you had a land enter the battlefield under your control this turn, that creature gets -4/-4 until end of turn instead.
        this.getSpellAbility().addWatcher(new LandfallWatcher());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new BoostTargetEffect(-4, -4, Duration.EndOfTurn), new BoostTargetEffect(-2, -2, Duration.EndOfTurn), 
                new LockedInCondition(LandfallCondition.instance),
                "Target creature gets -2/-2 until end of turn. <br><i>Landfall</i> &mdash; If you had a land enter the battlefield under your control this turn, that creature gets -4/-4 until end of turn instead"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TombHex(final TombHex card) {
        super(card);
    }

    @Override
    public TombHex copy() {
        return new TombHex(this);
    }
}
