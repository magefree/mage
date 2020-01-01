package mage.cards.r;

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
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RakdosRoustabout extends CardImpl {

    public RakdosRoustabout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Rakdos Roustabout becomes blocked, it deals 1 damage to the player or planeswalker its attacking.
        this.addAbility(new RakdosRoustaboutAbility());
    }

    private RakdosRoustabout(final RakdosRoustabout card) {
        super(card);
    }

    @Override
    public RakdosRoustabout copy() {
        return new RakdosRoustabout(this);
    }
}

class RakdosRoustaboutAbility extends TriggeredAbilityImpl {

    RakdosRoustaboutAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    private RakdosRoustaboutAbility(final RakdosRoustaboutAbility ability) {
        super(ability);
    }

    @Override
    public RakdosRoustaboutAbility copy() {
        return new RakdosRoustaboutAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (sourceId.equals(event.getTargetId())) {
            this.getEffects().get(0).setTargetPointer(
                    new FixedTarget(game.getCombat().getDefenderId(event.getTargetId()), game)
            );
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes blocked, it deals 1 damage to the player or planeswalker it's attacking";
    }
}
