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
package mage.sets.innistrad;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author nantuko
 */
public class CreepingRenaissance extends CardImpl<CreepingRenaissance> {

    public CreepingRenaissance(UUID ownerId) {
        super(ownerId, 174, "Creeping Renaissance", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        this.expansionSetCode = "ISD";

        this.color.setGreen(true);

        // Choose a permanent type. Return all cards of the chosen type from your graveyard to your hand.
        this.getSpellAbility().addEffect(new CreepingRenaissanceEffect());

        // Flashback {5}{G}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{5}{G}{G}"), Constants.TimingRule.SORCERY));
    }

    public CreepingRenaissance(final CreepingRenaissance card) {
        super(card);
    }

    @Override
    public CreepingRenaissance copy() {
        return new CreepingRenaissance(this);
    }
}

class CreepingRenaissanceEffect extends OneShotEffect<CreepingRenaissanceEffect> {

    public CreepingRenaissanceEffect() {
        super(Constants.Outcome.Detriment);
        staticText = "Choose a permanent type. Return all cards of the chosen type from your graveyard to your hand";
    }
    
    public CreepingRenaissanceEffect(final CreepingRenaissanceEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
		Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice typeChoice = new ChoiceImpl();
            typeChoice.setMessage("Choose permanent type");

            typeChoice.getChoices().add(CardType.ARTIFACT.toString());
            typeChoice.getChoices().add(CardType.CREATURE.toString());
            typeChoice.getChoices().add(CardType.ENCHANTMENT.toString());
            typeChoice.getChoices().add(CardType.LAND.toString());
            typeChoice.getChoices().add(CardType.PLANESWALKER.toString());

            while (!controller.choose(Constants.Outcome.ReturnToHand, typeChoice, game));

            String typeName = typeChoice.getChoice();
            CardType chosenType = null;
            for (CardType cardType : CardType.values()) {
                if (cardType.toString().equals(typeName)) {
                    chosenType = cardType;
                }
            }
            if (chosenType != null) {
                for (Card card : controller.getGraveyard().getCards(game)) {
                    if (card.getCardType().contains(chosenType)) {
                        card.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, false);
                    }
                }
                return true;
            }
        }        
        return false;
    }

    @Override
    public CreepingRenaissanceEffect copy() {
        return new CreepingRenaissanceEffect(this);
    }
    
}
