package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaptainStormCosmiumRaider extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.PIRATE);

    public CaptainStormCosmiumRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an artifact enters the battlefield under your control, put a +1/+1 on target Pirate you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private CaptainStormCosmiumRaider(final CaptainStormCosmiumRaider card) {
        super(card);
    }

    @Override
    public CaptainStormCosmiumRaider copy() {
        return new CaptainStormCosmiumRaider(this);
    }
}
