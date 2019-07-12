
package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class PraetorsGrasp extends CardImpl {

    public PraetorsGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Search target opponent's library for a card and exile it face down. Then that player shuffles their library. You may look at and play that card for as long as it remains exiled.
        this.getSpellAbility().addEffect(new PraetorsGraspEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public PraetorsGrasp(final PraetorsGrasp card) {
        super(card);
    }

    @Override
    public PraetorsGrasp copy() {
        return new PraetorsGrasp(this);
    }
}

class PraetorsGraspEffect extends OneShotEffect {

    public PraetorsGraspEffect() {
        super(Outcome.PlayForFree);
        staticText = "Search target opponent's library for a card and exile it face down. Then that player shuffles their library. You may look at and play that card for as long as it remains exiled";
    }

    public PraetorsGraspEffect(final PraetorsGraspEffect effect) {
        super(effect);
    }

    @Override
    public PraetorsGraspEffect copy() {
        return new PraetorsGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && opponent != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary();
            if (controller.searchLibrary(target, source, game, opponent.getId())) {
                UUID targetId = target.getFirstTarget();
                Card card = opponent.getLibrary().getCard(targetId, game);
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                if (card != null && exileId != null) {
                    game.informPlayers(controller.getLogName() + " moves the searched card face down to exile");
                    card.moveToExile(exileId, sourceObject.getIdName(), source.getSourceId(), game);
                    card.setFaceDown(true, game);
                    game.addEffect(new PraetorsGraspPlayEffect(card.getId()), source);
                    game.addEffect(new PraetorsGraspRevealEffect(card.getId()), source);
                }
            }
            opponent.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}

class PraetorsGraspPlayEffect extends AsThoughEffectImpl {

    private UUID cardId;

    public PraetorsGraspPlayEffect(UUID cardId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.cardId = cardId;
        staticText = "You may look at and play that card for as long as it remains exiled";
    }

    public PraetorsGraspPlayEffect(final PraetorsGraspPlayEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PraetorsGraspPlayEffect copy() {
        return new PraetorsGraspPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(cardId) && affectedControllerId.equals(source.getControllerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileId != null && controller != null) {
                ExileZone exileZone = game.getExile().getExileZone(exileId);
                if (exileZone != null && exileZone.contains(cardId)) {
                    return true;
                }
            }
        }
        return false;
    }

}

class PraetorsGraspRevealEffect extends AsThoughEffectImpl {

    private final UUID cardId;

    public PraetorsGraspRevealEffect(UUID cardId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.cardId = cardId;
        staticText = "You may look at and play that card for as long as it remains exiled";
    }

    public PraetorsGraspRevealEffect(final PraetorsGraspRevealEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PraetorsGraspRevealEffect copy() {
        return new PraetorsGraspRevealEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(cardId) && affectedControllerId.equals(source.getControllerId())) {
            MageObject sourceObject = source.getSourceObject(game);
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileId != null && sourceObject != null) {
                ExileZone exileZone = game.getExile().getExileZone(exileId);
                if (exileZone != null && exileZone.contains(cardId)) {
                    Player controller = game.getPlayer(source.getControllerId());
                    Card card = game.getCard(cardId);
                    if (controller != null && card != null && game.getState().getZone(cardId) == Zone.EXILED) {
                        return true;
                    }
                } else {
                    discard();
                }
            }
        }
        return false;
    }

}
