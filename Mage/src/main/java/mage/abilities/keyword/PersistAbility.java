package mage.abilities.keyword;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

public class PersistAbility extends DiesSourceTriggeredAbility {

    public PersistAbility() {
        super(new ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect(
                CounterType.M1M1.createInstance()));
    }

    protected PersistAbility(final PersistAbility ability) {
        super(ability);
    }

    @Override
    public PersistAbility copy() {
        return new PersistAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent.getCounters(game).getCount(CounterType.M1M1) == 0) {
                this.getEffects().setTargetPointer(new FixedTargets(
                        CardUtil.getAllCardsFromPermanentLeftBattlefield(permanent, game), game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "persist <i>(When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)</i>";
    }
}
