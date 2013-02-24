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
package mage.abilities.keyword;
 
import mage.Constants;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
 
/**
 *
 * @author LevelX2
 */
 

//
//    702.96. Unleash
//
//    702.96a Unleash is a keyword that represents two static abilities. 
//            "Unleash" means "You may have this permanent enter the battlefield with an additional +1/+1 counter on it"
//            and "This permanent can’t block as long as it has a +1/+1 counter on it."


 public class UnleashAbility extends SimpleStaticAbility {
 
    public UnleashAbility() {
        super(Constants.Zone.ALL, new UnleashReplacementEffect());
        this.addEffect(new UnleashRestrictionEffect());
    }
 
    public UnleashAbility(final UnleashAbility ability) {
        super(ability);
    }
 
    @Override
    public UnleashAbility copy() {
        return new UnleashAbility(this);
    }
 
    @Override
    public String getRule() {
        return "Unleash <i>(You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)</i>";
    }
}
 
class UnleashReplacementEffect extends ReplacementEffectImpl<UnleashReplacementEffect> {
 
    public UnleashReplacementEffect() {
        super(Constants.Duration.EndOfGame, Outcome.Detriment);
    }
 
    public UnleashReplacementEffect(UnleashReplacementEffect effect) {
        super(effect);
    }
 
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }
 
    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
 
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            if (controller.chooseUse(outcome, "Unleash "+ creature.getName() +"?", game)) {
                game.informPlayers(controller.getName() + " unleashes " + creature.getName());
                creature.addCounters(CounterType.P1P1.createInstance(), game);
            }
        }
        return false;
    }
 
    @Override
    public String getText(Mode mode) {
        return staticText;
    }
 
    @Override
    public UnleashReplacementEffect copy() {
        return new UnleashReplacementEffect(this);
    }
 
}
 
class UnleashRestrictionEffect extends RestrictionEffect<UnleashRestrictionEffect> {
 
    public UnleashRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
    }
 
    public UnleashRestrictionEffect(final UnleashRestrictionEffect effect) {
        super(effect);
    }
 
    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent != null && permanent.getId().equals(source.getSourceId())) {
            if (permanent.getCounters().getCount(CounterType.P1P1) > 0) {
                return true;
            }
        }
        return false;
    }
 
    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }
 
    @Override
    public UnleashRestrictionEffect copy() {
        return new UnleashRestrictionEffect(this);
    }
}