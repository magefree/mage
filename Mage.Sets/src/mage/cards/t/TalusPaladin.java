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
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class TalusPaladin extends CardImpl {
    
    public TalusPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.subtype.add("Ally");
        

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Talus Paladin or another Ally enters the battlefield under your control, you may have Allies you control gain lifelink until end of turn, and you may put a +1/+1 counter on Talus Paladin.
        this.addAbility(new TalusPaladinTriggeredAbility());
    }
    
    public TalusPaladin(final TalusPaladin card) {
        super(card);
    }
    
    @Override
    public TalusPaladin copy() {
        return new TalusPaladin(this);
    }
}

class TalusPaladinTriggeredAbility extends TriggeredAbilityImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("all allies you control");
    
    static {
        filter.add(new SubtypePredicate(SubType.ALLY));
    }
    
    TalusPaladinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, filter), true);
        this.addEffect(new TalusPaladinEffect());
    }
    
    TalusPaladinTriggeredAbility(final TalusPaladinTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public TalusPaladinTriggeredAbility copy() {
        return new TalusPaladinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent ally = game.getPermanent(event.getTargetId());
        if (ally != null) {
            if (ally.hasSubtype(SubType.ALLY, game)
                    && ally.getControllerId().equals(this.getControllerId())) {
                if (event.getTargetId().equals(this.getSourceId())
                        || event.getTargetId().equals(ally.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever {this} or another Ally enters the battlefield under your control, you may have Allies you control gain lifelink until end of turn, and you may put a +1/+1 counter on {this}.";
    }
}

class TalusPaladinEffect extends OneShotEffect {
    
    public TalusPaladinEffect() {
        super(Outcome.Benefit);
    }
    
    public TalusPaladinEffect(final TalusPaladinEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent taluspPaladin = game.getPermanent(source.getSourceId());
        if (taluspPaladin != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Put a +1/+1 counter on Talus Paladin?");
            if (!player.chooseUse(Outcome.Benefit, sb.toString(), source, game)) {
                return false;
            }
            taluspPaladin.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        return false;
    }
    
    @Override
    public TalusPaladinEffect copy() {
        return new TalusPaladinEffect(this);
    }
}
