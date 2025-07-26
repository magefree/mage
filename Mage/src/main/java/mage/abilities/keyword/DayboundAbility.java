package mage.abilities.keyword;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.common.DayNightHint;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author TheElk801
 */
public class DayboundAbility extends StaticAbility {

    public DayboundAbility() {
        super(Zone.BATTLEFIELD, new DayboundEffect());
        this.addHint(DayNightHint.instance);
        this.addSubAbility(new TransformAbility());
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
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        if (!game.hasDayNight()) {
            // 702.145d
            // Any time a player controls a permanent with daybound, if it’s neither day nor night, it becomes day.
            game.setDaytime(true);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            affectedObjects.add(permanent);
            return true;
        }
        return false;
    }
}
