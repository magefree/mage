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
package mage.cards.c;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

/**
 *
 * @author fireshoes
 */
public class CorruptedCrossroads extends CardImpl {

    public CorruptedCrossroads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        
        // {T}, Pay 1 life: Add one mana of any color to your mana pool. Spend this mana only to cast a spell with devoid.
        Ability ability = new ConditionalAnyColorManaAbility(1, new BlightedCrossroadsManaBuilder());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    public CorruptedCrossroads(final CorruptedCrossroads card) {
        super(card);
    }

    @Override
    public CorruptedCrossroads copy() {
        return new CorruptedCrossroads(this);
    }
}

class BlightedCrossroadsManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new BlightedCrossroadsConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a spell with devoid";
    }
}

class BlightedCrossroadsConditionalMana extends ConditionalMana {

    public BlightedCrossroadsConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a spell with devoid";
        addCondition(new BlightedCrossroadsManaCondition());
    }
}

class BlightedCrossroadsManaCondition implements Condition {
    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object != null) {
            for (Ability ability: object.getAbilities()) {
                if (ability instanceof DevoidAbility) {
                    return true;
                }
            }
        }
        return false;
    }
}
