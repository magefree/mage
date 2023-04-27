package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnToHandTargetEffect extends OneShotEffect {

    public ReturnToHandTargetEffect() {
        super(Outcome.ReturnToHand);
    }

    public ReturnToHandTargetEffect(final ReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandTargetEffect copy() {
        return new ReturnToHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<UUID> copyIds = new ArrayList<>();
        Set<Card> cards = new LinkedHashSet<>();
            for (UUID targetId : targetPointer.getTargets(game, source)) {
                MageObject mageObject = game.getObject(targetId);
                if (mageObject != null) {
                    if (mageObject instanceof Spell
                            && mageObject.isCopy()) {
                        copyIds.add(targetId);
                    } else if (mageObject instanceof Card) {
                        cards.add((Card) mageObject);
                    }
                }
            }
        for (UUID copyId : copyIds) {
            game.getStack().remove(game.getSpell(copyId), game);
        }
        return controller.moveCards(cards, Zone.HAND, source, game);
    }

    @Override
    public String getText(Mode mode)
    {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "return " + getTargetPointer().describeTargets(mode.getTargets(), "") +
                (getTargetPointer().isPlural(mode.getTargets()) ? " to their owners' hands" : " to its owner's hand");
    }
}
