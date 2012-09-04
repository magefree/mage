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
package mage.sets.urzaslegacy;

import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public class BrinkOfMadness extends CardImpl<BrinkOfMadness> {

    public BrinkOfMadness(UUID ownerId) {
        super(ownerId, 50, "Brink of Madness", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.expansionSetCode = "ULG";

        this.color.setBlack(true);

        // At the beginning of your upkeep, if you have no cards in hand, sacrifice Brink of Madness and target opponent discards his or her hand.
        TriggeredAbility ability  = new BeginningOfUpkeepTriggeredAbility(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect(), Constants.TargetController.YOU, false);
        ability.addEffect(new BrinkOfMadnessEffect());
        ability.addTarget(new TargetOpponent());
        CardsInHandCondition contition = new CardsInHandCondition(CardsInHandCondition.CountType.EQUAL_TO, 0);
        this.addAbility(new ConditionalTriggeredAbility(ability, contition, "At the beginning of your upkeep, if you have no cards in hand, sacrifice {this} and target opponent discards his or her hand"));
        
    }

    public BrinkOfMadness(final BrinkOfMadness card) {
        super(card);
    }

    @Override
    public BrinkOfMadness copy() {
        return new BrinkOfMadness(this);
    }
    
    class BrinkOfMadnessEffect extends OneShotEffect<BrinkOfMadnessEffect> {

    public BrinkOfMadnessEffect() {
        super(Constants.Outcome.Benefit);
        this.staticText = "Target player discards his or her hand";
    }

    public BrinkOfMadnessEffect(final BrinkOfMadnessEffect effect) {
        super(effect);
    }

    @Override
    public BrinkOfMadnessEffect copy() {
        return new BrinkOfMadnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Set<Card> cards = player.getHand().getCards(game);
            for (Card card : cards) {
                player.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}
}
