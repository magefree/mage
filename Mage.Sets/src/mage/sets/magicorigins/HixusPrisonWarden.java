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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class HixusPrisonWarden extends CardImpl {

    public HixusPrisonWarden(UUID ownerId) {
        super(ownerId, 19, "Hixus, Prison Warden", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "ORI";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(4);
        this.toughness = new MageInt(44);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        
        // Whenever a creature deals combat damage to you, if Hixus, Prison Warden entered the battlefield this turn, exile that creature until Hixus leaves the battlefield.
        this.addAbility(new HixusPrisonWardenTriggeredAbility(new HixusPrisonWardenExileEffect()));
    }

    public HixusPrisonWarden(final HixusPrisonWarden card) {
        super(card);
    }

    @Override
    public HixusPrisonWarden copy() {
        return new HixusPrisonWarden(this);
    }
}

class HixusPrisonWardenTriggeredAbility extends TriggeredAbilityImpl {

    public HixusPrisonWardenTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        this.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
     }

    public HixusPrisonWardenTriggeredAbility(final HixusPrisonWardenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HixusPrisonWardenTriggeredAbility copy() {
        return new HixusPrisonWardenTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        MageObject mageObject = getSourceObject(game);
        return (mageObject instanceof Permanent) && ((Permanent)mageObject).getTurnsOnBattlefield() ==1;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent sourcePermanent = game.getPermanent(event.getSourceId());
        if (damageEvent.getPlayerId().equals(getControllerId()) && 
                damageEvent.isCombatDamage() && 
                sourcePermanent != null && 
                sourcePermanent.getCardType().contains(CardType.CREATURE)) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to you, if {this} entered the battlefield this turn, exile that creature until {this} leaves the battlefield.";
    }

}

class HixusPrisonWardenExileEffect extends OneShotEffect {

    public HixusPrisonWardenExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile that creature until {this} leaves the battlefield";
    }

    public HixusPrisonWardenExileEffect(final HixusPrisonWardenExileEffect effect) {
        super(effect);
    }

    @Override
    public HixusPrisonWardenExileEffect copy() {
        return new HixusPrisonWardenExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        // If Prison Warden leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}