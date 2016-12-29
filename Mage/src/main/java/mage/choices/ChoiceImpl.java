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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChoiceImpl implements Choice, Serializable {

    protected boolean chosen;
    protected final boolean required;
    protected String choice;
    protected String choiceKey;
    protected Set<String> choices = new LinkedHashSet<>();
    protected Map<String, String> keyChoices = new LinkedHashMap<>();
    protected String message;

    public ChoiceImpl() {
        this(false);
    }

    public ChoiceImpl(boolean required) {
        this.required = required;
    }

    public ChoiceImpl(ChoiceImpl choice) {
        this.choice = choice.choice;
        this.chosen = choice.chosen;
        this.required = choice.required;
        this.message = choice.message;
        this.choices.addAll(choice.choices);
        this.choiceKey = choice.choiceKey;
        this.keyChoices = choice.keyChoices; // list should never change for the same object so copy by reference
    }

    @Override
    public boolean isChosen() {
        return chosen;
    }

    @Override
    public void clearChoice() {
        choice = null;
        choiceKey = null;
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
    public ChoiceImpl copy() {
        return new ChoiceImpl(this);
    }

    @Override
    public Map<String, String> getKeyChoices() {
        return keyChoices;
    }

    @Override
    public void setKeyChoices(Map<String, String> choices) {
        keyChoices = choices;
    }

    @Override
    public String getChoiceKey() {
        return choiceKey;
    }

    @Override
    public void setChoiceByKey(String choiceKey) {
        String choiceToSet = keyChoices.get(choiceKey);
        if (choiceToSet != null) {
            this.choice = choiceToSet;
            this.choiceKey = choiceKey;
        }
    }

    @Override
    public boolean isKeyChoice() {
        return !keyChoices.isEmpty();
    }

}
