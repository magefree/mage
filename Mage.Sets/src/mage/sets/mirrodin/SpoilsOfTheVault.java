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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.NameACardEffect;
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

/**
 *
 * @author Plopman
 */
public class SpoilsOfTheVault extends CardImpl {

    public SpoilsOfTheVault(UUID ownerId) {
        super(ownerId, 78, "Spoils of the Vault", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "MRD";


        // Name a card. Reveal cards from the top of your library until you reveal the named card, then put that card into your hand. Exile all other cards revealed this way, and you lose 1 life for each of the exiled cards.
        this.getSpellAbility().addEffect(new NameACardEffect(NameACardEffect.TypeOfName.ALL));
        this.getSpellAbility().addEffect(new SpoilsOfTheVaultEffect());
    }

    public SpoilsOfTheVault(final SpoilsOfTheVault card) {
        super(card);
    }

    @Override
    public SpoilsOfTheVault copy() {
        return new SpoilsOfTheVault(this);
    }
}


class SpoilsOfTheVaultEffect extends OneShotEffect {

    public SpoilsOfTheVaultEffect() {
        super(Outcome.Damage);
        this.staticText = "Reveal cards from the top of your library until you reveal the named card, then put that card into your hand. Exile all other cards revealed this way, and you lose 1 life for each of the exiled cards";
    }

    public SpoilsOfTheVaultEffect(final SpoilsOfTheVaultEffect effect) {
        super(effect);
    }

    @Override
    public SpoilsOfTheVaultEffect copy() {
        return new SpoilsOfTheVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        if (sourceObject == null || controller == null || cardName == null || cardName.isEmpty()) {
            return false;
        }
               
        Cards cards = new CardsImpl();
        while (controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                if(card.getName().equals(cardName)){
                    controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                    break;
                }
                else{
                    controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                }
            }
            else{
                break;
            }
        }
        
        controller.revealCards(sourceObject.getName(), cards, game);
        controller.loseLife(cards.size(), game);
        
        return true;
    }
}
