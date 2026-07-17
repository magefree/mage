package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class MerchantOfVenom extends CardImpl {

    public MerchantOfVenom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When this creature enters, each player sacrifices a creature of their choice.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new SacrificeAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE)
        ));

        // Whenever a player sacrifices a permanent, put a +1/+1 counter on this creature.
        this.addAbility(new SacrificePermanentTriggeredAbility(
            Zone.BATTLEFIELD,
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            StaticFilters.FILTER_PERMANENT,
            TargetController.ANY,
            SetTargetPointer.NONE,
            false
        ));
    }

    private MerchantOfVenom(final MerchantOfVenom card) {
        super(card);
    }

    @Override
    public MerchantOfVenom copy() {
        return new MerchantOfVenom(this);
    }
}
