package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;

/**
 * @author LevelX2
 */
public final class CelestialConvergence extends CardImpl {

    public CelestialConvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Celestial Convergence enters the battlefield with seven omen counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.OMEN.createInstance(7));
        this.addAbility(new EntersBattlefieldAbility(effect, "with seven omen counters"));

        // At the beginning of your upkeep, remove an omen counter from Celestial Convergence. If there are no omen counters on Celestial Convergence, the player with the highest life total wins the game. If two or more players are tied for highest life total, the game is a draw.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.OMEN.createInstance(1)), TargetController.YOU, false);
        ability.addEffect(new CelestialConvergenceEffect());
        this.addAbility(ability);
    }

    private CelestialConvergence(final CelestialConvergence card) {
        super(card);
    }

    @Override
    public CelestialConvergence copy() {
        return new CelestialConvergence(this);
    }
}

class CelestialConvergenceEffect extends OneShotEffect {

    public CelestialConvergenceEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "If there are no omen counters on {this}, the player with the highest life total wins the game. If two or more players are tied for highest life total, the game is a draw";
    }

    public CelestialConvergenceEffect(final CelestialConvergenceEffect effect) {
        super(effect);
    }

    @Override
    public CelestialConvergenceEffect copy() {
        return new CelestialConvergenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourcePermanent == null || controller == null
                || sourcePermanent.getCounters(game).getCount(CounterType.OMEN) > 0) {
            return false;
        }

        /**
         * 801.14. If an effect states that a player wins the game, all of
         * that player's opponents within their range of influence lose the
         * game instead. #
         *
         * 801.15. If the effect of a spell or ability states that the game
         * is a draw, the game is a draw for that spell or ability's
         * controller and all players within their range of influence. They
         * leave the game. All remaining players continue to play the game.
         *
         */
        Map<Integer, Set<UUID>> playerMap = new HashMap<>();
        game.getState()
                .getPlayersInRange(controller.getId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> playerMap.computeIfAbsent(
                        player.getLife(), x -> new HashSet<>()
                ).add(player.getId()));
        int highLife = playerMap.keySet().stream().mapToInt(x -> x).max().orElse(Integer.MIN_VALUE);
        if (playerMap.get(highLife).size() > 1) {
            game.setDraw(controller.getId());
            return true;
        }
        Player winner = game.getPlayer(playerMap.get(highLife).iterator().next());
        if (winner != null) {
            winner.won(game);
        }
        return true;
    }
}
