package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class LightOfTheLegion extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("white creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public LightOfTheLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Mentor
        this.addAbility(new MentorAbility());

        // When Light of the Legion dies, put a +1/+1 counter on each white creature you control.
        this.addAbility(new DiesSourceTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), filter
        )));
    }

    private LightOfTheLegion(final LightOfTheLegion card) {
        super(card);
    }

    @Override
    public LightOfTheLegion copy() {
        return new LightOfTheLegion(this);
    }
}
