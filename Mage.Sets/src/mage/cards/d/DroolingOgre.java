
package mage.cards.d;

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
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author wetterlicht
 */
public final class DroolingOgre extends CardImpl {

    public DroolingOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player casts an artifact spell, that player gains control of Drooling Ogre.
        this.addAbility(new DroolingOgreTriggeredAbility());
    }

    private DroolingOgre(final DroolingOgre card) {
        super(card);
    }

    @Override
    public DroolingOgre copy() {
        return new DroolingOgre(this);
    }

    private static class DroolingOgreEffect extends OneShotEffect {

        public DroolingOgreEffect() {
            super(Outcome.GainControl);
            this.staticText = "that player gains control of {this}";
        }

        private DroolingOgreEffect(DroolingOgreEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            Player newController = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (newController != null 
                    && controller != null 
                    && !controller.equals(newController)) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
                effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
                game.addEffect(effect, source);
                return true;
            }
            return false;
        }

        @Override
        public Effect copy() {
            return new DroolingOgreEffect(this);
        }

    }

    class DroolingOgreTriggeredAbility extends TriggeredAbilityImpl {

        public DroolingOgreTriggeredAbility() {
            super(Zone.BATTLEFIELD, new DroolingOgreEffect(), false);
        }

        public DroolingOgreTriggeredAbility(final DroolingOgreTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public DroolingOgreTriggeredAbility copy() {
            return new DroolingOgreTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.SPELL_CAST;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isArtifact(game)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                return true;
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a player casts an artifact spell, that player gains control of {this}.";
        }
    }
}
