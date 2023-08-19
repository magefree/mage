package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author xenohedron
 */
public class ExileThenReturnTargetEffect extends OneShotEffect {

    private final boolean yourControl;
    private final boolean textThatCard;
    private final PutCards putCards;

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
    }

    @Override
    public ExileThenReturnTargetEffect copy() {
        return new ExileThenReturnTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> toFlicker = new LinkedHashSet<>();
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            toFlicker.add(permanent);
        }
        if (controller == null || toFlicker.isEmpty()) {
            return false;
        }
        controller.moveCards(toFlicker, Zone.EXILED, source, game);
        game.getState().processAction(game);
        for (Card card : toFlicker) {
            putCards.moveCard(
                    yourControl ? controller : game.getPlayer(card.getOwnerId()),
                    card.getMainCard(), source, game, "card");
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
        return sb.toString();
    }

}
