
package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class ThoughtsOfRuin extends CardImpl {

    public ThoughtsOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");


        // Each player sacrifices a land for each card in your hand.
        this.getSpellAbility().addEffect(new ThoughtsOfRuinEffect());

    }

    private ThoughtsOfRuin(final ThoughtsOfRuin card) {
        super(card);
    }

    @Override
    public ThoughtsOfRuin copy() {
        return new ThoughtsOfRuin(this);
    }
}

class ThoughtsOfRuinEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    public ThoughtsOfRuinEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player sacrifices a land for each card in your hand";
    }

    public ThoughtsOfRuinEffect(final ThoughtsOfRuinEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtsOfRuinEffect copy() {
        return new ThoughtsOfRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.getHand().size();
            if (amount > 0) {
                List<Permanent> permanentsToSacrifice = new ArrayList<>();
                // select all lands to sacrifice
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        int lands = game.getState().getBattlefield().countAll(filter, playerId, game);
                        if (amount >= lands) {
                            permanentsToSacrifice.addAll(game.getState().getBattlefield().getAllActivePermanents(filter, playerId, game));
                        } else {
                            FilterLandPermanent playerFilter = filter.copy();
                            playerFilter.add(new ControllerIdPredicate(playerId));
                            Target target = new TargetLandPermanent(amount, amount, playerFilter, true);
                            player.choose(outcome, target, source, game);
                            for (UUID landId : target.getTargets()) {
                                Permanent permanent = game.getPermanent(landId);
                                if (permanent != null) {
                                    permanentsToSacrifice.add(permanent);
                                }
                            }
                        }

                    }
                }
                // sacrifice all lands
                for (Permanent permanent :permanentsToSacrifice) {
                    permanent.sacrifice(source, game);
                }
            }
        }
        return false;
    }
}
