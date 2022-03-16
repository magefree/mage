package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

/**
 * @author jeff
 */
public class ReturnFromGraveyardToHandTargetEffect extends OneShotEffect {

    public ReturnFromGraveyardToHandTargetEffect() {
        super(Outcome.ReturnToHand);
    }

    public ReturnFromGraveyardToHandTargetEffect(final ReturnFromGraveyardToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public ReturnFromGraveyardToHandTargetEffect copy() {
        return new ReturnFromGraveyardToHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        cards.retainZone(Zone.GRAVEYARD, game); //verify the target card is still in the graveyard
        return !cards.isEmpty() && controller.moveCards(cards, Zone.HAND, source, game);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        sb.append("return ");
        if (target.getMaxNumberOfTargets() != target.getNumberOfTargets()) {
            sb.append("up to ");
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
        } else if (target.getMaxNumberOfTargets() > 1) {
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
        }
        if (!target.getTargetName().startsWith("another")) {
            sb.append("target ");
        }
        sb.append(target.getTargetName());
        if (!target.getTargetName().endsWith("graveyard")) {
            sb.append(" from your graveyard");
        }
        sb.append(" to your hand");
        return sb.toString();
    }

}
