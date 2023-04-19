package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 * @author LevelX2
 */

public class HorsemanshipAbility extends EvasionAbility implements MageSingleton {
    private static final HorsemanshipAbility instance = new HorsemanshipAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HorsemanshipAbility getInstance() {
        return instance;
    }

    private HorsemanshipAbility() {
        this.addEffect(new Horsemanship());
    }

    @Override
    public String getRule() {
        return "horsemanship <i>(This creature can't be blocked except by creatures with horsemanship.)</i>";
    }

    @Override
    public HorsemanshipAbility copy() {
        return instance;
    }

}

class Horsemanship extends RestrictionEffect implements MageSingleton {

    public Horsemanship() {
        super(Duration.EndOfGame);
    }

    public Horsemanship(final Horsemanship effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(HorsemanshipAbility.getInstance().getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getAbilities().containsKey(HorsemanshipAbility.getInstance().getId());
    }

    @Override
    public Horsemanship copy() {
        return new Horsemanship(this);
    }
}
