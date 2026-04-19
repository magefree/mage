package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

/**
 *
 * @author muz
 */
public final class PoxPlague extends CardImpl {

    public PoxPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}{B}{B}{B}");

        // Each player loses half their life, then discards half the cards in their hand, then sacrifices half the permanents they control of their choice. Round down each time.
        this.getSpellAbility().addEffect(new PoxPlagueEffect());
    }

    private PoxPlague(final PoxPlague card) {
        super(card);
    }

    @Override
    public PoxPlague copy() {
        return new PoxPlague(this);
    }
}

class PoxPlagueEffect extends OneShotEffect {

    PoxPlagueEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player loses half their life, "
                + "then discards half the cards in their hand, "
                + "then sacrifices half the permanents they control. "
                + "Round down each time.";
    }

    private PoxPlagueEffect(final PoxPlagueEffect effect) {
        super(effect);
    }

    @Override
    public PoxPlagueEffect copy() {
        return new PoxPlagueEffect(this);
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
            int lifeToLose = (int) Math.floor(player.getLife() / 2.0);
            player.loseLife(lifeToLose, game, source, false);
        }
        // then discards half of the cards in their hand,
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int cardsToDiscard = (int) Math.floor(player.getHand().size() / 2.0);
            if (cardsToDiscard > 0) {
                player.discard(cardsToDiscard, false, false, source, game);
            }
        }
        // then sacrifices half of the permanents they control,
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int permanentsToSacrifice = (int) Math.floor(game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_PERMANENT, player.getId(), source, game) / 2.0);
            if (permanentsToSacrifice == 0) {
                continue;
            }
            TargetSacrifice target = new TargetSacrifice(permanentsToSacrifice, StaticFilters.FILTER_CONTROLLED_PERMANENT);
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
