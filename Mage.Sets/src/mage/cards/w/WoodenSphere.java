
package mage.cards.w;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;

/**
 *
 * @author KholdFuzion

 */
public final class WoodenSphere extends CardImpl {

    public WoodenSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Whenever a player casts a green spell, you may pay {1}. If you do, you gain 1 life.
        this.addAbility(new WoodenSphereAbility());
    }

    private WoodenSphere(final WoodenSphere card) {
        super(card);
    }

    @Override
    public WoodenSphere copy() {
        return new WoodenSphere(this);
    }
}

class WoodenSphereAbility extends TriggeredAbilityImpl {

    public WoodenSphereAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new GainLifeEffect(1), new GenericManaCost(1)), false);
    }

    private WoodenSphereAbility(final WoodenSphereAbility ability) {
        super(ability);
    }

    @Override
    public WoodenSphereAbility copy() {
        return new WoodenSphereAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getColor(game).isGreen();
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a green spell, you may pay {1}. If you do, you gain 1 life.";
    }

}