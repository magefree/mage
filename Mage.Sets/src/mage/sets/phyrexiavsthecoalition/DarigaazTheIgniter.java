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
package mage.sets.phyrexiavsthecoalition;

import java.util.Set;
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
import mage.cards.Cards;
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
 * @author FenrisulfrX
 */
public class DarigaazTheIgniter extends CardImpl {

    public DarigaazTheIgniter(UUID ownerId) {
        super(ownerId, 47, "Darigaaz, the Igniter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{R}{G}");
        this.expansionSetCode = "DDE";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever {this} deals combat damage to a player, you may pay {2}{R}. If you do, choose a color, then that player reveals his or her hand and Darigaaz deals damage to the player equal to the number of cards of that color revealed this way.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(
                new DarigaazTheIgniterEffect(), new ManaCostsImpl("{2}{R}")), false, true));
    }

    public DarigaazTheIgniter(final DarigaazTheIgniter card) {
        super(card);
    }

    @Override
    public DarigaazTheIgniter copy() {
        return new DarigaazTheIgniter(this);
    }
}

class DarigaazTheIgniterEffect extends OneShotEffect {

    public DarigaazTheIgniterEffect() {
        super(Outcome.Damage);
        staticText = "choose a color, then that player reveals his or her hand and {this} deals damage"
                + "to the player equal to the number of cards of that color revealed this way";
    }

    public DarigaazTheIgniterEffect(final DarigaazTheIgniterEffect effect) {
        super(effect);
    }

    @Override
    public DarigaazTheIgniterEffect copy() {
        return new DarigaazTheIgniterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null) {
            ChoiceColor choice = new ChoiceColor();
            controller.choose(outcome, choice, game);
            if(choice.getColor() != null) {
                game.informPlayers(new StringBuilder(controller.getName()).append(" chooses ").append(choice.getColor()).toString());
                Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                if(damagedPlayer != null) {
                    damagedPlayer.revealCards("hand of " + damagedPlayer.getName(), damagedPlayer.getHand(), game);
                    Cards cardsInHand = damagedPlayer.getHand();
                    if(cardsInHand.size() > 0) {
                        FilterCard  filter = new FilterCard();
                        filter.add(new ColorPredicate(choice.getColor()));
                        Set<Card> damage = cardsInHand.getCards(filter, game);
                        if(damage.size() > 0) {
                            damagedPlayer.damage(damage.size(), source.getSourceId(), game, false, true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}