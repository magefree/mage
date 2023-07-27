package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
import mage.abilities.effects.EvasionEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 * @author Styxo
 */
public class SpaceflightAbility extends EvasionAbility implements MageSingleton {

    private static final SpaceflightAbility instance = new SpaceflightAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static SpaceflightAbility getInstance() {
        return instance;
    }

    private SpaceflightAbility() {
        this.addEffect(new SpaceFlightBlockEffect());
        this.addEffect(new SpaceFlightEvasionEffect());
    }

    @Override
    public String getRule() {
        return "Spaceflight <i>(This creature can only block or be blocked by creatures with spaceflight)</i>";
    }

    @Override
    public SpaceflightAbility copy() {
        return instance;
    }

}

class SpaceFlightBlockEffect extends RestrictionEffect implements MageSingleton {

    SpaceFlightBlockEffect() {
        super(Duration.EndOfGame);
    }

    protected SpaceFlightBlockEffect(final SpaceFlightBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(SpaceflightAbility.getInstance().getId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        return attacker.getAbilities().containsKey(SpaceflightAbility.getInstance().getId());
    }

    @Override
    public SpaceFlightBlockEffect copy() {
        return new SpaceFlightBlockEffect(this);
    }

}

class SpaceFlightEvasionEffect extends EvasionEffect implements MageSingleton {

    SpaceFlightEvasionEffect() {
        super(Duration.EndOfGame);
    }

    private SpaceFlightEvasionEffect(final SpaceFlightEvasionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(SpaceflightAbility.getInstance().getId());
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return !blocker.getAbilities().containsKey(SpaceflightAbility.getInstance().getId())
                && !blocker.getAbilities().containsKey(CanBlockSpaceflightAbility.getInstance().getId());
    }

    @Override
    public SpaceFlightEvasionEffect copy() {
        return new SpaceFlightEvasionEffect(this);
    }

}