package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KataraWaterbendingMaster extends CardImpl {

    private static final DynamicValue xValue = new CountersControllerCount(CounterType.EXPERIENCE);

    public KataraWaterbendingMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a spell during an opponent's turn, you get an experience counter.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersPlayersEffect(
                CounterType.EXPERIENCE.createInstance(), TargetController.YOU), false
        ).withTriggerCondition(OpponentsTurnCondition.instance));

        // Whenever Katara attacks, you may draw a card for each experience counter you have. If you do, discard a card.
        Ability ability = new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(xValue)
                .setText("draw a card for each experience counter you have"), true);
        ability.addEffect(new DiscardControllerEffect(1).concatBy("If you do,"));
        this.addAbility(ability);
    }

    private KataraWaterbendingMaster(final KataraWaterbendingMaster card) {
        super(card);
    }

    @Override
    public KataraWaterbendingMaster copy() {
        return new KataraWaterbendingMaster(this);
    }
}
