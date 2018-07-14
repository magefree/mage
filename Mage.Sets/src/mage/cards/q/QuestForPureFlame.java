
package mage.cards.q;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
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
import mage.game.events.GameEvent.EventType;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class QuestForPureFlame extends CardImpl {

    public QuestForPureFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // Whenever a source you control deals damage to an opponent, you may put a quest counter on Quest for Pure Flame.
        this.addAbility(new QuestForPureFlameTriggeredAbility());

        // Remove four quest counters from Quest for Pure Flame and sacrifice it: If any source you control would deal damage to a creature or player this turn, it deals double that damage to that creature or player instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new QuestForPureFlameEffect(), new RemoveCountersSourceCost(CounterType.QUEST.createInstance(4)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public QuestForPureFlame(final QuestForPureFlame card) {
        super(card);
    }

    @Override
    public QuestForPureFlame copy() {
        return new QuestForPureFlame(this);
    }
}

class QuestForPureFlameTriggeredAbility extends TriggeredAbilityImpl {

    public QuestForPureFlameTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true);
    }

    public QuestForPureFlameTriggeredAbility(final QuestForPureFlameTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuestForPureFlameTriggeredAbility copy() {
        return new QuestForPureFlameTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
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

    public QuestForPureFlameEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "If any source you control would deal damage to a permanent or player this turn, it deals double that damage to that permanent or player instead";
    }

    public QuestForPureFlameEffect(final QuestForPureFlameEffect effect) {
        super(effect);
    }

    @Override
    public QuestForPureFlameEffect copy() {
        return new QuestForPureFlameEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_CREATURE
                || event.getType() == EventType.DAMAGE_PLAYER
                || event.getType() == EventType.DAMAGE_PLANESWALKER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(game.getControllerId(event.getSourceId()));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.addWithOverflowCheck(event.getAmount(), event.getAmount()));
        return false;
    }
}
