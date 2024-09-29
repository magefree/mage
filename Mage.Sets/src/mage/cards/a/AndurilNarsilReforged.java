package mage.cards.a;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class AndurilNarsilReforged extends CardImpl {

    public AndurilNarsilReforged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Ascend
        this.addAbility(new AscendAbility());

        // Whenever equipped creature attacks, put a +1/+1 counter on each creature you control. If you have the city's blessing, put two +1/+1 counters on each creature you control instead.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new ConditionalOneShotEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(2), StaticFilters.FILTER_CONTROLLED_CREATURE),
                        new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE), CitysBlessingCondition.instance,
                        "put a +1/+1 counter on each creature you control. If you have the city's blessing, put two +1/+1 counters on each creature you control instead"),
                AttachmentType.EQUIPMENT, false)
                .addHint(CitysBlessingHint.instance));
        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private AndurilNarsilReforged(final AndurilNarsilReforged card) {
        super(card);
    }

    @Override
    public AndurilNarsilReforged copy() {
        return new AndurilNarsilReforged(this);
    }
}
