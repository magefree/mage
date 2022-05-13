package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class FrayingOmnipotence extends CardImpl {

    public FrayingOmnipotence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Each player loses half their life, then discards half the cards in their hand, then sacrifices half the creatures they control. Round up each time.
        this.getSpellAbility().addEffect(new FrayingOmnipotenceEffect());
    }

    private FrayingOmnipotence(final FrayingOmnipotence card) {
        super(card);
    }

    @Override
    public FrayingOmnipotence copy() {
        return new FrayingOmnipotence(this);
    }
}

class FrayingOmnipotenceEffect extends OneShotEffect {

    FrayingOmnipotenceEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player loses half their life, "
                + "then discards half the cards in their hand, "
                + "then sacrifices half the creatures they control. "
                + "Round up each time.";
    }

    FrayingOmnipotenceEffect(final FrayingOmnipotenceEffect effect) {
        super(effect);
    }

    @Override
    public FrayingOmnipotenceEffect copy() {
        return new FrayingOmnipotenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Each player loses half of their life,
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int lifeToLose = (int) Math.ceil(player.getLife() / 2.0);
            player.loseLife(lifeToLose, game, source, false);
        }
        // then discards half of the cards in their hand,
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int cardsToDiscard = (int) Math.ceil(player.getHand().size() / 2.0);
            if (cardsToDiscard > 0) {
                player.discard(cardsToDiscard, false, false, source, game);
            }
        }
        // then sacrifices half of the creatures they control,
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
            int creaturesToSacrifice = (int) Math.ceil(game.getBattlefield().count(filter, player.getId(), source, game) / 2.0);
            if (creaturesToSacrifice == 0) {
                continue;
            }
            Target target = new TargetControlledCreaturePermanent(creaturesToSacrifice, creaturesToSacrifice, filter, true);
            target.chooseTarget(Outcome.Sacrifice, playerId, source, game);
            for (UUID permanentId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                }
            }
        }
        return true;
    }
}
