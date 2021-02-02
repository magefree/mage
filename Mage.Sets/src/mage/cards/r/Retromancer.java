
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class Retromancer extends CardImpl {

    public Retromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Retromancer becomes the target of a spell or ability, Retromancer deals 3 damage to that spell or ability's controller.
        this.addAbility(new RetromancerTriggeredAbility(new DamageTargetEffect(3)));
    }

    private Retromancer(final Retromancer card) {
        super(card);
    }

    @Override
    public Retromancer copy() {
        return new Retromancer(this);
    }
}

class RetromancerTriggeredAbility extends TriggeredAbilityImpl {

    public RetromancerTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public RetromancerTriggeredAbility(final RetromancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RetromancerTriggeredAbility copy() {
        return new RetromancerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        if (creature != null && event.getTargetId().equals(getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell or ability, {this} deals 3 damage to that spell or ability's controller.";
    }
}
