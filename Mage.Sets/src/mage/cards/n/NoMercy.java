
package mage.cards.n;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class NoMercy extends CardImpl {

    public NoMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Whenever a creature deals damage to you, destroy it.
        this.addAbility(new NoMercyTriggeredAbility());
    }

    private NoMercy(final NoMercy card) {
        super(card);
    }

    @Override
    public NoMercy copy() {
        return new NoMercy(this);
    }

    public static class NoMercyTriggeredAbility extends TriggeredAbilityImpl {

        public NoMercyTriggeredAbility() {
            super(Zone.BATTLEFIELD, new DestroyTargetEffect());
        }

        private NoMercyTriggeredAbility(final NoMercyTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public NoMercyTriggeredAbility copy() {
            return new NoMercyTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getPlayerId().equals(this.getControllerId())) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null && permanent.isCreature(game)) {
                    this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a creature deals damage to you, destroy it.";
        }

    }
}
