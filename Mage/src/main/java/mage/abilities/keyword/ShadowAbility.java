package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
import mage.abilities.effects.EvasionEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 * "Shadow" keyword
 *
 * @author Loki
 */
public class ShadowAbility extends EvasionAbility implements MageSingleton {

    private static final ShadowAbility instance = new ShadowAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ShadowAbility getInstance() {
        return instance;
    }

    private ShadowAbility() {
        this.addEffect(new ShadowBlockEffect());
        this.addEffect(new ShadowEvasionEffect());
    }

    @Override
    public String getRule() {
        return "shadow <i>(This creature can block or be blocked by only creatures with shadow.)</i>";
    }

    @Override
    public ShadowAbility copy() {
        return instance;
    }

}

class ShadowEvasionEffect extends EvasionEffect implements MageSingleton {

    ShadowEvasionEffect() {
        super(Duration.EndOfGame);
    }

    protected ShadowEvasionEffect(final ShadowEvasionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(ShadowAbility.getInstance().getId());
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return !blocker.getAbilities().containsKey(ShadowAbility.getInstance().getId())
            && null == game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_SHADOW, null, blocker.getControllerId(), game);
    }

    @Override
    public ShadowEvasionEffect copy() {
        return new ShadowEvasionEffect(this);
    }
}


class ShadowBlockEffect extends RestrictionEffect implements MageSingleton {

    ShadowBlockEffect() {
        super(Duration.EndOfGame);
    }

    private ShadowBlockEffect(final ShadowBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(ShadowAbility.getInstance().getId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        return attacker.getAbilities().containsKey(ShadowAbility.getInstance().getId());
    }

    @Override
    public ShadowBlockEffect copy() {
        return new ShadowBlockEffect(this);
    }
}
