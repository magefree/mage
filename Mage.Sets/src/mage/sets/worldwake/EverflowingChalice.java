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

package mage.sets.worldwake;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EmptyEffect;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com, North
 */
public class EverflowingChalice extends CardImpl<EverflowingChalice> {

    protected static final String rule = "Everflowing Chalice enters the battlefield with a charge counter on it for each time it was kicked";

    public EverflowingChalice(UUID ownerId) {
        super(ownerId, 123, "Everflowing Chalice", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "WWK";

        // Everflowing Chalice enters the battlefield with a charge counter on it for each time it was kicked.
        Ability ability1 = new EntersBattlefieldAbility(new EverflowingChaliceAddCountersEffect());
        this.addAbility(ability1);

        // Multikicker {2} (You may pay an additional {2} any number of times as you cast this spell.)
        MultikickerAbility ability = new MultikickerAbility(new EmptyEffect(rule), false);
        ability.addManaCost(new GenericManaCost(2));
        this.addAbility(ability);

        this.addAbility(new DynamicManaAbility(Mana.ColorlessMana, new CountersCount(CounterType.CHARGE)));
    }

    public EverflowingChalice(final EverflowingChalice card) {
        super(card);
    }

    @Override
    public EverflowingChalice copy() {
        return new EverflowingChalice(this);
    }

}

class EverflowingChaliceAddCountersEffect extends OneShotEffect<EverflowingChaliceAddCountersEffect> {

    public EverflowingChaliceAddCountersEffect() {
        super(Constants.Outcome.Benefit);
        staticText = EverflowingChalice.rule;
    }

    public EverflowingChaliceAddCountersEffect(final EverflowingChaliceAddCountersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            for (Ability ability : permanent.getAbilities()) {
                if (ability instanceof MultikickerAbility) {
                    int count = ((MultikickerAbility)ability).getActivateCount();
                    if (count > 0) {
                        permanent.addCounters(CounterType.CHARGE.createInstance(count), game);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public EverflowingChaliceAddCountersEffect copy() {
        return new EverflowingChaliceAddCountersEffect(this);
    }

}

