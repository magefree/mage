
package mage.cards.a;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Backfir3
 */
public final class AetherSting extends CardImpl {

    public AetherSting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");


        // Whenever an opponent casts a creature spell, Aether Sting deals 1 damage to that player.
        this.addAbility(new AetherStingTriggeredAbility());
    }

    private AetherSting(final AetherSting card) {
        super(card);
    }

    @Override
    public AetherSting copy() {
        return new AetherSting(this);
    }
}

class AetherStingTriggeredAbility extends TriggeredAbilityImpl {

    public AetherStingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), false);
    }

    private AetherStingTriggeredAbility(final AetherStingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AetherStingTriggeredAbility copy() {
        return new AetherStingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && card.isCreature(game)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a creature spell, {this} deals 1 damage to that player.";
    }
}
