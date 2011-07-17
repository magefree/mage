package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.WatcherImpl;

import java.util.UUID;

/**
 * @author Loki
 */
public class BloodthirstAbility extends EntersBattlefieldAbility {
    public BloodthirstAbility() {
        super(new BloodthirstEffect(), "");
    }
}

class BloodthirstEffect extends OneShotEffect<BloodthirstEffect> {
    BloodthirstEffect() {
        super(Constants.Outcome.BoostCreature);
    }

    BloodthirstEffect(final BloodthirstEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BloodthirstEffect copy() {
        return new BloodthirstEffect(this);
    }
}

class BloodthirstWatcher extends WatcherImpl<BloodthirstWatcher> {
    BloodthirstWatcher(UUID controllerId) {
        super("", controllerId);
    }

    BloodthirstWatcher(final BloodthirstWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public BloodthirstWatcher copy() {
        return new BloodthirstWatcher(this);
    }
}