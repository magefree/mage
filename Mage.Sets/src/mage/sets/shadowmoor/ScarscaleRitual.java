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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class ScarscaleRitual extends CardImpl {

    public ScarscaleRitual(UUID ownerId) {
        super(ownerId, 175, "Scarscale Ritual", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{U/B}");
        this.expansionSetCode = "SHM";

        // As an additional cost to cast Scarscale Ritual, put a -1/-1 counter on a creature you control.
        this.getSpellAbility().addCost(new ScarscaleRitualCost());

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

    }

    public ScarscaleRitual(final ScarscaleRitual card) {
        super(card);
    }

    @Override
    public ScarscaleRitual copy() {
        return new ScarscaleRitual(this);
    }
}

class ScarscaleRitualCost extends CostImpl {

    public ScarscaleRitualCost() {
        this.text = "put a -1/-1 counter on a creature you control";
    }

    public ScarscaleRitualCost(ScarscaleRitualCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), controllerId, game)) {
            return permanent != null;
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            Target target = new TargetControlledCreaturePermanent();
            target.setNotTarget(true);
            controller.chooseTarget(Outcome.UnboostCreature, target, ability, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(CounterType.M1M1.createInstance(), game);
                game.informPlayers(controller.getLogName() + " puts a -1/-1 counter on " + permanent.getLogName());
                this.paid = true;
            }

        }
        return paid;
    }

    @Override
    public ScarscaleRitualCost copy() {
        return new ScarscaleRitualCost(this);
    }
}
