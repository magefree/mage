package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ResplendentGriffin extends CardImpl {

    public ResplendentGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ascend
        this.addAbility(new AscendAbility());

        // Whenever Resplendent Griffin attacks, if you have the city's blessing, put a +1/+1 counter on it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new AttacksTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false), CitysBlessingCondition.instance,
                "Whenever {this} attacks, if you have the city's blessing, put a +1/+1 counter on it.")
                .addHint(CitysBlessingHint.instance));
    }

    private ResplendentGriffin(final ResplendentGriffin card) {
        super(card);
    }

    @Override
    public ResplendentGriffin copy() {
        return new ResplendentGriffin(this);
    }
}
