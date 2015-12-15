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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.PlantToken;

/**
 *
 * @author fireshoes
 */
public class NissaVoiceOfZendikar extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("lands you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public NissaVoiceOfZendikar(UUID ownerId) {
        super(ownerId, 138, "Nissa, Voice of Zendikar", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{G}");
        this.expansionSetCode = "OGW";
        this.subtype.add("Nissa");
        
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Put a 0/1 green Plant creature token onto the battlefield.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new PlantToken()), 1));
        
        // -2: Put a +1/+1 counter on each creature you control.
        this.addAbility(new LoyaltyAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()), -2));
        
        // -7: You gain X life and draw X cards, where X is the number of lands you control.
        Effect effect = new GainLifeEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("you gain X life");
        LoyaltyAbility ability = new LoyaltyAbility(effect, -7);
        effect = new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("and draw X cards, where X is the number of lands you control");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public NissaVoiceOfZendikar(final NissaVoiceOfZendikar card) {
        super(card);
    }

    @Override
    public NissaVoiceOfZendikar copy() {
        return new NissaVoiceOfZendikar(this);
    }
}
