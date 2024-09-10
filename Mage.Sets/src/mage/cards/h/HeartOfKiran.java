package mage.cards.h;

import mage.MageInt;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class HeartOfKiran extends CardImpl {

    public HeartOfKiran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Crew 3
        // You may remove a loyalty counter from a planeswalker you control rather than pay Heart of Kiran's crew cost.
        this.addAbility(new CrewAbility(
                3,
                new RemoveCounterCost(new TargetControlledPermanent(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
                ), CounterType.LOYALTY)
        ));
    }

    private HeartOfKiran(final HeartOfKiran card) {
        super(card);
    }

    @Override
    public HeartOfKiran copy() {
        return new HeartOfKiran(this);
    }
}
