package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class Bloodtracker extends CardImpl {

    public Bloodtracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {B}, Pay 2 life: Put a +1/+1 counter on Bloodtracker.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{B}"));
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);

        // When Bloodtracker leaves the battlefield, draw a card for each +1/+1 counter on it.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.P1P1))
                        .setText("draw a card for each +1/+1 counter on it"), false
        ));
    }

    private Bloodtracker(final Bloodtracker card) {
        super(card);
    }

    @Override
    public Bloodtracker copy() {
        return new Bloodtracker(this);
    }
}
