package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

public class VanishingUpkeepAbility extends BeginningOfUpkeepTriggeredAbility {

    private int vanishingAmount;

    public VanishingUpkeepAbility(int vanishingEffect) {
        super(new VanishingEffect(), TargetController.YOU, false);
        this.vanishingAmount = vanishingEffect;
    }

    public VanishingUpkeepAbility(final VanishingUpkeepAbility ability) {
        super(ability);
        this.vanishingAmount = ability.vanishingAmount;
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new VanishingUpkeepAbility(this);
    }

    @Override
    public String getRule() {
        return new StringBuilder("Vanishing ")
            .append(vanishingAmount)
            .append(" <i>(This permanent enters the battlefield with ").append(CardUtil.numberToText(vanishingAmount))
            .append(" time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.)<i>").toString();
    }
}

class VanishingEffect extends OneShotEffect<VanishingEffect> {
    VanishingEffect() {
        super(Outcome.Sacrifice);
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
                p.removeCounters(CounterType.TIME.createInstance(), game);
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
