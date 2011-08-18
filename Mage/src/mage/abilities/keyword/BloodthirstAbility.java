package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 * This ability depends on BloodthirstWather installed on source card
 * @author Loki
 */
public class BloodthirstAbility extends EntersBattlefieldAbility {
    private int amount;

    public BloodthirstAbility(int amount) {
        super(new BloodthirstEffect(amount), "");
        this.amount = amount;
    }

    public BloodthirstAbility(final BloodthirstAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public EntersBattlefieldAbility copy() {
        return new BloodthirstAbility(this);
    }

    @Override
    public String getRule() {
        return "Bloodthirst " + amount;
    }
}

class BloodthirstEffect extends OneShotEffect<BloodthirstEffect> {
    private int amount;

    BloodthirstEffect(int amount) {
        super(Constants.Outcome.BoostCreature);
        this.amount = amount;
		staticText = "this permanent comes into play with " + amount + " +1/+1 counters on it";
    }

    BloodthirstEffect(final BloodthirstEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get(source.getControllerId(), "DamagedOpponents");
        if (watcher.conditionMet()) {
            Permanent p = game.getPermanent(source.getSourceId());
            if (p != null) {
                p.addCounters(CounterType.P1P1.createInstance(amount), game);
            }
        }
        return false;
    }

    @Override
    public BloodthirstEffect copy() {
        return new BloodthirstEffect(this);
    }
}

