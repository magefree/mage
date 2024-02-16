package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CazurRuthlessStalker extends CardImpl {

    public CazurRuthlessStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Ukkima, Stalking Shadow
        this.addAbility(new PartnerWithAbility("Ukkima, Stalking Shadow"));

        // Whenever a creature you control deals combat damage to a player, put a +1/+1 counter on that creature.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false,
                SetTargetPointer.PERMANENT, true
        ));
    }

    private CazurRuthlessStalker(final CazurRuthlessStalker card) {
        super(card);
    }

    @Override
    public CazurRuthlessStalker copy() {
        return new CazurRuthlessStalker(this);
    }
}
