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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.cards.Card;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCard<T extends FilterCard<T>> extends FilterObject<Card, FilterCard<T>> {

	protected List<UUID> ownerId = new ArrayList<UUID>();
	protected boolean notOwner;
	protected List<UUID> expansionSetId = new ArrayList<UUID>();
	protected boolean notExpansionSetId;

	public FilterCard() {
		super("card");
	}

	public FilterCard(String name) {
		super(name);
	}

	public FilterCard(FilterCard<T> filter) {
		super(filter);
		for (UUID oId: filter.ownerId) {
			this.ownerId.add(oId);
		}
		this.notOwner = filter.notOwner;
		for (UUID eId: filter.expansionSetId) {
			this.expansionSetId.add(eId);
		}
		this.notExpansionSetId = filter.notExpansionSetId;
	}

	@Override
	public boolean match(Card card) {
		if (!super.match(card))
			return notFilter;

		if (ownerId.size() > 0 && ownerId.contains(card.getOwnerId()) == notOwner)
			return notFilter;

		if (expansionSetId.size() > 0 && expansionSetId.contains(card.getExpansionSetId()) == notExpansionSetId)
			return notFilter;
		
		return !notFilter;
	}

	public List<UUID> getOwnerId() {
		return ownerId;
	}

	public void setNotOwner(boolean notOwner) {
		this.notOwner = notOwner;
	}
	
	public List<UUID> getExpansionSetId() {
		return expansionSetId;
	}

	public void setNotExpansionSetId(boolean notExpansionSetId) {
		this.notExpansionSetId = notExpansionSetId;
	}

	public boolean matchOwner(UUID testOwnerId) {
		if (ownerId.size() > 0 && ownerId.contains(testOwnerId) == notOwner)
			return false;
		return true;
	}

	public Set<Card> filter(Set<Card> cards) {
		Set<Card> filtered = new HashSet<Card>();
		for (Card card: cards) {
			if (match(card)) {
				filtered.add(card);
			}
		}
		return filtered;
	}

	@Override
	public FilterCard<T> copy() {
		return new FilterCard<T>(this);
	}
}
