
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class CrosstownCourier extends CardImpl {

    public CrosstownCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.VEDALKEN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Crosstown Courier deals combat damage to a player, that player puts that many cards from the top of their library into their graveyard.
        this.addAbility(new CrosstownCourierTriggeredAbility());
    }

    private CrosstownCourier(final CrosstownCourier card) {
        super(card);
    }

    @Override
    public CrosstownCourier copy() {
        return new CrosstownCourier(this);
    }

    static class CrosstownCourierTriggeredAbility extends TriggeredAbilityImpl {

        public CrosstownCourierTriggeredAbility() {
            super(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(0), false);
        }

        public CrosstownCourierTriggeredAbility(final CrosstownCourierTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public CrosstownCourierTriggeredAbility copy() {
            return new CrosstownCourierTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
                for (Effect effect : getEffects()) {
                    if (effect instanceof PutLibraryIntoGraveTargetEffect) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                        ((PutLibraryIntoGraveTargetEffect) effect).setAmount(StaticValue.get(event.getAmount()));
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever {this} deals combat damage to a player, that player mills that many cards.";
        }
    }
}
