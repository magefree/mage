package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
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
public class PlayFromTopOfLibraryEffect extends AsThoughEffectImpl {

    private final FilterCard filter;
    private final TargetController targetLibrary;

    private static final FilterCard defaultFilter = new FilterCard("play lands and cast spells");

    /**
     * You may play lands and cast spells from the top of your library
     */
    public PlayFromTopOfLibraryEffect() {
        this(defaultFilter);
    }

    /**
     * You may [play lands and/or cast spells, according to filter] from the top of your library
     */
    public PlayFromTopOfLibraryEffect(FilterCard filter) {
        this(TargetController.YOU, filter);
    }

    public PlayFromTopOfLibraryEffect(TargetController targetLibrary) {
        this(targetLibrary, defaultFilter);
    }

    public PlayFromTopOfLibraryEffect(TargetController targetLibrary, FilterCard filter) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        this.targetLibrary = targetLibrary;

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
        this.staticText = "you may " + filter.getMessage() + " from the top of " + libInfo;

        // verify check: this ability is to allow playing lands or casting spells, not playing a "card"
        if (filter.getMessage().toLowerCase(Locale.ENGLISH).contains("card")) {
            throw new IllegalArgumentException("Wrong code usage or wrong filter text: PlayTheTopCardEffect");
        }
    }

    protected PlayFromTopOfLibraryEffect(final PlayFromTopOfLibraryEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.targetLibrary = effect.targetLibrary;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PlayFromTopOfLibraryEffect copy() {
        return new PlayFromTopOfLibraryEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        // can play lands/spells (must check specific part and allows specific part)

        Card cardToCheck = game.getCard(objectId); // maybe this should be removed and only check SpellAbility characteristics
        if (cardToCheck == null) {
            return false;
        }
        if (affectedAbility instanceof SpellAbility) {
            SpellAbility spell = (SpellAbility) affectedAbility;
            cardToCheck = spell.getCharacteristics(game);
            if (spell.getManaCosts().isEmpty()){
                return false;
            }
        }
        // only permits you to cast
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }

        Player cardOwner = game.getPlayer(cardToCheck.getOwnerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (cardOwner == null || controller == null) {
            return false;
        }

        // main card of spell must be on top of the relevant library
        switch (this.targetLibrary) {
            case YOU: {
                Card topCard = controller.getLibrary().getFromTop(game);
                if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
                    return false;
                }
                break;
            }

            case OPPONENT: { // no current usage
                if (!game.getOpponents(controller.getId()).contains(cardOwner.getId())) {
                    return false;
                }
                Card topCard = cardOwner.getLibrary().getFromTop(game);
                if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
                    return false;
                }
                break;
            }

            case SOURCE_TARGETS: { // only used for Xanathar, Guild Kingpin
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

        // spell characteristics must match filter
        return filter.match(cardToCheck, playerId, source, game);
    }
}
