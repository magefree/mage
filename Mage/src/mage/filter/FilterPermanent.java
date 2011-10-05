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

package mage.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterPermanent<T extends FilterPermanent<T>> extends FilterObject<Permanent, FilterPermanent<T>> {
	protected List<UUID> ownerId = new ArrayList<UUID>();
	protected boolean notOwner;
	protected List<UUID> controllerId = new ArrayList<UUID>();
	protected boolean notController;
	protected boolean useTapped;
	protected boolean tapped;
	protected boolean useFlipped;
	protected boolean flipped;
	protected boolean useFaceup;
	protected boolean faceup;
	protected boolean usePhased;
	protected boolean phasedIn;
	protected TargetController controller = TargetController.ANY;
    protected TargetController owner = TargetController.ANY;

	public FilterPermanent() {
		super("permanent");
	}

	public FilterPermanent(final FilterPermanent<T> filter) {
		super(filter);
		for (UUID oId: filter.ownerId) {
			this.ownerId.add(oId);
		}
		this.notOwner = filter.notOwner;
		for (UUID oId: filter.controllerId) {
			this.controllerId.add(oId);
		}
		this.notController = filter.notController;
		this.useTapped = filter.useTapped;
		this.tapped = filter.tapped;
		this.useFlipped = filter.useFlipped;
		this.flipped = filter.flipped;
		this.useFaceup = filter.useFaceup;
		this.faceup = filter.faceup;
		this.usePhased = filter.usePhased;
		this.phasedIn = filter.phasedIn;
		this.controller = filter.controller;
        this.owner = filter.owner;
	}

	public FilterPermanent(String name) {
		super(name);
	}

	@Override
	public boolean match(Permanent permanent) {
		if (!super.match(permanent))
			return notFilter;

		if (ownerId.size() > 0 && ownerId.contains(permanent.getOwnerId()) == notOwner)
			return notFilter;

		if (controllerId.size() > 0 && controllerId.contains(permanent.getControllerId()) == notController)
			return notFilter;

		if (useTapped && permanent.isTapped() != tapped)
			return notFilter;

		if (useFlipped && permanent.isFlipped() != flipped)
			return notFilter;

		if (useFaceup && permanent.isFaceUp() != faceup)
			return notFilter;

		if (usePhased && permanent.isPhasedIn() != phasedIn)
			return notFilter;

		return !notFilter;
	}

	public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
		if (!this.match(permanent))
			return notFilter;

		if (controller != TargetController.ANY && playerId != null) {
			switch(controller) {
				case YOU:
					if (!permanent.getControllerId().equals(playerId))
						return notFilter;
					break;
				case OPPONENT:
					if (!game.getOpponents(playerId).contains(permanent.getControllerId()))
						return notFilter;
					break;
				case NOT_YOU:
					if (permanent.getControllerId().equals(playerId))
						return notFilter;
					break;
			}
		}

		if (owner != TargetController.ANY && playerId != null) {
			switch(owner) {
				case YOU:
					if (!permanent.getOwnerId().equals(playerId))
						return notFilter;
					break;
				case OPPONENT:
					if (!game.getOpponents(playerId).contains(permanent.getOwnerId()))
						return notFilter;
					break;
				case NOT_YOU:
					if (permanent.getOwnerId().equals(playerId))
						return notFilter;
					break;
			}
		}

        if (another) {
			// filter out itself
			if (permanent.getId().equals(sourceId)) {
				return notFilter;
			}
		}

		return !notFilter;
	}

	public List<UUID> getOwnerId() {
		return ownerId;
	}

	public void setNotOwner(boolean notOwner) {
		this.notOwner = notOwner;
	}

	public List<UUID> getControllerId() {
		return controllerId;
	}

	public void setNotController(boolean notController) {
		this.notController = notController;
	}

	public void setUseTapped(boolean useTapped) {
		this.useTapped = useTapped;
	}

	public void setTapped(boolean tapped) {
		this.tapped = tapped;
	}

	public void setUseFlipped(boolean useFlipped) {
		this.useFlipped = useFlipped;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}

	public void setUseFaceup(boolean useFaceup) {
		this.useFaceup = useFaceup;
	}

	public void setFaceup(boolean faceup) {
		this.faceup = faceup;
	}

	public void setTargetController(TargetController controller) {
		this.controller = controller;
	}

    public void setTargetOwner(TargetController owner) {
		this.owner = owner;
	}

	public boolean matchOwner(UUID testOwnerId) {
		if (ownerId.size() > 0 && ownerId.contains(testOwnerId) == notOwner)
			return false;
		return true;
	}

	public boolean matchController(UUID testControllerId) {
		if (controllerId.size() > 0 && controllerId.contains(testControllerId) == notController)
			return false;
		return true;
	}

	@Override
	public FilterPermanent<T> copy() {
		return new FilterPermanent<T>(this);
	}

}
