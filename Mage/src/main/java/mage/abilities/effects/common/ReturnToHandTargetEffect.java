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

    protected boolean multitargetHandling;

    public ReturnToHandTargetEffect() {
        this(false);
    }

    public ReturnToHandTargetEffect(boolean multitargetHandling) {
        super(Outcome.ReturnToHand);
        this.multitargetHandling = multitargetHandling;
    }

    public ReturnToHandTargetEffect(final ReturnToHandTargetEffect effect) {
        super(effect);
        this.multitargetHandling = effect.multitargetHandling;
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
        if (multitargetHandling) {
            for (Target target : source.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    MageObject mageObject = game.getObject(targetId);
                    if (mageObject instanceof Spell && mageObject.isCopy()) {
                        copyIds.add(targetId);
                    } else if (mageObject instanceof Card) {
                        cards.add((Card) mageObject);
                    }
                }
            }
        } else {
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
        }
        for (UUID copyId : copyIds) {
            game.getStack().remove(game.getSpell(copyId), game);
        }
        return controller.moveCards(cards, Zone.HAND, source, game);
    }

    @Override
    public String getText(Mode mode
    ) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().size() < 1) {
            return "";
        }
        Target target = mode.getTargets().get(0);
        StringBuilder sb = new StringBuilder("return ");
        if (target.getMinNumberOfTargets() == 0 && target.getMaxNumberOfTargets() >= 1) {
            sb.append("up to ");
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" ");
        }
        else if (!(target.getMinNumberOfTargets() == 1 || target.getMaxNumberOfTargets() == 1)) {
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" ");
        }
        if (!target.getTargetName().contains("target")) {
            sb.append("target ");
        }
        sb.append(target.getTargetName());
        if(target.getMaxNumberOfTargets() > 1) {
            sb.append(" to their owners' hands");
        }
        else {
            sb.append(" to its owner's hand");
        }
        return sb.toString();
    }

}
