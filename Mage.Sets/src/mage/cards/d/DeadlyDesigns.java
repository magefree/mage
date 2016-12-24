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
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class DeadlyDesigns extends CardImpl {

    public DeadlyDesigns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // {2}: Put a plot counter on Deadly Designs. Any player may activate this ability.
        SimpleActivatedAbility activatedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.PLOT.createInstance()), new GenericManaCost(2));
        activatedAbility.setMayActivate(TargetController.ANY);
        activatedAbility.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(activatedAbility);

        // When there are five or more plot counters on Deadly Designs, sacrifice it. If you do, destroy up to two target creatures.
        StateTriggeredAbility triggerredAbility = new DeadlyDesignsTriggerAbility();
        triggerredAbility.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(triggerredAbility);
    }

    public DeadlyDesigns(final DeadlyDesigns card) {
        super(card);
    }

    @Override
    public DeadlyDesigns copy() {
        return new DeadlyDesigns(this);
    }
}

class DeadlyDesignsTriggerAbility extends StateTriggeredAbility {

    public DeadlyDesignsTriggerAbility() {
        super(Zone.BATTLEFIELD, new DeadlyDesignsEffect());
    }

    public DeadlyDesignsTriggerAbility(final DeadlyDesignsTriggerAbility ability) {
        super(ability);
    }

    @Override
    public DeadlyDesignsTriggerAbility copy() {
        return new DeadlyDesignsTriggerAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.PLOT) > 4;
    }

    @Override
    public String getRule() {
        return "When there are five or more plot counters on {this}, sacrifice it. If you do, destroy up to two target creatures";
    }
}

class DeadlyDesignsEffect extends SacrificeSourceEffect {

    private boolean sacrificed = false;

    public DeadlyDesignsEffect() {
        super();
    }

    public DeadlyDesignsEffect(final DeadlyDesignsEffect effect) {
        super(effect);
        this.sacrificed = effect.sacrificed;
    }

    @Override
    public DeadlyDesignsEffect copy() {
        return new DeadlyDesignsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        sacrificed = super.apply(game, source);
        if (sacrificed) {
            Permanent toDestroy;
            for (UUID target : getTargetPointer().getTargets(game, source)) {
                toDestroy = game.getPermanent(target);
                if (toDestroy != null) {
                    toDestroy.destroy(source.getId(), game, false);
                }
            }
        }
        return sacrificed;
    }

    public boolean isSacrificed() {
        return sacrificed;
    }
}
