package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

import java.util.Locale;
import java.util.UUID;

/**
 * @author nantuko, JayDi85
 */
public class PlayTheTopCardEffect extends AsThoughEffectImpl {

    private final FilterCard filter;

    // can play card or can play lands/cast spells, see two modes below
    private final boolean canPlayCardOnly;


    public PlayTheTopCardEffect() {
        this(new FilterCard("play lands and cast spells"), false);
    }

    public PlayTheTopCardEffect(FilterCard filter, boolean canPlayCardOnly) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        this.canPlayCardOnly = canPlayCardOnly;
        this.staticText = "You may " + filter.getMessage() + " from the top of your library";

        // verify check: if you see "card" text in the rules then use card mode
        // (there aren't any real cards after oracle update, but can be added in the future)
        if (this.canPlayCardOnly != filter.getMessage().toLowerCase(Locale.ENGLISH).contains("card")) {
            throw new IllegalArgumentException("Wrong usage of card mode settings");
        }
    }

    public PlayTheTopCardEffect(final PlayTheTopCardEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.canPlayCardOnly = effect.canPlayCardOnly;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PlayTheTopCardEffect copy() {
        return new PlayTheTopCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        // main card and all parts are checks in different calls.
        // two modes:
        // * can play cards (must check main card and allows any parts)
        // * can play lands/spells (must check specific part and allows specific part)

        // current card's part
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null) {
            return false;
        }

        if (this.canPlayCardOnly) {
            // check whole card intead part
            cardToCheck = cardToCheck.getMainCard();
        }

        // must be you
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        // must be your card
        Player player = game.getPlayer(cardToCheck.getOwnerId());
        if (player == null) {
            return false;
        }

        // must be from your library
        Card topCard = player.getLibrary().getFromTop(game);
        if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
            return false;
        }

        // can't cast without mana cost
        if (!cardToCheck.isLand() && cardToCheck.getManaCost().isEmpty()) {
            return false;
        }

        // must be correct card
        return filter.match(cardToCheck, source.getSourceId(), affectedControllerId, game);
    }
}
