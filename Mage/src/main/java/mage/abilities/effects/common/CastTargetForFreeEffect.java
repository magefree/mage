package mage.abilities.effects.common;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CastTargetForFreeEffect extends OneShotEffect {

    public CastTargetForFreeEffect() {
        super(Outcome.Benefit);
    }

    protected CastTargetForFreeEffect(final CastTargetForFreeEffect effect) {
        super(effect);
    }

    @Override
    public CastTargetForFreeEffect copy() {
        return new CastTargetForFreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card target = (Card) game.getObject(source.getFirstTarget());
        if (controller != null && target != null) {
            game.getState().setValue("PlayFromNotOwnHandZone" + target.getId(), Boolean.TRUE);
            boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(target, game, true),
                    game, true, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + target.getId(), null);
            return cardWasCast;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "you may cast " + getTargetPointer().describeTargets(mode.getTargets(), "that card")
                + " without paying its mana cost";
    }
}
