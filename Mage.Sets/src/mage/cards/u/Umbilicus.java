
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
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

/**
 *
 * @author Fenhl
 */
public final class Umbilicus extends CardImpl {

    public Umbilicus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // At the beginning of each player's upkeep, that player returns a permanent he or she controls to its owner's hand unless he or she pays 2 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new BloodClockEffect(), TargetController.ANY, false, true);
        this.addAbility(ability);
    }

    public Umbilicus(final Umbilicus card) {
        super(card);
    }

    @Override
    public Umbilicus copy() {
        return new Umbilicus(this);
    }
}

class BloodClockEffect extends OneShotEffect {

    public BloodClockEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "that player returns a permanent he or she controls to its owner's hand unless he or she pays 2 life";
    }

    public BloodClockEffect(final BloodClockEffect effect) {
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
        if (player.getLife() > 2 && player.chooseUse(Outcome.Neutral, "Pay 2 life? If you don't, return a permanent you control to its owner's hand.", source, game)) {
            player.loseLife(2, game, false);
            game.informPlayers(player.getLogName() + " pays 2 life. He will not return a permanent he or she controls.");
            return true;
        } else {
            Target target = new TargetControlledPermanent();
            if (target.canChoose(source.getSourceId(), player.getId(), game) && player.chooseTarget(outcome, target, source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    game.informPlayers(player.getLogName() + " returns " + permanent.getName() + " to hand.");
                    return permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }
}