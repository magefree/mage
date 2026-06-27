package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HulkBrutalBrawler extends CardImpl {

    public HulkBrutalBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Hulk attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever Hulk attacks, put a +1/+1 counter on each other creature you control.
        this.addAbility(new AttacksTriggeredAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE)
        ));
    }

    private HulkBrutalBrawler(final HulkBrutalBrawler card) {
        super(card);
    }

    @Override
    public HulkBrutalBrawler copy() {
        return new HulkBrutalBrawler(this);
    }
}
