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

    private final int vanishingAmount;
    private final String permanentType;

    public VanishingUpkeepAbility(int vanishingEffect) {
        super(new VanishingEffect(), TargetController.YOU, false);
        this.vanishingAmount = vanishingEffect;
        this.permanentType = "creature";
    }
    
    public VanishingUpkeepAbility(int vanishingEffect, String permanentType) {
        super(new VanishingEffect(), TargetController.YOU, false);
        this.vanishingAmount = vanishingEffect;
        this.permanentType = permanentType;
    }

    private VanishingUpkeepAbility(final VanishingUpkeepAbility ability) {
        super(ability);
        this.vanishingAmount = ability.vanishingAmount;
        this.permanentType = ability.permanentType;
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new VanishingUpkeepAbility(this);
    }

    @Override
    public String getRule() {
        if(vanishingAmount > 0) {
            return "Vanishing " + vanishingAmount
                + " <i>(This " + permanentType + " enters the battlefield with " + CardUtil.numberToText(vanishingAmount)
                + " time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.)</i>";
        }
        else {
            return "Vanishing <i>(At the beginning of your upkeep, remove a time counter from this " + permanentType + ". When the last is removed, sacrifice it.)</i>";
        }
    }
}

class VanishingEffect extends OneShotEffect {

    VanishingEffect() {
        super(Outcome.Sacrifice);
    }

    VanishingEffect(final VanishingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p == null) {
            return false;
        }

        int amount = p.getCounters(game).getCount(CounterType.TIME);
        if (amount > 0) {
            p.removeCounters(CounterType.TIME.createInstance(), source, game);
            game.informPlayers("Removed a time counter from " + p.getLogName() + " (" + amount + " left)");
        }
        return true;
    }

    @Override
    public VanishingEffect copy() {
        return new VanishingEffect(this);
    }
}
