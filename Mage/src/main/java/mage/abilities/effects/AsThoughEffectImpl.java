package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;
import mage.cards.SplitCard;
import mage.cards.SplitCardHalf;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class AsThoughEffectImpl extends ContinuousEffectImpl implements AsThoughEffect {

    protected AsThoughEffectType type;
    boolean consumable;

    public AsThoughEffectImpl(AsThoughEffectType type, Duration duration, Outcome outcome) {
        this(type, duration, outcome, false);
    }
    
    public AsThoughEffectImpl(AsThoughEffectType type, Duration duration, Outcome outcome, boolean consumable) {
        super(duration, outcome);
        this.type = type;
        this.effectType = EffectType.ASTHOUGH;
        this.consumable = consumable;
    }

    public AsThoughEffectImpl(final AsThoughEffectImpl effect) {
        super(effect);
        this.type = effect.type;
        this.consumable = effect.consumable;
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        // affectedControllerId = player to check
        if (getAsThoughEffectType().equals(AsThoughEffectType.LOOK_AT_FACE_DOWN)) {
            return applies(objectId, source, playerId, game);
        } else {
            return applies(objectId, source, playerId, game);
        }
    }

    @Override
    public AsThoughEffectType getAsThoughEffectType() {
        return type;
    }

    /**
     * Helper to check that affectedAbility is compatible for alternative cast
     * modifications by setCastSourceIdWithAlternateMana
     *
     * @param cardToCheck
     * @param affectedAbilityToCheck
     * @param playerToCheck
     * @param source
     * @return
     */
    public boolean isAbilityAppliedForAlternateCast(Card cardToCheck, Ability affectedAbilityToCheck, UUID playerToCheck, Ability source) {
        return cardToCheck != null
                && playerToCheck.equals(source.getControllerId())
                && cardToCheck.isOwnedBy(source.getControllerId())
                && affectedAbilityToCheck instanceof ActivatedAbility
                && (affectedAbilityToCheck.getAbilityType() == AbilityType.SPELL
                || affectedAbilityToCheck.getAbilityType() == AbilityType.PLAY_LAND);
    }

    /**
     * Internal method to do the neccessary to allow the card from objectId to be cast or played (if it's a land) without paying any mana.
     * Additional costs (like sacrificing or discarding) have still to be payed.
     * Checks if the card is of the correct type or in the correct zone have to be done before.
     * 
     * @param objectId sourceId of the card to play
     * @param source source ability that allows this effect
     * @param affectedControllerId player allowed to play the card
     * @param game
     * @return 
     */
    protected boolean allowCardToPlayWithoutMana(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Player player = game.getPlayer(affectedControllerId);
        Card card = game.getCard(objectId);
        if (card == null || player == null) {
            return false;
        }
        if (!card.isLand()) {
            if (card instanceof SplitCard) {
                SplitCardHalf leftCard = ((SplitCard) card).getLeftHalfCard();
                player.setCastSourceIdWithAlternateMana(leftCard.getId(), null, leftCard.getSpellAbility().getCosts());
                SplitCardHalf rightCard = ((SplitCard) card).getRightHalfCard();
                player.setCastSourceIdWithAlternateMana(rightCard.getId(), null, rightCard.getSpellAbility().getCosts());
            } else {
                player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
            }
        }
        return true;
    }

    @Override
    public boolean isConsumable() {
        return consumable;
    }

}
