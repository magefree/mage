package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.RoleType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class CreateRoleAttachedSourceEffect extends OneShotEffect {

    private final RoleType roleType;

    public CreateRoleAttachedSourceEffect(RoleType roleType) {
        super(Outcome.Benefit);
        this.roleType = roleType;
    }

    private CreateRoleAttachedSourceEffect(final CreateRoleAttachedSourceEffect effect) {
        super(effect);
        this.roleType = effect.roleType;
    }

    @Override
    public CreateRoleAttachedSourceEffect copy() {
        return new CreateRoleAttachedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        roleType.createToken(permanent, game, source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "create a " + roleType.getName() + " Role token attached to it";
    }
}
