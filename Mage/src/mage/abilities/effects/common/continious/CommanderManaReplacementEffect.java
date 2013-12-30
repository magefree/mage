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
package mage.abilities.effects.common.continious;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;

/**
 *
 * @author LevelX
 */

//20130711
/*
 * 903.9. If mana would be added to a player's mana pool of a color that isn't in the color identity
 * of that player's commander, that amount of colorless mana is added to that player's mana pool instead.
 */
public class CommanderManaReplacementEffect extends ReplacementEffectImpl<CommanderManaReplacementEffect> {

    private final UUID playerId;
    private final Mana commanderMana;

    public CommanderManaReplacementEffect(UUID playerId, Mana commanderMana) {
        super(Duration.EndOfGame, Outcome.Neutral);
        staticText = "If mana would be added to a player's mana pool of a color that isn't in the color identity of that player's commander, that amount of colorless mana is added to that player's mana pool instead.";
        this.playerId = playerId;
        this.commanderMana = commanderMana;
    }

    public CommanderManaReplacementEffect(final CommanderManaReplacementEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
        this.commanderMana = effect.commanderMana;
    }

    @Override
    public CommanderManaReplacementEffect copy() {
        return new CommanderManaReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Mana mana = ((ManaEvent) event).getMana();
        if (mana.getBlack() > 0 && commanderMana.getBlack() == 0) {
            for (int i = 0; i < mana.getBlack(); i++) {
                mana.addColorless();
            }
            mana.setBlack(0);
        }
        if (mana.getBlue() > 0 && commanderMana.getBlue() == 0) {
            for (int i = 0; i < mana.getBlue(); i++) {
                mana.addColorless();
            }
            mana.setBlue(0);
        }
        if (mana.getGreen() > 0 && commanderMana.getGreen() == 0) {
            for (int i = 0; i < mana.getGreen(); i++) {
                mana.addColorless();
            }
            mana.setGreen(0);
        }
        if (mana.getRed() > 0 && commanderMana.getRed() == 0) {
            for (int i = 0; i < mana.getRed(); i++) {
                mana.addColorless();
            }
            mana.setRed(0);
        }
        if (mana.getWhite() > 0 && commanderMana.getWhite()== 0) {
            for (int i = 0; i < mana.getWhite(); i++) {
                mana.addColorless();
            }
            mana.setWhite(0);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ADD_MANA && event.getPlayerId().equals(playerId)) {
            return true;
        }
        return false;
    }

}
