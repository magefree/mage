package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.IxalanVampireToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElendasHierophant extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public ElendasHierophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you gain life, put a +1/+1 counter on Elenda's Hierophant.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // When Elenda's Hierophant dies, create X 1/1 white Vampire creature tokens with lifelink, where X is its power.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new IxalanVampireToken(), xValue)
                .setText("create X 1/1 white Vampire creature tokens with lifelink, where X is its power")));
    }

    private ElendasHierophant(final ElendasHierophant card) {
        super(card);
    }

    @Override
    public ElendasHierophant copy() {
        return new ElendasHierophant(this);
    }
}
