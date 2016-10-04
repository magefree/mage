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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class TrevaTheRenewer extends CardImpl {

    public TrevaTheRenewer(UUID ownerId) {
        super(ownerId, 280, "Treva, the Renewer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{W}{U}");
        this.expansionSetCode = "INV";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Treva, the Renewer deals combat damage to a player, you may pay {2}{W}. If you do, choose a color, then you gain 1 life for each permanent of that color.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new TrevaTheRenewerEffect(), new ManaCostsImpl("{2}{W}")), false));
    }

    public TrevaTheRenewer(final TrevaTheRenewer card) {
        super(card);
    }

    @Override
    public TrevaTheRenewer copy() {
        return new TrevaTheRenewer(this);
    }
}

class TrevaTheRenewerEffect extends OneShotEffect {
    
    public TrevaTheRenewerEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a color, then you gain 1 life for each permanent of that color.";
    }
    
    public TrevaTheRenewerEffect(final TrevaTheRenewerEffect effect) {
        super(effect);
    }
    
    @Override
    public TrevaTheRenewerEffect copy() {
        return new TrevaTheRenewerEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());        
        if (controller == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor();
        controller.choose(outcome, choice, game);
        if (choice.getColor() != null) {
            game.informPlayers(new StringBuilder(controller.getLogName()).append(" chooses ").append(choice.getColor()).toString());
            FilterPermanent filter = new FilterPermanent();
            filter.add(new ColorPredicate(choice.getColor()));
            int cardsWithColor = game.getBattlefield().count(filter, source.getSourceId(), controller.getId(), game);
            if (cardsWithColor > 0) {
                new GainLifeEffect(cardsWithColor).apply(game, source);
            }
            return true;
        }
        return false;
    }
}