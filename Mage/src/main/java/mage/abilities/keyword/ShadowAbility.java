package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

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

    public ShadowEffect(final ShadowEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(ShadowAbility.getInstance().getId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return attacker.getAbilities().containsKey(ShadowAbility.getInstance().getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return blocker.getAbilities().containsKey(ShadowAbility.getInstance().getId())
                || null != game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_SHADOW, source, blocker.getControllerId(), game);
    }

    @Override
    public ShadowEffect copy() {
        return new ShadowEffect(this);
    }
}
