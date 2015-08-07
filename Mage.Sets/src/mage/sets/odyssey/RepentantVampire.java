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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiesAndDealtDamageThisTurnTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33, Nantuko (Sengir Vampire)
 */
public class RepentantVampire extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public RepentantVampire(UUID ownerId) {
        super(ownerId, 157, "Repentant Vampire", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Vampire");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a creature dealt damage by Repentant Vampire this turn dies, put a +1/+1 counter on Repentant Vampire.
        this.addAbility(new DiesAndDealtDamageThisTurnTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
        // Threshold - As long as seven or more cards are in your graveyard, Repentant Vampire is white and has "{tap}: Destroy target black creature."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BecomesColorSourceEffect(ObjectColor.WHITE, Duration.WhileOnBattlefield),
                new CardsInControllerGraveCondition(7),
                "<i>Threshold</i> - As long as seven or more cards are in your graveyard, {this} is white"));
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield),
                new CardsInControllerGraveCondition(7),
                "and has \"{t}: Destroy target black creature.\""));
        this.addAbility(ability);
    }

    public RepentantVampire(final RepentantVampire card) {
        super(card);
    }

    @Override
    public RepentantVampire copy() {
        return new RepentantVampire(this);
    }
}
