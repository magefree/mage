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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */

//    http://www.wizards.com/magic/magazine/article.aspx?x=mtg/faq/rtr
//
//    While Rest in Peace is on the battlefield, abilities that trigger whenever a
//    creature dies won't trigger because cards and tokens never reach a player's graveyard.
//
//    If Rest in Peace is destroyed by a spell, Rest in Peace will be exiled and
//    then the spell will be put into its owner's graveyard.
//
//    If a card is discarded while Rest in Peace is on the battlefield, abilities
//    that function when a card is discarded (such as madness) still work, even
//    though that card never reaches a graveyard. In addition, spells or abilities
//    that check the characteristics of a discarded card (such as Chandra Ablaze's
//    first ability) can find that card in exile.
//

public class RestInPeace extends CardImpl<RestInPeace> {

    public RestInPeace(UUID ownerId) {
        super(ownerId, 18, "Rest in Peace", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "RTR";

        this.color.setWhite(true);

        // When Rest in Peace enters the battlefield, exile all cards from all graveyards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RestInPeaceExileAllEffect()));

        // If a card or token would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RestInPeaceReplacementEffect()));
    }

    public RestInPeace(final RestInPeace card) {
        super(card);
    }

    @Override
    public RestInPeace copy() {
        return new RestInPeace(this);
    }
}

class RestInPeaceExileAllEffect extends OneShotEffect<RestInPeaceExileAllEffect> {

    public RestInPeaceExileAllEffect() {
        super(Outcome.Detriment);
        staticText = "exile all cards from all graveyards";
    }

    public RestInPeaceExileAllEffect(final RestInPeaceExileAllEffect effect) {
        super(effect);
    }

    @Override
    public RestInPeaceExileAllEffect copy() {
        return new RestInPeaceExileAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID cid : player.getGraveyard().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source.getId(), game);
                    }
                }

            }
        }
        return true;
    }
}

class RestInPeaceReplacementEffect extends ReplacementEffectImpl<RestInPeaceReplacementEffect> {

    public RestInPeaceReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Exile);
        staticText = "If a card or token would be put into a graveyard from anywhere, exile it instead";
    }

    public RestInPeaceReplacementEffect(final RestInPeaceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public RestInPeaceReplacementEffect copy() {
        return new RestInPeaceReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent)event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = ((ZoneChangeEvent)event).getTarget();
            if (permanent != null) {
                return permanent.moveToExile(null, "", source.getId(), game);
            }
        }
        else {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                return card.moveToExile(null, "", source.getId(), game);
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone() == Zone.GRAVEYARD) {
            return true;
        }
        return false;
    }

}