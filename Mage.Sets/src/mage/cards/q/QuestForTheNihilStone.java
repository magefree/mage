
package mage.cards.q;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class QuestForTheNihilStone extends CardImpl {

    public QuestForTheNihilStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Whenever an opponent discards a card, you may put a quest counter on Quest for the Nihil Stone.
        this.addAbility(new DiscardsACardOpponentTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance(), true), true));

        // At the beginning of each opponent's upkeep, if that player has no cards in hand and Quest for the Nihil Stone has two or more quest counters on it, you may have that player lose 5 life.
        this.addAbility(new QuestForTheNihilStoneTriggeredAbility());
    }

    private QuestForTheNihilStone(final QuestForTheNihilStone card) {
        super(card);
    }

    @Override
    public QuestForTheNihilStone copy() {
        return new QuestForTheNihilStone(this);
    }
}

class QuestForTheNihilStoneTriggeredAbility extends TriggeredAbilityImpl {

    public QuestForTheNihilStoneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(5), true);
    }

    private QuestForTheNihilStoneTriggeredAbility(final QuestForTheNihilStoneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuestForTheNihilStoneTriggeredAbility copy() {
        return new QuestForTheNihilStoneTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent quest = game.getPermanent(super.getSourceId());
        if (quest == null) {
            Permanent questLKI = (Permanent) game.getLastKnownInformation(super.getSourceId(), Zone.BATTLEFIELD);
            quest = questLKI;
        }
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null
                    && opponent.getHand().isEmpty()
                    && quest.getCounters(game).getCount(CounterType.QUEST) >= 2) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(opponent.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each opponent's upkeep, if that player has no cards in hand and {this} has two or more quest counters on it, you may have that player lose 5 life.";
    }
}
