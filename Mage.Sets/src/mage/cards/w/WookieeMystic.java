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
package mage.cards.w;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author Styxo
 */
public class WookieeMystic extends CardImpl {

    public WookieeMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}{W}");
        this.subtype.add(SubType.WOOKIEE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {R}, {G} or {W} to your mana pool. If that mana is spent on a creature spell, it enters the battlefield with a +1/+1 counter on it.
        Mana mana = Mana.RedMana(1);
        mana.setFlag(true);
        ManaEffect effect = new BasicManaEffect(mana);
        effect.setText("Add {R} to your mana pool. If that mana is spent on a creature spell, it enters the battlefield with a +1/+1 counter on it");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability, new WookieeMysticWatcher(ability));

        mana = Mana.GreenMana(1);
        mana.setFlag(true);
        effect = new BasicManaEffect(mana);
        effect.setText("Add {G} to your mana pool. If that mana is spent on a creature spell, it enters the battlefield with a +1/+1 counter on it");
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability, new WookieeMysticWatcher(ability));

        mana = Mana.WhiteMana(1);
        mana.setFlag(true);
        effect = new BasicManaEffect(mana);
        effect.setText("Add {W} to your mana pool. If that mana is spent on a creature spell, it enters the battlefield with a +1/+1 counter on it");
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability, new WookieeMysticWatcher(ability));
    }

    public WookieeMystic(final WookieeMystic card) {
        super(card);
    }

    @Override
    public WookieeMystic copy() {
        return new WookieeMystic(this);
    }
}

class WookieeMysticWatcher extends Watcher {

    private final Ability source;
    private final List<UUID> creatures = new ArrayList<>();

    WookieeMysticWatcher(Ability source) {
        super("HallOfTheBanditLordWatcher", WatcherScope.CARD);
        this.source = source;
    }

    WookieeMysticWatcher(final WookieeMysticWatcher watcher) {
        super(watcher);
        this.creatures.addAll(watcher.creatures);
        this.source = watcher.source;
    }

    @Override
    public WookieeMysticWatcher copy() {
        return new WookieeMysticWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            MageObject target = game.getObject(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId())
                    && target != null && target.isCreature()
                    && event.getFlag()) {
                if (target instanceof Spell) {
                    this.creatures.add(((Spell) target).getCard().getId());
                }
            }
        }
        if (event.getType() == GameEvent.EventType.COUNTERED) {
            if (creatures.contains(event.getTargetId())) {
                creatures.remove(event.getSourceId());
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            if (creatures.contains(event.getSourceId())) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                // spell was e.g. exiled and goes again to stack, so previous cast has not resolved.
                if (zEvent.getToZone() == Zone.STACK) {
                    creatures.remove(event.getSourceId());
                }
            }
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            if (creatures.contains(event.getSourceId())) {
                Permanent creature = game.getPermanent(event.getSourceId());
                creature.addCounters(CounterType.P1P1.createInstance(), source, game);
                creatures.remove(event.getSourceId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatures.clear();
    }

}
