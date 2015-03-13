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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public class SunscapeMaster extends CardImpl {

    public SunscapeMaster(UUID ownerId) {
        super(ownerId, 42, "Sunscape Master", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "INV";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}{G}, {tap}: Creatures you control get +2/+2 until end of turn.
        Effect effect1 = new BoostControlledEffect(2, 2, Duration.EndOfTurn);
        effect1.setText("Creatures you control get +2/+2 until end of turn");
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect1, new ManaCostsImpl("{G}{G}"));
        ability1.addCost(new TapSourceCost());
        this.addAbility(ability1);
        
        // {U}{U}, {tap}: Return target creature to its owner's hand.
        Effect effect2 = new ReturnToHandTargetEffect();
        effect2.setText("Return target creature to its owner's hand.");
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect2, new ManaCostsImpl("{U}{U}"));
        ability2.addTarget(new TargetCreaturePermanent());
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);
    }

    public SunscapeMaster(final SunscapeMaster card) {
        super(card);
    }

    @Override
    public SunscapeMaster copy() {
        return new SunscapeMaster(this);
    }
}
