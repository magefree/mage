/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.keyword;

import mage.Constants.Zone;
import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;

/**
 * Miracle ability:
 *   You may cast this card for its miracle cost when you draw it if it's the first card you drew this turn.
 *
 * @author noxx
 */
public class MiracleAbility extends StaticAbility<MiracleAbility> {

    private static final String staticRule = " (You may cast this card for its miracle cost when you draw it if it's the first card you drew this turn.)";
    
    private String ruleText;
    
	public MiracleAbility(Cost cost) {
		super(Zone.BATTLEFIELD, null);
        addCost(cost);
        ruleText = "Miracle" + cost.getText() + staticRule;
	}

    public MiracleAbility(MiracleAbility miracleAbility) {
        super(miracleAbility);
    }

	@Override
	public String getRule() {
		return ruleText;
	}

	@Override
	public MiracleAbility copy() {
		return new MiracleAbility(this);
	}

}