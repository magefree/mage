
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterTeamPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class DecoratedChampion extends CardImpl {

    private static final FilterTeamPermanent filter = new FilterTeamPermanent(SubType.WARRIOR, "another Warrior");

    static {
        filter.add(new AnotherPredicate());
    }

    public DecoratedChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Warrior enters the battlefield under your team's control, put a +1/+1 counter on Decorated Champion.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter,
                "Whenever another Warrior enters the battlefield under your team's control,"
                + " put a +1/+1 counter on {this}"
        ));
    }

    public DecoratedChampion(final DecoratedChampion card) {
        super(card);
    }

    @Override
    public DecoratedChampion copy() {
        return new DecoratedChampion(this);
    }
}
