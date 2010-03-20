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

package mage.choices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChoiceImpl implements Choice, Serializable {

	protected Ability source;
	protected boolean chosen;
	protected String choice;
	protected List<String> choices = new ArrayList<String>();
	protected String message;

	public ChoiceImpl() {

	}

	@Override
	public boolean isChosen() {
		return chosen;
	}

	@Override
	public void clearChoice() {
		choice = null;
		chosen = false;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public List<String> getChoices() {
		return choices;
	}

	@Override
	public String getChoice() {
		return choice;
	}

	@Override
	public void setChoice(String choice) {
		if (choices.contains(choice)) {
			this.choice = choice;
			this.chosen = true;
		}
	}

	@Override
	public Ability getAbility() {
		return source;
	}

	@Override
	public void setAbility(Ability source) {
		this.source = source;
	}

}
