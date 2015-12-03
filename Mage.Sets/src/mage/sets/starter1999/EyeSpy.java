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
package mage.sets.starter1999;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public class EyeSpy extends CardImpl {

    public EyeSpy(UUID ownerId) {
        super(ownerId, 38, "Eye Spy", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{U}");
        this.expansionSetCode = "S99";

        // Look at the top card of target player's library. You may put that card into his or her graveyard.
        this.getSpellAbility().addEffect(new EyeSpyEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public EyeSpy(final EyeSpy card) {
        super(card);
    }

    @Override
    public EyeSpy copy() {
        return new EyeSpy(this);
    }
}

class EyeSpyEffect extends OneShotEffect {

    public EyeSpyEffect() {
        super(Outcome.Detriment);
        staticText = "Look at the top card of target player's library. You may put that card into his or her graveyard";
    }

    public EyeSpyEffect(final EyeSpyEffect effect) {
        super(effect);
    }

    @Override
    public EyeSpyEffect copy() {
        return new EyeSpyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller != null && player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                controller.lookAtCards("Eye Spy", cards, game);
                if (controller.chooseUse(outcome, "Do you wish to put card into the player's graveyard?", source, game)) {
                    controller.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                } else {
                    game.informPlayers(controller.getLogName() + " puts the card back on top of the library.");
                }
                return true;
            }
        }
        return false;
    }

}