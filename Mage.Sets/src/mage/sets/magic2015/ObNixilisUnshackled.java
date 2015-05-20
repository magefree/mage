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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author emerald000
 */
public class ObNixilisUnshackled extends CardImpl {

    public ObNixilisUnshackled(UUID ownerId) {
        super(ownerId, 110, "Ob Nixilis, Unshackled", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "M15";
        this.supertype.add("Legendary");
        this.subtype.add("Demon");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Whenever an opponent searches his or her library, that player sacrifices a creature and loses 10 life.
        this.addAbility(new ObNixilisUnshackledTriggeredAbility());
        
        // Whenever another creature dies, put at +1/+1 counter on Ob Nixilis, Unshackled.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));
    }

    public ObNixilisUnshackled(final ObNixilisUnshackled card) {
        super(card);
    }

    @Override
    public ObNixilisUnshackled copy() {
        return new ObNixilisUnshackled(this);
    }
}

class ObNixilisUnshackledTriggeredAbility extends TriggeredAbilityImpl {
    
    ObNixilisUnshackledTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ObNixilisUnshackledEffect(), false);
    }
    
    ObNixilisUnshackledTriggeredAbility(final ObNixilisUnshackledTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public ObNixilisUnshackledTriggeredAbility copy() {
        return new ObNixilisUnshackledTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.LIBRARY_SEARCHED) {
            Player controller = game.getPlayer(this.getControllerId());
            if (controller != null && game.isOpponent(controller, event.getTargetId())) {
                this.addTarget(new TargetPlayer());
                getTargets().get(0).add(event.getPlayerId(), game);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever an opponent searches his or her library, that player sacrifices a creature and loses 10 life.";
    }
}

class ObNixilisUnshackledEffect extends SacrificeEffect {
    
    static private final FilterPermanent filter = new FilterControlledCreaturePermanent("creature");
    
    ObNixilisUnshackledEffect() {
        super(filter, 1, "that player");
        this.staticText = "that player sacrifices a creature and loses 10 life";
    }
    
    ObNixilisUnshackledEffect(final ObNixilisUnshackledEffect effect) {
        super(effect);
    }
    
    @Override
    public ObNixilisUnshackledEffect copy() {
        return new ObNixilisUnshackledEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.loseLife(10, game);
        }
        return super.apply(game, source);
    }
}
