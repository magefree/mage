package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xenohedron
 */
public class ExileThenReturnTargetEffect extends OneShotEffect {

    private final boolean yourControl;
    private final boolean textThatCard;
    private final PutCards putCards;
    private OneShotEffect afterEffect = null;

    public ExileThenReturnTargetEffect(boolean yourControl, boolean textThatCard) {
        this(yourControl, textThatCard, PutCards.BATTLEFIELD);
    }

    public ExileThenReturnTargetEffect(boolean yourControl, boolean textThatCard, PutCards putCards) {
        super(Outcome.Benefit);
        this.yourControl = yourControl;
        this.textThatCard = textThatCard;
        this.putCards = putCards;
    }

    protected ExileThenReturnTargetEffect(final ExileThenReturnTargetEffect effect) {
        super(effect);
        this.putCards = effect.putCards;
        this.yourControl = effect.yourControl;
        this.textThatCard = effect.textThatCard;
        this.afterEffect = effect.afterEffect == null ? null : effect.afterEffect.copy();
    }

    @Override
    public ExileThenReturnTargetEffect copy() {
        return new ExileThenReturnTargetEffect(this);
    }

    public ExileThenReturnTargetEffect withAfterEffect(OneShotEffect afterEffect) {
        this.afterEffect = afterEffect;
        return this;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> toFlicker = getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (toFlicker.isEmpty()) {
            return false;
        }
        controller.moveCards(toFlicker, Zone.EXILED, source, game);
        game.processAction();
        for (Card card : toFlicker) {
            putCards.moveCard(
                    yourControl ? controller : game.getPlayer(card.getOwnerId()),
                    card.getMainCard(), source, game, "card");
        }
        if (afterEffect != null) {
            afterEffect.setTargetPointer(new FixedTargets(toFlicker, game));
            afterEffect.apply(game, source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("exile ");
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "that permanent"));
        sb.append(", then return ");
        if (getTargetPointer().isPlural(mode.getTargets())) {
            sb.append(textThatCard ? "those cards " : "them ");
            sb.append(putCards.getMessage(false, false).replace("onto", "to"));
            sb.append(" under ");
            sb.append(this.yourControl ? "your" : "their owner's");
        } else {
            sb.append(textThatCard ? "that card " : "it ");
            sb.append(putCards.getMessage(false, false).replace("onto", "to"));
            sb.append(" under ");
            sb.append(this.yourControl ? "your" : "its owner's");
        }
        sb.append(" control");
        if (afterEffect != null) {
            sb.append(". ").append(CardUtil.getTextWithFirstCharUpperCase(afterEffect.getText(mode)));
        }
        return sb.toString();
    }

}
