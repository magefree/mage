package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbigaleEloquentFirstYear extends CardImpl {

    public AbigaleEloquentFirstYear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W/B}{W/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Abigale enters, up to one other target creature loses all abilities. Put a flying counter, a first strike counter, and a lifelink counter on that creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseAllAbilitiesTargetEffect(Duration.Custom)
                .setText("up to one other target creature loses all abilities"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.FLYING.createInstance())
                .setText("Put a flying counter"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.FIRST_STRIKE.createInstance())
                .setText(", a first strike counter"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.LIFELINK.createInstance())
                .setText(", and a lifelink counter on that creature"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private AbigaleEloquentFirstYear(final AbigaleEloquentFirstYear card) {
        super(card);
    }

    @Override
    public AbigaleEloquentFirstYear copy() {
        return new AbigaleEloquentFirstYear(this);
    }
}
