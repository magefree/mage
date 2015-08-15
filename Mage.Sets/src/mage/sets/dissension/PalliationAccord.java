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
package mage.sets.dissension;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public class PalliationAccord extends CardImpl {

    public PalliationAccord(UUID ownerId) {
        super(ownerId, 122, "Palliation Accord", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");
        this.expansionSetCode = "DIS";

        // Whenever a creature an opponent controls becomes tapped, put a shield counter on Palliation Accord.
        this.addAbility(new PallationAccordTriggeredAbility());
        
        // Remove a shield counter from Palliation Accord: Prevent the next 1 damage that would be dealt to you this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PalliationAccordPreventionEffect(), new RemoveCountersSourceCost(CounterType.SHIELD.createInstance())));
    }

    public PalliationAccord(final PalliationAccord card) {
        super(card);
    }

    @Override
    public PalliationAccord copy() {
        return new PalliationAccord(this);
    }
}

class PallationAccordTriggeredAbility extends TriggeredAbilityImpl {
    PallationAccordTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.SHIELD.createInstance()));
    }

    PallationAccordTriggeredAbility(final PallationAccordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PallationAccordTriggeredAbility copy() {
        return new PallationAccordTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent p = game.getPermanent(event.getTargetId());
        if (p != null && p.getCardType().contains(CardType.CREATURE)) {
            if (game.getOpponents(this.controllerId).contains(p.getControllerId()))
                return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls becomes tapped, " + modes.getText();
    }
}

class PalliationAccordPreventionEffect extends PreventionEffectImpl {

    public PalliationAccordPreventionEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "Prevent the next 1 damage that would be dealt to you this turn";
    }

    public PalliationAccordPreventionEffect(final PalliationAccordPreventionEffect effect) {
        super(effect);
    }

    @Override
    public PalliationAccordPreventionEffect copy() {
        return new PalliationAccordPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE,
                source.getControllerId(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            if (damage > 0) {
                event.setAmount(damage - 1);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE,
                        source.getControllerId(), source.getSourceId(), source.getControllerId(), 1));
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }
}