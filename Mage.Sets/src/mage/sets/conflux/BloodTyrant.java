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
package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class BloodTyrant extends CardImpl<BloodTyrant> {

    public BloodTyrant(UUID ownerId) {
        super(ownerId, 99, "Blood Tyrant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{B}{R}");
        this.expansionSetCode = "CON";
        this.subtype.add("Vampire");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, each player loses 1 life. Put a +1/+1 counter on Blood Tyrant for each 1 life lost this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new BloodTyrantEffect(), TargetController.YOU, false));

        // Whenever a player loses the game, put five +1/+1 counters on Blood Tyrant.
        this.addAbility(new PlayerLosesTheGameTriggeredAbility());

    }

    public BloodTyrant(final BloodTyrant card) {
        super(card);
    }

    @Override
    public BloodTyrant copy() {
        return new BloodTyrant(this);
    }
}

class PlayerLosesTheGameTriggeredAbility extends TriggeredAbilityImpl<PlayerLosesTheGameTriggeredAbility> {

    public PlayerLosesTheGameTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)), false);
    }

    public PlayerLosesTheGameTriggeredAbility(final PlayerLosesTheGameTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PlayerLosesTheGameTriggeredAbility copy() {
        return new PlayerLosesTheGameTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.LOSES) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player loses the game, put five +1/+1 counters on {this}.";
    }
}

class BloodTyrantEffect extends OneShotEffect<BloodTyrantEffect> {

    public BloodTyrantEffect() {
        super(Outcome.Benefit);
        staticText = "each player loses 1 life. Put a +1/+1 counter on {this} for each 1 life lost this way";
    }

    public BloodTyrantEffect(final BloodTyrantEffect effect) {
        super(effect);
    }

    @Override
    public BloodTyrantEffect copy() {
        return new BloodTyrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int counters = 0;
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                player.loseLife(1, game);
                counters++;
            }
        }
        Permanent bloodTyrant = game.getPermanent(source.getSourceId());
        if (bloodTyrant != null && counters != 0) {
            bloodTyrant.addCounters(CounterType.P1P1.createInstance(counters), game);
        }
        return true;
    }
}