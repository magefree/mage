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

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class CrucibleOfTheSpiritDragon extends CardImpl {

    public CrucibleOfTheSpiritDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Put a storage counter on Crucible of the Spirit Dragon.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()),new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Remove X storage counters from Crucible of the Spirit Dragon: Add X mana in any combination of colors to your mana pool. Spend this mana only to cast Dragon spells or activate abilities of Dragons.
        ability = new ConditionalAnyColorManaAbility(
                new TapSourceCost(),
                new RemovedCountersForCostValue(),
                new CrucibleOfTheSpiritDragonManaBuilder(),
                false
                );
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE.createInstance()));
        this.addAbility(ability);
    }

    public CrucibleOfTheSpiritDragon(final CrucibleOfTheSpiritDragon card) {
        super(card);
    }

    @Override
    public CrucibleOfTheSpiritDragon copy() {
        return new CrucibleOfTheSpiritDragon(this);
    }
}

class CrucibleOfTheSpiritDragonManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CrucibleOfTheSpiritDragonConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Dragon spells or activate abilities of Dragons";
    }
}

class CrucibleOfTheSpiritDragonConditionalMana extends ConditionalMana {

    public CrucibleOfTheSpiritDragonConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast Dragon spells or activate abilities of Dragons";
        addCondition(new CrucibleOfTheSpiritDragonManaCondition());
    }
}

class CrucibleOfTheSpiritDragonManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object != null && object.hasSubtype(SubType.DRAGON, game)) {
            return true;
        }
        return false;
    }
}