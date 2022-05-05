
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

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

    public EntrapmentManeuverSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target player sacrifices an attacking creature. You create X 1/1 white Soldier creature tokens, where X is that creature's toughness";
    }

    public EntrapmentManeuverSacrificeEffect(final EntrapmentManeuverSacrificeEffect effect) {
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
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        filter.add(AttackingPredicate.instance);
        int realCount = game.getBattlefield().countAll(filter, player.getId(), game);
        if (realCount > 0) {
            Target target = new TargetControlledPermanent(1, 1, filter, true);
            while (player.canRespond() && !target.isChosen() && target.canChoose(player.getId(), source, game)) {
                player.chooseTarget(Outcome.Sacrifice, target, source, game);
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                int amount = permanent.getToughness().getValue();
                permanent.sacrifice(source, game);
                new CreateTokenEffect(new SoldierToken(), amount).apply(game, source);
            } else{
                return false;
            }
        }
        return true;
    }
}
