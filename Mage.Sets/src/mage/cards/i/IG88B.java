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
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public class IG88B extends CardImpl {

    public IG88B(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Droid");
        this.subtype.add("Hunter");
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // <i>Bounty</i> &mdash; Whenever IF-88B deals combat damage to a player, that player loses life equal to the number of bounty counters on creatures he or she controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new LoseLifeTargetEffect(new CountersOnDefendingPlayerCreaturesCount(CounterType.BOUNTY)),
                false,
                "<i>Bounty</i> &mdash; Whenever {this} deals combat damage to a player, that player loses life equal to the number of bounty counters on creatures he or she controls",
                true)
        );

        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    public IG88B(final IG88B card) {
        super(card);
    }

    @Override
    public IG88B copy() {
        return new IG88B(this);
    }
}

class CountersOnDefendingPlayerCreaturesCount implements DynamicValue {

    private CounterType counterType;

    public CountersOnDefendingPlayerCreaturesCount(CounterType counterType) {
        this.counterType = counterType;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        UUID defender = game.getCombat().getDefendingPlayerId(sourceAbility.getSourceId(), game);
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, defender, game)) {
            count += permanent.getCounters(game).getCount(counterType);
        }
        return count;
    }

    @Override
    public CountersOnDefendingPlayerCreaturesCount copy() {
        return new CountersOnDefendingPlayerCreaturesCount(counterType);
    }

    @Override
    public String getMessage() {
        return "the number of " + counterType.getName() + " counters on creatures he or she controls";
    }
}
