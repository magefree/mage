package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TargetObjectMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class DriftgloomCoyote extends CardImpl {
    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 2));
    }

    public DriftgloomCoyote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.COYOTE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Driftgloom Coyote enters, exile target creature an opponent controls until Driftgloom Coyote leaves the battlefield. If that creature had power 2 or less, put a +1/+1 counter on Driftgloom Coyote.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addEffect(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new TargetObjectMatchesFilterCondition("that creature had power 2 or less", filter)));
        this.addAbility(ability);
    }

    private DriftgloomCoyote(final DriftgloomCoyote card) {
        super(card);
    }

    @Override
    public DriftgloomCoyote copy() {
        return new DriftgloomCoyote(this);
    }
}
