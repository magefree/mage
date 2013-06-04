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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class SilentBladeOni extends CardImpl<SilentBladeOni> {

    public SilentBladeOni(UUID ownerId) {
        super(ownerId, 105, "Silent-Blade Oni", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{B}{B}");
        this.expansionSetCode = "PC2";
        this.subtype.add("Demon");
        this.subtype.add("Ninja");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Ninjutsu {4}{U}{B}
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl("{4}{U}{B}")));
        // Whenever Silent-Blade Oni deals combat damage to a player, look at that player's hand. You may cast a nonland card in it without paying that card's mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SilentBladeOniEffect(), false, true));

    }

    public SilentBladeOni(final SilentBladeOni card) {
        super(card);
    }

    @Override
    public SilentBladeOni copy() {
        return new SilentBladeOni(this);
    }
}

class SilentBladeOniEffect extends OneShotEffect<SilentBladeOniEffect> {

    public SilentBladeOniEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "look at that player's hand. You may cast a nonland card in it without paying that card's mana cost";
    }

    public SilentBladeOniEffect(final SilentBladeOniEffect effect) {
        super(effect);
    }

    @Override
    public SilentBladeOniEffect copy() {
        return new SilentBladeOniEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            Cards cardsInHand = new CardsImpl(Constants.Zone.PICK);
            cardsInHand.addAll(opponent.getHand());
            if (cardsInHand.size() > 0) {
                TargetCard target = new TargetCard(1, Constants.Zone.PICK, new FilterNonlandCard());
                if (controller.chooseTarget(outcome, cardsInHand, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.cast(card.getSpellAbility(), game, true);
                    }
                }

            }
            return true;
        }
        return false;
    }
}
