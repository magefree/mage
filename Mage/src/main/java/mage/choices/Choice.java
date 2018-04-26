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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public interface Choice {

    String getMessage();
    void setMessage(String message);

    String getSubMessage();
    void setSubMessage(String subMessage);

    void clearChoice();
    boolean isChosen();
    boolean isRequired();

    Choice copy();

    // string choice
    void setChoices(Set<String> choices);
    Set<String> getChoices();
    void setChoice(String choice);
    String getChoice();

    // key-value choice
    boolean isKeyChoice();
    void setKeyChoices(Map<String, String> choices);
    Map<String,String> getKeyChoices();
    void setChoiceByKey(String choiceKey);
    String getChoiceKey();
    String getChoiceValue();

    // search
    boolean isSearchEnabled();
    void setSearchEnabled(boolean isEnabled);
    void setSearchText(String searchText);
    String getSearchText();

    // sorting
    boolean isSortEnabled();
    void setSortData(Map<String, Integer> sortData);
    Map<String, Integer> getSortData();

    // random choice
    void setRandomChoice();
    boolean setChoiceByAnswers(List<String> answers, boolean removeSelectAnswerFromList);
}
