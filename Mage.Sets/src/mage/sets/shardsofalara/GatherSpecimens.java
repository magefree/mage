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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.abilities.Ability;
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
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class GatherSpecimens extends CardImpl {

    public GatherSpecimens(UUID ownerId) {
        super(ownerId, 45, "Gather Specimens", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}{U}{U}");
        this.expansionSetCode = "ALA";

        // If a creature would enter the battlefield under an opponent's control this turn, it enters the battlefield under your control instead.
        this.getSpellAbility().addEffect(new GatherSpecimensReplacementEffect());
    }

    public GatherSpecimens(final GatherSpecimens card) {
        super(card);
    }

    @Override
    public GatherSpecimens copy() {
        return new GatherSpecimens(this);
    }
}

class GatherSpecimensReplacementEffect extends ReplacementEffectImpl {

    public GatherSpecimensReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.GainControl);
        staticText = "If a creature would enter the battlefield under an opponent's control this turn, it enters the battlefield under your control instead";
    }

    public GatherSpecimensReplacementEffect(final GatherSpecimensReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GatherSpecimensReplacementEffect copy() {
        return new GatherSpecimensReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE || event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone().match(Zone.BATTLEFIELD)) {
            Card card = game.getCard(event.getTargetId());
            if (card.getCardType().contains(CardType.CREATURE)) { // TODO: Bestow Card cast as Enchantment probably not handled correctly
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.CREATE_TOKEN && event.getFlag()) { // flag indicates if it's a creature token
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            event.setPlayerId(controller.getId());
        }
        return false;
    }
}
