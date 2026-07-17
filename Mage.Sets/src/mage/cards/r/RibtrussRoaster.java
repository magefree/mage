package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.PestBlackGreenDiesToken;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RibtrussRoaster extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public RibtrussRoaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devour 1
        this.addAbility(new DevourAbility(1));

        // At the beginning of your end step, create a number of 1/1 black and green Pest creature tokens equal to the number of +1/+1 counters on this creature. They have "When this token dies, you gain 1 life."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(
            new PestBlackGreenDiesToken(), xValue
        ).setText("create a number of 1/1 black and green Pest creature tokens equal to the number of +1/+1 counters on this creature. They have \"When this token dies, you gain 1 life.\"")));
    }

    private RibtrussRoaster(final RibtrussRoaster card) {
        super(card);
    }

    @Override
    public RibtrussRoaster copy() {
        return new RibtrussRoaster(this);
    }
}
