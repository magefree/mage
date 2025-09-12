package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.MultiAmountType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;

public class RemoveUpToAmountCountersEffect extends OneShotEffect {

    final DynamicValue amount;

    public RemoveUpToAmountCountersEffect(int amount) {
        super(Outcome.Neutral);
        this.amount = StaticValue.get(amount);
    }

    public RemoveUpToAmountCountersEffect(DynamicValue amount) {
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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int max = this.amount.calculate(game, source, this);
        // from permanent
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            List<String> toChoose = new ArrayList<>(permanent.getCounters(game).keySet());
            List<Integer> counterList = controller.getMultiAmount(Outcome.UnboostCreature, toChoose, 0, 0, max, MultiAmountType.REMOVE_COUNTERS, game);
            for (int i = 0; i < toChoose.size(); i++) {
                int amountToRemove = counterList.get(i);
                if (amountToRemove > 0) {
                    permanent.removeCounters(toChoose.get(i), amountToRemove, source, game);
                }
            }
            return true;
        }

        // from player
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            List<String> toChoose = new ArrayList<>(player.getCountersAsCopy().keySet());
            List<Integer> counterList = controller.getMultiAmount(Outcome.Neutral, toChoose, 0, 0, max, MultiAmountType.REMOVE_COUNTERS, game);
            for (int i = 0; i < toChoose.size(); i++) {
                int amountToRemove = counterList.get(i);
                if (amountToRemove > 0) {
                    player.loseCounters(toChoose.get(i), amountToRemove, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "remove up to " + CardUtil.numberToText(this.amount.toString()) + " counters from " + getTargetPointer().describeTargets(mode.getTargets(), "that permanent");
    }
}
