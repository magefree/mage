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
package mage.sets.scourge;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Backfir3
 */
public class AncientOoze extends CardImpl<AncientOoze> {

    public AncientOoze(UUID ownerId) {
        super(ownerId, 112, "Ancient Ooze", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "SCG";
        this.subtype.add("Ooze");

        this.color.setGreen(true); 
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

		// Ancient Ooze's power and toughness are each equal to the total converted mana cost of other creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new AncientOozePowerToughnessValue(), Duration.EndOfGame)));
    }

    public AncientOoze(final AncientOoze card) {
        super(card);
    }

    @Override
    public AncientOoze copy() {
        return new AncientOoze(this);
    }
}

class AncientOozePowerToughnessValue implements DynamicValue {
    
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int value = 0;
        for(Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), sourceAbility.getControllerId(), game)){
            if(creature != null && !sourceAbility.getSourceId().equals(creature.getId())){
                value += creature.getManaCost().convertedManaCost();
            }
        }
        return value;
    }

    @Override
    public DynamicValue copy() {
        return new AncientOozePowerToughnessValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "total converted mana cost of other creatures you control";
    }
}
