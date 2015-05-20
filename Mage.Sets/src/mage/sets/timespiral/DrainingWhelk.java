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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public class DrainingWhelk extends CardImpl {

    public DrainingWhelk(UUID ownerId) {
        super(ownerId, 57, "Draining Whelk", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Illusion");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Draining Whelk enters the battlefield, counter target spell. Put X +1/+1 counters on Draining Whelk, where X is that spell's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrainingWhelkEffect());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    public DrainingWhelk(final DrainingWhelk card) {
        super(card);
    }

    @Override
    public DrainingWhelk copy() {
        return new DrainingWhelk(this);
    }
}

class DrainingWhelkEffect extends CounterTargetEffect {
    
    DrainingWhelkEffect() {
        super();
        staticText = "counter target spell. Put X +1/+1 counters on Draining Whelk, where X is that spell's converted mana cost";
    }
    
    DrainingWhelkEffect(final DrainingWhelkEffect effect) {
        super(effect);
    }
    
    @Override
    public DrainingWhelkEffect copy() {
        return new DrainingWhelkEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Spell targetSpell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (targetSpell != null) {
            int spellCMC = targetSpell.getConvertedManaCost();
            super.apply(game, source);
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(spellCMC)).apply(game, source);
            return true;
        }
        return false;
    }
}
