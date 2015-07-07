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
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.common.CreatureWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public class HallowedMoonlight extends CardImpl {

    public HallowedMoonlight(UUID ownerId) {
        super(ownerId, 16, "Hallowed Moonlight", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "ORI";

        // Until end of turn, if a creature would enter the battlefield and it wasn't cast, exile it instead.
        this.getSpellAbility().addEffect(new HallowedMoonlightEffect());
        this.getSpellAbility().addWatcher(new CreatureWasCastWatcher());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public HallowedMoonlight(final HallowedMoonlight card) {
        super(card);
    }

    @Override
    public HallowedMoonlight copy() {
        return new HallowedMoonlight(this);
    }
}

class HallowedMoonlightEffect extends ReplacementEffectImpl {

    public HallowedMoonlightEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "Until end of turn, if a creature would enter the battlefield and it wasn't cast, exile it instead";
    }

    public HallowedMoonlightEffect(final HallowedMoonlightEffect effect) {
        super(effect);
    }

    @Override
    public HallowedMoonlightEffect copy() {
        return new HallowedMoonlightEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
            }
            return true;

        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE || event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CREATE_TOKEN) {
            // TODO: not clear how to check for creature type
            // Tokens: Even though you’re probably casting a spell that makes tokens (Lingering Souls, Raise the Alarm, etc),
            // the tokens themselves are not cast. As such, Hallowed Moonlight will stop ANY kind of creature tokens
            // from entering the battlefield.
            return true;
        }
        if (((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                // TODO: Bestow Card cast as Enchantment probably not handled correctly
                CreatureWasCastWatcher watcher = (CreatureWasCastWatcher) game.getState().getWatchers().get("CreatureWasCast");
                if (watcher != null && !watcher.wasCreatureCastThisTurn(event.getTargetId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
