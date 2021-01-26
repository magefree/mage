package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestSharedCreatureTypeCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ChangelingAbility;
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
public final class LittjaraKinseekers extends CardImpl {

    public LittjaraKinseekers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // When Littjara Kinseekers enters the battlefield, if you control three or more creatures that share a creature type, put a +1/+1 counter on Littjara Kinseekers, then scry 1.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                ), LittjaraKinseekersCondition.instance, "When {this} enters the battlefield, " +
                "if you control three or more creatures that share a creature type, " +
                "put a +1/+1 counter on {this}, then scry 1."
        );
        ability.addEffect(new ScryEffect(1));
        this.addAbility(ability.addHint(GreatestSharedCreatureTypeCount.getHint()));
    }

    private LittjaraKinseekers(final LittjaraKinseekers card) {
        super(card);
    }

    @Override
    public LittjaraKinseekers copy() {
        return new LittjaraKinseekers(this);
    }
}

enum LittjaraKinseekersCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return GreatestSharedCreatureTypeCount.instance.calculate(game, source, null) >= 3;
    }
}
