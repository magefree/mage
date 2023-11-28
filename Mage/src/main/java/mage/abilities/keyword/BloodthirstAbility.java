package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.BloodthirstWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Loki
 */
public class BloodthirstAbility extends EntersBattlefieldAbility {

    private final int amount;

    public BloodthirstAbility(int amount) {
        super(new BloodthirstEffect(amount), "");
        this.amount = amount;
    }

    protected BloodthirstAbility(final BloodthirstAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public BloodthirstAbility copy() {
        return new BloodthirstAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Bloodthirst ").append(amount)
                .append(" <i>(If an opponent was dealt damage this turn, this creature enters the battlefield with ");
        if (amount == 1) {
            sb.append("a +1/+1 counter");
        } else {
            sb.append(CardUtil.numberToText(amount)).append(" +1/+1 counters");
        }
        sb.append(" on it.)</i>");
        return sb.toString();
    }
}

class BloodthirstEffect extends OneShotEffect {

    private final int amount;

    BloodthirstEffect(int amount) {
        super(Outcome.BoostCreature);
        this.amount = amount;
        staticText = "this permanent comes into play with " + this.amount + " +1/+1 counters on it";
    }

    BloodthirstEffect(final BloodthirstEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            BloodthirstWatcher watcher = game.getState().getWatcher(BloodthirstWatcher.class, source.getControllerId());
            if (watcher != null && watcher.conditionMet()) {
                Permanent permanent = game.getPermanentEntering(source.getSourceId());
                if (permanent != null) {
                    List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects"); // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
                    permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game, appliedEffects);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public BloodthirstEffect copy() {
        return new BloodthirstEffect(this);
    }
}
