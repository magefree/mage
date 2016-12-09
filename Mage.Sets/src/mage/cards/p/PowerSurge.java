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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.UntappedLandsAtBeginningOfTurnWatcher;

/**
 *
 * @author MTGfan
 */
public class PowerSurge extends CardImpl {

    public PowerSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}");
        

        // At the beginning of each player's upkeep, Power Surge deals X damage to that player, where X is the number of untapped lands he or she controlled at the beginning of this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PowerSurgeDamageEffect(), TargetController.ANY, false, true), new UntappedLandsAtBeginningOfTurnWatcher());
    }

    public PowerSurge(final PowerSurge card) {
        super(card);
    }

    @Override
    public PowerSurge copy() {
        return new PowerSurge(this);
    }
}

class PowerSurgeDamageEffect extends OneShotEffect{
    
    public PowerSurgeDamageEffect()
    {
        super(Outcome.Damage);
    }
    
    public PowerSurgeDamageEffect(PowerSurgeDamageEffect copy)
    {
        super(copy);
    }
        
    @Override
    public String getText(Mode mode) {
        return "{this} deals X damage to that player where X is the number of untapped lands he or she controlled at the beginning of this turn";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            UntappedLandsAtBeginningOfTurnWatcher watcher = (UntappedLandsAtBeginningOfTurnWatcher) game.getState().getWatchers().get("UntappedLandsAtBeginningOfTurn");
            int damage = watcher.getUntappedLandCount(targetPointer.getFirst(game, source));
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public PowerSurgeDamageEffect copy() {
        return new PowerSurgeDamageEffect(this);
    }
}
