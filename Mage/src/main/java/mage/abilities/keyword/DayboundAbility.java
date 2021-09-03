package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 * @author TheElk801
 */
public class DayboundAbility extends StaticAbility implements MageSingleton {

    private static final DayboundAbility instance;

    static {
        instance = new DayboundAbility();
        // instance.addIcon(DayboundAbilityIcon.instance); (needs to be added)
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static DayboundAbility getInstance() {
        return instance;
    }

    private DayboundAbility() {
        super(Zone.ALL, new DayboundEffect());
    }

    @Override
    public String getRule() {
        return "daybound <i>(If a player casts no spells during their own turn, it becomes night next turn.)</i>";
    }

    @Override
    public DayboundAbility copy() {
        return instance;
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
