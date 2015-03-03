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

package mage.abilities.effects;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public interface ContinuousRuleModifiyingEffect extends ContinuousEffect {

    /**
     * This check for the relevant events is called at first to prevent further actions if
     * the current event is ignored from this effect. Speeds up event handling.
     * @param event
     * @param game
     * @return
     */
    boolean checksEventType(GameEvent event, Game game);

    /**
     * 
     * @param event the event to check if it may happen
     * @param source the ability of the effect
     * @param game the game
     * @return 
     */
    boolean applies(GameEvent event, Ability source, Game game);
 
    /**
     * Defines if the user should get a message about the rule modifying effect
     * if he was applied
     * 
     * @return true if user should be informed
     */
    boolean sendMessageToUser();

    /**
     * Defines if the a message should be send to game log about the rule modifying effect
     * if he was applied
     * 
     * @return true if message should go to game log
     */
    boolean sendMessageToGameLog();
    /**
     * Returns a message text that informs the player why he can't do something.
     * 
     * @param source the ability of the effect
     * @param event
     * @param game the game
     * @return 
     */
    String getInfoMessage(Ability source, GameEvent event, Game game);
}
