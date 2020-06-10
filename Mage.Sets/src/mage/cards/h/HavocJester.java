package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HavocJester extends CardImpl {

    public HavocJester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever you sacrifice a permanent, Havoc Jester deals 1 damage to any target.
        this.addAbility(new HavocJesterTriggeredAbility());
    }

    private HavocJester(final HavocJester card) {
        super(card);
    }

    @Override
    public HavocJester copy() {
        return new HavocJester(this);
    }
}

class HavocJesterTriggeredAbility extends TriggeredAbilityImpl {

    HavocJesterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
        this.addTarget(new TargetAnyTarget());
    }

    private HavocJesterTriggeredAbility(final HavocJesterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(getControllerId());
    }

    @Override
    public HavocJesterTriggeredAbility copy() {
        return new HavocJesterTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you sacrifice a permanent, {this} deals 1 damage to any target.";
    }
}
