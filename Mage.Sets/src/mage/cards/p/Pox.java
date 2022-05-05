package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public final class Pox extends CardImpl {

    public Pox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}{B}");

        // Each player loses a third of their life, then discards a third of the cards in their hand, then sacrifices a third of the creatures they control, then sacrifices a third of the lands they control. Round up each time.
        this.getSpellAbility().addEffect(new PoxEffect());
    }

    private Pox(final Pox card) {
        super(card);
    }

    @Override
    public Pox copy() {
        return new Pox(this);
    }
}

class PoxEffect extends OneShotEffect {

    PoxEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player loses a third of their life, "
                + "then discards a third of the cards in their hand, "
                + "then sacrifices a third of the creatures they control, "
                + "then sacrifices a third of the lands they control. "
                + "Round up each time.";
    }

    PoxEffect(final PoxEffect effect) {
        super(effect);
    }

    @Override
    public PoxEffect copy() {
        return new PoxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Each player loses a third of their life,
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int lifeToLose = (int) Math.ceil(player.getLife() / 3.0);
                    player.loseLife(lifeToLose, game, source, false);
                }
            }
            // then discards a third of the cards in their hand,
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsToDiscard = (int) Math.ceil(player.getHand().size() / 3.0);
                    if (cardsToDiscard > 0) {
                        player.discard(cardsToDiscard, false, false, source, game);
                    }
                }
            }
            // then sacrifices a third of the creatures they control,
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
                    int creaturesToSacrifice = (int) Math.ceil(game.getBattlefield().count(filter, player.getId(), source, game) / 3.0);
                    if (creaturesToSacrifice > 0) {
                        Target target = new TargetControlledCreaturePermanent(creaturesToSacrifice, creaturesToSacrifice, filter, true);
                        target.chooseTarget(Outcome.Sacrifice, playerId, source, game);
                        for (UUID permanentId : target.getTargets()) {
                            Permanent permanent = game.getPermanent(permanentId);
                            if (permanent != null) {
                                permanent.sacrifice(source, game);
                            }
                        }
                    }
                }
            }
            // then sacrifices a third of the lands they control.
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterControlledLandPermanent filter = new FilterControlledLandPermanent();
                    int landsToSacrifice = (int) Math.ceil(game.getBattlefield().count(filter, player.getId(), source, game) / 3.0);
                    if (landsToSacrifice > 0) {
                        Target target = new TargetControlledPermanent(landsToSacrifice, landsToSacrifice, filter, true);
                        target.chooseTarget(Outcome.Sacrifice, playerId, source, game);
                        for (UUID permanentId : target.getTargets()) {
                            Permanent permanent = game.getPermanent(permanentId);
                            if (permanent != null) {
                                permanent.sacrifice(source, game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
