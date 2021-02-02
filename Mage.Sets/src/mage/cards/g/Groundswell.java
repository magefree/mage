
package mage.cards.g;

import java.util.UUID;
import mage.abilities.condition.common.LandfallCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.LandfallWatcher;

/**
 *
 * @author Viserion
 */
public final class Groundswell extends CardImpl {

    public Groundswell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target creature gets +2/+2 until end of turn.
        // Landfall - If you had a land enter the battlefield under your control this turn, that creature gets +4/+4 until end of turn instead.
        this.getSpellAbility().addWatcher(new LandfallWatcher());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(4, 4, Duration.EndOfTurn)),
                new AddContinuousEffectToGame(new BoostTargetEffect(2, 2, Duration.EndOfTurn)),
                LandfallCondition.instance,
                "Target creature gets +2/+2 until end of turn. <br><i>Landfall</i> &mdash; If you had a land enter the battlefield under your control this turn, that creature gets +4/+4 until end of turn instead"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Groundswell(final Groundswell card) {
        super(card);
    }

    @Override
    public Groundswell copy() {
        return new Groundswell(this);
    }
}
