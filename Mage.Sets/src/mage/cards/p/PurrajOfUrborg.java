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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public class PurrajOfUrborg extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a black spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public PurrajOfUrborg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Purraj of Urborg has first strike as long as it's attacking.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                        SourceAttackingCondition.instance,
                        "{this} has first strike as long as it's attacking"
                )
        ));

        // Whenever a player casts a black spell, you may pay {B}. If you do, put a +1/+1 counter on Purraj of Urborg.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        new ManaCostsImpl("{B}")
                ),
                filter, false
        ));
    }

    public PurrajOfUrborg(final PurrajOfUrborg card) {
        super(card);
    }

    @Override
    public PurrajOfUrborg copy() {
        return new PurrajOfUrborg(this);
    }
}
