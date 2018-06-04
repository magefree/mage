
package mage.cards.n;

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
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class NoeticScales extends CardImpl {

    public NoeticScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of each player's upkeep, return to its owner's hand each creature that player controls with power greater than the number of cards in their hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new NoeticScalesEffect(), TargetController.ANY, false, true));
    }

    public NoeticScales(final NoeticScales card) {
        super(card);
    }

    @Override
    public NoeticScales copy() {
        return new NoeticScales(this);
    }
}

class NoeticScalesEffect extends OneShotEffect {

    public NoeticScalesEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to its owner's hand each creature that player controls with power greater than the number of cards in their hand";
    }

    public NoeticScalesEffect(final NoeticScalesEffect effect) {
        super(effect);
    }

    @Override
    public NoeticScalesEffect copy() {
        return new NoeticScalesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int numberOfCardsInHand = player.getHand().size();
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                if (creature.getPower().getValue() > numberOfCardsInHand) {
                    if (creature.moveToZone(Zone.HAND, source.getId(), game, false)) {
                        game.informPlayers(player.getLogName() + " moves " + creature.getLogName() + " from the battlefield to their hand.");
                    }
                }
            }
            return true;
        }
        return false;
    }
}
