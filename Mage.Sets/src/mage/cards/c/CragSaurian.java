
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author wetterlicht & L_J
 */
public final class CragSaurian extends CardImpl {

    public CragSaurian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}{R}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a source deals damage to Crag Saurian, that source's controller gains control of Crag Saurian.
        this.addAbility(new CragSaurianTriggeredAbility());
    }

    private CragSaurian(final CragSaurian card) {
        super(card);
    }

    @Override
    public CragSaurian copy() {
        return new CragSaurian(this);
    }

    private static class CragSaurianEffect extends OneShotEffect {

        public CragSaurianEffect() {
            super(Outcome.GainControl);
            this.staticText = "that source's controller gains control of {this}";
        }

        private CragSaurianEffect(CragSaurianEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            Player newController = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (newController != null && controller != null && !controller.equals(newController)) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
                effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
                game.addEffect(effect, source);
                return true;
            }
            return false;
        }

        @Override
        public Effect copy() {
            return new CragSaurianEffect(this);
        }
    }

    class CragSaurianTriggeredAbility extends TriggeredAbilityImpl {
    
        CragSaurianTriggeredAbility() {
            super(Zone.BATTLEFIELD, new CragSaurianEffect());
        }
    
        CragSaurianTriggeredAbility(final CragSaurianTriggeredAbility ability) {
            super(ability);
        }
    
        @Override
        public CragSaurianTriggeredAbility copy() {
            return new CragSaurianTriggeredAbility(this);
        }
    
        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
        }
    
        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getTargetId().equals(this.sourceId)) {
                UUID controller = game.getControllerId(event.getSourceId());
                if (controller != null) {
                    Player player = game.getPlayer(controller);
                    if (player != null) {
                        getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                        return true;
                    }
                }
            }
            return false;
        }
    
        @Override
        public String getTriggerPhrase() {
            return "Whenever a source deals damage to {this}, " ;
        }
    }
}
