package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.MultiAmountType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RemoveUpToAmountCountersEffect extends OneShotEffect {

    private final int amount;

    public RemoveUpToAmountCountersEffect(int amount) {
        super(Outcome.Neutral);
        this.amount = amount;
    }

    private RemoveUpToAmountCountersEffect(final RemoveUpToAmountCountersEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public RemoveUpToAmountCountersEffect copy() {
        return new RemoveUpToAmountCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return doRemoval(
                amount,
                getTargetPointer().getFirst(game, source),
                game.getPlayer(source.getControllerId()),
                game, source
        ) > 0;
    }

    private static List<String> getCounters(Permanent permanent, Player player, Game game) {
        if (permanent != null) {
            return new ArrayList<>(permanent.getCounters(game).keySet());
        }
        if (player != null) {
            return new ArrayList<>(player.getCountersAsCopy().keySet());
        }
        return new ArrayList<>();
    }

    private static int removeCounters(Permanent permanent, Player player, String counterName, int amountToRemove, Game game, Ability source) {
        if (permanent != null) {
            return permanent.removeCounters(counterName, amountToRemove, source, game, false);
        }
        if (player != null) {
            // TODO: currently doesn't return how many counters are removed for a player
            player.loseCounters(counterName, amountToRemove, source, game);
        }
        return 0;
    }

    public static int doRemoval(int amount, UUID targetId, Player controller, Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetId);
        Player player = game.getPlayer(targetId);
        if (controller == null || (permanent == null && player == null) || amount < 1) {
            return 0;
        }
        List<String> toChoose = getCounters(permanent, player, game);
        if (toChoose.isEmpty()) {
            return 0;
        }
        List<Integer> counterList = controller.getMultiAmount(
                Outcome.UnboostCreature, toChoose, 0, 0,
                amount, MultiAmountType.REMOVE_COUNTERS, game
        );
        int total = 0;
        for (int i = 0; i < toChoose.size(); i++) {
            int amountToRemove = counterList.get(i);
            if (amountToRemove > 0) {
                total += removeCounters(permanent, player, toChoose.get(i), amountToRemove, game, source);
            }
        }
        return total;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("remove ");
        if (amount < Integer.MAX_VALUE) {
            sb.append("up to ");
        }
        sb.append(CardUtil.numberToText(amount));
        sb.append(" counters from ");
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "that permanent"));
        return sb.toString();
    }
}
