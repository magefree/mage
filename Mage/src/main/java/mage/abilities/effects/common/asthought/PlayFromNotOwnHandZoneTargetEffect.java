package mage.abilities.effects.common.asthought;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class PlayFromNotOwnHandZoneTargetEffect extends AsThoughEffectImpl {

    private final Zone fromZone;
    private final TargetController allowedCaster;
    private final boolean withoutMana;

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
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, withoutMana ? Outcome.PlayForFree : Outcome.PutCardInPlay);
        this.fromZone = fromZone;
        this.allowedCaster = allowedCaster;
        this.withoutMana = withoutMana;
    }

    public PlayFromNotOwnHandZoneTargetEffect(final PlayFromNotOwnHandZoneTargetEffect effect) {
        super(effect);
        this.fromZone = effect.fromZone;
        this.allowedCaster = effect.allowedCaster;
        this.withoutMana = effect.withoutMana;
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

        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.isEmpty()) {
            this.discard();
            return false;
        }
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
            case ANY:
                break;
        }
        UUID objectIdToCast = CardUtil.getMainCardId(game, objectId);
        if (targets.contains(objectIdToCast)
                && playerId.equals(source.getControllerId())
                && game.getState().getZone(objectId).match(fromZone)) {
            if (withoutMana) {
                if (affectedAbility != null) {
                    objectIdToCast = affectedAbility.getSourceId();                    
                }
                return allowCardToPlayWithoutMana(objectIdToCast, source, playerId, game);
            }
            return true;
        }
        return false;
    }
    
    public static boolean exileAndPlayFromExile(Game game, Ability source, Card card, TargetController allowedCaster, Duration duration, boolean withoutMana) {
        if (card == null) {
            return true;
        }
        Set<Card> cards = new HashSet<>();
        cards.add(card);
        return exileAndPlayFromExile(game, source, cards, allowedCaster, duration, withoutMana);
    }    
    /**
     * Exiles the cards and let the allowed player play them from exile for the given duration
     * 
     * @param game
     * @param source
     * @param cards
     * @param allowedCaster
     * @param duration
     * @param withoutMana
     * @return 
     */
    public static boolean exileAndPlayFromExile(Game game, Ability source, Set<Card> cards, TargetController allowedCaster, Duration duration, boolean withoutMana) {
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
                + (Duration.EndOfTurn.equals(duration) ? " on turn " + game.getState().getTurnNum():"") 
                + " for " + controller.getName();
        if (Duration.EndOfTurn.equals(duration)) {
            game.getExile().createZone(exileId, exileName).setCleanupOnEndTurn(true);
        }        
        if (!controller.moveCardsToExile(cards, source, game, true, exileId, exileName)) {
            return false;
        }
        ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, allowedCaster, duration, withoutMana);
        effect.setTargetPointer(new FixedTargets(cards, game));
        game.addEffect(effect, source);
        return true;
    }
}
