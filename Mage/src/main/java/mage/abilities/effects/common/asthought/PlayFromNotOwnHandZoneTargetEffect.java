package mage.abilities.effects.common.asthought;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public class PlayFromNotOwnHandZoneTargetEffect extends AsThoughEffectImpl {

    private final Zone fromZone;
    private final TargetController allowedCaster;
    private final boolean withoutMana;
    private final boolean onlyCastAllowed; // can cast spells, but can't play lands

    public PlayFromNotOwnHandZoneTargetEffect() {
        this(Duration.EndOfTurn);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Duration duration) {
        this(Zone.ALL, TargetController.YOU, duration);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Zone fromZone, Duration duration) {
        this(fromZone, TargetController.YOU, duration);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Zone fromZone, TargetController allowedCaster, Duration duration) {
        this(fromZone, allowedCaster, duration, false);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Zone fromZone, TargetController allowedCaster, Duration duration, boolean withoutMana) {
        this(fromZone, allowedCaster, duration, withoutMana, false);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Zone fromZone, TargetController allowedCaster, Duration duration, boolean withoutMana, boolean onlyCastAllowed) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, withoutMana ? Outcome.PlayForFree : Outcome.PutCardInPlay);
        this.fromZone = fromZone;
        this.allowedCaster = allowedCaster;
        this.withoutMana = withoutMana;
        this.onlyCastAllowed = onlyCastAllowed;
    }

    public PlayFromNotOwnHandZoneTargetEffect(final PlayFromNotOwnHandZoneTargetEffect effect) {
        super(effect);
        this.fromZone = effect.fromZone;
        this.allowedCaster = effect.allowedCaster;
        this.withoutMana = effect.withoutMana;
        this.onlyCastAllowed = effect.onlyCastAllowed;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PlayFromNotOwnHandZoneTargetEffect copy() {
        return new PlayFromNotOwnHandZoneTargetEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return applies(objectId, null, source, game, affectedControllerId);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (affectedAbility == null) {
            // ContinuousEffects.asThough already checks affectedAbility, so that error must never be called here
            // PLAY_FROM_NOT_OWN_HAND_ZONE must applies to affectedAbility only
            throw new IllegalArgumentException("ERROR, can't call applies method on empty affectedAbility");
        }

        // invalid targets
        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.isEmpty()) {
            this.discard();
            return false;
        }

        // invalid zone
        if (!game.getState().getZone(objectId).match(fromZone)) {
            return false;
        }

        // invalid caster
        switch (allowedCaster) {
            case YOU:
                if (playerId != source.getControllerId()) {
                    return false;
                }
                break;
            case OPPONENT:
                if (!game.getOpponents(source.getControllerId()).contains(playerId)) {
                    return false;
                }
                break;
            case OWNER:
                if (playerId != game.getCard(objectId).getOwnerId()) {
                    return false;
                }
                break;
            case ANY:
                break;
        }

        // targets goes to main card all the time
        UUID objectIdToCast = CardUtil.getMainCardId(game, objectId);
        if (!targets.contains(objectIdToCast)) {
            return false;
        }

        // if can't play lands
        if (!affectedAbility.getAbilityType().isPlayCardAbility()
                || onlyCastAllowed && affectedAbility instanceof PlayLandAbility) {
            return false;
        }

        // OK, allow to play
        if (withoutMana) {
            allowCardToPlayWithoutMana(objectId, source, playerId, game);
        }
        return true;
    }

    public static boolean exileAndPlayFromExile(Game game, Ability source, Card card, TargetController allowedCaster,
                                                Duration duration, boolean withoutMana, boolean anyColor, boolean onlyCastAllowed) {
        if (card == null) {
            return true;
        }
        Set<Card> cards = new HashSet<>();
        cards.add(card);
        return exileAndPlayFromExile(game, source, cards, allowedCaster, duration, withoutMana, anyColor, onlyCastAllowed);
    }

    /**
     * Exiles the cards and let the allowed player play them from exile for the given duration
     * Supports:
     *  - cards (use any side)
     *  - permanents (use permanent, not permanent's card)
     *
     * @param game
     * @param source
     * @param cards
     * @param allowedCaster
     * @param duration
     * @param withoutMana
     * @param anyColor
     * @param onlyCastAllowed true for rule "cast that card" and false for rule "play that card"
     * @return
     */
    public static boolean exileAndPlayFromExile(Game game, Ability source, Set<Card> cards, TargetController allowedCaster,
                                                Duration duration, boolean withoutMana, boolean anyColor, boolean onlyCastAllowed) {
        if (cards == null || cards.isEmpty()) {
            return true;
        }
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(
                controller.getId().toString()
                        + "-" + game.getState().getTurnNum()
                        + "-" + sourceObject.getIdName(), game
        );
        String exileName = sourceObject.getIdName() + " free play"
                + (Duration.EndOfTurn.equals(duration) ? " on turn " + game.getState().getTurnNum() : "")
                + " for " + controller.getName();
        if (Duration.EndOfTurn.equals(duration)) {
            game.getExile().createZone(exileId, exileName).setCleanupOnEndTurn(true);
        }
        if (!controller.moveCardsToExile(cards, source, game, true, exileId, exileName)) {
            return false;
        }

        // get real cards (if it was called on permanent instead card, example: Release to the Wind)
        Set<Card> cardsToPlay = cards
                .stream()
                .map(Card::getMainCard)
                .filter(card -> Zone.EXILED.equals(game.getState().getZone(card.getId())))
                .collect(Collectors.toSet());

        ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, allowedCaster, duration, withoutMana, onlyCastAllowed);
        effect.setTargetPointer(new FixedTargets(cardsToPlay, game));
        game.addEffect(effect, source);
        if (anyColor) {
            for (Card card : cardsToPlay) {
                game.addEffect(new YouMaySpendManaAsAnyColorToCastTargetEffect(duration).setTargetPointer(new FixedTarget(card, game)), source);
            }
        }
        return true;
    }
}
