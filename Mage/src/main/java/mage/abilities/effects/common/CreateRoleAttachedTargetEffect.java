package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.RoleType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 * @author TheElk801
 */
public class CreateRoleAttachedTargetEffect extends OneShotEffect {

    private final RoleType roleType;

    public CreateRoleAttachedTargetEffect(RoleType roleType) {
        super(Outcome.Benefit);
        this.roleType = roleType;
    }

    private CreateRoleAttachedTargetEffect(final CreateRoleAttachedTargetEffect effect) {
        super(effect);
        this.roleType = effect.roleType;
    }

    @Override
    public CreateRoleAttachedTargetEffect copy() {
        return new CreateRoleAttachedTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Token token = roleType.createToken(permanent, game, source);
        // The token may not be created, for instance if the creature has protection from enchantments.
        return token.getLastAddedTokenIds().size() > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "create a " + roleType.getName() + " Role token attached to " +
                getTargetPointer().describeTargets(mode.getTargets(), "that creature");
    }
}
