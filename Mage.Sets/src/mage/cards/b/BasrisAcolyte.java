package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BasrisAcolyte extends CardImpl {

    public BasrisAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Basri's Acolyte enters the battlefield, put a +1/+1 counter on each of up to two other target creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on each of up to two other target creatures you control")
        );
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES, false));
        this.addAbility(ability);
    }

    private BasrisAcolyte(final BasrisAcolyte card) {
        super(card);
    }

    @Override
    public BasrisAcolyte copy() {
        return new BasrisAcolyte(this);
    }
}
