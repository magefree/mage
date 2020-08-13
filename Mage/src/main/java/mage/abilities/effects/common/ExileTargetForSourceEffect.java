package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FirstTargetPointer;
import mage.util.CardUtil;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.TargetPointer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileTargetForSourceEffect extends OneShotEffect {

    /**
     * Exile cards to source's exile window (e.g. if it has another effect like
     * return from exile later) TODO: delete that effect and replace it by
     * ExileTargetEffect (it will have a special param for same purpose)
     */
    public ExileTargetForSourceEffect() {
        super(Outcome.Exile);
    }

    public ExileTargetForSourceEffect(final ExileTargetForSourceEffect effect) {
        super(effect);
    }

    @Override
    public ExileTargetForSourceEffect copy() {
        return new ExileTargetForSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);

        if (controller != null
                && sourceObject != null) {
            Set<Card> cards = new LinkedHashSet<>();
            if (source.getTargets().size() > 1
                    && targetPointer instanceof FirstTargetPointer) {
                for (Target target : source.getTargets()) {
                    for (UUID targetId : target.getTargets()) {
                        MageObject mageObject = game.getObject(targetId);
                        if (mageObject instanceof Card) {
                            cards.add((Card) mageObject);
                        }
                    }
                }
            } else {
                if (!targetPointer.getTargets(game, source).isEmpty()) {
                    for (UUID targetId : targetPointer.getTargets(game, source)) {
                        MageObject mageObject = game.getObject(targetId);
                        if (mageObject != null) {
                            cards.add((Card) mageObject);
                        }
                    }
                } else {
                    // issue with Madness keyword  #6889
                    UUID fixedTargetId = null;
                    for (Effect effect : source.getEffects()) {
                        TargetPointer targetPointerId = effect.getTargetPointer();
                        if (targetPointerId instanceof FixedTarget) {
                            fixedTargetId = (((FixedTarget) targetPointerId).getTarget());
                        }
                    }
                    if (fixedTargetId != null) {
                        cards.add((Card) game.getObject(fixedTargetId));
                    }
                }
            }
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            return controller.moveCardsToExile(cards, source, game, true, exileId, sourceObject.getIdName());
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        String amountText = "";
        if (mode.getTargets().get(0).getMinNumberOfTargets() < mode.getTargets().get(0).getMaxNumberOfTargets()) {
            amountText = "up to " + CardUtil.numberToText(mode.getTargets().get(0).getMaxNumberOfTargets()) + " ";
        } else if (mode.getTargets().get(0).getMinNumberOfTargets() > 1) {
            amountText = CardUtil.numberToText(mode.getTargets().get(0).getMinNumberOfTargets()) + " ";
        }

        String targetText = "";
        if (mode.getTargets().get(0).getTargetName().contains("target ")) {
            targetText = "";
        } else {
            targetText = "target ";
        }

        if (mode.getTargets().isEmpty()) {
            return "exile it";
        } else {
            return "exile " + amountText + targetText + mode.getTargets().get(0).getTargetName();
        }
    }
}
