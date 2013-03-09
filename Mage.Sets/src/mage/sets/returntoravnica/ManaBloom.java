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

package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.HasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.mana.ActivateOncePerTurnManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class ManaBloom extends CardImpl<ManaBloom> {

    static final String rule = "with X charge counters on it";

    public ManaBloom (UUID ownerId) {
        super(ownerId, 130, "Mana Bloom", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{X}{G}");
        this.expansionSetCode = "RTR";

        this.color.setGreen(true);

        // Mana Bloom enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new ManaBloomEffect(),rule));

        // Remove a charge counter from Mana Bloom: Add one mana of any color to your mana pool. Activate this ability only once each turn.
        Ability ability = new ActivateOncePerTurnManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addChoice(new ChoiceColor());
        this.addAbility(ability);

        // At the beginning of your upkeep, if Mana Bloom has no charge counters on it, return it to its owner's hand.
        TriggeredAbility triggeredAbility = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandSourceEffect(), Constants.TargetController.YOU, false);
        this.addAbility(new ConditionalTriggeredAbility(triggeredAbility, new HasCounterCondition(CounterType.CHARGE, 0,0), "At the beginning of your upkeep, if Mana Bloom has no charge counters on it, return it to its owner's hand."));

    }

    public ManaBloom (final ManaBloom card) {
        super(card);
    }

    @Override
    public ManaBloom copy() {
        return new ManaBloom(this);
    }
}

class ManaBloomEffect extends OneShotEffect<ManaBloomEffect> {
    public ManaBloomEffect() {
        super(Outcome.Benefit);
    }

    public ManaBloomEffect(final ManaBloomEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (obj != null && obj instanceof SpellAbility) {
                int amount = ((SpellAbility) obj).getManaCostsToPay().getX();
                if (amount > 0) {
                    permanent.addCounters(CounterType.CHARGE.createInstance(amount), game);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public ManaBloomEffect copy() {
        return new ManaBloomEffect(this);
    }
}