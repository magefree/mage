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
import mage.Constants.TargetController;
import mage.cards.Card;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCard<T extends FilterCard<T>> extends FilterObject<Card, FilterCard<T>> {

	private static final long serialVersionUID = 1L;

	protected List<UUID> ownerId = new ArrayList<UUID>();
	protected boolean notOwner;
	protected List<String> expansionSetCode = new ArrayList<String>();
	protected boolean notExpansionSetCode;
    protected TargetController owner = TargetController.ANY;

	/**
	 * Text that appears on card.
	 * At the moment only card name and rules are checked.
	 */
	protected String text = "";

	public FilterCard() {
		super("card");
	}

	public FilterCard(String name) {
		super(name);
	}

	public FilterCard(FilterCard<T> filter) {
		super(filter);
        this.ownerId.addAll(filter.ownerId);
		this.notOwner = filter.notOwner;
        this.expansionSetCode.addAll(filter.expansionSetCode);
		this.notExpansionSetCode = filter.notExpansionSetCode;
        this.owner = filter.owner;
	}

	@Override
	public boolean match(Card card) {
		if (!super.match(card))
			return notFilter;

		if (ownerId.size() > 0 && ownerId.contains(card.getOwnerId()) == notOwner)
			return notFilter;

		if (expansionSetCode.size() > 0 && expansionSetCode.contains(card.getExpansionSetCode()) == notExpansionSetCode)
			return notFilter;

		if (text.length() > 0) {
			// first check in card name
			boolean filterOut = !card.getName().toLowerCase().contains(text.toLowerCase());
			// if couldn't find
			if (filterOut) {
				// then try to find in rules
				for (String rule : card.getRules()) {
					if (rule.toLowerCase().contains(text.toLowerCase())) {
						filterOut = false;
						break;
					}
				}

                if (filterOut) {
				    for (String subType : card.getSubtype()) {
                        if (subType.equalsIgnoreCase(text)) {
                            filterOut = false;
                            break;
                        }
                    }
                }

				if (filterOut)
					return notFilter;
			}

		}
		
		return !notFilter;
	}
    
	public boolean match(Card card, UUID playerId, Game game) {
		if (!this.match(card))
			return notFilter;

		if (owner != TargetController.ANY && playerId != null) {
			switch(owner) {
				case YOU:
					if (!card.getOwnerId().equals(playerId))
						return notFilter;
					break;
				case OPPONENT:
					if (!game.getOpponents(playerId).contains(card.getOwnerId()))
						return notFilter;
					break;
				case NOT_YOU:
					if (card.getOwnerId().equals(playerId))
						return notFilter;
					break;
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
	
	public List<String> getExpansionSetCode() {
		return expansionSetCode;
	}

	public void setNotExpansionSetCode(boolean notExpansionSetCode) {
		this.notExpansionSetCode = notExpansionSetCode;
	}

	public void setText(String text) {
		this.text = text;
	}

    public void setTargetOwner(TargetController owner) {
		this.owner = owner;
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
