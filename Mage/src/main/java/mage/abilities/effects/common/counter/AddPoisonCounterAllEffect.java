package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author TheElk801
 */
public class AddPoisonCounterAllEffect extends OneShotEffect {

    private final int amount;
    private final TargetController targetController;

    public AddPoisonCounterAllEffect(TargetController targetController) {
        this(1, targetController);
    }

    public AddPoisonCounterAllEffect(int amount, TargetController targetController) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.targetController = targetController;
        staticText = makeText();
    }

    private AddPoisonCounterAllEffect(final AddPoisonCounterAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.targetController = effect.targetController;
    }

    @Override
    public AddPoisonCounterAllEffect copy() {
        return new AddPoisonCounterAllEffect(this);
    }

    private Collection<UUID> getPlayers(Game game, Ability source) {
        switch (targetController) {
            case OPPONENT:
                return game.getOpponents(source.getControllerId());
            case EACH_PLAYER:
            case ANY:
                return game.getState().getPlayersInRange(source.getControllerId(), game);
            case YOU:
                return Arrays.asList(source.getControllerId());
            case CONTROLLER_ATTACHED_TO:
                List<UUID> list = new ArrayList<>();
                Optional.ofNullable(source.getSourcePermanentOrLKI(game))
                        .filter(Objects::nonNull)
                        .map(Permanent::getAttachedTo)
                        .map(game::getControllerId)
                        .filter(Objects::nonNull)
                        .ifPresent(list::add);
                return list;
            default:
                throw new UnsupportedOperationException(targetController + " not supported");
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : getPlayers(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.addCounters(CounterType.POISON.createInstance(amount), source.getControllerId(), source, game);
            }
        }
        return true;
    }

    public String makeText() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case OPPONENT:
                sb.append("each opponent gets");
                break;
            case ANY:
            case EACH_PLAYER:
                sb.append("each player gets");
                break;
            case YOU:
                sb.append("you get");
                break;
            case CONTROLLER_ATTACHED_TO:
                sb.append("its controller gets");
                break;
            default:
                throw new UnsupportedOperationException(targetController + " not supported");
        }
        sb.append(' ' + CardUtil.numberToText(amount, "a") + " poison counter");
        if (amount > 1) {
            sb.append('s');
        }
        return sb.toString();
    }
}
