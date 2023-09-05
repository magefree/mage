package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
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
        this.addEffect(new ShadowEffect());
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

class ShadowEffect extends RestrictionEffect implements MageSingleton {

    public ShadowEffect() {
        super(Duration.EndOfGame);
    }

    protected ShadowEffect(final ShadowEffect effect) {
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
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getAbilities().containsKey(ShadowAbility.getInstance().getId())
                || !game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_SHADOW, null, blocker.getControllerId(), game).isEmpty();
    }

    @Override
    public ShadowEffect copy() {
        return new ShadowEffect(this);
    }
}
