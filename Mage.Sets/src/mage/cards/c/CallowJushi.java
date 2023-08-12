
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class CallowJushi extends CardImpl {

    public CallowJushi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Jaraku the Interloper";

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Callow Jushi.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));

        // At the beginning of the end step, if there are two or more ki counters on Callow Jushi, you may flip it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new FlipSourceEffect(new JarakuTheInterloper()), true),
                new SourceHasCounterCondition(CounterType.KI, 2, Integer.MAX_VALUE),
                "At the beginning of the end step, if there are two or more ki counters on {this}, you may flip it."));
    }

    private CallowJushi(final CallowJushi card) {
        super(card);
    }

    @Override
    public CallowJushi copy() {
        return new CallowJushi(this);
    }
}

class JarakuTheInterloper extends TokenImpl {

    JarakuTheInterloper() {
        super("Jaraku the Interloper", "");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(3);
        toughness = new MageInt(4);

        // Remove a ki counter from Jaraku the Interloper: Counter target spell unless its controller pays {2}.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CounterUnlessPaysEffect(new GenericManaCost(2)),
                new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }
    public JarakuTheInterloper(final JarakuTheInterloper token) {
        super(token);
    }

    public JarakuTheInterloper copy() {
        return new JarakuTheInterloper(this);
    }
}
