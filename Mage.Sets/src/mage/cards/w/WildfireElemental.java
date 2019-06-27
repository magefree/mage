package mage.cards.w;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildfireElemental extends CardImpl {

    public WildfireElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent is dealt noncombat damage, creatures you control get +1/+0 until end of turn.
        this.addAbility(new WildfireElementalTriggeredAbility());
    }

    private WildfireElemental(final WildfireElemental card) {
        super(card);
    }

    @Override
    public WildfireElemental copy() {
        return new WildfireElemental(this);
    }
}

class WildfireElementalTriggeredAbility extends TriggeredAbilityImpl {

    WildfireElementalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.EndOfTurn), false);
    }

    private WildfireElementalTriggeredAbility(final WildfireElementalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WildfireElementalTriggeredAbility copy() {
        return new WildfireElementalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        return !damageEvent.isCombatDamage()
                && game.getOpponents(controllerId).contains(event.getTargetId());
    }

    @Override
    public String getRule() {
        return "Whenever an opponent is dealt noncombat damage, " +
                "creatures you control get +1/+0 until end of turn.";
    }

}
