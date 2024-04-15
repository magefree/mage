package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author TheElk801
 */
public class AddCountersPlayersEffect extends OneShotEffect {

    private final Counter counter;
    private final DynamicValue amount;
    private final TargetController targetController;

    public AddCountersPlayersEffect(Counter counter, TargetController targetController) {
        this(counter, StaticValue.get(0), targetController);
    }

    public AddCountersPlayersEffect(Counter counter, DynamicValue amount, TargetController targetController) {
        super(Outcome.Benefit);
        this.counter = counter;
        this.amount = amount;
        this.targetController = targetController;
        staticText = makeText();
    }

    private AddCountersPlayersEffect(final AddCountersPlayersEffect effect) {
        super(effect);
        this.counter = effect.counter;
        this.amount = effect.amount;
        this.targetController = effect.targetController;
    }

    @Override
    public AddCountersPlayersEffect copy() {
        return new AddCountersPlayersEffect(this);
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
        Counter newCounter = counter.copy();
        int calculated = amount.calculate(game, source, this);
        if (!(amount instanceof StaticValue) || calculated > 0) {
            // If dynamic, or static and set to a > 0 value, we use that instead of the counter's internal amount.
            newCounter.remove(newCounter.getCount());
            newCounter.add(calculated);
        } else {
            // StaticValue 0 -- the default counter has the amount, so no adjustment.
        }

        if (newCounter.getCount() <= 0) {
            return false; // no need to iterate on targets, no counters will be put on them
        }
        for (UUID playerId : getPlayers(game, source)) {
            Counter newCounterForPlayer = newCounter.copy();
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.addCounters(newCounterForPlayer, source.getControllerId(), source, game);
                game.informPlayers(player.getLogName() + " gets "
                        + newCounterForPlayer.getCount() + ' ' + newCounterForPlayer.getName() + " counters"
                        + CardUtil.getSourceLogName(game, source));
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
        sb.append(' ');
        sb.append(counter.getDescription());
        return sb.toString();
    }
}
