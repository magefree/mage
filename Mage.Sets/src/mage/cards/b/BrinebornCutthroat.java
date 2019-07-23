package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrinebornCutthroat extends CardImpl {

    public BrinebornCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever you cast a spell during an opponent's turn, put a +1/+1 counter on Brineborn Cutthroat.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastControllerTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
                ), NotMyTurnCondition.instance, "Whenever you cast a spell during an opponent's turn, " +
                "put a +1/+1 counter on {this}."
        ));
    }

    private BrinebornCutthroat(final BrinebornCutthroat card) {
        super(card);
    }

    @Override
    public BrinebornCutthroat copy() {
        return new BrinebornCutthroat(this);
    }
}
