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
package mage.sets.portal;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class SternMarshal extends CardImpl {

    public SternMarshal(UUID ownerId) {
        super(ownerId, 194, "Stern Marshal", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "POR";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Target creature gets +2/+2 until end of turn. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, 
                new BoostTargetEffect(2, 2, Duration.EndOfTurn), new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.getInstance());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public SternMarshal(final SternMarshal card) {
        super(card);
    }

    @Override
    public SternMarshal copy() {
        return new SternMarshal(this);
    }
}
