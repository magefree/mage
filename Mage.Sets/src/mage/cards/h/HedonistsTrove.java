
package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class HedonistsTrove extends CardImpl {

    public HedonistsTrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");

        // When Hedonist's Trove enters the battlefield, exile all cards from target opponent's graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HedonistsTroveExileEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // You may play land cards exiled by Hedonist's Trove.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HedonistsTrovePlayLandEffect()));

        // You may cast nonland cards exiled with Hedonist's Trove. You can't cast more than one spell this way each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HedonistsTroveCastNonlandCardsEffect()));

    }

    public HedonistsTrove(final HedonistsTrove card) {
        super(card);
    }

    @Override
    public HedonistsTrove copy() {
        return new HedonistsTrove(this);
    }
}

class HedonistsTroveExileEffect extends OneShotEffect {

    public HedonistsTroveExileEffect() {
        super(Outcome.Exile);
        staticText = "exile all cards from target opponent's graveyard";
    }

    @Override
    public HedonistsTroveExileEffect copy() {
        return new HedonistsTroveExileEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && targetPlayer != null && sourceObject != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            List<UUID> graveyard = new ArrayList<>(targetPlayer.getGraveyard());
            for (UUID cardId : graveyard) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
            }
            return true;
        }
        return false;
    }
}

class HedonistsTrovePlayLandEffect extends AsThoughEffectImpl {

    public HedonistsTrovePlayLandEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play land cards exiled with {this}";
    }

    public HedonistsTrovePlayLandEffect(final HedonistsTrovePlayLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HedonistsTrovePlayLandEffect copy() {
        return new HedonistsTrovePlayLandEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            MageObject sourceObject = source.getSourceObject(game);
            if (card != null && card.isLand() && sourceObject != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                if (exileId != null) {
                    ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                    return exileZone != null && exileZone.contains(objectId);
                }
            }
        }
        return false;
    }
}

class HedonistsTroveCastNonlandCardsEffect extends AsThoughEffectImpl {

    private int turnNumber;
    private UUID cardId;

    public HedonistsTroveCastNonlandCardsEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast nonland cards exiled with {this}. You can't cast more than one spell this way each turn";
    }

    public HedonistsTroveCastNonlandCardsEffect(final HedonistsTroveCastNonlandCardsEffect effect) {
        super(effect);
        this.turnNumber = effect.turnNumber;
        this.cardId = effect.cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HedonistsTroveCastNonlandCardsEffect copy() {
        return new HedonistsTroveCastNonlandCardsEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            MageObject sourceObject = source.getSourceObject(game);
            if (card != null && !card.isLand() && sourceObject != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                if (exileId != null) {
                    ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                    if (exileZone != null && exileZone.contains(objectId)) {
                        if (game.getTurnNum() == turnNumber) {
                            if (!exileZone.contains(cardId)) {
                                // last checked card this turn is no longer exiled, so you can't cast another with this effect
                                // TODO: Handle if card was cast/removed from exile with effect from another card.
                                //       If so, this effect could prevent player from casting although he should be able to use it
                                return false;
                            }
                        }
                        this.turnNumber = game.getTurnNum();
                        this.cardId = objectId;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
