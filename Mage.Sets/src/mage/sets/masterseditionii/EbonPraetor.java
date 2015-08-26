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
package mage.sets.masterseditionii;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class EbonPraetor extends CardImpl {

    public EbonPraetor(UUID ownerId) {
        super(ownerId, 89, "Ebon Praetor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "ME2";
        this.subtype.add("Avatar");
        this.subtype.add("Praetor");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // At the beginning of your upkeep, put a -2/-2 counter on Ebon Praetor.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.M2M2.createInstance()), TargetController.YOU, false));
        
        // Sacrifice a creature: Remove a -2/-2 counter from Ebon Praetor. If the sacrificed creature was a Thrull, put a +1/+0 counter on Ebon Praetor. Activate this ability only during your upkeep and only once each turn.
        Ability ability = new EbonPraetorAbility(new RemoveCounterSourceEffect(CounterType.M2M2.createInstance()), 
                new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        ability.addEffect(new EbonPraetorEffect());
        this.addAbility(ability);
    }

    public EbonPraetor(final EbonPraetor card) {
        super(card);
    }

    @Override
    public EbonPraetor copy() {
        return new EbonPraetor(this);
    }
}

class EbonPraetorAbility extends LimitedTimesPerTurnActivatedAbility {

    public EbonPraetorAbility(Effect effect, Cost cost) {
        super(Zone.BATTLEFIELD, effect, cost);
    }

    public EbonPraetorAbility(final EbonPraetorAbility ability) {
        super(ability);
    }

    @Override
    public EbonPraetorAbility copy() {
        return new EbonPraetorAbility(this);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (!game.getActivePlayerId().equals(controllerId) || !PhaseStep.UPKEEP.equals(game.getStep().getType())) {
            return false;
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("");
        sb.append(super.getRule()).append(" <i>Activate this ability only during your upkeep.</i>");
        return sb.toString();
    }
}

class EbonPraetorEffect extends OneShotEffect {

    public EbonPraetorEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If the sacrificed creature was a Thrull, put a +1/+0 counter on {this}";
    }

    public EbonPraetorEffect(final EbonPraetorEffect effect) {
        super(effect);
    }

    @Override
    public EbonPraetorEffect copy() {
        return new EbonPraetorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                Permanent sacrificedCreature = ((SacrificeTargetCost) cost).getPermanents().get(0);
                Permanent sourceCreature = game.getPermanent(source.getSourceId());
                if (sacrificedCreature.hasSubtype("Thrull") && sourceCreature != null) {
                    sourceCreature.addCounters(CounterType.P1P0.createInstance(), game);
                    return true;
                }
            }
        }
        return false;
    }
}
