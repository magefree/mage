package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Quercitron
 */
public class LookAtTargetPlayerHandEffect extends OneShotEffect {

    public LookAtTargetPlayerHandEffect() {
        super(Outcome.Benefit);
    }

    protected LookAtTargetPlayerHandEffect(final LookAtTargetPlayerHandEffect effect) {
        super(effect);
    }

    @Override
    public LookAtTargetPlayerHandEffect copy() {
        return new LookAtTargetPlayerHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        if (you != null && targetPlayer != null) {
            you.lookAtCards(sourceObject != null ? sourceObject.getIdName() : null, targetPlayer.getHand(), game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "look at " + getTargetPointer().describeTargets(mode.getTargets(), "that player") + "'s hand";
    }

}
