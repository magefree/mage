package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeddingSecurity extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Blood token");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(SubType.BLOOD.getPredicate());
    }

    public WeddingSecurity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Wedding Security attacks, you may sacrifice a Blood token. If you do, put a +1/+1 counter on Wedding Security and draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        ).addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"))));
    }

    private WeddingSecurity(final WeddingSecurity card) {
        super(card);
    }

    @Override
    public WeddingSecurity copy() {
        return new WeddingSecurity(this);
    }
}
