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
package mage.sets.invasion;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox

 */
public class CrosisThePurger extends CardImpl {

    public CrosisThePurger(UUID ownerId) {
        super(ownerId, 242, "Crosis, the Purger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");
        this.expansionSetCode = "INV";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Crosis, the Purger deals combat damage to a player, you may pay {2}{B}. If you do, choose a color, then that player reveals his or her hand and discards all cards of that color.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new CrosisThePurgerEffect(),
            new ManaCostsImpl("{2}{B}")), false, true));
    }

    public CrosisThePurger(final CrosisThePurger card) {
        super(card);
    }

    @Override
    public CrosisThePurger copy() {
        return new CrosisThePurger(this);
    }
}

class CrosisThePurgerEffect extends OneShotEffect {

    CrosisThePurgerEffect() {
        super(Outcome.Discard);
        this.staticText = "choose a color, then that player reveals his or her hand and discards all cards of that color.";
    }

    CrosisThePurgerEffect(final CrosisThePurgerEffect effect) {
        super(effect);
    }

    @Override
    public CrosisThePurgerEffect copy() {
        return new CrosisThePurgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null) {
            ChoiceColor choice = new ChoiceColor();
            player.choose(outcome, choice, game);
            if(choice.getColor() != null) {
                game.informPlayers(new StringBuilder(player.getLogName()).append(" chooses ").append(choice.getColor()).toString());
                Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                damagedPlayer.revealCards("hand of " + damagedPlayer.getName(), damagedPlayer.getHand(), game);
                FilterCard filter = new FilterCard();
                filter.add(new ColorPredicate(choice.getColor()));
                List<Card> toDiscard = new ArrayList<>();
                for(UUID cardId : damagedPlayer.getHand()) {
                    Card card = game.getCard(cardId);
                    if(filter.match(card, game)) {
                        toDiscard.add(card);
                    }
                }
                for(Card card: toDiscard) {
                    damagedPlayer.discard(card, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
