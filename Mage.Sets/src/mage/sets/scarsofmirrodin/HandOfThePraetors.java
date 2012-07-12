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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.target.TargetPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Viserion, North
 */
public class HandOfThePraetors extends CardImpl<HandOfThePraetors> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with infect");
    private static final FilterSpell filterSpell = new FilterSpell("a creature spell with infect");

    static {
        filter.add(new AbilityPredicate(InfectAbility.class));
        filterSpell.add(new AbilityPredicate(InfectAbility.class));
        filterSpell.getCardType().add(CardType.CREATURE);
        filterSpell.setScopeCardType(Filter.ComparisonScope.Any);
    }

    public HandOfThePraetors (UUID ownerId) {
        super(ownerId, 66, "Hand of the Praetors", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        SpellCastTriggeredAbility ability = new SpellCastTriggeredAbility(new AddPoisonCounterTargetEffect(1), filterSpell, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public HandOfThePraetors (final HandOfThePraetors card) {
        super(card);
    }

    @Override
    public HandOfThePraetors copy() {
        return new HandOfThePraetors(this);
    }

}
