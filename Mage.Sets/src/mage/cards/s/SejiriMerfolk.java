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
package mage.cards.s;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;

/**
 *
 * @author North, nantuko
 */
public class SejiriMerfolk extends CardImpl {

    private static final String rule1 = "As long as you control a Plains, {this} has first strike.";
    private static final String rule2 = "As long as you control a Plains, {this} has lifelink.";
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Plains");

    static {
        filter.add(new SubtypePredicate(SubType.PLAINS));
    }

    public SejiriMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add("Merfolk");
        this.subtype.add("Soldier");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect1));
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect2));

    }

    public SejiriMerfolk(final SejiriMerfolk card) {
        super(card);
    }

    @Override
    public SejiriMerfolk copy() {
        return new SejiriMerfolk(this);
    }
}
