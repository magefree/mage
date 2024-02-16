package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Use it for combo with ReturnFromExileForSourceEffect (exile and return exiled later)
 *
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

    protected ExileTargetForSourceEffect(final ExileTargetForSourceEffect effect) {
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
        if (controller == null || sourceObject == null) {
            return false;
        }

        Set<UUID> objectsToMove = new LinkedHashSet<>();
        if (this.targetPointer instanceof FirstTargetPointer
                && source.getTargets().size() > 1) {
            for (Target target : source.getTargets()) {
                objectsToMove.addAll(target.getTargets());
            }
        } else {
            if (this.targetPointer != null && !this.targetPointer.getTargets(game, source).isEmpty()) {
                objectsToMove.addAll(this.targetPointer.getTargets(game, source));
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
                    objectsToMove.add(fixedTargetId);
                }
            }
        }

        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());

        // it can target permanents on battlefield, so use objects first
        Set<Card> cardsToMove = objectsToMove.stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .map(object -> {
                    if (object instanceof Card) {
                        return (Card) object;
                    } else {
                        return game.getCard(object.getId());
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return controller.moveCardsToExile(cardsToMove, source, game, true, exileId, sourceObject.getIdName());
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "exile " + getTargetPointer().describeTargets(mode.getTargets(), "it");
    }
}
