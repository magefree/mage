package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BloodClock extends CardImpl {

    public BloodClock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of each player's upkeep, that player returns a permanent they control to its owner's hand unless they pay 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new BloodClockEffect(), TargetController.ANY, false, true
        ));
    }

    private BloodClock(final BloodClock card) {
        super(card);
    }

    @Override
    public BloodClock copy() {
        return new BloodClock(this);
    }
}

class BloodClockEffect extends OneShotEffect {

    BloodClockEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "that player returns a permanent they control to its owner's hand unless they pay 2 life";
    }

    private BloodClockEffect(final BloodClockEffect effect) {
        super(effect);
    }

    @Override
    public BloodClockEffect copy() {
        return new BloodClockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        PayLifeCost cost = new PayLifeCost(2);
        if (cost.canPay(source, source, player.getId(), game) && player.chooseUse(
                Outcome.Neutral, "Pay 2 life? If you don't, " +
                        "return a permanent you control to its owner's hand.", source, game
        ) && cost.pay(source, game, source, player.getId(), true)) {
            return true;
        }
        Target target = new TargetControlledPermanent();
        target.setNotTarget(true);
        if (!target.canChoose(player.getId(), source, game)
                || !player.chooseTarget(outcome, target, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return player.moveCards(permanent, Zone.HAND, source, game);
    }
}
