package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;
import mage.abilities.condition.common.SourceHasCounterCondition;

/**
 * @author jeffwadsworth
 */
public final class HeroOfBretagard extends CardImpl {

    private static final Condition FIVE_OR_MORE_COUNTERS = new SourceHasCounterCondition(CounterType.P1P1, 5);
    private static final Condition TEN_OR_MORE_COUNTERS = new SourceHasCounterCondition(CounterType.P1P1, 10);

    public HeroOfBretagard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever one or more cards are put into exile from your hand or a spell or ability you control exiles one or more permanents from the battlefield, put that many +1/+1 counters on Hero of Bretagard.
        this.addAbility(new HeroOfBretagardTriggeredAbility());

        // As long as Hero of Bretagard has five or more counters on it, it has flying and is an Angel in addition to its other types.
        Ability fiveCountersAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                FIVE_OR_MORE_COUNTERS,
                "As long as {this} has five or more counters on it, it has flying"
        ));
        fiveCountersAbility.addEffect(new ConditionalContinuousEffect(
                new AddCardSubTypeSourceEffect(Duration.WhileOnBattlefield, SubType.ANGEL),
                FIVE_OR_MORE_COUNTERS,
                "and is an Angel in addition to its other types"
        ));
        this.addAbility(fiveCountersAbility);

        // As long as Hero of Bretagard has ten or more counters on it, it has indestructible and is a God in addition to its other types.
        Ability tenCountersAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                TEN_OR_MORE_COUNTERS,
                "As long as {this} has ten or more counters on it, it has indestructible"
        ));
        tenCountersAbility.addEffect(new ConditionalContinuousEffect(
                new AddCardSubTypeSourceEffect(Duration.WhileOnBattlefield, true, SubType.GOD),
                TEN_OR_MORE_COUNTERS,
                "and is a God in addition to its other types"
        ));
        this.addAbility(tenCountersAbility);
    }

    private HeroOfBretagard(final HeroOfBretagard card) {
        super(card);
    }

    @Override
    public HeroOfBretagard copy() {
        return new HeroOfBretagard(this);
    }
}

class HeroOfBretagardTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    HeroOfBretagardTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private HeroOfBretagardTriggeredAbility(final HeroOfBretagardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HeroOfBretagardTriggeredAbility copy() {
        return new HeroOfBretagardTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getToZone() != Zone.EXILED) {
            return false;
        }
        switch (event.getFromZone()) {
            case BATTLEFIELD:
                // a spell or ability you control exiles a permanent (card or token)
                return event.getSource() != null && isControlledBy(event.getSource().getControllerId());
            case HAND:
                Card card = game.getCard(event.getTargetId());
                return card != null && card.isOwnedBy(getControllerId());
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int exiledCount = getFilteredEvents((ZoneChangeBatchEvent) event, game).size();
        if (exiledCount > 0) {
            this.getEffects().clear();
            this.getEffects().add(new AddCountersSourceEffect(CounterType.P1P1.createInstance(exiledCount)));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more cards are put into exile from your hand or a spell "
                + "or ability you control exiles one or more permanents from the battlefield, "
                + "put that many +1/+1 counters on {this}.";
    }
}
