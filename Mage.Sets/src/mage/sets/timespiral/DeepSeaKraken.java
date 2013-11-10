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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.keyword.UnblockableAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.common.TimeCounter;
import mage.filter.FilterSpell;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public class DeepSeaKraken extends CardImpl<DeepSeaKraken> {

    private static final FilterSpell filter = new FilterSpell("an opponent casts");
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public DeepSeaKraken(UUID ownerId) {
        super(ownerId, 56, "Deep-Sea Kraken", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{7}{U}{U}{U}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Kraken");

        this.color.setBlue(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deep-Sea Kraken is unblockable.
        this.addAbility(new UnblockableAbility());
        // Suspend 9-{2}{U}
        this.addAbility(new SuspendAbility(9, new ManaCostsImpl("{2}{U}"), this));
        // Whenever an opponent casts a spell, if Deep-Sea Kraken is suspended, remove a time counter from it.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastAllTriggeredAbility(new RemoveCounterSourceEffect(new TimeCounter(1)), filter, false), SuspendedCondition.getInstance(),
                "Whenever an opponent casts a spell, if Deep-Sea Kraken is suspended, remove a time counter from it.", false));
    }

    public DeepSeaKraken(final DeepSeaKraken card) {
        super(card);
    }

    @Override
    public DeepSeaKraken copy() {
        return new DeepSeaKraken(this);
    }
}
