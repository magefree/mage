package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Cguy7777
 */
public class ExileCardsFromTopOfLibraryControllerEffect extends OneShotEffect {

    private final int amount;
    private final boolean toUniqueExileZone;
    private final boolean faceDown;

    public ExileCardsFromTopOfLibraryControllerEffect(int amount) {
        this(amount, false);
    }

    public ExileCardsFromTopOfLibraryControllerEffect(int amount, boolean toUniqueExileZone) {
        this(amount, toUniqueExileZone, false);
    }

    public ExileCardsFromTopOfLibraryControllerEffect(int amount, boolean toUniqueExileZone, boolean faceDown) {
        this(amount, toUniqueExileZone, faceDown, false);
    }

    /**
     * @param amount                   number of cards to exile
     * @param toUniqueExileZone        moves the card to a source object dependant
     *                                 unique exile zone, so another effect of the same source object (e.g.
     *                                 Theater of Horrors) can identify the card
     * @param faceDown                 if true, cards are exiled face down
     * @param withFaceDownReminderText if true, add the reminder text for exiling one face down card
     */
    public ExileCardsFromTopOfLibraryControllerEffect(int amount, boolean toUniqueExileZone, boolean faceDown, boolean withFaceDownReminderText) {
        super(Outcome.Exile);
        this.amount = amount;
        this.toUniqueExileZone = toUniqueExileZone;
        this.faceDown = faceDown;

        staticText = "exile the top "
                + ((amount > 1) ? CardUtil.numberToText(amount) + " cards" : "card")
                + " of your library"
                + (faceDown ? " face down" : "")
                + (withFaceDownReminderText ? ". <i>(You can't look at it.)</i>" : "");
    }

    protected ExileCardsFromTopOfLibraryControllerEffect(final ExileCardsFromTopOfLibraryControllerEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.toUniqueExileZone = effect.toUniqueExileZone;
        this.faceDown = effect.faceDown;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        UUID exileZoneId = null;
        String exileZoneName = "";
        if (toUniqueExileZone) {
            MageObject sourceObject = source.getSourceObject(game);
            if (sourceObject == null) {
                return false;
            }
            exileZoneId = CardUtil.getExileZoneId(game, source);
            exileZoneName = CardUtil.createObjectRealtedWindowTitle(source, game, null);
        }

        Set<Card> cards = controller.getLibrary().getTopCards(game, amount);
        if (cards.isEmpty()) {
            return true;
        }

        boolean exiledSuccessfully = false;
        for (Card card : cards) {
            card.setFaceDown(faceDown, game);
            exiledSuccessfully |= controller.moveCardsToExile(card, source, game, !faceDown, exileZoneId, exileZoneName);
            card.setFaceDown(faceDown, game);
        }
        return exiledSuccessfully;
    }

    @Override
    public ExileCardsFromTopOfLibraryControllerEffect copy() {
        return new ExileCardsFromTopOfLibraryControllerEffect(this);
    }
}
