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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.MultikickerManaCost;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;


/**
 * @author noxx
 */
public class StrengthOfTheTajuru extends CardImpl<StrengthOfTheTajuru> {

    public StrengthOfTheTajuru(UUID ownerId) {
        super(ownerId, 113, "Strength of the Tajuru", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{G}{G}");
        this.expansionSetCode = "WWK";

        this.color.setGreen(true);


        // Multikicker (You may pay an additional {1} any number of times as you cast this spell.)
        this.addAbility(new KickerAbility(new MultikickerManaCost("{1}")));

        // Choose target creature, then choose another target creature for each time Strength of the Tajuru was kicked. Put X +1/+1 counters on each of them.
        this.getSpellAbility().addEffect(new StrengthOfTheTajuruAddCountersTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int numbTargets = new MultikickerCount().calculate(game, ability) + 1;
            ability.addTarget(new TargetCreaturePermanent(0, numbTargets));
        }
    }

    public StrengthOfTheTajuru(final StrengthOfTheTajuru card) {
        super(card);
    }

    @Override
    public StrengthOfTheTajuru copy() {
        return new StrengthOfTheTajuru(this);
    }
}

class StrengthOfTheTajuruAddCountersTargetEffect extends OneShotEffect<StrengthOfTheTajuruAddCountersTargetEffect> {

    public StrengthOfTheTajuruAddCountersTargetEffect() {
        super(Outcome.BoostCreature);
        staticText = "Choose target creature, then choose another target creature for each time Strength of the Tajuru was kicked. Put X +1/+1 counters on each of them";
    }

    public StrengthOfTheTajuruAddCountersTargetEffect(final StrengthOfTheTajuruAddCountersTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        int amount = source.getManaCostsToPay().getX() + 1;
        Counter counter = CounterType.P1P1.createInstance(amount);
        for (UUID uuid : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(uuid);
            if (permanent != null) {
                permanent.addCounters(counter.copy(), game);
                affectedTargets ++;
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public StrengthOfTheTajuruAddCountersTargetEffect copy() {
        return new StrengthOfTheTajuruAddCountersTargetEffect(this);
    }


}
