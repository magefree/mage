package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.SquidToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChasmSkulker extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public ChasmSkulker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.SQUID);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you draw a card, put a +1/+1 counter on Chasm Skulker.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));

        // When Chasm Skulker dies, create X 1/1 blue Squid creature tokens with islandwalk, where X is the number of +1/+1 counters on Chasm Skulker.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SquidToken(), xValue)
                .setText("create X 1/1 blue Squid creature tokens with islandwalk, " +
                        "where X is the number of +1/+1 counters on {this}"), false));
    }

    private ChasmSkulker(final ChasmSkulker card) {
        super(card);
    }

    @Override
    public ChasmSkulker copy() {
        return new ChasmSkulker(this);
    }
}
