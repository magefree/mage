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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class ShimatsuTheBloodcloaked extends CardImpl<ShimatsuTheBloodcloaked> {

    public ShimatsuTheBloodcloaked(UUID ownerId) {
        super(ownerId, 186, "Shimatsu the Bloodcloaked", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Demon");
        this.subtype.add("Spirit");

        this.color.setRed(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Shimatsu the Bloodcloaked enters the battlefield, sacrifice any number of permanents. Shimatsu enters the battlefield with that many +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ShimatsuTheBloodcloakedEffect()));
    }

    public ShimatsuTheBloodcloaked(final ShimatsuTheBloodcloaked card) {
        super(card);
    }

    @Override
    public ShimatsuTheBloodcloaked copy() {
        return new ShimatsuTheBloodcloaked(this);
    }
}

class ShimatsuTheBloodcloakedEffect extends ReplacementEffectImpl<ShimatsuTheBloodcloakedEffect> {
    
    public ShimatsuTheBloodcloakedEffect() {
        super(Constants.Duration.WhileOnBattlefield, Outcome.BoostCreature);
        this.staticText = "As Shimatsu the Bloodcloaked enters the battlefield, sacrifice any number of permanents. Shimatsu enters the battlefield with that many +1/+1 counters on it";
    }
    
    public ShimatsuTheBloodcloakedEffect(final ShimatsuTheBloodcloakedEffect effect) {
        super(effect);
    }
    
    @Override
    public ShimatsuTheBloodcloakedEffect copy() {
        return new ShimatsuTheBloodcloakedEffect(this);
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
         && event.getTargetId().equals(source.getSourceId())) {
                return true;
        }
        return false;        

    }
    
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            Target target = new TargetControlledPermanent(0, Integer.MAX_VALUE, new FilterPermanent(), true);
            target.setRequired(false);
            if (!target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                return false;
            }
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            if (target.getTargets().size() > 0) {
                int sacrificedCreatures = target.getTargets().size();
                game.informPlayers(new StringBuilder(controller.getName()).append(" sacrifices ").append(sacrificedCreatures).append(" creatures for ").append(creature.getName()).toString());
                for (UUID targetId: target.getTargets()) {
                    Permanent targetCreature = game.getPermanent(targetId);
                    if (targetCreature == null || !targetCreature.sacrifice(source.getSourceId(), game)) {
                        return false;
                    }
                }
                creature.addCounters(CounterType.P1P1.createInstance(sacrificedCreatures), game);
            }
        }
        return false;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }    
}
