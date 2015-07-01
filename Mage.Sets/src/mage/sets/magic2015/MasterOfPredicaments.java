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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class MasterOfPredicaments extends CardImpl {

    public MasterOfPredicaments(UUID ownerId) {
        super(ownerId, 67, "Master of Predicaments", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "M15";
        this.subtype.add("Sphinx");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Master of Predicaments deals combat damage to a player, choose a card in your hand.  That player guesses whether the card's converted mana cost is greater than 4.  If the player guessed wrong, you may cast the card without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MasterOfPredicamentsEffect(), false, true));
    }

    public MasterOfPredicaments(final MasterOfPredicaments card) {
        super(card);
    }

    @Override
    public MasterOfPredicaments copy() {
        return new MasterOfPredicaments(this);
    }
}

class MasterOfPredicamentsEffect extends OneShotEffect {

    public MasterOfPredicamentsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "choose a card in your hand. That player guesses whether the card's converted mana cost is greater than 4. If the player guessed wrong, you may cast the card without paying its mana cost";
    }

    public MasterOfPredicamentsEffect(final MasterOfPredicamentsEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfPredicamentsEffect copy() {
        return new MasterOfPredicamentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getHand().size() > 0) {
                Card cardFromHand = null;
                if (controller.getHand().size() > 1) {
                    TargetCard target = new TargetCardInHand(new FilterCard());
                    if (controller.choose(outcome, controller.getHand(), target, game)) {
                        cardFromHand = game.getCard(target.getFirstTarget());
                    }
                } else {
                    cardFromHand = controller.getHand().getRandom(game);
                }
                if (cardFromHand == null) {
                    return false;
                }
                Player attackedPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (attackedPlayer == null) {
                    return false;
                }
                boolean guessWrong;
                if (attackedPlayer.chooseUse(Outcome.Detriment, "Is the chosen card's converted mana cost greater than 4?", source, game)) {
                    game.informPlayers(attackedPlayer.getLogName() + " guessed that the chosen card's converted mana cost is greater than 4");
                    guessWrong = cardFromHand.getManaCost().convertedManaCost() <= 4;
                } else {
                    game.informPlayers(attackedPlayer.getLogName() + " guessed that the chosen card's converted mana cost is not greater than 4");
                    guessWrong = cardFromHand.getManaCost().convertedManaCost() > 4;
                }
                game.informPlayers(attackedPlayer.getLogName() + " guessed " + (guessWrong ? "wrong" : "right"));
                if (guessWrong) {
                    if (cardFromHand.getCardType().contains(CardType.LAND)) {
                        // If the revealed card is a land, you can't cast it. So nothing happens
                    } else {
                        if (controller.chooseUse(outcome, "Cast " + cardFromHand.getName() + " without paying its mana cost?", source, game)) {
                            controller.cast(cardFromHand.getSpellAbility(), game, true);
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }
}
