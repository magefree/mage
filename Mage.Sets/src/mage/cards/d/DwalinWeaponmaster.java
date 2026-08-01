package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class DwalinWeaponmaster extends CardImpl {

    public DwalinWeaponmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Dwalin enters or attacks, put a hone counter on each Equipment you control.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new AddCountersAllEffect(
                CounterType.HONE.createInstance(),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT
            )
        ));
    }

    private DwalinWeaponmaster(final DwalinWeaponmaster card) {
        super(card);
    }

    @Override
    public DwalinWeaponmaster copy() {
        return new DwalinWeaponmaster(this);
    }
}
