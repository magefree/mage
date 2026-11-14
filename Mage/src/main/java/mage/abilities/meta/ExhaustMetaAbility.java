package mage.abilities.meta;

import mage.abilities.ActivatedAbility;
import mage.constants.AsThoughEffectType;
import mage.constants.TimingRule;
import mage.game.Game;

public interface ExhaustMetaAbility extends ActivatedAbility {

    default boolean hasMoreExhaustActivationsThisTurn(Game game) {
        if (this.getTotalActivations(game) < this.getMaxActivationsPerGame(game)) {
            return false;
        }
        return !game.getContinuousEffects()
                .asThough(this.getSourceId(), AsThoughEffectType.ALLOW_EXHAUST_PER_TURN, this, this.getControllerId(), game)
                .isEmpty();
    }

    default String getExhaustRule(String baseRule, boolean withReminderText) {
        return "Exhaust &mdash; " + baseRule +
                (this.getTiming() == TimingRule.SORCERY ? " Activate only as a sorcery." : "") +
                (withReminderText ? " <i>(Activate each exhaust ability only once.)</i>" : "");
    }
}
