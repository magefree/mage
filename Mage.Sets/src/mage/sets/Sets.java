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

package mage.sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import mage.cards.Card;
import mage.cards.ExpansionSet;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Sets extends HashMap<String, ExpansionSet> {

	private static final Sets fINSTANCE =  new Sets();
	private static Set<String> names;

	public static Sets getInstance() {
		return fINSTANCE;
	}

	private Sets() {
		names = new HashSet<String>();
		this.addSet(AlaraReborn.getInstance());
		this.addSet(Conflux.getInstance());
		this.addSet(Magic2010.getInstance());
		this.addSet(Magic2011.getInstance());
//		this.addSet(Planechase.getInstance());
		this.addSet(RiseOfTheEldrazi.getInstance());
		this.addSet(ShardsOfAlara.getInstance());
		this.addSet(Tenth.getInstance());
		this.addSet(Worldwake.getInstance());
		this.addSet(Zendikar.getInstance());
	}

	private void addSet(ExpansionSet set) {
		this.put(set.getCode(), set);
		for (Card card: set.createCards()) {
			names.add(card.getName());
		}
	}

	public static Set<String> getCardNames() {
		return names;
	}

	public static String findCard(String name) {
		for (ExpansionSet set: fINSTANCE.values()) {
			for (Card card: set.createCards()) {
				if (name.equals(card.getName()))
					return card.getClass().getCanonicalName();
			}
		}
		return null;
	}
}
