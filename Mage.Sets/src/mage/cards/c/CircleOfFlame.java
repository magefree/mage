
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author nantuko
 */
public final class CircleOfFlame extends CardImpl {

    public CircleOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");


        // Whenever a creature without flying attacks you or a planeswalker you control, Circle of Flame deals 1 damage to that creature.
        this.addAbility(new CircleOfFlameTriggeredAbility());
    }

    private CircleOfFlame(final CircleOfFlame card) {
        super(card);
    }

    @Override
    public CircleOfFlame copy() {
        return new CircleOfFlame(this);
    }
}

class CircleOfFlameTriggeredAbility extends TriggeredAbilityImpl {

    public CircleOfFlameTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    public CircleOfFlameTriggeredAbility(final CircleOfFlameTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CircleOfFlameTriggeredAbility copy() {
        return new CircleOfFlameTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // check has flying
        Permanent attacker = game.getPermanent(event.getSourceId());
        if (attacker == null || attacker.getAbilities().contains(FlyingAbility.getInstance())) {
            return false;
        }

        // check attacks you
        boolean youOrYourPlaneswalker;
        boolean you = event.getTargetId().equals(this.getControllerId());
        if (you) {
            youOrYourPlaneswalker = true;
        } else{ // check attacks your planeswalker
            Permanent permanent = game.getPermanent(event.getTargetId());
            youOrYourPlaneswalker = permanent != null
                    && permanent.isPlaneswalker(game)
                    && permanent.isControlledBy(this.getControllerId());
        }
        if (youOrYourPlaneswalker) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(attacker.getId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature without flying attacks you or a planeswalker you control, Circle of Flame deals 1 damage to that creature.";
    }
}
