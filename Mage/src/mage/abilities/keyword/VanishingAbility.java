package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class VanishingAbility extends BeginningOfUpkeepTriggeredAbility {
    public VanishingAbility() {
        super(new VanishingEffect(), Constants.TargetController.YOU, false);
    }

    public VanishingAbility(final VanishingAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new VanishingAbility(this);
    }

    @Override
    public String getRule() {
        return "Vanishing (This permanent enters the battlefield with time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.)";
    }
}

class VanishingEffect extends OneShotEffect<VanishingEffect> {
    VanishingEffect() {
        super(Constants.Outcome.Sacrifice);
    }

    VanishingEffect(final VanishingEffect effect) {
        super(effect);
    }


    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            int amount = p.getCounters().getCount(CounterType.TIME);
            if (amount > 0) {
                p.getCounters().removeCounter(CounterType.TIME, 1);
            } else {
                p.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public VanishingEffect copy() {
        return new VanishingEffect(this);
    }
}