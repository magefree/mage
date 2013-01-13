/*
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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class PrimeSpeakerZegana extends CardImpl<PrimeSpeakerZegana> {

    public PrimeSpeakerZegana(UUID ownerId) {
        super(ownerId, 188, "Prime Speaker Zegana", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{U}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Wizard");

        this.color.setGreen(true);
        this.color.setBlue(true);
        
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Prime Speaker Zegana enters the battlefield with X +1/+1 counters on it, where X is the greatest power among other creatures you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), new greatestPowerCount(), true), "where X is the greatest power among other creatures you control"));
        //When Prime Speaker Zegana enters the battlefield, draw cards equal to its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardControllerEffect(new SourcePermanentPowerCount())));
    }

    public PrimeSpeakerZegana(final PrimeSpeakerZegana card) {
        super(card);
    }

    @Override
    public PrimeSpeakerZegana copy() {
        return new PrimeSpeakerZegana(this);
    }
}

class greatestPowerCount implements DynamicValue {
    
    
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int value = 0;
        for(Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), sourceAbility.getControllerId(), game)){
            if(creature != null && creature.getPower().getValue() > value && !sourceAbility.getSourceId().equals(creature.getId())){
                value = creature.getPower().getValue();
            }
        }
        return value;
    }

    @Override
    public DynamicValue copy() {
        return new greatestPowerCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "greatest power among other creatures you control";
    }
}