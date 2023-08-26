package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaviorOfTheSleeping extends CardImpl {

    public SaviorOfTheSleeping(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever an enchantment you control is put into a graveyard from the battlefield, put a +1/+1 counter on Savior of the Sleeping.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_AN_ENCHANTMENT, false
        ));
    }

    private SaviorOfTheSleeping(final SaviorOfTheSleeping card) {
        super(card);
    }

    @Override
    public SaviorOfTheSleeping copy() {
        return new SaviorOfTheSleeping(this);
    }
}
