package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicSleuth extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(CounterAnyPredicate.instance);
    }

    public AngelicSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another permanent you control leaves the battlefield, if it had counters on it, investigate.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new InvestigateEffect().setText("if it had counters on it, investigate"), filter, false
        ));
    }

    private AngelicSleuth(final AngelicSleuth card) {
        super(card);
    }

    @Override
    public AngelicSleuth copy() {
        return new AngelicSleuth(this);
    }
}
