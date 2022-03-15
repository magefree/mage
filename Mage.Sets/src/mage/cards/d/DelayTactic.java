package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInPlayersNextUntapStepAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author NinthWorld
 */
public final class DelayTactic extends CardImpl {

    public DelayTactic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        

        // Choose one -
        //   Creatures you control gain hexproof until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent())
            .setText("Creatures you control gain hexproof until end of turn"));

        //   Creatures target opponent controls don't untap during their next untap step.
        Mode mode = new Mode(new DelayTacticEffect());
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    private DelayTactic(final DelayTactic card) {
        super(card);
    }

    @Override
    public DelayTactic copy() {
        return new DelayTactic(this);
    }
}

// Based on ManaVaporsEffect
class DelayTacticEffect extends OneShotEffect {

    DelayTacticEffect() {
        super(Outcome.Benefit);
        this.staticText = "Creatures target opponent controls don't untap during their next untap step";
    }

    DelayTacticEffect(final DelayTacticEffect effect) {
        super(effect);
    }

    @Override
    public DelayTacticEffect copy() {
        return new DelayTacticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(targetPlayer.getId()));
            ContinuousEffect effect = new DontUntapInPlayersNextUntapStepAllEffect(filter);
            effect.setTargetPointer(new FixedTarget(targetPlayer.getId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}