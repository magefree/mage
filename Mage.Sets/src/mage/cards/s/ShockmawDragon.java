package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ShockmawDragon extends CardImpl {

    public ShockmawDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Shockmaw Dragon deals combat damage to a player, it deals 1 damage to each creature that player controls.
        this.addAbility(new PolisCrusherTriggeredAbility());
    }

    public ShockmawDragon(final ShockmawDragon card) {
        super(card);
    }

    @Override
    public ShockmawDragon copy() {
        return new ShockmawDragon(this);
    }
}

class PolisCrusherTriggeredAbility extends TriggeredAbilityImpl {

    public PolisCrusherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), true);
    }

    public PolisCrusherTriggeredAbility(final PolisCrusherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PolisCrusherTriggeredAbility copy() {
        return new PolisCrusherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                FilterPermanent filter = new FilterPermanent("a creature controlled by " + player.getLogName());
                filter.add(new CardTypePredicate(CardType.CREATURE));
                filter.add(new ControllerIdPredicate(event.getTargetId()));
                this.getTargets().clear();
                this.addTarget(new TargetPermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player,"
                + " it deals 1 damage to each creature that player controls";
    }
}
