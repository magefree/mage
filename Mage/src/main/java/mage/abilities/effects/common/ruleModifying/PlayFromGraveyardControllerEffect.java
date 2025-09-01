package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author JayDi85, xenohedron
 */
public class PlayFromGraveyardControllerEffect extends AsThoughEffectImpl {

    private static final FilterCard filterPlayCards = new FilterCard("cards");
    private static final FilterCard filterPlayLands = new FilterLandCard("lands");
    private static final FilterCard filterPlayCast = new FilterCard("play lands and cast spells");

    private final FilterCard filter;

    /**
     * You may play cards from your graveyard.
     */
    public static PlayFromGraveyardControllerEffect playCards() {
        return new PlayFromGraveyardControllerEffect(filterPlayCards);
    }

    /**
     * You may play lands from your graveyard.
     */
    public static PlayFromGraveyardControllerEffect playLands() {
        return new PlayFromGraveyardControllerEffect(filterPlayLands);
    }

    /**
     * You may play lands and cast spells from your graveyard.
     */
    public static PlayFromGraveyardControllerEffect playLandsAndCastSpells(Duration duration) {
        return new PlayFromGraveyardControllerEffect(filterPlayCast, duration);
    }

    /**
     * You may [play/cast xxx] from your graveyard.
     */
    public PlayFromGraveyardControllerEffect(FilterCard filter) {
        this(filter, Duration.WhileOnBattlefield);
    }

    /**
     * [Until duration,] you may [play/cast xxx] from your graveyard.
     */
    public PlayFromGraveyardControllerEffect(FilterCard filter, Duration duration) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        this.filter = filter;
        String filterMessage = filter.getMessage();
        if (!filterMessage.startsWith("play ") && !filterMessage.startsWith("cast")) {
            if (filterMessage.contains("cards") || filterMessage.contains("lands")) {
                filterMessage = "play " + filterMessage;
            } else {
                filterMessage = "cast " + filterMessage;
            }
        }
        String durationString = duration.toString();
        if (!durationString.isEmpty()) {
            durationString += ", ";
        }
        this.staticText = durationString + "you may " + filterMessage + " from your graveyard";
    }

    protected PlayFromGraveyardControllerEffect(final PlayFromGraveyardControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PlayFromGraveyardControllerEffect copy() {
        return new PlayFromGraveyardControllerEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        // current card's part
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null) {
            return false;
        }

        // must be you
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }

        // must be your card
        Player player = game.getPlayer(cardToCheck.getOwnerId());
        if (player == null || !player.getId().equals(playerId)) {
            return false;
        }

        // must be from your graveyard
        UUID needCardId = cardToCheck.getMainCard().getId();
        if (player.getGraveyard().getCards(game).stream().noneMatch(c -> c.getId().equals(needCardId))) {
            return false;
        }

        // can't cast without mana cost
        if (!cardToCheck.isLand(game) && cardToCheck.getManaCost().isEmpty()) {
            return false;
        }
        if (affectedAbility instanceof SpellAbility) {
            cardToCheck = ((SpellAbility) affectedAbility).getCharacteristics(game);
        }
        // must be correct card
        return filter.match(cardToCheck, playerId, source, game);
    }
}
