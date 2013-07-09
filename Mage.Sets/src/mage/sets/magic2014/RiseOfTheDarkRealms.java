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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class RiseOfTheDarkRealms extends CardImpl<RiseOfTheDarkRealms> {

    public RiseOfTheDarkRealms(UUID ownerId) {
        super(ownerId, 111, "Rise of the Dark Realms", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{7}{B}{B}");
        this.expansionSetCode = "M14";

        this.color.setBlack(true);

        // Put all creature cards from all graveyards onto the battlefield under your control.
        this.getSpellAbility().addEffect(new RiseOfTheDarkRealmsEffect());
    }

    public RiseOfTheDarkRealms(final RiseOfTheDarkRealms card) {
        super(card);
    }

    @Override
    public RiseOfTheDarkRealms copy() {
        return new RiseOfTheDarkRealms(this);
    }
}

class RiseOfTheDarkRealmsEffect extends OneShotEffect<RiseOfTheDarkRealmsEffect> {

    public RiseOfTheDarkRealmsEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put all creature cards from all graveyards onto the battlefield under your control";
    }

    public RiseOfTheDarkRealmsEffect(final RiseOfTheDarkRealmsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        for (UUID playerId: controller.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Card card: player.getGraveyard().getCards(game)) {
                    if (card.getCardType().contains(CardType.CREATURE)) {
                        card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), source.getControllerId());
                    }
                }
            }
        }
        return true;
    }

    @Override
    public RiseOfTheDarkRealmsEffect copy() {
        return new RiseOfTheDarkRealmsEffect(this);
    }

}
