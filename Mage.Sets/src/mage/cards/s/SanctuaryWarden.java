package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanctuaryWarden extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("a creature or planeswalker you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public SanctuaryWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sanctuary Warden enters the battlefield with two shield counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHIELD.createInstance(2)),
                "with two shield counters on it. <i>(If it would be dealt damage " +
                        "or destroyed, remove a shield counter from it instead.)</i>"
        ));

        // Whenever Sanctuary Warden enters the battlefield or attacks, you may remove a counter from a creature or planeswalker you control. If you do, draw a card and create a 1/1 green and white Citizen creature token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1),
                        new RemoveCounterCost(new TargetControlledPermanent(filter))
                ).addEffect(new CreateTokenEffect(new CitizenGreenWhiteToken()))
        ));
    }

    private SanctuaryWarden(final SanctuaryWarden card) {
        super(card);
    }

    @Override
    public SanctuaryWarden copy() {
        return new SanctuaryWarden(this);
    }
}
