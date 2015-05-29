/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.dragonsoftarkir;

import java.util.ArrayList;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class HedonistsTrove extends CardImpl {

    public HedonistsTrove(UUID ownerId) {
        super(ownerId, 106, "Hedonist's Trove", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");
        this.expansionSetCode = "DTK";

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
            ArrayList<UUID> graveyard = new ArrayList<>(targetPlayer.getGraveyard());
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
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play land cards exiled by {this}";
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
        Card card = game.getCard(objectId);
        MageObject sourceObject = source.getSourceObject(game);
        if (card != null && card.getCardType().contains(CardType.LAND) && sourceObject != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileId != null) {
                ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                return exileZone != null && exileZone.contains(objectId);
            }
        }
        return false;
    }
}

class HedonistsTroveCastNonlandCardsEffect extends AsThoughEffectImpl {

    private int turnNumber;
    private UUID cardId;
    
    public HedonistsTroveCastNonlandCardsEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
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
        Card card = game.getCard(objectId);
        MageObject sourceObject = source.getSourceObject(game);
        if (card != null && !card.getCardType().contains(CardType.LAND) && sourceObject != null) {
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
        return false;
    }
}
