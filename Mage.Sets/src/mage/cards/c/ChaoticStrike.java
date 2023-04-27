
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AfterBlockersAreDeclaredCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ChaoticStrike extends CardImpl {

    public ChaoticStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Cast Chaotic Strike only during combat after blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, AfterBlockersAreDeclaredCondition.instance));

        // Flip a coin. If you win the flip, target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new ChaoticStrikeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ChaoticStrike(final ChaoticStrike card) {
        super(card);
    }

    @Override
    public ChaoticStrike copy() {
        return new ChaoticStrike(this);
    }
}

class ChaoticStrikeEffect extends OneShotEffect {

    public ChaoticStrikeEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, target creature gets +1/+1 until end of turn";
    }

    public ChaoticStrikeEffect(ChaoticStrikeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            if (controller.flipCoin(source, game, true)) {
                game.addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public ChaoticStrikeEffect copy() {
        return new ChaoticStrikeEffect(this);
    }
}
