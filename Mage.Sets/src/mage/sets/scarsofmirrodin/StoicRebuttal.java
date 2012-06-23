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

package mage.sets.scarsofmirrodin;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author ayrat
 */
public class StoicRebuttal extends CardImpl<StoicRebuttal> {

    public StoicRebuttal(UUID ownerId) {
        super(ownerId, 46, "Stoic Rebuttal", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "SOM";
        this.color.setBlue(true);
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    public StoicRebuttal(final StoicRebuttal card) {
        super(card);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (MetalcraftCondition.getInstance().apply(game, ability)) {
            /*ManaCosts<ManaCost> previousCost = ability.getManaCostsToPay();
            ManaCosts<ManaCost> adjustedCost = new ManaCostsImpl<ManaCost>();
            boolean reduced = false;
            for (ManaCost manaCost : previousCost) {
                Mana mana = manaCost.getOptions().get(0);
                if (!reduced && mana != null && mana.getColorless() > 0) {
                    mana.setColorless(0);
                    adjustedCost.add(manaCost);
                    reduced = true;
                } else {
                    adjustedCost.add(manaCost);
                }
            }
            ability.getManaCostsToPay().clear();
            ability.getManaCostsToPay().addAll(adjustedCost);*/
            ability.getManaCostsToPay().clear();
            ability.getManaCostsToPay().load("{U}{U}");
        }
    }

    @Override
    public StoicRebuttal copy() {
        return new StoicRebuttal(this);
    }
}
