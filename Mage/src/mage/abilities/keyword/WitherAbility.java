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

import java.io.ObjectStreamException;

/**
 * 702.77. Wither
 *
 *   702.77a. Wither is a static ability. Damage dealt to a creature by a source with wither isn't marked on that creature. Rather, it causes that many -1/-1 counters to be put on that creature. See rule 119.3.
 *
 *   702.77b. If a permanent leaves the battlefield before an effect causes it to deal damage, its last known information is used to determine whether it had wither.
 *
 *   702.77c. The wither rules function no matter what zone an object with wither deals damage from.
 *
 *   702.77d. Multiple instances of wither on the same object are redundant.
 *
 *  @author nantuko
 */
public class WitherAbility extends StaticAbility<WitherAbility> {

	private static final WitherAbility fINSTANCE =  new WitherAbility();

	private Object readResolve() throws ObjectStreamException {
		return fINSTANCE;
	}

	public static WitherAbility getInstance() {
		return fINSTANCE;
	}

	private WitherAbility() {
		super(Zone.ALL, null);
	}

	@Override
	public String getRule() {
		return "Wither";
	}

	@Override
	public WitherAbility copy() {
		return fINSTANCE;
	}

}
