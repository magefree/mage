
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class PatronOfTheValiant extends CardImpl {

    public PatronOfTheValiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Patron of the Valiant enters the battlefield, put a +1/+1 counter on each creature you control with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1
        )));
    }

    private PatronOfTheValiant(final PatronOfTheValiant card) {
        super(card);
    }

    @Override
    public PatronOfTheValiant copy() {
        return new PatronOfTheValiant(this);
    }
}
