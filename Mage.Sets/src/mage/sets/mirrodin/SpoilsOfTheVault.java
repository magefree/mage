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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Plopman
 */
public class SpoilsOfTheVault extends CardImpl<SpoilsOfTheVault> {

    public SpoilsOfTheVault(UUID ownerId) {
        super(ownerId, 78, "Spoils of the Vault", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "MRD";

        this.color.setBlack(true);

        // Name a card. Reveal cards from the top of your library until you reveal the named card, then put that card into your hand. Exile all other cards revealed this way, and you lose 1 life for each of the exiled cards.
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


class SpoilsOfTheVaultEffect extends OneShotEffect<SpoilsOfTheVaultEffect> {

    public SpoilsOfTheVaultEffect() {
        super(Constants.Outcome.Damage);
        this.staticText = "Name a card. Reveal cards from the top of your library until you reveal the named card, then put that card into your hand. Exile all other cards revealed this way, and you lose 1 life for each of the exiled cards";
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
        String cardName;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        else{
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNames());
            cardChoice.clearChoice();
            while (!controller.choose(Constants.Outcome.Detriment, cardChoice, game)) {
                game.debugMessage("player canceled choosing name. retrying.");
            }
            cardName = cardChoice.getChoice();
            game.informPlayers("Spoils of the Vault, named card: [" + cardName + "]");
        }        
        Card sourceCard = game.getCard(source.getSourceId());
        
        if (cardName == null || sourceCard == null) {
            return false;
        }
        
        Cards cards = new CardsImpl(Constants.Zone.PICK);
        while (controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                if(card.getName().equals(cardName)){
                    card.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
                    break;
                }
                else{
                    card.moveToExile(null, sourceCard.getName(), source.getId(), game);
                }
            }
            else{
                break;
            }
        }
        
        controller.revealCards(sourceCard.getName(), cards, game);
        controller.loseLife(cards.size(), game);
        
        return true;
    }
}
