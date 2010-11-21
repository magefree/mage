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

package mage.game.events;

import java.util.UUID;
import mage.Constants.Zone;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ZoneChangeEvent extends GameEvent {

	private Zone fromZone;
	private Zone toZone;
	private Permanent target;

	public ZoneChangeEvent(Permanent target, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone) {
		super(EventType.ZONE_CHANGE, target.getId(), sourceId, playerId);
		this.fromZone = fromZone;
		this.toZone = toZone;
		this.target = target;
	}

	public ZoneChangeEvent(UUID targetId, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone) {
		super(EventType.ZONE_CHANGE, targetId, sourceId, playerId);
		this.fromZone = fromZone;
		this.toZone = toZone;
	}

	public ZoneChangeEvent(Permanent target, UUID playerId, Zone fromZone, Zone toZone) {
		this(target, null, playerId, fromZone, toZone);
	}

	public ZoneChangeEvent(UUID targetId, UUID playerId, Zone fromZone, Zone toZone) {
		this(targetId, null, playerId, fromZone, toZone);
	}

	public Zone getFromZone() {
		return fromZone;
	}

	public Zone getToZone() {
		return toZone;
	}

	public void setToZone(Zone toZone) {
		this.toZone = toZone;
	}

	public Permanent getTarget() {
		return target;
	}
}
