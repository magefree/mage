package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.icon.CardIconImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FlyingAbility extends EvasionAbility implements MageSingleton {

    private static final FlyingAbility instance;

    static {
        instance = new FlyingAbility();
        instance.addIcon(CardIconImpl.ABILITY_FLYING);
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static FlyingAbility getInstance() {
        return instance;
    }

    private FlyingAbility() {
        this.addEffect(new FlyingEffect());
    }

    @Override
    public String getRule() {
        return "flying";
    }

    @Override
    public FlyingAbility copy() {
        return instance;
    }

}

class FlyingEffect extends RestrictionEffect implements MageSingleton {

    public FlyingEffect() {
        super(Duration.EndOfGame);
    }

    public FlyingEffect(final FlyingEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(FlyingAbility.getInstance().getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getAbilities().containsKey(FlyingAbility.getInstance().getId())
                || blocker.getAbilities().containsKey(ReachAbility.getInstance().getId())
                || (null != game.getContinuousEffects().asThough(blocker.getId(), AsThoughEffectType.BLOCK_DRAGON, null, blocker.getControllerId(), game)
                && attacker.hasSubtype(SubType.DRAGON, game));
    }

    @Override
    public FlyingEffect copy() {
        return new FlyingEffect(this);
    }

}
