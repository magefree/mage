package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author arcox
 */
public final class SingerOfSwiftRivers extends CardImpl {

    private static final FilterCard filter = new FilterCard("Merfolk spells");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public SingerOfSwiftRivers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Singer of Swift Rivers enters the battlefield, put a shield counter on another target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.SHIELD.createInstance())
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // You may cast Merfolk spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter, false)));
    }

    private SingerOfSwiftRivers(final SingerOfSwiftRivers card) {
        super(card);
    }

    @Override
    public SingerOfSwiftRivers copy() {
        return new SingerOfSwiftRivers(this);
    }
}
