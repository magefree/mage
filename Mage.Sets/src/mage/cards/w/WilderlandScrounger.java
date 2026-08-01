package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WilderlandScrounger extends CardImpl {

    public WilderlandScrounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Ferocious -- Whenever this creature attacks while you control a creature with power 4 or greater, put a +1/+1 counter on each creature you control.
        this.addAbility(new AttacksTriggeredAbility(
            new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                StaticFilters.FILTER_CONTROLLED_CREATURE
            )).withTriggerCondition(FerociousCondition.instance)
            .setAbilityWord(AbilityWord.FEROCIOUS)
            .addHint(FerociousHint.instance)
        );
    }

    private WilderlandScrounger(final WilderlandScrounger card) {
        super(card);
    }

    @Override
    public WilderlandScrounger copy() {
        return new WilderlandScrounger(this);
    }
}
