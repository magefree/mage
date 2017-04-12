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

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 * @author fireshoes
 */
public class SylvanAdvocate extends CardImpl {

    private static final String rule1 = "As long as you control six or more lands, {this}";
    private static final String rule2 = "and land creatures you control get +2/+2.";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("land creatures");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public SylvanAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add("Elf");
        this.subtype.add("Druid");
        this.subtype.add("Ally");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // As long as you control six or more lands, Sylvan Advocate and land creatures you control get +2/+2.
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledLandPermanent(), ComparisonType.MORE_THAN, 5), rule1);
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, true),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledLandPermanent(), ComparisonType.MORE_THAN, 5), rule2);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public SylvanAdvocate(final SylvanAdvocate card) {
        super(card);
    }

    @Override
    public SylvanAdvocate copy() {
        return new SylvanAdvocate(this);
    }
}
