package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelExemplar extends CardImpl {

    public SteelExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Steel Exemplar enters the battlefield with two +1/+1 counters on it unless two or more colors of mana were spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                SteelExemplarCondition.instance, "{this} enters the battlefield with " +
                "two +1/+1 counters on it unless two or more colors of mana were spent to cast it.", ""
        ));
    }

    private SteelExemplar(final SteelExemplar card) {
        super(card);
    }

    @Override
    public SteelExemplar copy() {
        return new SteelExemplar(this);
    }
}

enum SteelExemplarCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return ColorsOfManaSpentToCastCount.getInstance().calculate(game, source, null) < 2;
    }
}