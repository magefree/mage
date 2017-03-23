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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author emerald000 / HCrescent
 */
public class SharedFate extends CardImpl {

    public SharedFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{U}");

        // If a player would draw a card, that player exiles the top card of one of his or her opponents' libraries face down instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SharedFateReplacementEffect()));

        // Each player may look at and play cards he or she exiled with Shared Fate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SharedFatePlayEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SharedFateLookEffect()));
    }

    public SharedFate(final SharedFate card) {
        super(card);
    }

    @Override
    public SharedFate copy() {
        return new SharedFate(this);
    }
}

class SharedFateReplacementEffect extends ReplacementEffectImpl {

    SharedFateReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "If a player would draw a card, that player exiles the top card of one of his or her opponents' libraries face down instead";
    }

    SharedFateReplacementEffect(final SharedFateReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SharedFateReplacementEffect copy() {
        return new SharedFateReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player playerToDraw = game.getPlayer(event.getPlayerId());
        if (playerToDraw != null && sourcePermanent != null) {
            TargetOpponent target = new TargetOpponent(true);
            if (playerToDraw.choose(Outcome.DrawCard, target, source.getSourceId(), game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    Card card = chosenPlayer.getLibrary().getFromTop(game);
                    if (card != null) {
                        playerToDraw.moveCardsToExile(
                                card,
                                source,
                                game,
                                false,
                                CardUtil.getExileZoneId(source.getSourceId().toString() + sourcePermanent.getZoneChangeCounter(game) + playerToDraw.getId().toString(), game),
                                "Shared Fate (" + playerToDraw.getName() + ')');
                        card.setFaceDown(true, game);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

class SharedFatePlayEffect extends AsThoughEffectImpl {

    SharedFatePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each player may look at and play cards he or she exiled with {this}";
    }

    SharedFatePlayEffect(final SharedFatePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SharedFatePlayEffect copy() {
        return new SharedFatePlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
       if (game.getState().getZone(objectId) == Zone.EXILED) {
            Player player = game.getPlayer(affectedControllerId);
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString() + sourcePermanent.getZoneChangeCounter(game) + affectedControllerId.toString(), game);
            if (exileId != null) {
                ExileZone exileZone = game.getExile().getExileZone(exileId);
                if (exileZone != null && exileZone.contains(objectId)) {
                    if (player.chooseUse(outcome, "Play " + game.getCard(objectId).getIdName() + '?', source, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class SharedFateLookEffect extends AsThoughEffectImpl {

    public SharedFateLookEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each player may look at the cards exiled with {this}";
    }

    public SharedFateLookEffect(final SharedFateLookEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SharedFateLookEffect copy() {
        return new SharedFateLookEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
       if (game.getState().getZone(objectId) == Zone.EXILED) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString() + sourcePermanent.getZoneChangeCounter(game) + affectedControllerId.toString(), game);
            if (exileId != null) {
                ExileZone exileZone = game.getExile().getExileZone(exileId);
                if (exileZone != null && exileZone.contains(objectId)) {
                    Card card = game.getCard(objectId);
                    if (card != null && game.getState().getZone(objectId) == Zone.EXILED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
