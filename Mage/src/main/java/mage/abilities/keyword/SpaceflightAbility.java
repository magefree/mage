
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
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
        this.addEffect(new SpaceFlightEffect());
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

class SpaceFlightEffect extends RestrictionEffect implements MageSingleton {

    public SpaceFlightEffect() {
        super(Duration.EndOfGame);
    }

    public SpaceFlightEffect(final SpaceFlightEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(SpaceflightAbility.getInstance().getId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return attacker.getAbilities().containsKey(SpaceflightAbility.getInstance().getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return blocker.getAbilities().containsKey(SpaceflightAbility.getInstance().getId())
                || blocker.getAbilities().containsKey(CanBlockSpaceflightAbility.getInstance().getId());
    }

    @Override
    public SpaceFlightEffect copy() {
        return new SpaceFlightEffect(this);
    }

}
