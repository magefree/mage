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
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class ManaCache extends CardImpl {

    public ManaCache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // At the beginning of each player's end step, put a charge counter on Mana Cache for each untapped land that player controls.
        TriggeredAbility ability = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of each player's end step", true, new ManaCacheEffect());
        this.addAbility(ability);
        
        // Remove a charge counter from Mana Cache: Add {C} to your mana pool. Any player may activate this ability but only during their turn before the end step.
        this.addAbility(new ManaCacheManaAbility());
    }

    public ManaCache(final ManaCache card) {
        super(card);
    }

    @Override
    public ManaCache copy() {
        return new ManaCache(this);
    }
}

class ManaCacheEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledLandPermanent();
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public ManaCacheEffect() {
        super(Outcome.Damage);
        this.staticText = "put a charge counter on {this} for each untapped land that player controls";
    }

    @Override
    public Effect copy() {
        return new ManaCacheEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            int controlledUntappedLands = game.getBattlefield().countAll(filter, game.getActivePlayerId(), game);
            sourcePermanent.addCounters(CounterType.CHARGE.createInstance(controlledUntappedLands), source, game);
            return true;
        }
        return false;
    }
}

class ManaCacheManaAbility extends ActivatedManaAbilityImpl {

    public ManaCacheManaAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.ColorlessMana(1)), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.netMana.add(new Mana(0,0,0,0,0,0,0,1));
    }

    public ManaCacheManaAbility(final ManaCacheManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (!super.hasMoreActivationsThisTurn(game) || !(condition == null || condition.apply(game, this))) {
            return false;
        }
        Player player = game.getPlayer(playerId);
        if (player != null && playerId.equals(game.getActivePlayerId()) && game.getStep().getType().isBefore(PhaseStep.END_TURN)) {
            if (costs.canPay(this, sourceId, playerId, game)) {
                this.setControllerId(playerId);
                return true;
            }
        }
        return false;
    }

    @Override
    public ManaCacheManaAbility copy() {
        return new ManaCacheManaAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Any player may activate this ability but only during their turn before the end step.";
    }
}
