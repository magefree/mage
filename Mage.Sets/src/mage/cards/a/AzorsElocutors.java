package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AzorsElocutors extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.FILIBUSTER, 5);

    public AzorsElocutors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W/U}{W/U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, put a filibuster counter on Azor's Elocutors. Then if Azor's Elocutors has five or more filibuster counters on it, you win the game.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.FILIBUSTER.createInstance())
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new WinGameSourceControllerEffect(), condition,
                "Then if {this} has five or more filibuster counters on it, you win the game"
        ));

        // Whenever a source deals damage to you, remove a filibuster counter from Azor's Elocutors.
        this.addAbility(new AzorsElocutorsTriggeredAbility());
    }

    private AzorsElocutors(final AzorsElocutors card) {
        super(card);
    }

    @Override
    public AzorsElocutors copy() {
        return new AzorsElocutors(this);
    }
}

class AzorsElocutorsTriggeredAbility extends TriggeredAbilityImpl {

    public AzorsElocutorsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.FILIBUSTER.createInstance()), false);
        setTriggerPhrase("Whenever a source deals damage to you, ");
    }

    private AzorsElocutorsTriggeredAbility(final AzorsElocutorsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AzorsElocutorsTriggeredAbility copy() {
        return new AzorsElocutorsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getTargetId());
    }
}
