package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldUntappedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IdyllicGrange extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.PLAINS);

    static {
        filter.add(AnotherPredicate.instance);
    }
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.FEWER_THAN, 3);

    public IdyllicGrange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);

        // ({T}: Add {W}.)
        this.addAbility(new WhiteManaAbility());

        // Idyllic Grange enters the battlefield tapped unless you control three or more other Plains.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new TapSourceEffect(), condition),
                "tapped unless you control three or more other Plains"
        ));

        // When Idyllic Grange enters the battlefield untapped, put a +1/+1 counter on target creature you control.
        Ability ability = new EntersBattlefieldUntappedTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private IdyllicGrange(final IdyllicGrange card) {
        super(card);
    }

    @Override
    public IdyllicGrange copy() {
        return new IdyllicGrange(this);
    }
}
