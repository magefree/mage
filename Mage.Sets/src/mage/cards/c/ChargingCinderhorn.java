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
package mage.cards.c;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author spjspj
 */
public class ChargingCinderhorn extends CardImpl {

    public ChargingCinderhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add("Elemental");
        this.subtype.add("Ox");
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of each player's end step, if no creatures attacked this turn, put a fury counter on Charging Cinderhorn. Then Charging Cinderhorn deals damage equal to the number of fury counters on it to that player.
        ChargingCinderhornDamageTargetEffect effect = new ChargingCinderhornDamageTargetEffect();
        effect.setText("if no creatures attacked this turn, put a fury counter on {this}. Then {this} deals damage equal to the number of fury counters on it to that player");
        BeginningOfEndStepTriggeredAbility ability =
                new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.ANY, new ChargingCinderhornCondition(), false);
        this.addAbility(ability, new AttackedThisTurnWatcher());
    }

    public ChargingCinderhorn(final ChargingCinderhorn card) {
        super(card);
    }

    @Override
    public ChargingCinderhorn copy() {
        return new ChargingCinderhorn(this);
    }
}

class ChargingCinderhornCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = (AttackedThisTurnWatcher) game.getState().getWatchers().get("AttackedThisTurn");
        if (watcher != null && watcher instanceof AttackedThisTurnWatcher) {
            Set<UUID> attackedThisTurnCreatures = watcher.getAttackedThisTurnCreatures();
            return attackedThisTurnCreatures.isEmpty();
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "no creatures attacked this turn";
    }


}

class ChargingCinderhornDamageTargetEffect extends OneShotEffect{
    
    public ChargingCinderhornDamageTargetEffect()
    {
        super(Outcome.Damage);
    }
    
    public ChargingCinderhornDamageTargetEffect(ChargingCinderhornDamageTargetEffect copy)
    {
        super(copy);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent chargingCinderhoof = game.getPermanent(source.getSourceId());
        if (chargingCinderhoof != null) {
            chargingCinderhoof.addCounters(CounterType.FURY.createInstance(), source, game);
        } else {
            chargingCinderhoof = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }

        if (chargingCinderhoof == null) {
            return false;
        }

        DynamicValue amount = new CountersSourceCount(CounterType.FURY);
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public ChargingCinderhornDamageTargetEffect copy() {
        return new ChargingCinderhornDamageTargetEffect(this);
    }
}
