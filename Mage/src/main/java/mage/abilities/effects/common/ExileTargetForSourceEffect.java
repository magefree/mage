
package mage.abilities.effects.common;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
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

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExileTargetForSourceEffect extends OneShotEffect {

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
        if (controller != null && sourceObject != null) {
            Set<Card> cards = new LinkedHashSet<>();
            if (source.getTargets().size() > 1 && targetPointer instanceof FirstTargetPointer) {
                for (Target target : source.getTargets()) {
                    for (UUID targetId : target.getTargets()) {
                        MageObject mageObject = game.getObject(targetId);
                        if (mageObject instanceof Card) {
                            cards.add((Card) mageObject);
                        }
                    }
                }
            } else {
                for (UUID targetId : targetPointer.getTargets(game, source)) {
                    MageObject mageObject = game.getObject(targetId);
                    if (mageObject != null) {
                        cards.add((Card) mageObject);
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

        if (mode.getTargets().isEmpty()) {
            return "exile it";
        } else if (mode.getTargets().get(0).getTargetName().startsWith("another")) {
            return "exile " + mode.getTargets().get(0).getTargetName();
        } else {
            return "exile target " + mode.getTargets().get(0).getTargetName();
        }
    }
}
