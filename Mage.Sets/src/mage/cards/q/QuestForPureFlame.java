package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class QuestForPureFlame extends CardImpl {

    public QuestForPureFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // Whenever a source you control deals damage to an opponent, you may put a quest counter on Quest for Pure Flame.
        this.addAbility(new QuestForPureFlameTriggeredAbility());

        // Remove four quest counters from Quest for Pure Flame and sacrifice it: If any source you control would deal damage to a creature or player this turn, it deals double that damage to that creature or player instead.
        this.addAbility(new SimpleActivatedAbility(
                new QuestForPureFlameEffect(),
                new CompositeCost(
                        new RemoveCountersSourceCost(CounterType.QUEST.createInstance(4)),
                        new SacrificeSourceCost(),
                        "Remove four quest counters from {this} and sacrifice it"
                )
        ));
    }

    private QuestForPureFlame(final QuestForPureFlame card) {
        super(card);
    }

    @Override
    public QuestForPureFlame copy() {
        return new QuestForPureFlame(this);
    }
}

class QuestForPureFlameTriggeredAbility extends TriggeredAbilityImpl {

    QuestForPureFlameTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true);
    }

    private QuestForPureFlameTriggeredAbility(final QuestForPureFlameTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuestForPureFlameTriggeredAbility copy() {
        return new QuestForPureFlameTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return isControlledBy(game.getControllerId(event.getSourceId()));
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source you control deals damage to an opponent, you may put a quest counter on {this}.";
    }
}

class QuestForPureFlameEffect extends ReplacementEffectImpl {

    QuestForPureFlameEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "If any source you control would deal damage to a permanent or player this turn, " +
                "it deals double that damage to that permanent or player instead";
    }

    private QuestForPureFlameEffect(final QuestForPureFlameEffect effect) {
        super(effect);
    }

    @Override
    public QuestForPureFlameEffect copy() {
        return new QuestForPureFlameEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(game.getControllerId(event.getSourceId()));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
