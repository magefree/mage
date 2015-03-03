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
package mage.sets.invasion;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;

/**
 *
 * @author emerald000
 */
public class KangeeAerieKeeper extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Bird creatures");
    
    static {
        filter.add(new SubtypePredicate("Bird"));
        filter.add(new AnotherPredicate());
    }

    public KangeeAerieKeeper(UUID ownerId) {
        super(ownerId, 253, "Kangee, Aerie Keeper", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.expansionSetCode = "INV";
        this.supertype.add("Legendary");
        this.subtype.add("Bird");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {X}{2}
        this.addAbility(new KickerAbility("{X}{2}"));
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Kangee, Aerie Keeper enters the battlefield, if it was kicked, put X feather counters on it.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.FEATHER.createInstance(), new KangeeAerieKeeperGetKickerXValue(), true));
        this.addAbility(new ConditionalTriggeredAbility(ability, KickedCondition.getInstance(), "When {this} enters the battlefield, if it was kicked, put X feather counters on it."));
        
        // Other Bird creatures get +1/+1 for each feather counter on Kangee, Aerie Keeper.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(new CountersCount(CounterType.FEATHER), new CountersCount(CounterType.FEATHER), Duration.WhileOnBattlefield, filter, true, "Other Bird creatures get +1/+1 for each feather counter on {this}.")));
    }

    public KangeeAerieKeeper(final KangeeAerieKeeper card) {
        super(card);
    }

    @Override
    public KangeeAerieKeeper copy() {
        return new KangeeAerieKeeper(this);
    }
}

class KangeeAerieKeeperGetKickerXValue implements DynamicValue {

    public KangeeAerieKeeperGetKickerXValue() {
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            for (Ability ability: card.getAbilities()) {
                if (ability instanceof KickerAbility) {
                    count += ((KickerAbility) ability).getXManaValue();
                }
            }
        }
        return count;
    }

    @Override
    public KangeeAerieKeeperGetKickerXValue copy() {
        return new KangeeAerieKeeperGetKickerXValue();
    }

    @Override
    public String toString() {
        return "X";
    };

    @Override
    public String getMessage() {
        return "X";
    };
}