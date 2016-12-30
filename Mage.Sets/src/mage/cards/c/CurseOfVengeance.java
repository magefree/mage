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

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author spjspj
 */
public class CurseOfVengeance extends CardImpl {

    public CurseOfVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add("Aura");
        this.subtype.add("Curse");

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted player casts a spell, put a spite counter on Curse of Vengeance.
        this.addAbility(new CurseOfVengeanceTriggeredAbility());

        // When enchanted player loses the game, you gain X life and draw X cards, where X is the number of spite counters on Curse of Vengeance.
        this.addAbility(new CurseOfVengeancePlayerLosesTriggeredAbility());
    }

    public CurseOfVengeance(final CurseOfVengeance card) {
        super(card);
    }

    @Override
    public CurseOfVengeance copy() {
        return new CurseOfVengeance(this);
    }
}

class CurseOfVengeanceTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfVengeanceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.SPITE.createInstance(), Outcome.Detriment), false);
    }

    public CurseOfVengeanceTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public CurseOfVengeanceTriggeredAbility(final CurseOfVengeanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;

    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(getSourceId());
        Spell spell = game.getStack().getSpell(event.getSourceId());

        if (enchantment != null && spell != null
                && enchantment.getAttachedTo() != null
                && enchantment.getAttachedTo().equals(spell.getControllerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(getSourceId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted player casts a spell, put a spite counter on {this}";
    }

    @Override
    public CurseOfVengeanceTriggeredAbility copy() {
        return new CurseOfVengeanceTriggeredAbility(this);
    }
}

class CurseOfVengeancePlayerLosesTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfVengeancePlayerLosesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfVengeanceDrawLifeEffect(), false);
    }

    public CurseOfVengeancePlayerLosesTriggeredAbility(final CurseOfVengeancePlayerLosesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfVengeancePlayerLosesTriggeredAbility copy() {
        return new CurseOfVengeancePlayerLosesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.LOST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getPermanent(this.getSourceId()).getAttachedTo().equals(event.getPlayerId());
    }

    @Override
    public String getRule() {
        return "When enchanted player loses the game, you gain X life and draw X cards, where X is the number of spite counters on Curse of Vengeance";
    }
}

class CurseOfVengeanceDrawLifeEffect extends OneShotEffect {

    public CurseOfVengeanceDrawLifeEffect() {
        super(Outcome.Benefit);
        staticText = "you gain X life and draw X cards, where X is the number of spite counters on {this}";
    }

    public CurseOfVengeanceDrawLifeEffect(final CurseOfVengeanceDrawLifeEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfVengeanceDrawLifeEffect copy() {
        return new CurseOfVengeanceDrawLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = (Permanent) source.getSourceObjectIfItStillExists(game);

        if (sourceObject != null) {
            if (sourceObject.getCounters(game).containsKey(CounterType.SPITE)) {
                controller.drawCards(sourceObject.getCounters(game).getCount(CounterType.SPITE), game);
                controller.gainLife(sourceObject.getCounters(game).getCount(CounterType.SPITE), game);
            }
        }
        return false;
    }
}
