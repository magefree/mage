package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.common.DayNightHint;
import mage.constants.*;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class DayboundAbility extends StaticAbility {

    public DayboundAbility() {
        super(Zone.BATTLEFIELD, new DayboundEffect());
        this.addHint(DayNightHint.instance);
    }

    private DayboundAbility(final DayboundAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "daybound <i>(If a player casts no spells during their own turn, it becomes night next turn.)</i>";
    }

    @Override
    public DayboundAbility copy() {
        return new DayboundAbility(this);
    }
}

class DayboundEffect extends ContinuousEffectImpl {

    DayboundEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
    }

    private DayboundEffect(final DayboundEffect effect) {
        super(effect);
    }

    @Override
    public DayboundEffect copy() {
        return new DayboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.hasDayNight()) {
            game.setDaytime(true);
        }
        return true;
    }
}
