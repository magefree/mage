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
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.combat.CombatGroup;

/**
 *
 * @author fireshoes
 */
public class SparringGolem extends CardImpl {

    public SparringGolem(UUID ownerId) {
        super(ownerId, 312, "Sparring Golem", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.expansionSetCode = "INV";
        this.subtype.add("Golem");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Sparring Golem becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.
        this.addAbility(new SparringGolemAbility());
    }

    public SparringGolem(final SparringGolem card) {
        super(card);
    }

    @Override
    public SparringGolem copy() {
        return new SparringGolem(this);
    }
}

class SparringGolemAbility extends BecomesBlockedTriggeredAbility {

    public SparringGolemAbility() {
        super(null, false);
        SparringGolemValue value = new SparringGolemValue();
        this.addEffect(new BoostSourceEffect(value, value, Duration.EndOfTurn));
    }

    public SparringGolemAbility(final SparringGolemAbility ability) {
        super(ability);
    }

    @Override
    public SparringGolemAbility copy() {
        return new SparringGolemAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.";
    }
}

class SparringGolemValue implements DynamicValue {

    @Override
    public SparringGolemValue copy() {
        return new SparringGolemValue();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for(CombatGroup combatGroup : game.getCombat().getGroups()) {
            if(combatGroup.getAttackers().contains(sourceAbility.getSourceId())) {
                 int blockers = combatGroup.getBlockers().size();
                 return blockers > 1 ? (blockers) : 0;
            }
        }
        return 0;
    }

    @Override
    public String getMessage() {
        return "+1/+1 until end of turn for each creature blocking it";
    }
}
