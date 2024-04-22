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
 * @author Loki
 */
public class FearAbility extends EvasionAbility implements MageSingleton {

    private static final FearAbility instance = new FearAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static FearAbility getInstance() {
        return instance;
    }

    private FearAbility() {
        this.addEffect(new FearEffect());
    }

    @Override
    public String getRule() {
        return "fear";
    }

    @Override
    public FearAbility copy() {
        return instance;
    }

}

class FearEffect extends RestrictionEffect implements MageSingleton {

    public FearEffect() {
        super(Duration.EndOfGame);
    }

    protected FearEffect(final FearEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(FearAbility.getInstance().getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.isArtifact(game) || blocker.getColor(game).isBlack();
    }

    @Override
    public FearEffect copy() {
        return new FearEffect(this);
    }

}
