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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *     10/4/2004: If the creature regenerates, the fuse counters are still removed and 
 *                the four damage is still dealt.
 *     10/4/2004: If there are two Bomb Squads on the battlefield when a creature ends 
 *                up with 4 or more fuse counters, both Bomb Squad abilities will trigger
 *                causing 4 damage each even though the first to resolve will destroy the
 *                creature.
 *
 * @author LevelX2
 */
public class BombSquad extends CardImpl<BombSquad> {

    public BombSquad(UUID ownerId) {
        super(ownerId, 179, "Bomb Squad", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Dwarf");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Put a fuse counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.FUSE.createInstance()), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);
        // At the beginning of your upkeep, put a fuse counter on each creature with a fuse counter on it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new BombSquadBeginningEffect(), TargetController.YOU, false));
        // Whenever a creature has four or more fuse counters on it, remove all fuse counters from it and destroy it. That creature deals 4 damage to its controller.
        this.addAbility(new BombSquadTriggeredAbility());
    }

    public BombSquad(final BombSquad card) {
        super(card);
    }

    @Override
    public BombSquad copy() {
        return new BombSquad(this);
    }
}

class BombSquadTriggeredAbility extends TriggeredAbilityImpl<BombSquadTriggeredAbility> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public BombSquadTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BombSquadDamgeEffect(), false);
    }

    public BombSquadTriggeredAbility(final BombSquadTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BombSquadTriggeredAbility copy() {
        return new BombSquadTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(EventType.COUNTER_ADDED) && event.getData().equals(CounterType.FUSE.getName())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && filter.match(permanent, game)) {
                if (4 <= permanent.getCounters().getCount(CounterType.FUSE)) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature has four or more fuse counters on it, " + super.getRule();
    }
}

class BombSquadDamgeEffect extends OneShotEffect<BombSquadDamgeEffect> {

    public BombSquadDamgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "remove all fuse counters from it and destroy it. That creature deals 4 damage to its controller";
    }

    public BombSquadDamgeEffect(final BombSquadDamgeEffect effect) {
        super(effect);
    }

    @Override
    public BombSquadDamgeEffect copy() {
        return new BombSquadDamgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature != null) {
            creature.removeCounters(CounterType.FUSE.getName(), creature.getCounters().getCount(CounterType.FUSE), game);
            creature.destroy(source.getSourceId(), game, false);
        }
        if (creature == null) {
            creature = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        }
        if (creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                controller.damage(4, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}


class BombSquadBeginningEffect extends OneShotEffect<BombSquadBeginningEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a fuse counter on it");
    static {
        filter.add(new CounterPredicate(CounterType.FUSE));
    }

    public BombSquadBeginningEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a fuse counter on each creature with a fuse counter on it";
    }

    public BombSquadBeginningEffect(final BombSquadBeginningEffect effect) {
        super(effect);
    }

    @Override
    public BombSquadBeginningEffect copy() {
        return new BombSquadBeginningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            permanent.addCounters(CounterType.FUSE.createInstance(), game);

            game.informPlayers(new StringBuilder(card.getName()).append(" puts a fuse counter on ").append(permanent.getName()).toString());
        }
        return true;
    }
}
