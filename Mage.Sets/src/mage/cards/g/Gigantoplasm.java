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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author fireshoes
 */
public class Gigantoplasm extends CardImpl {

    public Gigantoplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Gigantoplasm enter the battlefield as a copy of any creature on the battlefield except it gains "{X}: This creature has base power and toughness X/X."
        Effect effect = new CopyPermanentEffect(new FilterCreaturePermanent(), new GigantoplasmApplyToPermanent());
        effect.setText("a copy of any creature on the battlefield except it gains \"{X}: This creature has base power and toughness X/X.\"");
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    public Gigantoplasm(final Gigantoplasm card) {
        super(card);
    }

    @Override
    public Gigantoplasm copy() {
        return new Gigantoplasm(this);
    }
}

class GigantoplasmApplyToPermanent extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent permanent) {
        DynamicValue variableMana = new ManacostVariableValue();
        Effect effect = new SetPowerToughnessSourceEffect(variableMana, Duration.WhileOnBattlefield, SubLayer.SetPT_7b);
        effect.setText("This creature has base power and toughness X/X");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{X}"));
        permanent.getAbilities().add(ability);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject) {
        DynamicValue variableMana = new ManacostVariableValue();
        Effect effect = new SetPowerToughnessSourceEffect(variableMana, Duration.WhileOnBattlefield, SubLayer.SetPT_7b);
        effect.setText("This creature has base power and toughness X/X");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{X}"));
        mageObject.getAbilities().add(ability);
        return true;
    }

}
