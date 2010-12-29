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

import java.io.ObjectStreamException;
import mage.Constants.Zone;
import mage.abilities.StaticAbility;

/**
 *   702.87. Infect
 *
 *      702.87a. Infect is a static ability.
 *
 *      702.87b. Damage dealt to a player by a source with infect doesn't cause that player to lose life. Rather, it causes the player to get that many poison counters. See rule 119.3.
 *
 *      702.87c. Damage dealt to a creature by a source with infect isn't marked on that creature. Rather, it causes that many -1/-1 counters to be put on that creature. See rule 119.3.
 *
 *      702.87d. If a permanent leaves the battlefield before an effect causes it to deal damage, its last known information
 *      (Last Known Information: Information about an object that's no longer in the zone it's expected to be in, or information about a player that's no longer in the game. This information captures that object's last existence in that zone or that player's last existence in the game....)
 *      112.7a. Once activated or triggered, an ability exists on the stack independently of its source. Destruction or removal of the source after that time won't affect the ability. Note that some abilities cause a source to do something (for example, "Prodigal Sorcerer deals 1 damage...
 *      608.2b. If the spell or ability specifies targets, it checks whether the targets are still legal. A target that's no longer in the zone it was in when it was targeted is illegal. Other changes to the game state may cause a target to no longer be legal; for example, its...
 *      608.2g. If an effect requires information from the game (such as the number of creatures on the battlefield), the answer is determined only once, when the effect is applied. If the effect requires information from a specific object, including the source of the ability itself or a...
 *      800.4f. If an effect requires information about a specific player, the effect uses the current information about that player if he or she is still in the game; otherwise, the effect uses the last known information about that player before he or she left the game.
 *       is used to determine whether it had infect.
 *
 *      702.87e. The infect rules function no matter what zone an object with infect deals damage from.
 *
 *      702.87f. Multiple instances of infect on the same object are redundant.
 *
 *  @author nantuko
 */
public class InfectAbility extends StaticAbility<InfectAbility> {

	private static final InfectAbility fINSTANCE =  new InfectAbility();

	private Object readResolve() throws ObjectStreamException {
		return fINSTANCE;
	}

	public static InfectAbility getInstance() {
		return fINSTANCE;
	}

	private InfectAbility() {
		super(Zone.ALL, null);
	}

	@Override
	public String getRule() {
		return "Infect";
	}

	@Override
	public InfectAbility copy() {
		return fINSTANCE;
	}

}
