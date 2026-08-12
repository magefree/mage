package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.other.PowerUpAbilityPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class MarvelBoyNohVarr extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("a power-up ability");

    static {
        filter.add(PowerUpAbilityPredicate.instance);
    }

    public MarvelBoyNohVarr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KREE);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature you control enters and whenever you activate a power-up ability, put a +1/+1 counter on Marvel Boy.
        this.addAbility(new OrTriggeredAbility(
            Zone.BATTLEFIELD,
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new EntersBattlefieldControlledTriggeredAbility(null, StaticFilters.FILTER_ANOTHER_CREATURE),
            new ActivateAbilityTriggeredAbility(null, filter, SetTargetPointer.NONE)
        ));
    }

    private MarvelBoyNohVarr(final MarvelBoyNohVarr card) {
        super(card);
    }

    @Override
    public MarvelBoyNohVarr copy() {
        return new MarvelBoyNohVarr(this);
    }
}
