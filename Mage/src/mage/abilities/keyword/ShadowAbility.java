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
 * @author Loki
 */
public class ShadowAbility extends EvasionAbility implements MageSingleton {
    private static final ShadowAbility fINSTANCE =  new ShadowAbility();

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static ShadowAbility getInstance() {
        return fINSTANCE;
    }

    private ShadowAbility() {
        this.addEffect(ShadowEffect.getInstance());
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

class ShadowEffect extends RestrictionEffect implements MageSingleton {

    private static final ShadowEffect fINSTANCE =  new ShadowEffect();
    
    private ShadowEffect() {
        super(Duration.EndOfGame);
    }

    public static ShadowEffect getInstance() {
        return fINSTANCE;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAbilities().containsKey(ShadowAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return attacker.getAbilities().containsKey(ShadowAbility.getInstance().getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.getAbilities().containsKey(ShadowAbility.getInstance().getId())
                || game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_SHADOW, source, blocker.getControllerId(), game)) {
            return true;
        }
        return false;
    }

    @Override
    public ShadowEffect copy() {
        return fINSTANCE;
    }
}
