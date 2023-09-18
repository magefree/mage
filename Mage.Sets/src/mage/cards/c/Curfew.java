
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author maxlebedev
 */
public final class Curfew extends CardImpl {

    public Curfew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Each player returns a creature they control to its owner's hand.
        this.getSpellAbility().addEffect(new CurfewEffect());
    }

    private Curfew(final Curfew card) {
        super(card);
    }

    @Override
    public Curfew copy() {
        return new Curfew(this);
    }
}

class CurfewEffect extends OneShotEffect {

    public CurfewEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Each player returns a creature they control to its owner's hand";
    }

    private CurfewEffect(final CurfewEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.informPlayers("Each player returns a creature they control to its owner's hand");
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game) > 0) {
                TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_CREATURE, true);
                player.choose(Outcome.ReturnToHand, target, source, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    player.moveCards(permanent, Zone.HAND, source, game);
                }
            }
        }
        return true;
    }

    @Override
    public CurfewEffect copy() {
        return new CurfewEffect(this);
    }
}
