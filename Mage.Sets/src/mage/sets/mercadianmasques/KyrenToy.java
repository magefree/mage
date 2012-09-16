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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class KyrenToy extends CardImpl<KyrenToy> {

    public KyrenToy(UUID ownerId) {
        super(ownerId, 303, "Kyren Toy", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "MMQ";

        // {1}, {T}: Put a charge counter on Kyren Toy.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
        // {T}, Remove X charge counters from Kyren Toy: Add {X}{1} to your mana pool.
        ability = new KyrenToyManaAbility();
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.addAbility(ability);
    }

    public KyrenToy(final KyrenToy card) {
        super(card);
    }

    @Override
    public KyrenToy copy() {
        return new KyrenToy(this);
    }
    
    private class KyrenToyManaAbility extends BasicManaAbility<KyrenToyManaAbility> {
        KyrenToyManaAbility() {
            super(new KyrenToyManaEffect());
        }

        KyrenToyManaAbility(final KyrenToyManaAbility ability) {
            super(ability);
        }

        @Override
        public KyrenToyManaAbility copy() {
            return new KyrenToyManaAbility(this);
        }
}

    private class KyrenToyManaEffect extends ManaEffect<KyrenToyManaEffect> {

        KyrenToyManaEffect() {
            super();
            staticText = "Add {X}{1} to your mana pool";
        }

        KyrenToyManaEffect(final KyrenToyManaEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());

            if (player != null) {
                int numberOfMana = 0;
                for (Cost cost : source.getCosts()) {
                    if (cost instanceof RemoveVariableCountersSourceCost) {
                        numberOfMana = ((RemoveVariableCountersSourceCost)cost).getAmount();
                    }
                }             
                player.getManaPool().addMana(new Mana(0, 0, 0, 0, 0, numberOfMana + 1, 0), game, source);
                return true;
            }
            return false;
        }

        @Override
        public KyrenToyManaEffect copy() {
            return new KyrenToyManaEffect(this);
        }
    }    
}
