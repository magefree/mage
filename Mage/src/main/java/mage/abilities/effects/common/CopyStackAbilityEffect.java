package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class CopyStackAbilityEffect extends OneShotEffect {

    public CopyStackAbilityEffect() {
        super(Outcome.Copy);
        staticText = "copy that ability. You may choose new targets for the copy";
    }

    private CopyStackAbilityEffect(final CopyStackAbilityEffect effect) {
        super(effect);
    }

    @Override
    public CopyStackAbilityEffect copy() {
        return new CopyStackAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        StackAbility ability = (StackAbility) getValue("stackAbility");
        if (controller == null || ability == null) {
            return false;
        }
        ability.createCopyOnStack(game, source, source.getControllerId(), true);
        return true;
    }
}
