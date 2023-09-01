package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author cbt33
 */
public final class ZombieCannibal extends CardImpl {

    public ZombieCannibal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Zombie Cannibal deals combat damage to a player, you may exile target card from that player's graveyard.
        this.addAbility(new ZombieCannibalTriggeredAbility());
    }

    private ZombieCannibal(final ZombieCannibal card) {
        super(card);
    }

    @Override
    public ZombieCannibal copy() {
        return new ZombieCannibal(this);
    }
}

class ZombieCannibalTriggeredAbility extends TriggeredAbilityImpl {

    public ZombieCannibalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect(null, "", Zone.GRAVEYARD), true);
    }

    private ZombieCannibalTriggeredAbility(final ZombieCannibalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZombieCannibalTriggeredAbility copy() {
        return new ZombieCannibalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.sourceId) || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        Player damagedPlayer = game.getPlayer(event.getTargetId());
        if (damagedPlayer == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card in " + damagedPlayer.getName() + "'s graveyard");
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        this.getTargets().clear();
        this.addTarget(target);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, "
                + "you may exile target card from that player's graveyard.";
    }
}
