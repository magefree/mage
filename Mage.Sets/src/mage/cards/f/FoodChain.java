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
package mage.cards.f;

import java.util.UUID;
import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class FoodChain extends CardImpl {

    public FoodChain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Exile a creature you control: Add X mana of any one color to your mana pool, where X is the exiled creature's converted mana cost plus one. Spend this mana only to cast creature spells.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new FoodChainManaEffect(), new ExileTargetCost(new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent("a creature you control"), true)));
        this.addAbility(ability);
    }

    public FoodChain(final FoodChain card) {
        super(card);
    }

    @Override
    public FoodChain copy() {
        return new FoodChain(this);
    }
}

class FoodChainManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creature spells";
    }
}

class FoodChainManaEffect extends ManaEffect {

    FoodChainManaEffect() {
        this.staticText = "Add X mana of any one color to your mana pool, where X is the exiled creature's converted mana cost plus one. Spend this mana only to cast creature spells.";
    }

    FoodChainManaEffect(final FoodChainManaEffect effect) {
        super(effect);
    }

    @Override
    public FoodChainManaEffect copy() {
        return new FoodChainManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int manaCostExiled = 0;
            for (Cost cost : source.getCosts()) {
                if (cost.isPaid() && cost instanceof ExileTargetCost) {
                    for (Card card : ((ExileTargetCost) cost).getPermanents()) {
                        manaCostExiled += card.getConvertedManaCost();
                    }
                }
            }
            ChoiceColor choice = new ChoiceColor();
            controller.choose(Outcome.PutManaInPool, choice, game);
            if (choice.getColor() == null) {
                return false;
            }
            Mana chosen = choice.getMana(manaCostExiled + 1);
            Mana mana = new FoodChainManaBuilder().setMana(chosen, source, game).build();
            if (mana != null) {
                checkToFirePossibleEvents(mana, game, source);
                controller.getManaPool().addMana(mana, game, source);
                return true;
            }
        }

        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

}
