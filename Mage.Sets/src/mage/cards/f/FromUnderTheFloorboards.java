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
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class FromUnderTheFloorboards extends CardImpl {

    public FromUnderTheFloorboards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Madness {X}{B}{B} <i>(If you discard this card discard it into exile. When you do cast it for its madness cost or put it into your graveyard.
        Ability ability = (new MadnessAbility(this, new ManaCostsImpl("{X}{B}{B}")));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        
        // Create three 2/2 black Zombie creature tokens tapped and you gain 3 life.        
        // If From Under the Floorboards's madness cost was paid, instead create X of those tokens tapped and you gain X life.      
        DynamicValue xValue = new FromUnderTheFloorboardsManacostVariableValue();
        Effect effect = new CreateTokenEffect(new ZombieToken(), xValue, true, false);
        effect.setText("Create three 2/2 black Zombie creature tokens tapped and you gain 3 life. If {this} madness cost was paid, instead create X of those tokens tapped and you gain X life.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainLifeEffect(xValue));        
    }

    public FromUnderTheFloorboards(final FromUnderTheFloorboards card) {
        super(card);
    }

    @Override
    public FromUnderTheFloorboards copy() {
        return new FromUnderTheFloorboards(this);
    }
}

class FromUnderTheFloorboardsManacostVariableValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ManaCosts manaCosts = sourceAbility.getManaCostsToPay();
        if (manaCosts.getVariableCosts().isEmpty()) {
            return 3;
        }
        return sourceAbility.getManaCostsToPay().getX();
    }

    @Override
    public FromUnderTheFloorboardsManacostVariableValue copy() {
        return new FromUnderTheFloorboardsManacostVariableValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
