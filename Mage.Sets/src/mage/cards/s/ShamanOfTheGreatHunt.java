
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author LevelX2
 */
public final class ShamanOfTheGreatHunt extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }
    
    public ShamanOfTheGreatHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Whenever a creature you control deals combat damage to a player, put a +1/+1 counter on that creature.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("put a +1/+1 counter on that creature");
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                effect,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false, SetTargetPointer.PERMANENT, true
        ));
        
        // <i>Ferocious</i> &mdash; {2}{G/U}{G/U}: Draw a card for each creature you control with power 4 or greater.
        DynamicValue amount = new PermanentsOnBattlefieldCount(filter);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(amount), new ManaCostsImpl("{2}{G/U}{G/U}"));
        ability.setAbilityWord(AbilityWord.FEROCIOUS);
        this.addAbility(ability);
        
    }

    private ShamanOfTheGreatHunt(final ShamanOfTheGreatHunt card) {
        super(card);
    }

    @Override
    public ShamanOfTheGreatHunt copy() {
        return new ShamanOfTheGreatHunt(this);
    }
}
