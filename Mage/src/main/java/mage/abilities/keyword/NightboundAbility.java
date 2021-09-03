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
public class NightboundAbility extends StaticAbility implements MageSingleton {

    private static final NightboundAbility instance;

    static {
        instance = new NightboundAbility();
        // instance.addIcon(NightboundAbilityIcon.instance); (needs to be added)
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static NightboundAbility getInstance() {
        return instance;
    }

    private NightboundAbility() {
        super(Zone.ALL, new NightboundEffect());
    }

    @Override
    public String getRule() {
        return "nightbound <i>(If a player casts at least two spells during their own turn, it becomes day next turn.)</i>";
    }

    @Override
    public NightboundAbility copy() {
        return instance;
    }
}

class NightboundEffect extends ContinuousEffectImpl {

    NightboundEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
    }

    private NightboundEffect(final NightboundEffect effect) {
        super(effect);
    }

    @Override
    public NightboundEffect copy() {
        return new NightboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.hasDayNight()) {
            game.setDaytime(false);
        }
        return true;
    }
}
