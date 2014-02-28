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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 *
 */
public class HideawayPlayEffect extends OneShotEffect<HideawayPlayEffect> {

    public HideawayPlayEffect() {
        super(Outcome.Benefit);
        staticText = "You may play the exiled card without paying its mana cost";
    }

    public HideawayPlayEffect(final HideawayPlayEffect effect) {
        super(effect);
    }

    @Override
    public HideawayPlayEffect copy() {
        return new HideawayPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (zone ==null || zone.isEmpty()) {
            return false;
        }
        Card card = zone.getCards(game).iterator().next();
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            if (card.getCardType().contains(CardType.LAND)) {
                // If the revealed card is a land, you can play it only if it's your turn and you haven't yet played a land this turn.
                if (game.getActivePlayerId().equals(source.getControllerId()) && controller.canPlayLand()) {
                    if (controller.chooseUse(Outcome.Benefit, new StringBuilder("Play ").append(card.getName()).append(" from Exile?").toString(), game)) {
                        return controller.playLand(card, game);
                    }
                } else {
                    game.informPlayer(controller, "You're not able to play the land now due to regular restrictions.");
                }
            } else {
                // The land's last ability allows you to play the removed card as part of the resolution of that ability.
                // Timing restrictions based on the card's type are ignored (for instance, if it's a creature or sorcery).
                // Other play restrictions are not (such as "Play [this card] only during combat").
                if (controller.chooseUse(Outcome.Benefit, new StringBuilder("Cast ").append(card.getName()).append(" without paying it's mana cost?").toString(), game)) {
                    return controller.cast(card.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}

