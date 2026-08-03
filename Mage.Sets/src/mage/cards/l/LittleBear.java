package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TargetHasSubtypeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class LittleBear extends CardImpl {

    public LittleBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this creature enters, untap another target creature you control. If that creature is a Bear, put a +1/+1 counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new UntapTargetEffect(), false);
        ability.addEffect(new ConditionalOneShotEffect(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            new TargetHasSubtypeCondition(SubType.BEAR),
            "If that creature is a Bear, put a +1/+1 counter on it"
        ));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private LittleBear(final LittleBear card) {
        super(card);
    }

    @Override
    public LittleBear copy() {
        return new LittleBear(this);
    }
}
