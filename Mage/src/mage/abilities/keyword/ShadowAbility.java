package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 * "Shadow" keyword
 * @author Loki
 */
public class ShadowAbility extends EvasionAbility<ShadowAbility> implements MageSingleton {
    private static final ShadowAbility fINSTANCE =  new ShadowAbility();

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static ShadowAbility getInstance() {
        return fINSTANCE;
    }

    private ShadowAbility() {
        this.addEffect(new ShadowEffect());
    }

    @Override
    public String getRule() {
        return "Shadow";
    }

    @Override
    public ShadowAbility copy() {
        return fINSTANCE;
    }

}

class ShadowEffect extends RestrictionEffect<ShadowEffect> implements MageSingleton {

    public ShadowEffect() {
        super(Constants.Duration.EndOfGame);
    }

    public ShadowEffect(final ShadowEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAbilities().containsKey(ShadowAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.getAbilities().containsKey(ShadowAbility.getInstance().getId()))
            return true;
        return false;
    }

    @Override
    public ShadowEffect copy() {
        return new ShadowEffect(this);
    }
}
