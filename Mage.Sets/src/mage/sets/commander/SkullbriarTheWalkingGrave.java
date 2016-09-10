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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author anonymous
 */
public class SkullbriarTheWalkingGrave extends CardImpl {
    private Counters counters;

    public SkullbriarTheWalkingGrave(UUID ownerId) {
        super(ownerId, 227, "Skullbriar, the Walking Grave", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{G}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Elemental");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Skullbriar, the Walking Grave deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
        // Counters remain on Skullbriar as it moves to any zone other than a player's hand or library.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SkullbriarEffect()));
    }

    public SkullbriarTheWalkingGrave(SkullbriarTheWalkingGrave card) { super(card); }

    @Override
    public SkullbriarTheWalkingGrave copy() {
        return new SkullbriarTheWalkingGrave(this);
    }


    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        boolean skullBriarEffectApplied = false;
        if (event.getToZone() != Zone.HAND && event.getToZone() != Zone.LIBRARY) {
            for (StaticAbility ability : getAbilities (game).getStaticAbilities(event.getFromZone())) {
                for (Effect effect : ability.getEffects(game, EffectType.REPLACEMENT)) {
                    if (effect instanceof SkullbriarEffect && event.getAppliedEffects().contains(effect.getId())) {
                        skullBriarEffectApplied = true;
                    }
                }
            }
        }
        Counters copyFrom = null;
        if (skullBriarEffectApplied) {
            if (event.getTarget() != null && event.getFromZone() == Zone.BATTLEFIELD) {
                copyFrom = new Counters(event.getTarget().getCounters(game));
            } else {
                copyFrom = new Counters(this.getCounters(game));
            }
        }
        super.updateZoneChangeCounter(game, event);
        Counters copyTo = null;
        if (event.getTarget() != null && event.getToZone() == Zone.BATTLEFIELD) {
            if (event.getFromZone() != Zone.BATTLEFIELD) {
                copyTo = event.getTarget().getCounters(game);
            }
        } else {
            copyTo = this.getCounters(game);
        }
        if (copyTo != null && copyFrom != null) {
            for(Counter counter : copyFrom.values()) {
                copyTo.addCounter(counter);
            }
        }
    }
}

class SkullbriarEffect extends ReplacementEffectImpl {
    public SkullbriarEffect() {
        super(Duration.EndOfGame, Outcome.Benefit);
        staticText = "Counters remain on {this} as it moves to any zone other than a player's hand or library.";
    }

    public SkullbriarEffect(SkullbriarEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId());
    }

    @Override
    public ContinuousEffect copy() {
        return new SkullbriarEffect(this);
    }
}