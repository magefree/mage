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
 * @author raphael-schulz
 * Implementation taken from PlayTheTopCardEffect and adjusted accordingly
 */
public class PlayTheTopCardTargetEffect extends AsThoughEffectImpl  {

    private final FilterCard filter;

    // can play card or can play lands/cast spells, see two modes below
    private final boolean canPlayCardOnly;


    public PlayTheTopCardTargetEffect() {
        this(new FilterCard("play lands and cast spells"), false);
    }


    public PlayTheTopCardTargetEffect(FilterCard filter, boolean canPlayCardOnly) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.filter = filter;
        this.canPlayCardOnly = canPlayCardOnly;
        this.staticText = "You may " + filter.getMessage() + " from the top of target player's library";

        // verify check: if you see "card" text in the rules then use card mode
        // (there aren't any real cards after oracle update, but can be added in the future)
        if (this.canPlayCardOnly != filter.getMessage().toLowerCase(Locale.ENGLISH).contains("card")) {
            throw new IllegalArgumentException("Wrong usage of card mode settings");
        }
    }

    public PlayTheTopCardTargetEffect(final PlayTheTopCardTargetEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.canPlayCardOnly = effect.canPlayCardOnly;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PlayTheTopCardTargetEffect copy() {
        return new PlayTheTopCardTargetEffect(this);
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
            // check whole card instead part
            cardToCheck = cardToCheck.getMainCard();
        }

        // must be you
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        // must be target opponents card
        Player player = game.getPlayer(cardToCheck.getOwnerId());
        if (player == null || !player.getId().equals(source.getFirstTarget())) {
            return false;
        }

        // must be from target opponents library
        Card topCard = game.getPlayer(source.getFirstTarget()).getLibrary().getFromTop(game);
        if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
            return false;
        }

        // can't cast without mana cost
        if (!cardToCheck.isLand(game) && cardToCheck.getManaCost().isEmpty()) {
            return false;
        }

        // must be correct card
        return filter.match(cardToCheck, source.getSourceId(), affectedControllerId, game);
    }
}
