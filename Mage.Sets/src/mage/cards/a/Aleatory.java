
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.condition.common.AfterBlockersAreDeclaredCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
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
public final class Aleatory extends CardImpl {

    public Aleatory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Cast Aleatory only during combat after blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, AfterBlockersAreDeclaredCondition.instance));

        // Flip a coin. If you win the flip, target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new AleatoryEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
    }

    private Aleatory(final Aleatory card) {
        super(card);
    }

    @Override
    public Aleatory copy() {
        return new Aleatory(this);
    }
}

class AleatoryEffect extends OneShotEffect {

    public AleatoryEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, target creature gets +1/+1 until end of turn";
    }

    private AleatoryEffect(final AleatoryEffect effect) {
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
    public AleatoryEffect copy() {
        return new AleatoryEffect(this);
    }
}
