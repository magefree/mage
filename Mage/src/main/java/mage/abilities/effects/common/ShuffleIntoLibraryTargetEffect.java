package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author awjackson
 */
public class ShuffleIntoLibraryTargetEffect extends OneShotEffect {

    boolean optional;

    public ShuffleIntoLibraryTargetEffect() {
        this(false);
    }

    public ShuffleIntoLibraryTargetEffect(boolean optional) {
        super(Outcome.Detriment);
        this.optional = optional;
    }

    public ShuffleIntoLibraryTargetEffect(final ShuffleIntoLibraryTargetEffect effect) {
        super(effect);
        this.optional = effect.optional;
    }

    @Override
    public ShuffleIntoLibraryTargetEffect copy() {
        return new ShuffleIntoLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (cards.isEmpty()) {
            return true;
        }
        if (optional && !controller.chooseUse(Outcome.Benefit, "Shuffle the target card" + (cards.size() > 1 ? "s" : "") + " into your library?", source, game)) {
            return true;
        }
        // sort the target cards by owner
        Map<UUID, Cards> cardsByOwner = new HashMap<>();
        for (Card card : cards.getCards(game)) {
            cardsByOwner.computeIfAbsent(card.getOwnerId(), x -> new CardsImpl()).add(card);
        }
        // then each player shuffles the cards they own
        for (Map.Entry<UUID, Cards> entry : cardsByOwner.entrySet()) {
            Player owner = game.getPlayer(entry.getKey());
            if (owner != null) {
                owner.shuffleCardsToLibrary(entry.getValue(), game, source);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode)
    {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String targetDescription = getTargetPointer().describeTargets(mode.getTargets(), "");
        if (targetDescription.contains("your graveyard")) {
            return (optional ? "you may shuffle " : "shuffle ") + targetDescription + " into your library";
        }
        return "choose " + targetDescription + (
                getTargetPointer().isPlural(mode.getTargets()) ?
                ". The owners of those cards shuffle them into their libraries" :
                ". Its owner shuffles it into their library"
        );
    }
}
