package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
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
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.filter.predicate.Predicate;

/**
 * @author jeffwadsworth
 */
public final class HeroOfBretagard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();

    static {
        filter.add(test1.instance);
        filter2.add(test2.instance);
    }

    public HeroOfBretagard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you exile one or more cards from your hand and/or permanents from the battlefield, put that many +1/+1 counters on Hero of Bretagard.
        this.addAbility(new HeroOfBretagardTriggeredAbility(new HeroOfBretagardEffect()));

        // As long as Hero of Bretagard has five or more counters on it, it has flying and is an Angel in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                        new SourceMatchesFilterCondition(filter),
                        "As long as Hero of Bretagard has five or more counters on it, it has flying ")));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new AddCardSubTypeSourceEffect(Duration.WhileOnBattlefield, SubType.ANGEL), new SourceMatchesFilterCondition(filter),
                        "and is an Angel in addition to its other types.")));

        // As long as Hero of Bretagard has ten or more counters on it, it has indestructible and is a God in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                        new SourceMatchesFilterCondition(filter2),
                        "As long as Hero of Bretagard has ten or more counters on it, it has indestructible ")));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new AddCardSubTypeSourceEffect(Duration.WhileOnBattlefield, SubType.GOD), new SourceMatchesFilterCondition(filter2),
                        "and is a God in addition to its other types.")));

    }

    private HeroOfBretagard(final HeroOfBretagard card) {
        super(card);
    }

    @Override
    public HeroOfBretagard copy() {
        return new HeroOfBretagard(this);
    }
}

class HeroOfBretagardTriggeredAbility extends TriggeredAbilityImpl {

    HeroOfBretagardTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    HeroOfBretagardTriggeredAbility(final HeroOfBretagardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HeroOfBretagardTriggeredAbility copy() {
        return new HeroOfBretagardTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent != null
                && Zone.HAND == zEvent.getFromZone()
                && Zone.EXILED == zEvent.getToZone()
                && zEvent.getTargetId() != null) {
            Card card = game.getCard(zEvent.getTargetId());
            if (card != null) {
                UUID cardOwnerId = card.getOwnerId();
                if (cardOwnerId != null
                        && card.isOwnedBy(getControllerId())) {
                    this.getEffects().get(0).setValue("number", 1);
                    return true;
                }
            }
        }
        if (zEvent != null
                && Zone.BATTLEFIELD == zEvent.getFromZone()
                && Zone.EXILED == zEvent.getToZone()
                && zEvent.getTargetId() != null) {
            Card card = game.getCard(zEvent.getTargetId());
            if (card != null) {
                UUID cardOwnerId = card.getOwnerId();
                if (cardOwnerId != null
                        && card.isOwnedBy(getControllerId())) {
                    this.getEffects().get(0).setValue("number", 1);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you exile one or more cards from your hand and/or permanents from the battlefield, put that many +1/+1 counters on {this}";

    }
}

class HeroOfBretagardEffect extends OneShotEffect {

    public HeroOfBretagardEffect() {
        super(Outcome.Benefit);
    }

    public HeroOfBretagardEffect(final HeroOfBretagardEffect effect) {
        super(effect);
    }

    @Override
    public HeroOfBretagardEffect copy() {
        return new HeroOfBretagardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new AddCountersSourceEffect(CounterType.P1P1.createInstance((Integer) this.getValue("number"))).apply(game, source);
    }
}

enum test1 implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getCounters(game).values().stream().anyMatch(counter -> counter.getCount() > 4);
    }

    @Override
    public String toString() {
        return "any counter";
    }
}

enum test2 implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getCounters(game).values().stream().anyMatch(counter -> counter.getCount() > 9);
    }

    @Override
    public String toString() {
        return "any counter";
    }
}
