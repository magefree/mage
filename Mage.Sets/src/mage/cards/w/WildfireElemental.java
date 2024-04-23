package mage.cards.w;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;

import java.util.UUID;
import java.util.stream.Stream;

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

class WildfireElementalTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

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
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public Stream<DamagedPlayerEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((DamagedBatchForOnePlayerEvent) event)
                .getEvents()
                .stream()
                .filter(e -> !e.isCombatDamage())
                .filter(e -> e.getAmount() > 0)
                .filter(e -> game.getOpponents(controllerId).contains(e.getTargetId()));
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return filterBatchEvent(event, game).findAny().isPresent();
    }

    @Override
    public String getRule() {
        return "Whenever an opponent is dealt noncombat damage, " +
                "creatures you control get +1/+0 until end of turn.";
    }

}
