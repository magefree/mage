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
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChoiceImpl<T extends ChoiceImpl<T>> implements Choice, Serializable {

	protected boolean chosen;
	protected boolean required;
	protected String choice;
	protected Set<String> choices = new HashSet<String>();
	protected String message;

	public ChoiceImpl() {
		this(false);
	}

	public ChoiceImpl(boolean required) {
		this.required = required;
	}

	public ChoiceImpl(ChoiceImpl<T> choice) {
		this.choice = choice.choice;
		this.chosen = choice.chosen;
		this.required = choice.required;
		this.message = choice.message;
        this.choices.addAll(choice.choices);
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
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Set<String> getChoices() {
		return choices;
	}

	@Override
	public void setChoices(Set<String> choices) {
		this.choices = choices;
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
	public boolean isRequired() {
		return this.required;
	}

	@Override
	public T copy() {
		return (T)new ChoiceImpl(this);
	}

}
