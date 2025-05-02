package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SadisticShellGame extends CardImpl {

    public SadisticShellGame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Starting with the next opponent in turn order, each player chooses a creature you don't control. Destroy the chosen creatures.
        this.getSpellAbility().addEffect(new SadisticShellGameEffect());
    }

    private SadisticShellGame(final SadisticShellGame card) {
        super(card);
    }

    @Override
    public SadisticShellGame copy() {
        return new SadisticShellGame(this);
    }
}

class SadisticShellGameEffect extends OneShotEffect {

    SadisticShellGameEffect() {
        super(Outcome.Benefit);
        staticText = "starting with the next opponent in turn order, " +
                "each player chooses a creature you don't control. Destroy the chosen creatures";
    }

    private SadisticShellGameEffect(final SadisticShellGameEffect effect) {
        super(effect);
    }

    @Override
    public SadisticShellGameEffect copy() {
        return new SadisticShellGameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterCreaturePermanent(
                "creature not controlled by " +
                        Optional.ofNullable(source)
                                .map(Controllable::getControllerId)
                                .map(game::getPlayer)
                                .map(Player::getName)
                                .orElse("this spell's controller")
        );
        filter.add(Predicates.not(new ControllerIdPredicate(source.getControllerId())));
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        Set<Permanent> permanents = new HashSet<>();
        List<UUID> playerIds = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .distinct()
                .collect(Collectors.toList());
        playerIds.remove(source.getControllerId());
        playerIds.add(source.getControllerId());
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        for (UUID playerId : playerIds) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            target.clearChosen();
            player.choose(Outcome.DestroyPermanent, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                continue;
            }
            game.informPlayers(player.getLogName() + " chooses to destroy " + permanent.getLogName());
            permanents.add(permanent);
        }
        for (Permanent permanent : permanents) {
            permanent.destroy(source, game);
        }
        return true;
    }
}
