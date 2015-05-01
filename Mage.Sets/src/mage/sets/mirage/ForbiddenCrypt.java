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
package mage.sets.mirage;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Quercitron
 */
public class ForbiddenCrypt extends CardImpl {

    public ForbiddenCrypt(UUID ownerId) {
        super(ownerId, 22, "Forbidden Crypt", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "MIR";

        this.color.setBlack(true);

        // If you would draw a card, return a card from your graveyard to your hand instead. If you can't, you lose the game.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ForbiddenCryptDrawCardReplacementEffect()));
        // If a card would be put into your graveyard from anywhere, exile that card instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ForbiddenCryptPutIntoYourGraveyardReplacementEffect()));
    }

    public ForbiddenCrypt(final ForbiddenCrypt card) {
        super(card);
    }

    @Override
    public ForbiddenCrypt copy() {
        return new ForbiddenCrypt(this);
    }
}

class ForbiddenCryptDrawCardReplacementEffect extends ReplacementEffectImpl {

    public ForbiddenCryptDrawCardReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "If you would draw a card, return a card from your graveyard to your hand instead. If you can't, you lose the game";
    }
    
    public ForbiddenCryptDrawCardReplacementEffect(final ForbiddenCryptDrawCardReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ForbiddenCryptDrawCardReplacementEffect copy() {
        return new ForbiddenCryptDrawCardReplacementEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            boolean cardReturned = false;
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard();
            if (target.canChoose(source.getSourceId(), player.getId(), game)) {
                if (target.choose(Outcome.ReturnToHand, player.getId(), source.getSourceId(), game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        player.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD);
                        cardReturned = true;
                    }
                }
            }
            if (!cardReturned) {
                game.informPlayers(new StringBuilder(player.getName()).append(" can't return a card from graveyard to hand.").toString());
                player.lost(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == EventType.DRAW_CARD && event.getPlayerId().equals(source.getControllerId());
    }
    
}

class ForbiddenCryptPutIntoYourGraveyardReplacementEffect extends ReplacementEffectImpl {

    public ForbiddenCryptPutIntoYourGraveyardReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "If a card would be put into your graveyard from anywhere, exile that card instead";
    }
    
    public ForbiddenCryptPutIntoYourGraveyardReplacementEffect(final ForbiddenCryptPutIntoYourGraveyardReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ForbiddenCryptPutIntoYourGraveyardReplacementEffect copy() {
        return new ForbiddenCryptPutIntoYourGraveyardReplacementEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent != null) {
                    return controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, ((ZoneChangeEvent) event).getFromZone(), true);
                }
            } else {
                Card card = game.getCard(event.getTargetId());
                if (card != null) {
                    return controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, ((ZoneChangeEvent) event).getFromZone(), true);
                }
            }
            return false;
        }
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }   
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getOwnerId().equals(source.getControllerId())) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent == null || !(permanent instanceof PermanentToken)) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
