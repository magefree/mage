

package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MustBeBlockedByAllSourceEffect extends RequirementEffect {

    public MustBeBlockedByAllSourceEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public MustBeBlockedByAllSourceEffect(Duration duration) {
        super(duration);
        staticText = "All creatures able to block {this} do so";
    }

    public MustBeBlockedByAllSourceEffect(final MustBeBlockedByAllSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        if (sourceCreature != null && sourceCreature.isAttacking()) {
            return permanent.canBlock(source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return source.getSourceId();
    }

    @Override
    public MustBeBlockedByAllSourceEffect copy() {
        return new MustBeBlockedByAllSourceEffect(this);
    }

}
