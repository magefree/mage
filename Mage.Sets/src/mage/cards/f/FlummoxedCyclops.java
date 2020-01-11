package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlummoxedCyclops extends CardImpl {

    public FlummoxedCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever two or more creatures your opponents control attack, Flummoxed Cyclops can't block this combat.
        this.addAbility(new FlummoxedCyclopsTriggeredAbility());
    }

    private FlummoxedCyclops(final FlummoxedCyclops card) {
        super(card);
    }

    @Override
    public FlummoxedCyclops copy() {
        return new FlummoxedCyclops(this);
    }
}

class FlummoxedCyclopsTriggeredAbility extends TriggeredAbilityImpl {

    FlummoxedCyclopsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CantBlockSourceEffect(Duration.EndOfCombat));
    }

    private FlummoxedCyclopsTriggeredAbility(final FlummoxedCyclopsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FlummoxedCyclopsTriggeredAbility copy() {
        return new FlummoxedCyclopsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(game.getCombat().getAttackingPlayerId())) {
            return false;
        }
        return game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .count() > 1;
    }

    @Override
    public String getRule() {
        return "Whenever two or more creatures your opponents control attack, {this} can't block this combat.";
    }

}
