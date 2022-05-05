package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * @author nantuko, JayDi85
 */
public class PlayTheTopCardEffect extends AsThoughEffectImpl {

    private final FilterCard filter;
    private final TargetController targetLibrary;

    // can play card or can play lands/cast spells, see two modes below
    private final boolean canPlayCardOnly;


    /**
     * Support targets, use TargetController.SOURCE_TARGETS
     */
    public PlayTheTopCardEffect() {
        this(TargetController.YOU);
    }

    public PlayTheTopCardEffect(TargetController targetLibrary) {
        this(targetLibrary, new FilterCard("play lands and cast spells"), false);
    }

    public PlayTheTopCardEffect(TargetController targetLibrary, FilterCard filter, boolean canPlayCardOnly) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        this.targetLibrary = targetLibrary;
        this.canPlayCardOnly = canPlayCardOnly;

        String libInfo;
        switch (this.targetLibrary) {
            case YOU:
                libInfo = "your library";
                break;
            case OPPONENT:
                libInfo = "opponents libraries";
                break;
            case SOURCE_TARGETS:
                libInfo = "target player's library";
                break;
            default:
                throw new IllegalArgumentException("Unknown target library type: " + targetLibrary);
        }
        this.staticText = "You may " + filter.getMessage() + " from the top of " + libInfo;

        // verify check: if you see "card" text in the rules then use card mode
        // (there aren't any real cards after oracle update, but can be added in the future)
        if (this.canPlayCardOnly != filter.getMessage().toLowerCase(Locale.ENGLISH).contains("card")) {
            throw new IllegalArgumentException("Wrong usage of card mode settings");
        }
    }

    public PlayTheTopCardEffect(final PlayTheTopCardEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.targetLibrary = effect.targetLibrary;
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
            // check whole card instead part
            cardToCheck = cardToCheck.getMainCard();
        }

        // must be you
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        Player cardOwner = game.getPlayer(cardToCheck.getOwnerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (cardOwner == null || controller == null) {
            return false;
        }

        // must be your or opponents library
        switch (this.targetLibrary) {
            case YOU: {
                Card topCard = controller.getLibrary().getFromTop(game);
                if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
                    return false;
                }
                break;
            }

            case OPPONENT: {
                if (!game.getOpponents(controller.getId()).contains(cardOwner.getId())) {
                    return false;
                }
                Card topCard = cardOwner.getLibrary().getFromTop(game);
                if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
                    return false;
                }
                break;
            }

            case SOURCE_TARGETS: {
                UUID needCardId = cardToCheck.getMainCard().getId();
                if (CardUtil.getAllSelectedTargets(source, game).stream()
                        .map(game::getPlayer)
                        .filter(Objects::nonNull)
                        .noneMatch(player -> {
                            Card topCard = player.getLibrary().getFromTop(game);
                            return topCard != null && topCard.getId().equals(needCardId);
                        })) {
                    return false;
                }
                break;
            }

            default: {
                return false;
            }
        }

        // can't cast without mana cost
        if (!cardToCheck.isLand(game) && cardToCheck.getManaCost().isEmpty()) {
            return false;
        }

        // must be correct card
        return filter.match(cardToCheck, affectedControllerId, source, game);
    }
}
