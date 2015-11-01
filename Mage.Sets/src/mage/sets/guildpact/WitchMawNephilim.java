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
package mage.sets.guildpact;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;

/**
 * @author Loki
 */
public class WitchMawNephilim extends CardImpl {

    public WitchMawNephilim(UUID ownerId) {
        super(ownerId, 138, "Witch-Maw Nephilim", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}{W}{U}{B}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Nephilim");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell, you may put two +1/+1 counters on Witch-Maw Nephilim.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), true));

        // Whenever Witch-Maw Nephilim attacks, it gains trample until end of turn if its power is 10 or greater.
        this.addAbility(new AttacksTriggeredAbility(new WitchMawNephilimEffect(), false));
    }

    public WitchMawNephilim(final WitchMawNephilim card) {
        super(card);
    }

    @Override
    public WitchMawNephilim copy() {
        return new WitchMawNephilim(this);
    }
}

class WitchMawNephilimEffect extends OneShotEffect {

    public WitchMawNephilimEffect() {
        super(Outcome.AddAbility);
        this.staticText = "it gains trample until end of turn if its power is 10 or greater";
    }

    public WitchMawNephilimEffect(final WitchMawNephilimEffect effect) {
        super(effect);
    }

    @Override
    public WitchMawNephilimEffect copy() {
        return new WitchMawNephilimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            if (sourceObject.getPower().getValue() >= 10) {
                game.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}
