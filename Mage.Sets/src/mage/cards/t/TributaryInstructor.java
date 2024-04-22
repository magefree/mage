package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TributaryInstructor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with a +1/+1 counter on it");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public TributaryInstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Mentor
        this.addAbility(new MentorAbility());

        // Whenever a creature you control with a +1/+1 counter on it dies, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, filter
        ));
    }

    private TributaryInstructor(final TributaryInstructor card) {
        super(card);
    }

    @Override
    public TributaryInstructor copy() {
        return new TributaryInstructor(this);
    }
}
