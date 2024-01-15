
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class EntrapmentManeuver extends CardImpl {

    public EntrapmentManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Target player sacrifices an attacking creature. You create X 1/1 white Soldier creature tokens, where X is that creature's toughness.
        this.getSpellAbility().addEffect(new EntrapmentManeuverSacrificeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private EntrapmentManeuver(final EntrapmentManeuver card) {
        super(card);
    }

    @Override
    public EntrapmentManeuver copy() {
        return new EntrapmentManeuver(this);
    }
}

class EntrapmentManeuverSacrificeEffect extends OneShotEffect {

    EntrapmentManeuverSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target player sacrifices an attacking creature. You create X 1/1 white Soldier creature tokens, where X is that creature's toughness";
    }

    private EntrapmentManeuverSacrificeEffect(final EntrapmentManeuverSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public EntrapmentManeuverSacrificeEffect copy() {
        return new EntrapmentManeuverSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        if (game.getBattlefield().count(TargetSacrifice.makeFilter(StaticFilters.FILTER_ATTACKING_CREATURE), player.getId(), source, game) > 0) {
            Target target = new TargetSacrifice(StaticFilters.FILTER_ATTACKING_CREATURE);
            while (player.canRespond() && !target.isChosen() && target.canChoose(player.getId(), source, game)) {
                player.choose(Outcome.Sacrifice, target, source, game);
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                int amount = permanent.getToughness().getValue();
                permanent.sacrifice(source, game);
                new CreateTokenEffect(new SoldierToken(), amount).apply(game, source);
            } else {
                return false;
            }
        }
        return true;
    }
}
