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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

/**
 *
 * @author fireshoes
 */

public class AlhammarretsArchive extends CardImpl {

    public AlhammarretsArchive(UUID ownerId) {
        super(ownerId, 221, "Alhammarret's Archive", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "ORI";
        this.supertype.add("Legendary");

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AlhammarretsArchiveEffect()));
        
        // If you draw a card except the first one you draw in each of your draw steps, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AlhammarretsArchiveReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());
    }

    public AlhammarretsArchive(final AlhammarretsArchive card) {
        super(card);
    }

    @Override
    public AlhammarretsArchive copy() {
        return new AlhammarretsArchive(this);
    }
}

class AlhammarretsArchiveEffect extends ReplacementEffectImpl {

    public AlhammarretsArchiveEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would gain life, you gain twice that much life instead";
    }

    public AlhammarretsArchiveEffect(final AlhammarretsArchiveEffect effect) {
        super(effect);
    }

    @Override
    public AlhammarretsArchiveEffect copy() {
        return new AlhammarretsArchiveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.GAIN_LIFE);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId()) && (source.getControllerId() != null);
    }
}

class AlhammarretsArchiveReplacementEffect extends ReplacementEffectImpl {

    public AlhammarretsArchiveReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you draw a card except the first one you draw in each of your draw steps, draw two cards instead";
    }

    public AlhammarretsArchiveReplacementEffect(final AlhammarretsArchiveReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AlhammarretsArchiveReplacementEffect copy() {
        return new AlhammarretsArchiveReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(2, game, event.getAppliedEffects());
        }
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }   

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            if (game.getActivePlayerId().equals(event.getPlayerId())) {
                CardsDrawnDuringDrawStepWatcher watcher = (CardsDrawnDuringDrawStepWatcher) game.getState().getWatchers().get("CardsDrawnDuringDrawStep");
                if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }
}