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
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */

//20130711
/*
 * 903.11. If a commander would be put into its owner’s graveyard from anywhere, that player may put it into the command zone instead.
 * 903.12. If a commander would be put into the exile zone from anywhere, its owner may put it into the command zone instead.
 */
public class CommanderReplacementEffect extends ReplacementEffectImpl<CommanderReplacementEffect> {

    private final UUID commanderId;

    public CommanderReplacementEffect(UUID commanderId) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a commander would be put into its owner’s graveyard from anywhere, that player may put it into the command zone instead. If a commander would be put into the exile zone from anywhere, its owner may put it into the command zone instead.";
        this.commanderId = commanderId;
        this.duration = Duration.EndOfGame;
    }

    public CommanderReplacementEffect(final CommanderReplacementEffect effect) {
        super(effect);
        this.commanderId = effect.commanderId;
    }

    @Override
    public CommanderReplacementEffect copy() {
        return new CommanderReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent)event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = ((ZoneChangeEvent)event).getTarget();
            if (permanent != null) {
                Player player = game.getPlayer(permanent.getOwnerId());
                if (player != null && player.chooseUse(Outcome.Benefit, "Move commander to command zone?", game)){
                    return permanent.moveToZone(Zone.COMMAND, source.getSourceId(), game, false);
                }
            }
        } else {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                Player player = game.getPlayer(card.getOwnerId());
                if (player != null && player.chooseUse(Outcome.Benefit, "Move commander to command zone?", game)){
                    return card.moveToZone(Zone.COMMAND, source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && (((ZoneChangeEvent)event).getToZone() == Zone.GRAVEYARD || ((ZoneChangeEvent)event).getToZone() == Zone.EXILED)) {
            if(commanderId != null && commanderId.equals(event.getTargetId())){
                return true;
            }
        }
        return false;
    }

}
