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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;


/**
 *
 * @author LevelX2
 */
public class AzorsElocutors extends CardImpl {

    public AzorsElocutors(UUID ownerId) {
        super(ownerId, 210, "Azor's Elocutors", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W/U}{W/U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Human");
        this.subtype.add("Advisor");


        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, put a filibuster counter on Azor's Elocutors. Then if Azor's Elocutors has five or more filibuster counters on it, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AzorsElocutorsEffect(), TargetController.YOU, false));

        // Whenever a source deals damage to you, remove a filibuster counter from Azor's Elocutors.
        this.addAbility(new AzorsElocutorsTriggeredAbility());

    }

    public AzorsElocutors(final AzorsElocutors card) {
        super(card);
    }

    @Override
    public AzorsElocutors copy() {
        return new AzorsElocutors(this);
    }
}
class AzorsElocutorsTriggeredAbility extends TriggeredAbilityImpl {

    public AzorsElocutorsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(new Counter("filibuster")), false);
    }

    public AzorsElocutorsTriggeredAbility(final AzorsElocutorsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AzorsElocutorsTriggeredAbility copy() {
        return new AzorsElocutorsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return "Whenever a source deals damage to you, " + super.getRule();
    }

}

class AzorsElocutorsEffect extends OneShotEffect {

    public AzorsElocutorsEffect() {
        super(Outcome.Benefit);
        staticText = "put a filibuster counter on Azor's Elocutors. Then if Azor's Elocutors has five or more filibuster counters on it, you win the game";
    }

    public AzorsElocutorsEffect(final AzorsElocutorsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(new Counter("filibuster"), game);
            if (permanent.getCounters(game).getCount("filibuster") > 4) {
                Player player = game.getPlayer(permanent.getControllerId());
                if (player != null) {
                    player.won(game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public AzorsElocutorsEffect copy() {
        return new AzorsElocutorsEffect(this);
    }
}
