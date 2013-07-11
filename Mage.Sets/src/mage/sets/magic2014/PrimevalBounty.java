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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.BeastToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class PrimevalBounty extends CardImpl<PrimevalBounty> {

    private static final FilterSpell filterCreature = new FilterSpell("a creature spell");
    private static final FilterSpell filterNonCreature = new FilterSpell("a noncreature spell");
    static {
        filterCreature.add(new CardTypePredicate(CardType.CREATURE));
        filterNonCreature.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public PrimevalBounty(UUID ownerId) {
        super(ownerId, 190, "Primeval Bounty", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{5}{G}");
        this.expansionSetCode = "M14";

        this.color.setGreen(true);

        // Whenever you cast a creature spell, put a 3/3 green Beast creature token onto the battlefield.
        this.addAbility(new SpellCastTriggeredAbility(new CreateTokenEffect(new BeastToken()), filterCreature, false));

        // Whenever you cast a noncreature spell, put three +1/+1 counters on target creature you control.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(3));
        Ability ability = new SpellCastTriggeredAbility(effect, filterNonCreature, false);
        ability.addTarget(new TargetControlledCreaturePermanent(true));
        this.addAbility(ability);

        // Whenever a land enters the battlefield under your control, you gain 3 life. 
        effect = new GainLifeEffect(3);
        ability = new EntersBattlefieldControlledTriggeredAbility(effect, new FilterLandPermanent("a land"));
        this.addAbility(ability);

    }

    public PrimevalBounty(final PrimevalBounty card) {
        super(card);
    }

    @Override
    public PrimevalBounty copy() {
        return new PrimevalBounty(this);
    }
}
