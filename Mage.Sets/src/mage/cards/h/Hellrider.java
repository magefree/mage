
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HasteAbility;
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
 * @author North
 */
public final class Hellrider extends CardImpl {

    public Hellrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(HasteAbility.getInstance());
        // Whenever a creature you control attacks, Hellrider deals 1 damage to defending player.
        this.addAbility(new HellriderTriggeredAbility());
    }

    private Hellrider(final Hellrider card) {
        super(card);
    }

    @Override
    public Hellrider copy() {
        return new Hellrider(this);
    }
}

class HellriderTriggeredAbility extends TriggeredAbilityImpl {

    public HellriderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    private HellriderTriggeredAbility(final HellriderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HellriderTriggeredAbility copy() {
        return new HellriderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(event.getSourceId());
        if (source != null && source.isControlledBy(controllerId)) {
            UUID defendingPlayerId = game.getCombat().getDefenderId(event.getSourceId());
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defendingPlayerId));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks, {this} deals 1 damage to the player or planeswalker it's attacking.";
    }
}
