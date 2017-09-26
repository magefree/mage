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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetOpponent;

/**
 *
 * @author L_J
 */
public class KarplusanMinotaur extends CardImpl {

    public KarplusanMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Cumulative upkeep-Flip a coin.
        this.addAbility(new CumulativeUpkeepAbility(new KarplusanMinotaurCost()));
        
        // Whenever you win a coin flip, Karplusan Minotaur deals 1 damage to target creature or player.
        Ability abilityWin = new KarplusanMinotaurFlipWinTriggeredAbility();
        abilityWin.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(abilityWin);

        //TODO: Make ability properly copiable
        // Whenever you lose a coin flip, Karplusan Minotaur deals 1 damage to target creature or player of an opponent's choice.
        Ability abilityLose = new KarplusanMinotaurFlipLoseTriggeredAbility();
        abilityLose.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(abilityLose);
    }
    
    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof KarplusanMinotaurFlipLoseTriggeredAbility) {
            Player controller = game.getPlayer(ability.getControllerId());
            if(controller != null) {
                UUID opponentId = null;
                if(game.getOpponents(controller.getId()).size() > 1) {
                    Target target = new TargetOpponent(true);
                    if(controller.chooseTarget(Outcome.Neutral, target, ability, game)) {
                        opponentId = target.getFirstTarget();
                    }
                }
                else {
                    opponentId = game.getOpponents(controller.getId()).iterator().next();
                }
                if(opponentId != null) {
                    ability.getTargets().get(0).setTargetController(opponentId);
                }
            }
        }
    }

    public KarplusanMinotaur(final KarplusanMinotaur card) {
        super(card);
    }

    @Override
    public KarplusanMinotaur copy() {
        return new KarplusanMinotaur(this);
    }
}

class KarplusanMinotaurFlipWinTriggeredAbility extends TriggeredAbilityImpl {

    public KarplusanMinotaurFlipWinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), false);
    }

    public KarplusanMinotaurFlipWinTriggeredAbility(final KarplusanMinotaurFlipWinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KarplusanMinotaurFlipWinTriggeredAbility copy() {
        return new KarplusanMinotaurFlipWinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COIN_FLIPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this.getControllerId().equals(event.getPlayerId()) && event.getFlag();
    }

    @Override
    public String getRule() {
        return "Whenever you win a coin flip, {this} deals 1 damage to target creature or player";
    }
}

class KarplusanMinotaurFlipLoseTriggeredAbility extends TriggeredAbilityImpl {

    public KarplusanMinotaurFlipLoseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), false);
    }

    public KarplusanMinotaurFlipLoseTriggeredAbility(final KarplusanMinotaurFlipLoseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KarplusanMinotaurFlipLoseTriggeredAbility copy() {
        return new KarplusanMinotaurFlipLoseTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COIN_FLIPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this.getControllerId().equals(event.getPlayerId()) && !event.getFlag();
    }

    @Override
    public String getRule() {
        return "Whenever you lose a coin flip, {this} deals 1 damage to target creature or player of an opponent's choice";
    }
}

class KarplusanMinotaurCost extends CostImpl {
    
    KarplusanMinotaurCost() {
        this.text = "Flip a coin";
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            controller.flipCoin(game);
            this.paid = true;
            return true;
            }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (!game.getOpponents(controllerId).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public KarplusanMinotaurCost copy() {
        return new KarplusanMinotaurCost();
    }
}
