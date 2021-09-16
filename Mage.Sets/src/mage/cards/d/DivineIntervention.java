package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class DivineIntervention extends CardImpl {

    public DivineIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{W}{W}");

        // Divine Intervention enters the battlefield with 2 intervention counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.INTERVENTION.createInstance(2));
        this.addAbility(new EntersBattlefieldAbility(effect, "with 2 intervention counters"));

        // At the beginning of your upkeep, remove an intervention counter from Divine Intervention.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RemoveCounterSourceEffect(CounterType.INTERVENTION.createInstance()), TargetController.YOU, false));

        // When you remove the last intervention counter from Divine Intervention, the game is a draw.
        this.addAbility(new DivineInterventionAbility());
    }

    private DivineIntervention(final DivineIntervention card) {
        super(card);
    }

    @Override
    public DivineIntervention copy() {
        return new DivineIntervention(this);
    }

    class DivineInterventionAbility extends TriggeredAbilityImpl {

        public DivineInterventionAbility() {
            super(Zone.BATTLEFIELD, new DivineAbilityEffect2(), false);
        }

        public DivineInterventionAbility(final DivineInterventionAbility ability) {
            super(ability);
        }

        @Override
        public DivineInterventionAbility copy() {
            return new DivineInterventionAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.COUNTERS_REMOVED;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return (event.getData().equals(CounterType.INTERVENTION.getName())
                    && event.getTargetId().equals(this.getSourceId())
                    && event.getPlayerId() != null
                    && event.getPlayerId() == this.getControllerId());  // the controller of this removed the counter 
        }

        @Override
        public String getRule() {
            return "When you remove the last intervention counter from {this}, the game is drawn.";
        }
    }

    class DivineAbilityEffect2 extends OneShotEffect {

        public DivineAbilityEffect2() {
            super(Outcome.Neutral);
            this.staticText = "you draw the game";
        }

        public DivineAbilityEffect2(final DivineAbilityEffect2 effect) {
            super(effect);
        }

        @Override
        public DivineAbilityEffect2 copy() {
            return new DivineAbilityEffect2(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (controller != null 
                    && sourcePermanent != null) {
                if (game.getState().getZone(sourcePermanent.getId()) == Zone.BATTLEFIELD
                        && sourcePermanent.getCounters(game).getCount(CounterType.INTERVENTION) == 0) {
                    game.setDraw(controller.getId());
                }
                return true;
            }
            return false;
        }
    }
}
