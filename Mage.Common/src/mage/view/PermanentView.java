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

package mage.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.cards.Card;
import mage.counters.Counter;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentView extends CardView {
    private static final long serialVersionUID = 1L;

	private boolean tapped;
	private boolean flipped;
	private boolean phasedIn;
	private boolean faceUp;
	private boolean summoningSickness;
	private int damage;
	private List<UUID> attachments;
	private List<CounterView> counters;
	private CardView original;

	public PermanentView(Permanent permanent, Card card) {
		super(permanent);
		this.rules = permanent.getRules();
		this.tapped = permanent.isTapped();
		this.flipped = permanent.isFlipped();
		this.phasedIn = permanent.isPhasedIn();
		this.faceUp = permanent.isFaceUp();
		this.summoningSickness = permanent.hasSummoningSickness();
		this.damage = permanent.getDamage();
		if (permanent.getAttachments().size() > 0) {
			attachments = new ArrayList<UUID>();
			attachments.addAll(permanent.getAttachments());
		}
		if (permanent.getCounters().size() > 0) {
			counters = new ArrayList<CounterView>();
			for (Counter counter: permanent.getCounters().values()) {
				counters.add(new CounterView(counter));
			}
		}
		if (permanent instanceof PermanentToken) {
			original = new CardView(((PermanentToken)permanent).getToken());
		}
		else {
			original = new CardView(card);
		}
	}

	public boolean isTapped() {
		return tapped;
	}

	public int getDamage() {
		return damage;
	}

	public boolean isFlipped() {
		return flipped;
	}

	public boolean isPhasedIn() {
		return phasedIn;
	}

	public boolean isFaceUp() {
		return faceUp;
	}

	public boolean hasSummoningSickness(){
		return summoningSickness;
	}

	public List<UUID> getAttachments() {
		return attachments;
	}

	public List<CounterView> getCounters() {
		return counters;
	}

	public CardView getOriginal() {
		return original;
	}
	
	public void overrideTapped(boolean tapped) {
		this.tapped = tapped;
	}
}
